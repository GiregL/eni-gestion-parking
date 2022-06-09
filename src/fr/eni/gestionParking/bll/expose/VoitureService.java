package fr.eni.gestionParking.bll.expose;

import fr.eni.gestionParking.bll.exceptions.BLLException;
import fr.eni.gestionParking.bo.Voiture;

import java.util.List;
import java.util.Optional;

public interface VoitureService {

    Optional<Voiture> getVoitureById(int id) throws BLLException;

    List<Voiture> getAll() throws BLLException;

    boolean save(Voiture voiture) throws BLLException;

    boolean delete(Voiture voiture) throws BLLException;

    boolean update(Voiture voiture) throws BLLException;

    boolean validate(Voiture voiture) throws BLLException;

    boolean validateName(String nom);

    boolean validatePlaqueImmat(String plaque);
}
