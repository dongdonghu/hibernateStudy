package com.hdd.hibernate.study.object.state;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.School;
import com.hdd.hibernate.test.util.SchoolTestUtils;

public class DetachObjectTest {

	private School detachedSchool;

	@Before
	public void setUp(){
		School sc=new School();
		sc.setName("anhui_sixiao");
		SchoolTestUtils.saveObjectToDB(sc);
		detachedSchool=SchoolTestUtils.getSchoolFromDB_First();
	}
	
	@Test
	public void save_detached_object_another_new_object_created(){
		assertEquals(1,SchoolTestUtils.getSchoolFromDB().size());
		
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		
		session.save(detachedSchool);
		
		session.getTransaction().commit();
		assertEquals(2,SchoolTestUtils.getSchoolFromDB().size());
	}
	
	@Test
	public void saveOrUpdate_detached_object_no_new_object_created(){
		assertEquals(1,SchoolTestUtils.getSchoolFromDB().size());
		
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		
		session.saveOrUpdate(detachedSchool);
		
		session.getTransaction().commit();
		assertEquals(1,SchoolTestUtils.getSchoolFromDB().size());
	}
	
	@Test(expected=NonUniqueObjectException.class)
	public void saveOrUpdate_detached_object_with_one_po_in_session_already_will_throw_exception(){
		Session session = HibernateUtils.getSession();
		System.out.println(detachedSchool.getId());
		session.beginTransaction();
		List res = session.createQuery("from School").list();
		session.saveOrUpdate(detachedSchool);   //Throw Exception for po already in session.
		session.getTransaction().commit();

		assertEquals(1,SchoolTestUtils.getSchoolFromDB().size());
	}
	
	@Test(expected=NonUniqueObjectException.class)
	public void update_detached_object_with_one_po_in_session_will_throw_exception(){
		Session session = HibernateUtils.getSession();
		System.out.println(detachedSchool.getId());
		session.beginTransaction();
		List res = session.createQuery("from School").list();
		session.update(detachedSchool);   //Throw Exception for po already in session.
		session.getTransaction().commit();

		assertEquals(1,SchoolTestUtils.getSchoolFromDB().size());
	}
	
	@Test
	public void merge_detached_object_with_one_po_in_session_works_fine(){
		detachedSchool.setName("xxxjj");
		Session session = HibernateUtils.getSession();
		
		session.beginTransaction();
		List res = session.createQuery("from School").list();
		session.merge(detachedSchool);   //Throw Exception for po already in session.
		session.getTransaction().commit();

		assertEquals(1,SchoolTestUtils.getSchoolFromDB().size());
		assertEquals("xxxjj", SchoolTestUtils.getSchoolFromDB_First().getName());
	}
	
	@Test
	public void merge_detached_object_with_no_po_in_session_works_fine(){
		detachedSchool.setName("xxxjj");
		Session session = HibernateUtils.getSession();
		System.out.println(detachedSchool.getId());
		session.beginTransaction();
//		List res = session.createQuery("from School").list();
		session.merge(detachedSchool);   //Throw Exception for po already in session.
		session.getTransaction().commit();

		assertEquals(1,SchoolTestUtils.getSchoolFromDB().size());
		assertEquals("xxxjj", SchoolTestUtils.getSchoolFromDB_First().getName());
	}
	
	
	@Test
	public void update_detached_object_no_new_object_created(){
		
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		session.update(detachedSchool);
		session.getTransaction().commit();
		
		assertEquals(1,SchoolTestUtils.getSchoolFromDB().size());
	}
	
	@Test
	public void update_detached_object_no_new_object_created_and_db_sych_if_not_matched_db(){
		
		detachedSchool.setName("xxxx");
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		
		session.update(detachedSchool);
		
		session.getTransaction().commit();
		
		assertEquals(1,SchoolTestUtils.getSchoolFromDB().size());
		
		School school_in_db = SchoolTestUtils.getSchoolFromDB_First();
		assertEquals(school_in_db.getName(), "xxxx");
	}
	
	@After
	public void tearDown(){
		SchoolTestUtils.cleanTableSchool();
	}
	
}
