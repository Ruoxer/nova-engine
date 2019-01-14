package com.war3.nova.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 
 * 数值工具类
 * 
 * @author Cytus_
 * @since 2018年5月26日 上午9:55:15
 * @version 1.0
 *
 */
public final class Numbers {

    /**
     * 判断传递参数是否是int类型
     * @param str
     * @return
     */
    public final static boolean isInteger(String str){
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * 转换为int 
     * @param str
     * @return
     */
    public final static Integer toInteger(String str) {
        return toInteger(str, null);
    }
    
    /**
     * 转换为int
     * @param str
     * @param defVal
     * @return
     */
    public final static Integer toInteger(String str, Integer defVal) {
        return isInteger(str) ? Integer.valueOf(str) : defVal;
    }
    
    /**
     * 转换为int
     * @param object
     * @return
     */
    public final static Integer toInteger(Object object) {
        return toInteger(object, null);
    }
    
    /**
     * 转换为int
     * @param object
     * @param defVal
     * @return
     */
    public final static Integer toInteger(Object object, Integer defVal) {
        String str = Strings.replaceObjNull(object);
        return toInteger(str, defVal);
    }
    
    
    /**
     * 判断传递参数是否是double类型
     * @param str
     * @return
     */
    public final static boolean isDouble(String str){
        try {
            Double.parseDouble(str);
        } catch (Exception e) {
            return false;
        }
        return true;    
    }
    
    /**
     * 转double
     * @param str
     * @return
     */
    public final static Double toDouble(String str) {
        return toDouble(str, null);
    }
    
    /**
     * 转double
     * @param str
     * @param defVal
     * @return
     */
    public final static Double toDouble(String str, Double defVal) {
        return isDouble(str) ? Double.valueOf(str) : defVal;
    }
    
    /**
     * 转double
     * @param object
     * @return
     */
    public final static Double toDouble(Object object) {
        return toDouble(object, null);
    }
    
    /**
     * 转double
     * @param object
     * @param defVal
     * @return
     */
    public final static Double toDouble(Object object, Double defVal) {
        String str = Strings.replaceObjNull(object);
        return toDouble(str, defVal);
    }
    
    /**
     * 将金额格式化  如：111111111.1111则转化为：111,111,111.11
     * @param str
     * @return
     */
    public final static String formatAmt(String str) {
        return formatAmt(str, "###,###.00");
    }
    
    /**
     * 将金额格式化
     * @param str
     * @param pattern
     * @return
     */
    public final static String formatAmt(String str, String pattern) {
        if (Strings.isNotEmpty(str)) {
            try {
                return new DecimalFormat(pattern).format(Double.valueOf(str));
            } catch (Exception e) { }
        }
        return null;
    }
    
    /**
     * 格式化金额
     * @param number
     * @return
     */
    public final static String formatAmt(Number number) {
        return formatAmt(number.toString());
    }
    
    /**
     * 格式化金额
     * @param number
     * @return
     */
    public final static String formatAmt(Number number, String pattern) {
        return formatAmt(number.toString(), pattern);
    }
    
    /**
     * 将数字金额转化为大写金额
     * @param amount
     * @return
     */
    public final static String digitalConvChinese(double amount)  {
        DecimalFormat nf = new DecimalFormat("#0.##");
        nf.setMinimumFractionDigits(2);//小数点后不足的补零
        String amt = nf.format(amount);//将double类型的数格式化并转换成字符串,实际上进行了四舍五入
        String dotPart = ""; //取小数位
        String intPart = ""; //取整数位
        int dotPos;

        if ((dotPos = amt.indexOf('.')) != -1) {
            intPart = amt.substring(0, dotPos);
            dotPart = amt.substring(dotPos + 1);
        } else {
            intPart = amt;
        }
        if (intPart.length() > 16)
            return null;
        String intBig = processInteger(intPart);
        String dotBig = processDecimal(dotPart);

        if ((dotBig.length() == 0) && (intBig.length() != 0)) {
            return intBig + "元整";
        } else if ((dotBig.length() == 0) && (intBig.length() == 0)) {
            return intBig + "零元";
        } else if ((dotBig.length() != 0) && (intBig.length() != 0)) {
            return intBig + "元" + dotBig;
        } else {
            return dotBig;
        }
       
    }

    /**
     * 用来处理小数部分
     * @param dotPart
     * @return
     */
    private final static String processDecimal(String dotPart) {
        String NUM = "零壹贰叁肆伍陆柒捌玖";
        String DOTUNIT = "角分厘";
        String strRet = "";
        for (int i = 0; i < dotPart.length() && i < 3; i++) {
            int num;
            if ((num = Integer.parseInt(dotPart.substring(i, i + 1))) != 0)
                strRet += NUM.substring(num, num + 1)
                        + DOTUNIT.substring(i, i + 1);
        }
        return strRet;
    }

    /**
     * 用来处理整数部分
     * @param intPart
     * @return
     */
    private final static String processInteger(String intPart) {
        String GRADEUNIT = "仟万亿兆";
        int GRADE = 4;
        int grade; //级长
        String result = "";
        String strTmp = "";

        //得到当级长
        grade = intPart.length() / GRADE;
        //调整级次长度
        if (intPart.length() % GRADE != 0)
            grade += 1;

        //对每级数字处理，先处理最高级，然后再处理低级的
        for (int i = grade; i >= 1; i--) {
            strTmp = getNowGradeVal(intPart, i);//取得当前级次数字
            result += conversionChinese(strTmp);//转换大写
            result = removeContinueZero(result);//除零 去掉连续的零
            //加级次单位
            if (i > 1) //末位不加单位
                //单位不能相连续，连续4个零的时候要特殊处理
                if(conversionChinese(strTmp).equals("零零零零")){
                    result = result+"零";
                }else{
                 result += GRADEUNIT.substring(i - 1, i);
                }
        }
        return result;
    }
    
    
    private final static String getNowGradeVal(String strVal, int grade) {
        //得到当前级次的串
        int GRADE = 4;
        String rst;
        if (strVal.length() <= grade * GRADE)
            rst = strVal.substring(0, strVal.length() - (grade - 1) * GRADE);
        else
            rst = strVal.substring(strVal.length() - grade * GRADE, strVal
                    .length()
                    - (grade - 1) * GRADE);
        return rst;
    }

    /**
     * 转换中文
     * 
     * @param strVal
     * @return
     */
    private final static String conversionChinese(String strVal) {
        //数值转换
        String NUM = "零壹贰叁肆伍陆柒捌玖";
        String UNIT = "仟佰拾个";
        String rst = "";

        for (int i = 0; i < strVal.length(); i++) {
            String s = strVal.substring(i, i + 1);
            int num = Integer.parseInt(s);
            if (num == 0) {
                //“零”作特殊处理
                if (i != strVal.length()) //转换后数末位不能为零
                    rst += "零";
            } else {
                //If IntKey = 1 And i = 2 Then
                //“壹拾”作特殊处理
                //“壹拾”合理
                //Else
                rst += NUM.substring(num, num + 1);
                //End If
                //追加单位
                if (i != strVal.length() - 1)//个位不加单位
                    rst += UNIT.substring(i + 4 - strVal.length(), i + 4
                            - strVal.length() + 1);
            }
        }
        return rst;
    }

    private final static String removeContinueZero(String strVal) {
        //去除连继的“零”
        String strBefore = strVal.substring(0, 1);; //前一位置字符
        String strRst = strBefore;
        String strNow; //现在位置字符

        for (int i = 1; i < strVal.length(); i++) {
            strNow = strVal.substring(i, i + 1);
            if (!strNow.equals("零") || !strBefore.equals("零"))
                strRst += strNow;
            strBefore = strNow;
        }

        //末位去零
        if (strRst.substring(strRst.length() - 1, strRst.length()).equals("零"))
            strRst = strRst.substring(0, strRst.length() - 1);
        return strRst;
    }
    

    /**
     * 奇数判断 是奇数返回true, 偶数返回false
     * @param a
     * @return
     */
    public final static boolean isOddNumber(int a) {
        return (a & 1) == 1;
    }

    /**
     * 偶数判断 是偶数返回true, 偶数返回false
     * @param a
     * @return
     */
    public final static boolean isEvenNumber(int a) {
        return (a & 1) == 0;
    }

    /**
     * 获取平均值
     * @param x
     * @param y
     * @return
     */
    public final static int getAverageValue(int x, int y) {
        return (x & y) + (( x ^ y) >> 1);
    }

    /**
     * 判断是否为2的n次方
     * @param x
     * @return
     */
    public final static boolean is2NSquare(int x) {
        return ((x & (x - 1)) == 0) && (x != 0);
    }
    
    /**
     * 是否为BigDecimal
     * @param str
     * @return
     */
    public final static boolean isBigDecimal(String str) {
        try {
            new BigDecimal(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 字符串转化为bigDecimal类型，如果字符串为空或null，则返回null
     * @param str
     * @return
     */
    public final static BigDecimal toBigDecimal(String str) {
        return isBigDecimal(str) ? new BigDecimal(str) : null;
    }

    /**
     * bigDecimal
     * @param str
     * @param defVal
     * @return
     */
    public final static BigDecimal toBigDecimal(String str, BigDecimal defVal) {
        return isBigDecimal(str) ? new BigDecimal(str) : defVal;
    }
    
    /**
     * 字符串转化为bigDecimal类型, 如果字符串为空或null，则返回默认值
     * @param str
     * @param defaultValue
     * @return
     */
    public final static BigDecimal toBigDecimal(String str, String defVal) {
        return toBigDecimal(str, new BigDecimal(defVal));
    }

    /**
     * 字符串转化为bigDecimal类型, 如果字符串为空或null，则返回默认值
     * @param str
     * @param defaultValue
     * @return
     */
    public final static BigDecimal toBigDecimal(String str, Number defVal) {
        return toBigDecimal(str, defVal.toString());
    }

    /**
     * 判断是否为数字
     * @param str
     * @return
     */
    public final static boolean isNumeric(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }


    /**
     * 字符串是否为Float类型
     * @param str
     * @return
     */
    public final static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }

    /**
     * 转Float
     * @param str
     * @return
     */
    public static final Float toFloat(String str) {
        return toFloat(str, null);
    }
    
    /**
     * 转Float
     * @param str
     * @param defVal
     * @return
     */
    public static final Float toFloat(String str, Float defVal) {
        return isFloat(str) ? Float.valueOf(str) : defVal;
    }
    
    /**
     * 转Float
     * @param object
     * @return
     */
    public static final Float toFloat(Object object) {
        return toFloat(object, null);
    }
    
    /**
     * 转Float
     * @param object
     * @param defVal
     * @return
     */
    public static final Float toFloat(Object object, Float defVal) {
        String str = Strings.replaceObjNull(object);
        return toFloat(str, defVal);
    }
    
    public static final BigDecimal divide(Number n1, Number n2, int scale) {
        return new BigDecimal(n1.doubleValue()).divide(new BigDecimal(n2.doubleValue())).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    
}
