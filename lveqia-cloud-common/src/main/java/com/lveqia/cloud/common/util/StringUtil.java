package com.lveqia.cloud.common.util;


import com.lveqia.cloud.common.config.Constant;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Random;

public class StringUtil {

    /**金额为分的格式 */
    private static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

    /**
     * 检查字符串("") or null
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     *  检查字符串是否是数字， null/空字符串 皆为false
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


    /**
     *  检查字符串是否是字母， null/空字符串 皆为false
     * @param str  the String to check, may be null
     * @return <code>true</code> if only contains digits, and is non-null
     */
    public static boolean isLetter(String str) {
        if (isEmpty(str)) return false;
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetter(str.charAt(i))) {
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



    /**
     * 通过指定符号拼接字符串
     * @return  params[sign]arg1[sign]...args
     */
    public static String join(String separator, Object... array){
        StringBuffer buffer = new StringBuffer();
        for (Object key:array) {
            if(buffer.length()!=0) buffer.append(separator);
            buffer.append(key);
        }
        return new String(buffer);
    }


    /**
     * 采用分号字符串形式拼接
     */
    public static String toLink(Object... args) {
        return join(Constant.SIGN_SEMICOLON, args);
    }

    /**
     * 采用逗号字符串形式拼接
     */
    public static String toLinkByComma(Object... args) {
        return join(Constant.SIGN_COMMA, args);
    }


    /**
     * 把格式 1_2_3 换成 1,2,3
     * @param key 1_2_3
     * @return   1,2,3
     */
    public static String formatIds(String key) {
        if(key!=null && key.contains(Constant.SIGN_LINE)) { // 把格式 1_2_3 换成 1,2,3
            key = key.replaceAll(Constant.SIGN_LINE, Constant.SIGN_COMMA);
        }
        return key;
    }


    /**
     * 将分为单位的转换为元 （除100）
     */
    public static String changeF2Y(String amount){
        if(isEmpty(amount)) return Constant.DIGIT_ZERO;
        if(!amount.matches(CURRENCY_FEN_REGEX)) {
            return Constant.DIGIT_ZERO;
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)
                ,2,RoundingMode.UP).toString();
    }

    /**
     * 将元为单位的转换为分 （乘100）
     */
    public static String changeY2F(String amount){
        if(isEmpty(amount)) return Constant.DIGIT_ZERO;
        return BigDecimal.valueOf(Double.valueOf(amount)).multiply(new BigDecimal(100)).toString();
    }

    /**
     * 计算百分数
     */
    public static String getPercent(String count, String total) {
        Double c = Double.valueOf(count), t = Double.valueOf(total);
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);//控制保留小数点后几位，2：表示保留2位小数点
        return nf.format( c/t);
    }
}
