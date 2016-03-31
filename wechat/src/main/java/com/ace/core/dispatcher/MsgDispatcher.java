package com.ace.core.dispatcher;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ace.core.util.MessageUtil;
import com.ace.core.wechat.message.response.Article;
import com.ace.core.wechat.message.response.NewsMessage;
import com.ace.core.wechat.message.response.TextMessage;

 
/**
 * ClassName: MsgDispatcher
 * @Description: 消息业务处理分发器
 * @author dapengniao
 * @date 2016年3月7日 下午4:04:21
 */
public class MsgDispatcher {
    public static String processMessage(Map<String, String> map) {
    	String openid=map.get("FromUserName"); //用户openid
    	String mpid=map.get("ToUserName");   //公众号原始ID
    	         
    	//普通文本消息
    	TextMessage txtmsg=new TextMessage();
    	txtmsg.setToUserName(openid);
    	txtmsg.setFromUserName(mpid);
    	txtmsg.setCreateTime(new Date().getTime());
    	txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
    	
        
       //对图文消息
   	NewsMessage newmsg=new NewsMessage();
   	newmsg.setToUserName(openid);
   	newmsg.setFromUserName(mpid);
   	newmsg.setCreateTime(new Date().getTime());
   	newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
    	
    	
    	if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
    	    String content=map.get("Content");
    	    if("1".equals(content)){
    	        txtmsg.setContent("你好，你发送的内容是1！");
    	    }else if("2".equals(content)){
    	        txtmsg.setContent("你好，你发送的内容是2！");
    	    }else if("3".equals(content)){
    	        txtmsg.setContent("你好，你发送的内容是3！");
    	    }else if("4".equals(content)){
    	        txtmsg.setContent("<a href=\"http://www.cuiyongzhi.com\">崔用志博客</a>");
    	    }else{
    	    	 Article article=new Article();
    	    	    article.setDescription("欢迎来到昆明拓狐网络科技有限公司"); //图文消息的描述
    	    	    article.setPicUrl("http://7xreud.com1.z0.glb.clouddn.com/QQ%E5%9B%BE%E7%89%8720160303131430.jpg"); //图文消息图片地址
    	    	    article.setTitle("欢迎来到昆明拓狐网络科技有限公司");  //图文消息标题
    	    	    article.setUrl("http://www.baidu.com");  //图文url链接
    	    	    List<Article> list=new ArrayList<Article>();
    	    	    list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里list中加入多个Article即可！
    	    	    newmsg.setArticleCount(list.size());
    	    	    newmsg.setArticles(list);
    	    	    return MessageUtil.newsMessageToXml(newmsg);
    	    }
    	    return MessageUtil.textMessageToXml(txtmsg);
    	}
    	/*         
    	if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
    	    txtmsg.setContent("你好，欢迎访问拓狐网络科技有限公司官方微信！<a href='tel:15288489568'>点击拨打电话</a>");
    	    return MessageUtil.textMessageToXml(txtmsg);
    	}*/

    	         
    	if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
    	    System.out.println("==============这是图片消息！");
    	    Article article=new Article();
    	    article.setDescription("欢迎来到昆明拓狐网络科技有限公司"); //图文消息的描述
    	    article.setPicUrl("http://7xreud.com1.z0.glb.clouddn.com/QQ%E5%9B%BE%E7%89%8720160303131430.jpg"); //图文消息图片地址
    	    article.setTitle("欢迎来到昆明拓狐网络科技有限公司");  //图文消息标题
    	    article.setUrl("http://www.baidu.com");  //图文url链接
    	    List<Article> list=new ArrayList<Article>();
    	    list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里list中加入多个Article即可！
    	    newmsg.setArticleCount(list.size());
    	    newmsg.setArticles(list);
    	    return MessageUtil.newsMessageToXml(newmsg);
    	} 
         
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
            System.out.println("==============这是链接消息！");
        }
         
         
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) { // 位置消息
            System.out.println("==============这是位置消息！");
        }
         
         
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
            System.out.println("==============这是视频消息！");
        }
         
         
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
            System.out.println("==============这是语音消息！");
        }
 
        return null;
    }
}