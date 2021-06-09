package com.mylocalgost.SpringBootApp.service;


import com.mylocalgost.SpringBootApp.dao.UserDao;
import com.mylocalgost.SpringBootApp.models.Role;
import com.mylocalgost.SpringBootApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getById(long id) {
        return userDao.getById(id);
    }


    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public boolean add(User user) {
        User userFromDB = userDao.getByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        } else {
            userDao.add(user);
            user.getRoles().add(new Role(1L, "ROLE_USER"));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return true;
        }
    }

    @Override
    public boolean update(long id, User user, String role) {
        User userFromDB = userDao.getByUsername(user.getUsername());
        if (userDao.getById(id) == null) {
            return false;
        } else if (userFromDB != null && userFromDB.getId() != id) {
            return false;
        } else {
            Set<Role> roleSet = new HashSet<>();
            if (role == null) {
                userDao.update(id, user);
            } else {
                if (role.equals("ROLE_ADMIN")) {
                    roleSet = userDao.getAllRoles().stream().collect(Collectors.toSet());
                } else {
                    roleSet.add(new Role(1l, "ROLE_USER"));
                }
                userDao.update(id, user, roleSet);
            }

            return true;
        }
    }

    @Override
    public boolean delete(long id) {
        if (userDao.getById(id) != null) {
            userDao.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public List<Role> getAllRoles() {
        return userDao.getAllRoles();
    }


}
