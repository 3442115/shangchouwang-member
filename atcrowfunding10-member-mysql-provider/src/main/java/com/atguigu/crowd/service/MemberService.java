package com.atguigu.crowd.service;


import com.atguigu.crowd.po.MemberPO;

public interface MemberService {

	MemberPO getMemberPOByLoginAcct(String loginacct);

	void save(MemberPO memberPO);
}
