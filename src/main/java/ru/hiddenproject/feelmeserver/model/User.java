package ru.hiddenproject.feelmeserver.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * User model
 */
@Entity(name = "app_user")
public class User {

    /**
     * User id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * User login
     */
    @Column(name = "login", nullable = false)
    @NotBlank(message = "User login can't be empty")
    private String login;

    /**
     * Unique device ID
     */
    @Column(name = "device_uid")
    @NotBlank(message = "User's device UID can't be empty")
    private String deviceUID;

    /**
     * Device token for Firebase Cloud Messaging
     */
    @Column(name = "firebase_token")
    private String firebaseToken;

    /**
     * Auth token
     */
    @Column(name = "token")
    private String token;

    /**
     * Invite code
     */
    @Column(name = "code")
    private String code;

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

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
