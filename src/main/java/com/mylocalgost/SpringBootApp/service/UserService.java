package com.mylocalgost.SpringBootApp.service;


import com.mylocalgost.SpringBootApp.models.Role;
import com.mylocalgost.SpringBootApp.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService extends UserDetailsService {

    public List<User> getAll();

    public User getById(long id);

    public boolean add(User user);

    public boolean update(User user);

    public boolean delete(long id);

    public UserDetails loadUserByUsername(String username);

    public List<Role> getAllRoles();
}
