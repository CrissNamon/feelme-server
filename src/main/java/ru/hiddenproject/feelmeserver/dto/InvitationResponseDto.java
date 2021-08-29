package ru.hiddenproject.feelmeserver.dto;

import ru.hiddenproject.feelmeserver.enums.InvitationStatus;

public class InvitationResponseDto {

    private String login;

    private Long id;

    private InvitationStatus status;

    public InvitationResponseDto() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }
}
