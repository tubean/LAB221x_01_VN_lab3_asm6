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

    /**
     * update times of log in failed
     * @param userName user Id
     */
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

    /**
     * update new password for user
     * @param userName user id
     * @param oldPassword old password
     * @param newPassword new password
     * @return success or error message
     */
    public String saveNewPassword(String userName, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            User u = user.get();
            String hashOldPassword = u.getEncryptedPassword();
            if (BCrypt.checkpw(oldPassword, hashOldPassword)) {
                u.setEncryptedPassword(AppUtils.encryptPassword(newPassword));
                u.setFirstLogin(false);
                userRepository.save(u);
                return "success";
            } return "wrong password";
        } else {
            return "User not found!";
        }
    }

    /**
     * find user by user id
     * @param userName user id
     * @return Optional User
     */
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    /**
     * save or update user
     * @param user user
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
