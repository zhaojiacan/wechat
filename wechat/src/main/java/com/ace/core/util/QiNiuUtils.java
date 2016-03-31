package com.ace.core.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.baidu.ueditor.util.QQiNiuUtil;

public class QiNiuUtils extends QQiNiuUtil {

	
	public static void genUptoken(){
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(new Runnable() {
			public void run() {
				long start = System.currentTimeMillis(); 
				uptoken = getUptoken();
				long end = System.currentTimeMillis(); 
				System.out.println("QiNiuUtils.genUptoken()"+ak+(end-start)+"秒");
			}
		}, 0, 3000, TimeUnit.SECONDS);	
	}
}
