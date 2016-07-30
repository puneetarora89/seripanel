package com.seri.common;

public enum CommonTypes {
	SYLLABUS("Syllabus"),
	HOME_WORK("Home Work"),
	CLASS_WORK("Class Work"), 
	RATING("Rating");
	
	private final String title;
	
	private CommonTypes(final String title){
		this.title=title;
	}

	public String getTitle(){
		return this.title;
	}
}
