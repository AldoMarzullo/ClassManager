package it.unical.classmanager.model.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name ="answer")
public class Answer implements Serializable  {
	private static final long serialVersionUID = 1969728579950297116L;
	
	@Id
	@Column(name="id", nullable=false, length=20)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="description", nullable=false, length=10000)
	@Size(min=1, max=10000)
	private String description;
	
	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "question")
	private Question question;
	
	@OneToMany(mappedBy = "answer", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<AnswerAttachedContent> answerAttachedContents;
	
	public Answer(){
		this.id = 0;
		this.description = "";
		this.user = null;
		this.question = null;
		this.answerAttachedContents = new HashSet<AnswerAttachedContent>();
	}

	public Answer(int id, String description, User user, Question question,
			Set<AnswerAttachedContent> answerAttachedContents) {
		this.id = id;
		this.description = description;
		this.user = user;
		this.question = question;
		this.answerAttachedContents = answerAttachedContents;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Set<AnswerAttachedContent> getAnswerAttachedContents() {
		return answerAttachedContents;
	}

	public void setAnswerAttachedContents(Set<AnswerAttachedContent> answerAttachedContents) {
		this.answerAttachedContents = answerAttachedContents;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Answer other = (Answer) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}