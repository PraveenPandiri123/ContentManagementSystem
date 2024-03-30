package com.example.cms.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
public class Blog {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int blogId;
	private String title;
	private String[] topics;
	private String about;
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime lastModifiedAt;
	
	@ManyToOne
	private User  user;
	
	@OneToOne
	private contributionPanel contributionpanel;
}
