package ru.hiddenproject.feelmeserver.dto;

public class ResponseDto<V> {

    private String message;

    private V object;

    public ResponseDto(String message, V object) {
        this.message = message;
        this.object = object;
    }

    public ResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public V getObject() {
        return object;
    }

    public void setObject(V object) {
        this.object = object;
    }
}
