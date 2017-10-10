package pl.blog.services.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.blog.domain.CustomUserDetails;
import pl.blog.domain.Users;
import pl.blog.repos.UserRepo;

import java.util.Optional;

/**
 * Created by Mike on 04.08.2017.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Users> optional = userRepo.findUserByUsername(s);
        optional.
                orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return optional.
                map(CustomUserDetails::new).get();
    }
}
