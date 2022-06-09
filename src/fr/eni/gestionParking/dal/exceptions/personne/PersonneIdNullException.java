package fr.eni.gestionParking.dal.exceptions.personne;

public class PersonneIdNullException extends Exception {
    public PersonneIdNullException() {
    }

    public PersonneIdNullException(String message) {
        super(message);
    }

    public PersonneIdNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
