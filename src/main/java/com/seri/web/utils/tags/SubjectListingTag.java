package com.seri.web.utils.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.seri.web.dao.SchoolDao;
import com.seri.web.dao.SubjectDao;
import com.seri.web.dao.daoImpl.SchoolDaoImpl;
import com.seri.web.dao.daoImpl.SubjectDaoImpl;
import com.seri.web.utils.GlobalFunUtils;

/**
 * Created by puneet on 29/05/16.
 */
public class SubjectListingTag extends SimpleTagSupport {

    private String ctrlName;
    private String selectedSubject;
    private GlobalFunUtils globalFunUtils = new GlobalFunUtils();
    private SubjectDao subjectDao = new SubjectDaoImpl();
    SchoolDao schoolDao = new SchoolDaoImpl();

    public void setCtrlName(String ctrlName) {
        this.ctrlName = ctrlName;
    }

    public void setSelectedStandard(String selectedSubject) {
        this.selectedSubject = selectedSubject;
    }

    public void doTag() throws IOException {
//selectedStandard="1";
        //List<Subject> standardList = subjectDao.getSubjectByStandardId()
        String selectCtrl = "<select name='"+ctrlName+"' id='"+ctrlName+"'><option value='0'>Make a selection</option>";
        /*if(standardList.size()>0) {
            for (Standard standard:standardList) {
                selectCtrl += "<option value='"+standard.getStandardId()+"' "+((Integer.parseInt(selectedSubject)==standard.getStandardId())?"selected='selected'":"")+">"+standard.getStandardName()+"</option>";
            }

        }*/
        selectCtrl += "</select>";
        JspWriter out = getJspContext().getOut();
        out.println(selectCtrl);
    }

}
