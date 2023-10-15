package com.example.sellars.controller;

import com.example.sellars.exeption.FeignClientException;
import com.example.sellars.exeption.NotFoundException;
import com.example.sellars.exeption.ValidationException;
import com.example.sellars.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(
                String.format("Ошибка валидации:  \"%s\".", e.getMessage())
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleFeignException(final FeignClientException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(
                String.format("Ошибка отправления запроса микросервису комментариев:  \"%s\".",
                        e.getMessage())
        );
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(
                e.getMessage()
        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidExeption(MethodArgumentNotValidException ex) {
        List<String> exeptions = new ArrayList<>();
        ex.getAllErrors().forEach(err -> {
            log.warn(err.getDefaultMessage());
            exeptions.add(err.getDefaultMessage());
        });
        return new ErrorResponse(String.join(", ", exeptions));
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        log.error("Error: ", e);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stack = sw.toString();
        return new ErrorResponse(
                "Произошла непредвиденная ошибка.", stack
        );
    }
}
