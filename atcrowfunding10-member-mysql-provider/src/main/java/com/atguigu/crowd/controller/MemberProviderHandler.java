package com.atguigu.crowd.controller;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.po.MemberPO;
import com.atguigu.crowd.service.MemberService;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class MemberProviderHandler {

    @Autowired
    private MemberService memberService;


   // 根据账号来进行查询
    @RequestMapping("/get/memberpo/by/loginacct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct){

        MemberPO tom = memberService.getMemberPOByLoginAcct(loginacct);
        return ResultEntity.successWithData(tom);
    }


    // 进行远程保存服务
    @RequestMapping("/save/member/remote")        // consumer进行远程调用是需要添加
    public ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO){

      try {
          memberService.save(memberPO);
          return ResultEntity.successWithoutData();
      }catch (Exception e){
          if(e instanceof DuplicateKeyException){
              return  ResultEntity.failed(CrowdConstant.ATTR_NAME_ACCESS_FORBINED);
          }
          return ResultEntity.failed(e.getMessage());
         }

    }


}
