package com.hdd.hibernate.study.basic;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.School;
import com.hdd.hibernate.test.util.HibernateTest;
import com.hdd.hibernate.test.util.SchoolTestUtils;

//@TestCase
public class SchoolTest extends HibernateTest{

	@Before
	public void setUp(){
	}
	
	@Test
	public void School_is_work(){
		School sc=new School();
		sc.setName("hudongdong");
		Session session = SchoolTestUtils.saveObjectToDB(sc);
		assertEquals(1, SchoolTestUtils.getSchoolFromDB().size());
		printSQLs(session);
		assertSQLNum(2, session); // one select for primary key, one for insert
	}

	@Test
	public void auto_sych_to_db_when_commit(){
		School sc=new School();
		sc.setName("hudongdong");
		SchoolTestUtils.saveObjectToDB(sc);
		
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		School school_1 =(School) session.createQuery(School.FROM_SCHOOL).list().get(0);
		
		school_1.setName("hdd's home school");
		
		session.getTransaction().commit();

		
		School s = SchoolTestUtils.getSchoolFromDB_First();
		
		assertEquals(school_1.getName(), s.getName());
		printSQLs(session);
		
		assertSQLNum(2,session);
	}
	
	@After
	public void tearDown(){
		SchoolTestUtils.cleanTableSchool();
	}
}
