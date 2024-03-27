package com.example.cms.serviceimp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.User;
import com.example.cms.exception.UserAlreadyExistsByEmailException;
import com.example.cms.exception.UserNotFoundException;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.util.ResponseStructure;

import jakarta.validation.ReportAsSingleViolation;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private ResponseStructure<UserResponse> responseStructure;
	UserResponse userResponse;

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest) {
		if (userRepository.existsByEmail(userRequest.getEmail()))
			throw new UserAlreadyExistsByEmailException("Failed to register User");
		User user = mapToUser(userRequest, new User());
		user = userRepository.save(user);
		return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
				.setMessage("User registered Successfully").setData(mapToUserResponse(user)));
	}
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId) {
				return userRepository.findById(userId).map(user->{
					user.setDeleted(true);
					return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
							.setMessage("User deleted Successfully").setData(mapToUserResponse(userRepository.save(user))));
				}).orElseThrow(()-> new UserNotFoundException("USer Not found success fully"));
	}
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> userFindById(int userId) {
		
		return userRepository.findById(userId).map(user->{
			return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
					.setMessage("User found Successfully").setData(mapToUserResponse(user)));
		}).orElseThrow(()-> new UserNotFoundException("USer Not found success fully"));
	}

	private User mapToUser(UserRequest userRequest, User user) {
		user.setUserName(userRequest.getUserName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setDeleted(false);
		return user;
	}
	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder().userId(user.getUserId()).userName(user.getUserName()).email(user.getEmail())
				.createdAt(user.getCreatedAt()).lastModifiedAt(user.getLastModifiedAt()).build();
	}

	

	

	}
