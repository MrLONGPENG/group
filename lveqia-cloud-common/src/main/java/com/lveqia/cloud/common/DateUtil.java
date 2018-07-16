package com.lveqia.cloud.common;

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

    public static final String[] FORMAT = {"yyyy-MM-dd","yyyyMMdd", "HH:mm:ss","HHmmss"
            ,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss","ddHH"};

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


    public static String dateToString(int type) {
        return dateToString(getCurrentDate(), type);
    }

    public static String dateToString(Date date, int type) {
        return dateToString(date, FORMAT[type]);
    }


    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date getCurrentDate() {
        return new Date();
    }


    /**
     * 获取支付日期，当大于指定时间（不含日期，只包括时间），即当天；小于指定时间，日期减一
     * @param time  不含日期，只包括时间
     * @return 八位日期  yyyyMMdd
     */
    public static  String getPayDate(long time){
        Calendar cal = Calendar.getInstance();
        if((cal.get(Calendar.HOUR_OF_DAY) * 60*60 + cal.get(Calendar.MINUTE)* 60) *1000 > time ){ //
            return  dateToString(cal.getTime(), TYPE_DATE_08);
        }
        cal.add(Calendar.DAY_OF_YEAR , -1);
        return dateToString(cal.getTime(), TYPE_DATE_08);
    }



    public static long getTimesMorning(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);//获取小时
        cal.set(Calendar.SECOND, 0);//获取分钟
        cal.set(Calendar.MINUTE, 0);//获取秒
        cal.set(Calendar.MILLISECOND, 0);//获取毫秒
        return cal.getTimeInMillis()/1000 ;//返回值去除后3位  00:00:00.000
    }
    //获得当天24点时间
    public static long getTimesNight(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis()/1000;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.dateToString(new Date(getTimesMorning()*1000),DateUtil.TYPE_DATETIME_19));
        System.out.println(DateUtil.dateToString(new Date(getTimesNight()*1000),DateUtil.TYPE_DATETIME_19));
        System.out.println(System.currentTimeMillis()/1000);
        System.out.println(getTimesNight()+9*60*60);
    }
}
