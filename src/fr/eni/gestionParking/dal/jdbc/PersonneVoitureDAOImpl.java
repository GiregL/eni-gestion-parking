package fr.eni.gestionParking.dal.jdbc;

import fr.eni.gestionParking.bll.services.PersonneVoitureServiceImpl;
import fr.eni.gestionParking.bo.Pair;
import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;
import fr.eni.gestionParking.dal.exceptions.ConnectionException;
import fr.eni.gestionParking.dal.exceptions.PersonneVoitureDAOException;
import fr.eni.gestionParking.dal.expose.PersonneVoitureDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PersonneVoitureDAOImpl implements PersonneVoitureDAO {

    private static final Logger LOGGER = Logger.getLogger(PersonneVoitureDAOImpl.class.getSimpleName());

    private static final String FULL_JOIN = "SELECT * FROM Personne FULL JOIN Voiture ON Voiture.utilisateur = Personne.id";

    @Override
    public List<Pair<Personne, Voiture>> fullJoin() throws PersonneVoitureDAOException {
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(FULL_JOIN)) {
            List<Pair<Personne, Voiture>> result = new ArrayList<>();

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Integer personneId = resultSet.getObject(1, Integer.class);
                String personneNom = resultSet.getString(2);
                String personnePrenom = resultSet.getString(3);

                Integer voitureId = resultSet.getObject(4, Integer.class);
                String voitureNom = resultSet.getString(5);
                String voiturePlaque = resultSet.getString(6);
                Integer voitureUtilisateurId = resultSet.getObject(7, Integer.class);

                Personne personne = null;
                Voiture voiture = null;

                if (personneId != null && personneNom != null && personnePrenom != null) {
                    personne = new Personne(personneId, personneNom, personnePrenom);
                }

                if (voitureId != null && voitureNom != null && voiturePlaque != null) {
                    voiture = new Voiture(voitureId, voitureNom, voiturePlaque, null);
                }

                if (voiture != null && voitureUtilisateurId != null && personne != null && voitureUtilisateurId.equals(personne.getId())) {
                    voiture.setPersonne(personne);
                }

                if (personne == null && voiture == null) {
                    LOGGER.warning("[fullJoin] ");
                }
                result.add(new Pair<>(personne, voiture));
            }

            return result;
        } catch (SQLException | ConnectionException e) {
            LOGGER.severe("[fullJoin] Error while creating connection and statement : " + e.getMessage());
            throw new PersonneVoitureDAOException(e.getMessage());
        }
    }
}
