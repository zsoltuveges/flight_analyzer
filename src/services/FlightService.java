package services;

import models.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FlightService implements XmlReader {
    private File flightsFile;
    private JAXBContext jaxbContext;
    private Unmarshaller jaxbUnmarshaller;
    private String filePath = "src/resources/flights.xml";
    private ArrayList<Flight> flights;
    private AirlineService airlineService;
    private CityService cityService;
    private GraphService graphService;

    public FlightService(AirlineService airlineService, CityService cityService, GraphService graphService) {
        this.airlineService = airlineService;
        this.cityService = cityService;
        this.graphService = graphService;
        try {
            initParser();
            readDataFromFile();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    @Override
    public void initParser() throws JAXBException {
        flightsFile = new File(filePath);
        jaxbContext = JAXBContext.newInstance(Flights.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }

    @Override
    public void readDataFromFile() throws JAXBException {
        Flights flightsModel = (Flights) jaxbUnmarshaller.unmarshal(flightsFile);
        flights = flightsModel.getFlights();
        makeConnectionBetweenModels();
    }

    private void makeConnectionBetweenModels() {
        flights.forEach(flight -> {
            flight.setAirline(airlineService.getAirlineById(flight.getAirline().getId()));
            flight.setOrigin(cityService.getCityById(flight.getOrigin().getId()));
            flight.setDestination(cityService.getCityById(flight.getDestination().getId()));
        });
    }

    public List<Flight> getFlightsByAirlineId(int id) {
        return flights.stream().filter(flight -> flight.getAirline().getId() == id).collect(Collectors.toList());
    }

    public List<Flight> getFlightsByCities(Integer originId, Integer destId) {
        return getFlightsByCitiesFromGivenFlights(originId, destId, flights);
    }

    public List<Flight> getFlightsByCitiesFromGivenFlights(Integer originId, Integer destId, List<Flight> flights) {
        if (originId == null && destId != null) {
            return flights.stream()
                    .filter(flight -> flight.getDestination().getId() == destId)
                    .collect(Collectors.toList());
        } else if (destId == null && originId != null) {
            return flights.stream()
                    .filter(flight -> flight.getOrigin().getId() == originId)
                    .collect(Collectors.toList());
        } else {
            return flights.stream()
                    .filter(flight -> flight.getDestination().getId() == destId
                            && flight.getOrigin().getId() == originId)
                    .collect(Collectors.toList());
        }
    }

    public List<Flight> getShortestFlightBetweenTwoCityByAirline(int originId, int destId, int airlineId) {
        List<Flight> shortestFlights = getDirectFlights(originId, destId, airlineId);
        if (shortestFlights.size() > 0) {
            return shortestFlights;
        }
        shortestFlights = getShortestFlightWithTransferByAirline(originId, destId, airlineId);

        return shortestFlights;
    }

    private List<Flight> getDirectFlights(int originId, int destId, int airlineId) {
        List<Flight> shortestFlights = getFlightsByCitiesFromGivenFlights(originId, destId, getFlightsByAirlineId(airlineId));
        shortestFlights = shortestFlights.stream()
                .filter(flight -> flight.getAirline().getId() == airlineId)
                .sorted(Comparator.comparingInt(Flight::getDistance))
                .collect(Collectors.toList());
        if (shortestFlights.size() > 1) {
            shortestFlights.subList(1, shortestFlights.size()).clear();
        }
        return shortestFlights;
    }

    private List<Flight> getShortestFlightWithTransferByAirline(int originId, int destId, int airlineId) {
        List<Flight> result = new ArrayList<>();
        List<Flight> allFlightsByAirline = getFlightsByAirlineId(airlineId);
        if (getFlightsByCitiesFromGivenFlights(originId, null, allFlightsByAirline).size() == 0
                || getFlightsByCitiesFromGivenFlights(null, destId, allFlightsByAirline).size() == 0) {
            return result;
        }

        List<Flight> originFlights = allFlightsByAirline.stream()
                .filter(flight -> flight.getOrigin().getId() == originId)
                .collect(Collectors.toList());


        Node firstNodeModel = new Node(null);
        boolean firstNode = true;
        Graph graph = new Graph();
        for (Flight flight : allFlightsByAirline) {
            if (flight.getDestination().getId() == originId) {
                continue;
            }
            Node node = new Node(flight);

            List<Flight> neighborhoodFlights = getFlightsByCitiesFromGivenFlights(flight.getDestination().getId(), null, allFlightsByAirline);

            for (Flight neighborhoodFlight : neighborhoodFlights) {
                node.addDestination(new Node(neighborhoodFlight), flight.getDistance());
            }
            if (firstNode) {
                firstNodeModel = node;
                firstNode = false;
            }
            if (node.getAdjacentNodes().size() > 0 || node.getFlight().getDestination().getId() == destId) {
                graph.addNode(node);
            }
        }

        graph = graphService.calculateShortestPathFromSource(graph, firstNodeModel);
        graph.getNodes().forEach(node -> result.add(node.getFlight()));
        return result;
    }

    public Flight getFlightById(int id) {
        return flights.stream().filter(flight -> flight.getId() == id).findFirst().orElse(null);
    }
}
