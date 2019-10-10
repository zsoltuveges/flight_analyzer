package services;

import models.Cities;
import models.City;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;

public class CityService implements XmlReader {
    private File citiesFile;
    private JAXBContext jaxbContext;
    private Unmarshaller jaxbUnmarshaller;
    private String filePath = "resources/cities.xml";
    private ArrayList<City> cities;

    public CityService() {
        try {
            initParser();
            readDataFromFile();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    @Override
    public void initParser() throws JAXBException {
        citiesFile = new File(filePath);
        jaxbContext = JAXBContext.newInstance(Cities.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }

    @Override
    public void readDataFromFile() throws JAXBException {
        Cities citiesModel = (Cities) jaxbUnmarshaller.unmarshal(this.citiesFile);
        cities = citiesModel.getCities();
    }
}
