package fr.eni.gestionParking.dal.expose;

import fr.eni.gestionParking.dal.jdbc.PersonneDAOImpl;
import fr.eni.gestionParking.dal.jdbc.PersonneVoitureDAOImpl;
import fr.eni.gestionParking.dal.jdbc.VoitureDAOImpl;

public class DAOFactory {

    public static PersonneDAO getPersonneDAO() {
        return new PersonneDAOImpl();
    }

    public static VoitureDAO getVoitureDAO() {
        return new VoitureDAOImpl();
    }

    public static PersonneVoitureDAO getPersonneVoitureDAO() {
        return new PersonneVoitureDAOImpl();
    }

}
