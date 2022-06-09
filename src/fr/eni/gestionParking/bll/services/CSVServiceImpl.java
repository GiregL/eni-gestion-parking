package fr.eni.gestionParking.bll.services;

import fr.eni.gestionParking.bll.exceptions.CSVExportException;
import fr.eni.gestionParking.bll.exceptions.XMLSerializerException;
import fr.eni.gestionParking.bll.expose.CSVService;
import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVServiceImpl implements CSVService {

    private static final Logger LOGGER = Logger.getLogger(CSVServiceImpl.class.getSimpleName());

    @Override
    public void writeToFile(File target) throws CSVExportException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(target))) {
            // Header
            outputStream.write("personne.id;personne.nom;personne.prenom;voiture.id;voiture.nom;voiture.plaque_immat;voiture.utilisateur".getBytes());



        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "[writeToFile] File not found : " + e.getMessage());
            throw new CSVExportException("BLL - " + e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "[writeToFile] IO error : " + e.getMessage());
            throw new CSVExportException("BLL - " + e.getMessage());
        }
    }
}
