package com.hdd.hibernate.study.polymorphic;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.polymorphic.join.Person2;
import com.hdd.hibernate.study.polymorphic.join.Student2;
import com.hdd.hibernate.study.polymorphic.join.Teacher2;
import com.hdd.hibernate.test.util.HibernateTest;

public class JoinTableTest {


	private Session session;

	@Before
	public void setUp(){
		session=HibernateUtils.getSession();
	}
	
	@Test
	public void test_save_basic(){
		insert_one_student_and_one_teacher();
		session.beginTransaction();
		List res = session.createQuery(Student2.FROM_STUDENT2).list();
		session.getTransaction().commit();
		assertEquals(1,res.size());
	}

	private void insert_one_student_and_one_teacher() {
		session.beginTransaction();
		Student2 s=new Student2();
		session.save(s);
		Teacher2 t=new Teacher2();
		t.setName("my teacher");
		session.save(t);
		session.getTransaction().commit();
	}
	
	@Test public void test_ploymoiphic(){
		insert_one_student_and_one_teacher();
		List res = session.createQuery(Person2.FROM_PERSON2).list();
		assertEquals(2,res.size());
		List res2 = session.createQuery(Student2.FROM_STUDENT2).list();
		assertEquals(1,res2.size());
		List res3 = session.createQuery(Teacher2.FROM_TEACHER2).list();
		assertEquals(1,res3.size());
	}
	
	@After
	public void tearDown(){
		session=HibernateUtils.getSession();
		HibernateTest.cleanTable("student2");
		HibernateTest.cleanTable("teacher2");
		HibernateTest.cleanTable("person2");
	}
	
		
}
