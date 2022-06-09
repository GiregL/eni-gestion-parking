package fr.eni.gestionParking.tests;

import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneAlreadyExistsException;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneDAOException;
import fr.eni.gestionParking.dal.exceptions.voiture.VoitureAlreadyExistsException;
import fr.eni.gestionParking.dal.exceptions.voiture.VoitureDAOException;
import fr.eni.gestionParking.dal.expose.DAOFactory;
import fr.eni.gestionParking.dal.expose.PersonneDAO;
import fr.eni.gestionParking.dal.expose.VoitureDAO;

public class InsertVoituresTest {

    public static void main(String[] args) throws VoitureAlreadyExistsException, VoitureDAOException, PersonneAlreadyExistsException, PersonneDAOException {
        VoitureDAO dao = DAOFactory.getVoitureDAO();
        for (int i = 0; i < 15; i++) {
            Voiture voiture = new Voiture();
            voiture.setNom("Tweezy " + i);
            voiture.setPlaqueImmatriculation("BE-569-VN");
            voiture.setPersonne(null);
            dao.insert(voiture);
        }

        PersonneDAO personneDAO = DAOFactory.getPersonneDAO();
        Personne pers = new Personne();
        pers.setNom("Doe");
        pers.setPrenom("John");
        personneDAO.insert(pers);

        Voiture v = new Voiture();
        v.setPersonne(pers);
        v.setPlaqueImmatriculation("BE-569-VN");
        v.setNom("Meh");
        dao.insert(v);
    }

}
