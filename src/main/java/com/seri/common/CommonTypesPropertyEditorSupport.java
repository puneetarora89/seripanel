package com.seri.common;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;

public class CommonTypesPropertyEditorSupport extends PropertyEditorSupport {
	
	@Override public void setAsText(final String text) throws IllegalArgumentException
    {
		if(StringUtils.isBlank(text))
			setValue(null);
		else
			setValue(CommonTypes.valueOf(text.trim()));
    }

}
