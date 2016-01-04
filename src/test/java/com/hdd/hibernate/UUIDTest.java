package com.hdd.hibernate;

import com.hdd.HibernateUtil;
import com.hdd.hibernate.study.UUIDIdentifier;
import org.hibernate.classic.Session;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by dhu on 12/29/15.
 */
public class UUIDTest {
   @Test
   public void uuid_generate_once_invoke_save(){
      Session session = HibernateUtil.getSessionFactory().openSession();
      UUIDIdentifier uuidIdentifier=new UUIDIdentifier();

      assertNull(uuidIdentifier.getUuidHex());

      session.beginTransaction();

      session.save(uuidIdentifier);

      assertNotNull(uuidIdentifier.getUuidHex());

      session.getTransaction().commit();

      assertNotNull(uuidIdentifier.getUuidHex());


   }

   @Test
   public void uuid_generate_one_time_in_2_save(){
      Session session = HibernateUtil.getSessionFactory().openSession();
      UUIDIdentifier uuidIdentifier=new UUIDIdentifier();
      session.beginTransaction();
      session.save(uuidIdentifier);
      String id = uuidIdentifier.getUuidHex();
      session.save(uuidIdentifier);
      assertEquals(id,uuidIdentifier.getUuidHex());

      session.getTransaction().commit();
   }

   @Test(expected = org.hibernate.HibernateException.class)
   public void uuid_refresh_after_save_without_flush(){
      Session session = HibernateUtil.getSessionFactory().openSession();
      session.beginTransaction();

      UUIDIdentifier uuidIdentifier=new UUIDIdentifier();
      session.save(uuidIdentifier);
      assertNotNull(uuidIdentifier.getUuidHex());
      session.refresh(uuidIdentifier);
   }

   @Test
   public void uuid_get_id_only_one_sql(){
      Session session = HibernateUtil.getSessionFactory().openSession();
      session.beginTransaction();

      UUIDIdentifier uuidIdentifier=new UUIDIdentifier();
      session.save(uuidIdentifier);
      assertNotNull(uuidIdentifier.getUuidHex());
      session.getTransaction().commit();
   }

   @Test
   public void uuid_refresh_after_save_and_flush(){
      Session session = HibernateUtil.getSessionFactory().openSession();
      session.beginTransaction();

      UUIDIdentifier uuidIdentifier=new UUIDIdentifier();
      session.save(uuidIdentifier);
      assertNotNull(uuidIdentifier.getUuidHex());
      session.flush();
      session.refresh(uuidIdentifier);
      assertNotNull(uuidIdentifier.getUuidHex());
   }

}
