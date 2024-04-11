package com.example.cms.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.cms.enums.PostType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BlogPost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	private String title;
	private String subTitle;
	@Column(length = 500)
	private String Summary;
	private PostType postType;
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createAt;
	@LastModifiedDate	
	private LocalDateTime lastModifiedAt;
	@CreatedBy
	@Column(updatable = false)
	private String createdBy;
	@LastModifiedBy
	private String lastModifiedBy;
	@ManyToOne
	private Blog blog;
	@OneToOne(mappedBy = "blogPost")
	private Publish publish;
}