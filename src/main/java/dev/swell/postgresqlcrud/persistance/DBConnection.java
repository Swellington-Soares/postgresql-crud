package dev.swell.postgresqlcrud.persistance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBConnection {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("postgresqlPU");

    private DBConnection() {}

    public static EntityManager getInstance() {
        return emf.createEntityManager();
    }

    public static void closeFactory() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
