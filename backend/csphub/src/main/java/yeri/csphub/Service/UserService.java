package yeri.csphub.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yeri.csphub.Entities.ERole;
import yeri.csphub.Entities.Role;
import yeri.csphub.Entities.Users;
import yeri.csphub.Payload.Request.SignupRequest;
import yeri.csphub.Repository.RoleRepo;
import yeri.csphub.Repository.UsersRepo;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepo usersRepo;
    private final RoleRepo roleRepo;

    public UserService(PasswordEncoder passwordEncoder, UsersRepo usersRepo, RoleRepo roleRepo){
        this.passwordEncoder = passwordEncoder;
        this.usersRepo = usersRepo;
        this.roleRepo = roleRepo;
    }


    // provid3er =  email or auth, assuming signin link = email
    public void createUser(SignupRequest signUpRequest){
        Users user = new Users(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepo.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepo.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepo.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        user.setProvider("email");

        usersRepo.save(user);
    }
}
