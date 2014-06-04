package com.hdd.hibernate.study.polymorphic;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hdd.hibernate.study.HibernateUtils;
import com.hdd.hibernate.study.polymorphic.singletable.Person;
import com.hdd.hibernate.study.polymorphic.singletable.Student;
import com.hdd.hibernate.study.polymorphic.singletable.Teacher;
import com.hdd.hibernate.test.util.HibernateTest;

public class SingleTableInherenceTest {

	private Session session;

	@Before
	public void setUp(){
		session=HibernateUtils.getSession();
	}
	
	@Test
	public void test_save_basic(){
		insert_one_student_and_one_teacher();
		session.beginTransaction();
		List res = session.createQuery(Student.FROM_STUDENT).list();
		session.getTransaction().commit();
		assertEquals(1,res.size());
	}

	private void insert_one_student_and_one_teacher() {
		session.beginTransaction();
		Student s=new Student();
		session.save(s);
		Teacher t=new Teacher();
		t.setName("my teacher");
		session.save(t);
		session.getTransaction().commit();
	}
	
	@Test public void test_ploymoiphic(){
		insert_one_student_and_one_teacher();
		List res = session.createQuery(Person.FROM_PERSON).list();
		assertEquals(2,res.size());
		List res2 = session.createQuery(Student.FROM_STUDENT).list();
		assertEquals(1,res2.size());
		List res3 = session.createQuery(Teacher.FROM_TEACHER).list();
		assertEquals(1,res3.size());
	}
	
	@After
	public void tearDown(){
		session=HibernateUtils.getSession();
		HibernateTest.cleanTable("person");
	}
}
