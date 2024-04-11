package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.entity.User;
import com.example.cms.entity.contributionPanel;

public interface ContributionPanelrepository extends JpaRepository<contributionPanel, Integer>{

	boolean existsByUsers(User contributer);

	boolean existsByPanelIdAndUsers(int panelId, User user);

	


}
