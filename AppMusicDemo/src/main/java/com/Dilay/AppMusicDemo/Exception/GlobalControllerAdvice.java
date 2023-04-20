package com.Dilay.AppMusicDemo.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

//    @ExceptionHandler(value = NotFindException.class)
//    public ResponseEntity<String> handleBusinessException(NotFindException e) {
//
//        log.error("NotFindException: " + e.getMessage());
//
//        String responseMessage = "EXCEPTION: "+ HttpStatus.valueOf(e.getStatusCode()) + "MESSAGE :"+ e.getMessage();
//
//        return new ResponseEntity<>(
//                responseMessage),);
//
//    }
//
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    protected ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//
//        log.error("MethodArgumentNotValidException: " + e.getMessage());
//
//        return new ResponseEntity<>(
//                new ErrorCode(
//                        HttpStatus.BAD_REQUEST,
//                        e.getMessage()),
//                HttpStatus.BAD_REQUEST);
//
//    }
//
//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<String> handleException(Exception e) {
//        log.error("Exception: " + e.getMessage());
//        return new ResponseEntity<>(
//                new ErrorCode(
//                        HttpStatus.INTERNAL_SERVER_ERROR,
//                        e.getMessage(),
//                        Arrays.toString(e.getStackTrace())),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
