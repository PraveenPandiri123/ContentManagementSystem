package com.example.cms.service;

import org.springframework.http.ResponseEntity;
import com.example.cms.entity.contributionPanel;
import com.example.cms.util.ResponseStructure;

public interface ContributionPanelService {

	ResponseEntity<ResponseStructure<contributionPanel>> addContributors(int userId, int panelId);

	ResponseEntity<ResponseStructure<contributionPanel>> removeUserFromContributionPanel(int userId, int panelId);

}
