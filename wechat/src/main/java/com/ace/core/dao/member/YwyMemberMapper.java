package com.ace.core.dao.member;

import com.ace.core.bean.member.YwyMember;
import com.ace.core.bean.member.YwyMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YwyMemberMapper {
    int countByExample(YwyMemberExample example);

    int deleteByExample(YwyMemberExample example);

    int deleteByPrimaryKey(String id);

    int insert(YwyMember record);

    int insertSelective(YwyMember record);

    List<YwyMember> selectByExample(YwyMemberExample example);

    YwyMember selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") YwyMember record, @Param("example") YwyMemberExample example);

    int updateByExample(@Param("record") YwyMember record, @Param("example") YwyMemberExample example);

    int updateByPrimaryKeySelective(YwyMember record);

    int updateByPrimaryKey(YwyMember record);
}