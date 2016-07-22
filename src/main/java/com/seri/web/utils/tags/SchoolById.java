package com.seri.web.utils.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.seri.web.dao.SchoolDao;
import com.seri.web.dao.daoImpl.SchoolDaoImpl;
import com.seri.web.model.School;

public class SchoolById extends SimpleTagSupport {

	private String schoolId;
	private String disabled;
	private String ctrlName;
	
	SchoolDao schoolDao = new SchoolDaoImpl();
	
    public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	

	public String getCtrlName() {
		return ctrlName;
	}

	public void setCtrlName(String ctrlName) {
		this.ctrlName = ctrlName;
	}

	public void doTag() throws IOException {
		int schoolnumber;
		School school = null;
        if(!StringUtils.isBlank(schoolId)){
        	try {
        		schoolnumber= Integer.valueOf(schoolId);
				school = schoolDao.getSchoolUsingId(schoolnumber);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        String selectSchoolCtrl = "";
        if(null != school){
        	selectSchoolCtrl = "<input type='text'";
        	if(!StringUtils.isBlank(ctrlName))
        		selectSchoolCtrl += " id='"+ctrlName+"' name='"+ctrlName+"'";
        	selectSchoolCtrl += " value='"+school.getSchoolName()+"'";
        	if("disabled".equals(disabled))
        		selectSchoolCtrl += " disabled='disabled'";
        	selectSchoolCtrl += "/>"; 
        }
        JspWriter out = getJspContext().getOut();
        out.println(selectSchoolCtrl);
    }
}
