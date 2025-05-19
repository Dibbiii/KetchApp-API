/*package com.alessandra_alessandro.ketchapp.service;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleCalendarService {

    private static final String APPLICATION_NAME = "KetchApp";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = "src/main/resources/testalebrutto-f88563a203fc.json";
    private static final String CALENDAR_SCOPE = "https://www.googleapis.com/auth/calendar";
    // Si consiglia di usare l'email del calendario condiviso o il relativo ID.
    private static final String CALENDAR_ID = "primary"; 

    private Calendar getCalendarService() throws IOException, GeneralSecurityException {
        try (InputStream in = new FileInputStream(SERVICE_ACCOUNT_KEY_PATH)) {
            GoogleCredential credential = GoogleCredential.fromStream(in)
                    .createScoped(Collections.singleton(CALENDAR_SCOPE));
            return new Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        }
    }

    public Events listEvents() {
        try {
            Calendar service = getCalendarService();
            return service.events().list(CALENDAR_ID).setMaxResults(10).execute();
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Errore durante la lettura degli eventi", e);
        }
    }

    public Event createEvent(Event event) {
        try {
            Calendar service = getCalendarService();
            return service.events().insert(CALENDAR_ID, event).execute();
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Errore durante la creazione dell'evento", e);
        }
    }
}*/