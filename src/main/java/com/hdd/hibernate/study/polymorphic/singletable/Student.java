package com.hdd.hibernate.study.polymorphic.singletable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="student")
public class Student extends Person{
	String parent;
	public static final String FROM_STUDENT = "from Student";

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
}
