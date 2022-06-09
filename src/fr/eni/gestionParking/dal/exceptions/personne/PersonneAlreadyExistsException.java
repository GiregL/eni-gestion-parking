package fr.eni.gestionParking.dal.exceptions.personne;

public class PersonneAlreadyExistsException extends Exception {
    public PersonneAlreadyExistsException() {
    }

    public PersonneAlreadyExistsException(String message) {
        super(message);
    }

    public PersonneAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
