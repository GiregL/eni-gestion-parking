package fr.eni.gestionParking.bll.services;

import fr.eni.gestionParking.bll.exceptions.BLLException;
import fr.eni.gestionParking.bll.exceptions.CSVExportException;
import fr.eni.gestionParking.bll.exceptions.XMLSerializerException;
import fr.eni.gestionParking.bll.expose.CSVService;
import fr.eni.gestionParking.bll.expose.ServiceFactory;
import fr.eni.gestionParking.bo.Pair;
import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;
import fr.eni.gestionParking.dal.expose.DAOFactory;

import java.io.*;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVServiceImpl implements CSVService {

    private static CSVServiceImpl instance = null;

    public static CSVService getInstance() {
        if (instance == null)
            instance = new CSVServiceImpl();
        return instance;
    }

    private CSVServiceImpl() {}

    private static final Logger LOGGER = Logger.getLogger(CSVServiceImpl.class.getSimpleName());

    @Override
    public void writeToFile(File target) throws CSVExportException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(target))) {
            // Header
            outputStream.write("personne.id;personne.nom;personne.prenom;voiture.id;voiture.nom;voiture.plaque_immat;voiture.utilisateur\n".getBytes());

            List<Pair<Personne, Voiture>> pairs = ServiceFactory.getPersonneVoitureService().fullTables();
            Function<Pair<Personne, Voiture>, String> serialize = (pair -> {
                StringBuilder builder = new StringBuilder();

                pair.ifLeftPresentOrElse(personne -> {
                    builder.append(personne.getId()).append(';');
                    builder.append(personne.getNom()).append(';');
                    builder.append(personne.getPrenom()).append(';');
                }, () -> builder.append(";;;"));

                pair.ifRightPresentOrElse(voiture -> {
                    builder.append(voiture.getId()).append(';');
                    builder.append(voiture.getNom()).append(';');
                    builder.append(voiture.getPlaqueImmatriculation()).append(';');
                    builder.append(voiture.getPersonne() != null && voiture.getPersonne().getId() != null ? voiture.getPersonne().getId() : "").append(';');
                }, () -> builder.append(";;;;"));

                return builder.append("\n").toString();
            });

            for (Pair<Personne, Voiture> pair : pairs) {
                outputStream.write(serialize.apply(pair).getBytes());
            }

            LOGGER.info("[writeToFile] Finished writing to file.");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "[writeToFile] File not found : " + e.getMessage());
            throw new CSVExportException("BLL - " + e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "[writeToFile] IO error : " + e.getMessage());
            throw new CSVExportException("BLL - " + e.getMessage());
        } catch (BLLException e) {
            LOGGER.severe("[writeToFile] Error : " + e.getMessage());
            throw new CSVExportException(e.getMessage());
        }
    }
}
