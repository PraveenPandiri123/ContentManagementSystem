package com.example.cms.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class contributionPanel {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int panelId;
	@ManyToMany
	private List<User> users = new ArrayList<>();
}
