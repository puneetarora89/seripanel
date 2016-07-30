package com.seri.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.seri.common.CommonTypes;
import com.seri.service.SyllabusService;
import com.seri.service.notification.NotificatiobServiceAdaptor;
import com.seri.web.dao.RatingDao;
import com.seri.web.dao.daoImpl.RatingDaoImpl;
import com.seri.web.model.Rating;
import com.seri.web.model.Syllabus;
import com.seri.web.utils.CalendarUtil;
import com.seri.web.utils.LoggedUserUtil;

/**
 * Created by puneet on 08/06/16.
 */
@Controller
@RequestMapping(value = "rating")
public class RatingController {
	
    private RatingDao ratingDao = new RatingDaoImpl();
    
    @Autowired
    private SyllabusService syllabusService;

    @RequestMapping(value = "/updateRating/{syllabusId}/{rate}", method = RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    public void updateRating(@PathVariable long syllabusId, @PathVariable int rate) {
        try {
        	syllabusService.updateSyllabusRating(syllabusId, rate, LoggedUserUtil.getUserId());
        	Syllabus syllabus = syllabusService.getParentSyllabus(syllabusId);
        	Map<String, Long> parentStudentIds = syllabusService.getParentStudentBySyllabus(syllabusId);
        	String  desc  =rate+" rating is given for work given on "+CalendarUtil.getSystemDateFormat().format(syllabus.getCreatedDate());
        	long studend = parentStudentIds.get("student");
        	long parent = parentStudentIds.get("parent");
        	if(studend != 0)
        		NotificatiobServiceAdaptor.createSingleNotification(CommonTypes.RATING, studend, 2, desc);
        		
        	if(parent != 0)
        		NotificatiobServiceAdaptor.createSingleNotification(CommonTypes.RATING, parent, 2, desc);
        } catch (Exception e){
        }
    }
    
    @RequestMapping(value = "/updateRating/{syllabusId}/comment/{comment}", method = RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    public void updateRating(@PathVariable long syllabusId, @PathVariable String comment) {
        try {
        	syllabusService.updateSyllabusRatingComments(syllabusId, comment, LoggedUserUtil.getUserId());
        } catch (Exception e){
        }
    }


    @RequestMapping(value = "/addrating/", method = RequestMethod.GET)
    public @ResponseBody
    String getTeacherListing(@ModelAttribute("rating") Rating rating, HttpServletRequest request) {
        Boolean errFlag = false;
        try {
            Map<String, Object> params = new HashMap<String, Object>();

            rating.setCreatedBy(LoggedUserUtil.getUserId());
            rating.setCreatedDate(CalendarUtil.getDate());
            rating.setLastUpdatedBy(LoggedUserUtil.getUserId());
            rating.setLastUpdatedDate(CalendarUtil.getDate());

            params.put("studentId", rating.getStudentId());
            params.put("entityId", rating.getEntityId());
            params.put("entityName", rating.getEntityName());

            List<Rating> ratingList = ratingDao.getRatingRecUsingFilters(params, false);
            if(ratingList.size()==0) {
                ratingDao.create(rating);
            } else {
                rating.setRatingId(ratingList.get(0).getRatingId());
                ratingDao.update(rating);
            }
        } catch (Exception e){
            errFlag = true;
        }

        return "true";
    }
}
