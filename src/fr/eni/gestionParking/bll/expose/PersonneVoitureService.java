package fr.eni.gestionParking.bll.expose;

import fr.eni.gestionParking.bll.exceptions.BLLException;
import fr.eni.gestionParking.bo.Pair;
import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;

import java.util.List;

public interface PersonneVoitureService {

    List<Pair<Personne, Voiture>> fullTables() throws BLLException;

}
