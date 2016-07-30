package com.seri.web.dao;

import com.seri.web.dto.RatingTask;
import com.seri.web.model.Syllabus;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by puneet on 29/05/16.
 */
public interface SyllabusDao {

    public Boolean create(Syllabus syllabus);

    public Boolean update(Syllabus syllabus);

    public Syllabus getSyllabusBySyllabusId(long id);

    public List<Syllabus> getSyllabusBySyllabusSchoolId(int id);

    public List<Syllabus> getAllSyllabusBySyllabusStandardId(int id);

    public Syllabus getRecentSyllabusBySyllabusStandardId(int id);

    public List<Syllabus> getAllSyllabusBySyllabusStudentId(int id);

    public Syllabus getRecentSyllabusBySyllabusStudentId(int id);

    public Syllabus getSyllabusBySyllabusFilters(Map<String, String> params);

    public List<Syllabus> getSyllabusListBySyllabusFilters(Map<String, String> params);
    
    public List<RatingTask> getWorkFromSyllabus(long standardId, long subjectId, Date taskDate);
    
    public Syllabus getParentSyllabus(long id);
    
    public Map<String, Long> getParentStudentBySyllabus(long syllabusId);
}
