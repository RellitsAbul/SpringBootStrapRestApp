package com.mylocalgost.SpringBootApp.dao;


import com.mylocalgost.SpringBootApp.models.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    public User getById(long id);

    List<User> getAll();

    public void update(long id, User user);

    public void delete(long id);

    User getByUsername(String username);
}
