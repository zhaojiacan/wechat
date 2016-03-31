package com.ace.core.wechat.common;
 
import java.util.HashMap;

import com.ace.core.util.GlobalConstants;
import com.ace.core.util.HttpUtils;
import com.alibaba.fastjson.JSONObject;

/**
 * ClassName: GetUseInfo
 * @Description: 获取微信用户信息
 * @author dapengniao
 * @date 2016年3月18日 下午2:00:52
 */
public class GetUseInfo {
    /**
     * @Description: 通过openid获取用户微信信息
     * @param @param openid
     * @param @return
     * @param @throws Exception   
     * @author dapengniao
     * @date 2016年3月18日 下午2:01:30
     */
    public static HashMap<String, String> Openid_userinfo(String openid)
            throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token",
                GlobalConstants.getInterfaceUrl("access_token"));  //定时器中获取到的token
        params.put("openid", openid);  //需要获取的用户的openid
        params.put("lang", "zh_CN");
        String subscribers = HttpUtils.sendGet(
                GlobalConstants.getInterfaceUrl("OpenidUserinfoUrl"), params);
        System.out.println(subscribers);
        params.clear();
        //这里返回参数只取了昵称、头像、和性别
        params.put("nickname",
                JSONObject.parseObject(subscribers).getString("nickname")); //昵称
        params.put("headimgurl",
                JSONObject.parseObject(subscribers).getString("headimgurl"));  //图像
        params.put("sex", JSONObject.parseObject(subscribers).getString("sex"));  //性别
        return params;
    }
 
}