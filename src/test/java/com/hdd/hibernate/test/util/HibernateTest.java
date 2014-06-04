package com.hdd.hibernate.test.util;

import static org.junit.Assert.assertEquals;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.hdd.hibernate.study.HibernateUtils;

public class HibernateTest {

	public HibernateTest() {
		super();
	}

	public void assertSQLNum(int size,Session session) {
		assertEquals(size, HibernateUtils.getSQLs(session).size());
	}

	protected void printSQLs(Session session) {
		HibernateUtils.getSQLCounter(session).printSQLs();
	}
	
	protected void clearSQLs(Session session) {
		HibernateUtils.getSQLCounter(session).clearSqls();
	}

	public static void cleanTable(String tableName) {
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		SQLQuery q = session.createSQLQuery(String.format("delete from %s", tableName));
		q.executeUpdate();
		session.getTransaction().commit();
	}
	
}