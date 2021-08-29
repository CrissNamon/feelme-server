package ru.hiddenproject.feelmeserver.exception;

import org.springframework.http.HttpStatus;

public class RequestException extends Exception{

    private HttpStatus responseStatus;

    public RequestException(String message) {
        super(message);
        responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public RequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.responseStatus = httpStatus;
    }

    public HttpStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(HttpStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
