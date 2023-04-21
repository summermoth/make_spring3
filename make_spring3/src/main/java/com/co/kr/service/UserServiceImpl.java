package com.co.kr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.kr.domain.LoginDomain;
import com.co.kr.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	//mbSelectList	
	@Override
	public LoginDomain mbSelectList(Map<String, String> map) {
		// TODO Auto-generated method stub
		return userMapper.mbSelectList(map);
	}
	
	//mbAllList
	@Override
	public List<LoginDomain> mbAlList(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return userMapper.mbAllList(map);
	}
	
	//mbCreate
	@Override
	public void mbCreate(LoginDomain loginDomain) {
		// TODO Auto-generated method stub
		userMapper.mbCreate(loginDomain);		
	}
	
	//mbGetAll	
	@Override
	public int mbGetAll() {
		// TODO Auto-generated method stub
		return userMapper.mbGetAll();
	}

	//mbDulicationCheck
	@Override
	public int mbDuplicationCheck(Map<String, String> map) {
		// TODO Auto-generated method stub
		return userMapper.mbDuplicationCheck(map);
	}

	//mbGetId
	@Override
	public LoginDomain mbGetId(Map<String, String> map) {
		// TODO Auto-generated method stub
		return userMapper.mbGetId(map);
	}

	//mbUpdate
	@Override
	public void mbUpdate(LoginDomain loginDomain) {
		// TODO Auto-generated method stub
		userMapper.mbUpdate(loginDomain);
		
	}

	//mbRemove
	@Override
	public void mbRemove(Map<String, String> map) {
		// TODO Auto-generated method stub
		userMapper.mbRemove(map);
	}

}
