package com.seri.web.utils.tags;

import com.seri.web.dao.SchoolDao;
import com.seri.web.dao.daoImpl.SchoolDaoImpl;
import com.seri.web.model.School;
import com.seri.web.model.User;
import com.seri.web.utils.GlobalFunUtils;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Created by puneet on 25/04/16.
 */
public class SchoolListWithoutPrincipalTag extends SimpleTagSupport {

    private GlobalFunUtils globalFunUtils = new GlobalFunUtils();
    SchoolDao schoolDao = new SchoolDaoImpl();
    public void doTag() throws IOException {
        List<School> schoolList = schoolDao.getSchoolwithoutPrincipal();
        String selectSchoolCtrl = "<select name='schoolId'>";
        if(schoolList != null) {
            selectSchoolCtrl+= "<option value='0'>Make a selection</option>";
            for (School school : schoolList) {
                selectSchoolCtrl += "<option value='" + school.getSchoolId() + "'>" + school.getSchoolName() + "</option>";
            }
        } else {
            selectSchoolCtrl+= "<option value='0'>-No School Found without Principal-</option>";
        }
        selectSchoolCtrl += "</select>";
        JspWriter out = getJspContext().getOut();
        out.println(selectSchoolCtrl);
    }
}
