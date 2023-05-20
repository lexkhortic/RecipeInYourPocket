package by.lexkhortic.recipeInPocket.repositories;

import by.lexkhortic.recipeInPocket.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    List<Owner> findByLoginAndPassword(String login, String password);
}
