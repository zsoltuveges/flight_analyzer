import models.Airline;
import models.City;
import models.Flight;
import services.AirlineService;
import services.CityService;
import services.FlightService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightAnalyzer {
    private AirlineService airlineService;
    private FlightService flightService;
    private CityService cityService;

    public FlightAnalyzer() {
        airlineService = new AirlineService();
        cityService = new CityService();
        flightService = new FlightService(airlineService, cityService);
    }

    public void start() {
        City smallestCity = cityService.getSmallestCity();
        City largestCity = cityService.getLargestCity();
        System.out.println(String.format("Legkissebb város: %s, %d lakos", smallestCity.getName(), smallestCity.getPopulation()));
        System.out.println(String.format("Legnagyobb város: %s, %d lakos", largestCity.getName(), largestCity.getPopulation()));

        System.out.println("Legrövidebb út:");
        for (Airline airline : airlineService.getAirlines()) {
            System.out.println("    " + airline.getName() + ":");
            List<Flight> tempFlights = flightService.getShortestFlightBetweenTwoCityByAirline(smallestCity.getId(), largestCity.getId(), airline.getId());
            if (tempFlights.size() > 0) {
                for (Flight flight : tempFlights) {
                    System.out.println(String.format("      %s -> %s: %d perc", flight.getOrigin().getName(), flight.getDestination().getName(), flight.getTimeInterval()));
                }
            } else {
                System.out.println("            Nincs útvonal");
            }
        }
    }


}
