package com.ray.frame.presentation.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 给数字金额增加逗号分隔
 * 123456789->123,456,789
 */
public class DecimalFormatTool {
      
    //每3位中间添加逗号的格式化显示  
    public static String getCommaFormat(BigDecimal value){
        return getFormat(",###.##",value);  
    }

    //每3位中间添加逗号的格式化显示
    public static String getCommaFormat(String  value){
        try{
            return getFormat(",###.##",BigDecimal.valueOf(Double.valueOf(value)));
        }catch (Exception e){
            e.printStackTrace();
        }
        return  value;
    }

    //自定义数字格式方法  
    private static String getFormat(String style,BigDecimal value){
        DecimalFormat df = new DecimalFormat();
        df.applyPattern(style);// 将格式应用于格式化器  
        return df.format(value.doubleValue());  
    }  
      
    public static void main(String[] args) {  
        System.out.println(DecimalFormatTool.getCommaFormat(new BigDecimal(0.5)));  
    }  
      
}  