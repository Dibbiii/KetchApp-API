package com.alessandra_alessandro.ketchapp.repositories;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia generica per le operazioni CRUD di base.
 * @param <T> Il tipo dell'entità
 * @param <ID> Il tipo dell'identificatore dell'entità (es. Integer, Long, UUID)
 */
public interface GenericRepository<T, ID> {

    /**
     * Creates and persists a new entity.
     *
     * @param entity the entity instance to create and persist
     * @return the created entity instance
     */
    T create(T entity);

    /**
     * Updates and persists changes to an existing entity.
     *
     * @param entity the entity instance with updated information
     * @return the updated entity instance
     */
    T update(T entity);


    /**
     * Deletes the provided entity from the data source.
     *
     * @param entity the entity instance to be deleted
     * @return the deleted entity instance
     */
    T delete(T entity);

    /**
     * Finds an entity by its unique identifier.
     *
     * @param id the unique identifier of the entity to find
     * @return an {@code Optional} containing the entity if found, or an empty {@code Optional} if not found
     */
    Optional<T> selectById(ID id);


    /**
     * Retrieves all entities of type T from the data source.
     *
     * @return a list containing all entities of type T
     */
    List<T> selectAll();
}