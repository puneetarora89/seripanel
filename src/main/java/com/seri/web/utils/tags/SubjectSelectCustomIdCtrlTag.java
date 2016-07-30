package com.seri.web.utils.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.seri.web.dao.StandardDao;
import com.seri.web.dao.SubjectDao;
import com.seri.web.dao.daoImpl.StandardDaoImpl;
import com.seri.web.dao.daoImpl.SubjectDaoImpl;
import com.seri.web.model.Standard;
import com.seri.web.model.Subject;
import com.seri.web.utils.GlobalFunUtils;

/**
 * Created by puneet on 05/06/16.
 */
public class SubjectSelectCustomIdCtrlTag extends SimpleTagSupport {
    private GlobalFunUtils globalFunUtils = new GlobalFunUtils();
    private SubjectDao subjectDao = new SubjectDaoImpl();
    private StandardDao standardDao = new StandardDaoImpl();
    private String ids;
    private String ctrlName;
    private String ctrlId;
    private String ctrlClass;
    private String selectedSubject;
    private String multi;

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setCtrlName(String ctrlName) {
        this.ctrlName = ctrlName;
    }

    public void setCtrlId(String ctrlId) {
        this.ctrlId = ctrlId;
    }

    public void setCtrlClass(String ctrlClass) {
        this.ctrlClass = ctrlClass;
    }

    public void setSelectedSubject(String selectedSubject) {
        this.selectedSubject = selectedSubject;
    }

    public void setMulti(String multi) {
        this.multi = multi;
    }

    public void doTag() throws IOException {
        if(ctrlName == null)
            ctrlName="customSubjectStandardCtrl";
        if(ctrlClass==null)
            ctrlClass=ctrlName;
        if(ctrlId==null)
            ctrlId=ctrlName;
        if(multi==null)
            multi="false";
        String selectCtrl = "<select name='"+ctrlName+"' id='"+ctrlId+"' class='"+ctrlClass+"'><option value='0'>Make a selection</option>";
        try {
            if(multi=="true")
                selectCtrl = "<select name='"+ctrlName+"' id='"+ctrlId+"' class='"+ctrlClass+"' multiple='multiple'><option value='0'>Make a selection</option>";
            List<Subject> subjectList = subjectDao.getSubjectListUsingInId(ids);
            List<Integer> tempList = new ArrayList<Integer>();
            if(subjectList.size()>0){
                Standard standard = null;
                int i=0;
                for (Subject subject: subjectList) {
                    if(tempList==null || !tempList.contains(subject.getStandardId())) {
                        tempList.add(subject.getStandardId());
                        standard = standardDao.getStandardById(subject.getStandardId());
                        selectCtrl+="<option value='"+standard.getStandardId()+"' disabled='disabled'> -- Standard :: "+standard.getStandardName()+" -- </option>";
                    }
                    selectCtrl+="<option value='"+subject.getSubjectId()+"' parentid='"+subject.getStandardId()+"' data-standard-id='"+subject.getStandardId()+"' "+((Integer.parseInt(selectedSubject)==subject.getSubjectId())?"selected='selected'":"")+">"+subject.getSubjectName()+"</option>";
                }
            }
        } catch (Exception e){

        }
        selectCtrl+="</select>";
        JspWriter out = getJspContext().getOut();
        out.println(selectCtrl);
    }
}
