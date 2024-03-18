package com.chinthaka.questionandanswerplatform.excaption.advicer;

import com.chinthaka.questionandanswerplatform.excaption.*;
import com.chinthaka.questionandanswerplatform.utils.StandardResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HandleGlobalException {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardResponse> handleHandleException(NotFoundException e){
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(404,"Error",e.getMessage())
                , HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<StandardResponse> handleAlreadyExistException(AlreadyExistException e){
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(400,"Error",e.getMessage())
                , HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HandleException.class)
    public ResponseEntity<StandardResponse> handleAllException(HandleException e){
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(500,"Error",e.getMessage())
                , HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<StandardResponse> handleDataAccessException(DataAccessException e){
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(500,"Error",e.getMessage())
                , HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
