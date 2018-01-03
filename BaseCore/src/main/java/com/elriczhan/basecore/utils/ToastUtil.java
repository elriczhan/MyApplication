package com.elriczhan.basecore.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast;

    /**
     * 描述：Toast提示文本.
     *
     * @param text 文本
     */
    public static void showToast(Context context, String text) {
        if (toast == null) {
            synchronized (ToastUtil.class) {
                if (toast == null) {
                    toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                }
            }
        }
        toast.setText(text);
        toast.show();
    }

    /**
     * 描述：Toast提示文本.
     * 文本
     */
    public static void showToast(Context context, int resource) {
        if (toast == null) {
            synchronized (ToastUtil.class) {
                if (toast == null) {
                    toast = Toast.makeText(context, resource, Toast.LENGTH_SHORT);
                }
            }
        }
        toast.setText(resource);
        toast.show();
    }

    public static void release() {
        toast = null;
    }
}
