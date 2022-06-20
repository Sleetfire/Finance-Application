package by.it.academy.classifier_service.models.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    protected LocalDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    public LocalDateTimeDeserializer() {
        this(null);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonParser.getValueAsLong()),
                TimeZone.getDefault().toZoneId());
    }
}
