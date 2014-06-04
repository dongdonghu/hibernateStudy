package com.hdd.hibernate.study;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.connection.ConnectionProviderFactory;

public class HibernateUtils {

	private static SessionFactory sessionFactory;

	public static Session getSession(){
		if(sessionFactory == null){
		sessionFactory = new Configuration()
        .configure() // configures settings from hibernate.cfg.xml
        .buildSessionFactory();
		}
		SQLCounter sqlCounter=new SQLCounter();
		Session session = sessionFactory.openSession(sqlCounter);
		return new SqlCountSession(session,sqlCounter);
	}
	
	public static List<String> getSQLs(Session session){
		SqlCountSession mysession = (SqlCountSession)session;
		return mysession.getSQLCounter().getSqls();
	}
	
	public static SQLCounter getSQLCounter(Session session){
		SqlCountSession mysession = (SqlCountSession)session;
		return mysession.getSQLCounter();
		
	}
	
	public static Connection getSQLConnection() throws SQLException{
		ConnectionProvider cp = ConnectionProviderFactory.newConnectionProvider();
		return cp.getConnection();
	}

}
