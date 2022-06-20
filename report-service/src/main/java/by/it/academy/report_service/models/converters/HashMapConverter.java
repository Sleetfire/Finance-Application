package by.it.academy.report_service.models.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        String reportString;
        try {
            reportString = this.objectMapper.writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return reportString;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        Map<String, Object> paramsMap;
        try {
            paramsMap = this.objectMapper.readValue(s, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return paramsMap;
    }
}
