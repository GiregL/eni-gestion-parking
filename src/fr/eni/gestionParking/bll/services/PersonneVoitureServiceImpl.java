package fr.eni.gestionParking.bll.services;

import fr.eni.gestionParking.bll.exceptions.BLLException;
import fr.eni.gestionParking.bll.expose.PersonneVoitureService;
import fr.eni.gestionParking.bo.Pair;
import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;
import fr.eni.gestionParking.dal.exceptions.PersonneVoitureDAOException;
import fr.eni.gestionParking.dal.expose.DAOFactory;

import java.util.List;
import java.util.logging.Logger;

public class PersonneVoitureServiceImpl implements PersonneVoitureService {

    private static final Logger LOGGER = Logger.getLogger(PersonneVoitureServiceImpl.class.getSimpleName());

    private static PersonneVoitureServiceImpl instance = null;

    private PersonneVoitureServiceImpl() {}

    public static PersonneVoitureService getInstance() {
        if (instance == null)
            instance = new PersonneVoitureServiceImpl();
        return instance;
    }

    @Override
    public List<Pair<Personne, Voiture>> fullTables() throws BLLException {
        try {
            return DAOFactory.getPersonneVoitureDAO().fullJoin();
        } catch (PersonneVoitureDAOException e) {
            LOGGER.severe("[fullTables] Error : " + e.getMessage());
            throw new BLLException(e.getMessage());
        }
    }
}
