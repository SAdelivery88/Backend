package group1.sa_delivery.util;


import group1.sa_delivery.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Utility class to standardize response creation across the application.
 * All responses will use HTTP status code 200, with the actual status in the "code" field.
 */
public class ResponseUtil {

    /**
     * Create a success response with data
     *
     * @param data The data to include in the response
     * @param <T>  The type of data
     * @return ResponseEntity with status 200 and data
     */
    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", data));
    }

    /**
     * Create a success response with a message and data
     *
     * @param message The success message
     * @param data    The data to include in the response
     * @param <T>     The type of data
     * @return ResponseEntity with status 200 and data
     */
    public static <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(new ApiResponse<>(200, message, data));
    }

    /**
     * Create a success response with just a message
     *
     * @param message The success message
     * @return ResponseEntity with status 200 and message
     */
    public static ResponseEntity<ApiResponse<Void>> successMessage(String message) {
        return ResponseEntity.ok(ApiResponse.success(message, null));
    }

    /**
     * Create an error response
     *
     * @param errorCode The error code
     * @param message   The error message
     * @return ResponseEntity with status 200 but error code in body
     */
    public static ResponseEntity<ApiResponse<Void>> error(int errorCode, String message) {
        return ResponseEntity.ok(ApiResponse.error(errorCode, message));
    }

    /**
     * Create a standard error response for HTTP 400 (Bad Request)
     *
     * @param message The error message
     * @return ResponseEntity with status 200 but error code 400 in body
     */
    public static ResponseEntity<ApiResponse<Void>> badRequest(String message) {
        return error(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * Create a standard error response for HTTP 401 (Unauthorized)
     *
     * @param message The error message
     * @return ResponseEntity with status 200 but error code 401 in body
     */
    public static ResponseEntity<ApiResponse<Void>> unauthorized(String message) {
        return error(HttpStatus.UNAUTHORIZED.value(), message);
    }

    /**
     * Create a standard error response for HTTP 403 (Forbidden)
     *
     * @param message The error message
     * @return ResponseEntity with status 200 but error code 403 in body
     */
    public static ResponseEntity<ApiResponse<Void>> forbidden(String message) {
        return error(HttpStatus.FORBIDDEN.value(), message);
    }

    /**
     * Create a standard error response for HTTP 404 (Not Found)
     *
     * @param message The error message
     * @return ResponseEntity with status 200 but error code 404 in body
     */
    public static ResponseEntity<ApiResponse<Void>> notFound(String message) {
        return error(HttpStatus.NOT_FOUND.value(), message);
    }

    /**
     * Create a standard error response for HTTP 500 (Internal Server Error)
     *
     * @param message The error message
     * @return ResponseEntity with status 200 but error code 500 in body
     */
    public static ResponseEntity<ApiResponse<Void>> serverError(String message) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }
} 