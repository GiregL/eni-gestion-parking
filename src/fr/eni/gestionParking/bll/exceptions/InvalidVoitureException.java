package fr.eni.gestionParking.bll.exceptions;

public class InvalidVoitureException extends BLLException {
    public InvalidVoitureException() {
    }

    public InvalidVoitureException(String message) {
        super(message);
    }

    public InvalidVoitureException(String message, Throwable cause) {
        super(message, cause);
    }
}
