package com.atguigu.crowd.controller;

import com.atguigu.crowd.MYSQLremoteservice;
import com.atguigu.crowd.RedisRemoteService;
import com.atguigu.crowd.config.ShowMessageConfigProperties;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.po.MemberPO;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.MemberLoginVO;
import com.atguigu.crowd.vo.MemberVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberController {
   @Resource
   private MYSQLremoteservice mysqLremoteservice;
   @Resource
   private ShowMessageConfigProperties showMessageConfigProperties;
   @Resource
   private RedisRemoteService redisRemoteService;

   @RequestMapping("/auth/do/member/layout")
   public String doLagout(HttpSession session){
   session.invalidate();
   return "redirect:/auth/to/member/lagout";
   }

   // 进行验证登录
    @RequestMapping("/auth/do/member/login")
    public String doMemberLogin(@RequestParam("loginacct") String loginacct,
                                @RequestParam("userpswd") String userpswd,
                                ModelMap modelMap,
                                HttpSession session){

        ResultEntity<MemberPO> resultEntity = mysqLremoteservice.getMemberPOByLoginAcctRemote(loginacct);
        if (ResultEntity.FAILED.equals(resultEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.ATTR_NAME_SYSTEM_ERROR);
            return "member-login";
        }
        MemberPO data = resultEntity.getData();
        if(data == null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        String userDatbase=data.getUserpswd();


        boolean matches = userDatbase.equals(userpswd);



        if (!matches){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);

            return "member-login";
        }
        MemberLoginVO memberLoginVO = new MemberLoginVO(data.getId(),data.getUsername(),data.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER,memberLoginVO);
        return "redirect:http://www.abc.com/auth/member/to/center/page";
    }


   // 进行验证盐值注册
   @RequestMapping("/auth/do/member/register")
   public String doMemberRegister(MemberVO memberVO,ModelMap modelMap){
       // 1从redis中获取验证码进行比对
       ResultEntity<String> rediscode =
               redisRemoteService.getRedisStringValueByKeyRemote(CrowdConstant.ATTR_REDIS_CODE_PREFIX);

        if(ResultEntity.FAILED.equals(rediscode.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.ATTR_NAME_SYSTEM_ERROR);
            return "member-reg";
        }
        String formCode=memberVO.getCode();

        if(formCode == null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.ATTR_NAME_NOT_NULL);
            return "member-reg";
        }
        if(!Objects.equals(formCode,rediscode.getData())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.ATTR_NAME_NOT_EQUAL);
            return "member-reg";
        }

       // 3 密码加密
       BCryptPasswordEncoder bcryp = new BCryptPasswordEncoder();
       memberVO.setUserpswd(bcryp.encode(memberVO.getUserpswd()));
       // 4 转换为PO
       MemberPO memberPO = new MemberPO();
       // 对象复制
       BeanUtils.copyProperties(memberVO,memberPO);
       // 5存储
       ResultEntity<String> resultEntity = mysqLremoteservice.saveMemberRemote(memberPO);
       if(ResultEntity.FAILED.equals(resultEntity.getResult())){
           modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.ATTR_NAME_SYSTEM_ERROR);
           return "member-reg";
       }
       // 2 验证成之后 删除验证码
       redisRemoteService.removeRedisKeyRemote(CrowdConstant.ATTR_REDIS_CODE_PREFIX);
       // 使用重定向 避免刷新页面 又重新注册
       return "redirect:/auth/member/to/login/page";
   }



   // 将验证吗存入到 redis
    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum")String phoneNum){
        // 是为了获取验证码
        ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendCodeByShortMessage(
                showMessageConfigProperties.getHost(),
                showMessageConfigProperties.getPath(),
                showMessageConfigProperties.getMethod(),
                phoneNum,
                showMessageConfigProperties.getAppcode(),
                showMessageConfigProperties.getSkin()
        );
        // 判断验证码生成是否成功
        if(ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())){
            // 存入到redis里的键
            String key= CrowdConstant.ATTR_REDIS_CODE_PREFIX;
            // 将验证码存入到redis
            String value=sendMessageResultEntity.getData();
            // 将验证码存入redis是指过期时间为300秒
            ResultEntity<String> resultEntity =
                    redisRemoteService.setRedisKeyValueRemoteWithTimeOut(key, value, 3000, TimeUnit.SECONDS);
           // 判断是否存入成功 成功则返回，不成功则将存入失败信息返回
            if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
                 return  ResultEntity.successWithoutData();
            }
            return resultEntity;
        }else{
          return sendMessageResultEntity;
        }
    }
}
