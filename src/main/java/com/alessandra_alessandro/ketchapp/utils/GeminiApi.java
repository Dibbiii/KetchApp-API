package com.alessandra_alessandro.ketchapp.utils;

import com.alessandra_alessandro.ketchapp.models.dto.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.PlanBuilderResponseDto;
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

import com.alessandra_alessandro.ketchapp.models.Schema;

import java.util.HashMap;
import java.util.Arrays;

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

    /**
     * Sends a request to the Gemini API using the provided PlanBuilderResponseDto,
     * builds the appropriate question and payload, and returns a PlanBuilderRequestDto
     * based on the API's response.
     *
     * @param dto the PlanBuilderResponseDto containing the user's study plan and calendar data
     * @return a PlanBuilderRequestDto generated from the Gemini API response, or null if an error occurs
     */
    public PlanBuilderRequestDto ask(PlanBuilderResponseDto dto) {
        assert dto != null : "Request DTO cannot be null";
        if (apiKey == null || apiKey.isEmpty()) {
            log.error("Error: GEMINI_API_KEY property not set in application.properties.");
            return null;
        }
        log.debug("Received PlanBuilderResponseDto: {}", dto);
        String session = getSessionFromDto(dto);
        log.debug("Extracted session from DTO: {}", session);
        String pause = getPauseFromDto(dto);
        log.debug("Extracted pause from DTO: {}", pause);
        String question = buildQuestion(session, pause, dto);
        log.debug("Built question for Gemini API: {}", question);

        try {
            String dtoJson = OBJECT_MAPPER.writeValueAsString(dto);
            log.debug("Serialized DTO to JSON: {}", dtoJson);
            String jsonPayload = buildGeminiPayload(dtoJson, question);
            log.debug("Built Gemini API payload: {}", jsonPayload);
            HttpRequest request = buildHttpRequest(jsonPayload);
            log.debug("Sending HTTP request to Gemini API endpoint: {}", endpoint);
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("Received response from Gemini API: status={}, body={}", response.statusCode(), response.body());
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

    /**
     * Retrieves the session duration from the given PlanBuilderResponseDto.
     * Returns "N/A" if the session is null.
     *
     * @param dto the PlanBuilderResponseDto to extract the session from
     * @return the session duration as a String, or "N/A" if not available
     */
    private static String getSessionFromDto(PlanBuilderResponseDto dto) {
        return dto.getSession() != null ? dto.getSession() : "N/A";
    }

    /**
     * Retrieves the break duration from the given PlanBuilderResponseDto.
     * Returns "N/A" if the break duration is null.
     *
     * @param dto the PlanBuilderResponseDto to extract the break duration from
     * @return the break duration as a String, or "N/A" if not available
     */
    private static String getPauseFromDto(PlanBuilderResponseDto dto) {
        return dto.getBreakDuration() != null ? dto.getBreakDuration() : "N/A";
    }

    /**
     * Builds a user question string for the Gemini API based on the session duration,
     * break duration, and the provided PlanBuilderResponseDto.
     *
     * @param session the duration of each study session
     * @param pause   the duration of each break
     * @param dto     the PlanBuilderResponseDto containing subjects and other info
     * @return a formatted question string for the Gemini API
     */
    private static String buildQuestion(String session, String pause, PlanBuilderResponseDto dto) {
        StringBuilder subjectsInfo = new StringBuilder();
        if (dto.getSubjects() != null && !dto.getSubjects().isEmpty()) {
            subjectsInfo.append("Subjects to study:\n");
            for (var subject : dto.getSubjects()) {
                subjectsInfo.append("- ")
                        .append(subject.getName())
                        .append("\n");
            }
        }
        return String.format("""
                        Today's date is %s.
                        Look at the events you have in your calendar and, based on those, create a study plan for me that allows me to study without overlapping with my scheduled commitments.
                        
                        Subjects to study: %s
                        Each study session lasts %s and the break is %s.
                        Leave a margin of 30 minutes before and after each calendar event.
                        Remember that Start_at, End_at, and Pause_end_at are in ISO 8601 format (YYYY-MM-DDTHH:MM:SSZ).""",
                LocalDate.now(),
                subjectsInfo,
                session,
                pause);
    }

    /**
     * Builds the JSON payload to send to the Gemini API.
     * The payload includes the serialized DTO and the user question,
     * as well as the generation configuration and response schema.
     *
     * @param dtoJson  the serialized PlanBuilderResponseDto as a JSON string
     * @param question the question to be sent to the Gemini API
     * @return the complete JSON payload as a string
     * @throws JsonProcessingException if serialization fails
     */
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
        generationConfig.set("responseSchema", OBJECT_MAPPER.valueToTree(buildResponseSchema()));

        payload.set("contents", contentsArray);
        payload.set("generationConfig", generationConfig);
        return OBJECT_MAPPER.writeValueAsString(payload);
    }

    /**
     * Builds the response schema POJO for the Gemini API.
     * This schema defines the expected structure of the response, including
     * calendar items, subjects, and their associated properties.
     *
     * @return an ObjectSchema representing the expected response structure
     */
    private static Schema.ObjectSchema buildResponseSchema() {
        HashMap<String, Object> calendarItemProps = new HashMap<>();
        calendarItemProps.put("title", new Schema.PropertySchema("string"));
        calendarItemProps.put("start_at", new Schema.PropertySchema("string"));
        calendarItemProps.put("end_at", new Schema.PropertySchema("string"));
        Schema.ObjectSchema calendarItemSchema = new Schema.ObjectSchema(calendarItemProps, Arrays.asList("title", "start_at", "end_at"));

        HashMap<String, Object> tomatoItemProps = new HashMap<>();
        tomatoItemProps.put("start_at", new Schema.PropertySchema("string"));
        tomatoItemProps.put("end_at", new Schema.PropertySchema("string"));
        tomatoItemProps.put("pause_end_at", new Schema.PropertySchema("string"));
        Schema.ObjectSchema tomatoItemSchema = new Schema.ObjectSchema(tomatoItemProps, Arrays.asList("start_at", "end_at", "pause_end_at"));

        HashMap<String, Object> subjectItemProps = new HashMap<>();
        subjectItemProps.put("name", new Schema.PropertySchema("string"));
        subjectItemProps.put("tomatoes", new Schema.ArraySchema(tomatoItemSchema));
        Schema.ObjectSchema subjectItemSchema = new Schema.ObjectSchema(subjectItemProps, Arrays.asList("name", "tomatoes"));

        HashMap<String, Object> mainProps = new HashMap<>();
        mainProps.put("calendar", new Schema.ArraySchema(calendarItemSchema));
        mainProps.put("subjects", new Schema.ArraySchema(subjectItemSchema));
        return new Schema.ObjectSchema(mainProps, Arrays.asList("calendar", "subjects"));
    }

    /**
     * Builds an HTTP POST request to the Gemini API endpoint with the given JSON payload.
     *
     * @param jsonPayload the JSON string to send as the request body
     * @return a configured HttpRequest object ready to be sent
     */
    private HttpRequest buildHttpRequest(String jsonPayload) {
        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();
    }

    /**
     * Extracts a PlanBuilderRequestDto from the Gemini API response body.
     * Parses the JSON response, navigates to the relevant content, and deserializes it into a PlanBuilderRequestDto.
     *
     * @param responseBody the raw JSON response from the Gemini API
     * @return the extracted PlanBuilderRequestDto, or null if extraction fails
     */
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
