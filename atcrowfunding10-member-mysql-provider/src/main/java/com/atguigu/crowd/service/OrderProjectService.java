package com.atguigu.crowd.service;

import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.AddressVO;
import com.atguigu.crowd.vo.OrderProjectVO;
import com.atguigu.crowd.vo.OrderVO;

import java.util.List;

public interface OrderProjectService {

    OrderProjectVO getOrderProject(Integer projectId, Integer returnID);


    List<AddressVO> getAddress(Integer id);

    void insertAddress(AddressVO addressVO);

    void save(OrderVO orderVO);
}
