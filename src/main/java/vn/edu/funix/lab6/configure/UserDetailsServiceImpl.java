package vn.edu.funix.lab6.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.funix.lab6.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<vn.edu.funix.lab6.entity.User> user = this.userRepository.findByUserName(userName);

        if (!user.isPresent()) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        } else {
            System.out.println("Found User: " + user);

            if (user.get().isLocked()) {
                throw new LockedException("User " + userName + " has been locked!");
            }

            // [ROLE_USER, ROLE_ADMIN,..]
            List<GrantedAuthority> grantList = new ArrayList<>();
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
            grantList.add(authority);

            return new User(user.get().getUserName(), user.get().getEncrytedPassword(), grantList);
        }
    }



}