package fr.eni.gestionParking.bll.expose;

import fr.eni.gestionParking.bll.services.PersonneServiceImpl;
import fr.eni.gestionParking.bll.services.VoitureServiceImpl;

public class ServiceFactory {

    public static PersonneService getPersonneService() {
        return PersonneServiceImpl.getInstance();
    }

    public static VoitureService getVoitureService() {
        return VoitureServiceImpl.getInstance();
    }

}
