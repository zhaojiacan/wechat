package com.ace.core.service;

import java.util.List;

import com.ace.core.bean.BaseDict;

public interface BaseDictService {

	//查询出sales_type相关项
	public List<BaseDict> getTypesByName(BaseDict baseDict);
}
