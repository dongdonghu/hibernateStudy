package com.hdd.hibernate;

import com.hdd.Event;
import com.hdd.HibernateUtil;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by dhu on 12/29/15.
 */
public class RefreshTest {

   @Before
   public void setup(){
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Event e = new Event();
      e.setDate(new Date());
      e.setTitle("old");
      session.beginTransaction();
      session.save(e);
      session.getTransaction().commit();
   }

   @Test
   public void should_get_new_value_refresh_after_flush(){
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Event e = (Event) session.get(Event.class, new Long(1));
      assertEquals("old",e.getTitle());
      e.setTitle("new");
//      session.flush();
      session.refresh(e);
      assertEquals("old",e.getTitle());
      session.getTransaction().commit();
   }

   @Test
   public void should_get_old_value_refresh_before_flush(){
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Event e = (Event) session.get(Event.class, new Long(1));
      assertEquals("old",e.getTitle());
      e.setTitle("new");
      session.flush();
      session.refresh(e);
      assertEquals("new",e.getTitle());
      session.getTransaction().commit();
   }
}
