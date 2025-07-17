package yeri.csphub.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.UsersRepo;

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
