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
@Table(name ="question")
public class Question implements Serializable  {
	private static final long serialVersionUID = -4199532379941166226L;
	
	@Id
	@Column(name="id", nullable=false, length=20)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
		
	@Column(name="title", nullable=false, length=100)
	@Size(min=1, max=100)
	private String title;
	
	@Column(name="description", nullable=false, length=10000)
	@Size(min=1, max=10000)
	private String description;
	
	@Column(name="tags", length=100)
	@Size(max=100)
	private String tags;

	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Answer> answers;
	
	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<QuestionAttachedContent> questionAttachedContents;
	
	public Question(){
		this.id = 0;
		this.title = "";
		this.description = "";
		this.tags = "";
		this.user = null;
		this.answers = new HashSet<Answer>();
		this.questionAttachedContents = new HashSet<QuestionAttachedContent>();
	}

	public Question(int id, String title, String description, String tags, User user, Set<Answer> answers,
			Set<QuestionAttachedContent> questionAttachedContents) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.user = user;
		this.answers = answers;
		this.questionAttachedContents = questionAttachedContents;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public Set<QuestionAttachedContent> getQuestionAttachedContents() {
		return questionAttachedContents;
	}

	public void setQuestionAttachedContents(Set<QuestionAttachedContent> questionAttachedContents) {
		this.questionAttachedContents = questionAttachedContents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((questionAttachedContents == null) ? 0 : questionAttachedContents.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Question other = (Question) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (questionAttachedContents == null) {
			if (other.questionAttachedContents != null)
				return false;
		} else if (!questionAttachedContents.equals(other.questionAttachedContents))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
}