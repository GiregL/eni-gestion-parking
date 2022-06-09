package fr.eni.gestionParking.bll.expose;

import fr.eni.gestionParking.bll.exceptions.XMLSerializerException;
import fr.eni.gestionParking.bll.services.XMLServiceImpl;
import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;

import java.io.File;
import java.util.List;

public interface XMLService {
    void writeToFile(File target, List<Personne> personnes, List<Voiture> voitures) throws XMLSerializerException;
}
