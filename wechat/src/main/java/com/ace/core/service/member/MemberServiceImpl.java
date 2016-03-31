package com.ace.core.service.member;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.core.bean.member.YwyMember;
import com.ace.core.dao.member.YwyMemberMapper;
import com.ace.core.util.StringUtil;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private YwyMemberMapper ywyMemberMapper;
	
	@Override
	public YwyMember addMember(YwyMember member) {
		member.setId(StringUtil.getUUID());
		member.setCreateTime(new Date());
		int insertSelective = ywyMemberMapper.insertSelective(member);
		if (insertSelective>0) {
			return member;
		}
		return null;
	}

	@Override
	public Integer delMemberById(String id) {
		 
		return ywyMemberMapper.deleteByPrimaryKey(id);
	}

}
