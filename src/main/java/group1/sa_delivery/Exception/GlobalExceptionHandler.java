package group1.sa_delivery.Exception;

import group1.sa_delivery.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
        int code = ex.getStatus().value();
        String message = ex.getReason();
        ApiResponse<Void> error = ApiResponse.error(code, message);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        int code = 400;
        String message = ex.getMessage();
        ApiResponse<Void> error = ApiResponse.error(code, message);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        int code = 403;
        String message = ex.getMessage();
        ApiResponse<Void> error = ApiResponse.error(code, message);
        return ResponseEntity.ok(error);
    }
}
