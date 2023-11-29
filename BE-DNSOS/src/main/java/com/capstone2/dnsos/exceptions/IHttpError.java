package com.capstone2.dnsos.exceptions;

import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.exceptions.exception.NullPointerException;
import com.capstone2.dnsos.responses.ResponsesEntity;
import org.springframework.http.ResponseEntity;

public interface IHttpError {

    ResponseEntity<ResponsesEntity> handleNotFoundException(NotFoundException e, String mess);

    ResponseEntity<ResponsesEntity> handleDuplicatedException(DuplicatedException e, String mess);

    ResponseEntity<ResponsesEntity> handleInvalidParamException(InvalidParamException e, String mess);


//    ResponseEntity<ResponsesEntity> handleAllNullPointerException(NullPointerException e, String mess);

}
