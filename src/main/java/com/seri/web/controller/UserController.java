package com.seri.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.seri.security.Role;
import com.seri.service.notification.RoleType;
import com.seri.web.dao.daoImpl.UserDaoImpl;
import com.seri.web.model.User;
import com.seri.web.model.UserRoles;
import com.seri.web.utils.CalendarUtil;
import com.seri.web.utils.GlobalFunUtils;
import com.seri.web.utils.LoggedUserUtil;

/**
 * Created by puneet on 07/04/16.
 */
public class UserController {

    private UserDaoImpl userDao = new UserDaoImpl();

    public Boolean createUser(User user, HttpServletRequest request) {
        try {
            if(userDao.getUserUsingEmail(user.getEmail()) == null) {
                String passwordToken = GlobalFunUtils.getMd5Hex(user.getEmail() + user.getDefaultRole() + CalendarUtil.getDate().toString());
                System.out.println(user.getEmail() +" :: "+ user.getfName()+" :: "+passwordToken);
                user.setCreatedDate(CalendarUtil.getDate());
                user.setCreatedBy(LoggedUserUtil.getUserId());
                user.setLastUpdatedDate(CalendarUtil.getDate());
                user.setLastUpdatedBy(LoggedUserUtil.getUserId());
                user.setFirstReset(0);
                user.setPasswordToken(passwordToken);
                user.setPassword(passwordToken);
                user.setUsername(user.getEmail());
                
                Role role  = userDao.getRoleByRoleName(RoleType.valueOf(request.getParameter("role")));
                		
                UserRoles userRoles = new UserRoles();
                userRoles.setRole(role);
                userRoles.setUser(user);
                user.getUserRoles().add(userRoles);
                
                userDao.create(user);

                Map<String, String> map = new HashMap<String, String>();
                String siteUrl = GlobalFunUtils.getSiteUrl(request);

                map.put("to", user.getEmail());
                map.put("from", "noreply@seri.com");
                map.put("subject", "SERI ACCOUNT CREATED");
                map.put("body", "Create your password using below link <br>" + siteUrl + "/createpassword/?token=" + passwordToken);
                //globalFunUtils.sendMail(map);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }

    }

    public User checkPasswordToken(HttpServletRequest request) {
        try {
            String passwordToken = request.getParameter("token");
            List<User> unregisterUser = userDao.getUnregisterUser();
            for (User userList : unregisterUser) {
                String userToken = GlobalFunUtils.getMd5Hex(userList.getEmail() + userList.getDefaultRole() + userList.getCreatedDate().toString());
                if(userToken.equals(passwordToken))
                    return userList;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public User createPassword(HttpServletRequest request){
        try {
            String email = request.getParameter("login");
            User user = userDao.getUserUsingEmail(email);
            user.setPassword(GlobalFunUtils.getMd5Hex(request.getParameter("password")));
            user.setLastUpdatedBy(LoggedUserUtil.getUserId());
            user.setLastUpdatedDate(CalendarUtil.getDate());
            user.setFirstReset(1);
            userDao.update(user);
            return user;
        } catch(Exception e) {
            return null;
        }
    }

    public User setFormAttributes(User dbUser, User formUser){
        dbUser.setfName(formUser.getfName());
        dbUser.setmName(formUser.getmName());
        dbUser.setlName(formUser.getlName());
        dbUser.setDob(formUser.getDob());
        //dbUser.setGender(formUser.getGender());
        dbUser.setFirstReset(2);
        dbUser.setLastUpdatedDate(CalendarUtil.getDate());

        return dbUser;
    }
}
