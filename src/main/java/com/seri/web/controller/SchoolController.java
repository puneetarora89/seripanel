package com.seri.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seri.service.notification.RoleType;
import com.seri.web.dao.DepartmentDao;
import com.seri.web.dao.SchoolDao;
import com.seri.web.dao.StandardDao;
import com.seri.web.dao.daoImpl.DepartmentDaoImpl;
import com.seri.web.dao.daoImpl.SchoolDaoImpl;
import com.seri.web.dao.daoImpl.StandardDaoImpl;
import com.seri.web.model.Department;
import com.seri.web.model.School;
import com.seri.web.model.Standard;
import com.seri.web.utils.CalendarUtil;
import com.seri.web.utils.GlobalFunUtils;
import com.seri.web.utils.LoggedUserUtil;

/**
 * Created by puneet on 23/04/16.
 */
@Controller
@RequestMapping(value = "school")
public class SchoolController {
    @Autowired
    private GlobalFunUtils globalFunUtils;

    private SchoolDao schoolDao = new SchoolDaoImpl();
    private DepartmentDao departmentDao = new DepartmentDaoImpl();
    private StandardDao standardDao = new StandardDaoImpl();

    @RequestMapping(value = "/addschool**", method = RequestMethod.GET)
    public ModelAndView addSchoolPage(@ModelAttribute("schoolForm") School schoolForm) {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);

        model.addObject("schoolForm", schoolForm);
        model.addObject("formAction", "add");
        model.setViewName("school/add_update");
        return model;
    }

    @RequestMapping(value = "/add**", method = RequestMethod.POST)
    public ModelAndView addPage(@ModelAttribute("schoolForm") School schoolForm) {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        Boolean errFlag = false;
        try {
            if (schoolDao.getSchoolUsingEmail(schoolForm.getSchoolEmail()) != null) {
                errFlag = true;
                model.addObject("emailErr", true);
                model.addObject("emailErrrrMsg", "School with same email id already exists");
            }
            if (schoolDao.getSchoolUsingContact(schoolForm.getSchoolContact()) != null) {
                errFlag = true;
                model.addObject("contactErr", true);
                model.addObject("contactErrrrMsg", "School with same Contact No already exists");
            }

            if (!errFlag) {
                schoolForm.setCreatedDate(CalendarUtil.getDate());
                schoolForm.setCreatedBy(LoggedUserUtil.getUserId());
                schoolForm.setLastUpdatedDate(CalendarUtil.getDate());
                schoolForm.setLastUpdatedBy(LoggedUserUtil.getUserId());
                errFlag = schoolDao.create(schoolForm);
            }
        } catch (Exception e){
            model.addObject("otherErr", true);
            model.addObject("otherErrrrMsg", "Data Error!!! Please verify and try again...");
            errFlag = true;
        }
        if (errFlag) {
            model.addObject("schoolForm", schoolForm);
            model.addObject("formAction", "add");
            model.setViewName("school/add_update");
            return model;
        }
        return new ModelAndView("redirect:addschool?addschool=success");
    }

    @RequestMapping(value = "/manage**", method = RequestMethod.GET)
    public ModelAndView manageSchoolPage() {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        List<School> schoolList = schoolDao.getAllSchool();
        List<Standard> standardList = standardDao.getPrimaryStandard();
        model.addObject("standardList", standardList);
        model.addObject("schoolList", schoolList);
        model.setViewName("school/manage_school");
        return model;
    }

    @RequestMapping(value = "/editschool**", method = RequestMethod.GET)
    public ModelAndView editSchoolPage(HttpServletRequest request) {
        try {
            School schoolForm = null;
            if (LoggedUserUtil.hasRole(RoleType.ROLE_SCHOOL_ADMIN)) {
                long schoolLogin = LoggedUserUtil.getUserId();
                schoolForm = schoolDao.getSchoolUsingPrincipal(schoolLogin);
            } else {
                int tempSchoolId = Integer.parseInt(request.getParameter("id"));
                schoolForm = schoolDao.getSchoolUsingId(tempSchoolId);
            }
            if(schoolForm == null)
                throw new Exception();
            ModelAndView model = new ModelAndView();
            globalFunUtils.getNotification(model);
            model.addObject("schoolForm", schoolForm);
            model.addObject("formAction", "edit");
            model.setViewName("school/add_update");
            return model;
        } catch(Exception e) {
            return new ModelAndView("redirect:addschool");
        }

    }

    @RequestMapping(value = "/edit**", method = RequestMethod.POST)
    public ModelAndView editPage(@ModelAttribute("schoolForm") School schoolForm) {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        Boolean errFlag = false;
        try {
            if (schoolDao.getSchoolNotUsingSameEmail(schoolForm.getSchoolEmail(), schoolForm.getSchoolId()) != null) {
                errFlag = true;
                model.addObject("emailErr", true);
                model.addObject("emailErrrrMsg", "School with same email id already exists");
            }
            if (schoolDao.getSchoolNotUsingSameSchoolContact(schoolForm.getSchoolContact(), schoolForm.getSchoolId()) != null) {
                errFlag = true;
                model.addObject("contactErr", true);
                model.addObject("contactErrrrMsg", "School with same Contact No already exists");
            }

            if (!errFlag) {
                schoolForm.setLastUpdatedDate(CalendarUtil.getDate());
                schoolForm.setLastUpdatedBy(LoggedUserUtil.getUserId());
                errFlag = schoolDao.update(schoolForm);
            }
        } catch (Exception e){
            model.addObject("otherErr", true);
            model.addObject("otherErrrrMsg", "Data Error!!! Please verify and try again...");
            errFlag = true;
        }
        if (errFlag) {
            model.addObject("schoolForm", schoolForm);
            model.addObject("formAction", "editschool");
            model.setViewName("school/add_update");
            return model;
        }
        return new ModelAndView("redirect:editschool?update=success&id="+schoolForm.getSchoolId());
    }

    @RequestMapping(value = "updateadmin", method = RequestMethod.GET)
    public ModelAndView updateSchoolAdmin(){
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/departmentlistctrl/", method = RequestMethod.GET)
    public @ResponseBody
    String getDepartmentListing(HttpServletRequest request) {
        String schoolId = request.getParameter("schoolId");
        String ctrl = "";
        /*if(schoolId != null)
            ctrl = getDepartmentListing();*/
        return "";
    }

    public String getDepartmentListing(String ctrlName, String ctrlId, String ctrlClass, String selectedDepartment) {
        List<Department> departmentList = departmentDao.getDepartmentList();
        String ctrl = "";

        ctrl = "<select name='"+ctrlName+"' id='"+ctrlId+"' class='"+ctrlClass+"'><option value='0'>Make a selection</option>";
        if(departmentList!=null) {
            for (Department department : departmentList) {
                ctrl += "<option value=" + department.getDepartmentId() + " " + (((department.getDepartmentId()) == Integer.parseInt(selectedDepartment)) ? "selected='selected'" : "") + ">" + department.getDepartmentName() + "</option>";
            }
        }
        ctrl+="</select>";
        return ctrl;
    }
}
