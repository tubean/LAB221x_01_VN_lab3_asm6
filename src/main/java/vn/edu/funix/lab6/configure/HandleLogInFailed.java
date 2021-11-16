package vn.edu.funix.lab6.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import vn.edu.funix.lab6.service.UserService;
import vn.edu.funix.lab6.utils.Strings;


@Component
public class HandleLogInFailed implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    private UserService userService;

    /**
     * update login failed when use login with wrong password
     * @param event event of login failed
     */
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String userName = event.getAuthentication().getPrincipal().toString();
        userService.updateLoginFailed(Strings.nvl(userName));
    }
}