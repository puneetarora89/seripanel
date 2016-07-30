package com.seri.web.utils.tags;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.seri.web.dao.SchoolDao;
import com.seri.web.dao.StandardDao;
import com.seri.web.dao.daoImpl.SchoolDaoImpl;
import com.seri.web.dao.daoImpl.StandardDaoImpl;
import com.seri.web.model.Standard;

/**
 * Created by puneet on 25/04/16.
 */
public class StandardListTag extends SimpleTagSupport {

    private String ctrlName;
    private String selectedStandard;
    private String multi;
    private String disabled;
    private List<String> standardIds;
    private StandardDao standardDao = new StandardDaoImpl();
    SchoolDao schoolDao = new SchoolDaoImpl();

    public void setCtrlName(String ctrlName) {
        this.ctrlName = ctrlName;
    }

    public void setSelectedStandard(String selectedStandard) {
        this.selectedStandard = selectedStandard;
    }

    public void setMulti(String multi) {
        this.multi = multi;
    }
    
    public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public void setStandardIds(String standardIds) {
    	if(!StringUtils.isBlank(standardIds))
    		this.standardIds = Arrays.asList(StringUtils.split(standardIds, ","));
	}

	public void doTag() throws IOException {
        if(selectedStandard==null || selectedStandard=="" || Integer.parseInt(selectedStandard)<1)
        {
            selectedStandard="0";
        }
        List<Standard> standardList = standardDao.getPrimaryStandard();
        String selectCtrl = "<select name='"+ctrlName+"' id='"+ctrlName+"'><option value='0'>Make a selection</option>";
        if(multi!=null && multi.equals("true"))
            selectCtrl = "<select name='"+ctrlName+"' id='"+ctrlName+"' multiple='multiple'>";
        if(standardList !=null  && standardList.size()>0) {
            for (Standard standard:standardList) {
            	if(standardIds != null){
            		if(standardIds.contains(String.valueOf(standard.getStandardId())))
            				selectCtrl += "<option value='"+standard.getStandardId()+"' "+((Integer.parseInt(selectedStandard)==standard.getStandardId())?"selected='selected'":"")+">"+standard.getStandardName()+"</option>";
            	}else{
            		selectCtrl += "<option value='"+standard.getStandardId()+"' "+((Integer.parseInt(selectedStandard)==standard.getStandardId())?"selected='selected'":"")+">"+standard.getStandardName()+"</option>";
            	}
            }

        }
        selectCtrl += "</select>";
        JspWriter out = getJspContext().getOut();
        out.println(selectCtrl);
    }
}
