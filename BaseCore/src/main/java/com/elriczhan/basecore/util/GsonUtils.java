package com.elriczhan.basecore.util;

import com.google.gson.Gson;

public class GsonUtils {
    public static <T> T json2Bean(String jsonResult, Class<T> clazz) {
        Gson gson = new Gson();
        T t = gson.fromJson(jsonResult, clazz);
        return t;

    }

    /**
     * 对象转换成json字符串
     *
     * @param obj
     * @return
     */
    public static String BeanToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
