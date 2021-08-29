package ru.hiddenproject.feelmeserver.service;

import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.exception.DataExistsException;
import ru.hiddenproject.feelmeserver.exception.DataNotExistsException;
import ru.hiddenproject.feelmeserver.exception.DataValidityException;
import ru.hiddenproject.feelmeserver.exception.InternalException;
import ru.hiddenproject.feelmeserver.model.User;

/**
 * Interface for operations with {@link ru.hiddenproject.feelmeserver.model.User}
 */
public interface UserService {

    /**
     * Saves user data to database
     * @param user User data
     * @return Saved user data
     * @throws DataValidityException if user data is not valid
     */
    User save(User user) throws DataValidityException;

    /**
     * Searches user by id
     * @param id User id to find
     * @return User data or null
     * @throws DataNotExistsException if user doesn't exists
     */
    User findById(Long id) throws DataNotExistsException;

    /**
     * Searches user by token
     * @param token User token to find
     * @return User data or null
     * @throws DataNotExistsException if user doesn't exists
     */
    User findByToken(String token) throws DataNotExistsException;

    /**
     * Searches user by invite code
     * @param code User code to find
     * @return User data or null
     * @throws DataNotExistsException if user doesn't exists
     */
    User findByCode(String code) throws DataNotExistsException;

    /**
     * Updates user token
     * @param user User to update
     * @return New user token
     */
    String updateToken(User user);

    /**
     * Creates db user from dto
     * @param baseUserDto New user's data
     * @return User to save in db
     * @throws DataExistsException if user already exists
     * @throws DataValidityException if user data is not valid
     * @throws InternalException if internal exception occurred
     */
    User createUser(BaseUserDto baseUserDto) throws DataValidityException,
            InternalException, DataExistsException;

}
