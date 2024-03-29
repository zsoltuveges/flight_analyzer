package hu.rszoft.zsolt.flightanalyzer.services;

import hu.rszoft.zsolt.flightanalyzer.models.Airline;
import hu.rszoft.zsolt.flightanalyzer.models.Airlines;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class AirlineService implements XmlReader {
    private File airlinesFile;
    private JAXBContext jaxbContext;
    private Unmarshaller jaxbUnmarshaller;
    private String filePath = "src/resources/airlines.xml";
    private List<Airline> airlines;

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

    public List<Airline> getAirlines() {
        return airlines;
    }

    public Airline getAirlineById(int id) {
        return airlines.stream().filter(airline -> airline.getId() == id).findFirst().orElse(null);
    }
}
