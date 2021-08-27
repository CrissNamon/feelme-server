package ru.hiddenproject.feelmeserver.dto;

public class RegisteredUserDto extends BaseUserDto{

    private String token;
    private String code;

    public RegisteredUserDto() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
