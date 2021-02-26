package com.zzh.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author swj
 * @description
 * @date
 */
public class DateUtil {
    /*
     *功能描述  将商品转换成时间戳
     * @author zzh
     * @date 2021/2/5
     * @param
     * @return java.lang.String
     */
    public static String getOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /*
     *功能描述 将下单时间类型转换
     * @author zzh
     * @date 2021/2/5
     * @param
     * @return java.lang.String
     */
    public static String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
