package com.hdd.hibernate.study;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by dhu on 12/29/15.
 */
@Entity(name = "uuidIdentifier")
public class UUIDIdentifier {

   @GeneratedValue(generator = "uuid")
   @GenericGenerator(name = "uuid", strategy = "uuid")
   @Column(columnDefinition = "CHAR(32)")
   @Id
   private String uuidHex;

   public String getUuidHex() {
      return uuidHex;
   }

   public void setUuidHex(String uuidHex) {
      this.uuidHex = uuidHex;
   }
}