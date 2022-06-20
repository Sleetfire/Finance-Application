package by.it.academy.user_service.models.deserializers;

import by.it.academy.user_service.utils.DateTimeUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    protected LocalDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    public LocalDateTimeDeserializer() {
        this(null);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return DateTimeUtil.convertLongToLocalDateTime(jsonParser.getValueAsLong());
    }
}
