package com.neusoft.study.springboot.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
/**
 * <p>Title: com.neusoft.study.springboot.utils</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/4 16:57
 * Description: No Description
 */
public class SerialNoUtils {

    private static long sequence;
    private static String compareTime;
    private static NumberFormat numberFormat;

    static {
        numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumIntegerDigits(5);
        numberFormat.setMaximumIntegerDigits(5);
    }

    /**
     * 生成唯一序列号
     * <p>
     * 根据当前时间加五位序号，一共20位
     *
     * @return 序列号
     */
    public static synchronized String getUNID() {
        // System.out.println(sequence);
        String currentTime = DateUtil.getCurrentDateString("yyMMddHHmmssSSS");
        if (compareTime == null || compareTime.compareTo(currentTime) != 0) {
            compareTime = currentTime;
            sequence = 1;
        } else {
            sequence++;
        }
        int i = (int) (Math.random() * 9000 + 1000);
        return currentTime + i + sequence;
    }

    /**
     * 生成唯一序列号
     * <p>
     * 根据当前时间生成，用于非批量数据记录生成时，一共15位(如果存在批量插入时，可能出现重复)
     *
     * @return 序列号
     */
    public static String getSerialforDB() {
        return DateUtil.getCurrentDateString("yyMMddHHmmssSSS");
    }

    /**
     * 生成短序列号
     * <p>
     * 根据当前时间生成，用于少量数据记录时。(可能出现重复，一般用于记录较少且变动不频繁的静态表的记录生成)
     *
     * @return 序列号
     */
    public static String getShortSerial() {
        return DateUtil.getCurrentDateString("mmssSSS");
    }

    /**
     * @return 形如 yyyyMMddHHmmssSSS-Z0000019558195832297 的(38位)保证唯一的递增的序列号字符串，
     * 主要用于数据库的主键，方便基于时间点的跨数据库的异步数据同步。
     * 前半部分是currentTimeMillis，后半部分是nanoTime（正数）补齐20位的字符串，
     * 如果通过System.nanoTime()获取的是负数，则通过nanoTime =
     * nanoTime+Long.MAX_VALUE+1; 转化为正数或零。
     */
    public static String getTimeMillisSequence() {
        long nanoTime = System.nanoTime();
        String preFix = "";
        if (nanoTime < 0) {
            preFix = "A";// 负数补位A保证负数排在正数Z前面,解决正负临界值(如A9223372036854775807至Z0000000000000000000)问题。
            nanoTime = nanoTime + Long.MAX_VALUE + 1;
        } else {
            preFix = "Z";
        }
        String nanoTimeStr = String.valueOf(nanoTime);

        int difBit = String.valueOf(Long.MAX_VALUE).length() - nanoTimeStr.length();
        for (int i = 0; i < difBit; i++) {
            preFix = preFix + "0";
        }
        nanoTimeStr = preFix + nanoTimeStr;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS"); // 24小时制
        // String
        // timeMillisSequence=sdf.format(System.currentTimeMillis())+"-"+nanoTimeStr;
        return nanoTimeStr;
    }

    public static void main(String[] args) {
        // System.out.println(getTimeMillisSequence());
        String s1 = "1111";
        int count = 0;
        long stat = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String s2 = getUNID();
            System.out.println(s2);
            // if(s1.equals(s2)){
            // count++;
            // }
            // s1=s2;
        }
        System.out.println(System.currentTimeMillis() - stat);
        System.out.println(count);
    }

    public static synchronized String getUNID18() {
        String currentTime = DateUtil.getCurrentDateString("yyMMddHHmmssSSS");
        if (compareTime == null || compareTime.compareTo(currentTime) != 0) {
            compareTime = currentTime;
            sequence = 1;
        } else {
            sequence++;
        }
        // System.out.println(sequence);
        int i = (int) (Math.random() * 90 + 10);
        // System.out.println(numberFormat.format(sequence));
        // System.out.println(currentTime + i+sequence);
        return currentTime + i + sequence;
    }
}
