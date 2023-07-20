package com.sunmi.wallpaperservice.utils;

import java.lang.reflect.Method;

/**
 * 获取 / 设置 系统属性
 *
 * @author sm2886
 */
public class SystemPropertyUtil {

    private static SystemPropertyUtil instance = new SystemPropertyUtil();

    private SystemPropertyUtil() {

    }

    public static SystemPropertyUtil getInstance() {
        return instance;
    }

    /**
     * Get the value for the given key.
     *
     * @return an empty string if the key isn't found
     */
    public String get(String key) {
        return instance.getProperty(key, null);
    }

    public  String getProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, defaultValue));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }

    public void setProperty(String key, String value) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class, String.class);
            set.invoke(c, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

