package com.hdd.hibernate.study.basic;

import static org.junit.Assert.assertEquals;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.DataException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.School;
import com.hdd.hibernate.test.util.SchoolTestUtils;

public class EntityAnnotationTest {

	@Before
	public void setUp(){
	}
	
	@Test
	public void save(){
		School sc=new School();
		sc.setName("anhui_sixiao");
		SchoolTestUtils.saveObjectToDB(sc);
		
		assertEquals(1, SchoolTestUtils.getSchoolFromDB().size());
	}
	
	@Test(expected=PropertyValueException.class)
	public void raise_error_required_field_is_null(){
		School sc=new School();
		SchoolTestUtils.saveObjectToDB(sc);
	}
	
	
	@Test(expected=DataException.class)
	public void raise_error_length_exceed_max(){
		School sc=new School();
		sc.setName("012345678901234567891");
		SchoolTestUtils.saveObjectToDB(sc);
	
	}
	
	@After
	public void tearDown(){
		SchoolTestUtils.cleanTableSchool();
	}
}
