package com.hdd;

import org.hibernate.Session;

/**
 * Created by dhu on 12/29/15.
 */
public interface Action {
   public void execute(Session session);
}
