package ru.hiddenproject.feelmeserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hiddenproject.feelmeserver.model.User;

import java.util.Optional;

/**
 * Repository for operations with {@link ru.hiddenproject.feelmeserver.model.User} model
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Searches for {@link ru.hiddenproject.feelmeserver.model.User} by {@code token}
     * @param token User token
     * @return Optional user data
     */
    Optional<User> findByToken(String token);

    /**
     * Searches for {@link ru.hiddenproject.feelmeserver.model.User} by {@code code}
     * @param code User code
     * @return Optional user data
     */
    Optional<User> findByCode(String code);

    /**
     * Searches for {@link ru.hiddenproject.feelmeserver.model.User} by {@code login}
     * @param login User login
     * @return Optional user data
     */
    Optional<User> findByLogin(String login);

    /**
     * Searches for {@link ru.hiddenproject.feelmeserver.model.User} by {@code uid}
     * @param uid User's device UID
     * @return Optional user data
     */
    Optional<User> findByDeviceUID(String uid);

    /**
     * Searches for {@link ru.hiddenproject.feelmeserver.model.User} by {@code login} and {@code deviceUID}
     * @param login User token
     * @param deviceUID User's device UID
     * @return Optional user data
     */
    Optional<User> findByLoginAndDeviceUID(String login, String deviceUID);

}
