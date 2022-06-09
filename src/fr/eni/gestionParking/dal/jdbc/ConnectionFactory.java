package fr.eni.gestionParking.dal.jdbc;

import fr.eni.gestionParking.dal.exceptions.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws ConnectionException {
        try {
            return DriverManager.getConnection(
                    Settings.getProperty("URI"),
                    Settings.getProperty("USER"),
                    Settings.getProperty("PASSWORD")
            );
        } catch (SQLException e) {
            throw new ConnectionException("DAL - " + e.getMessage());
        }
    }

}
