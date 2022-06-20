package by.it.academy.account_service.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public class DateTimeUtil {

    private DateTimeUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static long convertLocalDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime convertLongToLocalDateTime(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), TimeZone.getDefault().toZoneId());
    }
}
