package ru.hiddenproject.feelmeserver;

/**
 * Mapping urls for controllers {@link ru.hiddenproject.feelmeserver.controller}
 */
public interface Url {

    /**
     * API endpoint
     */
    String API = "/api";

    /**
     * API version
     */
    String API_VERSION = "v1";

    /**
     * API full path
     */
    String API_PATH = API + "/" + API_VERSION;

    /**
     * Mapping urls for {@link ru.hiddenproject.feelmeserver.controller.UserController}
     */
    interface USER {
        /**
         * Main endpoint
         */
        String ENDPOINT = "/user";

        /**
         * Registration endpoint
         */
        String REGISTER = "/register";

        /**
         * Invitation endpoint
         */
        String INVITE = "/invite";

        /**
         * Invitation acceptance endpoint
         */
        String ACCEPT = "/accept";

        /**
         * Invitation reject endpoint
         */
        String REJECT = "/reject";

        /**
         * All pending invitations endpoint
         */
        String PENDING_LIST = "/pending";
    }

    /**
     * Mapping urls for
     */
    interface NOTIFICATION {
        /**
         * Main endpoint
         */
        String ENDPOINT = "/notification";

        /**
         * Notification sending endpoint
         */
        String SEND = "/send";
    }

}
