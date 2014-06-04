package com.hdd.hibernate.study.object.state;

import static org.junit.Assert.assertEquals;

import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.TransientObjectException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.School;
import com.hdd.hibernate.test.util.SchoolTestUtils;

public class NewObjectTest {
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
	
	@Test
	public void merge_new_object_inserted(){
		School sc2=new School();
		sc2.setName("xdfdf");
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		session.merge(sc2);   //Throw Exception for po already in session.
		session.getTransaction().commit();

		assertEquals(1,SchoolTestUtils.getSchoolFromDB().size());
	}
	
	@Test(expected=TransientObjectException.class)
	public void update_new_object_will_throw_exception(){
		School sc2=new School();
		sc2.setName("xdfdf");
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		session.update(sc2);   //Throw Exception for po already in session.
		session.getTransaction().commit();

		assertEquals(1,SchoolTestUtils.getSchoolFromDB().size());
	}
	
	
	@Test
	public void auto_sych_to_db_when_commit(){
		School sc=new School();
		sc.setName("anhui_sixiao");
		SchoolTestUtils.saveObjectToDB(sc);
		
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		School school_1 =(School) session.createQuery("from School").list().get(0);
		
		school_1.setName("hdd's home school");
		
		session.getTransaction().commit();

		
		School s = SchoolTestUtils.getSchoolFromDB_First();
		assertEquals(school_1.getName(), s.getName());
	}
	
	
	@After
	public void tearDown(){
		SchoolTestUtils.cleanTableSchool();
	}
}
