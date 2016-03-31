package com.ace.core.util;

import java.util.Random;
import java.util.UUID;

public class StringUtil {
	//生成36位UUID
	public static  String getUUID(){
		return UUID.randomUUID().toString().toUpperCase();
	}
	//动态长度生成UUID
	public static  String getUUID(int length){
		return UUID.randomUUID().toString().toUpperCase().substring(0, length);
	}	
	
	//生成随机数字和字母,  
    public static String getStringRandom(int length) {  
          
        String val = "";  
        Random random = new Random();  
          
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
              
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }  
}
