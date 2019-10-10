package models;

public class Flight {
    private int id;
    private int distance;
    private int timeInterval;
    private Airline airline;
    private City origin;
    private City destination;

    public Flight() {
    }

    public Flight(int id, int distance, int timeInterval, Airline airline, City origin, City destination) {
        this.id = id;
        this.distance = distance;
        this.timeInterval = timeInterval;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public City getOrigin() {
        return origin;
    }

    public void setOrigin(City origin) {
        this.origin = origin;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", distance=" + distance +
                ", timeInterval=" + timeInterval +
                ", airline=" + airline +
                ", origin=" + origin +
                ", destination=" + destination +
                '}';
    }
}
