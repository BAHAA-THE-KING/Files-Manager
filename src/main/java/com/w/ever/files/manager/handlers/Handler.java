package com.w.ever.files.manager.handlers;
import com.w.ever.files.manager.exceptions.ErrorResponseException;
import com.w.ever.files.manager.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class Handler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ErrorResponse("Validation failed",HttpStatus.UNPROCESSABLE_ENTITY,errors.toArray());
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<?> handleErrorResponse(ErrorResponseException ex) {
        return new ErrorResponse(ex.getCode(),ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGeneralExceptions(Exception ex) throws Exception {
        if(ex instanceof ErrorResponseException e){
            return this.handleErrorResponse(e);
        }
        return new ErrorResponse("Server error",500,ex.getMessage());
    }
}
