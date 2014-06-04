package com.hdd.hibernate.study.hql;

import static com.hdd.hibernate.test.util.SchoolTestUtils.cleanTableSchool;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.School;
import com.hdd.hibernate.test.util.SchoolTestUtils;

public class MixUseHQLAndEntity {


	@Before
	public void setUp(){
		School sc=new School();
		sc.setName("anhui_sixiao");
		SchoolTestUtils.saveObjectToDB(sc);
	}
	
	@Test
	public void hql_will_find_entity_no_commit(){
		School sc=new School();
		sc.setName("123");
		
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		
		session.save(sc);
		Query query = session.createQuery("from School");   //will trigger flush in back ground
		List res = query.list();
		assertEquals(2,res.size());
		System.out.println("before commit");
		session.getTransaction().commit();
		System.out.println("after commit");
	}
	
	
	@Test
	public void hql_update_not_cause_entity_change_entity_not_in_line_with_db_after_update_but_no_dirty_check_run_when_commit(){
		School sc=new School();
		sc.setName("123");
		
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		
		Query query = session.createQuery("update School school set school.name='hdd' ");   //will trigger flush in back ground
		query.executeUpdate();
		assertEquals("123", sc.getName());
		
		System.out.println("before commit");
		session.getTransaction().commit();
		System.out.println("after commit");
	}
	
	/**
	 * Purpose:
	 * The real problem is that the native sql will not flush all the change from 1 level cache to DB
	 * Problem:
	 * The testcase depend on the primary key generate strategy.
	 * (1)If the primary key is GenerationType.SEQUENCE used in oracle/postgresql, 
	 * it only to get the primary key first when saving the object. The object will insert into db when transcation submmit
	 * (2)if the primary key is GenerationType.IDENTITY used in mysql/sqlserver, the hibernate have to insert the data first to get object.
	 * 
	 * So,In second case, the native sql may find the data too. But the first is not.
	 * Postgresql doesn't support identity but it can mock it by sequence with event function.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void nativesql_will_not_find_entity_no_commit() throws InterruptedException{
		School sc=new School();
		sc.setName("123");
		
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		
		session.save(sc);
		System.out.println("add ----------");
		Query query = session.createSQLQuery("select * from schools");
		
		List res = query.list();
		
		assertEquals(1, res.size());
		System.out.println("before commit");
		session.getTransaction().commit();
		System.out.println("after commit");
		assertEquals(2, SchoolTestUtils.getSchoolFromDB().size());
		
	}
	
	@Test
	public void nativesql_update_will_not_update_loaded_entity_like_hql(){
		
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		School sc = (School)session.createQuery("from School").list().get(0);
		Query query = session.createSQLQuery("update schools set name='hdd'");
		query.executeUpdate();
		assertEquals("anhui_sixiao", sc.getName());
		System.out.println("before commit");
		session.getTransaction().commit();
		System.out.println("after commit");
	}
	
	@After
	public void tearDown(){
		cleanTableSchool();
	}
	
}
