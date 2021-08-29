package ru.hiddenproject.feelmeserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hiddenproject.feelmeserver.enums.InvitationStatus;
import ru.hiddenproject.feelmeserver.model.Invitation;

import java.util.List;
import java.util.Optional;

/**
 * Repository for operations with {@link Invitation} model
 */
@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    /**
     * Searches for {@link Invitation} by {@code originalUserId}
     * @param originalUserId Original user id (Who invites)
     * @return Optional invitation data
     */
    Optional<Invitation> findByOriginalUserId(Long originalUserId);

    /**
     * Searches for {@link Invitation} by {@code acceptedUserId}
     * @param acceptedUserId Accepted user id (Whom invited)
     * @return Optional invitation data
     */
    Optional<Invitation> findByAcceptedUserId(Long acceptedUserId);

    /**
     * Searches for {@link Invitation} by {@code originalUserId} and {@code acceptedUserId}
     * @param originalUserId Original user id (Who invites)
     * @param acceptedUserId Accepted user id (Whom invited)
     * @return Optional invitation data
     */
    Optional<Invitation> findByOriginalUserIdAndAcceptedUserId(Long originalUserId, Long acceptedUserId);

    /**
     * Searches for all {@link Invitation} with {@code invitationStatus} status
     * @param originalUserId Original user id (Who invites)
     * @param invitationStatus Invitation status to search
     * @return List of invitations data
     */
    List<Invitation> findByOriginalUserIdAndInvitationStatus(Long originalUserId, InvitationStatus invitationStatus);

}
