package com.atguigu.crowd.mapper;


import com.atguigu.crowd.po.ProjectPO;
import com.atguigu.crowd.po.ProjectPOExample;
import com.atguigu.crowd.vo.DetailProjectVO;
import com.atguigu.crowd.vo.PortalTyprVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectPOMapper {
    long countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);
    // 根据 主键进行删除
    int deleteByPrimaryKey(Integer id);
    // 插入保存对象
    int insert(ProjectPO record);
     // 有选择地保存
    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);
    // 根据id进行查询
    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);
     // 根据主键进行有选择地更新
    int updateByPrimaryKeySelective(ProjectPO record);
    // 根据主键进行更新
    int updateByPrimaryKey(ProjectPO record);

    void insertSelect(@Param("typeIdList") List<Integer> typeIdList,@Param("projectPOId") Integer projectPOId);

    void insertSelectTag(@Param("tagIdList") List<Integer> tagIdList, @Param("projectPOId") Integer projectPOId);

    List<PortalTyprVO> selectPortalTypeVOList();

    DetailProjectVO selectDetailProjectVO(Integer id);
}