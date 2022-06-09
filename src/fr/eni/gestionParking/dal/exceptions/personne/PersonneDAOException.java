package fr.eni.gestionParking.dal.exceptions.personne;

public class PersonneDAOException extends Exception {
    public PersonneDAOException() {
    }

    public PersonneDAOException(String message) {
        super(message);
    }

    public PersonneDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
