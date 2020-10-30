package com.shinsegae_inc.sitims.common;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.shinsegae_inc.swaf.common.mapper.CommonOdmMapper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

//@Repository
@SuppressWarnings("rawtypes")
@SuppressFBWarnings(value="NP_UNWRITTEN_FIELD")
public class CmOdmDao {
//	@Resource(name="CommonOdmMapper")
    private transient CommonOdmMapper commonMapper;
	
	protected transient Log logger = LogFactory.getLog(this.getClass());
	
	protected void printSqlId(String queryId) {
		if (logger.isDebugEnabled()) {
			logger.debug("################ ODM SQL ID ################");
			logger.debug("# " + queryId);
			logger.debug("########################################");
		}
	}

	public int getCount(String queryId) {
		this.printSqlId(queryId);
		return (Integer)commonMapper.selectOne(queryId);
	}
	
	public int getCount(String queryId, CmMap reqVo) {
		this.printSqlId(queryId);
		return (Integer)commonMapper.selectOne(queryId, reqVo);
	}
	
	public CmMap getObject(String queryId) {
		this.printSqlId(queryId);
		return (CmMap)commonMapper.selectOne(queryId);
	}
	
	public CmMap getObject(String queryId, CmMap reqVo) {
		this.printSqlId(queryId);
		return (CmMap)commonMapper.selectOne(queryId, reqVo);
	}
	
	public List<CmMap> getList (String queryId, CmMap reqVo){
		this.printSqlId(queryId);
		return commonMapper.selectList(queryId, reqVo);
	}

	public List<Object> getObjectList (String queryId, CmMap reqVo){
		this.printSqlId(queryId);
		return commonMapper.selectList(queryId, reqVo);
	}
	
	public List<CmMap> getPagingList (String queryId, CmMap reqVo){
		this.printSqlId(queryId);
		RowBounds rowBounds = new RowBounds(reqVo.getInt("i_iSkipCnt"), reqVo.getInt("i_iPageSize"));
		SqlSession	sqlSession	= commonMapper.getSqlSession();
		return sqlSession.selectList(queryId, reqVo, rowBounds);
	}
	
	public Object insert(String queryId, CmMap reqVo) {
		this.printSqlId(queryId);
		return commonMapper.insert(queryId, reqVo);
	}

	public int update(String queryId, CmMap reqVo) {
		this.printSqlId(queryId);
		return commonMapper.update(queryId, reqVo);
	}
	
	public int delete(String queryId, CmMap reqVo) {
		this.printSqlId(queryId);
		return commonMapper.delete(queryId, reqVo);
	}
	
	public String getString(String queryId) {
		this.printSqlId(queryId);
		return (String)commonMapper.selectOne(queryId);
	}
	
	public String getString(String queryId, CmMap reqVo) {
		this.printSqlId(queryId);
		return (String)commonMapper.selectOne(queryId, reqVo);
	}
	
	public List<CmMap> getList(String queryId) {
		this.printSqlId(queryId);
		return commonMapper.selectList(queryId);
	}
	
	public Map<String, CmMap> getMap (String queryId, CmMap reqVo, String key){
		this.printSqlId(queryId);
		SqlSession	sqlSession	= commonMapper.getSqlSession();
		return sqlSession.selectMap(queryId, reqVo, key);
	}

}
