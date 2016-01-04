package com.hdd.hibernate.study.polymorphic.tablepercontereclass;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Person3 {

	long id;
	String name;
	long age;
	public static final String FROM_PERSON3 = "from Person3";

	// It work find in postgres but doesn't work for mysql

	/**
	 * @GeneratedValue work in postgresql but not mysql. mysql give error: talbe_per_class can't mix with generation.auto
	 * It have changet the id generate from auto to table to make it work in mysql
	 *
	 */
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Id
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
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}
}
