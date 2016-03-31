package com.ace.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ace.core.bean.BaseDict;
import com.ace.core.bean.BaseDictExample;
import com.ace.core.dao.BaseDictMapper;

@Service
@Transactional
public class BaseDictServiceImpl implements BaseDictService {
	@Autowired
	private BaseDictMapper baseDictMapper;
	
	@Transactional(readOnly=true)
	@Override
	public List<BaseDict> getTypesByName(BaseDict baseDict) {
		BaseDictExample baseDictExample=new BaseDictExample();
		baseDictExample.setOrderByClause("no");
		BaseDictExample.Criteria criteria=baseDictExample.createCriteria();
		criteria.andTypeEqualTo(baseDict.getType());
		return baseDictMapper.selectByExample(baseDictExample);
	}

}
