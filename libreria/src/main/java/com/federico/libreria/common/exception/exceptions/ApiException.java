package com.federico.libreria.common.exception.exceptions;


import com.federico.libreria.common.response.CommonResponse;

/**
 * ApiException is a custom runtime exception that encapsulates HTTP error details
 * returned from backend API calls.
 * <p>
 * It includes the HTTP status code and the response body from the backend,
 * which can be used for logging and debugging purposes.
 */
public class ApiException extends RuntimeException {

    /**
     * The HTTP status code returned by the backend.
     */
    private final int statusCode;

    /**
     * The response body returned by the backend.
     */
    private final CommonResponse<?> body;

    /**
     * Constructs a new ApiException with the specified detail message, HTTP status code, and response body.
     */
    public ApiException(CommonResponse<?> body, int statusCode) {
        super(body != null ? body.getMessage() : "Errore API");
        this.body = body;
        this.statusCode = statusCode;
    }

    /**
     * Returns the HTTP status code associated with this exception.
     *
     * @return the HTTP status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the response body returned by the backend.
     *
     * @return the response body as a String
     */
    public CommonResponse<?> getBody() {
        return body;
    }
}