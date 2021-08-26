package ru.hiddenproject.feelmeserver.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.exception.DataValidityException;
import ru.hiddenproject.feelmeserver.mapper.UserMapper;
import ru.hiddenproject.feelmeserver.model.User;
import ru.hiddenproject.feelmeserver.object.ValidationResult;
import ru.hiddenproject.feelmeserver.repository.UserRepository;
import ru.hiddenproject.feelmeserver.service.UserService;
import ru.hiddenproject.feelmeserver.util.RandomUtils;
import ru.hiddenproject.feelmeserver.util.ValidationUtils;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Value("$app.user-code-length")
    private int userCodeLength;

    @Value("$app.user-token-length")
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
        return null;
    }

    @Override
    public User createUser(BaseUserDto baseUserDto) throws DataValidityException {
        ValidationResult validationResult = ValidationUtils.validate(baseUserDto);
        if(!validationResult.isValid()) {
            throw new DataValidityException(
                    validationResult.getFirstErrorMessage()
            );
        }
        User user = UserMapper.INSTANCE.baseDtoToModel(baseUserDto);
        user = savePlain(user);
        String userCode = user.getId() + RandomUtils.generateRandomString(userCodeLength);
        String userToken = RandomUtils.generateRandomString(userTokenLength);
        user.setCode(userCode);
        user.setToken(userToken);
        return save(user);
    }

    private User savePlain(User user)
    {
        return userRepository.save(user);
    }
}
