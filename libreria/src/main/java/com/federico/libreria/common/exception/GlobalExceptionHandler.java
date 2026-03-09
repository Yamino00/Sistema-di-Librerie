package com.federico.libreria.common.exception;

import com.federico.libreria.common.exception.exceptions.ApiException;
import com.federico.libreria.common.response.CommonResponse;
import com.federico.libreria.common.utils.ResponseUtils;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse<Void>> onBadRequest(IllegalArgumentException ex) {
        log.error("[ERROR]:: Richiesta non valida: {}", ex.getMessage());
        return ResponseUtils.badRequest(null, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponse<Void>> onJsonParseError(HttpMessageNotReadableException ex) {
        String msg = "Corpo della richiesta non valido: " + ex.getMostSpecificCause().getMessage();
        log.error("[ERROR]:: JSON malformato", ex);
        return ResponseUtils.badRequest(null, msg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Void>> onValidationError(MethodArgumentNotValidException ex) {
        String errors = ex.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        log.error("[ERROR]:: Validazione fallita: {}", errors);
        return ResponseUtils.unprocessableEntity(null, "Dati non validi: " + errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<Void>> onConstraintViolation(ConstraintViolationException ex) {
        String errors = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));

        log.error("[ERROR]:: Vincoli JSR-303 violati: {}", errors);
        return ResponseUtils.badRequest(null, "Vincoli violati: " + errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse<Void>> onAccessDenied(AccessDeniedException ex) {
        log.error("[ERROR]:: Accesso negato", ex);
        return ResponseUtils.forbidden(null, "Non hai i permessi per accedere a questa risorsa");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<CommonResponse<Void>> onIllegalState(IllegalStateException ex) {
        log.error("[ERROR]:: Illegal state", ex);
        return ResponseUtils.internalServerError(null, ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CommonResponse<Void>> onNoSuchElementException(NoSuchElementException ex) {
        log.error("[ERROR]:: Errore interno inatteso. Un valore non risulta presente", ex);
        return ResponseUtils.internalServerError(null, ex.getMessage());
    }

    @ExceptionHandler(AsyncRequestNotUsableException.class)
    public void onAsyncRequestNotUsable(AsyncRequestNotUsableException ex) {
        log.debug("[SSE] Client disconnesso: {}", ex.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<CommonResponse<Void>> onIOException(IOException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("Broken pipe")) {
            log.debug("[SSE] Broken pipe: il client ha chiuso la connessione");
            return ResponseEntity.noContent().build();
        }

        log.error("[ERROR] IOException non gestita", ex);
        return ResponseUtils.internalServerError(null, ex.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<CommonResponse<Object>> onApiException(ApiException ex) {
        log.error("[ERROR]:: Backend error status={} message={}", ex.getStatusCode(), ex.getMessage(), ex);

        String message = ex.getBody() != null && ex.getBody().getMessage() != null
                ? ex.getBody().getMessage()
                : ex.getMessage();

        return ResponseUtils.status(HttpStatus.valueOf(ex.getStatusCode()), null, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> onGeneric(Exception ex) {
        log.error("[ERROR]:: Errore interno inatteso", ex);
        return ResponseUtils.internalServerError(null, "Errore interno del server: " + ex.getMessage());
    }
}