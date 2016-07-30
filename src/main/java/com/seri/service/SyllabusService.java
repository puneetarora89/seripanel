package com.seri.service;

import java.util.Map;

import com.seri.web.model.Syllabus;

public interface SyllabusService {
	
	public void updateSyllabusRating(long syllabusId, int rate, long updatedBy);
	public void updateSyllabusRatingComments(long syllabusId, String comment, long updatedBy);
	public Map<String, Long> getParentStudentBySyllabus(long syllabusId);
	public Syllabus getParentSyllabus(long id);

}
