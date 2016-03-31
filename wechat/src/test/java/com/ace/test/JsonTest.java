package com.ace.test;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonTest {

	@Test
	public void getJsonMap() {
		// 一个JsonObject文本数据
		String s = "{\"token\":\"48e1a118a99e59fb09254e42a0335bc8\",\"data\":\"{\\\"cid\\\":\\\"4DD0B651-6445-438C-B7F2-CD11641370FC\\\",\\\"companyName\\\":\\\"云南绿城物业有限公司\\\",\\\"companyShortName\\\":\\\"绿城物业\\\",\\\"createTime\\\":1458884751000,\\\"cuid\\\":\\\"2FA9B8B1-3D95-4AFE-AA2C-23AECB458846\\\",\\\"houses\\\":100000,\\\"id\\\":\\\"C9BFDD35-8B34-44E1-B22A-31A54847056B\\\",\\\"licenseNo\\\":\\\"01234567890123\\\",\\\"pageNo\\\":1,\\\"pageSize\\\":10,\\\"propertyType\\\":\\\"住宅\\\",\\\"serverPhone\\\":\\\"15288489568\\\",\\\"trialName\\\":\\\"赵加灿\\\",\\\"trialPhone\\\":\\\"15288489568\\\",\\\"trialStatus\\\":\\\"未审核\\\",\\\"uid\\\":\\\"admin\\\",\\\"updateTime\\\":1458895209000}\"}";
		// 将JsonObject数据转换为Json
		JSONObject object = JSON.parseObject(s);
		// 利用键值对的方式获取到值
		System.out.println(object.get("token"));
		System.out.println(object.get("data"));
		JSONObject trial = JSON.parseObject((String) object.get("data"));
		System.out.println("JsonTest.getJsonMap()"+trial.get("companyName"));
		/**
		 * 打印： liuzhao
		 */
	}
}
