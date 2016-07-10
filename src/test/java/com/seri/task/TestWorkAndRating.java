package com.seri.task;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.seri.web.dao.daoImpl.SyllabusDaoImpl;
import com.seri.web.dto.RatingTask;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:applicationContext.xml"})
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestWorkAndRating {
	
	SyllabusDaoImpl dao = new SyllabusDaoImpl();
	
	@Test
	public void getTaskRate(){
		List<RatingTask> task = dao.getWorkFromSyllabus(1, 1);
		String r  = new Gson().toJson(task);
		
	}

}
