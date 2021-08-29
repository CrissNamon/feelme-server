package ru.hiddenproject.feelmeserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hiddenproject.feelmeserver.enums.InvitationStatus;
import ru.hiddenproject.feelmeserver.exception.DataExistsException;
import ru.hiddenproject.feelmeserver.exception.DataNotExistsException;
import ru.hiddenproject.feelmeserver.model.Invitation;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.repository.InvitationRepository;
import ru.hiddenproject.feelmeserver.service.InvitationService;

import java.util.List;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;

    protected Logger logger = LoggerFactory.getLogger(InvitationServiceImpl.class);

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    @Override
    public Invitation inviteUser(User originalUser, User acceptedUser) throws DataExistsException {
        Invitation invitation = invitationRepository.findByOriginalUserIdAndAcceptedUserId(
                originalUser.getId(),
                acceptedUser.getId()
        ).orElse(null);
        if(invitation != null) {
            throw new DataExistsException("Invitation already exists");
        }
        invitation = new Invitation();
        invitation.setOriginalUser(originalUser);
        invitation.setAcceptedUser(acceptedUser);
        invitation.setInvitationStatus(InvitationStatus.PENDING);
        invitation = invitationRepository.save(invitation);
        return invitation;
    }

    @Override
    public Invitation acceptInvitation(Long id) throws DataExistsException, DataNotExistsException {
        Invitation invitation = getInvitation(id);
        if(!invitation
                .getInvitationStatus()
                .name().equals(InvitationStatus.PENDING.name())
        ) {
            throw new DataExistsException("Invitation has been already processed");
        }
        return setInvitationStatus(invitation, InvitationStatus.ACCEPTED);
    }

    @Override
    public List<Invitation> getAllPendingInvitations(Long id) {
        return invitationRepository.findByOriginalUserIdAndInvitationStatus(id, InvitationStatus.PENDING);
    }

    @Override
    public void rejectInvitation(Long id) throws DataExistsException, DataNotExistsException {
        Invitation invitation = getInvitation(id);
        if(!invitation
                .getInvitationStatus()
                .name().equals(InvitationStatus.PENDING.name())
        ) {
            throw new DataExistsException("Invitation has been already processed");
        }
        invitationRepository.deleteById(id);
    }

    @Override
    public Invitation getInvitation(Long id) throws DataNotExistsException {
        Invitation invitation = invitationRepository.findById(id).orElse(null);
        if(invitation == null) {
            throw new DataNotExistsException("Invitation doesn't exists");
        }
        return invitation;
    }

    @Override
    public Invitation setInvitationStatus(Invitation invitation, InvitationStatus status) throws DataNotExistsException{
        if(invitation.getId() == null) {
            throw new DataNotExistsException("Invitation doesn't exists");
        }
        invitation.setInvitationStatus(status);
        return invitationRepository.save(invitation);
    }
}
