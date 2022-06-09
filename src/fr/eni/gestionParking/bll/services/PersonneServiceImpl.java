package fr.eni.gestionParking.bll.services;

import fr.eni.gestionParking.bll.exceptions.BLLException;
import fr.eni.gestionParking.bll.exceptions.InvalidPersonneException;
import fr.eni.gestionParking.bll.expose.PersonneService;
import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneAlreadyExistsException;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneDAOException;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneIdNullException;
import fr.eni.gestionParking.dal.exceptions.voiture.VoitureDAOException;
import fr.eni.gestionParking.dal.expose.DAOFactory;
import fr.eni.gestionParking.dal.expose.PersonneDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonneServiceImpl implements PersonneService {

    private static volatile PersonneServiceImpl instance;

    public static PersonneServiceImpl getInstance() {
        if (instance == null) {
            instance = new PersonneServiceImpl();
        }
        return instance;
    }

    private PersonneServiceImpl() {}

    /*
    --------------------------------------------------------------------------------------------------------------------
     */

    public List<Personne> getAllPersonnes() throws BLLException {
        try {
            return DAOFactory.getPersonneDAO().getAll();
        } catch (PersonneDAOException e) {
            throw new BLLException("BLL - " + e);
        }
    }

    @Override
    public Optional<Personne> getPersonne(int index) throws BLLException {
        try {
            return DAOFactory.getPersonneDAO().getById(index);
        } catch (PersonneDAOException e) {
            throw new BLLException("BLL - " + e);
        }
    }

    @Override
    public List<Voiture> getPersonneVoiture(Personne personne) throws BLLException {
        try {
            return DAOFactory.getVoitureDAO().getCarsOf(personne);
        } catch (PersonneIdNullException | VoitureDAOException e) {
            throw new BLLException("BLL - " + e.getMessage());
        }
    }

    @Override
    public boolean removePersonne(Personne personne) throws BLLException {
        try {
            return DAOFactory.getPersonneDAO().delete(personne);
        } catch (PersonneDAOException | PersonneIdNullException e) {
            throw new BLLException("BLL - " + e.getMessage());
        }
    }

    @Override
    public boolean insertPersonne(Personne personne) throws BLLException {
        if (this.validatePersonne(personne)) {
            try {
                return DAOFactory.getPersonneDAO().insert(personne);
            } catch (PersonneAlreadyExistsException | PersonneDAOException e) {
                throw new BLLException("BLL - " + e.getMessage());
            }
        } else throw new InvalidPersonneException("BLL - Invalid personne");
    }

    @Override
    public boolean update(Personne personne) throws BLLException {
        if (this.validatePersonne(personne)) {
            try {
                return DAOFactory.getPersonneDAO().update(personne);
            } catch (PersonneDAOException | PersonneIdNullException e) {
                throw new BLLException("BLL - " + e.getMessage());
            }
        } else throw new InvalidPersonneException("BLL - Invalid personne");
    }

    @Override
    public boolean validatePersonne(Personne personne) {
        return personne != null && validatePrenom(personne.getPrenom()) && validateNom(personne.getNom());
    }

    @Override
    public boolean validatePrenom(String prenom) {
        return prenom != null && prenom.length() > 0 && prenom.length() < 256;
    }

    @Override
    public boolean validateNom(String nom) {
        return nom != null && nom.length() > 0 && nom.length() < 256;
    }
}
