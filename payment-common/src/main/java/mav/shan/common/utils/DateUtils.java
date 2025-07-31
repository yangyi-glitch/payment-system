package mav.shan.common.utils;

import java.time.*;

public class DateUtils {
    public static Long dateTimeToTimestamp(LocalDateTime localDateTime) {
        long epochMilli = localDateTime.atZone(ZoneId.of("Asia/Shanghai"))
                .toInstant()
                .toEpochMilli();
        return epochMilli;
    }

    public static Long dateToTimestamp(LocalDate localDate) {
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.MIN);
        long epochMilli = localDateTime.atZone(ZoneId.of("Asia/Shanghai"))
                .toInstant()
                .toEpochMilli();
        return epochMilli;
    }

    public static Long timeToTimestamp(LocalTime localTime) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime);
        long epochMilli = localDateTime.atZone(ZoneId.of("Asia/Shanghai"))
                .toInstant()
                .toEpochMilli();
        return epochMilli;
    }

    public static LocalDateTime timestampToDateTime(Long timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("Asia/Shanghai"));
        return localDateTime;
    }
}
