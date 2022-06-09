package fr.eni.gestionParking.bll.exceptions;

public class BLLException extends Exception {
    public BLLException() {
    }

    public BLLException(String message) {
        super(message);
    }

    public BLLException(String message, Throwable cause) {
        super(message, cause);
    }
}
