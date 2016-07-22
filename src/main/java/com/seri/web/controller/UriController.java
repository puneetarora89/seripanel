package com.seri.web.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.seri.web.utils.GlobalFunUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seri.common.Gender;
import com.seri.common.GenderPropertyEditorSupport;
import com.seri.common.MyCustomNumberEditor;
import com.seri.common.RoleTypePropertyEditorSupport;
import com.seri.service.notification.RoleType;
import com.seri.web.dao.HodDao;
import com.seri.web.dao.ParentsDao;
import com.seri.web.dao.SchoolDao;
import com.seri.web.dao.StudentDao;
import com.seri.web.dao.daoImpl.HodDaoImpl;
import com.seri.web.dao.daoImpl.ParentsDaoImpl;
import com.seri.web.dao.daoImpl.SchoolDaoImpl;
import com.seri.web.dao.daoImpl.StudentDaoImpl;
import com.seri.web.dao.daoImpl.TeacherDaoImpl;
import com.seri.web.dao.daoImpl.UserDaoImpl;
import com.seri.web.model.Hod;
import com.seri.web.model.Parents;
import com.seri.web.model.School;
import com.seri.web.model.Student;
import com.seri.web.model.Teacher;
import com.seri.web.model.User;
import com.seri.web.utils.CalendarUtil;
import com.seri.web.utils.LoggedUserUtil;

/**
 * Created by puneet on 04/04/16.
 */
@Controller
public class UriController {

    private UserController userController = new UserController();
    private TeacherController teacherController = new TeacherController();
    private StudentController studentController = new StudentController();
    private HodController hodController = new HodController();

    private UserDaoImpl userDao = new UserDaoImpl();
    private TeacherDaoImpl teacherDao = new TeacherDaoImpl();
    private SchoolDao schoolDao = new SchoolDaoImpl();
    private StudentDao studentDao = new StudentDaoImpl();
    private ParentsDao parentsDao = new ParentsDaoImpl();
    private HodDao hodDao = new HodDaoImpl();

    @Autowired
    private GlobalFunUtils globalFunUtils;
    
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
    	// true passed to CustomDateEditor constructor means convert empty String to null
    	binder.registerCustomEditor(Date.class, new CustomDateEditor(CalendarUtil.getSystemDateFormat(), true));
        binder.registerCustomEditor(RoleType.class, new RoleTypePropertyEditorSupport());
        binder.registerCustomEditor(Gender.class, new GenderPropertyEditorSupport());
        binder.registerCustomEditor(float.class, new MyCustomNumberEditor(Float.class));
        binder.registerCustomEditor(long.class, new MyCustomNumberEditor(Long.class));
        binder.registerCustomEditor(int.class, new MyCustomNumberEditor(Integer.class));
        binder.registerCustomEditor(double.class, new MyCustomNumberEditor(Double.class));
    } 

    @RequestMapping(value = "/login**", method = RequestMethod.GET)
    public ModelAndView loginPage() {
        ModelAndView model = new ModelAndView();
        //globalFunUtils.getNotification(model);
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/adduser**", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        model.setViewName("user/adduser");
        return model;

    }

    @RequestMapping(value = "/addadmin**", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView adminAdd(@RequestParam("role") RoleType roleType, @ModelAttribute("userForm") User userForm, @ModelAttribute("schoolForm") School schoolForm, @ModelAttribute("studentForm") Student studentForm, @ModelAttribute("teacherForm") Teacher teacherForm, @ModelAttribute("parentsForm") Parents parentsForm, @ModelAttribute("hodForm") Hod hodForm, HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        ModelAndView model = new ModelAndView();
        if(userController.createUser(userForm, request))
        {

            if(RoleType.ROLE_SCHOOL_ADMIN.equals(roleType)) {

                School school = schoolDao.getSchoolUsingId(schoolForm.getSchoolId());
                school.setPrincipal(LoggedUserUtil.getUserId());
                school.setLastUpdatedBy(LoggedUserUtil.getUserId());
                school.setLastUpdatedDate(CalendarUtil.getDate());
                schoolDao.update(school);

            } else if(RoleType.ROLE_STUDENT.equals(roleType)) {

                studentForm.setEmail(userForm.getEmail());
                studentForm.setUserId(userForm.getId());
                studentForm.setCreatedBy(LoggedUserUtil.getUserId());
                studentForm.setLastUpdatedBy(LoggedUserUtil.getUserId());
                studentForm.setLastUpdatedDate(CalendarUtil.getDate());
                studentForm.setCreatedDate(CalendarUtil.getDate());
                studentDao.create(studentForm);
                userForm.setStandard(String.valueOf(studentForm.getStuStandardId()));
            	userForm.setSchool(studentForm.getStuSchoolId());
            	userDao.update(userForm);

            } else if(RoleType.ROLE_TEACHER.equals(roleType)) {

                teacherForm.setEmail(userForm.getEmail());
                teacherForm.setTeacherStandardId(","+teacherForm.getTeacherStandardId()+",");
                teacherForm.settLoginId(userForm.getId());
                teacherForm.settCreatedBy(LoggedUserUtil.getUserId());
                teacherForm.settLastUpdateBy(LoggedUserUtil.getUserId());
                teacherForm.settLastUpdateDate(CalendarUtil.getDate());
                teacherForm.settCreatedDate(CalendarUtil.getDate());
                teacherDao.create(teacherForm);
                userForm.setStandard(teacherForm.getTeacherStandardId());
            	userForm.setSchool(teacherForm.getTeacherSchoolId());
            	userDao.update(userForm);

            }  else if(RoleType.ROLE_PARENT.equals(roleType)) {
            	
            	parentsForm.setUserId(userForm.getId());
                parentsForm.setEmail(userForm.getEmail());
                parentsForm.setCreatedBy(LoggedUserUtil.getUserId());
                parentsForm.setLastUpdatedBy(LoggedUserUtil.getUserId());
                parentsForm.setLastUpdatedDate(CalendarUtil.getDate());
                parentsForm.setCreatedDate(CalendarUtil.getDate());
                if(parentsForm.getStudentId() !=0 ){
                	User user = userDao.getUserById(parentsForm.getStudentId());
                	userForm.setStandard(user.getStandard());
                	userForm.setSchool(user.getSchool());
                	userDao.update(userForm);
            	}
                parentsDao.create(parentsForm);
                Student student = studentDao.getStudentUsingStudentId(parentsForm.getStudentId());
                student.setParentId(userForm.getId());
                studentDao.update(student);

            } else if(RoleType.ROLE_HOD.equals(roleType)) {
            	hodForm.setHodUserId(userForm.getId());
                hodForm.setEmail(userForm.getEmail());
                hodForm.setEmail(userForm.getEmail());
                hodForm.setCreatedBy(LoggedUserUtil.getUserId());
                hodForm.setLastUpdatedBy(LoggedUserUtil.getUserId());
                hodForm.setLastUpdatedDate(CalendarUtil.getDate());
                hodForm.setCreatedDate(CalendarUtil.getDate());
                hodDao.create(hodForm);
            }
            return new ModelAndView("redirect:adduser?token=success&");
        } else {
            model.addObject("userForm", userForm);
            model.addObject("emailErrrrMsg", "User with same email already registered");
            model.addObject("emailErr", true);
            model.setViewName("user/adduser");
        }


        return model;
    }

    @RequestMapping(value = "/createpassword**", method = RequestMethod.GET)
    public ModelAndView createPasswordPage(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        User userDetails = userController.checkPasswordToken(request);
        if(userDetails!=null) {
            model.addObject("userDetails", userDetails);
            model.setViewName("user/createpassword");
        }
        else {
            model.addObject("errMsg", "Opps!!! Page not valid");
            model.setViewName("common/error");
        }

        return model;

    }

    @RequestMapping(value = "/addpassword**", method = RequestMethod.POST)
    public ModelAndView addPasswordFirstTimeUser(@ModelAttribute("userForm") User userForm, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        userController.createPassword(request);
        model.addObject("userDetails", userForm);
        model.setViewName("user/createpassword");
        return model;
    }

    @RequestMapping(value = "/updateprofile**", method = RequestMethod.GET)
    public ModelAndView updateUserPage() {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        model.addObject("userForm", LoggedUserUtil.getUser());
        if(LoggedUserUtil.hasRole(RoleType.ROLE_SUP_ADMIN))
        {
            model.addObject("formAction", "adminUpdate");
        }
        else if(LoggedUserUtil.hasRole(RoleType.ROLE_SUB_ADMIN))
        {
            model.addObject("formAction", "adminUpdate");
        }
        else if(LoggedUserUtil.hasRole(RoleType.ROLE_SCHOOL_ADMIN))
        {
            model.addObject("formAction", "schoolUpdate");
        }
        else if(LoggedUserUtil.hasRole(RoleType.ROLE_TEACHER))
        {
            Teacher teacherProfile = teacherDao.getTeacherUsingLoginId(LoggedUserUtil.getUserId());
            model.addObject("teacher", teacherProfile);
            model.addObject("formAction", "teacherUpdate");
        }
        else if(LoggedUserUtil.hasRole(RoleType.ROLE_HOD))
        {
            Hod hodProfile = hodDao.getHodByHodId(LoggedUserUtil.getUserId());
            model.addObject("hodForm", hodProfile);
            model.addObject("formAction", "hodUpdate");
        }
        else if(LoggedUserUtil.hasRole(RoleType.ROLE_STUDENT))
        {
            Student studentProfile = studentDao.getStudentUsingStudentLogin(LoggedUserUtil.getUserId());
            model.addObject("studentForm", studentProfile);
            model.addObject("formAction", "studentUpdate");
        }
        else if(LoggedUserUtil.hasRole(RoleType.ROLE_PARENT))
        {
            Parents parents = parentsDao.getProfileUsingLoginId(LoggedUserUtil.getUserId());
            model.addObject("parentsForm", parents);
            model.addObject("formAction", "parentUpdate");
        }
        model.setViewName("user/updateprofile");
        return model;
    }

    @RequestMapping(value = "/teacherUpdate**", method = RequestMethod.POST)
    public ModelAndView teacherUpdate(@ModelAttribute("teacher") Teacher teacher, @ModelAttribute("userForm") User user, HttpServletRequest request) {
        User sessUser = LoggedUserUtil.getUser();
        sessUser = userController.setFormAttributes(sessUser, user);
        userDao.update(sessUser);

        teacherController.addUpdate(teacher, sessUser);
        if(LoggedUserUtil.hasRole(RoleType.ROLE_TEACHER))
            return new ModelAndView("redirect:updateprofile?token=success&");
        else
            return new ModelAndView("redirect:teacher/manage?token=success&p="+request.getParameter("p")+"&rpp="+request.getParameter("rpp"));
    }

    @RequestMapping(value = "/adminUpdate**", method = RequestMethod.POST)
    public ModelAndView adminUpdate(@ModelAttribute("userForm") User user) {
        User sessUser = LoggedUserUtil.getUser();
        sessUser = userController.setFormAttributes(sessUser, user);
        sessUser.setLastUpdatedBy(LoggedUserUtil.getUserId());
        userDao.update(sessUser);
        return new ModelAndView("redirect:updateprofile?token=success&");
    }

    @RequestMapping(value = "/studentUpdate**", method = RequestMethod.POST)
    public ModelAndView studentUpdate(@ModelAttribute("studentForm") Student student, @ModelAttribute("userForm") User user, HttpServletRequest request) {
        User updateUser = userDao.getUserUsingEmail(user.getEmail());
        updateUser = userController.setFormAttributes(updateUser, user);
        userDao.update(updateUser);

        studentController.addUpdate(student);
        if(LoggedUserUtil.hasRole(RoleType.ROLE_STUDENT))
            return new ModelAndView("redirect:updateprofile?token=success&");
        else
            return new ModelAndView("redirect:student/manage?token=success&p="+request.getParameter("p")+"&rpp="+request.getParameter("rpp"));
    }

    @RequestMapping(value = "/hodUpdate**", method = RequestMethod.POST)
    public ModelAndView hodUpdate(@ModelAttribute("hodForm") Hod hod, @ModelAttribute("userForm") User user, HttpServletRequest request) {
        User updateUser = userDao.getUserUsingEmail(user.getEmail());
        updateUser = userController.setFormAttributes(updateUser, user);
        userDao.update(updateUser);

        hodController.addUpdate(hod);
        if(LoggedUserUtil.hasRole(RoleType.ROLE_HOD))
            return new ModelAndView("redirect:updateprofile?token=success&");
        else
            return new ModelAndView("redirect:hod/manage?token=success&schoolid="+hod.getHodSchoolId()+"&p="+request.getParameter("p")+"&rpp="+request.getParameter("rpp"));
    }

    @RequestMapping(value = "/parentUpdate**", method = RequestMethod.POST)
    public ModelAndView parentUpdate(@ModelAttribute("parentsForm") Parents parents, @ModelAttribute("userForm") User user, HttpServletRequest request) {
        User updateUser = userDao.getUserUsingEmail(user.getEmail());
        updateUser = userController.setFormAttributes(updateUser, user);
        userDao.update(updateUser);

        parents.setGender(updateUser.getGender());
        parents.setLastUpdatedBy(LoggedUserUtil.getUserId());
        parents.setLastUpdatedDate(CalendarUtil.getDate());
        parents.setEmail(updateUser.getEmail());

        parentsDao.update(parents);

        //hodController.addUpdate(hod);
        if(LoggedUserUtil.hasRole(RoleType.ROLE_PARENT))
            return new ModelAndView("redirect:updateprofile?token=success&");
        else
            return new ModelAndView("redirect:student/manage?token=success&schoolid="+request.getParameter("schoolid")+"&p="+request.getParameter("p")+"&rpp="+request.getParameter("rpp"));
    }

}
