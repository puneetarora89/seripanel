package com.seri.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.seri.service.notification.NotificatiobServiceAdaptor;
import com.seri.web.dao.daoImpl.EmailDaoImpl;
import com.seri.web.model.Email;
import com.seri.web.model.User;
import com.seri.web.utils.GlobalFunUtils;
import com.seri.web.utils.LoggedUserUtil;

/**
 * Created by puneet on 12/04/16.
 */
@Controller
@RequestMapping(value = "/email**")
public class EmailController {
	
	@Autowired
    private GlobalFunUtils globalFunUtils;
    EmailDaoImpl emailDao = new EmailDaoImpl();

    @RequestMapping(value = "/compose**", method = RequestMethod.GET)
    public ModelAndView composePage(@ModelAttribute("emailForm")Email email, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        email.setFromId(LoggedUserUtil.getEmailId());
        try{
            if(request.getParameter("forward") != null)
            {
                Email dbEmail = emailDao.getEmailUsingId(Integer.parseInt(request.getParameter("forward")));
                if(dbEmail.getToId().equals(LoggedUserUtil.getEmailId())) {
                    email.setSubject("FW: " + dbEmail.getSubject());
                    email.setMsg(dbEmail.getMsg());
                }
            }
        } catch (Exception e) {}

        model.addObject("email", email);
        model.setViewName("email/compose");
        return model;
    }

    @RequestMapping(value = "/sendemail**", method = RequestMethod.POST)
    public ModelAndView sendEmailPage(@ModelAttribute("emailForm")Email email, HttpServletResponse response, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        String date = globalFunUtils.getDateTime();
        email.setSentDate(date);
        try {
            emailDao.create(email);
            model.setViewName("email/compose");
            if(request.getParameter("isAjax") == null)
                response.sendRedirect(globalFunUtils.getSiteUrl(request)+"/inbox/compose/?sendsuccess=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "/inbox**", method = RequestMethod.GET)
    public ModelAndView loginPage() {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        User dbUser = globalFunUtils.getLoggedInUserDetail();
        List<Email> recEmailList = emailDao.getEmailUsingTo(LoggedUserUtil.getEmailId());
        model.addObject("recEmailList", recEmailList);
        model.setViewName("email/inbox");
        return model;
    }

    @RequestMapping(value = "/sent**", method = RequestMethod.GET)
    public ModelAndView sentPage() {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        User dbUser = globalFunUtils.getLoggedInUserDetail();
        List<Email> sentEmailList = emailDao.getEmailUsingFrom(LoggedUserUtil.getEmailId());
        model.addObject("sentEmailList", sentEmailList);
        model.setViewName("email/sent");
        return model;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("mailId"));
        String mode = request.getParameter("mode");
        Email dbEmail = emailDao.getEmailUsingId(id);
        if(mode.equals("sentDel"))
            dbEmail.setSenderDelStatus(1);
        if(mode.equals("recDel"))
            dbEmail.setReciverDelStatus(1);
        emailDao.update(dbEmail);
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        model.setViewName("email/inbox");
        return model;
    }

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public ModelAndView readStatus(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("mailId"));
            Email dbEmail = emailDao.getEmailUsingId(id);
            dbEmail.setReadStatus(1);
            emailDao.update(dbEmail);
        }
        catch (Exception e){
        }
        return null;
    }
}
