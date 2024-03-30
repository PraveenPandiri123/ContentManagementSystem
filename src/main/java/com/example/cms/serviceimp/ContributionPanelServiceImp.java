package com.example.cms.serviceimp;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.contributionPanel;
import com.example.cms.exception.ContributionPanelrNotFoundException;
import com.example.cms.exception.IllegalAccesRequestException;
import com.example.cms.exception.UserNotFoundException;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributionPanelrepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.ContributionPanelService;
import com.example.cms.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContributionPanelServiceImp implements ContributionPanelService {
	private ResponseStructure<contributionPanel> responseStructure;
	private ContributionPanelrepository contributionPanelrepository;
	private UserRepository userRepository;
	private BlogRepository blogRepository;

	@Override
	public ResponseEntity<ResponseStructure<contributionPanel>> addContributors(int userId, int panelId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepository.findByEmail(email).map(owner -> {

			return contributionPanelrepository.findById(panelId).map(panel -> {

				if (blogRepository.existsByUserAndContributionpanel(owner, panel))
					throw new IllegalAccesRequestException("failed to add user");

				return userRepository.findById(userId).map(contributer -> {

					if (!panel.getUsers().contains(contributer) && panel.getUsers().contains(owner)) { // *****
						panel.getUsers().add(contributer);
						contributionPanelrepository.save(panel);
					}
					return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
							.setMessage("userAdd successfully to the contribution panel").setData(panel));

				}).orElseThrow(() -> new UserNotFoundException("User not found to add"));

			}).orElseThrow(() -> new ContributionPanelrNotFoundException("Contributiobpanel not found exception"));

		}).get();

	}

	@Override
	public ResponseEntity<ResponseStructure<contributionPanel>> removeUserFromContributionPanel(int userId,
			int panelId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepository.findByEmail(email).map(owner -> {

			return contributionPanelrepository.findById(panelId).map(panel -> {

				if (blogRepository.existsByUserAndContributionpanel(owner, panel))
					throw new IllegalAccesRequestException("failed to add user");

				return userRepository.findById(userId).map(contributer -> {

					panel.getUsers().remove(contributer);

					return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
							.setMessage("user removed successfully to the contributionpanel")
							.setData(contributionPanelrepository.save(contributionPanelrepository.save(panel))));

				}).orElseThrow(() -> new UserNotFoundException("User not found to add"));

			}).orElseThrow(() -> new ContributionPanelrNotFoundException("Contributiobpanel not found exception"));

		}).get();
	}
}