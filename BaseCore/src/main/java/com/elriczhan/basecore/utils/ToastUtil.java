package com.elriczhan.basecore.utils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast;
    private static Toast toast2;

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
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    /**
     * 描述Toast提示文本 设置显示字体大小
     *
     * @param context
     * @param text
     * @param size
     */
    public static void showToast(Context context, String text, int size) {
        if (toast2 == null) {
            synchronized (ToastUtil.class) {
                if (toast2 == null) {
                    toast2 = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                    TextView v = toast2.getView().findViewById(android.R.id.message);
                    v.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                }
            }
        } else {
            toast2.setText(text);
        }
        toast2.show();
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
        } else {
            toast.setText(resource);
        }
        toast.show();
    }

    public static void release() {
        toast = null;
        toast2 = null;
    }
}
