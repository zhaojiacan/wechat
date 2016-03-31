package com.ace.core.controller.wechat;
 
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ace.core.dispatcher.EventDispatcher;
import com.ace.core.dispatcher.MsgDispatcher;
import com.ace.core.util.MessageUtil;
import com.ace.core.util.ResponseUtils;
import com.ace.core.util.SignUtil;
import com.ace.core.wechat.menu.MenuMain;
import com.alibaba.fastjson.JSONObject;

 
@Controller
@RequestMapping("/wechat")
public class WechatSecurity {
    private static Logger logger = Logger.getLogger(WechatSecurity.class);
 
    /**
     * 
     * @Description: 用于接收get参数，返回验证参数
     * @param @param request
     * @param @param response
     * @param @param signature
     * @param @param timestamp
     * @param @param nonce
     * @param @param echostr
     * @author dapengniao
     * @date 2016年3月4日 下午6:20:00
     */
    @RequestMapping(value = "security.do", method = RequestMethod.GET)
    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "signature", required = true) String signature,
            @RequestParam(value = "timestamp", required = true) String timestamp,
            @RequestParam(value = "nonce", required = true) String nonce,
            @RequestParam(value = "echostr", required = true) String echostr) {
        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
            } else {
                logger.info("这里存在非法请求！");
            }
        } catch (Exception e) {
            logger.error(e, e);
        }
    }
    /**
     * @Description: 接收微信端消息处理并做分发
     * @param @param request
     * @param @param response   
     * @author dapengniao
     * @date 2016年3月7日 下午4:06:47
     */
    @RequestMapping(value = "security.do", method = RequestMethod.POST)
    public void DoPost(HttpServletRequest request,HttpServletResponse response) {
        try{
            Map<String, String> map=MessageUtil.parseXml(request);
            String msgtype=map.get("MsgType");
            if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)){
                ResponseUtils.renderXml(response, EventDispatcher.processEvent(map)); //进入事件处理
            }else{
                ResponseUtils.renderXml(response, MsgDispatcher.processMessage(map)); //进入消息处理
            }
        }catch(Exception e){
            logger.error(e,e);
        }
    }
    
    /**
     * 
     * @Title: creatMenu
     * @Description: TODO
     * @param @param response
     * @return void
     * @autor francis.zhaojiacan@gmail.com
     * @date 2016年3月31日 下午12:05:15
     * @throws
     */
    @RequestMapping(value= "createMenu.do", method =RequestMethod.GET )
    public void creatMenu(HttpServletResponse response){
    	String createMenu = MenuMain.createMenu();
    	JSONObject jo=new JSONObject();
    	if(StringUtils.isNotBlank(createMenu)){
    		jo.put("status", 200);
    	}else{
    		jo.put("status", 300);
    		
    	}
    	ResponseUtils.renderJson(response, jo.toString());
    }
   
}