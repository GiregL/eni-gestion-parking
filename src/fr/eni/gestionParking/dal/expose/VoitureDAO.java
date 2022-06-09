package fr.eni.gestionParking.dal.expose;

import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneIdNullException;
import fr.eni.gestionParking.dal.exceptions.voiture.VoitureAlreadyExistsException;
import fr.eni.gestionParking.dal.exceptions.voiture.VoitureDAOException;
import fr.eni.gestionParking.dal.exceptions.voiture.VoitureIdNullException;

import java.util.List;
import java.util.Optional;

public interface VoitureDAO {

    /**
     * Récupérer une voiture par son ID
     * @param id Id de la voiture
     * @return Un optional de la Voiture si elle est trouvée ou non
     * @throws VoitureDAOException en cas d'erreur en base
     */
    Optional<Voiture> getById(final Integer id) throws VoitureDAOException;

    /**
     * Récupérer toutes les Voitures de la base
     * @return Liste des Voitures en base
     * @throws VoitureDAOException en cas d'erreur lors de la requête
     */
    List<Voiture> getAll() throws VoitureDAOException;

    /**
     * Met à jour une Voiture en base en fonction de son id
     * @param Voiture Voiture à modifier, suivant son ID
     * @return True si la voiture est bien modifié, False sinon
     * @throws VoitureIdNullException Si la voiture à un ID à Null
     */
    boolean update(final Voiture Voiture) throws VoitureDAOException, VoitureIdNullException;

    /**
     * Supprimes la Voiture en base, en se basant sur son ID
     * @param Voiture Voiture à supprimer
     * @return True si la voiture est supprimé, False sinon
     * @throws VoitureIdNullException Si la voiture a un ID a Null
     */
    boolean delete(final Voiture Voiture) throws VoitureDAOException, VoitureIdNullException;

    /**
     * Créé la Voiture en base et affecte son ID
     * Ne créé aucune ligne en base si l'utilisateur existe déjà
     * @param Voiture Voiture à créer en base
     * @return True si la voiture est créé, False sinon
     * @throws VoitureDAOException En cas d'erreur de requête
     */
    boolean insert(Voiture Voiture) throws VoitureAlreadyExistsException, VoitureDAOException;

    boolean linkPersonne(Voiture voiture, Personne personne) throws PersonneIdNullException, VoitureIdNullException, VoitureDAOException;

    boolean unlinkPersonne(Voiture voiture) throws VoitureIdNullException, VoitureDAOException;

    boolean isLinked(Voiture voiture) throws VoitureIdNullException, PersonneIdNullException, VoitureDAOException;

    List<Voiture> getCarsOf(Personne personne) throws PersonneIdNullException, VoitureDAOException;
}
