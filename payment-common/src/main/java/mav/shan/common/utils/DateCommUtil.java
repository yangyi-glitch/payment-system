package mav.shan.common.utils;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 类DateUtil.java的实现描述：日期工具类
 */
@Slf4j
public class DateCommUtil {

    public final static String YYYY_MM_DD_HH_MM_DIY = "yyyy年MM月dd日 HH:mm";
    public final static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public final static String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String YYYY_MM = "yyyy-MM";
    public final static String YYYY = "yyyy";
    public final static String YYYYMMDD = "yyyyMMdd";
    public final static String YYYYMMDDHH = "yyyyMMddHH";
    public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public final static String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public final static String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public final static String MM_DD_HH_MM = "MM-dd HH:mm";
    public final static String MM_DD = "MM-dd";
    public final static String HH_MM = "HH:mm";
    public final static String YYYY_MM_DD_T_HH_mm_ss = "yyyy-MM-dd'T'HH:mm:ss";
    /**
     * 系统启动时间[时间戳]
     **/
    private final static long APP_BOOT_TIMESTAMP = getCurrDate().getTime();


    /**
     * 通过制定的格式，将日期字符串解析为java.util.Date对象
     *
     * @param dateStr   日期字符串
     * @param formatStr 解析的格式
     * @return 转换后的结果：Date对象
     * @throws ParseException
     */
    public static Date strToDate(String dateStr, String formatStr) {
        Date date = null;
        if (dateStr != null && !"".equals(dateStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
        return date;
    }

    /**
     * 转换字符串(long型格式) 为java.util.Date对象
     *
     * @param longStr
     * @return
     */
    public static Date longStrToDate(String longStr) {
        Date date = null;
        if (ObjectUtil.isNotNull(longStr) && ObjectUtil.isNotNull(longStr)) {
            try {
                date = new Date(Long.parseLong(longStr));
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }
        }
        return date;
    }

    /**
     * 根据一个完整的日期，格式化成一个只到分钟的时间.yyyy-MM-dd HH:mm
     *
     * @param date
     * @param formatFormat
     * @return
     */
    public static Date getMinuteDate(String date, String formatFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(date, formatFormat));
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 根据一个完整的日期，格式化成一个只到天的时间.yyyy-MM-dd
     *
     * @param date
     * @param formatFormat
     * @return
     */
    public static Date getDayDate(String date, String formatFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(date, formatFormat));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 通过制定的格式，将Date对象格式化为字符串
     *
     * @param date      需要转换的Date对象
     * @param formatStr 转换的格式
     * @return 转换之后的字符串
     */
    public static String dateToStr(Date date, String formatStr) {
        String result = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            result = sdf.format(date);
        }
        return result;
    }

    /**
     * 将字符串,格式化.
     *
     * @param date
     * @param format
     * @return
     */
    public static String parseDateStr(String date, String format) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(format)) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat(format);

        return f.format(strToDate(date, format));
    }

    /**
     * 将一个字符串日期转换成另一个字符串日期.
     *
     * @param date       日期对象
     * @param fromFormat 原来的日期格式
     * @param toFormat   转换后的日期格式
     * @return
     */
    public static String parseDateStr(String date, String fromFormat, String toFormat) {
        return dateToStr(strToDate(date, fromFormat), toFormat);
    }

    //

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static Date getCurrDate() {
        return new Date();
    }

    /**
     * 获取当前指定格式 的系统时间，可以改变分钟的数值 ，加或者减，例如：-1，10.
     *
     * @param formatStr    日期的格式化.
     * @param changeMinute 正数或者负数。
     * @return
     */
    public static String getCurrCustomMinuteDate(String formatStr, int changeMinute) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getCurrDate());
        cal.add(Calendar.MINUTE, changeMinute);
        return dateToStr(cal.getTime(), formatStr);
    }

    /**
     * 得到当前日期的 指定时间
     *
     * @param hour
     * @param min
     * @param sec
     * @return
     */
    public static Date getCurrDateFixTime(int hour, int min, int sec) {
        Calendar cal = new GregorianCalendar();

        cal.setTime(getCurrDate());
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);

        return cal.getTime();
    }

    /**
     * 获取当前指定格式的系统时间
     *
     * @param formatStr
     * @return
     */
    public static String getCurrDate(String formatStr) {
        return dateToStr(getCurrDate(), formatStr);
    }

    /**
     * 获取指定日期的小时 如果date为nul则返回-1.
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取指定日期的天,如果date为null则返回-1;
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当月最大的天数.
     *
     * @param date
     * @return
     */
    public static int getMonthMaxDay(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取当前日期对应的上一个月的日期.
     *
     * @return
     */
    public static Date getCurrUpMonthDate() {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.MONTH, -1);
        return c.getTime();
    }

    /**
     * 获取当前日期对应的前n年的日期.
     *
     * @param n
     * @return
     */
    public static Date getCurrUpYearDate(int n) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.YEAR, -n);
        return c.getTime();
    }

    /**
     * 获取当前日期对应的后n年的日期.
     *
     * @param n
     * @return
     */
    public static Date getCurrNextYearDate(int n) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.YEAR, n);
        return c.getTime();
    }

    /**
     * 获取当前日期对应的前n个月的日期.
     *
     * @param n
     * @return
     */
    public static Date getCurrUpMonthDate(int n) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.MONTH, -n);
        return c.getTime();
    }

    /**
     * 获取当前日期对应的后n个月的日期.
     *
     * @param n
     * @return
     */
    public static Date getCurrNextMonthDate(int n) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.MONTH, n);
        return c.getTime();
    }

    /**
     * 获取指定日期的上一个月的日期
     *
     * @return
     */
    public static Date getUpMonthDate(String date, String dateFormat) {
        Calendar c = new GregorianCalendar();
        c.setTime(strToDate(date, dateFormat));
        c.add(Calendar.MONTH, -1);
        return c.getTime();
    }

    /**
     * 获取当前日期对应的上一天的日期.
     *
     * @return
     */
    public static Date getCurrUpDayDate() {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    /**
     * 获取当前日期对应的下一天的日期.
     *
     * @return
     */
    public static Date getCurrNextDayDate() {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    /**
     * 获取当前日期对应的前n天的日期.
     *
     * @param n
     * @return
     */
    public static Date getCurrUpDayDate(int n) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, -n);
        return c.getTime();
    }

    /**
     * 获取当前日期对应的后n天的日期.
     *
     * @param n
     * @return
     */
    public static Date getCurrNextDayDate(int n) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, n);
        return c.getTime();
    }

    /**
     * 获取指定日期对应的前n分钟的日期.
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getCurrUpMinuteDate(Date date, int n) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.MINUTE, -n);
        return c.getTime();
    }

    /**
     * 获取指定日期对应的后n分钟的日期.
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getCurrNextMinuteDate(Date date, int n) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.MINUTE, n);
        return c.getTime();
    }

    /**
     * 获取指定日期对应的前n天的日期.
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getUpDayDate(Date date, int n) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DATE, -n);
        return c.getTime();
    }

    /**
     * 获取指定日期对应的后n天的日期.
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getNextDayDate(Date date, int n) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DATE, n);
        return c.getTime();
    }

    /**
     * 获取指定日期对应的前n个月的日期.
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getUpMonthDate(Date date, int n) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.MONTH, -n);
        return c.getTime();
    }

    /**
     * 获取指定日期对应的后n个月的日期.
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getNextMonthDate(Date date, int n) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.MONTH, n);
        return c.getTime();
    }

    /**
     * 获取指定日期对应的后n个月的日期.
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getNextMonthDate(String date, int n, String formatStr) {
        Calendar c = new GregorianCalendar();
        c.setTime(strToDate(date, formatStr));
        c.add(Calendar.MONTH, n);
        return c.getTime();
    }

    /**
     * 获取指定日期对应的前n年的日期.
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getUpYearDate(Date date, int n) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.YEAR, -n);
        return c.getTime();
    }

    /**
     * 获取指定日期对应的后n年的日期.
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getNextYearDate(Date date, int n) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.YEAR, n);
        return c.getTime();
    }

    /**
     * 获取当前日期对应的前n分钟的日期.
     *
     * @param n
     * @return
     */
    public static Date getCurrUpMinuteDate(int n) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.MINUTE, -n);
        return c.getTime();
    }

    /**
     * 获取当前日期对应的后n分钟的日期.
     *
     * @param n
     * @return
     */
    public static Date getCurrNextMinuteDate(int n) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.MINUTE, n);
        return c.getTime();
    }

    /**
     * 获取指定日期的上一天的日期。
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static Date getUpDayDate(String date, String dateFormat) {
        Calendar c = new GregorianCalendar();
        c.setTime(strToDate(date, dateFormat));
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    /**
     * 获取指定日期的上一天的日期。
     *
     * @param date
     * @return
     */
    public static Date getUpDayDate(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    /**
     * 获取指定日期的下一天的日期。
     *
     * @param date
     * @return
     */
    public static Date getNextDayDate(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 获取指定日期的上一天的日期。
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static Date getUpDayDate(Date date, String dateFormat) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    /**
     * 获取指定日期的下一天的日期。
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static Date getNextDayDate(Date date, String dateFormat) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 获取指定日期的上n周的日期。
     *
     * @param n
     * @return
     */
    public static Date getCurrUpWeekDate(int n) {
        Calendar c = new GregorianCalendar();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, -7 * n);
        return c.getTime();
    }

    /**
     * 获取指定日期的上一周的日期。
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static Date getUpWeekDate(String date, String dateFormat) {
        Calendar c = new GregorianCalendar();
        c.setTime(strToDate(date, dateFormat));
        c.add(Calendar.DAY_OF_MONTH, -7);
        return c.getTime();
    }

    /**
     * 计算指定日期加上后面2个日期之间的差值的日期，精确到秒
     *
     * @param d1 指定日期
     * @param d2 计算差值日期1
     * @param d3 计算差值日期2
     * @return
     */
    public static Date addDateInterval(Date d1, Date d2, Date d3) {
        long d1LongValue = d1.getTime();
        long d2LongValue = d2.getTime();
        long d3LongValue = d3.getTime();
        long lastDateValue = d1LongValue + Math.abs(d3LongValue - d2LongValue);
        Date newDate = new Date(lastDateValue);
        return newDate;
    }

    /**
     * 计算指定日期加上增量时间后的日期，精确到秒
     *
     * @param date     待处理的日期
     * @param addition 增加的毫秒数
     * @return Date
     */
    public static Date addDate(Date date, long addition) {
        long dateLongValue = date.getTime();
        long lastDateValue = dateLongValue + addition;
        Date newDate = new Date(lastDateValue);
        return newDate;
    }

    /**
     * 检查传入日期是否在当前日期之前
     *
     * @return boolean
     */
    public static boolean isLate(Date date) {
        return getCurrDate().after(date);
    }

    /**
     * 获取两个日期的时间差(单位：毫秒)
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    public static long getIntervalMilSeconds(String startDateStr, String endDateStr,
                                             String dateFormat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date startDate = format.parse(startDateStr);
        Date endDate = format.parse(endDateStr);
        return endDate.getTime() - startDate.getTime();
    }

    /**
     * java.sql.Timestamp转换为java.util.Date
     *
     * @param time
     * @return
     */
    public static Date timestampToDate(Timestamp time) {
        return new Date(time.getTime());
    }

    /**
     * 获得当天是周几 1-7
     *
     * @return
     */
    public static int getCurrWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return dayOfWeek == 0 ? 7 : dayOfWeek;
    }

    /**
     * 获取指定日期的分钟 如果date为nul则返回-1.
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    /**
     * 获取相隔多少天的时间
     *
     * @param date
     * @return
     */
    public static Date getMonthDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 当天的开始时间
     *
     * @return
     */
    public static Date startOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 当天的结束时间
     *
     * @return
     */
    public static Date endOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获得某一时间段内的所有日期
     */
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        lDate.remove(lDate.size() - 1);
        return lDate;
    }

    /**
     * 计算两个日期间的天数
     */
    public static int getDaysBetween(Date startDate, Date endDate) {
        long to = endDate.getTime();
        long from = startDate.getTime();
        return (int) ((to - from) / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取系统启动时间[时间戳]
     **/
    public static long getAppBootTimestamp() {
        return APP_BOOT_TIMESTAMP;
    }

    /**
     * 日期格式转换yyyy-MM-dd‘T‘HH:mm:ss.SSSXXX TO yyyy-MM-dd HH:mm:ss
     *
     * @throws ParseException
     */
    public static String formatTDateFormat(String dateStr) {
        try {
            DateFormat df = new SimpleDateFormat(YYYY_MM_DD_T_HH_mm_ss);
            Date date = df.parse(dateStr);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date1 = df1.parse(date.toString());
            DateFormat df2 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            return df2.format(date1);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 两个日期的相差的月份数
     *
     * @param startTime 开始日期
     * @param endTime   结束日期
     * @return int
     */
    public static int differMonth(String startTime, String endTime) {
        try {
            DateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
            Calendar bef = Calendar.getInstance();
            Calendar aft = Calendar.getInstance();
            bef.setTime(sdf.parse(startTime));
            aft.setTime(sdf.parse(endTime));
            int surplus = aft.get(Calendar.DATE) - bef.get(Calendar.DATE);
            int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
            int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
            System.out.println(surplus);
            surplus = surplus <= 0 ? 1 : 0;
            System.out.println(surplus);
            System.out.println("相差月份：" + (Math.abs(month + result) + surplus));
            return (Math.abs(month + result) + surplus);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    public static int differMonth(Date startTime, Date endTime) {
        try {
            Calendar bef = Calendar.getInstance();
            Calendar aft = Calendar.getInstance();
            bef.setTime(startTime);
            aft.setTime(endTime);
            int surplus = aft.get(Calendar.DATE) - bef.get(Calendar.DATE);
            int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
            int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
            System.out.println(surplus);
            surplus = surplus <= 0 ? 1 : 0;
            System.out.println(surplus);
            System.out.println("相差月份：" + (Math.abs(month + result) + surplus));
            return (Math.abs(month + result) + surplus);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }


    /**
     * 获取某月的开始日期
     *
     * @return
     */
    public static String getStartDayOfMonth(LocalDate date) {
        LocalDate with = date.with(TemporalAdjusters.firstDayOfMonth());
        return LocalDateTime.of(with, LocalTime.MIN).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取某月的结束日期
     *
     * @return
     */
    public static String getEndDayOfMonth(LocalDate date) {
        LocalDate with = date.with(TemporalAdjusters.lastDayOfMonth());
        return LocalDateTime.of(with, LocalTime.MAX).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static void main(String[] args) {
        System.out.println(getStartDayOfMonth(LocalDate.now().plusMonths(-1)));
        System.out.println(getEndDayOfMonth(LocalDate.now().plusMonths(-1)));
    }

    /**
     * 获取某月的最后一天 如201712结果为2017-12-30
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastday(String year, String month) {
        LocalDate firstDayOfCurrentDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
        LocalDate lastDayOfCurrentDate = firstDayOfCurrentDate.with(TemporalAdjusters.lastDayOfMonth());
        return lastDayOfCurrentDate.toString();
    }

    /**
     * 将 Date 转为 LocalDate
     *
     * @param date
     * @return java.time.LocalDate;
     */
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * @description: gtDesignateTime
     * @author: wey
     * @date: 2023/3/1 10:39
     * @param: [designateTime] HH:mm:ss
     * @return: boolean
     **/
    public static boolean gtDesignateTime(String designateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = LocalDate.now() + designateTime;
        LocalDateTime localTime = LocalDateTime.parse(time, dtf);
        System.out.println(localTime);
        return LocalDateTime.now().isAfter(localTime);
    }


}
