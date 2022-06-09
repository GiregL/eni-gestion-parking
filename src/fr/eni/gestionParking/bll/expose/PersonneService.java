package fr.eni.gestionParking.bll.expose;

import fr.eni.gestionParking.bll.exceptions.BLLException;
import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;

import java.util.List;
import java.util.Optional;

public interface PersonneService {

    /**
     * Retrieves all the Personne of the application
     * @return Personne list
     */
    List<Personne> getAllPersonnes() throws BLLException;

    /**
     * Retrieve a Personne from the Personne list, given its id
     * @param index Personne id
     * @return Personne if exists, else Empty
     */
    Optional<Personne> getPersonne(int index) throws BLLException;

    /**
     * Retrieves the personne's cars
     * @param personne Personne
     * @return List of cars
     */
    List<Voiture> getPersonneVoiture(Personne personne) throws BLLException;

    /**
     *
     * @param personne
     * @return
     * @throws BLLException
     */
    boolean removePersonne(Personne personne) throws BLLException;

    boolean insertPersonne(Personne personne) throws BLLException;

    boolean update(Personne personne) throws BLLException;

    boolean validatePersonne(Personne personne);

    boolean validatePrenom(String prenom);

    boolean validateNom(String nom);
}