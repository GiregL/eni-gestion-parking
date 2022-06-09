package fr.eni.gestionParking.dal.exceptions.voiture;

public class VoitureAlreadyExistsException extends Exception {
    public VoitureAlreadyExistsException() {
    }

    public VoitureAlreadyExistsException(String message) {
        super(message);
    }

    public VoitureAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
