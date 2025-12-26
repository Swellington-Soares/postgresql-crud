package dev.swell.postgresqlcrud.persistance.aluno;

import dev.swell.postgresqlcrud.domain.aluno.AlunoEntity;
import dev.swell.postgresqlcrud.persistance.CrudRepository;
import dev.swell.postgresqlcrud.persistance.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class AlunoRepository implements CrudRepository<AlunoEntity> {

    @Override
    public AlunoEntity save(AlunoEntity entity) {
        try (EntityManager em = DBConnection.getInstance()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
        }
        return entity;
    }

    @Override
    public void update(AlunoEntity entity) {
        //TODO: NOT IMPLEMENTED
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = DBConnection.getInstance();
        try (em) {
           AlunoEntity aluno =  em.getReference(AlunoEntity.class, id);
           if (aluno != null) {
               EntityTransaction tx = em.getTransaction();
               tx.begin();
               em.remove(aluno);
               tx.commit();
           }
        }
    }

    @Override
    public AlunoEntity findById(Long id) {
       try (EntityManager em = DBConnection.getInstance()) {
           return em.find(AlunoEntity.class, id);
       }
    }

    @Override
    public List<AlunoEntity> findAll() {
        return DBConnection.getInstance()
                .createQuery("SELECT a from AlunoEntity a", AlunoEntity.class)
                .getResultList();
    }
}
