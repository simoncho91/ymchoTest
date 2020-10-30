package com.shinsegae_inc.swaf.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.shinsegae_inc.core.util.SessionUtils;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(updatable=false)
    private String regUserNo;
    
    @Column(updatable=false)
    private Date regDtm;
    
    @Column(insertable=false)
    private String modUserNo;
    
    @Column(insertable=false)
    private Date modDtm;

	@PrePersist
	public void beforeCreate() {
	    if (regUserNo == null) regUserNo = SessionUtils.getUserNo();
		regDtm = new Date();
	}
	
	@PreUpdate
	public void beforeUpdate() {
	    if (modUserNo == null) modUserNo = SessionUtils.getUserNo();
		modDtm = new Date();
	}

    public String getRegUserNo() {
        return regUserNo;
    }

    public void setRegUserNo(String regUserNo) {
        this.regUserNo = regUserNo;
    }

    public Date getRegDtm() {
        return regDtm;
    }

    public void setRegDtm(Date regDtm) {
        this.regDtm = regDtm;
    }

    public String getModUserNo() {
        return modUserNo;
    }

    public void setModUserNo(String modUserNo) {
        this.modUserNo = modUserNo;
    }

    public Date getModDtm() {
        return modDtm;
    }

    public void setModDtm(Date modDtm) {
        this.modDtm = modDtm;
    }
}
