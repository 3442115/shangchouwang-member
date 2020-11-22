package com.atguigu.crowd.controller;

import com.atguigu.crowd.service.OrderProjectService;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.AddressVO;
import com.atguigu.crowd.vo.OrderProjectVO;
import com.atguigu.crowd.vo.OrderVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class OrderProjectProviderController {
    @Resource
    private OrderProjectService orderProjectService;


    // 进行结算时保存orderVO对象
    @RequestMapping("/save/order/vo")
    ResultEntity<String> saveOrderVO(@RequestBody OrderVO orderVO){

        try {
            orderProjectService.save(orderVO);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            return ResultEntity.failed(e.getMessage());
        }
    }


    @RequestMapping("/get/order/project/remote")
    public ResultEntity<OrderProjectVO> getOrderProjectRemote(@RequestParam("projectId") Integer projectId,
                                                              @RequestParam("returnID") Integer returnID){
         try {
             OrderProjectVO  orderProject =orderProjectService.getOrderProject(projectId,returnID);
             return ResultEntity.successWithData(orderProject);
         }catch (Exception e){
             return ResultEntity.failed(e.getMessage());
         }
    }

    @RequestMapping("/get/adddress/remote")
    ResultEntity<List<AddressVO>> getAdddressRemote(@RequestParam("id") Integer id){
       try {
           List<AddressVO> address = orderProjectService.getAddress(id);
           return ResultEntity.successWithData(address);
       }catch (Exception e){
           return ResultEntity.failed(e.getMessage());
       }
    }

    // 插入新的地址信息
    @RequestMapping("/insert/new/address/remote")
    ResultEntity<String> insertNewAddressRemote(@RequestBody AddressVO addressVO){
        orderProjectService.insertAddress(addressVO);

        return ResultEntity.successWithoutData();
    }
}
