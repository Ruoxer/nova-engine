package com.war3.nova.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午3:11:35
 * @version 1.0
 */
public class Dates {
    
    public static final String PATTERN_DEFAULT = "yyyy-MM-dd";
    
    public static final String PATTERN_DAYPATH = "yyyy\\MM\\dd\\";
    
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    
    public static final String PATTERN_TIME = "HH:mm:ss";
    
    public static final String PATTERN_TIME_COMPACT = "HHmmss";
    
    public static final String PATTERN_DATETIME_COMPACT = "yyyyMMddHHmmss";
    
    public static final String PATTERN_DATETIME_COMPACT_SSS = "yyyyMMddHHmmssSSS";
    
    public static final String PATTERN_DATE_COMPACT = "yyyyMMdd";
    
    public static final String PATTERN_DATESHORT = "yyMMdd";
    
    public static final String PATTERN_YEARMONTH = "yyyyMM";

    public static final String MONTHS_STRING[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    
    private Dates() {}
    
    /**
     * 获得当前日期的年份
     * @return
     */
    public final static int getCurrYear() {
        return getYear(new Date());
    }
    
    /**
     * 获得传入日期的年份
     * @param date
     * @return
     */
    public final static int getYear(Date date) {
        return get(date, Calendar.YEAR);
    }
    
    /**
     * 获得当前日期的月份
     * @return
     */
    public final static int getCurrMonth() {
        return getMonth(new Date());
    }
    
    /**
     * 获得传入日期的月份
     * @param date
     * @return
     */
    public final static int getMonth(Date date) {
        return get(date, Calendar.MONTH) + 1;
    }
    
    /**
     * 获得当前日期的在月份中的天数
     * @return
     */
    public final static int getCurrMonthDay() {
        return getMonthDay(new Date());
    }
    
    /**
     * 获得当前日期的在月份中的天数
     * @param date
     * @return
     */
    public final static int getMonthDay(Date date) {
        return get(date, Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获得当前日期的在年中的天数
     * @return
     */
    public final static int getCurrYearDay() {
        return getYearDay(new Date());
    }
    
    /**
     * 获得当前日期的在年中的天数
     * @param date
     * @return
     */
    public final static int getYearDay(Date date) {
        return get(date, Calendar.DAY_OF_YEAR);
    }
    
    /**
     * 获得传入日期的在一天中的小时数
     * @param date
     * @return
     */
    public final static int getDayHour(Date date) {
        return get(date, Calendar.HOUR_OF_DAY);
    }
    
    /**
     * 获得传入日期的在一天中的小时数
     * @return
     */
    public final static int getCurrDayHour() {
        return getDayHour(new Date());
    }
    
    /**
     * 获得传入日期的在一天中的分钟数
     * @return
     */
    public final static int getCurrMinute() {
        return getMinute(new Date());
    }
    
    /**
     * 获得传入日期的在一天中的分钟数
     * @param date
     * @return
     */
    public final static int getMinute(Date date) {
        return get(date, Calendar.MINUTE);
    }
    
    /**
     * 获得传入日期的在一天中的秒钟数
     * @return
     */
    public final static int getCurrSecond() {
        return getSecond(new Date());
    }
    
    /**
     * 获得传入日期的在一天中的秒钟数
     * @param date
     * @return
     */
    public final static int getSecond(Date date) {
        return get(date, Calendar.SECOND);
    }
    
    /**
     * 获得传入日期的域信息
     * @param date
     * @param field
     * @return
     */
    public final static int get(Date date, int field) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        return calendar.get(field);
    }
    
    /**
     * 当前日期加年份
     * @param amount
     * @return
     */
    public final static Date addYear(int amount) {
        return addYear(new Date(0), amount);
    }
    
    /**
     * 加年份
     * @param date
     * @param amount
     * @return
     */
    public final static Date addYear(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }
    
    /**
     * 加年份
     * @param date
     * @param pattern
     * @param amount
     * @return
     */
    public final static String addYear(String date, String pattern, int amount) {
        return formatDate(addYear(parseDate(date, pattern), amount), pattern);
    }
    
    /**
     * 加年份
     * @param date
     * @param pattern
     * @param amount
     * @return
     */
    public final static String addYear(Date date, String pattern, int amount) {
        return formatDate(addYear(date, amount), pattern);
    }
    
    /**
     * 当前日期加月份
     * @param amount
     * @return
     */
    public final static Date addMonth(int amount) {
        return addMonth(new Date(0), amount);
    }
    
    /**
     * 加月份
     * @param date
     * @param amount
     * @return
     */
    public final static Date addMonth(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }
    
    /**
     * 加月份
     * @param date
     * @param pattern
     * @param amount
     * @return
     */
    public final static String addMonth(String date, String pattern, int amount) {
        return formatDate(addMonth(parseDate(date, pattern), amount), pattern);
    }
    
    /**
     * 加月份
     * @param date
     * @param pattern
     * @param amount
     * @return
     */
    public final static String addMonth(Date date, String pattern, int amount) {
        return formatDate(addMonth(date, amount), pattern);
    }
    
    /**
     * 当前日期加天
     * @param amount
     * @return
     */
    public final static Date addDay(int amount) {
        return addDay(new Date(0), amount);
    }
    
    /**
     * 加天
     * @param date
     * @param amount
     * @return
     */
    public final static Date addDay(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }
    
    /**
     * 加天
     * @param date
     * @param pattern
     * @param amount
     * @return
     */
    public final static String addDay(String date, String pattern, int amount) {
        return formatDate(addDay(parseDate(date, pattern), amount), pattern);
    }
    
    /**
     * 加天
     * @param date
     * @param pattern
     * @param amount
     * @return
     */
    public final static String addDay(Date date, String pattern, int amount) {
        return formatDate(addDay(date, amount), pattern);
    }
    
    /**
     * 日期信息相加
     * @param date 日期
     * @param field 相加域
     * @param amount 相加数量
     * @return
     */
    public final static Date add(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }
    
    /**
     * 格式化当前日期
     * @param pattern
     * @return
     */
    public final static String formatDate(String pattern) {
        return formatDate(new Date(), pattern);
    }
    
    /**
     * 格式化日期
     * @param date
     * @param pattern
     * @return
     */
    public final static String formatDate(Date date, String pattern) {
        return Objects.nonNull(date) ? new SimpleDateFormat(pattern, Locale.CHINA).format(date) : null;
    }
    
    /**
     * 使用默认的格式格式化日期
     * @return
     */
    public final static String formatDateByDef() {
        return formatDate(PATTERN_DEFAULT);
    }
    
    /**
     * 使用默认的格式格式化日期
     * @return
     */
    public final static String formatDateByDef(Date date) {
        return formatDate(date, PATTERN_DEFAULT);
    }
    
    /**
     * 使用默认的格式格式化日期
     * @return
     */
    public final static String formatDateTimeByDef() {
        return formatDate(PATTERN_DATETIME);
    }
    
    /**
     * 使用默认的格式格式化日期
     * @return
     */
    public final static String formatDateTimeByDef(Date date) {
        return formatDate(date, PATTERN_DATETIME);
    }
    
    /**
     * 日期准换
     * @param date
     * @param pattern
     * @return
     */
    public final static Date parseDate(String date, String pattern) {
        try {
            return (Strings.isNotEmpty(date)) ? new SimpleDateFormat(pattern).parse(date) : null;
        } catch (Exception e) {
        } 
        return null;
    }
    
    /**
     * 日期转换
     * @param date
     * @return
     */
    public final static Date parseDateByDef(String date) {
        return parseDate(date, PATTERN_DEFAULT);
    }
    
    /**
     * 日期转换
     * @param date
     * @return
     */
    public final static Date parseDateTimeByDef(String date) {
        return parseDate(date, PATTERN_DATETIME);
    }
    
    /**
     * 获得两个时间之间相差的月份，如天数未对应，则不算满一个月
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public final static int getMonthsByTwoDates(Date startDate, Date endDate) {
        int month = 0;
        Calendar start = Calendar.getInstance(Locale.CHINA);
        Calendar end = Calendar.getInstance(Locale.CHINA);

        start.setTime(startDate);
        end.setTime(endDate);

        month = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12 + end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        start.add(Calendar.MONTH, month);
        return start.after(end) ? month - 1 : month;
    }
    
    /**
     * 获得两个时间之间相差的月份，如天数未对应，则不算满一个月
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param pattern
     * @return
     */
    public final static int getMonthsByTwoDates(String startDate, String endDate, String pattern) {
        return getMonthsByTwoDates(startDate, pattern, endDate, pattern);
    }
    
    /**
     * 获得两个时间之间相差的月份，如天数未对应，则不算满一个月
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param startPattern
     * @param endPattern
     * @return
     */
    public final static int getMonthsByTwoDates(String startDate, String startPattern, String endDate, String endPattern) {
        return getMonthsByTwoDates(parseDate(startDate, startPattern), parseDate(endDate, endPattern));
    }
    
    /**
     * 获得两个时间之间相差的月份，如天数未对应，则不算满一个月
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public final static int getMonthsByTwoDatesDef(String startDate, String endDate) {
        return getMonthsByTwoDates(startDate, endDate, PATTERN_DEFAULT);
    }

    /**
     * 比较两个日期的相隔天数<br>
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public final static int getDaysByTwoDates(Date startDate, Date endDate) {
        return (int) ((startDate.getTime() - endDate.getTime()) / 1000 / 60 / 60 / 24);
    }
    
    /**
     * 比较两个日期的相隔天数<br>
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param pattern
     * @return
     */
    public final static int getDaysByTwoDates(String startDate, String endDate, String pattern) {
        return getDaysByTwoDates(startDate, pattern, endDate, pattern);
    }
    
    /**
     * 比较两个日期的相隔天数<br>
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param startPattern
     * @param endPattern
     * @return
     */
    public final static int getDaysByTwoDates(String startDate, String startPattern, String endDate, String endPattern) {
        return getDaysByTwoDates(parseDate(startDate, startPattern), parseDate(endDate, endPattern));
    }
    
    /**
     * 比较两个日期的相隔天数<br>
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public final static int getDaysByTwoDatesDef(String startDate, String endDate) {
        return getDaysByTwoDates(startDate, endDate, PATTERN_DEFAULT);
    }
    
    /**
     * 判断是否闰年<br>
     * 闰年条件（满足一个即可）：1、能被4整除但不能被100整除；2、能被400整除
     * 
     * @param year
     * @return
     */
    public final static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }
    
    /**
     * 判断是否闰年<br>
     * 闰年条件（满足一个即可）：1、能被4整除但不能被100整除；2、能被400整除
     * 
     * @param year
     * @return
     */
    public final static boolean isLeapYear(String year) {
        return Strings.isNotEmpty(year) ? isLeapYear(Integer.valueOf(year)) : false;
    }
    
    /**
     * 获取昨日日期
     * 
     * @param curDate
     *            格式：yyyy-MM-dd
     * @return 格式：yyyy-MM-dd
     * @throws Exception
     */
    public final static String getYesterday(String date) throws Exception {
        return getYesterday(date, PATTERN_DEFAULT);
    }
    
    /**
     * 获取昨日日期
     * 
     * @param curDate
     * @param pattern
     * @throws Exception
     */
    public final static String getYesterday(Date date) throws Exception {
        return formatDate(getYesterday(date, PATTERN_DEFAULT), PATTERN_DEFAULT);
    }
    
    /**
     * 获取昨日日期
     * 
     * @param curDate
     * @param pattern
     * @throws Exception
     */
    public final static String getYesterday(String date, String pattern) throws Exception {
        return addDay(date, pattern, -1);
    }
    
    /**
     * 获取昨日日期
     * 
     * @param curDate
     * @param pattern
     * @throws Exception
     */
    public final static Date getYesterday(Date date, String pattern) throws Exception {
        return addDay(date, -1);
    }
    
    /**
     * 获取明日日期
     * 
     * @param curDate
     *            格式：yyyy-MM-dd
     * @return 格式：yyyy-MM-dd
     * @throws Exception
     */
    public final static String getTomorrow(String date) throws Exception {
        return getTomorrow(date, PATTERN_DEFAULT);
    }
    
    /**
     * 获取明日日期
     * 
     * @param curDate
     * @param pattern
     * @throws Exception
     */
    public final static String getTomorrow(Date date) throws Exception {
        return formatDate(getTomorrow(date, PATTERN_DEFAULT), PATTERN_DEFAULT);
    }
    
    /**
     * 获取明日日期
     * 
     * @param curDate
     * @param pattern
     * @throws Exception
     */
    public final static String getTomorrow(String date, String pattern) throws Exception {
        return addDay(date, pattern, 1);
    }
    
    /**
     * 获取明日日期
     * 
     * @param curDate
     * @param pattern
     * @throws Exception
     */
    public final static Date getTomorrow(Date date, String pattern) throws Exception {
        return addDay(date, 1);
    }
    
    /**
     * 获取当前月份所在的季末月份
     * 
     * @param month
     * @return
     */
    public final static int getEndMonthOfQuarter(int month) {
        return ((int) (month - 1) / 3 + 1) * 3; 
        
    }
    
    /**
     * 获取当前月份所在的季末月份
     * 
     * @param month
     * @return
     */
    public final static int getEndMonthOfQuarter(String month) {
        return getEndMonthOfQuarter(Integer.valueOf(month));
    }
    
    /**
     * 获取当前日期（机器时间）
     * @return 时间格式：yyyy-MM-dd
     */
    public final static String getCurrDate() {
        return formatDateByDef();
    }
    
    /**
     * 获取当前时间（机器时间）
     * @return 时间格式：yyyy-MM-dd HH:mm:ss
     */
    public final static String getCurrDateTime() {
        return formatDateTimeByDef();
    }
    
    /**
     * 获取当前时间（机器时间）
     * @return 时间格式：HH:mm:ss
     */
    public final static String getCurrTime() {
        return formatDate(PATTERN_TIME);
    }
    
    /**
     * 按传入格式获取时间戳
     * @param pattern
     * @return
     */
    public final static String getTimeStampByPattern(String pattern) {
        return formatDate(pattern);
    }
    
    /**
     * 获得当前月转换成字符串显示</p>
     * 如"Jan", "Feb", "Mar"
     * @return
     */
    public final static String getCurrStringMonth() {
        return getStringMonth(getCurrMonth() - 1);
    }
    
    /**
     * 获得当前月转换成字符串显示</p>
     * 如"Jan", "Feb", "Mar"
     * @return
     */
    public final static String getStringMonth(Date date) {
        return getStringMonth(getMonth(date) - 1);
    }
    
    /**
     * 获得当前月转换成字符串显示</p>
     * 如"Jan", "Feb", "Mar"
     * @return
     */
    public final static String getStringMonth(int month) {
        return MONTHS_STRING[month];
    }
    
    /**
     * 获得当前月转换成字符串显示</p>
     * 如"Jan", "Feb", "Mar"
     * @return
     */
    public final static String getStringMonth(String month) {
        return getStringMonth(Integer.valueOf(month));
    }
    
    /**
     * 返回两位字符串
     * @param m 月
     * @return
     * date：2014-11-20 下午05:31:17
     * author：Pansq
     * modify history
     */
    public final static String bZeroMonth(int month) {
        return (month < 10 ? ("0" + String.valueOf(month)) : String.valueOf(month));
    }
    
    /**
     * 返回月的天数
     * @param year 年
     * @param month 月
     * @return 天数
     */
    public static int getMonthTotalDays(int year, int month) {
        int days = 1;
        switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            days = 31;
            break;
        case 2:
            days = isLeapYear(year) ? 29 : 28;
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            days = 30;
            break;
        }
        return days;
    }
    
    /**
     * 将日期格式为YYYYMMDD改为YYYY-MM-DD
     * @param date
     * @return
     */
    public final static String formatDate8To10(String date) {
        return (Strings.isNotEmpty(date)) ? formatDateByDef(parseDate(date, PATTERN_DATE_COMPACT)) : null;
    }
    
    /**
     * 将日期格式为YYYY-MM-DD改为YYYYMMDD
     * @param date
     * @return
     */
    public final static String formatDate10To8(String date) {
        return Strings.isNotEmpty(date) ? formatDate(parseDateByDef(date), PATTERN_DATE_COMPACT) : null;
    }
    
    /**
     * 将日期格式为YYYYMMDDHHMMSS改为yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public final static String formatDateTime14To19(String date) {
        return Strings.isNotEmpty(date) ? formatDate(parseDate(date, PATTERN_DATETIME_COMPACT), PATTERN_DATETIME) : null;
    }
    
    /**
     * 将日期格式为yyyy-MM-dd HH:mm:ss改为YYYYMMDDHHMMSS
     * @param date
     * @return
     */
    public final static String formatDateTime19To14(String date) {
        return Strings.isNotEmpty(date) ? formatDate(parseDate(date, PATTERN_DATETIME), PATTERN_DATETIME_COMPACT) : null;
    }
    
    /**
     * 将时间格式为HHMMSS改为HH:mm:ss
     * @param date
     * @return
     */
    public final static String formatTime6To8(String date) {
        return Strings.isNotEmpty(date) ? formatDate(parseDate(date, PATTERN_TIME_COMPACT), PATTERN_TIME) : null;
    }
    
    /**
     * 将时间格式为HH:mm:ss改为HHMMSS
     * @param date
     * @return
     */
    public final static String formatTime8To6(String date) {
        return Strings.isNotEmpty(date) ? formatDate(parseDate(date, PATTERN_TIME), PATTERN_TIME_COMPACT) : null;
    }
    
    /**
     * 判断传递参数是否为日期类型 true是 false 不是
     * @param dateStr
     * @return 
     */
    public final static boolean checkDate(String dateStr) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher matcher = pattern.matcher(dateStr);
        return matcher.matches();
    }
    
    /**
     * 根据终止日和相隔月数计算起始日
     * @param endDate 结束日期
     * @param months 相隔月数
     * @return String
     */
    public static String getStartDateByMonths(Date endDate, int months) {
        return formatDateByDef(addMonth(endDate, 0 - months));
    }
    
    /**
     * 根据终止日和相隔月数计算起始日
     * @param endDate 结束日期
     * @param months 相隔月数
     * @return String
     */
    public static String getStartDateByMonths(String endDate, int months) {
        return getStartDateByMonths(parseDateByDef(endDate), months);
    }
    
    /**
     * 根据终止日和相隔月数计算起始日
     * @param endDate 结束日期
     * @param months 相隔月数
     * @return String
     */
    public static String getStartDateByMonths(String endDate, String pattern, int months) {
        return formatDate(addMonth(parseDate(endDate, pattern), 0 - months), pattern);
    }
    
    /**
     * 将对象转换为时间
     * @param obj
     * @param pattern
     * @return
     */
    public static Date object2Date(Object object, String pattern) {
        Date date = null;
        if (object instanceof Date) {
            date = (Date) object;
        } else if (object instanceof Calendar) {
            Calendar cal = (Calendar) object;
            date = cal.getTime();
        } else if (object instanceof String) {
            String str = String.valueOf(object);
            date = parseDate(str, pattern);
        }
        return date;
    }
}


