package hu.rszoft.zsolt.flightanalyzer;

import hu.rszoft.zsolt.flightanalyzer.models.Airline;
import hu.rszoft.zsolt.flightanalyzer.models.City;
import hu.rszoft.zsolt.flightanalyzer.models.Flight;
import hu.rszoft.zsolt.flightanalyzer.services.AirlineService;
import hu.rszoft.zsolt.flightanalyzer.services.CityService;
import hu.rszoft.zsolt.flightanalyzer.services.FlightService;
import hu.rszoft.zsolt.flightanalyzer.services.GraphService;

import java.util.List;

public class FlightAnalyzer {
    private AirlineService airlineService;
    private FlightService flightService;
    private CityService cityService;

    public FlightAnalyzer() {
        airlineService = new AirlineService();
        cityService = new CityService();
        flightService = new FlightService(airlineService, cityService, new GraphService());
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
                int sumTimeInterval = 0;
                for (Flight flight : tempFlights) {
                    sumTimeInterval += flight.getTimeInterval();
                    printFlight(flight);
                }
                printSumTimeInterval(sumTimeInterval);
            } else {
                System.out.println("            Nincs útvonal");
            }
        }

//        System.out.println("");
//        System.out.println("Bármely légitársasággal a legrövidebb út:");
//        List<Flight> tempFlights = flightService.getShortestFlightBetweenTwoCity(smallestCity.getId(), largestCity.getId());
//        if (tempFlights.size() > 0) {
//            int sumTimeInterval = 0;
//            for (Flight flight : tempFlights) {
//                sumTimeInterval += flight.getTimeInterval();
//                printFlightWithAirline(flight);
//            }
//            printSumTimeInterval(sumTimeInterval);
//        } else {
//            System.out.println("            Nincs útvonal");
//        }
    }

    private void printFlight(Flight flight) {
        int hours = flight.getTimeInterval() / 60;
        int minutes = flight.getTimeInterval() % 60;
        String timeString = hours == 0 ? String.format("%d perc", minutes) : String.format("%d óra %d perc", hours, minutes);
        System.out.println(String.format("      %s -> %s: %s", flight.getOrigin().getName(), flight.getDestination().getName(), timeString));
    }

    private void printSumTimeInterval(int value) {
        int hours = value / 60;
        int minutes = value % 60;
        String timeString = hours == 0 ? String.format("%d perc", minutes) : String.format("%d óra %d perc", hours, minutes);
        System.out.println("      --------------");
        System.out.println(String.format("      Összesen: %s", timeString));
    }

    private void printFlightWithAirline(Flight flight) {
        int hours = flight.getTimeInterval() / 60;
        int minutes = flight.getTimeInterval() % 60;
        String timeString = hours == 0 ? String.format("%d perc", minutes) : String.format("%d óra %d perc", hours, minutes);
        System.out.println(String.format("      %s: %s -> %s: %s",
                flight.getAirline().getName(),
                flight.getOrigin().getName(),
                flight.getDestination().getName(),
                timeString));
    }


}
