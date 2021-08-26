package ru.hiddenproject.feelmeserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hiddenproject.feelmeserver.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByToken(String token);
    Optional<User> findByCode(String code);

}
