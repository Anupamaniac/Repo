package com.rosettastone.succor.web.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The {@code Template} represents template entity from db.
 */
@Entity
@Table(name = "template", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"rule_id", "type", "kid" }) })
public class Template implements Serializable {

	public enum Type {
		EMAIL, SMS
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "type")
	@Enumerated(value = EnumType.STRING)
	private Type type;

	@Column(name = "sender")
	private String sender;

	@Column(name = "subject")
	private String subject;

	@Column(name = "body")
	@Lob
	private String body;

	@Column(name = "kid")
	private Boolean kid;

	public Template() {
	}

	public Template(Type type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

    public Boolean getKid() {
        return kid;
    }
    
	public void setKid(Boolean kid) {
		this.kid = kid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Template template = (Template) o;

		if (body != null ? !body.equals(template.body) : template.body != null)
			return false;
		if (sender != null ? !sender.equals(template.sender)
				: template.sender != null)
			return false;
		if (subject != null ? !subject.equals(template.subject)
				: template.subject != null)
			return false;
		if (type != template.type)
			return false;

        if (kid != template.kid)
            return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = type != null ? type.hashCode() : 0;
		result = 31 * result + (sender != null ? sender.hashCode() : 0);
		result = 31 * result + (subject != null ? subject.hashCode() : 0);
		result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (kid != null ? kid.hashCode() : 0);
		return result;
	}
}
