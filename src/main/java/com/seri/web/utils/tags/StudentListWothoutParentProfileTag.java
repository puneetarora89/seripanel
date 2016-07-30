package com.seri.web.utils.tags;

import com.seri.web.dao.StudentDao;
import com.seri.web.dao.daoImpl.StudentDaoImpl;
import com.seri.web.dao.daoImpl.UserDaoImpl;
import com.seri.web.model.School;
import com.seri.web.model.Student;
import com.seri.web.model.User;
import com.seri.web.utils.GlobalFunUtils;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Created by puneet on 13/04/16.
 */
public class StudentListWothoutParentProfileTag extends SimpleTagSupport {

    private String ctrlName;
    private StudentDao studentDao = new StudentDaoImpl();

    public void setCtrlName(String ctrlName) {
        this.ctrlName = ctrlName;
    }

    public void doTag() throws IOException {
        List<Student> studentList = studentDao.getStudentWithoutParentProfile();
        String selectCtrl = "<select name='"+ctrlName+"' id='"+ctrlName+"' >";
        if(studentList != null) {
            selectCtrl+= "<option value='0'>Make a selection</option>";
            for (Student student : studentList) {
                selectCtrl += "<option value='" + student.getStudentId() + "'>" + student.getfName() +" "+ student.getlName() + " ("+student.getUserId()+") </option>";
            }
        } else {
            selectCtrl+= "<option value='0'>-No Student Found without Parent Profile-</option>";
        }
        selectCtrl += "</select>";
        JspWriter out = getJspContext().getOut();
        out.println(selectCtrl);
    }
}
