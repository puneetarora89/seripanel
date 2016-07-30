package com.seri.service.rating;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ratingService")
public class RatingServiceImpl implements RatingService{
	
	@Autowired
	private RatingDao ratingDao;

	@Override
	public Rating create(Rating rating) {
		Rating rate = ratingDao.save(rating);
		return rate;
	}

	@Override
	public int update(long id, int rate, long updatedBy) {
		int records = ratingDao.update(id, rate, updatedBy);
		return records;
	}
	@Override
	public Rating update(Rating rating) {
		Rating updatedRating = ratingDao.update(rating);
		return updatedRating;
	}

	@Override
	public List<Rating> getRatingForEntity(long id) {
		List<Rating> ratingList = ratingDao.getRatingForEntity(id);
		return ratingList;
	}

	@Override
	public List<Rating> getRatingWithFilter(Map<String, Object> filter) {
		List<Rating> ratingList = ratingDao.getRatingWithFilter(filter);
		return ratingList;
	}

	@Override
	public int deleteRatingForEntity(long id) {
		int records = ratingDao.deleteRatingForEntity(id);
		return records;
	}

	@Override
	public int deleteRatingWithFilter(Map<String, Object> filter) {
		int records = ratingDao.deleteRatingWithFilter(filter);
		return records;
	}


}
