package com.hdd.hibernate.study.basic;

import java.util.List;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.School;
import com.hdd.hibernate.test.util.HibernateTest;
import com.hdd.hibernate.test.util.SchoolTestUtils;

public class SessionTest extends HibernateTest{

	private Session session;

	@Before
	public void setup(){
		
		School sc =new School();
		sc.setName("xxx");
		SchoolTestUtils.saveObjectToDB(sc);
		
	}
	
	@Test
	public void hsql_search_no_cached_usage(){
		session=HibernateUtils.getSession();
		session.beginTransaction();
		List list = session.createQuery(School.FROM_SCHOOL).list();
		assertSQLNum(1, session);
		List list2 = session.createQuery(School.FROM_SCHOOL).list();
		assertSQLNum(2, session);
		session.getTransaction().commit();
	}
	
	@Test
	public void hsql_search_from_DB_2() throws InterruptedException{
		session=HibernateUtils.getSession();
		session.beginTransaction();
		List list = session.createQuery(School.FROM_SCHOOL).list();
		
		assertSQLNum(1, session);
		assertEquals(1, list.size());
		
		Thread anotherUser =insertRecordByOtherThread();
		anotherUser.start();
		anotherUser.join();
		
		clearSQLs(session);
		List list2 = session.createQuery(School.FROM_SCHOOL).list();
		assertSQLNum(1, session);
		assertEquals(2, list2.size());
		session.getTransaction().commit();
	}
	
	
	@Test
	public void hsql_force_flush_and_get_result_for_db() throws InterruptedException{
		session=HibernateUtils.getSession();
		session.beginTransaction();
		List list = session.createQuery(School.FROM_SCHOOL).list();
		assertEquals(1, list.size());
		
		School sc2 =new School();
		sc2.setName("zzz");
		session.save(sc2);
		
		Thread anotherUser = insertRecordByOtherThread();
		anotherUser.start();
		anotherUser.join();
		
		clearSQLs(session);
		List list2 = session.createQuery(School.FROM_SCHOOL).list();
		printSQLs(session);
		assertSQLNum(2, session); // one insert, one select
		assertEquals(3, list2.size());
		session.getTransaction().commit();
	}
	
	private Thread insertRecordByOtherThread() {
		Thread anotherUser = new Thread(new Runnable() {
			
			@Override
			public void run() {
				School sc_in_other_thread =new School();
				sc_in_other_thread.setName("yyy");
				SchoolTestUtils.saveObjectToDB(sc_in_other_thread);
				System.out.println("insert another record in other thread");
			}
		});
		return anotherUser;
	}
	
	/**
	 * 1 level cache is for object. it's invalid for field/attribute search by hsql/sql
	 * @throws InterruptedException
	 */
	@Test
	public void get_and_load_use_cache_without_DB_if_possible() throws InterruptedException{
		session=HibernateUtils.getSession();
		session.beginTransaction();
		List list = session.createQuery(School.FROM_SCHOOL).list();
		Long id1 = ((School)list.get(0)).getId();
		assertSQLNum(1, session);
		assertEquals(1, list.size());
		
		session.getTransaction().commit();
		
		clearSQLs(session);
		
		session.get(School.class, id1);
		assertSQLNum(0, session);
		
		session.load(School.class, id1);
		assertSQLNum(0, session);
		
		List list3 = session.createQuery(School.FROM_SCHOOL).list();
		assertSQLNum(1, session);
	}
	
	@After
	public void teardown(){
		SchoolTestUtils.cleanTableSchool();
	}
}
