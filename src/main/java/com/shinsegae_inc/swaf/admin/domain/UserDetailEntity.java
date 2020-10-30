package com.shinsegae_inc.swaf.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.shinsegae_inc.swaf.common.domain.BaseEntity;

@Entity
@Table(name="tzf_user_d")
@IdClass(UserDetailId.class)
public class UserDetailEntity extends BaseEntity {

	@Id
	@Column
	private String userNo;
	
	@Id
    @Column
    private String userTp;

    @Column
    private String userLvl;

	public UserDetailEntity() {}

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserTp() {
        return userTp;
    }

    public void setUserTp(String userTp) {
        this.userTp = userTp;
    }

    public String getUserLvl() {
        return userLvl;
    }

    public void setUserLvl(String userLvl) {
        this.userLvl = userLvl;
    }
}
