package com.w.ever.files.manager.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationsService {

    // Send notification to a single user
    @Transactional
    public void sendNotificationToUser(String token, String title, String body) {
        try {
            // Create notification for a single user
            Notification notification = Notification.builder().setTitle(title).setBody(body).build();

            // Create the message
            Message message = Message.builder().setNotification(notification).setToken(token)  // Sending to a specific device token
                    .build();

            // Send the message
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message to user: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Send notification to a group of users (topic)
    @Transactional
    public void sendNotificationToGroup(String topic, String title, String body) {
        try {
            // Create notification for a group of users
            Notification notification = Notification.builder().setTitle(title).setBody(body).build();

            // Create the message
            Message message = Message.builder().setNotification(notification).setTopic(topic)  // Sending to a topic
                    .build();

            // Send the message
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message to group: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
