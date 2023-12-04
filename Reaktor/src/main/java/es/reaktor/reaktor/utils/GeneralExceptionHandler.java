package es.reaktor.reaktor.utils;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * * Handle all exceptions and java bean validation errors for all endpoints' income data that use the @Valid annotation
 *
 * @author Ehab Qadah
 */
@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler
{

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request)
    {
        log.debug("handleMethodArgumentNotValid. exception: [{}]", exception.toString());
        List<String> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request, validationErrors,true);
    }


    /**
     * ConstraintViolation exception to get info from exception and show this info.
     *
     * @param exception ConstraintViolation
     * @param request   request
     * @return ResponseEntity info error
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException exception, WebRequest request)
    {
        log.debug("handleConstraintViolation. exception: [{}]", exception.toString());
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request, List.of(exception.getMessage()),true);
    }

    /**
     * ResponseStatusException exception to get info from exception and show this info.
     *
     * @param exception ResponseStatus
     * @param request  request
     * @return ResponseEntity info error
     */
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Object> handleResponseStatusException(
            ResponseStatusException exception, WebRequest request)
    {
        log.debug("handleResponseStatusException. exception: [{}]", exception.toString());
        return getExceptionResponseEntity(HttpStatus.valueOf(exception.getStatusCode().value()), request,
                exception.getReason() == null ? List.of() : List.of(exception.getReason()),
                true);
    }

    /**
     * AccessDeniedException exception to get info from exception and show this info.
     * AccessDeniedException  403 FORBIDDEN
     *
     * @param exception ResponseStatus
     * @param request   request
     * @return ResponseEntity info error
     */
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedExceptionException(
            AccessDeniedException exception, WebRequest request)
    {
        log.debug("accessDeniedExceptionException. exception: [{}]", exception.toString());
        return getExceptionResponseEntity(HttpStatus.FORBIDDEN, request,
                Collections.singletonList("You don't have permission to perform this action"),
                true);
    }

    /**
     * Generic exception to get info from exception and show this info.
     *
     * @param exception exception
     * @param request   request
     * @return ResponseEntity
     * <p>
     * i.e trying to delete an entity does not exist id:  199898.
     * * {
     * *   "TIMESTAMP": "2022-01-18T11:06:55.605790700Z",
     * *   "STATUS": 400,
     * *   "ERRORS": "EntityRepresentationModel not found!",
     * *   "PATH": "uri=/profile/permissions/199898",
     * *   "MESSAGE": "Bad Request"
     * * }
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleExceptionGeneric
    (
            Exception exception, WebRequest request
    )
    {
        log.debug("handleExceptionGeneric. exception: [{}]", exception.toString());
        log.debug("handleExceptionGeneric. exception", exception);
        String error;
        try
        {
            // try to get ConstraintViolationException error
            error = exception.getCause().getCause().getMessage();
            // error has implementation detail, so let to generate an error message without this detail
            if (error.contains("value no permitted"))
            {
                error = "value no permitted";
            }
        }
        catch (Exception e)
        {
            // is not a ConstraintViolationException, get the message from parameter exception
            error = exception.getMessage();
        }
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request,
                error == null ? List.of() : List.of(error),true);
    }

    private ResponseEntity<Object> getExceptionResponseEntity(HttpStatus status, WebRequest request,
                                                              List<String> errors, boolean showError)
    {
        // avoid error in test. check if null.
        if (status == null)
        {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        final Map<String, Object> body = new LinkedHashMap<>();
        final String errorsMessage = CollectionUtils.isNotEmpty(errors) ? errors.stream().filter(StringUtils::isNotEmpty).collect(Collectors.joining(",")) : status.getReasonPhrase();
        final String path = request.getDescription(false);
        body.put("TIMESTAMP", Instant.now());
        body.put("STATUS", status.value());
        body.put("ERRORS", errorsMessage);
        body.put("PATH", path);
        body.put("MESSAGE", status.getReasonPhrase());
        return new ResponseEntity<>(body, status);
    }
}