package com.chinthaka.questionandanswerplatform.excaption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomMethodArgumentNotValidException extends RuntimeException {
    public CustomMethodArgumentNotValidException(String message) {
        super(message);
    }
}