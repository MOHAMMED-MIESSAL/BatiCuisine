package config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class dbConnection {

    // L'instance unique de la classe
    private static dbConnection instance;
    private Connection connection;

    // URL de connexion à la base de données PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/batiCuisine";
    private static final String USER = "batiCuisine";
    private static final String PASSWORD = "";


    // Constructeur privé pour empêcher l'instanciation directe
    private dbConnection() throws SQLException {
        try {
            // Chargement du driver JDBC
            Class.forName("org.postgresql.Driver");
            // Initialisation de la connexion
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver non trouvé", e);
        }
    }

    // Méthode pour obtenir l'instance unique
    public static dbConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new dbConnection();
        }
        return instance;
    }

    // Méthode pour obtenir la connexion
    public Connection getConnection() {
        return connection;
    }

}
