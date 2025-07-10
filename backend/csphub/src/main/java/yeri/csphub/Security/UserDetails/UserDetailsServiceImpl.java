package yeri.csphub.Security.UserDetails;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.UsersRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UsersRepo userRepo;

    public UserDetailsServiceImpl(UsersRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}
