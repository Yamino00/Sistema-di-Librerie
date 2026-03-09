package com.federico.libreria.common.service;

import com.federico.libreria.common.exception.exceptions.ApiException;
import com.federico.libreria.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.federico.libreria.common.utils.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunicationService {

    private static final String REQUEST_GET = "[REQUEST]:: [CommunicationService: httpGET] - Requesting GET for URI: {}";
    private static final String REQUEST_POST = "[REQUEST]:: [CommunicationService: httpPOST] - Requesting POST for URI: {}";
    private static final String REQUEST_PUT = "[REQUEST]:: [CommunicationService: httpPUT] - Requesting PUT for URI: {}";
    private static final String REQUEST_DELETE = "[REQUEST]:: [CommunicationService: httpDELETE] - Requesting DELETE for URI: {}";

    private final RestClient restClient;

    @Value("${seconds.response.timeout}")
    private int secondsResponseTimeOut;

    @Value("${eftigate.propagate-tenant-header:true}")
    private boolean propagateTenantHeader;

    private HttpHeaders buildBackendHeaders(HttpHeaders incomingHeaders) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        // if (propagateTenantHeader && incomingHeaders != null) {
        //     copyHeaderIfPresent(incomingHeaders, headers, "X-Tenant-Id");
        //     copyHeaderIfPresent(incomingHeaders, headers, "X-Correlation-Id");
        // }

        return headers;
    }

    @SuppressWarnings("ConstantConditions")
    private ApiException toApiException(RestClientResponseException ex, String errorPrefix) {
        String rawBody = ex.getResponseBodyAsString(StandardCharsets.UTF_8);

        CommonResponse<?> common;
        try {
            CommonResponse<?> parsed = ex.getResponseBodyAs(CommonResponse.class);
            common = parsed != null
                    ? parsed
                    : CommonResponse.builder()
                    .success(false)
                    .status(ex.getStatusCode().value())
                    .message(rawBody)
                    .data(null)
                    .build();
        } catch (Exception ignore) {
            common = CommonResponse.builder()
                    .success(false)
                    .status(ex.getStatusCode().value())
                    .message(rawBody == null || rawBody.isBlank() ? "Backend error (empty body)" : rawBody)
                    .data(null)
                    .build();
        }

        log.error("{} status={} body={}", errorPrefix, ex.getStatusCode().value(), common);
        return new ApiException(common, ex.getStatusCode().value());
    }

    private RestClient.RequestBodySpec prepareRequest(
            HttpMethod method,
            String uri,
            HttpHeaders incomingHeaders
    ) {
        return restClient
                .method(method)
                .uri(uri)
                .headers(headers -> headers.addAll(buildBackendHeaders(incomingHeaders)));
    }

    private <T> ResponseEntity<T> execute(
            HttpMethod method,
            String uri,
            HttpHeaders incomingHeaders,
            Object body,
            Class<T> responseClass
    ) {
        try {
            RestClient.RequestBodySpec request = prepareRequest(method, uri, incomingHeaders);

            ResponseEntity<T> response = body == null
                    ? request.retrieve().toEntity(responseClass)
                    : request.contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .toEntity(responseClass);

            log.debug(RESPONSE_SUCCESS_MSG);
            return response;

        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                throw toApiException(ex, CLIENT_ERROR_MSG);
            }
            if (ex.getStatusCode().is5xxServerError()) {
                throw toApiException(ex, SERVER_ERROR_MSG);
            }
            throw ex;
        }
    }

    private <T> ResponseEntity<T> execute(
            HttpMethod method,
            String uri,
            HttpHeaders incomingHeaders,
            Object body,
            ParameterizedTypeReference<T> responseType
    ) {
        try {
            RestClient.RequestBodySpec request = prepareRequest(method, uri, incomingHeaders);

            ResponseEntity<T> response = body == null
                    ? request.retrieve().toEntity(responseType)
                    : request.contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .toEntity(responseType);

            log.debug(RESPONSE_SUCCESS_MSG);
            return response;

        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                throw toApiException(ex, CLIENT_ERROR_MSG);
            }
            if (ex.getStatusCode().is5xxServerError()) {
                throw toApiException(ex, SERVER_ERROR_MSG);
            }
            throw ex;
        }
    }

    public <T> ResponseEntity<T> httpGET(
            String uri,
            HttpHeaders incomingHeaders,
            Class<T> responseClass
    ) {
        log.info(REQUEST_GET, uri);
        return execute(HttpMethod.GET, uri, incomingHeaders, null, responseClass);
    }

    public <T> ResponseEntity<T> httpGET(
            String uri,
            HttpHeaders incomingHeaders,
            ParameterizedTypeReference<T> responseType
    ) {
        log.info(REQUEST_GET, uri);
        return execute(HttpMethod.GET, uri, incomingHeaders, null, responseType);
    }

    public <T> ResponseEntity<T> httpPOST(
            String uri,
            HttpHeaders incomingHeaders,
            Object body,
            Class<T> responseClass
    ) {
        log.info(REQUEST_POST, uri);
        return execute(HttpMethod.POST, uri, incomingHeaders, body, responseClass);
    }

    public <T> ResponseEntity<T> httpPOST(
            String uri,
            HttpHeaders incomingHeaders,
            Object body,
            ParameterizedTypeReference<T> responseType
    ) {
        log.info(REQUEST_POST, uri);
        return execute(HttpMethod.POST, uri, incomingHeaders, body, responseType);
    }

    public <T> ResponseEntity<T> httpPUT(
            String uri,
            HttpHeaders incomingHeaders,
            Object body,
            Class<T> responseClass
    ) {
        log.info(REQUEST_PUT, uri);
        return execute(HttpMethod.PUT, uri, incomingHeaders, body, responseClass);
    }

    public <T> ResponseEntity<T> httpPUT(
            String uri,
            HttpHeaders incomingHeaders,
            Object body,
            ParameterizedTypeReference<T> responseType
    ) {
        log.info(REQUEST_PUT, uri);
        return execute(HttpMethod.PUT, uri, incomingHeaders, body, responseType);
    }

    public ResponseEntity<Void> httpDELETE(
            String uri,
            HttpHeaders incomingHeaders
    ) {
        log.info(REQUEST_DELETE, uri);
        return execute(HttpMethod.DELETE, uri, incomingHeaders, null, Void.class);
    }

    public <T> ResponseEntity<T> httpDELETE(
            String uri,
            HttpHeaders incomingHeaders,
            ParameterizedTypeReference<T> responseType
    ) {
        log.info(REQUEST_DELETE, uri);
        return execute(HttpMethod.DELETE, uri, incomingHeaders, null, responseType);
    }
}