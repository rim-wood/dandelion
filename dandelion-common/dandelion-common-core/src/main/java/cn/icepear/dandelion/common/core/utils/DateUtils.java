package cn.icepear.dandelion.common.core.utils;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期处理
 *
 * @author rimwood
 * @email wuliming195@gmail.com
 * @date 2018年2月21日 下午12:53:33
 */
public final class DateUtils {
    private DateUtils() {
    }
    /** 日期格式 **/
    public interface DATE_PATTERN {
        String HHMMSS = "HHmmss";
        String HH_MM_SS = "HH:mm:ss";
        String YYYYMMDD = "yyyyMMdd";
        String YYYY_MM_DD = "yyyy-MM-dd";
        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
        String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    }

    public interface TIMES {
        /**
         * 1秒 java已毫秒为单位
         */
        long SECOND = 1000;
        /**
         * 一分钟
         */
        long MINUTE = SECOND * 60;
        /**
         * 一小时
         */
        long HOUR = MINUTE * 60;
        /**
         * 一天
         */
        long DAY = HOUR * 24;
        /**
         * 一周
         */
        long WEEK = DAY * 7;
        /**
         * 一年
         */
        long YEAR = DAY * 365;
    }

    /********************format 格式化 start*********************/
    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  yyyy-MM-dd格式字符串
     */
    public static String format(Object date){
        return format(date, DATE_PATTERN.YYYY_MM_DD);
    }

    /**
     * 日期格式化
     * @param date 日期字符串
     * @param pattern 日期的格式，如：DATE_PATTERN.YYYY_MM_DD
     * @return pattern格式字符串,pattern为空默认返回yyyy-MM-dd格式字符串
     */
    public static String format(Object date, String pattern){
        if (date == null) {
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            return new SimpleDateFormat(DATE_PATTERN.YYYY_MM_DD).format(date);
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * @implNote 将localdate转换为yyyy-MM-dd格式字符串
     * @param date
     * @return yyyy-MM-dd格式字符串
     */
    public static String format(LocalDate date){
        if(date != null) {
            return format(date, DATE_PATTERN.YYYY_MM_DD);
        }
        return null;
    }

    /**
     * @implNote 将localdate转换为yyyy-MM-dd格式字符串
     * @param date
     * @param pattern 日期的格式，如：DATE_PATTERN.YYYY_MM_DD
     * @return pattern格式字符串,pattern为空默认返回yyyy-MM-dd格式字符串
     */
    public static String format(LocalDate date, String pattern) {
        if(date == null) {
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN.YYYY_MM_DD);
            return date.format(dtf);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return dtf.format(date);
    }

    /**
     * @implNote 将localdatetime转换为yyyy-MM-dd HH:mm:ss格式字符串
     * @param date
     * @return yyyy-MM-dd HH:mm:ss格式字符串
     */
    public static String format(LocalDateTime date){
        if(date != null) {
            return format(date, DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
        }
        return null;
    }

    /**
     * @implNote 将localdatetime转换为yyyy-MM-dd格式字符串
     * @param date
     * @param pattern 日期的格式，如：DATE_PATTERN.YYYY_MM_DD
     * @return yyyy-MM-dd格式字符串
     */
    public static String format(LocalDateTime date, String pattern) {
        if(date == null) {
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN.YYYY_MM_DD);
            return date.format(dtf);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return dtf.format(date);
    }
    /********************format 格式化 end*********************/



    /********************string、Date 转化 Date、LocalDate、LocalDateTime start*********************/
    /**
     * 字符串转换成Date
     * @param date 日期字符串
     * @return yyyy-MM-dd格式Date
     */
    public static Date transDate(String date) throws ParseException {
        if(StringUtils.isBlank(date)){
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN.YYYY_MM_DD);
        return df.parse(date);
    }

    /**
     * 字符串转换成LocalDate
     * @param date 日期字符串
     * @return yyyy-MM-dd格式LocalDate
     */
    public static LocalDate transLocalDate(String date) throws ParseException{
        if(StringUtils.isBlank(date)){
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN.YYYY_MM_DD);
        Instant instant = df.parse(date).toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return  localDateTime.toLocalDate();

    }

    /**
     * Date转换成LocalDate
     * @param date 日期
     * @return LocalDate
     */
    public static LocalDate transLocalDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return  localDateTime.toLocalDate();
    }

    /**
     * 字符串转换成LocalDateTime
     * @param date 日期字符串
     * @return yyyy-MM-dd HH:mm:ss格式LocalDateTime
     */
    public static LocalDateTime transLocalDateTime(String date){
        if(StringUtils.isBlank(date)){
            return null;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
        return LocalDateTime.parse(date,dtf);
    }

    /**
     * Date转换成LocalDateTime
     * @param date 日期
     * @return LocalDateTime
     */
    public static LocalDateTime transLocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * @implNote LocalDateTime转换为Date
     * @param localdatetime
     * @return Date
     */
    public static Date LocalDateTimetoDate(LocalDateTime localdatetime){
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localdatetime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 通用字符串转换为日期，根据字符串长度匹配格式；暂不支持yyM[M]d[d]格式
     * @param date
     * @return
     */
    public static Date stringToDate(String date) {
        if (date == null) {
            return null;
        }
        String separator = String.valueOf(date.charAt(4));
        String pattern = "yyyyMMdd";
        if (!separator.matches("\\d*")) {
            pattern = "yyyy" + separator + "MM" + separator + "dd";
            if (date.length() < 10) {
                pattern = "yyyy" + separator + "M" + separator + "d";
            }
            pattern += " HH:mm:ss.SSS";
        } else if (date.length() < 8) {
            pattern = "yyyyMd";
        } else {
            pattern += "HHmmss.SSS";
        }
        pattern = pattern.substring(0, Math.min(pattern.length(), date.length()));
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    /********************string、Date 转化 Date、LocalDate、LocalDateTime end*********************/


    /********************一些日期的计算方法start*********************/
    /**
     * 获取当前时间yyyy-MM-dd格式日期字符串
     * @return yyyy-MM-dd格式字符串
     */
    public static String getDate() {
        return format(new Date());
    }

    /**
     * 获取当前时间yyyy-MM-dd HH:mm:ss日期格式字符串
     * @return yyyy-MM-dd格式字符串
     */
    public static String getDateTime() {
        return format(new Date(), DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前时间pattern格式日期字符串
     * @param pattern
     * @return pattern格式字符串
     */
    public static String getDateTime(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 获取该日期月份的天数
     * @param date
     * @return 该月天数
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date 日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date 日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date 日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date 日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date 日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date 日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }

    /**
     * 间隔秒
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getBetween(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        long n = end.getTimeInMillis() - start.getTimeInMillis();
        return (int)(n / 1000l);
    }

    /**
     * 间隔秒
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return Duration.between(startDate,endDate).getSeconds();
    }

    /**
     * 间隔天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getDayBetween(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        long n = end.getTimeInMillis() - start.getTimeInMillis();
        return (int)(n / (60 * 60 * 24 * 1000l));
    }

    /**
     * 间隔月
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getMonthBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null || !startDate.before(endDate)) {
            return null;
        }
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        int year1 = start.get(Calendar.YEAR);
        int year2 = end.get(Calendar.YEAR);
        int month1 = start.get(Calendar.MONTH);
        int month2 = end.get(Calendar.MONTH);
        int n = (year2 - year1) * 12;
        n = n + month2 - month1;
        return n;
    }

    /**
     * 间隔月，多一天就多算一个月
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getMonthBetweenWithDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null || !startDate.before(endDate)) {
            return null;
        }
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        int year1 = start.get(Calendar.YEAR);
        int year2 = end.get(Calendar.YEAR);
        int month1 = start.get(Calendar.MONTH);
        int month2 = end.get(Calendar.MONTH);
        int n = (year2 - year1) * 12;
        n = n + month2 - month1;
        int day1 = start.get(Calendar.DAY_OF_MONTH);
        int day2 = end.get(Calendar.DAY_OF_MONTH);
        if (day1 <= day2) {
            n++;
        }
        return n;
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param millis 毫秒时间戳
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果小于1秒钟内，显示刚刚</li>
     * <li>如果在1分钟内，显示XXX秒前</li>
     * <li>如果在1小时内，显示XXX分钟前</li>
     * <li>如果在1小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>如果是当年的，显示10-15</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如2018-05-13 14:21:20</li>
     * </ul>
     */
    public static String getFriendly(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0) {
            return String.format("%tF %tT", millis, millis);
        }
        if (span < 1000) {
            return "刚刚";
        } else if (span < TIMES.MINUTE) {
            return String.format("%d秒前", span / TIMES.SECOND);
        } else if (span < TIMES.HOUR) {
            return String.format("%d分钟前", span / TIMES.MINUTE);
        }
        // 获取当天00:00
        long wee = now / TIMES.DAY * TIMES.DAY;
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - TIMES.DAY) {
            return String.format("昨天%tR", millis);
        } else {
            wee = now / TIMES.YEAR * TIMES.YEAR;
            if (millis >= wee) {
                return String.format("%tm-%te", millis, millis);
            }
            return String.format("%tF", millis);
        }
    }

    /**
     * 获取现在到凌晨还剩多少秒
     * @return
     */
    public static long getSecondsUntilZero(){
        String endTime = DateUtils.format(LocalDate.now())+" 23:59:59";
        LocalDateTime startTime = LocalDateTime.now();
        return getBetween(startTime,DateUtils.transLocalDateTime(endTime));
    }

    /**
     * 获取当前GMT时间
     * @return
     */
    public static String getGMTDate(){
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        return sdf.format(cd.getTime());
    }
    /**
     * 将字符串转化为秒为单位的时间戳
     * @return
     */
    public static Long dateTimeStrToLong(String dateTime) throws ParseException {
        LocalDateTime time = transLocalDateTime(dateTime);
        return time.toEpochSecond(ZoneOffset.of("+8"));
    }
    /********************一些日期的计算方法end*********************/
}

