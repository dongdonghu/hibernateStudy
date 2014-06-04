package com.hdd.hibernate.study.relation.bidirection.one2one;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Car {
	long id;
	String name;
	Driver _drvier;
	
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
	public Driver getMyDrvier() {
		return _drvier;
	}
	public void setMyDrvier(Driver drvier) {
		this._drvier = drvier;
	}
	
}
