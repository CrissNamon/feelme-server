package ru.hiddenproject.feelmeserver.dto;

public class ErrorResponseDto<V> extends ResponseDto<V> {

    public ErrorResponseDto(String message) {
        super(message);
    }
}
