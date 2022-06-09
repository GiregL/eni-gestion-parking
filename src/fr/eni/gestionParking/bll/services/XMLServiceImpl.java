package fr.eni.gestionParking.bll.services;

import fr.eni.gestionParking.bll.exceptions.XMLSerializerException;
import fr.eni.gestionParking.bll.expose.XMLService;
import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XMLServiceImpl implements XMLService {

    private static final Logger LOGGER = Logger.getLogger(XMLServiceImpl.class.getSimpleName());

    private static XMLServiceImpl instance = null;

    public static XMLService getInstance() {
        if (instance == null)
            instance = new XMLServiceImpl();
        return instance;
    }

    private XMLServiceImpl() {}


    @Override
    public void writeToFile(File target, List<Personne> personnes, List<Voiture> voitures) throws XMLSerializerException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(target))) {
            outputStream.write("<GestionParking>".getBytes());

            // Personnes
            outputStream.write("<Personnes>".getBytes());
            for (Personne p : personnes) {
                outputStream.write(p.toXML().getBytes());
            }
            outputStream.write("</Personnes>".getBytes());

            // Voitures (contenant les personnes)
            outputStream.write("<Voitures>".getBytes());
            for (Voiture v : voitures) {
                outputStream.write(v.toXML().getBytes());
            }
            outputStream.write("</Voitures>".getBytes());

            outputStream.write("</GestionParking>".getBytes());
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "[writeToFile] File not found : " + e.getMessage());
            throw new XMLSerializerException("BLL - " + e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "[writeToFile] IO error : " + e.getMessage());
            throw new XMLSerializerException("BLL - " + e.getMessage());
        }
    }
}
