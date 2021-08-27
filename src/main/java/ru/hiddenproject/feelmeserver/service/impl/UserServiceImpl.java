package ru.hiddenproject.feelmeserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.enums.InvitationStatus;
import ru.hiddenproject.feelmeserver.exception.DataExistsException;
import ru.hiddenproject.feelmeserver.exception.DataNotExistsException;
import ru.hiddenproject.feelmeserver.exception.DataValidityException;
import ru.hiddenproject.feelmeserver.exception.InternalException;
import ru.hiddenproject.feelmeserver.mapper.UserMapper;
import ru.hiddenproject.feelmeserver.model.AcceptedUser;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.object.ValidationResult;
import ru.hiddenproject.feelmeserver.repository.AcceptedUserRepository;
import ru.hiddenproject.feelmeserver.repository.UserRepository;
import ru.hiddenproject.feelmeserver.service.UserService;
import ru.hiddenproject.feelmeserver.util.RandomUtils;
import ru.hiddenproject.feelmeserver.util.ValidationUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AcceptedUserRepository acceptedUserRepository;

    @Value("${app.user-code-length}")
    private int userCodeLength;

    @Value("${app.user-token-length}")
    private int userTokenLength;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AcceptedUserRepository acceptedUserRepository) {
        this.userRepository = userRepository;
        this.acceptedUserRepository = acceptedUserRepository;
    }

    @Override
    public User save(User user) throws DataValidityException{
        ValidationResult validationResult = ValidationUtils.validate(user);
        if(!validationResult.isValid()) {
            throw new DataValidityException(
                    validationResult.getFirstErrorMessage()
            );
        }
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByToken(String token) {
        return userRepository.findByToken(token).orElse(null);
    }

    @Override
    public User findByCode(String code) {
        return userRepository.findByCode(code).orElse(null);
    }

    @Override
    public String updateToken(User user) {
        String userToken = RandomUtils.generateRandomString(userTokenLength);
        user.setToken(userToken);
        return userToken;
    }

    @Override
    public User createUser(BaseUserDto baseUserDto) throws DataValidityException, InternalException, DataExistsException {
        ValidationResult validationResult = ValidationUtils.validate(baseUserDto);
        if(!validationResult.isValid()) {
            throw new DataValidityException(
                    validationResult.getFirstErrorMessage()
            );
        }
        User user = userRepository.findByLoginAndDeviceUID(baseUserDto.getLogin(), baseUserDto.getDeviceUID())
                .orElse(null);
        if(user != null) {
            throw new DataExistsException("User exists");
        }
        user = UserMapper.INSTANCE.baseDtoToModel(baseUserDto);
        user = save(user);
        if(user == null) {
            throw new InternalException("User saving operation failed");
        }
        if(user.getId() == null) {
            throw new InternalException("User id null");
        }
        String userCode = user.getId() + RandomUtils.generateRandomString(userCodeLength);
        String userToken = RandomUtils.generateRandomString(userTokenLength);
        user.setCode(userCode);
        user.setToken(userToken);
        return save(user);
    }

    @Override
    public AcceptedUser inviteUser(User originalUser, User acceptedUser) throws DataExistsException{
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

    @Override
    public void rejectInvitation(Long id) throws DataExistsException, DataNotExistsException {
        AcceptedUser invitation = getAcceptedUser(id);
        acceptedUserRepository.deleteById(id);
    }

    private AcceptedUser getAcceptedUser(Long id) throws DataNotExistsException, DataExistsException {
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

    @Transactional
    AcceptedUser setInvitationStatus(AcceptedUser invitation, InvitationStatus status) {
        invitation.setInvitationStatus(status);
        return invitation;
    }
}
