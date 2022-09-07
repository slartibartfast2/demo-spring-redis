package ea.slartibarfast.demo.redis.configuration.interceptor;

import ea.slartibarfast.demo.redis.model.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(value = {CannotAcquireLockException.class})
    public ResponseEntity<ErrorResponse> handleUrlNotFoundException(CannotAcquireLockException ex) {
        log.warn("Caught exception", ex);
        ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }
}
