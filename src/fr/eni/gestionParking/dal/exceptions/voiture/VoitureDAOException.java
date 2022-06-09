package fr.eni.gestionParking.dal.exceptions.voiture;

public class VoitureDAOException extends Exception {
    public VoitureDAOException() {
    }

    public VoitureDAOException(String message) {
        super(message);
    }

    public VoitureDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
