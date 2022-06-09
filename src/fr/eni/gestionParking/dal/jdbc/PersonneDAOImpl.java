package fr.eni.gestionParking.dal.jdbc;

import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.dal.exceptions.ConnectionException;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneAlreadyExistsException;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneDAOException;
import fr.eni.gestionParking.dal.exceptions.personne.PersonneIdNullException;
import fr.eni.gestionParking.dal.expose.PersonneDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonneDAOImpl implements PersonneDAO {

    private static final Logger LOGGER = Logger.getLogger(PersonneDAOImpl.class.getSimpleName());

    private static final String GET_BY_ID = "SELECT * FROM Personne WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM Personne";
    private static final String UPDATE = "UPDATE Personne SET nom = ?, prenom = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Personne WHERE id = ?";
    private static final String INSERT = "INSERT INTO Personne (nom, prenom) VALUES (?, ?)";

    private Personne ofResultSet(ResultSet resultSet) throws SQLException {
        return new Personne(
                resultSet.getInt("id"),
                resultSet.getString("nom"),
                resultSet.getString("prenom"));
    }

    @Override
    public Optional<Personne> getById(Integer id) throws PersonneDAOException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_ID)) {

            if (id == null) {
                LOGGER.log(Level.WARNING, "[getByID] - given parameter id should not be null");
                throw new PersonneDAOException("ID cannot be null");
            }

            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return Optional.of(ofResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (ConnectionException | SQLException e) {
            LOGGER.log(Level.SEVERE, "[getByID] - error in request : " + e.getMessage());
            throw new PersonneDAOException("DAL - " + e.getMessage());
        }
    }

    @Override
    public List<Personne> getAll() throws PersonneDAOException {
        List<Personne> result = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(GET_ALL)) {

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                result.add(ofResultSet(resultSet));
            }
        } catch (ConnectionException | SQLException e) {
            LOGGER.log(Level.SEVERE, "[getAll] - error in request : " + e.getMessage());
            throw new PersonneDAOException("DAL - " + e.getMessage());
        }

        return result;
    }

    @Override
    public boolean update(Personne personne) throws PersonneDAOException, PersonneIdNullException {
        if (personne.getId() == null) {
            LOGGER.log(Level.WARNING, "[update] - given parameter personne's id should not be null");
            throw new PersonneIdNullException("Personne id cannot be null");
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, personne.getNom());
            stmt.setString(2, personne.getPrenom());
            stmt.setInt(3, personne.getId());

            return stmt.executeUpdate() == 1;
        } catch (ConnectionException | SQLException e) {
            LOGGER.log(Level.SEVERE, "[update] - error in request : " + e.getMessage());
            throw new PersonneDAOException("DAL - " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Personne personne) throws PersonneDAOException, PersonneIdNullException {
        if (personne.getId() == null) {
            LOGGER.log(Level.WARNING, "[delete] - given parameter personne's id should not be null");
            throw new PersonneIdNullException("Personne id cannot be null");
        }

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, personne.getId());
            return stmt.executeUpdate() == 1;
        } catch (ConnectionException | SQLException e) {
            LOGGER.log(Level.SEVERE, "[delete] - error in request : " + e.getMessage());
            throw new PersonneDAOException("DAL - " + e.getMessage());
        }
    }

    @Override
    public boolean insert(Personne personne) throws PersonneAlreadyExistsException, PersonneDAOException {
        if (personne.getId() != null) {
            LOGGER.log(Level.WARNING, "[insert] - given parameter personne's id is already set");
            throw new PersonneAlreadyExistsException("DAL - The given Personne already have an ID");
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, personne.getNom());
            stmt.setString(2, personne.getPrenom());

            int affected = stmt.executeUpdate();
            ResultSet returnedId = stmt.getGeneratedKeys();

            if (returnedId.next()) {
                personne.setId(returnedId.getInt(1));
            }

            return affected == 1;
        } catch (ConnectionException | SQLException e) {
            LOGGER.log(Level.SEVERE, "[insert] - error in request : " + e.getMessage());
            throw new PersonneDAOException("DAL - " + e.getMessage());
        }
    }
}
