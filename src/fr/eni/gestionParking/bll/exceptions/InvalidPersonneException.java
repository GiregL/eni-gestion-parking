package fr.eni.gestionParking.bll.exceptions;

public class InvalidPersonneException extends BLLException {
    public InvalidPersonneException() {
    }

    public InvalidPersonneException(String message) {
        super(message);
    }

    public InvalidPersonneException(String message, Throwable cause) {
        super(message, cause);
    }
}
