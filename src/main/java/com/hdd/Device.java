package com.hdd;

import javax.persistence.Id;

import org.hibernate.annotations.Entity;

@Entity
public class Device {
	Long id;

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
