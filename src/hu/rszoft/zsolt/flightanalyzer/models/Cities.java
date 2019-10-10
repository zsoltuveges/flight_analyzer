package hu.rszoft.zsolt.flightanalyzer.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "cities")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cities {

    @XmlElement(name = "city")
    private ArrayList<City> cities;

    public Cities() {
    }

    public ArrayList<City> getCities() {
        return cities;
    }
}
