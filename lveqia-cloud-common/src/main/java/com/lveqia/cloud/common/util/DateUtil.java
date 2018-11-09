package com.lveqia.cloud.common.util;

import com.lveqia.cloud.common.config.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具包
 * @author Leolaurel.e.l
 */
public class DateUtil {
    public static final int TYPE_DATE_10 = 0;
    public static final int TYPE_DATE_08 = 1;
    public static final int TYPE_TIME_08 = 2;
    public static final int TYPE_TIME_06 = 3;
    public static final int TYPE_DATETIME_19 = 4;
    public static final int TYPE_DATETIME_14 = 5;
    public static final int TYPE_DAY_HOUR = 6;
    public static final int TYPE_MONTH = 7;

    private static final String[] FORMAT = {"yyyy-MM-dd","yyyyMMdd", "HH:mm:ss","HHmmss"
            ,"yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "ddHH", "yyyyMM"};

    public static Date stringToDate(String date, int type) {
        return stringToDate(date, FORMAT[type]);
    }

    public static Date stringToDate(String date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {

        }
        return null;
    }

    /**
     * 计算该月有多少天， 返回值 28-29-30-31
     */
    public static int getDay(String yyyyMM){
        return getDay(Integer.parseInt(yyyyMM.substring(0,4)),Integer.parseInt(yyyyMM.substring(4)));
    }

    private static int getDay(int year,int month){
        int day;
        switch (month) {
            case 1:case 3:case 5:case 7:case 8:case 10:case 12:day = 31;break;
            case 4:case 6:case 9:case 11:day = 30;break;
            default: day = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0 ? 29 : 28;break;
        }
        return day;
    }

    /**
     * 10位时间戳转当前月 yyyyMM
     */
    public static String timestampToMonth(int timestamp) {
        return dateToString(new Date(timestamp*1000L), TYPE_MONTH);
    }

    /**
     * 10位时间戳转当前周 yyyyMMdd-yyyyMMdd
     */
    public static String timestampToWeek(long timestamp) {
        Date date1,date2;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000L);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        date1 = calendar.getTime();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        date2 = calendar.getTime();
        return dateToString(date1, TYPE_DATE_08)+"-"+dateToString(date2, TYPE_DATE_08);
    }

    /**
     * 10位时间戳转当前日 yyyyMMdd
     */
    public static String timestampToDays(long timestamp) {
        return timestampToString(timestamp, TYPE_DATE_08);
    }


    /**
     * 10位时间戳转时间字符串
     */
    public static String timestampToString(long timestamp, int type) {
        return dateToString(new Date(timestamp*1000L), type);
    }


    public static String dateToString(int type) {
        return dateToString(getCurrentDate(), type);
    }

    private static String dateToString(Date date, int type) {
        return dateToString(date, FORMAT[type]);
    }


    private static String dateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    private static Date getCurrentDate() {
        return new Date();
    }
    public static  String dateConvert(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒");
        return sdf.format(date);
    }

    /**
     * 获取不含日期的时间戳
     * @return 时+分+秒 （单位秒）
     */
    public static int getTimesNoDate(){
        Calendar c = Calendar.getInstance();
        return getTimesNoDate(new int[]{c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)});
    }


    /**
     * 获取不含日期的时间戳
     * @param desc hh:mm 或 hh:mm:ss
     * @return 时+分+秒 （单位秒）
     */
    public static int getTimesNoDate(String desc) throws NumberFormatException{
        String[] array = desc.split(":");
        int[] times = new int[array.length];
        for (int i = 0; i <array.length ; i++) {
            times[i] = Integer.parseInt(array[i]);
        }
        return getTimesNoDate(times);
    }
    /**
     * 获取不含日期的时间戳
     * @param times new Int[时，分，秒]
     * @return 时+分+秒 （单位秒）
     */
    private static int getTimesNoDate(int[] times){
        switch (times.length){
            case 1 : return times[0]*60*60;
            case 2 : return times[0]*60*60 + times[1]*60;
            case 3 : return times[0]*60*60 + times[1]*60 + times[2];
        }
        return 0;
    }

    /**
     * 获得当天凌晨时间
     * @return 时+分+秒 （单位秒）
     */
    public static long getTimesMorning(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);//获取小时
        cal.set(Calendar.MINUTE, 0);//获取分钟
        cal.set(Calendar.SECOND, 0);//获取秒
        cal.set(Calendar.MILLISECOND, 0);//获取毫秒
        return cal.getTimeInMillis()/1000 ;//返回值去除后3位  00:00:00.000
    }

    /**
     * 获得当天24点时间
     * @return 时+分+秒 （单位秒）
     */
    public static long getTimesNight(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.MINUTE, 0);//获取分钟
        cal.set(Calendar.SECOND, 0);//获取秒
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis()/1000;
    }

    /**
     * 获得当月月底的时间戳
     */
    public static long getTimesEndMonth(String yyyyMM){
        Calendar cal = Calendar.getInstance();
        cal.setTime(stringToDate(yyyyMM, "yyyyMM"));
        cal.add(Calendar.MONTH, 1);
        return cal.getTimeInMillis()/1000 ;//返回值去除后3位  00:00:00.000
    }

    /**
     * 字符串转时间戳
     */
    public static long toTimestamp(String date, int type) {
        return stringToDate(date, type).getTime()/1000L;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.dateToString(new Date(getTimesMorning()*1000),DateUtil.TYPE_DATETIME_19));
        System.out.println(DateUtil.dateToString(new Date(getTimesNight()*1000),DateUtil.TYPE_DATETIME_19));
        System.out.println(System.currentTimeMillis()/1000);
        System.out.println(getTimesNight()+9*60*60);
    }

    /**
     * 最近24小时即 当前时间到一天以前
     */
    public static long getLastDay() {
        return System.currentTimeMillis()/1000 - 24*60*60;
    }




    /**
     * 获取延后统计时间点，采用每天4:50
     */
    public static long getDelayTimestamp(String date) {
        long timestamp = Constant.TIMESTAMP_DELAY;
        if(date.length() == 6){
            timestamp += DateUtil.toTimestamp(date, DateUtil.TYPE_MONTH);
        }else{
            timestamp += DateUtil.toTimestamp(date, DateUtil.TYPE_DATE_08);
        }
        return timestamp;
    }
}
