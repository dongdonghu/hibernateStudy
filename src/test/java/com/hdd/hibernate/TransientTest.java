package com.hdd.hibernate;

import com.hdd.Event;
import com.hdd.HibernateUtil;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by dhu on 12/29/15.
 */
public class TransientTest {

   @Before
   public void setup() {
      HibernateUtil.execute((Session se) -> {
         Event e = new Event();
         e.setDate(new Date());
         e.setTitle("old");
         se.save(e);
      });
   }

   @Test
   public void transientFieldDoesNotRefresh() {

      HibernateUtil.execute((Session session) -> {
            Event e = (Event) session.get(Event.class, new Long(1));
            e.setTransientValue("sd");
            session.refresh(e);
            assertEquals("sd", e.getTransientValue());
         }
      );
   }


   @Test
   public void transientFieldDoesNotRefreshIn2Sesion() {
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Event e = (Event) session.get(Event.class, new Long(1));
      e.setTransientValue("sd");
      e.setTitle("new");
      try{
         if("new".equals(e.getTitle()))throw new RuntimeException("e");
         session.getTransaction().commit();
      }catch (Exception ex){
         session.getTransaction().rollback();
      }finally {

      }

      session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      assertEquals("new",e.getTitle());
      session.refresh(e);
      assertEquals("old",e.getTitle());

      assertEquals("sd",e.getTransientValue());
      session.getTransaction().commit();

   }
}
