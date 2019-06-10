package cn.icepear.dandelion.common.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具,try catch中不能直接使用e.printStackTrace;应该将堆栈信息输出到字符流，然后通过log打印
 * @author rimwood
 * @date 2019-04-12
 */
public class ThrowableUtil {

    /**
     * 获取堆栈信息
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
