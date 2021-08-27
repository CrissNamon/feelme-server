package ru.hiddenproject.feelmeserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hiddenproject.feelmeserver.enums.InvitationStatus;
import ru.hiddenproject.feelmeserver.model.AcceptedUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcceptedUserRepository extends JpaRepository<AcceptedUser, Long> {

    Optional<AcceptedUser> findByOriginalUserId(Long originalUserId);
    Optional<AcceptedUser> findByAcceptedUserId(Long acceptedUserId);
    Optional<AcceptedUser> findByOriginalUserIdAndAcceptedUserId(Long originalUserId, Long acceptedUserId);
    List<AcceptedUser> findByOriginalUserIdAndInvitationStatus(Long originalUserId, InvitationStatus invitationStatus);

}
