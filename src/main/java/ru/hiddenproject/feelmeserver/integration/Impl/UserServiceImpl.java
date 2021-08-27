package ru.hiddenproject.feelmeserver.integration.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.exception.DataExistsException;
import ru.hiddenproject.feelmeserver.exception.DataValidityException;
import ru.hiddenproject.feelmeserver.exception.InternalException;
import ru.hiddenproject.feelmeserver.integration.UserService;
import ru.hiddenproject.feelmeserver.mapper.UserMapper;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.object.ValidationResult;
import ru.hiddenproject.feelmeserver.repository.UserRepository;
import ru.hiddenproject.feelmeserver.util.RandomUtils;
import ru.hiddenproject.feelmeserver.util.ValidationUtils;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Value("${app.user-code-length}")
    private int userCodeLength;

    @Value("${app.user-token-length}")
    private int userTokenLength;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return null;
    }

    @Override
    public User findByToken(String token) {
        return null;
    }

    @Override
    public User findByCode(String code) {
        return null;
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
}
