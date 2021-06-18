package com.mylocalgost.SpringBootApp;

import com.mylocalgost.SpringBootApp.dao.UserDao;
import com.mylocalgost.SpringBootApp.models.Role;
import com.mylocalgost.SpringBootApp.models.User;
import com.mylocalgost.SpringBootApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class FirstInitialization {
    @Autowired
    private UserDao uD;
    @Autowired
    private UserService uS;
    private Set<Role> user = new HashSet<>();
    private Set<Role> admin = new HashSet<>();
    private User[] users = new User[3];

    {

        user.add(new Role(1L, "ROLE_USER"));
        admin.add(new Role(2L, "ROLE_ADMIN"));
        users[0] = new User("admin", "admin", "admin@mail.ru", "admin", "admin", admin);
        users[1] = new User("user1", "user1", "user1@mail.ru", "user1", "user1", user);
        users[2] = new User("user2", "user2", "user2@mail.ru", "user2", "user2", user);
    }

    @PostConstruct
    public void post() {
    uD.addRolesInBD();
        for (User u :
                users) {
            uS.add(u);
        }
    }

}
