package com.elriczhan.basecore.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xinshei on 2018/1/2.
 */

public class TimeUtil {


    /**
     * "2016-11-17 08:21:00"
     * 此方法用来把一个年月日时分秒的日期进行格式化。
     *
     * @param publish_date 初始化之前的日期
     * @return 初始化后的日子
     */
    public static String FormatDate(String publish_date) {

        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        Date date = null;
        try {
            date = df.parse(publish_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);

        String formatDate = "";
        if ((currentYear != c.get(Calendar.YEAR))) {
            formatDate += currentYear + "-";
        }
        formatDate += ((c.get(Calendar.MONTH) + 1) < 10 ? "0" + (c.get(Calendar.MONTH) + 1) : (c.get(Calendar.MONTH) + 1)) + "-" + (c.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + c.get(Calendar.DAY_OF_MONTH) : c.get(Calendar.DAY_OF_MONTH)) + " "
                + (c.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + c.get(Calendar.HOUR_OF_DAY) : c.get(Calendar.HOUR_OF_DAY)) + ":" + (c.get(Calendar.MINUTE) < 10 ? "0" + c.get(Calendar.MINUTE) : c.get(Calendar.MINUTE));
        return formatDate;
    }

    /**
     * 格式化日子成为 yyyy-MM-dd
     *
     * @param modified_datetime the date in
     * @return the date out
     */
    public static String FormatDateyyyyMMdd(String modified_datetime) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        Date date = null;
        try {
            date = df.parse(modified_datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);

        return c.get(Calendar.YEAR) + "-" + ((c.get(Calendar.MONTH) + 1) < 10 ? "0" + (c.get(Calendar.MONTH) + 1) : (c.get(Calendar.MONTH) + 1)) + "-" + (c.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + c.get(Calendar.DAY_OF_MONTH) : c.get(Calendar.DAY_OF_MONTH)) + " "
                + (c.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + c.get(Calendar.HOUR_OF_DAY) : c.get(Calendar.HOUR_OF_DAY)) + ":" + (c.get(Calendar.MINUTE) < 10 ? "0" + c.get(Calendar.MINUTE) : c.get(Calendar.MINUTE));
    }

    public static String FormatDateyyyyMMddNomm(String modified_datetime) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        Date date = null;
        try {
            date = df.parse(modified_datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);

        return c.get(Calendar.YEAR) + "-" + ((c.get(Calendar.MONTH) + 1) < 10 ? "0" + (c.get(Calendar.MONTH) + 1) : (c.get(Calendar.MONTH) + 1))
                + "-" + (c.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + c.get(Calendar.DAY_OF_MONTH) : c.get(Calendar.DAY_OF_MONTH));
    }


}
