package com.atguigu.crowd.controller;

import com.atguigu.crowd.MYSQLremoteservice;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.PortalTyprVO;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class portalController {

    @Resource
    private MYSQLremoteservice mysqLremoteservice;
    @RequestMapping("/")
    public String showPortalPage(Model model){
       // 调用这个微服务从数据库中取出来信息
        ResultEntity<List<PortalTyprVO>> resultEntity = mysqLremoteservice.getPortalTypeProjectRemote();
        if (resultEntity.getResult().equals(ResultEntity.SUCCESS)){
            List<PortalTyprVO> data = resultEntity.getData();

            model.addAttribute(CrowdConstant.ATTR_NAME_TPORTAL_DATA,data);
        }
        return "portal";
    }

}
