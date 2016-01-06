package com.hdd.hibernate;

import com.hdd.Device;
import com.hdd.Event;
import com.hdd.HibernateUtil;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by dhu on 1/6/16.
 */
public class HQLFlushTest {
   @Before
   public void setup(){
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Event e = new Event();
      e.setDate(new Date());
      e.setTitle("old");
      Device device=new Device();
      device.setName("hdd");
      session.beginTransaction();
      session.save(e);
      session.save(device);
      session.getTransaction().commit();
   }


   @Test
   public void should_only_flush_ALL_Object_Whatever_object_in_HQL(){
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Event e = (Event) session.get(Event.class, new Long(1));
      assertEquals("old",e.getTitle());
      e.setTitle("new");
      Device device = (Device) session.get(Device.class, new Long(1));
      device.setName("xx");

      String hql="from Event as e where e.title='old'";
      Query query = session.createQuery(hql);
      List queryResult = query.list();
      System.out.println("query finish");

      session.getTransaction().commit();
   }

   @Test
   public void should_not_flush_BY_HQL_WHEN_FLUSH_MODE_IS_COMMIT(){
      System.out.println("---------");
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Event e = (Event) session.get(Event.class, new Long(1));
      assertEquals("old",e.getTitle());
      e.setTitle("new");
      Device device = (Device) session.get(Device.class, new Long(1));
      device.setName("xx");

      session.setFlushMode(FlushMode.COMMIT);
      String hql="from Event as e where e.title='old'";
      Query query = session.createQuery(hql);

      List queryResult = query.list();
      session.setFlushMode(FlushMode.AUTO);
      System.out.println("query finish");

      session.getTransaction().commit();
   }

   @Test
   public void evit_object_before_query_and_attach_it_to_session(){
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Event e = (Event) session.get(Event.class, new Long(1));
      assertEquals("old",e.getTitle());
      e.setTitle("new");
      Device device = (Device) session.get(Device.class, new Long(1));
      device.setName("xx");

      session.evict(device);

      String hql="from Event as e where e.title='old'";
      Query query = session.createQuery(hql);
      List queryResult = query.list();
      System.out.println("query finish");

      session.update(device);
      session.getTransaction().commit();
   }

   @Test(expected = org.hibernate.NonUniqueObjectException.class)
   public void evit_object_before_query_and_attach_it_to_session_but_another_instance_in_session(){
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Event e = (Event) session.get(Event.class, new Long(1));
      assertEquals("old",e.getTitle());
      e.setTitle("new");
      Device device = (Device) session.get(Device.class, new Long(1));
      device.setName("xx");

      session.evict(device);

      String hql="from Event as e where e.title='old'";
      Query query = session.createQuery(hql);
      List queryResult = query.list();
      System.out.println("query finish");

      Device device2 = (Device) session.get(Device.class, new Long(1));
      device2.setName("xx");

      session.update(device);
      session.getTransaction().commit();
   }

}
