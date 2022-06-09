package fr.eni.gestionParking.dal.expose;

import fr.eni.gestionParking.bo.Pair;
import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;
import fr.eni.gestionParking.dal.exceptions.PersonneVoitureDAOException;

import java.util.List;

public interface PersonneVoitureDAO {

    List<Pair<Personne, Voiture>> fullJoin() throws PersonneVoitureDAOException;

}
