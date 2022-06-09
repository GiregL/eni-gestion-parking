package fr.eni.gestionParking.bll.services;

import fr.eni.gestionParking.bll.exceptions.BLLException;
import fr.eni.gestionParking.bll.expose.PersonneVoitureService;
import fr.eni.gestionParking.bo.Pair;
import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;

import java.util.List;
import java.util.logging.Logger;

public class PersonneVoitureServiceImpl implements PersonneVoitureService {

    private static final Logger LOGGER = Logger.getLogger(PersonneVoitureServiceImpl.class.getSimpleName());

    @Override
    public List<Pair<Personne, Voiture>> fullTables() throws BLLException {

    }
}
