package com.seri.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.seri.service.notification.Notification;
import com.seri.service.notification.NotificationService;
import com.seri.web.utils.GlobalFunUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.seri.common.Gender;
import com.seri.common.GenderPropertyEditorSupport;
import com.seri.common.MyCustomNumberEditor;
import com.seri.common.RoleTypePropertyEditorSupport;
import com.seri.service.notification.RoleType;
import com.seri.web.dao.HodDao;
import com.seri.web.dao.ParentsDao;
import com.seri.web.dao.SchoolDao;
import com.seri.web.dao.StudentDao;
import com.seri.web.dao.SubjectDao;
import com.seri.web.dao.SyllabusDao;
import com.seri.web.dao.TeacherDao;
import com.seri.web.dao.daoImpl.HodDaoImpl;
import com.seri.web.dao.daoImpl.ParentsDaoImpl;
import com.seri.web.dao.daoImpl.SchoolDaoImpl;
import com.seri.web.dao.daoImpl.StudentDaoImpl;
import com.seri.web.dao.daoImpl.SubjectDaoImpl;
import com.seri.web.dao.daoImpl.SyllabusDaoImpl;
import com.seri.web.dao.daoImpl.TeacherDaoImpl;
import com.seri.web.dto.RatingTask;
import com.seri.web.model.Hod;
import com.seri.web.model.Parents;
import com.seri.web.model.School;
import com.seri.web.model.Student;
import com.seri.web.model.Subject;
import com.seri.web.model.Syllabus;
import com.seri.web.model.Teacher;
import com.seri.web.utils.CalendarUtil;
import com.seri.web.utils.LoggedUserUtil;

/**
 * Created by puneet on 29/05/16.
 */
@Controller
@RequestMapping(value = "syllabus")
public class SyllabusController {

    private SchoolDao schoolDao = new SchoolDaoImpl();
    private SyllabusDao syllabusDao = new SyllabusDaoImpl();
    private HodDao hodDao = new HodDaoImpl();
    private TeacherDao teacherDao = new TeacherDaoImpl();
    private StudentDao studentDao = new StudentDaoImpl();
    private SubjectDao subjectDao = new SubjectDaoImpl();
    private ParentsDao parentsDao = new ParentsDaoImpl();
    @Autowired
    private NotificationService notificationService;

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

    @RequestMapping(value = "/content**", method = RequestMethod.GET)
    public ModelAndView manageStudentPage(HttpServletRequest request) {
        try {
            ModelAndView model = new ModelAndView();
            int schoolId = 0;
            int standardId = 0;
            int subjectId = 0;
            int moduleId = 0;
            if (LoggedUserUtil.hasRole(RoleType.ROLE_TEACHER)) {
                Teacher teacher = teacherDao.getTeacherUsingLoginId(LoggedUserUtil.getUserId());
                schoolId = teacher.getTeacherSchoolId();
                String tempStandardId = teacher.getTeacherStandardId();
                tempStandardId = tempStandardId.substring(1, tempStandardId.length() - 1);
                if (request.getParameter("standardid") != null){
                    standardId = Integer.parseInt(request.getParameter("standardid"));
                }
                model.addObject("tempStandardId", tempStandardId);
            } else if (LoggedUserUtil.hasRole(RoleType.ROLE_HOD)) {

            } else if (LoggedUserUtil.hasRole(RoleType.ROLE_SCHOOL_ADMIN)) {
                if (request.getParameter("standardid") != null){
                    standardId = Integer.parseInt(request.getParameter("standardid"));
                }
                School school = schoolDao.getSchoolUsingPrincipal(LoggedUserUtil.getUserId());
                schoolId = school.getSchoolId();

            } else if (LoggedUserUtil.hasRole(RoleType.ROLE_SUP_ADMIN)) {
                if (request.getParameter("standardid") != null){
                    standardId = Integer.parseInt(request.getParameter("standardid"));
                }
                if (request.getParameter("schoolid") != null){
                    schoolId = Integer.parseInt(request.getParameter("schoolid"));
                }
            } else {
                throw new Exception();
            }

            if(request.getParameter("subjectid") != null){
                subjectId = Integer.parseInt(request.getParameter("subjectid"));
            }
            if(request.getParameter("moduleid") != null){
                moduleId = Integer.parseInt(request.getParameter("moduleid"));
            }


            Syllabus syllabus = null;
            if(schoolId>0 && standardId>0 && subjectId>0) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("syllabusDueDate", CalendarUtil.getDateInFormat("yyyy-MM-dd HH:mm:ss"));
                params.put("schoolId", String.valueOf(schoolId));
                params.put("standardId", String.valueOf(standardId));
                params.put("subjectId", String.valueOf(subjectId));
                params.put("moduleId", String.valueOf(moduleId));
                params.put("pId", "0");
                params.put("studentId", "0");
                syllabus = syllabusDao.getSyllabusBySyllabusFilters(params);
            }
            model.addObject("schoolId", schoolId);
            model.addObject("standardId", standardId);
            model.addObject("subjectId", subjectId);
            model.addObject("moduleId", moduleId);

            globalFunUtils.getNotification(model);

            model.addObject("syllabusForm", syllabus);
            if (syllabus == null)
                model.addObject("formAction", "/syllabus/addsyllabus");
            else
                model.addObject("formAction", "/syllabus/editsyllabus");
            model.setViewName("syllabus/add_update");
            return model;
        } catch (Exception e) {
        	e.printStackTrace();
            return new ModelAndView("redirect:standard/manage?token=invalidselection&");
        }
    }

    @RequestMapping(value = "/addsyllabus**", method = RequestMethod.POST)
    public ModelAndView addSyllabusPage(@ModelAttribute("syllabusForm") Syllabus syllabusForm) {
        ModelAndView model = new ModelAndView();
        if(LoggedUserUtil.hasRole(RoleType.ROLE_SCHOOL_ADMIN)){
            School school = schoolDao.getSchoolUsingPrincipal(LoggedUserUtil.getUserId());
            syllabusForm.setSchoolId(school.getSchoolId());
        }

        if(LoggedUserUtil.hasRole(RoleType.ROLE_TEACHER)){
            Teacher teacher = teacherDao.getTeacherUsingTeacherId(LoggedUserUtil.getUserId());
            syllabusForm.setSchoolId(teacher.getTeacherSchoolId());
        }

        syllabusForm.setCreatedBy(LoggedUserUtil.getUserId());
        syllabusForm.setCreatedDate(CalendarUtil.getDate());
        syllabusForm.setLastUpdatedBy(LoggedUserUtil.getUserId());
        syllabusForm.setLastUpdatedDate(CalendarUtil.getDate());
        syllabusForm.setTaskName("syllabus");
        globalFunUtils.getNotification(model);
        syllabusDao.create(syllabusForm);
        model.setViewName("redirect:content?token=success&schoolid="+syllabusForm.getSchoolId()+"&standardid="+syllabusForm.getStandardId()+"&subjectid="+syllabusForm.getSubjectId()+"&moduleid="+syllabusForm.getModuleId());
        return model;
    }

    @RequestMapping(value = "/editsyllabus**", method = RequestMethod.POST)
    public ModelAndView editSyllabusPage(@ModelAttribute("syllabusForm") Syllabus syllabusForm, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        if(LoggedUserUtil.hasRole(RoleType.ROLE_SCHOOL_ADMIN)){
            School school = schoolDao.getSchoolUsingPrincipal(LoggedUserUtil.getUserId());
            syllabusForm.setSchoolId(school.getSchoolId());
        }
        if(LoggedUserUtil.hasRole(RoleType.ROLE_TEACHER)){
            Teacher teacher = teacherDao.getTeacherUsingTeacherId(LoggedUserUtil.getUserId());
            syllabusForm.setSchoolId(teacher.getTeacherSchoolId());
        }
        globalFunUtils.getNotification(model);
        syllabusForm.setTaskId(Integer.parseInt(request.getParameter("taskId1")));
        syllabusForm.setLastUpdatedBy(LoggedUserUtil.getUserId());
        syllabusForm.setLastUpdatedDate(CalendarUtil.getDate());
        syllabusForm.setTaskName("syllabus");
        syllabusDao.update(syllabusForm);
        model.setViewName("redirect:content?token=success&schoolid="+syllabusForm.getSchoolId()+"&standardid="+syllabusForm.getStandardId()+"&subjectid="+syllabusForm.getSubjectId()+"&moduleid="+syllabusForm.getModuleId());
        return model;
    }

    @RequestMapping(value = "/view**", method = RequestMethod.GET)
    public ModelAndView viewSyllabusPage(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        globalFunUtils.getNotification(model);
        String schoolId="0", standardId="0", subjectId="0";
        if(LoggedUserUtil.hasAnyRole(RoleType.ROLE_SUP_ADMIN,RoleType.ROLE_SUB_ADMIN)) {
            if(request.getParameter("schoolid")==null || request.getParameter("standardid")==null || request.getParameter("subjectid")==null)
                model.setViewName("redirect:manage?token=invalidselection");
            schoolId=request.getParameter("schoolid");
            standardId=request.getParameter("standardid");
            subjectId=request.getParameter("subjectid");
        }else if(LoggedUserUtil.hasRole(RoleType.ROLE_STUDENT)) {
            if(request.getParameter("subjectid")==null)
                return new ModelAndView("redirect:dashboard?token=invalidselection");
            Student student = studentDao.getStudentUsingStudentLogin(LoggedUserUtil.getUserId());
            schoolId=String.valueOf(student.getStuSchoolId());
            standardId=String.valueOf(student.getStuStandardId());
            subjectId=request.getParameter("subjectid");
            model.addObject("studentId", student.getStudentId());
        } else if(LoggedUserUtil.hasRole(RoleType.ROLE_PARENT)) {
            if(request.getParameter("subjectid")==null)
                return new ModelAndView("redirect:dashboard?token=invalidselection");
            Parents parents = parentsDao.getProfileUsingLoginId(LoggedUserUtil.getUserId());
            int tempStudentId = parents.getStudentId();
            Student student = studentDao.getStudentUsingStudentId(tempStudentId);
            schoolId=String.valueOf(student.getStuSchoolId());
            standardId=String.valueOf(student.getStuStandardId());
            subjectId=request.getParameter("subjectid");
            model.addObject("studentId", student.getStudentId());
        } else {
            if(request.getParameter("standardid")==null || request.getParameter("subjectid")==null)
                model.setViewName("redirect:manage?token=invalidselection");
            standardId=request.getParameter("standardid");
            subjectId=request.getParameter("subjectid");

            if(LoggedUserUtil.hasRole(RoleType.ROLE_SCHOOL_ADMIN)) {
                School school = schoolDao.getSchoolUsingPrincipal(LoggedUserUtil.getUserId());
                schoolId=String.valueOf(school.getSchoolId());
            } else if(LoggedUserUtil.hasRole(RoleType.ROLE_HOD)) {
                Hod hod = hodDao.getHodByHodId(LoggedUserUtil.getUserId());
                schoolId=String.valueOf(hod.getHodSchoolId());
            }else if(LoggedUserUtil.hasRole(RoleType.ROLE_TEACHER)) {
                Teacher teacher = teacherDao.getTeacherUsingTeacherId(LoggedUserUtil.getUserId());
                schoolId=String.valueOf(teacher.getTeacherSchoolId());
            }
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("syllabusDueDate", CalendarUtil.getDateInFormat("yyyy-MM-dd HH:mm:ss"));
        params.put("schoolId", schoolId);
        params.put("standardId", standardId);
        params.put("subjectId", subjectId);
        params.put("pId", "0");
        params.put("studentId", "0");
        params.put("taskName","syllabus");
        List<Syllabus> syllabus = syllabusDao.getSyllabusListBySyllabusFilters(params);
        Subject subject = subjectDao.getSubjectBySubjectId(Integer.parseInt(subjectId));
        model.addObject("subjectForm", subject);
        model.addObject("syllabusList", syllabus);

        model.setViewName("syllabus/view");
        return model;
    }

    @RequestMapping(value = "/findlatestsyllabus/", method = RequestMethod.GET)
    public @ResponseBody
    String getLatestSyllabus(HttpServletRequest request) {
        JSONObject obj = new JSONObject();

        Map<String, String> params = new HashMap<String, String>();
        try {
            if (request.getParameter("schoolId") != null && request.getParameter("moduleId") != null &&request.getParameter("standardId") != null && request.getParameter("subjectId") != null){
                params.put("syllabusDueDate", CalendarUtil.getDateInFormat("yyyy-MM-dd HH:mm:ss"));
                params.put("schoolId", request.getParameter("schoolId"));
                params.put("standardId", request.getParameter("standardId"));
                params.put("subjectId", request.getParameter("subjectId"));
                params.put("moduleId", request.getParameter("moduleId"));
                params.put("pId", "0");
                params.put("studentId", "0");
                Syllabus syllabus = syllabusDao.getSyllabusBySyllabusFilters(params);
                if(syllabus != null) {
                    obj.put("result", true);
                    obj.put("id", syllabus.getTaskId());
                    obj.put("content", syllabus.getContent());
                    obj.put("dueDate", syllabus.getTaskDueDate());
                } else {
                    obj.put("result", false);
                }
            }

        } catch (Exception e) {
            obj.put("result", false);
        }
        return obj.toJSONString();
    }
    @RequestMapping(value="/getStudentWork", method=RequestMethod.GET)
    public @ResponseBody List<RatingTask> getStudentWork(long standardId, long subjectId){
    	List<RatingTask> ratingTaskList = syllabusDao.getWorkFromSyllabus(standardId, subjectId);
    	return ratingTaskList;
    }
}
