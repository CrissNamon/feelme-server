package ru.hiddenproject.feelmeserver.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents notification data
 */
public class NotificationDto {

    private String subject;

    private String content;

    private Map<String, String> data;

    /**
     * Default constructor
     */
    public NotificationDto() {
        data = new HashMap<>();
    }

    /**
     * @return Notification subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets notification subject
     * @param subject Notification subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return Notification body
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets notification body content
     * @param content Notification body
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return Notification data as key => value
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * Sets notification data
     * @param data Notification data
     */
    public void setData(Map<String, String> data) {
        this.data = data;
    }

    /**
     * Adds param to existing data
     * @param key Param key
     * @param value Param value
     */
    public void putData(String key, String value) {
        this.data.put(key, value);
    }
}
