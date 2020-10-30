package com.shinsegae_inc.swaf.common.mapper;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.shinsegae_inc.core.mapper.SwafAbstractMapper;


/**
 * @Package Name : com.shinsegae_inc.swaf.common.mapper
 * @FileName     : CommonIfSivanMapper.java
 * @date         : 2016. 7. 22. 
 * @author       : wjLee7
 * 설명 : if_sivan으로 사용되는 데이터 접근 클래스
 * 변경이력 : 
 */

//@Repository("CommonIfSivanMapper")
public class CommonIfSivanMapper extends SwafAbstractMapper {
	@Resource(name = "ifSivanSqlSessionFactory")
	public void setSuperSqlMapClient(SqlSessionFactory sqlSession) {
		super.setSqlSessionFactory(sqlSession);
	}
}
