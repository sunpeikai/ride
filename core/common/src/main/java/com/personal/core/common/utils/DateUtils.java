package com.personal.core.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * jdk8 新时间API工具类
 * @author zhangyk
 * @date 2019/9/24 14:26
 */
@Slf4j
public class DateUtils {


    private static final String ALL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    private static final String ALL_DATE = "yyyy-MM-dd";

    private static final String ALL_BANK_DATE_TIME = "yyyyMMddHHmmss";

    private static Pattern patternTime = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");

    private static Pattern patternDate = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(ALL_DATE_TIME);

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(ALL_DATE);

    public static final DateTimeFormatter  BANK_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(ALL_BANK_DATE_TIME);

    /**
     * localDateTime转字符串
     * @author zhangyk
     * @date 2019/9/23 16:59
     */
    public static String localDateTime2String(LocalDateTime localDateTime, DateTimeFormatter  format) {
        return localDateTime.format(format);
    }

    /**
     * string转localDateTime
     * @author zhangyk
     * @date 2019/9/23 17:01
     */
    public static LocalDateTime string2LocalDateTime(String time, DateTimeFormatter  format) {
        return LocalDateTime.parse(time, format);
    }

    /**
     * localDateTime转date
     * @author zhangyk
     * @date 2019/9/23 16:52
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * date转localDateTime
     * @author zhangyk
     * @date 2019/9/23 17:28
     */
    public static LocalDateTime date2LocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * localDateTime 转 localDate
     * @author zhangyk
     * @date 2019/9/24 14:49
     */
    public static LocalDate localDateTime2LocalDate(LocalDateTime localDateTime){
        return  localDateTime.toLocalDate();
    }

    /**
     * localDateTime转时间戳
     * @author zhangyk
     * @date 2019/9/23 17:08
     */
    public static Long localDateTime2Timestamp(LocalDateTime localDateTime){
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();

    }

    /**
     * timestamp 转localDateTime
     * @author zhangyk
     * @date 2019/9/23 17:11
     */
    public static LocalDateTime timestamp2LocalDateTime(Long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 日期增加days天
     * @author zhangyk
     * @date 2019/9/23 18:36
     */
    public static LocalDate plusDays(LocalDate localDate, int days){
        return localDate.plusDays(days);
    }

    /**
     * 日期减少days天
     * @author zhangyk
     * @date 2019/9/23 18:36
     */
    public static LocalDate minusDays(LocalDate localDate, int days){
        return localDate.minusDays(days);
    }


    /**
     * 两个日期时间相差数
     * @author zhangyk
     * @date 2019/9/23 18:42
     */
    public static Long dayDuration(LocalDateTime begin, LocalDateTime end){
        return Duration.between(begin,end).toDays();
    }

    /**
     * 车位回购剩余天数(根据购买订单时记录的天数计算)
     * @author wangjun
     * @date 2020/04/02 18:42
     */
    public static int remainingDays(LocalDateTime firstTime, int days){
        int remaining = dayDuration(LocalDate.now().atStartOfDay(),firstTime.plusDays(days).toLocalDate().atStartOfDay()).intValue();
        return remaining<0?0:remaining;
    }

    /**
     * 过去的某一时间点与现在相差的天数
     * @author sunpeikai
     * @date 2020/04/09 17:19
     */
    public static int pastBetweenNow(LocalDateTime pastDateTime){
        int result = 0;
        if(pastDateTime.isBefore(LocalDateTime.now())){
            result = dayDuration(pastDateTime.toLocalDate().atStartOfDay(),LocalDateTime.now()).intValue();
        }
        return result;
    }

    /**
     * 两个日期时间相差小时
     * @author zhangyk
     * @date 2019/9/25 11:36
     */
    public static Long hourDuration(LocalDateTime begin, LocalDateTime end){
        return Duration.between(begin,end).toHours();
    }

    /**
     * 两个日期相差年数(计算年龄)
     * @author zhangyk
     * @date 2019/9/23 18:40
     */
    public static int yearDuration(LocalDate begin, LocalDate end){
        return begin.until(end).getYears();
    }

    /**
     * 某一天的0时0分0秒
     * @author zhangyk
     * @date 2019/9/23 19:02
     */
    public static LocalDateTime startOfDay(LocalDate localDate){
        return localDate.atStartOfDay();
    }

    // 比较日期时间早于和晚于 有 isAfter() 和 isBefore() 可以直接使用 不在封装

    /**
     * 适当校验日期格式(无法排除 0000-00-00)
     * @author zhangyk
     * @date 2019/9/27 11:39
     */
    public static Boolean isDate(String dateStr){
       return patternDate.matcher(dateStr).matches();
    }

    /**
     * 适当校验time格式(无法排除 00:00:00)
     * @author zhangyk
     * @date 2019/9/27 11:40
     */
    public static boolean isTime(String timeStr){
       return patternTime.matcher(timeStr).matches();
    }

    /**
     * LocalDateTime → yyyy-MM-dd HH:mm:ss
     * @param dateTime
     * @return String yyyy-MM-dd HH:mm:ss
     */
    public static String format(LocalDateTime dateTime){
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * LocalDateTime → yyyy-MM-dd
     * @param date
     * @return String yyyy-MM-dd
     */
    public static String format(LocalDate date){
        return date.format(DATE_FORMATTER);
    }
}
