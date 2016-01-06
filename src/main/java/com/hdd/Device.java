package com.hdd;

import javax.persistence.*;

@Entity
@Table(name="device")
public class Device {
	Long id;
	@Column(name="name",length=20, nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	String name;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	
}
