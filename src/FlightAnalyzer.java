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
        System.out.println(airlineService.getAirlines().toString());
        System.out.println(flightService.getFlights().toString());
        System.out.println(cityService.getCities().toString());
    }
}
