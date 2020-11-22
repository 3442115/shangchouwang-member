package com.atguigu.crowd.service.serviceIml;

import com.atguigu.crowd.mapper.AddressPOMapper;
import com.atguigu.crowd.mapper.OrderPOMapper;
import com.atguigu.crowd.mapper.OrderProjectPOMapper;
import com.atguigu.crowd.po.AddressPO;
import com.atguigu.crowd.po.AddressPOExample;
import com.atguigu.crowd.po.OrderPO;
import com.atguigu.crowd.po.OrderProjectPO;
import com.atguigu.crowd.service.OrderProjectService;
import com.atguigu.crowd.vo.AddressVO;
import com.atguigu.crowd.vo.OrderProjectVO;
import com.atguigu.crowd.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderProjectServiceIml implements OrderProjectService {
    @Resource
    private OrderProjectPOMapper orderProjectPOMapper;
    @Resource
    private OrderPOMapper orderPOMapper;
    @Resource
    private AddressPOMapper addressPOMapper;


    @Override
    public OrderProjectVO getOrderProject(Integer projectId, Integer returnID) {
        try {
            OrderProjectVO orderProjectVO = orderProjectPOMapper.selectOrderProject(returnID);
            return  orderProjectVO;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<AddressVO> getAddress(Integer id) {
        AddressPOExample addressPOExample = new AddressPOExample();
        addressPOExample.createCriteria().andMemberIdEqualTo(id);
        List<AddressPO> addressPOS = addressPOMapper.selectByExample(addressPOExample);

        List<AddressVO> addressVOS=new ArrayList<>();

        for (AddressPO addressPO : addressPOS) {
            AddressVO addressVO = new AddressVO();
            System.out.println("地址对象："+addressPO.toString());
            BeanUtils.copyProperties(addressPO,addressVO);
            addressVOS.add(addressVO);
        }
        return addressVOS;
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor =Exception.class )
    public void insertAddress(AddressVO addressVO) {
        AddressPO addressPO = new AddressPO();
        BeanUtils.copyProperties(addressVO,addressPO);
        addressPOMapper.insert(addressPO);
    }
    // 保存对象到数据库
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor =Exception.class )
    public void save(OrderVO orderVO) {
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO,orderPO);
        OrderProjectPO orderProjectPO = new OrderProjectPO();
        BeanUtils.copyProperties(orderVO.getOrderProjectVO(),orderProjectPO);
        orderPOMapper.insert(orderPO);
       // 需要保存 orderPO自增生成的OrderId，
        // 需要到对应的xml文件进行设置
        Integer id = orderPO.getId();
        orderProjectPO.setOrderId(id);
        orderProjectPOMapper.insert(orderProjectPO);

    }


}
