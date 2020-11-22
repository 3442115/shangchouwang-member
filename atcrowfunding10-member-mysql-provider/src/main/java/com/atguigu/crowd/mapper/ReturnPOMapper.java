package com.atguigu.crowd.mapper;


import com.atguigu.crowd.po.ReturnPO;
import com.atguigu.crowd.po.ReturnPOExample;
import com.atguigu.crowd.vo.ReturnVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnPOMapper {
    long countByExample(ReturnPOExample example);

    int deleteByExample(ReturnPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReturnPO record);

    int insertSelective(ReturnPO record);

    List<ReturnPO> selectByExample(ReturnPOExample example);

    ReturnPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReturnPO record, @Param("example") ReturnPOExample example);

    int updateByExample(@Param("record") ReturnPO record, @Param("example") ReturnPOExample example);

    int updateByPrimaryKeySelective(ReturnPO record);

    int updateByPrimaryKey(ReturnPO record);

    void insertSelectReturnVO(@Param("projectPOId") Integer projectPOId, @Param("list") List<ReturnPO> list);
}