package fr.eni.gestionParking.bll.exceptions;

public class CSVExportException extends BLLException {
    public CSVExportException() {
    }

    public CSVExportException(String message) {
        super(message);
    }

    public CSVExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
