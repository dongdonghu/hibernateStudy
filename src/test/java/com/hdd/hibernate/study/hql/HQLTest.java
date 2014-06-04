package com.hdd.hibernate.study.hql;

import static com.hdd.hibernate.test.util.SchoolTestUtils.cleanTableSchool;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.School;
import com.hdd.hibernate.test.util.SchoolTestUtils;

public class HQLTest {
	@Before
	public void setUp(){
		School sc=new School();
		sc.setName("anhui_sixiao");
		SchoolTestUtils.saveObjectToDB(sc);
	}
	
	
	//interesting, return is a List<School> but List<Object[]> actually
	//
	@Test
	public void select_attribute_return_list_with_object_array(){
		List<Object[]> res = (List<Object[]>)SchoolTestUtils.execute_hql_return_object("select sc.name ,sc.description from School sc");
		assertEquals("anhui_sixiao", res.get(0)[0]);
	}
	
	//Require constructor
	@Test
	public void select_attribute_return_object() {
		List<School> res = SchoolTestUtils.execute_hql("select new School(sc.name ,sc.description) from School sc");
		assertEquals("anhui_sixiao", res.get(0).getName());
	}
	
	@Test
	public void newobject_is_add_when_saveOrUpdate_object_which_primary_key_is_null() {
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		School school=(School) session.createQuery("select new School(sc.name ,sc.description) from School sc").list().get(0);
		session.saveOrUpdate(school);
		session.getTransaction().commit();
		session.close();
		assertEquals(2, SchoolTestUtils.getSchoolFromDB().size());
	}
	
	
	@Test public void select_by_parameter(){
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		Query query=session.createQuery("from School sc where sc.name=:name");
		query.setString("name","anhui_sixiao");
		List res = query.list();
		session.getTransaction().commit();
		assertEquals(1, res.size());
	}
	
	@Test public void select_by_parameter_null_is_not_equal_to_null_or_emtpy_string(){
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		Query query=session.createQuery("from School sc where sc.name=:name and sc.description=:desc");
		query.setString("name","anhui_sixiao");
		query.setString("desc",null);
		List res = query.list();
		Query query2=session.createQuery("from School sc where sc.name=:name and sc.description=:desc");
		query2.setString("name","anhui_sixiao");
		query2.setString("desc",null);
		List res2 = query2.list();
		session.getTransaction().commit();
		assertEquals(res2.size(), res.size());
		assertEquals(0, res.size());
	}
	
	@Test public void select_by_parameter_index(){
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		Query query=session.createQuery("from School sc where sc.name=?");
		query.setString(0,"anhui_sixiao");
		List res = query.list();
		assertEquals(1, res.size());
	}
	
//	@Test public void select_by_parameter_entity(){

//		Customer customer=(Customer)session.load(Customer.class,¡±1¡±);
//		Query query=session.createQuery(¡°from Order order where order.customer=:customer ¡±);
//		query. setProperties(¡°customer¡±,customer);
//		List list=query.list();
//	}
	
//	@Test public void select_by_parameter_entity(){
//
//		Customer customer=new Customer();
//		customer.setName(¡°pansl¡±);
//		customer.setAge(80);
//		Query query=session.createQuery(¡°from Customer c where c.name=:name and c.age=:age ¡±);
//		query.setProperties(customer);
//	}
	
	@After
	public void tearDown(){
		cleanTableSchool();
	}
}
