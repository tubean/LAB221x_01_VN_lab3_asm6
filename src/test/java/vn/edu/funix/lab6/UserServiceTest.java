package vn.edu.funix.lab6;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vn.edu.funix.lab6.entity.User;
import vn.edu.funix.lab6.repository.UserRepository;
import vn.edu.funix.lab6.service.UserService;
import vn.edu.funix.lab6.utils.AppUtils;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        // remove all data
        userRepository.deleteAll();
        // init data
        initData();
    }

    // Test module DELETE
    @Test
    void testUpdateLoginFailed() {
    }

    void initData() {
        // user 1: first login
        User user1 = new User();
        user1.setFirstLogin(true);
        user1.setLocked(false);
        user1.setFailureLoginTemp(0);
        user1.setUserName("1234567891234567");
        user1.setEncryptedPassword(AppUtils.encryptPassword("12345678"));
        userRepository.save(user1);

        // user 2: not first login
        User user2 = new User();
        user2.setFirstLogin(false);
        user2.setLocked(false);
        user2.setFailureLoginTemp(0);
        user2.setUserName("1234567899876543");
        user2.setEncryptedPassword(AppUtils.encryptPassword("12345678"));
        userRepository.save(user2);
    }

}
