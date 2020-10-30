package com.shinsegae_inc.swaf.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;

public class UserDetailId implements Serializable {

	@Column
	private String userNo;
	
	@Column
	private String userTp;
	
}
