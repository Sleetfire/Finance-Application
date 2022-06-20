package by.it.academy.account_scheduler_service.models.serializers;

import by.it.academy.account_scheduler_service.utils.DateTimeUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateSerializer extends StdSerializer<LocalDate> {

    protected LocalDateSerializer(Class<LocalDate> t) {
        super(t);
    }

    public LocalDateSerializer() {
        this(null);
    }

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        long dt = DateTimeUtil.convertLocalDateToLong(localDate);
        jsonGenerator.writeNumber(dt);
    }
}
