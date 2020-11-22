package com.atguigu.crowd.service.serviceIml;

import java.util.List;

import com.atguigu.crowd.po.MemberPO;
import com.atguigu.crowd.po.MemberPOExample;
import com.atguigu.crowd.service.MemberService;
import org.springframework.stereotype.Service;
import com.atguigu.crowd.mapper.MemberPOMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


// 在类上使用@Transactional(readOnly = true)针对查询操作设置事务属性

@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

	@Resource
	private MemberPOMapper memberPOMapper;


	@Override
	public MemberPO getMemberPOByLoginAcct(String loginacct) {

		// 1.创建Example对象
		MemberPOExample example = new MemberPOExample();

		// 2.创建Criteria对象
		MemberPOExample.Criteria criteria = example.createCriteria();

		// 3.封装查询条件
		criteria.andLoginacctEqualTo(loginacct);

		// 4.执行查询
		List<MemberPO> list = memberPOMapper.selectByExample(example);
       if (list == null || list.size()==0){
       	return null;
	   }
		// 5.获取结果
		return list.get(0);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class,readOnly = false)
	@Override
	public void save(MemberPO memberPO) {
		// 有值的可以保存到数据库里面 没有的设置为null
		 memberPOMapper.insertSelective(memberPO);
	}

}
