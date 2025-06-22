package com.alessandra_alessandro.ketchapp.utils;

import com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse.PlanBuilderResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeminiApi {
    public static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/";
    public static final String API_KEY = System.getenv("GEMINI_API_KEY");
    public static final String MODEL = "gemini-1.5-flash-latest";
    public static final String ENDPOINT = BASE_URL + MODEL + ":generateContent?key=" + API_KEY;

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static PlanBuilderRequestDto ask(PlanBuilderResponseDto dto) {
        assert dto != null : "Request DTO cannot be null";
        if (API_KEY == null || API_KEY.isEmpty()) {
            System.err.println("Error: GEMINI_API_KEY environment variable not set.");
            return null;
        }

        String session = getSessionFromDto(dto);
        String pause = getPauseFromDto(dto);
        String question = buildQuestion(session, pause);

        try {
            String dtoJson = OBJECT_MAPPER.writeValueAsString(dto);
            String jsonPayload = buildGeminiPayload(dtoJson, question);
            HttpRequest request = buildHttpRequest(jsonPayload);
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("Error: Gemini API returned status code " + response.statusCode());
                System.err.println("Response body: " + response.body());
                return null;
            }
            return extractDtoFromGeminiResponse(response.body());
        } catch (Exception e) {
            System.err.println("Error in GeminiApi.ask: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static String getSessionFromDto(PlanBuilderResponseDto dto) {
        return dto.getConfig() != null && dto.getConfig().getSession() != null ? dto.getConfig().getSession() : "N/A";
    }

    private static String getPauseFromDto(PlanBuilderResponseDto dto) {
        return dto.getConfig() != null && dto.getConfig().getPause() != null ? dto.getConfig().getPause() : "N/A";
    }

    private static String buildQuestion(String session, String pause) {
        return String.format("""
                        
                        Osserva gli eventi che hai nel calendar e basandoti su quelli creami un piano di studio che mi permetta di studiare senza sovrapporsi agli impegni che ho.
                        
                        La durata di ogni sessione di studio dura %s e la pausa %s.
                        
                        Tieni un margine di 30 minuti prima e dopo ogni evento nel calendar.
                        
                        Ritornami solo ed esclusivamente la struttura JSON richiesta.""",
                session, pause);
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
        generationConfig.set("responseSchema", buildResponseSchema());

        payload.set("contents", contentsArray);
        payload.set("generationConfig", generationConfig);
        return OBJECT_MAPPER.writeValueAsString(payload);
    }

    private static ObjectNode buildResponseSchema() {
        // Build the schema using the GeminiResponseSchema class structure
        ObjectNode mainProperties = OBJECT_MAPPER.createObjectNode();

        // CalendarItem schema
        ObjectNode calendarItemProperties = OBJECT_MAPPER.createObjectNode();
        calendarItemProperties.set("title", createPropertySchema("string"));
        calendarItemProperties.set("start_at", createPropertySchema("string"));
        calendarItemProperties.set("end_at", createPropertySchema("string"));
        ObjectNode calendarItemSchema = createObjectSchema(calendarItemProperties, "title", "start_at", "end_at");

        // TomatoItem schema
        ObjectNode tomatoItemProperties = OBJECT_MAPPER.createObjectNode();
        tomatoItemProperties.set("title", createPropertySchema("string"));
        tomatoItemProperties.set("start_at", createPropertySchema("string"));
        tomatoItemProperties.set("end_at", createPropertySchema("string"));
        tomatoItemProperties.set("pause_end_at", createPropertySchema("string"));
        tomatoItemProperties.set("subject", createPropertySchema("string"));
        ObjectNode tomatoItemSchema = createObjectSchema(tomatoItemProperties, "title", "start_at", "end_at", "pause_end_at", "subject");

        // Main schema
        mainProperties.set("calendar", createArraySchema(calendarItemSchema));
        mainProperties.set("tomatoes", createArraySchema(tomatoItemSchema));

        return createObjectSchema(mainProperties, "calendar", "tomatoes");
    }

    private static ObjectNode createPropertySchema(String type) {
        ObjectNode schema = OBJECT_MAPPER.createObjectNode();
        schema.put("type", type);
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
        if (required.size() > 0) {
            schema.set("required", required);
        }
        return schema;
    }

    private static HttpRequest buildHttpRequest(String jsonPayload) {
        return HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
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
            System.err.println("Could not extract DTO from Gemini API response: " + responseBody);
        } catch (Exception e) {
            System.err.println("Error parsing Gemini API response: " + e.getMessage());
        }
        return null;
    }
}