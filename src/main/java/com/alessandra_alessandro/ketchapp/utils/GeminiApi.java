package com.alessandra_alessandro.ketchapp.utils;

import com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse.PlanBuilderResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeminiApi {
    public static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/";
    public static final String API_KEY = "AIzaSyB6Ifh2frXZ2icVrS30F4TjP49mAhFVQK0";
    public static final String MODEL = "gemini-2.5-flash-preview-05-20";
    public static final String ENDPOINT = BASE_URL + MODEL + ":generateContent?key=" + API_KEY;
    public static final String RETURN_JSON = "{\n" +
                                             "  'calendar': [\n" +
                                             "    {\n" +
                                             "      'title': 'Morning Routine',\n" +
                                             "      'start_at': '7:30',\n" +
                                             "      'end_at': '8:00'\n" +
                                             "    }\n" +
                                             "  ],\n" +
                                             "  'tomatoes': [\n" +
                                             "    {\n" +
                                             "      'title': 'Work on project',\n" +
                                             "      'start_at': '09:00',\n" +
                                             "      'end_at': '11:00',\n" +
                                             "      'pause_end_at': '11:10',\n" +
                                             "      'subject': 'Development'\n" +
                                             "    },\n" +
                                             "    {\n" +
                                             "      'title': 'Team meeting',\n" +
                                             "      'start_at': '11:30',\n" +
                                             "      'end_at': '12:30',\n" +
                                             "      'pause_end_at': '12:40',\n" +
                                             "      'subject': 'Collaboration'\n" +
                                             "    }\n" +
                                             "  ],\n" +
                                             "  'rules': [\n" +
                                             "    {\n" +
                                             "      'title': 'Launch',\n" +
                                             "      'start_at': '13:00',\n" +
                                             "      'end_at': '14:00'\n" +
                                             "    },\n" +
                                             "    {\n" +
                                             "      'title': 'Dinner',\n" +
                                             "      'start_at': '18:00',\n" +
                                             "      'end_at': '19:00'\n" +
                                             "    },\n" +
                                             "    {\n" +
                                             "      'title': 'Sleep',\n" +
                                             "      'start_at': '22:00',\n" +
                                             "      'end_at': '07:00'\n" +
                                             "    }\n" +
                                             "  ]\n" +
                                             "}";


    public static <T> T ask(PlanBuilderResponseDto dto) {
        assert dto != null : "Request DTO cannot be null";
        ObjectMapper objectMapper = new ObjectMapper();

        String session = getSessionFromDto(dto);
        String pause = getPauseFromDto(dto);
        String question = buildQuestion(session, pause);

        try {
            String dtoJson = objectMapper.writeValueAsString(dto);
            String jsonPayload = buildGeminiPayload(dtoJson, question + RETURN_JSON, objectMapper);
            HttpRequest request = buildHttpRequest(jsonPayload);
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.err.println("Error: Gemini API returned status code " + response.statusCode());
                System.err.println("Response body: " + response.body());
                return null;
            }
            return extractDtoFromGeminiResponse(response.body(), objectMapper);
        } catch (Exception e) {
            System.err.println("Error in GeminiApi.ask: " + e.getMessage());
        }
        return null;
    }

    private static String getSessionFromDto(PlanBuilderResponseDto dto) {
        return dto.getConfig() != null && dto.getConfig().getSession() != null ? dto.getConfig().getSession() : null;
    }

    private static String getPauseFromDto(PlanBuilderResponseDto dto) {
        return dto.getConfig() != null && dto.getConfig().getPause() != null ? dto.getConfig().getPause() : null;
    }

    private static String buildQuestion(String session, String pause) {
        return "\nOsserva gli eventi che hai nel calendar e basandoti su quelli creami un piano di studio che mi permetta di studiare senza sovrapporsi agli impegni che ho.\n" +
               "\nLa durata di ogni sessione di studio dura " + session + " e la pausa " + pause + ".\n" +
               "\nTieni un margine di 30 minuti prima e dopo ogni evento nel calendar e rules.\n" +
               "\nRitornami una struttura simile a questa:";
    }

    private static String buildGeminiPayload(String dtoJson, String question, ObjectMapper objectMapper) throws JsonProcessingException {
        ObjectNode partNode = objectMapper.createObjectNode();
        partNode.put("text", dtoJson + question);
        ArrayNode partsArray = objectMapper.createArrayNode();
        partsArray.add(partNode);
        ObjectNode contentNode = objectMapper.createObjectNode();
        contentNode.set("parts", partsArray);
        ArrayNode contentsArray = objectMapper.createArrayNode();
        contentsArray.add(contentNode);
        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("contents", contentsArray);
        return objectMapper.writeValueAsString(payload);
    }

    private static HttpRequest buildHttpRequest(String jsonPayload) {
        return HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();
    }

    private static <T> T extractDtoFromGeminiResponse(String responseBody, ObjectMapper objectMapper) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode textNode = root.path("candidates").get(0).path("content").path("parts");
            if (textNode.isArray() && !textNode.isEmpty()) {
                String text = textNode.get(0).path("text").asText();
                int startIdx = text.indexOf('{');
                int endIdx = text.lastIndexOf('}');
                if (startIdx != -1 && endIdx != -1 && endIdx > startIdx) {
                    String dtoJsonResp = text.substring(startIdx, endIdx + 1);
                    ObjectMapper om = new ObjectMapper();
                    JsonNode node = om.readTree(dtoJsonResp);
                    if (node instanceof ObjectNode) {
                        ((ObjectNode) node).remove("config");
                    }
                    return (T) om.treeToValue(node, PlanBuilderRequestDto.class);
                }
            }
            System.err.println("Could not extract PlanBuilderRequestDto from Gemini API response");
        } catch (Exception e) {
            System.err.println("Error cleaning config from JSON: " + e.getMessage());
        }
        return null;
    }
}