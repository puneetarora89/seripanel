package com.seri.web.utils.tags;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.seri.web.utils.CalendarUtil;

public class SystemDateFormatter extends SimpleTagSupport{
	
	private String var;
	
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspContext cxt = getJspContext();
		String dateFormat =  CalendarUtil.getSystemDateFormat().toPattern();
		JspWriter out = getJspContext().getOut();
		if(!StringUtils.isBlank(var))
			cxt.setAttribute(var, dateFormat,1);
		else
			out.println(dateFormat);
	}
}
