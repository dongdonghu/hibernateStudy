package com.hdd.hibernate.study.polymorphic.join;

import javax.persistence.Entity;

@Entity
public class Teacher2 extends Person2{
	String leader;
	public static final String FROM_TEACHER2 = "from Teacher2";

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}
}
