package hu.rszoft.zsolt.flightanalyzer.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "flights")
@XmlAccessorType(XmlAccessType.FIELD)
public class Flights {

    @XmlElement(name = "flight")
    private ArrayList<Flight> flights;

    public Flights() {
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }
}
