package com.alessandra_alessandro.ketchapp.utils.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class Schema {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertySchema {
        private String type;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArraySchema {
        private String type = "array";
        private Object items;
        public ArraySchema(Object items) {
            this.items = items;
        }
    }

    @Getter
    @Setter
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
