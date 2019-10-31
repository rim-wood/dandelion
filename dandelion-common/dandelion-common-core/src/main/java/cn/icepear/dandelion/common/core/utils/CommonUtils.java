package cn.icepear.dandelion.common.core.utils;



import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author rim-wood
 * @description 日常工具类
 * @date Created on 2019/10/18.
 */
public class CommonUtils {

    /**
     * 获取当天流水号 如:LP20180410-000001
     *
     * @return
     */
    public static String getCurrentSerial(String preKey) {
        StringBuilder serialNo = new StringBuilder(preKey);
        serialNo.append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        serialNo.append("-");
        serialNo.append(new DecimalFormat("00000").format(queryNewSerial(serialNo.toString())));
        return serialNo.toString();
    }

    /**
     * 获取当前序列号
     */
    private static Integer queryNewSerial(String preK) {
        RedisUtils redisUtils = SpringContextHolder.getBean("redisUtils");
        Integer currentSerial =  redisUtils.get(preK, Integer.class);
        if( currentSerial == null ) {
            currentSerial = 1;
        }else {
            currentSerial ++;
        }
        redisUtils.set(preK, currentSerial, RedisUtils.DEFAULT_EXPIRE);
        return currentSerial;
    }

}
