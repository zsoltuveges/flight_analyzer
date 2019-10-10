package services;

import models.City;
import models.Flight;
import models.Flights;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
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

    public FlightService(AirlineService airlineService, CityService cityService) {
        this.airlineService = airlineService;
        this.cityService = cityService;
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
}
