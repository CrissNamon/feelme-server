package ru.hiddenproject.feelmeserver.dto;

import java.util.HashMap;
import java.util.Map;

public class NotificationDto {

    private String subject;

    private String content;

    private Map<String, String> data;


    public NotificationDto() {
        data = new HashMap<>();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public void putData(String key, String value) {
        this.data.put(key, value);
    }
}
