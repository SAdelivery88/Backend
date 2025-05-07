package group1.sa_delivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用的 API 响应类，用于封装接口返回的数据，包含响应码、响应消息和响应数据
 * @param <T> 响应数据的类型
 */
@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;       // 响应码，成功返回 200，失败返回其他错误码
    private String message; // 响应消息，用于描述响应的状态信息
    private T data;         // 响应数据，具体业务返回的数据

    /**
     * 全参构造函数，用于创建一个包含所有信息的 ApiResponse 对象
     * @param code 响应码
     * @param message 响应消息
     * @param data 响应数据
     */
    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 创建一个成功的响应对象，包含自定义的响应码、消息和数据
     * @param code 响应码
     * @param message 响应消息
     * @param data 响应数据
     * @param <T> 响应数据的类型
     * @return 成功的 ApiResponse 对象
     */
    public static <T> ApiResponse<T> success(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    /**
     * 创建一个成功的响应对象，使用默认的成功响应码 200 和消息 "Success"，并包含响应数据
     * @param data 响应数据
     * @param <T> 响应数据的类型
     * @return 成功的 ApiResponse 对象
     */
    public static <T> ApiResponse<T> success(T data) {
        return success(200, "Success", data);
    }

    /**
     * 创建一个成功的响应对象，使用默认的成功响应码 200，包含自定义的消息和响应数据
     * @param message 响应消息
     * @param data 响应数据
     * @param <T> 响应数据的类型
     * @return 成功的 ApiResponse 对象
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return success(200, message, data);
    }

    /**
     * 创建一个失败的响应对象，包含自定义的错误码和消息，无响应数据
     * @param code 错误码
     * @param message 错误消息
     * @param <T> 响应数据的类型
     * @return 失败的 ApiResponse 对象
     */
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    /**
     * 创建一个失败的响应对象，使用默认的错误码 500，包含自定义的错误消息，无响应数据
     * @param message 错误消息
     * @param <T> 响应数据的类型
     * @return 失败的 ApiResponse 对象
     */
    public static <T> ApiResponse<T> error(String message) {
        return error(500, message);
    }
}