package com.shinsegae_inc.swaf.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shinsegae_inc.swaf.admin.domain.UserDetailEntity;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {
    UserDetailEntity findByUserNo(String userNo);
}