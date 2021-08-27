package ru.hiddenproject.feelmeserver.dto;

import javax.validation.constraints.NotBlank;

public class BaseUserDto {

    @NotBlank(message = "User login can't be empty")
    private String login;

    @NotBlank(message = "User's device UID can't be empty")
    private String deviceUID;

    public BaseUserDto() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDeviceUID() {
        return deviceUID;
    }

    public void setDeviceUID(String deviceUID) {
        this.deviceUID = deviceUID;
    }
}
