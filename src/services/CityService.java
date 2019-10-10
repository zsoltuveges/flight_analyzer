package services;

import models.Cities;
import models.City;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class CityService implements XmlReader {
    private File citiesFile;
    private JAXBContext jaxbContext;
    private Unmarshaller jaxbUnmarshaller;
    private String filePath = "src/resources/cities.xml";
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

    public City getCityById(int id) {
        return cities.stream().filter((City city) -> city.getId() == id).findFirst().orElse(null);
    }

    public City getSmallestCity() {
        return cities.stream()
                .sorted(Comparator.comparingInt(City::getPopulation))
                .collect(Collectors.toList()).get(0);
    }

    public City getLargestCity() {
        return cities.stream()
                .sorted(Comparator.comparingInt(City::getPopulation).reversed())
                .collect(Collectors.toList()).get(0);
    }
}
