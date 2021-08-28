package ru.hiddenproject.feelmeserver.service;

import ru.hiddenproject.feelmeserver.enums.InvitationStatus;
import ru.hiddenproject.feelmeserver.exception.DataExistsException;
import ru.hiddenproject.feelmeserver.exception.DataNotExistsException;
import ru.hiddenproject.feelmeserver.model.AcceptedUser;
import ru.hiddenproject.feelmeserver.model.User;

import java.util.List;

/**
 * Interface for operations with {@link ru.hiddenproject.feelmeserver.model.AcceptedUser}
 */
public interface AcceptedUserService {

    /**
     * Creates invitation from {@code originalUser} to {@code acceptedUser}
     * @param originalUser Original user's data. (Who invites)
     * @param acceptedUser User's data to invite (Whom invited)
     * @return Invitation data
     * @throws DataExistsException if invitation for {@code acceptedUser} already exists
     */
    AcceptedUser inviteUser(User originalUser, User acceptedUser) throws DataExistsException;

    /**
     * Accepts invitation with {@code id}
     * @param id Invitation id to accept
     * @return Accepted invitation data
     * @throws DataExistsException If invitation is not in {@link ru.hiddenproject.feelmeserver.enums.InvitationStatus#PENDING} status
     * @throws DataNotExistsException If invitation with given {@code id} doesn't exists
     */
    AcceptedUser acceptInvitation(Long id) throws DataExistsException, DataNotExistsException;

    /**
     * Returns all invitations with {@link ru.hiddenproject.feelmeserver.enums.InvitationStatus#PENDING} status for user with {@code id}
     * @param id User id
     * @return List of invitations
     */
    List<AcceptedUser> getAllPendingInvitations(Long id);

    /**
     * Changes {@code invitation} status to {@code status}
     * @param invitation Invitation to change status
     * @param status New status of invitation {@link ru.hiddenproject.feelmeserver.enums.InvitationStatus}
     * @return Updated invitation data
     * @throws DataNotExistsException If {@code invitation} doesn't exists
     */
    AcceptedUser setInvitationStatus(AcceptedUser invitation, InvitationStatus status) throws DataNotExistsException;

    /**
     * Searches for invitation with {@link ru.hiddenproject.feelmeserver.enums.InvitationStatus#PENDING} status
     * @param id Invitation id
     * @return Invitation data with given {@code id}
     * @throws DataNotExistsException If invitation with given {@code id} doesn't exists
     * @throws DataExistsException If invitation with given {@code id} is not in {@link ru.hiddenproject.feelmeserver.enums.InvitationStatus#PENDING} status
     */
    AcceptedUser getAcceptedUser(Long id) throws DataNotExistsException, DataExistsException;

}
