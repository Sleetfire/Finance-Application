package by.it.academy.classifier_service.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtil {

    public static long convertLocalDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}