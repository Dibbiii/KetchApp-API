package com.alessandra_alessandro.ketchapp.models;

import lombok.*;

import java.util.List;
import java.util.Map;

public class Schema {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertySchema {
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArraySchema {
        private String type = "array";
        private Object items;
        public ArraySchema(Object items) {
            this.items = items;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ObjectSchema {
        private String type = "object";
        private Map<String, Object> properties;
        private List<String> required;
        public ObjectSchema(Map<String, Object> properties, List<String> required) {
            this.properties = properties;
            this.required = required;
        }
    }
}
