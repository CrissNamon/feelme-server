package ru.hiddenproject.feelmeserver.dto;

import com.google.gson.reflect.TypeToken;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Type;

public class BaseRequestDto<V> {

    @NotBlank(message = "User token required")
    private String token;

    private V object;

    public BaseRequestDto() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public V getObject() {
        return object;
    }

    public void setObject(V object) {
        this.object = object;
    }

    public Type getObjectType() {
        return new TypeToken<V>(){}.getType();
    }
}
