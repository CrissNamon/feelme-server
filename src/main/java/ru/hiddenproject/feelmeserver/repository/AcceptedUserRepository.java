package ru.hiddenproject.feelmeserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hiddenproject.feelmeserver.enums.InvitationStatus;
import ru.hiddenproject.feelmeserver.model.AcceptedUser;

import java.util.List;
import java.util.Optional;

/**
 * Repository for operations with {@link ru.hiddenproject.feelmeserver.model.AcceptedUser} model
 */
@Repository
public interface AcceptedUserRepository extends JpaRepository<AcceptedUser, Long> {

    /**
     * Searches for {@link ru.hiddenproject.feelmeserver.model.AcceptedUser} by {@code originalUserId}
     * @param originalUserId Original user id (Who invites)
     * @return Optional invitation data
     */
    Optional<AcceptedUser> findByOriginalUserId(Long originalUserId);

    /**
     * Searches for {@link ru.hiddenproject.feelmeserver.model.AcceptedUser} by {@code acceptedUserId}
     * @param acceptedUserId Accepted user id (Whom invited)
     * @return Optional invitation data
     */
    Optional<AcceptedUser> findByAcceptedUserId(Long acceptedUserId);

    /**
     * Searches for {@link ru.hiddenproject.feelmeserver.model.AcceptedUser} by {@code originalUserId} and {@code acceptedUserId}
     * @param originalUserId Original user id (Who invites)
     * @param acceptedUserId Accepted user id (Whom invited)
     * @return Optional invitation data
     */
    Optional<AcceptedUser> findByOriginalUserIdAndAcceptedUserId(Long originalUserId, Long acceptedUserId);

    /**
     * Searches for all {@link ru.hiddenproject.feelmeserver.model.AcceptedUser} with {@code invitationStatus} status
     * @param originalUserId Original user id (Who invites)
     * @param invitationStatus Invitation status to search
     * @return List of invitations data
     */
    List<AcceptedUser> findByOriginalUserIdAndInvitationStatus(Long originalUserId, InvitationStatus invitationStatus);

}
