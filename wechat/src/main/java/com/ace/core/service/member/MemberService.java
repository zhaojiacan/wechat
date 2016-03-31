package com.ace.core.service.member;

import com.ace.core.bean.member.YwyMember;

public interface MemberService {

	//插入一条会员数据
	public YwyMember addMember(YwyMember member);
	//删除会员信息
	public Integer delMemberById(String id);
}
