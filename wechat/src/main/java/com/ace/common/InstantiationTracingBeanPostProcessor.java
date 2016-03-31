package com.ace.common;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.ace.core.wechat.common.InterfaceUrlInti;
import com.ace.core.wechat.common.WeChatTask;
import com.baidu.ueditor.util.QQiNiuUtil;

public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
      //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
        if(event.getApplicationContext().getParent() == null){
        	//root application context 没有parent，他就是老大.
            //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
        	System.out.println("InstantiationTracingBeanPostProcessor.onApplicationEvent()");
        	InterfaceUrlInti.init();
            WeChatTask timer = new WeChatTask();
            try {
				timer.getToken_getTicket();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
        	QQiNiuUtil.genUptoken();
        }
    }
}