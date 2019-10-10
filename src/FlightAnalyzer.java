import models.City;
import services.AirlineService;
import services.CityService;
import services.FlightService;

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
    }
}
