package com.hdd.hibernate.study.object.state;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.hibernate.UnresolvableObjectException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.School;
import com.hdd.hibernate.test.util.SchoolTestUtils;

public class PersisentEntityTest {

	private Session session;
	private School school_in_persist;


	@Before
	public void setUp(){
		System.out.println("#Setup begin");
		School sc=new School();
		sc.setName("anhui_sixiao");
		SchoolTestUtils.saveObjectToDB(sc);
		session=HibernateUtils.getSession();
		session.beginTransaction();
		school_in_persist=(School)session.createQuery("from School").list().get(0);
		System.out.println("#Setup finish");
	}
	
	@Test
	public void save_nothing_happen(){
		school_in_persist.setName("xxxxxx");
		session.save(school_in_persist);
		session.getTransaction().commit();
		assertEquals(1, SchoolTestUtils.getSchoolFromDB().size());
	}
	
	@Test
	public void merge_nothing_happen(){
		school_in_persist.setName("xxxxxx");
		session.merge(school_in_persist);
		session.getTransaction().commit();
		assertEquals(1, SchoolTestUtils.getSchoolFromDB().size());
	}
	
	
	
	@Test
	public void update_nothing_happen(){
		school_in_persist.setName("xxxxxx");
		session.update(school_in_persist);
		session.getTransaction().commit();
		assertEquals(1, SchoolTestUtils.getSchoolFromDB().size());
	}
	
	@Test
	public void refresh_to_get_value_from_db_which_value_has_changed_by_other() throws InterruptedException{
		Thread t=new Thread(new Runnable() {
			public void run() {
				Session session3 = HibernateUtils.getSession();
				session3.beginTransaction();
				School school = (School)session3.createQuery("from School").list().get(0);
				school.setName("xxxx");
				session3.save(school);
				session3.getTransaction().commit();
			}
		});
		t.start();
		t.join();
		session.refresh(school_in_persist);
		assertEquals("xxxx", school_in_persist.getName());
	}
	
	@Test(expected=UnresolvableObjectException.class)
	public void refresh_but_have_been_deleted_by_other_throw_exception() throws InterruptedException{
		school_in_persist.setName("yyyy");

		Thread t=new Thread(new Runnable() {
			public void run() {
				Session session3 = HibernateUtils.getSession();
				session3.beginTransaction();
				session3.createQuery("delete School").executeUpdate();
				session3.getTransaction().commit();
			}
		});
		t.start();
		t.join();
		session.refresh(school_in_persist);
	}
	
	@After
	public void tearDown(){
		SchoolTestUtils.cleanTableSchool();
	}
	
}
