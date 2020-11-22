package com.atguigu.crowd.service;

import com.atguigu.crowd.vo.DetailProjectVO;
import com.atguigu.crowd.vo.PortalTyprVO;
import com.atguigu.crowd.vo.ProjectVO;

import java.util.List;

public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer id);

    List<PortalTyprVO> selectPortalTypeVOList();

    DetailProjectVO selectDetailProjectVO(Integer id);

}
