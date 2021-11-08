package vn.edu.funix.lab6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import vn.edu.funix.lab6.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final String LOGIN_FAILED_MESSAGE = "User %s login failed. You have %s times to try again.";
    private static final String LOGIN_FAILED_AND_LOCKED_MESSAGE = "Too many failed login attempts, login temporarily disabled. Please contact with administrator.";

    public void updateLoginFailed(String userName) {
        userRepository.findByUserName(userName).ifPresent(user -> {
            int attempt = user.getFailureLoginTemp();
            if (attempt < 2) {
                attempt++;
                user.setFailureLoginTemp(attempt);
                userRepository.save(user);
                String error = String.format(LOGIN_FAILED_MESSAGE, userName, 3 - attempt);
                throw new BadCredentialsException(error);
            } else {
                user.setFailureLoginTemp(3);
                user.setLocked(true);
                userRepository.save(user);
                throw new BadCredentialsException(LOGIN_FAILED_AND_LOCKED_MESSAGE);
            }
        });
    }
}
