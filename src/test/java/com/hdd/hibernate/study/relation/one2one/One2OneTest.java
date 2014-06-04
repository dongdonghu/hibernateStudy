package com.hdd.hibernate.study.relation.one2one;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.relation.bidirection.one2one.Car;
import com.hdd.hibernate.study.relation.bidirection.one2one.Driver;
import com.hdd.hibernate.test.util.HibernateTest;

public class One2OneTest {

	private Session session;
	@Before
	public void setUp(){
		session=HibernateUtils.getSession();
	}
	
	@Test
	public void normal_case(){
		Car car=new Car();
		Driver driver=new Driver();
		car.setName("car1");
		driver.setName("driver1");
		car.setMyDrvier(driver);
		driver.setCar(car);
		session.beginTransaction();
		session.save(driver);
		session.save(car);
		session.getTransaction().commit();
		
		Car car2=(Car)session.createQuery("from Car").list().get(0);
		assertNotNull(car2.getMyDrvier());
		
		session.beginTransaction();
		Driver driver2=(Driver)session.createQuery("from Driver").list().get(0);
		assertNotNull(driver2.getCar().getName());   // Retrieve in session cache
		session.getTransaction().commit();
	}
	
	@Test
	public void relation_matained_only_on_one_side_work_in_other_session_except_itself_for_cache(){
		Car car=new Car();
		Driver driver=new Driver();
		car.setName("car1");
		driver.setName("driver1");
		car.setMyDrvier(driver);
//		driver.setCar(car);   Remove this in the case on purpose
		session.beginTransaction();
		session.save(driver);
		session.save(car);
		session.getTransaction().commit();
		
		Car car2=(Car)session.createQuery("from Car").list().get(0);
		assertNotNull(car2.getMyDrvier());
		
		session.beginTransaction();
		Driver driver2=(Driver)session.createQuery("from Driver").list().get(0);
		
		assertNull(driver2.getCar());   // Retrieve in session cache
		session.getTransaction().commit();
		
		Session session2 = HibernateUtils.getSession();
		session2.beginTransaction();
		Driver driver3=(Driver)session2.createQuery("from Driver").list().get(0);
		
		assertNotNull(driver3.getCar());   // Retrieve in session cache
		session2.getTransaction().commit();
	}
	
	@Test
	public void relation_matained_only_on_wrong_side_which_has_mapped_by_properties_is_useless(){
		Car car=new Car();
		Driver driver=new Driver();
		car.setName("car1");
		driver.setName("driver1");
//		car.setMyDrvier(driver);  Remove this in the case on purpose
		session.beginTransaction();
		session.save(driver);
		session.save(car);
		session.getTransaction().commit();
		
		Car car2=(Car)session.createQuery("from Car").list().get(0);
		assertNull(car2.getMyDrvier());
		
		session.beginTransaction();
		Driver driver2=(Driver)session.createQuery("from Driver").list().get(0);
		
		assertNull(driver2.getCar());   // Retrieve in session cache
		session.getTransaction().commit();
		
	}
	
	@After
	public void tearDown(){
		HibernateTest.cleanTable("car");
		HibernateTest.cleanTable("driver");
	}
}
