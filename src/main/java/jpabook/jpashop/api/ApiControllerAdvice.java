package jpabook.jpashop.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult argumentNotValidExHandler(MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException] = {}", e.getMessage());
        return new ErrorResult("MethodArgumentNotValid", e.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResult illegalStateExHandler(IllegalStateException e) {
        log.error("[IllegalStateException] = {}", e.getMessage());
        return new ErrorResult("IllegalStateException", e.getMessage());
    }

}
