package com.moodsmap.waterlogging.presentation.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class ValidatorUtil {

    /**
     * 正则表达式:验证真实姓名（中文名）
     */
    public static final String REGEX_REALRNAME = "([\\u4E00-\\u9FA5][ ]?[\\u4E00-\\u9FA5]([ ]?[\\u4E00-\\u9FA5][ ]?)?([\\u4E00-\\u9FA5]{1}){0,1})";

    /**
     * 正则表达式:验证座机号
     */
    public static final String REGEX_LINE_PHONE = "^(010|02[012345789]|031[0-9]|0335|0349|035[0-9]|037[012345679]|039[1234568]|041[12456789]|042[179]|043[1-9]|045[1-9]|046[4789]|047[0-9]|048[23]|051[0-9]|052[37]|053[0-9]|054[36]|055[0-9]|056[1-6]|057[0-9]|0580|059[1-9]|063[1-5]|066[0238]|069[12]|0701|071[0-9]|072[248]|073[01456789]|074[3-6]|075[0-9]|076[023689]|077[0-9]|079[0-9]|081[23678]|082[567]|083[0-9]|085[1-9]|087[0-9]|088[3678]|089[1-8]|090[123689]|091[12345679]|093[0-9]|094[13]|095[1-5]|097[012345679]|099[0-9])[-| |]{0,1}\\d{7,8}$";

    /**
     * 正则表达式:验证手机号
     */
//    private static final String REGEX_MOBILE = "^\\s*$|^(0|86|\\+86|17951)?\\s*(13[0-9]|(15[^4,\\D])|(17[0-9])|(19[0-9])|18[0-9]|14[57])\\s*\\d{4}\\s*\\d{4}\\s*$";
    public static final String REGEX_MOBILE = "^((166)|(19[8-9])|(17[01345678])|(13[0-9])|(14[15689])|(15([01235789]))|(18[0-9]))\\d{8}$";

    /**
     * 正则表达式:验证验证码
     */
    public static final String REGEX_VERIFY_CODE = "^[0-9]{6}$";

    /**
     * 正则表达式:验证货物名称(10个汉字)
     */
    public static final String REGEX_CARGO = "^[\u4e00-\u9fa5]{1,10}$";

    /**
     * 正则表达式:验证身份证
     */
    public static final String REGEX_ID_CARD = "(^([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3})|([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x))$)|(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)";

    /**
     * 正则表达式:验证重量体积
     */
    public static final String REGEX_WEIGHT_VOLUME = "^([1-9]\\d{0,3}|0)(\\.\\d)?$";
    /**
     * 正则表达式:验证价格
     */
    public static final String REGEX_PRICE = "^[1-9]\\d{0,4}$";


    /**
     * 正则表达式:验证备注（20汉字）
     */
    public static final String REGEX_REMARK = "^([\\u4e00-\\u9fa50-9a-zA-Z\\\\?%&=\\\\-_,.\\uff0c\\u3002]{0,20})$";

    /**
     * 校验密码数字，英文 6-20位
     */
//    public static final String REGEX_PSW = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
    public static final String REGEX_PSW = "^[A-Za-z0-9]{6,16}$";
    public static final String REGEX_PSW_WORD = "[a-zA-Z0-9]*";

    /**
     * 校验邮箱
     */
    public static final String REGEX_EMAIL = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";


    /**
     * 校验座机号
     *
     * @param linePhone
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isLinePhone(String linePhone) {
        return Pattern.matches(REGEX_LINE_PHONE, linePhone);
    }

    /**
     * 校验真实姓名
     *
     * @param realname
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isRealName(String realname) {
        return Pattern.matches(REGEX_REALRNAME, realname);
    }


    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        /*if (!TextUtils.isEmpty(mobile) && mobile.length() == 11) {
            return true;
        }
        return false;*/
        if(TextUtils.isEmpty(mobile))return false;
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验验证码
     *
     * @param verifyCode
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isVerifyCode(String verifyCode) {
        return Pattern.matches(REGEX_VERIFY_CODE, verifyCode);
    }


    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }


    /**
     * 校验货物名称
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isCargoName(String chinese) {
        return Pattern.matches(REGEX_CARGO, chinese);
    }


    /**
     * 校验重量体积
     *
     * @param weight
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isWeightOrVolume(String weight) {
        return Pattern.matches(REGEX_WEIGHT_VOLUME, weight);
    }


    /**
     * 校验价格
     *
     * @param price
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPrice(String price) {
        return Pattern.matches(REGEX_PRICE, price);
    }

    /**
     * 校验备注
     *
     * @param remark
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isRemark(String remark) {
        return Pattern.matches(REGEX_REALRNAME, remark);
    }

    /**
     * 校验邮箱
     *
     * @param remark
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 密码判断
     *
     * @param password 密码(包含数字字母)
     */
    public static boolean password(String password) {
        Pattern p = Pattern.compile(REGEX_PSW);
        Matcher matcher = p.matcher(password);
        if (matcher.matches()) {
            return true;
        } else {
//            Toast.makeText(context, "请输入6-20位英文字母和数字组合", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean pswKEY(String password) {
        Pattern p = Pattern.compile(REGEX_PSW_WORD);
        Matcher matcher = p.matcher(password);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}