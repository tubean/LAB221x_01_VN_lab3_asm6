package vn.edu.funix.lab6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import vn.edu.funix.lab6.entity.User;
import vn.edu.funix.lab6.repository.UserRepository;
import vn.edu.funix.lab6.utils.AppUtils;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final String LOGIN_FAILED_MESSAGE = "Invalid User ID/Password. Number of attempts left %s";
    private static final String LOGIN_FAILED_AND_LOCKED_MESSAGE = "Too many failed login attempts, login temporarily disabled. Please contact with administrator.";

    public void updateLoginFailed(String userName) {
        userRepository.findByUserName(userName).ifPresent(user -> {
            int attempt = user.getFailureLoginTemp();
            if (attempt < 2) {
                attempt++;
                user.setFailureLoginTemp(attempt);
                userRepository.save(user);
                String error = String.format(LOGIN_FAILED_MESSAGE, 3 - attempt);
                throw new BadCredentialsException(error);
            } else {
                user.setFailureLoginTemp(3);
                user.setLocked(true);
                userRepository.save(user);
                throw new BadCredentialsException(LOGIN_FAILED_AND_LOCKED_MESSAGE);
            }
        });
    }

    public void resetFailureLogin(String userName) {
        userRepository.findByUserName(userName).ifPresent(user -> {
            if (user.getFailureLoginTemp() > 0) {
                user.setFailureLoginTemp(0);
                userRepository.save(user);
            }
        });
    }

    public String saveNewPassword(String userName, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            User u = user.get();
            String hashOldPassword = u.getEncrytedPassword();
            if (BCrypt.checkpw(oldPassword, hashOldPassword)) {
                u.setEncrytedPassword(AppUtils.encryptPassword(newPassword));
                u.setFirstLogin(false);
                userRepository.save(u);
                return "success";
            } return "wrong password";
        } else {
            return "User not found!";
        }
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


}
