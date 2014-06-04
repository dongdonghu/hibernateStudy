package com.hdd.hibernate.study;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name="Schools")
public class School {
	
	public School() {
	}
	

	public School(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		School other = (School) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	private Long id;
	

	
	private String name;
	
	//no annotation means basic
	//means @Basic(optional=true);
	private String description;

	//This annotation doesn't work, for the annotation for id is on the getXXX
	@Transient
	private String fullName;
	 

	private int age;



	public static final String FROM_SCHOOL = "from School";

	@Transient
	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	@Transient
	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	@Id
	@GeneratedValue
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GenericGenerator(name="increment",strategy="increment")
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="name",length=20, nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	@Column(name="build_date")
//	@Temporal(TemporalType.TIMESTAMP)
//	public Date getDate() {
//		return date;
//	}

//	public void setDate(Date date) {
//		this.date = date;
//	}

}
