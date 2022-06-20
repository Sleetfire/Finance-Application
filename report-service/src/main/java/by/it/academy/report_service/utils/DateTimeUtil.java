package by.it.academy.report_service.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

    public static String setLocalDateFormat(LocalDate date, FormatStyle formatStyle) {
        return date.format(DateTimeFormatter.ofLocalizedDate(formatStyle));
    }

    public static String setLocalTimeFormat(LocalTime time, FormatStyle formatStyle) {
        return time.format(DateTimeFormatter.ofLocalizedTime(formatStyle));
    }

    public static String setLocalDateTimeFormat(LocalDateTime dateTime, FormatStyle dateFormatStyle, FormatStyle timeFormatStyle) {
        return dateTime.format(DateTimeFormatter.ofLocalizedDateTime(dateFormatStyle, timeFormatStyle));
    }


}
