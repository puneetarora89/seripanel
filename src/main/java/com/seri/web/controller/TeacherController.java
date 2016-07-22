package com.seri.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.seri.web.utils.GlobalFunUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seri.service.notification.RoleType;
import com.seri.web.dao.HodDao;
import com.seri.web.dao.SchoolDao;
import com.seri.web.dao.UserDao;
import com.seri.web.dao.daoImpl.HodDaoImpl;
import com.seri.web.dao.daoImpl.SchoolDaoImpl;
import com.seri.web.dao.daoImpl.TeacherDaoImpl;
import com.seri.web.dao.daoImpl.UserDaoImpl;
import com.seri.web.model.Hod;
import com.seri.web.model.School;
import com.seri.web.model.Teacher;
import com.seri.web.model.User;
import com.seri.web.utils.CalendarUtil;
import com.seri.web.utils.LoggedUserUtil;

/**
 * Created by puneet on 10/04/16.
 */
@Controller
@RequestMapping(value = "teacher")
public class TeacherController {

    private TeacherDaoImpl teacherDao = new TeacherDaoImpl();
    private SchoolDao schoolDao = new SchoolDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private HodDao hodDao = new HodDaoImpl();
    private StandardController standardController = new StandardController();

    @Autowired
    private GlobalFunUtils globalFunUtils;

    @RequestMapping(value = "/dashboard**", method = RequestMethod.GET)
    public ModelAndView dashboardPage(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();

        globalFunUtils.getNotification(model);
        /*Student student = studentDao.getStudentUsingStudentLogin(sessUser.getLogin());
        List<Subject> subjectList = subjectDao.getSubjectByStandardId(student.getStuStandardId());
        model.addObject("subjectList", subjectList);*/
        model.setViewName("teacher/dashboard");
        return model;
    }

    @RequestMapping(value = "/manage**", method = RequestMethod.GET)
    public ModelAndView manage(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        model.setViewName("teacher/manage_teacher");
        return model;
    }

    @RequestMapping(value = "/teacherlisting/", method = RequestMethod.GET)
    public @ResponseBody
    String getTeacherListing(HttpServletRequest request) {
        int rpp = 10;
        int p=1;
        int offset = 0;
        String retHtml = "";

        List<Teacher> teacherList = null;
        List<Teacher> countTeacherList = null;
        Map<String, Integer> params = new HashMap<String, Integer>();

        if(request.getParameter("rpp") != null)
            rpp = Integer.valueOf(request.getParameter("rpp"));

        if(request.getParameter("p")!=null) {
            p = Integer.valueOf(request.getParameter("p"));
            offset = rpp*(p-1);
        }

        if(request.getParameter("deptId")!=null && request.getParameter("deptId")!="") {
            params.put("departmentId", Integer.valueOf(request.getParameter("deptId")));
        }

        params.put("offset", offset);
        params.put("rpp", rpp);


        if(LoggedUserUtil.hasRole(RoleType.ROLE_SUP_ADMIN)) {
            if(request.getParameter("schoolid") != null)
                params.put("schoolid", Integer.valueOf(request.getParameter("schoolid")));

        } else if(LoggedUserUtil.hasRole(RoleType.ROLE_SCHOOL_ADMIN)) {
            School school = schoolDao.getSchoolUsingPrincipal(LoggedUserUtil.getUserId());
            params.put("schoolid", school.getSchoolId());
        } else if(LoggedUserUtil.hasRole(RoleType.ROLE_HOD)) {
            Hod hod = hodDao.getHodByHodId(LoggedUserUtil.getUserId());
            params.put("schoolid", hod.getHodSchoolId());
            params.put("departmentId", hod.getHodDepartmentId());
        }

        teacherList = teacherDao.getTeacherListUsingSchoolId(params, false);
        countTeacherList = teacherDao.getTeacherListUsingSchoolId(params, true);

        if(teacherList != null && teacherList.size()>0){
            Map<Integer, String> standardListMap = standardController.getStandardMap();
            retHtml = "<table class=\"table table-bordered responsive\">\n" +
                    "                <thead>\n" +
                    "                <tr>\n" +
                    "                    <th></th>\n" +
                    "                    <th>Teacher Name</th>\n" +
                    "                    <th>Email (LoginId)</th>\n" +
                    "                    <th>Standard</th>\n" +
                    "                    <th>Address</th>\n" +
                    "                    <th>Specialization</th>\n" +
                    "                    <th>&nbsp;</th>\n" +
                    "                </tr>\n" +
                    "                </thead>\n" +
                    "                <tbody>\n";

            for (Teacher teacher:teacherList) {
                retHtml+="<tr><td></td>";
                retHtml+="<td>"+teacher.getfName()+" "+teacher.getlName() + " <em>( "+teacher.gettMobNo()+" )</em></td>";
                retHtml+="<td>"+teacher.getEmail()+"</td>";
                retHtml+="<td>"+standardListMap.get(teacher.getTeacherStandardId())+"</td>";
                retHtml+="<td>"+teacher.gettHomeAdress()+"</td>";
                retHtml+="<td>"+teacher.gettSpecialization()+"</td>";
                retHtml+="<td>"+"<a href=\"/teacher/update?id="+teacher.getTeacherId()+"&p="+p+"&rpp="+rpp+"\" class=\"editSchool\"><span class=\"icon-edit\"></span></a></td>";
                retHtml+="</tr>";
            }

            retHtml+="</tbody>\n" +
                    "</table>";

            if(countTeacherList.size() > teacherList.size()){
                retHtml+= "<ul class=\"pager\" data-p='"+p+"'>\n";
                if(p==1)
                    retHtml+="<li class='disabled'><a href=\"#\">Previous</a></li>\n";
                else
                    retHtml+="<li class=''><a href=\"#\" data-p='"+(p-1)+"'>Previous</a></li>\n";

                if(p < countTeacherList.size()/rpp)
                    retHtml+="<li><a href=\"javascript:void(0)\" data-p='"+(p+1)+"'>Next</a></li></ul>";
                else
                    retHtml+="<li class='disabled'><a href=\"javascript:void(0)\" data-p='"+(p+1)+"'>Next</a></li></ul>";


            }
        }
        return retHtml;
    }

    @RequestMapping(value = "/update**", method = RequestMethod.GET)
    public ModelAndView updateTeacher(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        if(request.getParameter("id") == null)
            return new ModelAndView("redirect:manage_teacher");

        int id = Integer.parseInt(request.getParameter("id"));

        Teacher teacherProfile = teacherDao.getTeacherUsingTeacherId(id);
        if(teacherProfile==null)
            return new ModelAndView("redirect:manage_teacher");

        if(LoggedUserUtil.hasRole(RoleType.ROLE_SCHOOL_ADMIN)) {
            School tempSchool = schoolDao.getSchoolUsingPrincipal(LoggedUserUtil.getUserId());
            if(tempSchool.getSchoolId()!=teacherProfile.getTeacherSchoolId()) {
                return new ModelAndView("redirect:manage_teacher");
            }
        } else if(LoggedUserUtil.hasRole(RoleType.ROLE_HOD)) {
            Hod tempHod = hodDao.getHodByHodId(LoggedUserUtil.getUserId());
            if(tempHod.getHodSchoolId()!=teacherProfile.getTeacherSchoolId() || tempHod.getHodDepartmentId()!=teacherProfile.gettDeptName()) {
                return new ModelAndView("redirect:manage_teacher");
            }
        }

        User userForm = userDao.getUserUsingEmail(teacherProfile.getEmail());

        model.addObject("userForm", userForm);
        model.addObject("teacher", teacherProfile);
        model.addObject("formAction", "teacherUpdate");

        model.setViewName("teacher/admin_update");
        return model;
    }

    @RequestMapping(value = "/viewprofile**", method = RequestMethod.GET)
    public ModelAndView viewProfile(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        Teacher teacherProfile = null;
        int teacherId = 0;
        if(LoggedUserUtil.hasRole(RoleType.ROLE_TEACHER)) {
            teacherProfile = teacherDao.getTeacherUsingTeacherId(LoggedUserUtil.getUserId());
        } else {
            if(request.getParameter("id") == null)
                return new ModelAndView("redirect:manage_teacher");

            teacherId = Integer.parseInt(request.getParameter("id"));
            teacherProfile = teacherDao.getTeacherUsingTeacherId(teacherId);
        }

        if(teacherProfile==null)
            return new ModelAndView("redirect:manage_teacher");

        model.addObject("teacher", teacherProfile);

        model.setViewName("teacher/viewProfile");
        return model;
    }

    public Boolean addUpdate(Teacher teacher, User sessUser) {
        Boolean flag = false;

        teacher.settLastUpdateBy(LoggedUserUtil.getUserId());
        teacher.settLastUpdateDate(CalendarUtil.getDate());
        Teacher tempDetails = teacherDao.getTeacherUsingLoginId(teacher.gettLoginId());
        if(tempDetails != null)
        {
            teacher.setEmail(tempDetails.getEmail());
            //teacher.setTeacherStandardId(tempDetails.getTeacherStandardId());
            teacher.setTeacherSchoolId(tempDetails.getTeacherSchoolId());
            teacher.setTeacherId(tempDetails.getTeacherId());
            teacher.setGender(tempDetails.getGender());
            teacherDao.update(teacher);
        } else {
            teacher.settCreatedDate(CalendarUtil.getDate());
            teacher.settCreatedBy(LoggedUserUtil.getUserId());
            teacherDao.create(teacher);
        }
        return flag;
    }
}
