package com.capstone2.dnsos.exceptions.exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

//    private static final String INTERNAL_ERROR_MSG = "A system error has occurred";
//    public static final String FORBIDDEN = "User doesn't have permission to access this resources";
//
//    private static final Logger LOG = LoggerFactory.getLogger(APIExceptionHandler.class);
//
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public Object handleConstraintViolationException(ConstraintViolationException e) {
//        LOG.error(e.getMessage(), e);
//        return new ErrorDTO(TrayIcon.MessageType.ERROR, INTERNAL_ERROR_MSG, INTERNAL_ERROR_MSG);
//    }
//
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public Object handleUserException(CommonException  e) {
//        LOG.error(e.getMessage(), e);
//        return e.getErrorDTO();
//    }
//
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public Object handleResourceNotFoundException(NotFoundException e) {
//        LOG.error(e.getMessage(), e);
//        return e.getErrorDTO();
//    }
//
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Object handleInvalidParamException(InvalidParamException e) {
//        LOG.error(e.getMessage(), e);
//        return e.getErrorDTO();
//    }
//
//    @ExceptionHandler
//    public void handleInvalidFormatException(InvalidFormatException e) {
//        LOG.error(e.getMessage(), e);
//    }
//
//    @ExceptionHandler
//    public void handleMismatchedInputException(MismatchedInputException e) {
//        LOG.error(e.getMessage(), e);
//    }
//
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Object handleDuplicatedException(DuplicatedException e) {
//        LOG.error(e.getMessage(), e);
//        return e.getErrorDTO();
//    }
//
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Object handleFileStoreException(GitlabException e) {
//        LOG.error(e.getMessage(), e);
//        return e.getErrorDTO();
//    }
//
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public Object handleAllExceptions(Exception e) {
//        LOG.error(e.getMessage(), e);
//        return new ErrorDTO(MessageType.ERROR, INTERNAL_ERROR_MSG, INTERNAL_ERROR_MSG);
//    }
//
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public Object handleAccessDeniedException(AccessDeniedException e) {
//        LOG.error(e.getMessage(), e);
//        return new ErrorDTO(MessageType.WARNING, FORBIDDEN, FORBIDDEN);
//    }
//
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public Object handleAuthenticationException(AuthenticationException e) {
//        LOG.error(e.getMessage(), e);
//        return new ErrorDTO(MessageType.WARNING, HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.toString());
//    }
//
//    @Override
//    public ResponseEntity<Object> handleHttpMessageNotReadable(
//            HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        LOG.error(e.getMessage(), e);
//        return new ResponseEntity<>(new ErrorDTO(MessageType.WARNING, Errors.PARAM_INVALID.getTitle(), Errors.PARAM_INVALID.getText()), HttpStatus.BAD_REQUEST);
//    }

}