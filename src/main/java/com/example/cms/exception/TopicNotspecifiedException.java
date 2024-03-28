package com.example.cms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TopicNotspecifiedException extends RuntimeException {
private String message;
}
