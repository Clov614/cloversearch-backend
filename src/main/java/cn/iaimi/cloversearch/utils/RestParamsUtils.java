package cn.iaimi.cloversearch.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/1
 */
public class RestParamsUtils {


    public static boolean isBlankStr(String str) {
        if (!StringUtils.isNotBlank(str)) {
            return true;
        }
        return false;
    }

    /**
     * 字符串为空时返回默认值
     * @param target
     * @param defaultStr
     * @return not blank
     */
    public static String isBlankDefault(String target, String defaultStr) {
        if (isBlankStr(target))
            return defaultStr;
        return target;
    }

    public static int isBlankDefault(int target, int defaultVal) {
        if (0 == target)
            return defaultVal;
        return target;
    }

    public static Integer isBlankDefault(Integer target, Integer defaultVal) {
        if (null == target || 0 == target)
            return defaultVal;
        return target;
    }
}
