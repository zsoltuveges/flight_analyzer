package services;

import models.Airline;
import models.Airlines;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;

public class AirlineService implements XmlReader {
    private File airlinesFile;
    private JAXBContext jaxbContext;
    private Unmarshaller jaxbUnmarshaller;
    private String filePath = "src/resources/airlines.xml";
    private ArrayList<Airline> airlines;

    public AirlineService() {
        try {
            initParser();
            readDataFromFile();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initParser() throws JAXBException {
        airlinesFile = new File(filePath);
        jaxbContext = JAXBContext.newInstance(Airlines.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }

    @Override
    public void readDataFromFile() throws JAXBException {
        Airlines airlinesModel = (Airlines) jaxbUnmarshaller.unmarshal(this.airlinesFile);
        airlines = airlinesModel.getAirlines();
    }

    public ArrayList<Airline> getAirlines() {
        return airlines;
    }

    public Airline getAirlineById(int id) {
        return airlines.stream().filter(airline -> airline.getId() == id).findFirst().orElse(null);
    }
}
