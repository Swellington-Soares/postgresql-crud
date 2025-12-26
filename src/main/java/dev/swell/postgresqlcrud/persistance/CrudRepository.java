package dev.swell.postgresqlcrud.persistance;

import java.util.List;

public interface CrudRepository<T> {
   T save(T entity);
   void update(T entity);
   void deleteById(Long id);
   T findById(Long id);
   List<T> findAll();
}
