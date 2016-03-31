package com.ace.core.dispatcher;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ace.core.util.MessageUtil;
import com.ace.core.wechat.common.GetUseInfo;
import com.ace.core.wechat.message.response.Article;
import com.ace.core.wechat.message.response.NewsMessage;
import com.ace.core.wechat.message.response.TextMessage;

 
/**
 * ClassName: EventDispatcher
 * @Description: 事件消息业务分发器
 * @author dapengniao
 * @date 2016年3月7日 下午4:04:41
 */
public class EventDispatcher {
    public static String processEvent(Map<String, String> map) {
    	String openid = map.get("FromUserName"); // 用户openid
    	String mpid = map.get("ToUserName"); // 公众号原始ID
    	/*VoiceMessage voice=new VoiceMessage();
    	voice.setToUserName(openid);
    	voice.setFromUserName(mpid);
    	voice.setCreateTime(new Date().getTime());
    	voice.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_Voice);
    	ImageMessage imgmsg = new ImageMessage();
    	imgmsg.setToUserName(openid);
    	imgmsg.setFromUserName(mpid);
    	imgmsg.setCreateTime(new Date().getTime());
    	imgmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_Image);
    	if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { // 关注事件
    	    Voice voi = new Voice();
    	    HttpPostUploadUtil util=new HttpPostUploadUtil();
    	    String filepath="F:\\1.mp3";  
    	    Map<String, String> textMap = new HashMap<String, String>();  
    	    textMap.put("name", "testname");  
    	    Map<String, String> fileMap = new HashMap<String, String>();  
    	    fileMap.put("userfile", filepath); 
    	    String mediaidrs = util.formUpload(textMap, fileMap);
    	    System.out.println(mediaidrs);
    	    String mediaid=JSON.parseObject(mediaidrs).getString("media_id");
    	    voi.setMediaId(mediaid);
    	    voice.setVoice(voi);
    	    return MessageUtil.voiceMessageToXml(voice);
    	}*/
    	//对图文消息
        NewsMessage newmsg=new NewsMessage();
        newmsg.setToUserName(openid);
        newmsg.setFromUserName(mpid);
        newmsg.setCreateTime(new Date().getTime());
        newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { // 关注事件
            System.out.println("==============这是关注事件！");
            try {
                    HashMap<String, String> userinfo=GetUseInfo.Openid_userinfo(openid);
                    Article article=new Article();
                    article.setDescription("欢迎来到赵加灿的个人博客：菜鸟程序员成长之路！"); //图文消息的描述
                    article.setPicUrl(userinfo.get("headimgurl")); //图文消息图片地址
                    article.setTitle("尊敬的："+userinfo.get("nickname")+",你好！");  //图文消息标题
                    article.setUrl("http://www.cuiyongzhi.com");  //图文url链接
                    List<Article> list=new ArrayList<Article>();
                    list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里list中加入多个Article即可！
                    newmsg.setArticleCount(list.size());
                    newmsg.setArticles(list);
                    return MessageUtil.newsMessageToXml(newmsg);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("====代码有问题额☺！");
//                logger.error(e,e);
            }
         
        }
    	//普通文本消息
    	TextMessage txtmsg=new TextMessage();
    	txtmsg.setToUserName(openid);
    	txtmsg.setFromUserName(mpid);
    	txtmsg.setCreateTime(new Date().getTime());
    	txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { //取消关注事件
    	    txtmsg.setContent("你好，欢迎访问拓狐网络科技有限公司官方微信！<a href='tel:15288489568'>点击拨打电话</a>");
    	    return MessageUtil.textMessageToXml(txtmsg);
        }
         
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { //扫描二维码事件
            System.out.println("==============这是扫描二维码事件！");
        }
         
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { //位置上报事件
            System.out.println("==============这是位置上报事件！");
        }
         
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { //自定义菜单点击事件
            System.out.println("==============这是自定义菜单点击事件！");
        }
         
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { //自定义菜单View事件
            System.out.println("==============这是自定义菜单View事件！");
        }
         
         
        return null;
    }
}