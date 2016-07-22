package com.seri.web.dao.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.seri.security.Role;
import com.seri.service.notification.RoleType;
import com.seri.web.dao.UserDao;
import com.seri.web.model.User;
import com.seri.web.utils.DbCon;
import com.seri.web.utils.GlobalFunUtils;

/**
 * Created by puneet on 04/04/16.
 */
public class UserDaoImpl implements UserDao {

    private GlobalFunUtils globalFunUtils = new GlobalFunUtils();

    @Override
    public void create(User userDetails) {
        EntityManager em = DbCon.getEntityManager();
        globalFunUtils.inActiveAllTransaction(em);
        em.getTransaction().begin();
        em.persist(userDetails);
        em.getTransaction().commit();em.clear();
    }

    @Override
    public User getAuth(String email, String password) {
        EntityManager em = DbCon.getEntityManager();
        Query ui =  em.createQuery("select c from User c where c.username='"+email+"' and c.password = '"+password+"' and c.firstReset!=0");
        return (User) ui.getResultList().get(0);
    }

    @Override
    public User getUserUsingEmail(String email) {
        EntityManager em = DbCon.getEntityManager();
        Query ui =  em.createQuery("select c from User c where c.email='"+email+"'");
        em.clear();
        if(ui.getResultList().size()>0)
            return (User) ui.getResultList().get(0);
        else
            return null;
    }

    @Override
    public List<User> getUnregisterUser() {
        EntityManager em = DbCon.getEntityManager();
        Query ui =  em.createQuery("select c from User c where c.firstReset=0 and c.status=true");
        em.clear();
        if(ui.getResultList().size()>0)
            return ui.getResultList();
        else
            return null;
    }
    
    public Role getRoleByRoleName(RoleType role){
    	EntityManager em = DbCon.getEntityManager();
        Query ui =  em.createQuery("select r from Role r where r.roleName=:rolename");
        ui.setParameter("rolename", role);
        em.clear();
        Role roles = (Role) ui.getSingleResult();
        return roles;
    }

    @Override
    public void update(User userDetails) {
        EntityManager em = DbCon.getEntityManager();
        globalFunUtils.inActiveAllTransaction(em);
        em.getTransaction().begin();
        User userDetails1 = em.find(User.class, userDetails.getId());
        userDetails1.setPassword(userDetails.getPassword());
        userDetails1.setEmail(userDetails.getEmail());
        userDetails1.setLastUpdatedDate(userDetails.getLastUpdatedDate());
        userDetails1.setLastUpdatedBy(userDetails.getLastUpdatedBy());
        userDetails1.setDob(userDetails.getDob());
//        userDetails1.setRole(userDetails.getRole());
        userDetails1.setfName(userDetails.getfName());
        userDetails1.setlName(userDetails.getlName());
        userDetails1.setmName(userDetails.getmName());
        userDetails1.setEnabled(userDetails.getEnabled());
        userDetails1.setFirstReset(userDetails.getFirstReset());
        userDetails1.setPhoto(userDetails.getPhoto());
        userDetails1.setGender(userDetails.getGender());
        userDetails1.setPasswordToken(userDetails.getPasswordToken());
        userDetails1.setSchool(userDetails.getSchool());
        userDetails1.setStandard(userDetails.getStandard());
        em.getTransaction().commit();
    }
    
    public User getUserById(long id){
    	EntityManager em = DbCon.getEntityManager();
        globalFunUtils.inActiveAllTransaction(em);
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        em.getTransaction().commit();
        return user;
    }
}
