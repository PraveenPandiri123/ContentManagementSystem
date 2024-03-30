package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.entity.Blog;
import com.example.cms.entity.User;
import com.example.cms.entity.contributionPanel;

public interface BlogRepository extends JpaRepository<Blog, Integer>{

	boolean existsByTitle(String title);

	boolean existsByUserAndContributionpanel(User owner, contributionPanel panel);





}
