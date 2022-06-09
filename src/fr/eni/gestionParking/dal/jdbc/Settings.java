package fr.eni.gestionParking.dal.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {

    private static Properties properties;

    static {
        try (InputStream input = Settings.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
