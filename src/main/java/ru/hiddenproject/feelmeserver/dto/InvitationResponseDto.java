package ru.hiddenproject.feelmeserver.dto;

public class InvitationResponseDto {

    private String login;
    private Long id;

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
}
