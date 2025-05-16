package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, UUID> {

    // Spring Data JPA creerà automaticamente l'implementazione per questo metodo
    // basandosi sul nome. Cercherà un UserEntity per il suo campo 'username'.
    Optional<UserEntity> findByUsername(String username);

    // Non è necessario dichiarare metodi come save, findById, findAll, deleteById, etc.,
    // perché sono già forniti da JpaRepository<UserEntity, UUID>.

    // Se avevi una logica specifica nel tuo vecchio metodo `deleteByUUID` che restituiva l'entità,
    // quella logica ora dovrebbe risiedere in un Service layer, come discusso in precedenza.
    // Il repository si occupa delle operazioni dirette con il database.
    // Esempio:
    // In UserService:
    // UserEntity user = userRepository.findById(uuid).orElse(null);
    // if (user != null) {
    //     userRepository.deleteById(uuid);
    //     return user;
    // }
    // return null;
}