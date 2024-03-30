package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.cms.entity.contributionPanel;
import com.example.cms.service.ContributionPanelService;
import com.example.cms.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ContributionPanelController {
	private ContributionPanelService contributionPanelService;

	@PutMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<contributionPanel>> addContributors(@PathVariable int userId,
			@PathVariable int panelId) {
//		System.out.println("dchvbsjcdhvsjhdcvs");
		return contributionPanelService.addContributors(userId, panelId);
	}
	@DeleteMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<contributionPanel>> removeUserFromContributionPanel(@PathVariable int userId,
			@PathVariable int panelId){
		return contributionPanelService.removeUserFromContributionPanel(userId,panelId);
	}
}
