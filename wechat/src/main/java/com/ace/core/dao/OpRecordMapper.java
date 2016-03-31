package com.ace.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ace.core.bean.OpRecord;
import com.ace.core.bean.OpRecordExample;

public interface OpRecordMapper {
    int countByExample(OpRecordExample example);

    int deleteByExample(OpRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(OpRecord record);

    int insertSelective(OpRecord record);

    List<OpRecord> selectByExample(OpRecordExample example);

    OpRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OpRecord record, @Param("example") OpRecordExample example);

    int updateByExample(@Param("record") OpRecord record, @Param("example") OpRecordExample example);

    int updateByPrimaryKeySelective(OpRecord record);

    int updateByPrimaryKey(OpRecord record);
}