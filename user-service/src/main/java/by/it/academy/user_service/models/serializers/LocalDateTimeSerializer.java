package by.it.academy.user_service.models.serializers;

import by.it.academy.user_service.utils.DateTimeUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    protected LocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    LocalDateTimeSerializer() {
        this(null);
    }

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        long dt = DateTimeUtil.convertLocalDateTimeToLong(localDateTime);
        jsonGenerator.writeNumber(dt);
    }
}
