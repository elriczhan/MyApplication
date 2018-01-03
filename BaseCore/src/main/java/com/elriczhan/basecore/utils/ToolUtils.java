package com.elriczhan.basecore.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ToolUtils {

    /**
     * @param context 上下文
     * @return packName versionName version
     */
    public static String[] getAppInfo(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return new String[]{pkName, versionName, versionCode + ""};
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static String getCurrent() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);//设置日期格式
        return format.format(calendar.getTime());
    }

    public static boolean isContinuousDates(List<Long> dates) {
        if (dates.size() > 0) {
            Calendar calender1 = Calendar.getInstance();
            Calendar calender2 = Calendar.getInstance();
            calender1.setTime(new Date(dates.get(0)));
            calender2.setTime(new Date(dates.get(dates.size() - 1)));
            calender1.add(Calendar.DATE, dates.size() - 1);
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
            return dates.size() == 4 && format.format(calender1.getTime()).equals(format.format(calender2.getTime()));
        }
        return false;
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * @author wxy
     * @Description: TODO px 转化为dp
     * @data: 2015-10-12 下午6:07:33
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    //            XinShui/1.1.2 Android/Nokia7.0
    public static String getUserAgent(Context context) {
        String agent = " Android/" + Build.BRAND + Build.VERSION.RELEASE;
        try {
            agent = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName
                    + " Android/" + Build.BRAND + Build.VERSION.RELEASE;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return agent;
    }
}
