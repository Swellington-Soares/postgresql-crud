package dev.swell.postgresqlcrud;

import dev.swell.postgresqlcrud.persistance.DBConnection;
import jakarta.persistence.Query;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        int result = DBConnection.getInstance().createQuery("SELECT 1", Integer.class).getSingleResult();
        if (result == 1) {
            Application.launch(HelloApplication.class, args);
        }
    }
}
