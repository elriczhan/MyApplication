package com.elriczhan.basecore.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created by xinshei on 2018/1/2.
 */

public class TextUtil {

    /**
     * 搜索时的文本变色；
     */
    public static void changeTextColor(TextView content, String summary, String target) {
        if (summary != null) {
            if (summary.contains(target)) {
                int targetStart = 0;
                int targetEnd = 0;
                SpannableStringBuilder style = new SpannableStringBuilder(summary);
                while (summary.indexOf(target, targetEnd) != -1) {
                    targetStart = summary.indexOf(target, targetEnd);
                    targetEnd = targetStart + target.length();
                    style.setSpan(new ForegroundColorSpan(Color.BLUE), targetStart, targetEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                }
                content.setText(style);
            } else {
                content.setText(summary);
            }
        }
    }


    /**
     * 实现文本复制功能
     *
     * @param content 上下文
     */
    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        ToastUtil.showToast(context, "评论已成功复制到剪切板！");
    }
}
