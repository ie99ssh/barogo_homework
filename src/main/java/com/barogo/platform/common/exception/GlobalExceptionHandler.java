package com.barogo.platform.common.exception;

import com.barogo.platform.common.constants.GlobalConstants;
import com.barogo.platform.common.domain.BaseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Objects;

/**
 * --------------------------------------------------------------------
 * ■전역 예외처리 핸들러 ■sangheon
 * --------------------------------------------------------------------
 **/
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorModel errorModel;

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<BaseModel> commonResException(Exception exception) {

        BaseModel baseModel = new BaseModel();

        log.error("Throwable Exception Stack Trace: {}", ExceptionUtils.getStackTrace(exception));

        String intRetCode = GlobalConstants.COMMON_FAILED_CODE;

        try{
            if (exception instanceof GlobalException) {

                GlobalException globalException = (GlobalException)exception;
                Errors errors = globalException.getErrors();

                errorModel.setCode(errors.getName());
                errorModel.setMessage(errors.getMessage());

            } else {
                errorModel.setCode(intRetCode);
                errorModel.setMessage(exception.getMessage());
            }

        } catch(Exception ex) {
            log.error("GlobalExceptionHandler Exception : {}", ex.getMessage());

            errorModel.setCode(intRetCode);
            errorModel.setMessage(ex.getMessage());

        }

        baseModel.setError(errorModel);
        return new ResponseEntity<>(baseModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, GlobalBadRequestException.class})
    public ResponseEntity<Object> badRequestException(Exception ex) {

        BaseModel baseModel = new BaseModel();

        if (ex instanceof GlobalBadRequestException) {
            GlobalBadRequestException badRequestException = (GlobalBadRequestException)ex;
            errorModel.setCode(badRequestException.getErrors().getName());
            String detailMessage = badRequestException.getDetailMessage();

            if (ObjectUtils.isEmpty(detailMessage)) {
                errorModel.setMessage(badRequestException.getErrors().getMessage());
            } else {
                errorModel.setMessage(badRequestException.getErrors().getMessage() + " " + detailMessage);
            }

        } else {

            BindingResult bindingResult = ((MethodArgumentNotValidException)ex).getBindingResult();
            StringBuilder errorMessage = new StringBuilder("Invalid Request: ");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (int i = 0; i < fieldErrors.size(); i++) {
                FieldError fieldError = fieldErrors.get(i);
                if (i == fieldErrors.size() - 1) {
                    errorMessage.append(fieldError.getField());
                    errorMessage.append(" [");
                    errorMessage.append(fieldError.getDefaultMessage());
                    errorMessage.append("]");
                }
            }
            errorModel.setCode("BAD_REQUEST_BODY");
            errorModel.setMessage(errorMessage.toString());

            log.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        baseModel.setError(errorModel);
        return new ResponseEntity<>(baseModel, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> exception(Exception ex) {

        BaseModel baseModel = new BaseModel();
        errorModel.setCode("BAD_REQUEST_BODY");
        errorModel.setMessage(ex.getMessage());

        baseModel.setError(errorModel);

        log.error("ERROR 상세 : HTTP 400 - BAD REQUEST");
        log.error(ex.getMessage());

        return new ResponseEntity<>(baseModel, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error(ex.getMessage(), ex);

        BaseModel baseModel = new BaseModel();
        errorModel.setCode("BAD_REQUEST_BODY");
        errorModel.setMessage(ex.getMessage());

        baseModel.setError(errorModel);

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class, GlobalNotFoundException.class})
    public ResponseEntity<Object> notFoundException(Exception ex) {

        BaseModel baseModel = new BaseModel();

        if (ex instanceof GlobalNotFoundException) {
            GlobalNotFoundException notFoundException = (GlobalNotFoundException)ex;
            errorModel.setCode(notFoundException.getErrors().getName());

            String detailMessage = notFoundException.getDetailMessage();

            if (ObjectUtils.isEmpty(detailMessage)) {
                errorModel.setMessage(notFoundException.getErrors().getMessage());
            } else {
                errorModel.setMessage(notFoundException.getErrors().getMessage() + " " + detailMessage);
            }


            baseModel.setError(errorModel);

            return new ResponseEntity<>(baseModel, HttpStatus.NOT_FOUND);
        } else {
            log.error("ERROR 상세 : HTTP 404 - NOT FOUND ERROR");
            log.error(ex.getMessage());

            errorModel.setCode("NOT_FOUND_BODY");
            errorModel.setMessage(ex.getMessage());
        }

        baseModel.setError(errorModel);
        return new ResponseEntity<>(baseModel, HttpStatus.NOT_FOUND);
    }
}
