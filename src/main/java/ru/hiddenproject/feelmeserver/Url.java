package ru.hiddenproject.feelmeserver;

public interface Url {

    String API = "/api";
    String API_VERSION = "v1";
    String API_PATH = API + "/" + API_VERSION;

    interface USER {
        String ENDPOINT = "/user";
        String REGISTER = "/register";
        String INVITE = "/invite";
    }

}
