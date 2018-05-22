package com.etoak.book.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public class CommonsUtils {

    public static String getUUIDTo32() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static Date strToDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isEmpty(String name) {
        return name == null || name.trim().length() == 0;
    }
}
