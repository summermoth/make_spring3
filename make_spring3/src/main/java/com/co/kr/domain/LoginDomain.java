package com.co.kr.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder(builderMethodName = "builder")
public class LoginDomain {
	
	//mb_seq
	private Integer mbSeq;
	//mb_id
	private String mbId;
	//mb_pw
	private String mbPw;
	//mb_level
	private String mbLevel;
	//mb_ip
	private String mbIp;
	//mb_use
	private String mbUse;
	//mb_createAt
	private String mbCreateAt;
	//mb_updateat
	private String mbUpdateAt;

}
