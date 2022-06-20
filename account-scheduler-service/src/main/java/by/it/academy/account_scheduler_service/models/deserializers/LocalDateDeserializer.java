package by.it.academy.account_scheduler_service.models.deserializers;

import by.it.academy.account_scheduler_service.utils.DateTimeUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    protected LocalDateDeserializer(Class<?> vc) {
        super(vc);
    }

    public LocalDateDeserializer() {
        this(null);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return DateTimeUtil.convertLongToLocalDate(jsonParser.getValueAsLong());
    }
}
