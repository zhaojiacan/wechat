package com.ace.core.service;

import com.ace.core.bean.OpRecord;


public interface OpRecordService {
	//记录操作日志
	public int add(OpRecord opRecord);
}
