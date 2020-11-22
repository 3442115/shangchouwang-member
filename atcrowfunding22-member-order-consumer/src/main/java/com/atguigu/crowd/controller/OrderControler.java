package com.atguigu.crowd.controller;

import com.atguigu.crowd.MYSQLremoteservice;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.AddressVO;
import com.atguigu.crowd.vo.MemberLoginVO;
import com.atguigu.crowd.vo.OrderProjectVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderControler {

    @Resource
    private MYSQLremoteservice mysqLremoteservice;
    // 跳转到结算界面
    @RequestMapping("/confirm/return/info/{projectID}/{returnID}")
    public String showConfirm(@PathVariable("projectID") Integer projectId,
                              @PathVariable("returnID") Integer returnId,
                              HttpSession session){
        ResultEntity<OrderProjectVO> resultEntity=
                mysqLremoteservice.getOrderProjectRemote(projectId,returnId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            OrderProjectVO orderProjectVO = resultEntity.getData();
            session.setAttribute("orderProjectVO",orderProjectVO);
            System.out.println(orderProjectVO);
        }

        return "confirm_return";
    }

   // 跳转到 订单 确认界面
    @RequestMapping("confirm/order/{returnCount}")
    public String confirmOrder(@PathVariable("returnCount") Integer returnCount,
                               HttpSession session
                              ){
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        orderProjectVO.setReturnCount(returnCount);
        session.setAttribute("orderProjectVO",orderProjectVO);
        // 查一下现有的 地址数据
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        ResultEntity<List<AddressVO>> resultEntity= mysqLremoteservice.getAdddressRemote(memberLoginVO.getId());


        if (resultEntity.getResult().equals(ResultEntity.SUCCESS)){
            List<AddressVO> data = resultEntity.getData();

            session.setAttribute("addressVO",data);

        }
        return "confirm_order";
    }
    // 添加新地址时的方法
    @RequestMapping("/save")
    public String saveAdress( AddressVO addressVO,HttpSession session){
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        mysqLremoteservice.insertNewAddressRemote(addressVO);
        Integer returnCount = orderProjectVO.getReturnCount();
        return "redirect:http://www.abc.com/order/confirm/order/"+returnCount;
    }


}
