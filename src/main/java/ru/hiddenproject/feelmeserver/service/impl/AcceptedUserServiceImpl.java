package ru.hiddenproject.feelmeserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hiddenproject.feelmeserver.enums.InvitationStatus;
import ru.hiddenproject.feelmeserver.exception.DataExistsException;
import ru.hiddenproject.feelmeserver.exception.DataNotExistsException;
import ru.hiddenproject.feelmeserver.model.AcceptedUser;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.repository.AcceptedUserRepository;
import ru.hiddenproject.feelmeserver.service.AcceptedUserService;

import java.util.List;

@Service
public class AcceptedUserServiceImpl implements AcceptedUserService {

    private final AcceptedUserRepository acceptedUserRepository;

    @Autowired
    public AcceptedUserServiceImpl(AcceptedUserRepository acceptedUserRepository) {
        this.acceptedUserRepository = acceptedUserRepository;
    }

    @Override
    public AcceptedUser inviteUser(User originalUser, User acceptedUser) throws DataExistsException {
        AcceptedUser invitation = acceptedUserRepository.findByOriginalUserIdAndAcceptedUserId(
                originalUser.getId(),
                acceptedUser.getId()
        ).orElse(null);
        if(invitation != null) {
            throw new DataExistsException("Invitation already exists");
        }
        invitation = new AcceptedUser();
        invitation.setOriginalUser(originalUser);
        invitation.setAcceptedUser(acceptedUser);
        invitation.setInvitationStatus(InvitationStatus.PENDING);
        invitation = acceptedUserRepository.save(invitation);
        return invitation;
    }

    @Override
    public AcceptedUser acceptInvitation(Long id) throws DataExistsException, DataNotExistsException {
        AcceptedUser invitation = getAcceptedUser(id);
        return setInvitationStatus(invitation, InvitationStatus.ACCEPTED);
    }

    @Override
    public List<AcceptedUser> getAllPendingInvitations(Long id) {
        return acceptedUserRepository.findByOriginalUserIdAndInvitationStatus(id, InvitationStatus.PENDING);
    }

    public void rejectInvitation(Long id) throws DataExistsException, DataNotExistsException {
        AcceptedUser invitation = getAcceptedUser(id);
        acceptedUserRepository.deleteById(id);
    }

    @Override
    public AcceptedUser getAcceptedUser(Long id) throws DataNotExistsException, DataExistsException {
        AcceptedUser invitation = acceptedUserRepository.findById(id).orElse(null);
        if(invitation == null) {
            throw new DataNotExistsException("Invitation doesn't exists");
        }
        if(!invitation
                .getInvitationStatus()
                .name().equals(InvitationStatus.PENDING.name())
        ) {
            throw new DataExistsException("Invitation has been already processed");
        }
        return invitation;
    }

    @Override
    @Transactional
    public AcceptedUser setInvitationStatus(AcceptedUser invitation, InvitationStatus status) throws DataNotExistsException{
        invitation.setInvitationStatus(status);
        return invitation;
    }
}
