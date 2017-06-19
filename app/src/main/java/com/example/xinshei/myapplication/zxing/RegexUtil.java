package com.example.xinshei.myapplication.zxing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xinshei on 16/12/21.
 */

public class RegexUtil {
    private static Pattern p1 = Pattern.compile("xinshui://app/webview/\\?url=((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?");
    private static Pattern p2 = Pattern.compile("xinshui://app/browser/\\?url=((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?");
    private static Pattern p3 = Pattern.compile("xinshui://app/mainpage/article/[a-zA-Z0-9\\-]{1,50}/");
    private static Pattern p4 = Pattern.compile("xinshui://app/find/topic/[a-zA-Z0-9\\-]{1,50}/");
    private static Pattern p5 = Pattern.compile("xinshui://app/bigv/[a-zA-Z0-9\\-]{1,50}/");
    private static Pattern p6 = Pattern.compile("xinshui://app/find/recommend/");
    private static Pattern p7 = Pattern.compile("xinshui://app/find/prediction/");
    private static Pattern p8 = Pattern.compile("xinshui://app/mainpage/");
    private static Pattern p9 = Pattern.compile("xinshui://app/mine/validate/");
    private static Pattern p10 = Pattern.compile("xinshui://app/message/usermessage/");


    public static boolean matchBrowser(String url) {
        Matcher m = p2.matcher(url);
        return m.matches();
    }

    public static boolean matches(String url) {

        Matcher m = p1.matcher(url);
        if (m.matches()) {
            return true;
        }
        m = p3.matcher(url);
        if (m.matches()) {
            return true;
        }
        m = p4.matcher(url);
        if (m.matches()) {
            return true;
        }
        m = p5.matcher(url);
        if (m.matches()) {
            return true;
        }
        m = p6.matcher(url);
        if (m.matches()) {
            return true;
        }
        //暂时不要prediction
//        m = p7.matcher(url);
//        if (m.matches()) {
//            return true;
//        }
//        m = p8.matcher(url);
//        if (m.matches()) {
//            return true;
//        }
        m = p9.matcher(url);
        if (m.matches()) {
            return true;
        }
        m = p10.matcher(url);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    public static void jumper(Context context, String uri) {
        try {
            if (RegexUtil.matches(URLDecoder.decode(uri, "UTF-8"))) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(intent);
            } else if (RegexUtil.matchBrowser(URLDecoder.decode(uri, "UTF-8"))) {
                //开启网页
                Uri website = Uri.parse(uri);
                String url = website.getQueryParameter("url");
                website = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, website);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "二维码错误!", Toast.LENGTH_SHORT).show();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(context, "二维码错误!", Toast.LENGTH_SHORT).show();
        }
    }

}
