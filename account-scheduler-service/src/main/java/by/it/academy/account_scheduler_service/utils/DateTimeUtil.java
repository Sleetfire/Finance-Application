package by.it.academy.account_scheduler_service.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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

    public static long convertLocalDateToLong(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
    }

    public static LocalDate convertLongToLocalDate(long millis) {
        return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}