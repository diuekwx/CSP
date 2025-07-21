package yeri.csphub.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.UsersRepo;

@Component
public class UserUtil {

    private final UsersRepo usersRepo;

    public UserUtil(UsersRepo usersRepo){
        this.usersRepo = usersRepo;
    }

    public Users findUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepo.findByUsername(username).orElseThrow();
        return user;
    }
}
