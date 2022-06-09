package fr.eni.gestionParking.dal.exceptions.voiture;

public class VoitureIdNullException extends Exception {
    public VoitureIdNullException() {
    }

    public VoitureIdNullException(String message) {
        super(message);
    }

    public VoitureIdNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
