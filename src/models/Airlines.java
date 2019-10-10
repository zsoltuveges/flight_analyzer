package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "airlines")
@XmlAccessorType(XmlAccessType.FIELD)
public class Airlines {

    @XmlElement(name = "airline")
    private ArrayList<Airline> airlines;

    public Airlines() {
    }

    public ArrayList<Airline> getAirlines() {
        return airlines;
    }
}
