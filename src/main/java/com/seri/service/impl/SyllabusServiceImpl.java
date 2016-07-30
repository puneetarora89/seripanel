package com.seri.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seri.service.SyllabusService;
import com.seri.service.rating.Rating;
import com.seri.service.rating.RatingService;
import com.seri.web.dao.SyllabusDao;
import com.seri.web.model.Syllabus;
import com.seri.web.utils.CalendarUtil;

@Service("syllabusService")
public class SyllabusServiceImpl implements SyllabusService {

	@Autowired
	private RatingService ratingService;
	@Autowired
	private SyllabusDao syllabusDao;

	static Logger logger = LoggerFactory.getLogger(SyllabusServiceImpl.class);

	public void updateSyllabusRating(long syllabusId, int rate, long updatedBy) {
		logger.info("Updateing rating for syllabus {} to {}, updated/created by {}", syllabusId, rate, updatedBy);
		List<Rating> ratingList = ratingService.getRatingForEntity(syllabusId);
		if (null != ratingList && ratingList.size() == 1) {
			Rating rating = ratingList.get(0);
			rating.setRate(rate);
			rating.setLastUpdatedBy(updatedBy);
			rating.setLastUpdatedDate(CalendarUtil.getDate());
			ratingService.update(rating);
			logger.info("Rating {} update to {} rate for syllabus {}", rating.getId(), rate, syllabusId);
		} else if (null == ratingList || ratingList.size() == 0) {
			logger.info("No rating found for syllabus {}, creating new Rating", syllabusId);
			Syllabus syllabus = syllabusDao.getSyllabusBySyllabusId((int) syllabusId);
			Rating rating = new Rating();
			rating.setRate(rate);
			rating.setCreatedDate(CalendarUtil.getDate());
			rating.setEntityId(syllabusId);
			rating.setOutof(5);
			rating.setRatingType(syllabus.getTaskName());
			rating.setCreatedBy(updatedBy);
			ratingService.create(rating);
			logger.info("New Rating {} created for syllabus {}", rating.getId(), syllabusId);
		}
	}

	public void updateSyllabusRatingComments(long syllabusId, String comment, long updatedBy) {
		List<Rating> ratingList = ratingService.getRatingForEntity(syllabusId);
		if (null != ratingList && ratingList.size() == 1) {
			Rating rating = ratingList.get(0);
			rating.setComment(comment);
			rating.setLastUpdatedBy(updatedBy);
			rating.setLastUpdatedDate(CalendarUtil.getDate());
			ratingService.update(rating);
		}
	}

	@Override
	public Map<String, Long> getParentStudentBySyllabus(long syllabusId) {
		Map<String, Long> parentStudentIds = syllabusDao.getParentStudentBySyllabus(syllabusId);
		return parentStudentIds;
	}

	@Override
	public Syllabus getParentSyllabus(long id) {
		Syllabus syllabus = syllabusDao.getParentSyllabus(id);
		return syllabus;
	}
	
	

}
