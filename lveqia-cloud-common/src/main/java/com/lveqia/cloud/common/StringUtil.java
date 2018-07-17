package com.lveqia.cloud.common;


import java.util.Random;

public class StringUtil {

    /**
     * <p>Checks if a String is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the String.
     * That functionality is available in isBlank().</p>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        //org.apache.commons.lang.StringUtils.isEmpty(str);
        return str == null || str.length() == 0;
    }

    /**
     * <p>Checks if the String contains only unicode digits.
     * A decimal point is not a unicode digit and returns false.</p>
     *
     * <p><code>null</code> will return <code>false</code>.
     * An empty String (length()=0) will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = false
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if only contains digits, and is non-null
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) return false;
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String autoFillDid(String did) {
        return autoFillDid(Long.parseLong(did));
    }

    public static String autoFillDid(long did) {
        return autoFillDid(did, 9);
    }

    public static String autoFillDid(long did, int length) {
        return String.format("%0"+length+"d", did);
    }

    //方法1：length为产生的位数
    public static String getRandomString(int length){
        return getRandomString(length, false);
    }


    public static String getRandomString(int length, boolean onlyNumber){
        String str="1234567890AaSsDdFfGgHhJjKkLlQqWwEeRrTtYyUuIiOoPpZzXxCcVvBbNnMm";
        //由Random生成随机数
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        //长度为几就循环几次
        for(int i=0; i< length; ++i){ //产生0-61的数字
            int number=random.nextInt(onlyNumber?10:62);//将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.autoFillDid(100101));
    }



}
