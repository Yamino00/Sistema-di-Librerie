package com.federico.libreria.common.utils;

import com.federico.libreria.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtils {

    private ResponseUtils() {}

    public static <T> ResponseEntity<CommonResponse<T>> ok(T data, String message) {
        return build(HttpStatus.OK, data, message);
    }

    public static <T> ResponseEntity<CommonResponse<T>> accepted(T data, String message) {
        return build(HttpStatus.ACCEPTED, data, message);
    }

    public static <T> ResponseEntity<CommonResponse<T>> badRequest(T data, String message) {
        return build(HttpStatus.BAD_REQUEST, data, message);
    }

    public static <T> ResponseEntity<CommonResponse<T>> unprocessableEntity(T data, String message) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, data, message);
    }

    public static <T> ResponseEntity<CommonResponse<T>> forbidden(T data, String message) {
        return build(HttpStatus.FORBIDDEN, data, message);
    }

    public static <T> ResponseEntity<CommonResponse<T>> internalServerError(T data, String message) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, data, message);
    }

    public static <T> ResponseEntity<CommonResponse<T>> notImplemented(T data, String message) {
        return build(HttpStatus.NOT_IMPLEMENTED, data, message);
    }

    public static <T> ResponseEntity<CommonResponse<T>> status(HttpStatus status, T data, String message) {
        return build(status, data, message);
    }

    public static <T> ResponseEntity<CommonResponse<T>> fromBackend(ResponseEntity<T> backendResponse, String message) {
        HttpStatus status = HttpStatus.valueOf(backendResponse.getStatusCode().value());
        return build(status, backendResponse.getBody(), message);
    }

    private static <T> ResponseEntity<CommonResponse<T>> build(HttpStatus status, T data, String message) {
        CommonResponse<T> response = CommonResponse.<T>builder()
                .success(status.is2xxSuccessful())
                .message(message)
                .status(status.value())
                .data(data)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}