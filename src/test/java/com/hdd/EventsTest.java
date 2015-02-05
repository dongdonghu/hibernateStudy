package com.hdd;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class EventsTest {

	@Before
	public void setup(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Event e = new Event();
		e.setDate(new Date());
		e.setTitle("old");
		session.beginTransaction();
		session.save(e);
		session.getTransaction().commit();
	}
	
	@Test public void should_level_1_cache_return_same_object_without_sql_query_2th() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Event e = (Event) session.get(Event.class, new Long(1));
		Event e2 = (Event) session.get(Event.class, new Long(1));
		assertTrue(e == e2);
		session.getTransaction().commit();
	}

	/**
	 *Attention: The equals method is so important in the scenario.
	 */
	@Test
	public void two_object_from_differnt_session_is_2_object() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Event e = (Event) session.get(Event.class, new Long(1));
		
				
		Session session2 = HibernateUtil.getSessionFactory().openSession();
		session2.beginTransaction();
		Event e2 = (Event) session2.get(Event.class, new Long(1));
//		e2.setTitle("new_one");
		session.getTransaction().commit();
		
		assertFalse(e == e2);
		assertTrue(e.equals(e2));
		session2.getTransaction().commit();	
	}
}
