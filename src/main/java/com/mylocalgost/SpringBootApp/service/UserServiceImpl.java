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

import java.util.List;


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
            if (user.getRoles().stream().findFirst().toString().equals("Optional[ROLE_ADMIN]")) {
                user.getRoles().add(new Role(1L, "ROLE_USER"));
            }
            userDao.add(user);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return true;
        }
    }

    @Override
    public boolean update(User user) {
        if (user.getRoles().stream().findFirst().toString().equals("Optional[ROLE_ADMIN]")) {
            user.getRoles().add(new Role(1L, "ROLE_USER"));
        }
        userDao.update(user);
        return true;
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
