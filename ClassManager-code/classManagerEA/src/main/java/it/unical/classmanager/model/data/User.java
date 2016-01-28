package it.unical.classmanager.model.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.unical.classmanager.model.FieldMatch;
import it.unical.classmanager.model.MaxDate;
import it.unical.classmanager.model.PasswordHashing;
import it.unical.classmanager.model.dao.DaoHelper;

@Entity
@Table(name ="user")
@Inheritance(strategy = InheritanceType.JOINED)
@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword"),
}) 


public class User implements Serializable {
	private static final long serialVersionUID = 7720914354560371125L;

	public final static String PROFESSOR = "Professor";
	public final static String STUDENT = "Student";
	
	@Id
	@Column(name="username", nullable=false, length=20)
	@Size(min=4,max=20)
	@Pattern(regexp = "^[A-Za-z0-9]+$")
	private String username;
	
	@Transient
	@Size(min=6,max=50)
	private String password;
	
	@Transient
	@Size(min=6,max=50)
	private String confirmPassword;
	
	@Column(name="password",nullable=false, length=129)
	private String hash;
	
	@Column(name="role", nullable=true, length=32)
	private String role;
	
	@Column(name="firstName", nullable=false, length=20)
	@Size(min=3, max=20)
	private String firstName;

	@Column(name="lastName", nullable=false, length=20)
	@Size(min=3, max=20)
	private String lastName;
	
	@Column(name="email", nullable=false, length=128)
	@Size(max=200)
	@Email()
	@NotEmpty()
	private String email;
	
	@Column(name="birthDate", nullable=false)
	@MaxDate
	@NotNull(message="La data di nascita non è stata inserita.")
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@JsonFormat(pattern="dd/MM/yyyy")
	@Past()	
	private Date birthDate;
	
	@Column(name="serialNumber", nullable=false, length=4,unique=true)
	@Pattern(regexp = "^[0-9]+$")
	@Size(min=1, max=4)
	private String serialNumber;
	
	//	Foreign key section
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Event> events;
//	private Set<Event> events;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Question> questions;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Answer> answers;	
		
	public User(){
		this.username = "";
		this.password = "";
		this.confirmPassword = "";
		this.hash = "";
		this.role = "";
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.birthDate = null;
		this.serialNumber = null;
		this.events = new ArrayList<Event>();
		this.questions = new ArrayList<Question>();
		this.answers = new ArrayList<Answer>();
	}	
	
	public User(String username, String password, String confirmPassword, String role, String firstName, String lastName, String email,
			Date birthDate, String address, List<Event> events, List<Question> questions, List<Answer> answers) {
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.birthDate = birthDate;
		this.serialNumber = address;
		this.events = events;
		this.questions = questions;
		this.answers = answers;
	}
	
	public User(User user) {
		this.username = user.username;
		this.password = user.password;
		this.confirmPassword = user.confirmPassword;
		this.hash = user.hash;
		this.role = user.role;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.email = user.email;
		this.birthDate = user.birthDate;
		this.serialNumber = user.serialNumber;
		this.events = new ArrayList<Event>(user.events);
		this.questions = new ArrayList<Question>(user.questions);
		this.answers = new ArrayList<Answer>(user.answers);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getHash() {
		return hash;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serialNumber == null) ? 0 : serialNumber.hashCode());
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((questions == null) ? 0 : questions.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (serialNumber == null) {
			if (other.serialNumber != null)
				return false;
		} else if (!serialNumber.equals(other.serialNumber))
			return false;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (confirmPassword == null) {
			if (other.confirmPassword != null)
				return false;
		} else if (!confirmPassword.equals(other.confirmPassword))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", confirmPassword=" + confirmPassword
				+ ", hash=" + hash + ", role=" + role + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", birthDate=" + birthDate + ", serialNumber=" + serialNumber + "]";
	}

	
}
