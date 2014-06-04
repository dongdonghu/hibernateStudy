package com.hdd.hibernate.study.relation.unidirection.one2one;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.hdd.hibernate.study.relation.bidirection.one2one.Driver;

@Entity
public class Car2 {
	long id;
	String name;
	Driver2 _drvier;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToOne
	public Driver2 getMyDrvier() {
		return _drvier;
	}
	public void setMyDrvier(Driver2 drvier) {
		this._drvier = drvier;
	}
	
}
