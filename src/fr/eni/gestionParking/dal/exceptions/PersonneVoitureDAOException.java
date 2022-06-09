package fr.eni.gestionParking.dal.exceptions;

public class PersonneVoitureDAOException extends Exception {

    public PersonneVoitureDAOException() {
    }

    public PersonneVoitureDAOException(String message) {
        super(message);
    }

    public PersonneVoitureDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
