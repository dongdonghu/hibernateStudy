package com.hdd;

import java.util.Date;

import org.hibernate.Session;

public class MainService {

	public static void main(String[] args){
//		Configuration cfg = new Configuration()
//	    .addResource("Item.hbm.xml")
//	    .addResource("Bid.hbm.xml");
//		
////		Session session = sessions.openSession();
//		Session session = HibernateUtil.getSession();
        MainService mgr = new MainService();

//        if (args[0].equals("store")) {
            mgr.createAndStoreEvent("My Event", new Date());
//        }

        HibernateUtil.getSessionFactory().close();
	}
	
	  public void createAndStoreEvent(String title, Date theDate) {
	        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	        session.beginTransaction();

	        Event theEvent = new Event();
	        theEvent.setTitle(title);
	        theEvent.setDate(theDate);
	        session.save(theEvent);

	        session.getTransaction().commit();
	    }
	
}
