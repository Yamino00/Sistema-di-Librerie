package com.federico.libreria.common.utils;

public class Constants {

    private Constants() {}

    // Constant for the header to propagate.
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_X_AUTH_TOKEN = "x-auth-token";
    public static final String TENANT_HEADER = "X-TENANT-ID";

    // Predefined messages for logging errors and successes.
    public static final String CLIENT_ERROR_MSG = "[ERROR]:: Client error: {} {}";
    public static final String SERVER_ERROR_MSG = "[ERROR]:: Server error: {} {}";
    public static final String RESPONSE_SUCCESS_MSG = "[RESPONSE]:: Response received successfully";
    public static final String ERROR_OCCURRED_MSG = "[ERROR]:: Error occurred";

    public static final String HEADER_MISSING_ERROR_MSG = "Missing required security header: ";

    public static final String OK = "OK";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String FORBIDDEN = "Forbidden";
    public static final String BAD_REQUEST = "Bad Request";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
}
