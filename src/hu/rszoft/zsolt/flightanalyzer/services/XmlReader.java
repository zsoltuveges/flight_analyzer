package hu.rszoft.zsolt.flightanalyzer.services;

import javax.xml.bind.JAXBException;

public interface XmlReader {
    void initParser() throws JAXBException;

    void readDataFromFile() throws JAXBException;
}
