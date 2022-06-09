package fr.eni.gestionParking.bll.services;

import fr.eni.gestionParking.bll.exceptions.BLLException;
import fr.eni.gestionParking.bll.exceptions.InvalidVoitureException;
import fr.eni.gestionParking.bll.expose.ServiceFactory;
import fr.eni.gestionParking.bll.expose.VoitureService;
import fr.eni.gestionParking.bo.Voiture;
import fr.eni.gestionParking.dal.exceptions.voiture.VoitureAlreadyExistsException;
import fr.eni.gestionParking.dal.exceptions.voiture.VoitureDAOException;
import fr.eni.gestionParking.dal.exceptions.voiture.VoitureIdNullException;
import fr.eni.gestionParking.dal.expose.DAOFactory;

import java.util.List;
import java.util.Optional;

public class VoitureServiceImpl implements VoitureService {

    private static VoitureServiceImpl instance;

    public static VoitureServiceImpl getInstance() {
        if (instance == null)
            instance = new VoitureServiceImpl();
        return instance;
    }

    private VoitureServiceImpl() {}

    /*
    --------------------------------------------------------------------------------------------------------------------
     */

    @Override
    public Optional<Voiture> getVoitureById(int id) throws BLLException {
        try {
            return DAOFactory.getVoitureDAO().getById(id);
        } catch (VoitureDAOException e) {
            throw new BLLException("BLL - " + e.getMessage());
        }
    }

    @Override
    public List<Voiture> getAll() throws BLLException {
        try {
            return DAOFactory.getVoitureDAO().getAll();
        } catch (VoitureDAOException e) {
            throw new BLLException("BLL - " + e.getMessage());
        }
    }

    @Override
    public boolean save(Voiture voiture) throws BLLException {
        if (validate(voiture)) {
            try {
                return DAOFactory.getVoitureDAO().insert(voiture);
            } catch (VoitureAlreadyExistsException | VoitureDAOException e) {
                throw new BLLException("BLL - " + e.getMessage());
            }
        } else throw new InvalidVoitureException("BLL - Invalid voiture instance\n" + voiture.toString());
    }

    @Override
    public boolean delete(Voiture voiture) throws BLLException {
        try {
            return DAOFactory.getVoitureDAO().delete(voiture);
        } catch (VoitureDAOException | VoitureIdNullException e) {
            throw new BLLException("BLL - " + e.getMessage());
        }
    }

    @Override
    public boolean update(Voiture voiture) throws BLLException {
        if (validate(voiture)) {
            try {
                return DAOFactory.getVoitureDAO().update(voiture);
            } catch (VoitureDAOException | VoitureIdNullException e) {
                throw new BLLException("BLL - " + e.getMessage());
            }
        } else throw new InvalidVoitureException("BLL - Invalid voiture instance\n" + voiture.toString());
    }

    @Override
    public boolean validate(Voiture voiture) throws BLLException {
        if (voiture == null) return false;
        return voiture.getNom() != null
                && voiture.getNom().length() > 0
                && voiture.getNom().length() < 256
                && voiture.getPlaqueImmatriculation() != null
                && validatePlaqueImmat(voiture.getPlaqueImmatriculation())
                // Validate personne if linked
                && (!voiture.isLinked() || ServiceFactory.getPersonneService().validatePersonne(voiture.getPersonne()));
    }

    @Override
    public boolean validateName(String nom) {
        return nom != null
                && nom.length() > 0
                && nom.length() < 255;
    }

    @Override
    public boolean validatePlaqueImmat(String plaque) {
        return plaque.matches("^[A-Z]{2}[-][0-9]{3}[-][A-Z]{2}$");
    }
}
