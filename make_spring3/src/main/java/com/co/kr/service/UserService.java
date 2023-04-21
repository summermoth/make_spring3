package com.co.kr.service;

import java.util.List;
import java.util.Map;

import com.co.kr.domain.LoginDomain;

public interface UserService {
	
	//mbSelectList
	public LoginDomain mbSelectList(Map<String, String> map);
	//mbAllList
	public List<LoginDomain>mbAlList(Map<String,Integer> map);
	//mbCreate
	public void mbCreate(LoginDomain loginDomain);
	//mbGetAll
	public int mbGetAll();
	//mbDulicationCheck
	public int mbDuplicationCheck(Map<String, String> map);
	//mbGetId
	public LoginDomain mbGetId(Map<String, String> map);
	//mbUpdate
	public void mbUpdate(LoginDomain loginDomain);
	//mbRemove
	public void mbRemove(Map<String, String> map);

}
