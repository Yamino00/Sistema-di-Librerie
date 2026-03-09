package com.federico.libreria.common.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.federico.libreria.common.utils.Constants.*;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    @Value("${wso2.security.enabled:false}")
    private boolean securityEnabled;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        final String path = request.getRequestURI();
        final String method = request.getMethod();

        // BYPASS: CORS preflight (OPTIONS)
        if (HttpMethod.OPTIONS.matches(method)) {
            return true;
        }

        // BYPASS: Swagger/OpenAPI
        if (isSwaggerPath(path)) {
            return true;
        }

        // BYPASS: Sicurezza disabilitata
        return !securityEnabled;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String path = request.getRequestURI();

        final String authorization = request.getHeader(HEADER_AUTHORIZATION);
        final String tenant = request.getHeader(TENANT_HEADER);

        if (authorization == null || authorization.isBlank()) {
            log.warn("[SecurityFilter] Header mancante: {} (path={})", HEADER_AUTHORIZATION, path);
            unauthorizedResponse(response, HEADER_MISSING_ERROR_MSG + HEADER_AUTHORIZATION);
            return;
        }

        if (tenant == null || tenant.isBlank()) {
            log.warn("[SecurityFilter] Header mancante: {} (path={})", TENANT_HEADER, path);
            unauthorizedResponse(response, HEADER_MISSING_ERROR_MSG + TENANT_HEADER);
            return;
        }

        log.debug("[SecurityFilter] Sicurezza OK, richiesta inoltrata (path: {})", path);
        filterChain.doFilter(request, response);
    }

    private boolean isSwaggerPath(String path) {
        return path.startsWith("/swagger")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/webjars")
                || path.startsWith("/swagger-ui");
    }

    private void unauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        log.error("[SecurityFilter] 401 Unauthorized: {}", message);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.getWriter().write(message);
        response.getWriter().flush();
    }
}