package com.seri.web.utils.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.seri.web.dao.SchoolDao;
import com.seri.web.dao.StandardDao;
import com.seri.web.dao.SubjectDao;
import com.seri.web.dao.daoImpl.SchoolDaoImpl;
import com.seri.web.dao.daoImpl.StandardDaoImpl;
import com.seri.web.dao.daoImpl.SubjectDaoImpl;
import com.seri.web.model.Standard;
import com.seri.web.model.Subject;
import com.seri.web.utils.GlobalFunUtils;

/**
 * Created by puneet on 04/06/16.
 */
public class SchoolStandardSubjectSelectTag extends SimpleTagSupport {

    private String ctrlName;
    private String ctrlClass;
    private String ctrlId;
    private String selectedStandard;
    private String multi;
    private GlobalFunUtils globalFunUtils = new GlobalFunUtils();
    private StandardDao standardDao = new StandardDaoImpl();
    SchoolDao schoolDao = new SchoolDaoImpl();
    private SubjectDao subjectDao = new SubjectDaoImpl();

    public void setCtrlName(String ctrlName) {
        this.ctrlName = ctrlName;
    }

    public void setCtrlClass(String ctrlClass) {
        this.ctrlClass = ctrlClass;
    }

    public void setCtrlId(String ctrlId) {
        this.ctrlId = ctrlId;
    }

    public void setSelectedStandard(String selectedStandard) {
        this.selectedStandard = selectedStandard;
    }

    public void setMulti(String multi) {
        this.multi = multi;
    }

    public void doTag() throws IOException {
//selectedStandard="1";
        if(selectedStandard==null || selectedStandard=="" || Integer.parseInt(selectedStandard)<1)
        {
            selectedStandard="0";
        }
        if(ctrlName==null)
            ctrlName="schoolStandardSubjectSelect";
        if(ctrlClass==null)
            ctrlClass=ctrlName;
        if(ctrlId==null)
            ctrlId=ctrlClass;

        List<Standard> standardList = standardDao.getPrimaryStandard();
        String selectCtrl = "<select name='"+ctrlName+"' class='"+ctrlClass+"' id='"+ctrlId+"'><option value='0'>Make a selection</option>";
        List<Subject> subjectList =null;
        if(multi!=null && multi.equals("true"));
            selectCtrl = "<select name='"+ctrlName+"' size='5' id='"+ctrlId+"' class='"+ctrlClass+"' multiple='multiple'>";
        if(standardList.size()>0) {
            for (Standard standard:standardList) {
                selectCtrl += "<option disabled='disabled' value='"+standard.getStandardId()+"'> -- STANDARD NAME :: "+standard.getStandardName()+" -- </option>";
                subjectList = subjectDao.getSubjectByStandardId(standard.getStandardId());
                if(subjectList != null) {
                    for (Subject subject:subjectList) {
                        selectCtrl+= "<option value='"+subject.getSubjectId()+"' " + ((subject.getSubjectId()==Integer.parseInt(selectedStandard)) ? "selected='selected'" : "") +">"+subject.getSubjectName()+"</option>";
                    }

                }
            }

        }
        selectCtrl += "</select>";
        JspWriter out = getJspContext().getOut();
        out.println(selectCtrl);
    }
}
