package com.atguigu.crowd.controller;

import com.atguigu.crowd.mapper.ProjectPOMapper;
import com.atguigu.crowd.service.ProjectService;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.*;
import org.junit.Test;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ProjectProviderController {
  @Resource
  private ProjectService projectService;



  @RequestMapping("/save/project/vo/remote")       // 这里的注解必须加
  ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("id") Integer id){

   try {
     projectService.saveProject(projectVO,id);
     return ResultEntity.successWithoutData();
   }catch (Exception e){
     e.printStackTrace();
     return ResultEntity.failed(e.getMessage());
   }
  }
    @Resource
    private ProjectPOMapper projectPOMapper;
    @RequestMapping("/sdaasda")
    public void  getConnection() throws SQLException {

        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(7);
        System.out.println(detailProjectVO.toString());
        List<DetailReturnVO> detailReturnVOList = detailProjectVO.getDetailReturnVOList();
        for (DetailReturnVO detailReturnVO : detailReturnVOList) {
            System.out.println(detailReturnVO.toString());
        }

    }

    @RequestMapping("/project/get/portal/type/project/remote")
    public ResultEntity<List<PortalTyprVO>> getPortalTypeProjectRemote(){
        try {
            List<PortalTyprVO> portalTyprVOS = projectService.selectPortalTypeVOList();

            return ResultEntity.successWithData(portalTyprVOS);
        }catch (Exception e){
            return ResultEntity.failed(e.getMessage());
        }
    }
   //
    @RequestMapping("/project/get/detail/project/remote/{id}")
    public ResultEntity<DetailProjectVO> getDetailProjectRemote(@PathVariable("id") Integer id) {
        try {
            DetailProjectVO detailProjectVO = projectService.selectDetailProjectVO(id);
            return ResultEntity.successWithData(detailProjectVO);
        }catch (Exception e){
            return ResultEntity.failed(e.getMessage());
        }
    }
}
