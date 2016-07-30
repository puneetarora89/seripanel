package com.seri.service.rating;

import java.util.List;
import java.util.Map;

public interface RatingService {
	
	public Rating create(Rating rating);
	public Rating update(Rating rating);
	public int update(long id, int rate, long updateBy);
	public List<Rating> getRatingForEntity(long id);
	public List<Rating> getRatingWithFilter(Map<String, Object> filter);
	public int deleteRatingForEntity(long id);
	public int deleteRatingWithFilter(Map<String, Object> filter);
}
