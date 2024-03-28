package com.example.cms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TitleAlreadyExistsException extends RuntimeException {
private String message;
}
