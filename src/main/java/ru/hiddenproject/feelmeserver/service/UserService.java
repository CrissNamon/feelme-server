package ru.hiddenproject.feelmeserver.service;

import ru.hiddenproject.feelmeserver.model.User;

public interface UserService {

    /**
     * Saves user data to database
     * @param user User data
     * @return true if data has been saved successfully
     */
    boolean save(User user);

    /**
     * @param id User id to find
     * @return User data or null
     */
    User findById(Long id);

    /**
     * @param token User token to find
     * @return User data or null
     */
    User findByToken(String token);

    /**
     * @param code User code to find
     * @return User data or null
     */
    User findByCode(String code);

    String updateToken(User user);

}
