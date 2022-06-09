package fr.eni.gestionParking.bll.exceptions;

public class XMLSerializerException extends BLLException {
    public XMLSerializerException() {
        super();
    }

    public XMLSerializerException(String message) {
        super(message);
    }

    public XMLSerializerException(String message, Throwable cause) {
        super(message, cause);
    }
}
