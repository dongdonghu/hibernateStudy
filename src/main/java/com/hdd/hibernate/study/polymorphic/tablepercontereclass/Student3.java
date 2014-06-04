package com.hdd.hibernate.study.polymorphic.tablepercontereclass;

import javax.persistence.Entity;

@Entity
public class Student3 extends Person3{
	String parent;
	public static final String FROM_STUDENT3 = "from Student3";

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
}
