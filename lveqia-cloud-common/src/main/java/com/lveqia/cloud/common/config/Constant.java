package com.lveqia.cloud.common.config;

public class Constant {
    /** 超级管理员 */
    public static final String ROLE_ADMIN = "ROLE_admin";
    ///** 木巨开发者 */
    //public static final String ROLE_DEVELOPER = "ROLE_developer";
    ///** 木巨主管 */
    //public static final String ROLE_MANAGER = "ROLE_manager";
    ///** 运营主管 */
    //public static final String ROLE_CAPTAIN = "ROLE_captain";
    /** 普通运营 */
    public static final String ROLE_OPERATE = "ROLE_operate";
    ///** 普通账号 */
    //public static final String ROLE_READERS = "ROLE_readers";

    /** 符号--与符号（&） */
    public static final String SIGN_AND = "&";
    /** 符号--下划线（_）  */
    public static final String SIGN_LINE = "_";
    /** 符号--百分号（%） */
    public static final String SIGN_PERCENT = "%";

    /** 符号--逗号（,） */
    public static final String SIGN_DOU_HAO = ",";
    /** 符号--冒号（:） */
    public static final String SIGN_MAO_HAO= ":";
    /** 符号--分号（;） */
    public static final String SIGN_FEN_HAO = ";";
    /** 符号--井号（#）  */
    public static final String SIGN_NUMBER = "#";
//    /** 符号--加号（+）  */
//    public static final String SIGN_PLUS = "+";
//    /** 符号--减号（-）  */
//    public static final String SIGN_MINUS = "-";

    /** 数字--0  */
    public static final String DIGIT_ZERO = "0";

    /** 字符串--未知  */
    public static final String STRING_UNKNOWN = "未知";

    /** 时间戳-- 3 hour  */
    public static final long TIMESTAMP_HOUR_3 = 3*60*60;

    /** 时间戳-- 8 hour  */
    public static final long TIMESTAMP_HOUR_8 = 8*60*60;

    /** 时间戳-- 1 天 （24 hour） */
    public static final long TIMESTAMP_DAYS_1 = 24*60*60;

    /** 时间戳-- 7 天  */
    public static final long TIMESTAMP_DAYS_7 = 7*24*60*60;

    /** 时间戳-- 4:50  */
    public static final long TIMESTAMP_DELAY = 4*60*60+50*60;

    /** JSON 数组字符串 */
    public static final String JSON_ARRAY = "[]";

    /** 开发模式（dev）*/
    public static final String MODEL_DEV = "dev";
}
