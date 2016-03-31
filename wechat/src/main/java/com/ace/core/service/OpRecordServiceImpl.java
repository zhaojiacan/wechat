package com.ace.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ace.core.bean.OpRecord;
import com.ace.core.dao.OpRecordMapper;

@Service
@Transactional
public class OpRecordServiceImpl implements OpRecordService {

	@Autowired
	private OpRecordMapper opRecordMapper;
	
	@Override
	public int add(OpRecord opRecord) {
		int count = opRecordMapper.insertSelective(opRecord);
		return count;
	}

}
