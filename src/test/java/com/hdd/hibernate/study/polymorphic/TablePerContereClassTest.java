package com.hdd.hibernate.study.polymorphic;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.polymorphic.join.Student2;
import com.hdd.hibernate.study.polymorphic.tablepercontereclass.Person3;
import com.hdd.hibernate.study.polymorphic.tablepercontereclass.Student3;
import com.hdd.hibernate.study.polymorphic.tablepercontereclass.Teacher3;
import com.hdd.hibernate.test.util.HibernateTest;

public class TablePerContereClassTest {

	private Session session;

	@Before
	public void setUp(){
		session=HibernateUtils.getSession();
	}
	
	@Test
	public void test_save_basic(){
		insert_one_student_and_one_teacher();
		session.beginTransaction();
		List res = session.createQuery(Student3.FROM_STUDENT3).list();
		session.getTransaction().commit();
		assertEquals(1,res.size());
	}

	private void insert_one_student_and_one_teacher() {
		session.beginTransaction();
		Student3 s=new Student3();
		session.save(s);
		Teacher3 t=new Teacher3();
		t.setName("my teacher");
		session.save(t);
		session.getTransaction().commit();
	}
	
	@Test public void test_ploymoiphic(){
		insert_one_student_and_one_teacher();
		List res = session.createQuery(Person3.FROM_PERSON3).list();
		assertEquals(2,res.size());
		List res2 = session.createQuery(Student3.FROM_STUDENT3).list();
		assertEquals(1,res2.size());
		List res3 = session.createQuery(Teacher3.FROM_TEACHER3).list();
		assertEquals(1,res3.size());
	}
	
	@After
	public void tearDown(){
		session=HibernateUtils.getSession();
		HibernateTest.cleanTable("student3");
		HibernateTest.cleanTable("teacher3");
	}
	
}
