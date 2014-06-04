package com.hdd.hibernate.study.polymorphic.singletable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity //mandatory
@DiscriminatorValue(value="teacher")
public class Teacher extends Person{
	String leader;
	public static final String FROM_TEACHER = "from Teacher";

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}
}
