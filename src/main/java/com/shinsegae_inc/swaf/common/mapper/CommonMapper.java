package com.shinsegae_inc.swaf.common.mapper;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.shinsegae_inc.core.mapper.SwafAbstractMapper;


/**
 * @Package Name : com.shinsegae_inc.swaf.common.mapper
 * @FileName     : CommonMapper.java
 * @date         : 2016. 7. 22. 
 * @author       : wjLee7
 * 설명 : 공용으로 사용되는 데이터 접근 클래스
 * 변경이력 : 
 */

@Repository("CommonMapper")
public class CommonMapper extends SwafAbstractMapper {

	@Resource(name = "commonSqlSessionFactory")
	public void setSuperSqlMapClient(SqlSessionFactory sqlSession) {
		super.setSqlSessionFactory(sqlSession);
	}
}
