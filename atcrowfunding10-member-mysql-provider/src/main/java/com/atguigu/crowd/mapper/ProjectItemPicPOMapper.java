package com.atguigu.crowd.mapper;


import com.atguigu.crowd.po.ProjectItemPicPO;
import com.atguigu.crowd.po.ProjectItemPicPOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectItemPicPOMapper {
    long countByExample(ProjectItemPicPOExample example);

    int deleteByExample(ProjectItemPicPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectItemPicPO record);

    int insertSelective(ProjectItemPicPO record);

    List<ProjectItemPicPO> selectByExample(ProjectItemPicPOExample example);

    ProjectItemPicPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectItemPicPO record, @Param("example") ProjectItemPicPOExample example);

    int updateByExample(@Param("record") ProjectItemPicPO record, @Param("example") ProjectItemPicPOExample example);

    int updateByPrimaryKeySelective(ProjectItemPicPO record);

    int updateByPrimaryKey(ProjectItemPicPO record);

    void saveDetailPicturePath(@Param("projectPOId") Integer projectPOId,@Param("detailPictruePathList") List<String> detailPictruePathList);
}