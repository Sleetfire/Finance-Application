package by.it.academy.account_service.models.serializers;

import by.it.academy.account_service.models.dto.Balance;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class BalanceSerializer extends StdSerializer<Balance> {

    protected BalanceSerializer(Class<Balance> t) {
        super(t);
    }

    public BalanceSerializer() {
        this(null);
    }

    @Override
    public void serialize(Balance balance, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(balance.getValue());
    }
}
