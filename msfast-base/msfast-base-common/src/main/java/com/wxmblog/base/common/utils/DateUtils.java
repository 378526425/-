package com.wxmblog.base.common.utils;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return dateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return dateToStr(YYYY_MM_DD, date);
    }

    public static final String dateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获取某一天的开始时间
     *
     * @param date，不能为null
     * @return
     */
    public static Date getStartTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new Calendar.Builder()
                .setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .build()
                .getTime();
    }

    /**
     * 获取某一天的结束时间
     *
     * @param date，不能为null
     * @return
     */
    public static Date getEndTimeOfDay(Date date) {
        Date startTimeOfNextDay = getStartTimeOfNextDay(date);
        return new Date(startTimeOfNextDay.getTime() - 1L);
    }

    /**
     * 获取某一天的第二天的开始时间
     *
     * @param date，不能为null
     * @return
     */
    public static Date getStartTimeOfNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new Calendar.Builder()
                .setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) + 1)
                .build()
                .getTime();
    }

    /*
     * @Author 根据出生日期计算年龄
     * @Description
     * @Date 20:31 2022/11/12
     * @Param
     * @return
     **/
    public static Integer getAgeByBirth(Date birthDay) {
        if (birthDay == null) {
            return null;
        }
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    /*
     * @Author
     * @Description
     * @Date 20:34 2022/11/12
     * @Param
     * @return
     **/
    public static String getChineseTime(Date latelyTime) {
        if (latelyTime != null) {
            long timeInterval = Math.abs(System.currentTimeMillis() - latelyTime.getTime()) / 1000;//秒
            if (timeInterval <= 180) {
                return "刚刚";
            } else if (timeInterval <= 3600) {
                return (int) timeInterval / 60 + "分钟前";
            } else if (timeInterval <= 86400) {
                return (int) timeInterval / 3600 + "小时前";
            } else if (timeInterval <= 2592000) {
                return (int) timeInterval / 86400 + "天前";
            } else {
                return dateToStr(YYYY_MM_DD_HH_MM_SS, latelyTime);
            }
        } else {
            return "刚刚";
        }
    }


    public static String getConstellation(Date birthday) {
        String xingzuo = "";
        if (birthday == null)
            return xingzuo;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        switch (month) {
            case 1:
                xingzuo = day < 21 ? "摩羯座" : "水瓶座";
                break;
            case 2:
                xingzuo = day < 20 ? "水瓶座" : "双鱼座";
                break;
            case 3:
                xingzuo = day < 21 ? "双鱼座" : "白羊座";
                break;
            case 4:
                xingzuo = day < 21 ? "白羊座" : "金牛座";
                break;
            case 5:
                xingzuo = day < 22 ? "金牛座" : "双子座";
                break;
            case 6:
                xingzuo = day < 22 ? "双子座" : "巨蟹座";
                break;
            case 7:
                xingzuo = day < 23 ? "巨蟹座" : "狮子座";
                break;
            case 8:
                xingzuo = day < 24 ? "狮子座" : "处女座";
                break;
            case 9:
                xingzuo = day < 24 ? "处女座" : "天秤座";
                break;
            case 10:
                xingzuo = day < 24 ? "天秤座" : "天蝎座";
                break;
            case 11:
                xingzuo = day < 23 ? "天蝎座" : "射手座";
                break;
            case 12:
                xingzuo = day < 22 ? "射手座" : "摩羯座";
                break;
        }
        return xingzuo;
    }
}
