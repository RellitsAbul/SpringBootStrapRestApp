package com.mylocalgost.SpringBootApp.dao;


import com.mylocalgost.SpringBootApp.models.Role;
import com.mylocalgost.SpringBootApp.models.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    void add(User user);

    public User getById(long id);

    List<User> getAll();

    public void update(User user);


    public void delete(long id);

    User getByUsername(String username);

    public List<Role> getAllRoles();
}

