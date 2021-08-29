package ru.hiddenproject.feelmeserver.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.core.io.ClassPathResource;
import ru.hiddenproject.feelmeserver.dto.NotificationDto;
import ru.hiddenproject.feelmeserver.exception.NotificationException;
import ru.hiddenproject.feelmeserver.service.NotificationService;

import java.io.IOException;

public class NotificationServiceImpl implements NotificationService {

    @Override
    public void send(NotificationDto notificationDto, String token) throws NotificationException {
        try {
            Notification notification = Notification
                    .builder()
                    .setTitle(notificationDto.getSubject())
                    .setBody(notificationDto.getContent())
                    .build();

            Message message = Message
                    .builder()
                    .setToken(token)
                    .setNotification(notification)
                    .putAllData(notificationDto.getData())
                    .build();
            firebaseMessaging().sendAsync(message);
        } catch (IOException e) {
            throw new NotificationException("Internal error. Account resource not found");
        }
    }

    private FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "FeelMe Wear");
        return FirebaseMessaging.getInstance(app);
    }
}
