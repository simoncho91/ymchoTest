package com.shinsegae_inc.sitims.mi.nb.nb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;

@Service(value="NbNb030Service")
@SuppressWarnings("rawtypes")
public class NbNb030Service extends CmService {

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 공지사항 목록 조회
	 * </pre>
	 */
	public List<CmMap> selectNbNb030List(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
    	int	recordCnt			= this.getNbNb030ListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("NbNb030Mapper.selectNbNb030List", reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 공지사항 목록 Count 조회
	 * </pre>
	 */
	public int getNbNb030ListCount(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		return cmDao.getCount("NbNb030Mapper.getNbNb030ListCount", reqVo);
	}
	
	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Commnets : 공지사항 상세조회 select
	 * </pre>
	 */
	public CmMap getNbNb030Info(CmMap reqVo) {
		return this.cmDao.getObject("NbNb030Mapper.getNbNb030Info", reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Commnets : 공지사항 데이터 저장 insert
	 * </pre>
	 * @throws Exception 
	 */
	public void insertNbNb030List(CmMap reqVo) throws Exception {
		 cmDao.insert("NbNb030Mapper.insertNbNb030List",reqVo);		
		 // 파일 업로드
		 insertFileList("NB",reqVo);
	}
	
	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Commnets : 공지사항 데이터 저장 update
	 * </pre>
	 * @throws Exception 
	 */
	public void updateNbNb030List(CmMap reqVo) throws Exception {
		 cmDao.update("NbNb030Mapper.updateNbNb030List",reqVo);
		 
		 reqVo.put("i_sRecordId", reqVo.getString("i_sRecordId"));
		 
		 // 파일 업로드
		 insertFileList("NB",reqVo);
	}
	
	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Commnets : 공지사항 데이터 저장 delete
	 * </pre>
	 */
	public void deleteNbNb030List(CmMap reqVo) {
		cmDao.update("NbNb030Mapper.deleteNbNb030List",reqVo);
	}
	
}
