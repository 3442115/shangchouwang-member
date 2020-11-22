package com.atguigu.crowd;

import com.atguigu.crowd.po.MemberPO;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("atguigu-crowd-mysql")
public interface MYSQLremoteservice {

    @RequestMapping("/get/memberpo/by/loginacct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveMemberRemote(@RequestBody  MemberPO memberPO);

    @RequestMapping("/save/project/vo/remote")       // 这里的注解必须加
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO,@RequestParam("id") Integer id);

    @RequestMapping("/project/get/portal/type/project/remote")
    public ResultEntity<List<PortalTyprVO>> getPortalTypeProjectRemote();

    @RequestMapping("/project/get/detail/project/remote/{id}")
    public ResultEntity<DetailProjectVO> getDetailProjectRemote(@PathVariable("id") Integer id);

    @RequestMapping("/get/order/project/remote")
    public ResultEntity<OrderProjectVO> getOrderProjectRemote(@RequestParam("projectId") Integer projectId,@RequestParam("returnID") Integer returnID);

    @RequestMapping("/get/adddress/remote")
    ResultEntity<List<AddressVO>> getAdddressRemote(@RequestParam("id") Integer id);

    @RequestMapping("/insert/new/address/remote")
    ResultEntity<String> insertNewAddressRemote(@RequestBody AddressVO addressVO);

    @RequestMapping("/save/order/vo")
    ResultEntity<String> saveOrderVO(@RequestBody OrderVO orderVO);
}
