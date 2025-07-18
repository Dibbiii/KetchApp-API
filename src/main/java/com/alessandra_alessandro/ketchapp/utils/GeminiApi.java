package com.alessandra_alessandro.ketchapp.utils;

import com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse.PlanBuilderResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Component
public class GeminiApi {
    public static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/";
    private final String apiKey;
    public static final String MODEL = "gemini-2.5-flash";
    private final String endpoint;
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(GeminiApi.class);

    public GeminiApi(@Value("${GEMINI_API_KEY}") String apiKey) {
        this.apiKey = apiKey;
        this.endpoint = BASE_URL + MODEL + ":generateContent?key=" + apiKey;
    }

    public PlanBuilderRequestDto ask(PlanBuilderResponseDto dto) {
        assert dto != null : "Request DTO cannot be null";
        if (apiKey == null || apiKey.isEmpty()) {
            log.error("Error: GEMINI_API_KEY property not set in application.properties.");
            return null;
        }
        String session = getSessionFromDto(dto);
        String pause = getPauseFromDto(dto);
        String question = buildQuestion(session, pause, dto);

        try {
            String dtoJson = OBJECT_MAPPER.writeValueAsString(dto);
            String jsonPayload = buildGeminiPayload(dtoJson, question);
            HttpRequest request = buildHttpRequest(jsonPayload);
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.error("Error: Gemini API returned status code {}", response.statusCode());
                return null;
            }
            return extractDtoFromGeminiResponse(response.body());
        } catch (Exception e) {
            log.error("Error in GeminiApi.ask: {}", e.getMessage());
        }
        return null;
    }

    private static String getSessionFromDto(PlanBuilderResponseDto dto) {
        return dto.getSession() != null ? dto.getSession() : "N/A";
    }

    private static String getPauseFromDto(PlanBuilderResponseDto dto) {
        return dto.getBreakDuration() != null ? dto.getBreakDuration() : "N/A";
    }

    private static String buildQuestion(String session, String pause, PlanBuilderResponseDto dto) {
        StringBuilder subjectsInfo = new StringBuilder();
        if (dto.getSubjects() != null && !dto.getSubjects().isEmpty()) {
            subjectsInfo.append("Le materie da studiare sono:\n");
            for (var subject : dto.getSubjects()) {
                subjectsInfo.append("- ")
                        .append(subject.getName())
                        .append("\n");
            }
        }
        return String.format("""
                        La data di oggi è %s.
                        Osserva gli eventi che hai nel calendar e basandoti su quelli creami un piano di studio che mi permetta di studiare senza sovrapporsi agli impegni che ho.
                        
                        Oggi é: %s
                        La durata di ogni sessione di studio dura %s e la pausa %s.
                        Tieni un margine di 30 minuti prima e dopo ogni evento nel calendar.
                        Ricordati che Start_at, End_at e Pause_end_at sono in formato ISO 8601 (YYYY-MM-DDTHH:MM:SSZ).""",
                LocalDate.now(),
                subjectsInfo,
                session,
                pause);
    }

    private static String buildGeminiPayload(String dtoJson, String question) throws JsonProcessingException {
        ObjectNode payload = OBJECT_MAPPER.createObjectNode();
        ArrayNode contentsArray = OBJECT_MAPPER.createArrayNode();
        ObjectNode contentNode = OBJECT_MAPPER.createObjectNode();
        ArrayNode partsArray = OBJECT_MAPPER.createArrayNode();
        ObjectNode partNode = OBJECT_MAPPER.createObjectNode();

        partNode.put("text", dtoJson + question);
        partsArray.add(partNode);
        contentNode.put("role", "user");
        contentNode.set("parts", partsArray);
        contentsArray.add(contentNode);

        ObjectNode generationConfig = OBJECT_MAPPER.createObjectNode();
        generationConfig.put("responseMimeType", "application/json");
        generationConfig.put("temperature", 1);
        generationConfig.set("responseSchema", buildResponseSchema());

        payload.set("contents", contentsArray);
        payload.set("generationConfig", generationConfig);
        return OBJECT_MAPPER.writeValueAsString(payload);
    }

    private static ObjectNode buildResponseSchema() {
        ObjectNode mainProperties = OBJECT_MAPPER.createObjectNode();

        ObjectNode calendarItemProperties = OBJECT_MAPPER.createObjectNode();
        calendarItemProperties.set("title", createPropertySchema());
        calendarItemProperties.set("start_at", createPropertySchema());
        calendarItemProperties.set("end_at", createPropertySchema());
        ObjectNode calendarItemSchema = createObjectSchema(calendarItemProperties, "title", "start_at", "end_at");

        ObjectNode tomatoItemProperties = OBJECT_MAPPER.createObjectNode();
        tomatoItemProperties.set("start_at", createPropertySchema());
        tomatoItemProperties.set("end_at", createPropertySchema());
        tomatoItemProperties.set("pause_end_at", createPropertySchema());
        ObjectNode tomatoItemSchema = createObjectSchema(tomatoItemProperties, "start_at", "end_at", "pause_end_at");

        ObjectNode subjectItemProperties = OBJECT_MAPPER.createObjectNode();
        subjectItemProperties.set("name", createPropertySchema());
        subjectItemProperties.set("tomatoes", createArraySchema(tomatoItemSchema));
        ObjectNode subjectItemSchema = createObjectSchema(subjectItemProperties, "name", "tomatoes");

        mainProperties.set("calendar", createArraySchema(calendarItemSchema));
        mainProperties.set("subjects", createArraySchema(subjectItemSchema));

        return createObjectSchema(mainProperties, "calendar", "subjects");
    }

    private static ObjectNode createPropertySchema() {
        ObjectNode schema = OBJECT_MAPPER.createObjectNode();
        schema.put("type", "string");
        return schema;
    }

    private static ObjectNode createArraySchema(ObjectNode itemSchema) {
        ObjectNode schema = OBJECT_MAPPER.createObjectNode();
        schema.put("type", "array");
        schema.set("items", itemSchema);
        return schema;
    }

    private static ObjectNode createObjectSchema(ObjectNode properties, String... requiredFields) {
        ObjectNode schema = OBJECT_MAPPER.createObjectNode();
        schema.put("type", "object");
        schema.set("properties", properties);
        ArrayNode required = OBJECT_MAPPER.createArrayNode();
        for (String field : requiredFields) {
            required.add(field);
        }
        if (!required.isEmpty()) {
            schema.set("required", required);
        }
        return schema;
    }

    private HttpRequest buildHttpRequest(String jsonPayload) {
        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();
    }

    private static PlanBuilderRequestDto extractDtoFromGeminiResponse(String responseBody) {
        try {
            JsonNode root = OBJECT_MAPPER.readTree(responseBody);
            JsonNode candidates = root.path("candidates");
            if (candidates.isArray() && !candidates.isEmpty()) {
                JsonNode parts = candidates.get(0).path("content").path("parts");
                if (parts.isArray() && !parts.isEmpty()) {
                    String jsonText = parts.get(0).path("text").asText();
                    return OBJECT_MAPPER.readValue(jsonText, PlanBuilderRequestDto.class);
                }
            }
            log.error("Could not extract DTO from Gemini API response: {}", responseBody);
        } catch (Exception e) {
            log.error("Error parsing Gemini API response: {}", e.getMessage());
        }
        return null;
    }
}
