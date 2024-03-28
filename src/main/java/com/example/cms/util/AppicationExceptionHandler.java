package com.example.cms.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.cms.exception.TitleAlreadyExistsException;
import com.example.cms.exception.TopicNotspecifiedException;
import com.example.cms.exception.UserAlreadyExistsByEmailException;
import com.example.cms.exception.UserNotFoundException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class AppicationExceptionHandler extends ResponseEntityExceptionHandler {

	private ErrorStructure<Object> structure;
	private ErrorStructure<String> errorStructure;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, String> map = new HashMap<>();
		ex.getAllErrors().forEach(error -> {
			map.put(((FieldError) error).getField(), error.getDefaultMessage());
		});

		return ResponseEntity.ok(
				structure.setStatus(HttpStatus.BAD_REQUEST.value()).setMessage("in valid inputs").setRootCause(map));

	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistsByEmailException(
			UserAlreadyExistsByEmailException ex) {
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Alredy User with this email is exists");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "given email is not present");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserNotFoundException(UserNotFoundException ex) {
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "given user is not present with given Id");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleTopicNotspecifiedException(TopicNotspecifiedException ex) {
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "topic is not specified");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleTitleAlreadyExistsException(TitleAlreadyExistsException ex) {
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "given title is alredy exists");
	}
	private ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status, String message, String rootcause) {

		return new ResponseEntity<ErrorStructure<String>>(
				errorStructure.setStatus(status.value()).setMessage(message).setRootCause(rootcause), status);
	}
}
