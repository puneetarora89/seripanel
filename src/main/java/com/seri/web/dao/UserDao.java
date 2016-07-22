package com.seri.web.dao;

import java.util.List;

import com.seri.security.Role;
import com.seri.service.notification.RoleType;
import com.seri.web.model.User;

/**
 * Created by puneet on 04/04/16.
 */
public interface UserDao {

    public void create(User userDetails);

    public User getAuth(String email, String password);

    public User getUserUsingEmail(String email);

    public List getUnregisterUser();

    public void update(User userDetails);
    public Role getRoleByRoleName(RoleType role);
    
    public User getUserById(long id);
}
