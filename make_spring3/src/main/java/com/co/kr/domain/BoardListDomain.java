package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "builder")
public class BoardListDomain {
	
	//bd_seq
	private String bdSeq;
	//mb_id
	private String mbId;
	//bd_title
	private String bdTitle;
	//bd_content
	private String bdContent;
	//bd_createAt
	private String bdCreateAt;
	//bd_updateAt
	private String bdUpdateAt;

}
