package com.hdd.hibernate.test.util;

import java.util.List;

import org.hibernate.Session;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.School;

public class SchoolTestUtils {

	public static void cleanTableSchool() {
		System.out.println("tear down begin");
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		session.createQuery("delete from School").executeUpdate();
		session.getTransaction().commit();
		session.close();
		Session session3 = HibernateUtils.getSession();
		List res = session3.createQuery("from School").list();
		if(!res.isEmpty()){
			throw new RuntimeException("table is not clear");
		}
		session3.close();
		System.out.println("tear down finish");
	}

	public static Session saveObjectToDB(Object o){
		Session session3 = HibernateUtils.getSession();
		session3.beginTransaction();
		session3.save(o);
		session3.getTransaction().commit();
		session3.close();
		return session3;
	}

	public static School getSchoolFromDB_First(){
		return SchoolTestUtils.getSchoolFromDB().get(0);
	}

	public static List<School> getSchoolFromDB(){
		return execute_hql("from School");
	}

	public static List<School> execute_hql(String hql) {
		Session session2 = HibernateUtils.getSession();
		List res = session2.createQuery(hql).list();
		session2.close();
		return res;
	}

	public static Object execute_hql_return_object(String hql) {
		Session session2 = HibernateUtils.getSession();
		session2.beginTransaction();
		List res = session2.createQuery(hql).list();
		session2.getTransaction().commit();
		session2.close();
		return res;
	}
	
}
