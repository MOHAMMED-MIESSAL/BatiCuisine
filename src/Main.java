import config.dbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Obtenir l'instance de connexion
            Connection connection = dbConnection.getInstance().getConnection();

            System.out.println("Connexion réussie !");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}