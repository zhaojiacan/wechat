package com.ace.core.bean.member;

import java.util.Date;

import com.ace.core.util.StringUtil;

public class YwyMember {
    private String id;

    private String serverName;

    private String serverPwd;

    private Integer serverCycle;

    private Date createTime;

    private Date updateTime;

    
    public YwyMember() {
		super();
		this.id = StringUtil.getUUID();
		this.serverName = StringUtil.getUUID(8);
		this.serverPwd ="123456";
		this.serverCycle =1;
		this.createTime = new Date();
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getServerPwd() {
        return serverPwd;
    }

    public void setServerPwd(String serverPwd) {
        this.serverPwd = serverPwd == null ? null : serverPwd.trim();
    }

    public Integer getServerCycle() {
        return serverCycle;
    }

    public void setServerCycle(Integer serverCycle) {
        this.serverCycle = serverCycle;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}