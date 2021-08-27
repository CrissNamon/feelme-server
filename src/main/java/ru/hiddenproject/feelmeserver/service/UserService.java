package ru.hiddenproject.feelmeserver.service;

import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.exception.DataExistsException;
import ru.hiddenproject.feelmeserver.exception.DataNotExistsException;
import ru.hiddenproject.feelmeserver.exception.DataValidityException;
import ru.hiddenproject.feelmeserver.exception.InternalException;
import ru.hiddenproject.feelmeserver.model.AcceptedUser;
import ru.hiddenproject.feelmeserver.model.User;

import java.util.List;

public interface UserService {

    /**
     * Saves user data to database
     * @param user User data
     * @return Saved user data
     */
    User save(User user) throws DataValidityException;

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
     */
    User createUser(BaseUserDto baseUserDto) throws DataValidityException,
            InternalException, DataExistsException;

    AcceptedUser inviteUser(User originalUser, User acceptedUser) throws DataExistsException;

    void rejectInvitation(Long id) throws DataExistsException, DataNotExistsException;

    AcceptedUser acceptInvitation(Long id) throws DataExistsException, DataNotExistsException;

    List<AcceptedUser> getAllPendingInvitations(Long id);

}
