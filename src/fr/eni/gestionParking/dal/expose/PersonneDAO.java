package fr.eni.gestionParking.dal.expose;

import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneAlreadyExistsException;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneDAOException;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneIdNullException;

import java.util.List;
import java.util.Optional;

/**
 * DAO d'une Personne
 */
public interface PersonneDAO {

    /**
     * Récupérer un utilisateur par son ID
     * @param id Id de l'utilisateur
     * @return Un optional de la personne si elle est trouvée ou non
     * @throws PersonneDAOException en cas d'erreur en base
     */
    Optional<Personne> getById(final Integer id) throws PersonneDAOException;

    /**
     * Récupérer toutes les personnes de la base
     * @return Liste des Personnes en base
     * @throws PersonneDAOException en cas d'erreur lors de la requête
     */
    List<Personne> getAll() throws PersonneDAOException;

    /**
     * Met à jour une personne en base en fonction de son id
     * @param personne Personne à modifier, suivant son ID
     * @return True si l'utilisateur est bien modifié, False sinon
     * @throws PersonneIdNullException Si l'utilisateur à un ID à Null
     * @throws PersonneDAOException En cas d'erreur lors de la requête
     */
    boolean update(final Personne personne) throws PersonneDAOException, PersonneIdNullException;

    /**
     * Supprimes la personne en base, en se basant sur son ID
     * @param personne Personne à supprimer
     * @return True si l'utilisateur est supprimé, False sinon
     * @throws PersonneIdNullException Si l'utilisateur a un ID a Null
     * @throws PersonneDAOException En cas d'erreur de la requête
     */
    boolean delete(final Personne personne) throws PersonneDAOException, PersonneIdNullException;

    /**
     * Créé la personne en base et affecte son ID
     * Ne créé aucune ligne en base si l'utilisateur existe déjà
     * @param personne Personne à créer en base
     * @return True si l'utilisateur est créé, False sinon
     * @throws PersonneDAOException En cas d'erreur de requête
     */
    boolean insert(Personne personne) throws PersonneAlreadyExistsException, PersonneDAOException;
}
