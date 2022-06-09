package fr.eni.gestionParking.bll.expose;

import fr.eni.gestionParking.bll.exceptions.CSVExportException;

import java.io.File;

public interface CSVService {

    void writeToFile(File target) throws CSVExportException;

}
