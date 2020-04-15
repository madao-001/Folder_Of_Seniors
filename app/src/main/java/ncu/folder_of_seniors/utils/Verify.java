package ncu.folder_of_seniors.utils;

import java.util.regex.Pattern;

/**
 * 类说明: 验证工具
 */
public class Verify {
//    public static boolean isPhone(final String phone){
//        String regular="^[1][3,4,5,7,8,9][0-9]{9}$";
//        Pattern pattern=Pattern.compile(regular);
//        Matcher matcher=pattern.matcher(phone);
//        return matcher.matches();
//    }


    /**
     * 数字正则（包含整数或小数）
     */
    public static final String REGEX_NUM="^(\\-|\\+)?\\d+(\\.\\d+)?$";

    /**
     * 旅馆编号正则（10位整数）
     */
    public static final String REGEX_HOTELCODE="^[0-9]{10}$";

    /**
     * 由数字，字母或下划线组成
     */
    public static final String REGEX_NUMORLETTER="^[0-9a-zA-Z_]{1,}$";


    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式: 验证是否是中文名
     */
    public static final String REGEX_ZHNAME = "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 是否是数组(包含整数或者小数）
     * @param str
     * @return
     */
    public static boolean isNum(String str){
        return Pattern.matches(REGEX_NUM,str);
    }
    /**
     * 字符串是否为空
     * return 为空返回true
     */
    public static boolean isStrEmpty(String str){
        return (str==null||str.trim().equals(""));
    }

    /**
     * 由字母，下划线，或数字组成
     * @param str
     * @return
     */
    public static boolean isConsistsOfNum_Letter(String str){
        return Pattern.matches(REGEX_NUMORLETTER,str);
    }

    /**
     * 校验是否为旅馆编号
     * @param code
     * @return
     */
    public static boolean isHotelCode(String code){
        return Pattern.matches(REGEX_HOTELCODE,code);
    }

    /**
     * 校验用户名
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUserName(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验年龄
     * @param age
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isAge(String age) {
        try {
            Integer age2 = Integer.parseInt(age.trim());
            if(age2>0&&age2<120){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    /**
     * 校验是否是中文名
     * @param zhname
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isZhName(String zhname){
        return Pattern.matches(REGEX_ZHNAME,zhname);
    }

    /**
     * 校验密码
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }
}
