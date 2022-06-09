package fr.eni.gestionParking.bll.expose;

import fr.eni.gestionParking.bll.services.*;

public class ServiceFactory {

    public static PersonneService getPersonneService() {
        return PersonneServiceImpl.getInstance();
    }

    public static VoitureService getVoitureService() {
        return VoitureServiceImpl.getInstance();
    }

    public static XMLService getXMLService() {
        return XMLServiceImpl.getInstance();
    }

    public static CSVService getCSVService() {
        return CSVServiceImpl.getInstance();
    }

    public static PersonneVoitureService getPersonneVoitureService() {
        return PersonneVoitureServiceImpl.getInstance();
    }

}
