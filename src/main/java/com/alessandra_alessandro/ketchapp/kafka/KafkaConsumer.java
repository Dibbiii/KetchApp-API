package com.alessandra_alessandro.ketchapp.kafka;

import com.alessandra_alessandro.ketchapp.services.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @Autowired
    private EmailServices emailService;

    @KafkaListener(topics = "myTopic", groupId = "myGroup") // Sostituisci con il nome del tuo topic e group id
    public void consume(String message) {
        System.out.println("Consumed message: " + message);

        // Invia l'email
        String recipientEmail = "alessandro.brunoh@gmail.com"; // Sostituisci con l'indirizzo email del destinatario
        String subject = "Kafka Message Received";
        String body = "A message has been received on Kafka: " + message;

        emailService.sendEmail(recipientEmail, subject, body);
    }
}