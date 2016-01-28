package it.unical.classmanager.model.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name ="departement")
public class Departement implements Serializable  {
	private static final long serialVersionUID = 2510501980555217109L;
	
	@Id
	@Column(name="id", nullable=false, length=32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="name", nullable=false, length=32)
	private String name;
	
	@Column(name="address", nullable=false, length=128)
	private String address;
	
	@Column(name="region", nullable=false, length=64)
	private String region;
	
	@Column(name="state", nullable=false, length=128)
	private String state;
	
	//	Foreign key section	
	@OneToMany(mappedBy = "departement", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)	
	private List<DegreeCourse> degreeCourses;
	
	public Departement(){
		this.id = 0;
		this.name = "";
		this.address = "";
		this.region = "";
		this.state = "";
		this.degreeCourses = new ArrayList<DegreeCourse>();		
	}

	public Departement(int id, String name, String address, String region, String state,
			List<DegreeCourse> degreeCourses) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.region = region;
		this.state = state;
		this.degreeCourses = degreeCourses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((degreeCourses == null) ? 0 : degreeCourses.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		Departement other = (Departement) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (degreeCourses == null) {
			if (other.degreeCourses != null)
				return false;
		} else if (!degreeCourses.equals(other.degreeCourses))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
	
	
}