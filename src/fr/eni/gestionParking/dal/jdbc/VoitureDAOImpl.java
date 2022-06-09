package fr.eni.gestionParking.dal.jdbc;

import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;
import fr.eni.gestionParking.dal.exceptions.ConnectionException;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneDAOException;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneIdNullException;
import fr.eni.gestionParking.dal.exceptions.voiture.VoitureAlreadyExistsException;
import fr.eni.gestionParking.dal.exceptions.voiture.VoitureDAOException;
import fr.eni.gestionParking.dal.exceptions.voiture.VoitureIdNullException;
import fr.eni.gestionParking.dal.expose.DAOFactory;
import fr.eni.gestionParking.dal.expose.PersonneDAO;
import fr.eni.gestionParking.dal.expose.VoitureDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VoitureDAOImpl implements VoitureDAO {

    private static final String GET_BY_ID = "SELECT * FROM Voiture WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM Voiture";
    private static final String UPDATE = "UPDATE Voiture SET nom = ?, plaque_immat = ?, utilisateur = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Voiture WHERE id = ?";
    private static final String INSERT = "INSERT INTO Voiture (nom, plaque_immat, utilisateur) VALUES (?, ?, ?)";
    private static final String LINK = "UPDATE Voiture SET utilisateur = ? WHERE id = ?";
    private static final String UNLINK = "UPDATE Voiture SET utilisateur = null WHERE id = ?";
    private static final String IS_LINKED = "SELECT COUNT(*) FROM Voiture WHERE id = ? AND utilisateur /= null";

    private static final String CARS_OF = "SELECT * FROM Voiture WHERE utilisateur = ?";

    private Voiture ofResultSet(ResultSet resultSet) throws SQLException, PersonneDAOException {
        Voiture voiture = new Voiture();
        voiture.setId(resultSet.getInt("id"));
        voiture.setNom(resultSet.getString("nom"));
        voiture.setPlaqueImmatriculation(resultSet.getString("plaque_immat"));

        Integer personneID = resultSet.getObject("utilisateur", Integer.class);
        if (personneID != null) {
            DAOFactory.getPersonneDAO().getById(personneID).ifPresent(voiture::setPersonne);
        }

        return voiture;
    }

    @Override
    public Optional<Voiture> getById(Integer id) throws VoitureDAOException {
        if (id == null)
            throw new VoitureDAOException("ID cannot be null");

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(GET_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Voiture voiture = ofResultSet(resultSet);
                return Optional.of(voiture);
            } else {
                return Optional.empty();
            }

        } catch (ConnectionException | SQLException | PersonneDAOException e) {
            throw new VoitureDAOException("DAL - " + e.getMessage());
        }
    }

    @Override
    public List<Voiture> getAll() throws VoitureDAOException {
        List<Voiture> voitures = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL)) {
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                voitures.add(ofResultSet(resultSet));
            }
        } catch (ConnectionException | SQLException | PersonneDAOException e) {
            throw new VoitureDAOException("DAL - " + e.getMessage());
        }

        return voitures;
    }

    @Override
    public boolean update(Voiture voiture) throws VoitureDAOException, VoitureIdNullException {
        if (voiture.getId() == null) {
            throw new VoitureIdNullException("ID cannot be null");
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, voiture.getNom());
            stmt.setString(2, voiture.getPlaqueImmatriculation());
            if (voiture.isLinked()) {
                stmt.setInt(3, voiture.getPersonne().getId());
            } else {
                stmt.setNull(3, Types.NUMERIC);
            }
            stmt.setInt(4, voiture.getId());

            return stmt.executeUpdate() == 1;
        } catch (ConnectionException | SQLException e) {
            throw new VoitureDAOException("DAL - " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Voiture voiture) throws VoitureDAOException, VoitureIdNullException {
        if (voiture.getId() == null) {
            throw new VoitureIdNullException("ID cannot be null");
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, voiture.getId());

            return stmt.executeUpdate() == 1;
        } catch (ConnectionException | SQLException e) {
            throw new VoitureDAOException("DAL - " + e.getMessage());
        }
    }

    @Override
    public boolean insert(Voiture voiture) throws VoitureAlreadyExistsException, VoitureDAOException {
        if (voiture.getId() != null) {
            throw new VoitureAlreadyExistsException("ID cannot be null");
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, voiture.getNom());
            stmt.setString(2, voiture.getPlaqueImmatriculation());
            if (voiture.getPersonne() != null) {
                if (voiture.getPersonne().getId() == null) {
                    stmt.setNull(3, Types.NUMERIC);
                } else {
                    stmt.setInt(3, voiture.getPersonne().getId());
                }
            } else {
                stmt.setNull(3, Types.NUMERIC);
            }

            boolean result = stmt.executeUpdate() == 1;

            if (result) {
                ResultSet resultSet = stmt.getGeneratedKeys();
                if (resultSet.next()) {
                    voiture.setId(resultSet.getInt(1));
                }
            }
            return result;
        } catch (ConnectionException | SQLException e) {
            throw new VoitureDAOException("DAL - " + e.getMessage());
        }
    }

    @Override
    public boolean linkPersonne(Voiture voiture, Personne personne) throws PersonneIdNullException, VoitureIdNullException, VoitureDAOException {
        if (voiture.getId() == null)
            throw new VoitureIdNullException("DAL - Voiture id cannot be null");

        if (personne.getId() == null)
            throw new PersonneIdNullException("DAL - Personne id cannot be null");

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(LINK)) {
            stmt.setInt(1, personne.getId());
            stmt.setInt(2, voiture.getId());

            boolean result = stmt.executeUpdate() == 1;
            if (result) {
                voiture.setPersonne(personne);
            }

            return result;
        } catch (ConnectionException | SQLException e) {
            throw new VoitureDAOException("DAL - " + e.getMessage());
        }
    }

    @Override
    public boolean unlinkPersonne(Voiture voiture) throws VoitureIdNullException, VoitureDAOException {
        if (voiture.getId() == null)
            throw new VoitureIdNullException("DAL - Voiture id cannot be null");

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UNLINK)) {
            stmt.setInt(1, voiture.getId());

            boolean result =  stmt.executeUpdate() == 1;
            if (result) {
                voiture.setPersonne(null);
            }
            return result;
        } catch (ConnectionException | SQLException e) {
            throw new VoitureDAOException("DAL - " + e.getMessage());
        }
    }

    public boolean isLinked(Voiture voiture) throws VoitureIdNullException, PersonneIdNullException, VoitureDAOException {
        if (voiture.getId() == null)
            throw new VoitureIdNullException("DAL - Voiture id cannot be null");

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(IS_LINKED)) {
            stmt.setInt(1, voiture.getId());

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) != 0;
            }
            return false;
        } catch (ConnectionException | SQLException e) {
            throw new VoitureDAOException("DAL - " + e.getMessage());
        }
    }

    @Override
    public List<Voiture> getCarsOf(Personne personne) throws PersonneIdNullException, VoitureDAOException {
        if (personne == null || personne.getId() == null)
            throw new PersonneIdNullException();

        List<Voiture> voitures = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CARS_OF)) {
            stmt.setInt(1, personne.getId());

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                voitures.add(ofResultSet(resultSet));
            }
        } catch (ConnectionException | SQLException | PersonneDAOException e) {
            throw new VoitureDAOException("DAL - " + e.getMessage());
        }
        return voitures;
    }
}
