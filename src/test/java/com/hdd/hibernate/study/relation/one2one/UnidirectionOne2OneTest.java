package com.hdd.hibernate.study.relation.one2one;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.relation.unidirection.one2one.Car2;
import com.hdd.hibernate.study.relation.unidirection.one2one.Driver2;
import com.hdd.hibernate.test.util.HibernateTest;

public class UnidirectionOne2OneTest {

	private Session session;
	@Before
	public void setUp(){
		session=HibernateUtils.getSession();
	}
	
	@Test
	public void normal_case(){
		Car2 Car2=new Car2();
		Driver2 driver2=new Driver2();
		Car2.setName("Car21");
		driver2.setName("driver21");
		Car2.setMyDrvier(driver2);
		session.beginTransaction();
		session.save(driver2);
		session.save(Car2);
		session.getTransaction().commit();
		
		Car2 Car22=(Car2)session.createQuery("from Car2").list().get(0);
		assertNotNull(Car22.getMyDrvier());
		
	}
	
	
	
	@After
	public void tearDown(){
//		HibernateTest.cleanTable("car2");
//		HibernateTest.cleanTable("driver2");
	}
}
