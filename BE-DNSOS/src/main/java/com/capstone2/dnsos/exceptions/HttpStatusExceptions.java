package com.capstone2.dnsos.exceptions;

import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.responses.ResponsesEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class HttpStatusExceptions implements IHttpError {
    /**
     * Xử lý ngoại lệ khi không tìm thấy tài nguyên.
     *
     * @param e    Ngoại lệ NotFoundException.
     * @param mess Thông điệp mô tả lỗi.
     * @return Phản hồi với mã HTTP status 404 Not Found.
     */
    @ExceptionHandler(NotFoundException.class)
    @Override
    public ResponseEntity<ResponsesEntity> handleNotFoundException(NotFoundException e, String mess) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsesEntity(mess, HttpStatus.NOT_FOUND.value(), ""));
    }

    /**
     * Xử lý ngoại lệ khi có sự trùng lặp dữ liệu.
     *
     * @param e    Ngoại lệ DuplicatedException.
     * @param mess Thông điệp mô tả lỗi.
     * @return Phản hồi với mã HTTP status 409 CONFLICT.
     */
    @ExceptionHandler(DuplicatedException.class)
    @Override
    public ResponseEntity<ResponsesEntity> handleDuplicatedException(DuplicatedException e, String mess) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(mess, HttpStatus.CONFLICT.value(), null));
    }

    /**
     * Xử lý ngoại lệ khi tham số đầu vào không hợp lệ.
     *
     * @param e    Ngoại lệ InvalidParamException.
     * @param mess Thông điệp mô tả lỗi.
     * @return Phản hồi với mã HTTP status 400 Bad Request.
     */
    @ExceptionHandler(InvalidParamException.class)
    @Override
    public ResponseEntity<ResponsesEntity> handleInvalidParamException(InvalidParamException e, String mess) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(mess, HttpStatus.BAD_REQUEST.value(), null));
    }

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ResponsesEntity> handleAllNullPointerException(NullPointerException e, String mess) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(mess, HttpStatus.BAD_REQUEST.value(), null));
//    }

//    /**
//     * Xử lý ngoại lệ chung.
//     *
//     * @param e    Ngoại lệ Exception.
//     * @param mess Thông điệp mô tả lỗi.
//     * @return Phản hồi với mã HTTP status 500 Internal Server Error.
//     */
//    @ExceptionHandler(Exception.class)
//    @Override
//    public ResponseEntity<ResponsesEntity> handleAllExceptions(Exception e, String mess) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponsesEntity(mess, HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
//    }
/*
---------------------------Auto try catch  Http Status ---------------------------------------------------------------------------------------------------------
*/

    /*
         Xử lý ngoại lệ dung sai phương thức http method
    */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ResponsesEntity> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ResponsesEntity(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED.value(), null));
    }

    /*
     Xử lý ngoại lệ máy chủ quá tải
   */
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponsesEntity> handleOverloadedServerException(HttpServerErrorException.InternalServerError e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponsesEntity("Server is overloaded", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
    }

    //    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponsesEntity> handleInternalServerError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponsesEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
    }


    /*
     Xử lý ngoại lệ yêu cầu quá lâu 408
    */
    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ResponseEntity<ResponsesEntity> handleRequestTimeoutException(HttpServerErrorException e) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(new ResponsesEntity("The request time has expired", HttpStatus.REQUEST_TIMEOUT.value(), null));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponsesEntity> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "Failed to read request. Malformed JSON or invalid data format.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponsesEntity(errorMessage,HttpStatus.BAD_REQUEST.value(), ""));
    }

}