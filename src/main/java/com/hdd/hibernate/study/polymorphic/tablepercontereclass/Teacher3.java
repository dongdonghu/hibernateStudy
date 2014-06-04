package com.hdd.hibernate.study.polymorphic.tablepercontereclass;

import javax.persistence.Entity;

@Entity
public class Teacher3 extends Person3{
	String leader;
	public static final String FROM_TEACHER3 = "from Teacher3";

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}
}
