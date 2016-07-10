package com.seri.web.dto;

public class RatingTask {
	
	private int studenID;
	private String studentName;
	private long rateId;
	private int rate;
	private int outOf;
	private String content;
	
	public RatingTask(){};
	
	
	public RatingTask(int studenID, String studentName, long rateId, int rate, int outOf, String content) {
		super();
		this.studenID = studenID;
		this.studentName = studentName;
		this.rateId = rateId;
		this.rate = rate;
		this.outOf = outOf;
		this.content = content;
	}


	public int getStudenID() {
		return studenID;
	}
	public String getStudentName() {
		return studentName;
	}
	public long getRateId() {
		return rateId;
	}
	public int getRate() {
		return rate;
	}
	public int getOutOf() {
		return outOf;
	}
	public String getContent() {
		return content;
	}
	public void setStudenID(int studenID) {
		this.studenID = studenID;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public void setRateId(long rateId) {
		this.rateId = rateId;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public void setOutOf(int outOf) {
		this.outOf = outOf;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
