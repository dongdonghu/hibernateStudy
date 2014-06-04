package com.hdd.hibernate.study.polymorphic.join;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="student_id") //default value, could dismiss
public class Student2 extends Person2{
	String parent;
	public static final String FROM_STUDENT2 = "from Student2";

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
}
