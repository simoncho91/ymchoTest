package com.shinsegae_inc.sitims.common.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.shinsegae_inc.core.exception.SwafException;
import com.shinsegae_inc.core.security.service.CryptoService;
import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.core.util.StringUtils;
import com.shinsegae_inc.sitims.common.CmDao;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.common.util.CmPathInfo;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
 
@SuppressWarnings("rawtypes")
public class CmService extends SwafService {
	
	protected transient Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	protected transient CmDao cmDao;

	@Autowired
	protected transient CommonService commonService;

	@Autowired
	private transient CryptoService cryptoService;
	public CmDao getCmDao() {
		return this.cmDao; 
	}
	
	public void setCmDao(CmDao cmDao) {
		this.cmDao = cmDao;
	}

//	@Autowired
//	protected transient CmOdmDao cmOdmDao;

//	public void setCmOdmDao(CmOdmDao cmOdmDao) {
//		this.cmOdmDao = cmOdmDao;
//	}

	@SuppressWarnings("resource")
	protected void convertPdfToImage(String tableNm, String recordId, String pdfPath, String attachId, String attachExt) throws IOException {
		//load a pdf from a byte buffer
        File file = new File(pdfPath+attachId+attachExt);
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdffile = new PDFFile(buf);
        
        for ( int i = 0 ; i < pdffile.getNumPages(); i++ ) {
        	// draw the first page to an image
            PDFPage page = pdffile.getPage(i+1);
            
            //get the width and height for the doc at the default zoom 
            Rectangle rect = new Rectangle(0,0,
                    (int)page.getBBox().getWidth(),
                    (int)page.getBBox().getHeight());
            
            //generate the image
            
            Image image = page.getImage(
                    rect.width, rect.height, //width & height
                    rect, // clip rect
                    null, // null for the ImageObserver
                    true, // fill background with white
                    true  // block until drawing is done
                    );
            
            int w = image.getWidth(null);
            int h = image.getHeight(null);
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bi.createGraphics();
            g2.drawImage(image, 0, 0, null);
            g2.dispose();
            try
            {
            	String filePath = CmPathInfo.getUPLOAD_PDF_IMAGE_PATH()+tableNm+"/"+recordId+"/"+attachId+"_"+i+".jpg";
            	
        		CmFunction.makeFilePath(filePath);
            	
                ImageIO.write(bi, "jpg",  new File(filePath));
                
                CmMap paramMap = new CmMap();
                paramMap.put("i_sTableNm", tableNm);
                paramMap.put("i_sImgPath", filePath);
                paramMap.put("i_sAttachId", attachId);
                paramMap.put("i_sRegUserId", CmFunction.getSessionStringValue("v_userid"));
                
                this.cmDao.insert("CmConvertPdfToImage.insertPdfImage", paramMap);
            }
            catch(IOException ioe)
            {
            	logger.error("write: " + ioe.getMessage());
            }
        }
	}

	protected void swfUploadProcess( CmMap reqVo, String uploadId) throws Exception {
		this.swfUploadProcess_bak(
				reqVo
				, uploadId
				, CmPathInfo.getUPLOAD_PATH() + CmFunction.getRegDate6() + "/"
				, "SSM_ATTACH");
	}
	
	protected void swfUploadProcess( CmMap reqVo, String uploadId, String movePath) throws Exception {
		this.swfUploadProcess_bak(
				reqVo
				, uploadId
				, movePath
				, "SSM_ATTACH");
	}
	
	/**
	 * 첨부파일
	 * @param uploadId
	 * @param movePath
	 * @param tableName
	 * @return
	 */
	protected void swfUploadProcess_bak( CmMap reqVo, String uploadId, String movePath, String tableName ) throws Exception {

		if (reqVo == null)
			return;
		
		CmMap		attMap	= null;
		
		String[]	arrAttachId		= null;
		String[]	arrAttachExt	= null;
		String[]	arrAttachNm		= null;
		String[]	arrAttachSize	= null;
		String[]	arrUploadId	= null;
		String		arrAttachPath	= CmPathInfo.getUPLOAD_FILE_TEMP_PATH() + CmFunction.getRegDate6() + "/";
		int			len				= 0;
		
		String tmpMovePath = movePath;
		String tmpTableName = tableName;
		
		if (tmpTableName == null || tmpTableName.equals("")) {
			tmpTableName	= "SSM_ATTACH";
		}
		
		if (tmpMovePath == null || tmpMovePath.equals("")) {
			tmpMovePath		= CmPathInfo.getUPLOAD_FILE_PATH() + CmFunction.getRegDate6() + "/";
		}
		
		arrAttachId		= reqVo.getStringArray(uploadId + "_del_attach_id");
		
		if (arrAttachId != null) {
			
			attMap 	= new CmMap();
			
			for (int i = 0; i < arrAttachId.length; i++) {
				attMap.put("table_name", tmpTableName);
				attMap.put("attach_id", arrAttachId[i]);
				attMap.put("i_sUpdateUserId", reqVo.get("i_sUpdateUserId"));
				
				this.cmDao.update("CmProcessDao.deleteAttach", attMap);
			}
		}
		
		arrAttachId		= reqVo.getStringArray(uploadId + "_attach_id");
		
		if (arrAttachId != null) {
			
			arrUploadId	    = reqVo.getStringArray(uploadId + "_upload_id");
			arrAttachExt	= reqVo.getStringArray(uploadId + "_attach_ext");
			arrAttachNm		= reqVo.getStringArray(uploadId + "_attach_nm");
			arrAttachSize	= reqVo.getStringArray(uploadId + "_attach_size");
			
			attMap 	= new CmMap();
			
			len		= arrAttachId.length;
			
			for (int i = 0; i < len; i++) {
				if (CmFunction.fileMove( arrAttachPath + arrAttachId[i] + arrAttachExt[i],  tmpMovePath + arrAttachId[i] + arrAttachExt[i])) {
					
					attMap.put("table_name", tmpTableName);
					attMap.put("attach_id", arrAttachId[i]);
					attMap.put("upload_id", arrUploadId[i]);
					attMap.put("attach_path", tmpMovePath);
					attMap.put("attach_ext", arrAttachExt[i]);
					attMap.put("attach_nm", arrAttachNm[i]);
					attMap.put("attach_size", arrAttachSize[i]);
					attMap.put("rec_type", reqVo.get("rec_type"));
					attMap.put("target_cd", reqVo.get("target_cd"));
					attMap.put("i_sRegUserId", reqVo.get("i_sRegUserId"));
					attMap.put("i_sUpdateUserId", reqVo.get("i_sUpdateUserId"));
					
					this.cmDao.insert("CmProcessDao.insertAttach", attMap);
				}
			}
		}
	}
	
	protected void deleteAttachAll(CmMap reqVo, String tableName) throws Exception {
		
		String	targetCd	= reqVo.getString("target_cd");
		String uploadId = reqVo.getStringArray("i_arrUploadId")[0];
		String tmpTableName = tableName;
		if (targetCd != null && !targetCd.equals("")) {
			
			CmMap		attMap	= new CmMap();
			
			if (tmpTableName == null || tmpTableName.equals("")) {
				tmpTableName	= "SSM_ATTACH";
			}
			
			attMap.put("table_name", tmpTableName);
			attMap.put("target_cd", targetCd);
			attMap.put("i_sUpdateUserId", reqVo.get("i_sUpdateUserId"));
			attMap.put("upload_id", uploadId);
			
			this.cmDao.update("CmProcessDao.deleteAttachAll", attMap);
		}
	}

//	public void resetTddProduct(CmMap reqVo) {
//		
//		
//	}

	/**
	 * list pagging
	 * 
	 * @param reqVo
	 * @param recordCnt
	 * @param pageSize
	 * @param nowPageNo
	 */
	public void setListPagging(CmMap reqVo, int recordCnt, int pageSize,int nowPageNo) throws Exception {

		int totalPageCnt = 0;
		int skipCnt = 0;
		int tmpPageSize  =pageSize;
		int tmpNowPageNo =nowPageNo;

		if (tmpPageSize <= 0)
			tmpPageSize = 10;
		if (tmpNowPageNo <= 0)
			tmpNowPageNo = 1;

		if (recordCnt <= tmpPageSize)
			totalPageCnt = 1;
		else
			totalPageCnt = ((recordCnt - 1) / tmpPageSize) + 1;

		if (totalPageCnt < tmpNowPageNo)
			tmpNowPageNo = totalPageCnt;
		if (tmpNowPageNo > 1)
			skipCnt = (tmpNowPageNo - 1) * tmpPageSize;

		reqVo.put("i_iTotalPageCnt", totalPageCnt);
		reqVo.put("i_iRecordCnt", recordCnt);
		reqVo.put("i_iPageSize", tmpPageSize);
		reqVo.put("i_iNowPageNo", tmpNowPageNo);
		reqVo.put("i_iSkipCnt", skipCnt);
		reqVo.put("i_iListStartNo", String.valueOf(recordCnt - (tmpNowPageNo - 1) * tmpPageSize));
		reqVo.put("i_iStartRownum", skipCnt + 1);
		reqVo.put("i_iEndRownum", skipCnt + tmpPageSize);
	}
//	protected void insertFileListOdm(String uploadCd, CmMap reqVo) throws Exception {
//		// TODO Auto-generated method stub
//		if(CmFunction.isEmpty(uploadCd) || CmFunction.isEmpty(reqVo.getString("i_sRecordId"))) {
//			throw new SwafException("파일등록을 위한 필수값이 없습니다.");
//		}
//		
//		reqVo.put("i_sUploadCd", uploadCd);		
//		String recordId = reqVo.getString("i_sRecordId");
//		String[] arrAttachId = reqVo.getStringArray(uploadCd + "_attach_id");
//		String[] arrAttachType = reqVo.getStringArray(uploadCd + "_attach_type");
//		String[] arrAttachLNm = reqVo.getStringArray(uploadCd + "_attach_lnm");
//		String[] arrAttachSize = reqVo.getStringArray(uploadCd + "_attach_size");
//		String[] arrAttachPk1 = reqVo.getStringArray(uploadCd + "_attach_pk1");
//		String[] arrAttachPk2 = reqVo.getStringArray(uploadCd + "_attach_pk2");
//		String[] arrAttachPk3 = reqVo.getStringArray(uploadCd + "_attach_pk3");
//		String[] arrAttachPk4 = reqVo.getStringArray(uploadCd + "_attach_pk4");
//		String[] arrAttachPk5 = reqVo.getStringArray(uploadCd + "_attach_pk5");
//		String[] arrAttachPNm = reqVo.getStringArray(uploadCd + "_attach_pnm");
//		String[] arrAttachMgtId = reqVo.getStringArray(uploadCd + "_attach_mgtid");
//		String arrAttachPath = CmPathInfo.getUPLOAD_FILE_TEMP_PATH() + CmFunction.getRegDate6() + "/";
//
//		String pathTemp = "";
//		String pathMove = "";
//		String movePath = "";
//
//		String[] delArrAttachId = reqVo.getStringArray(uploadCd + "_del_attach_id");
//		int delAttachLen = delArrAttachId == null ? 0 : delArrAttachId.length;
//		
//		if(delAttachLen > 0) {
//			CmMap tempMap = new CmMap();
//			
//			for(int i=0; i<delAttachLen; i++) {
//				tempMap.put("i_sAttachId", delArrAttachId[i]);				
//				this.cmOdmDao.delete("CmOdmMapper.deleteAttachFile", tempMap);
//			}
//		}
//		
//		CmMap attMap = null;
//		int attachLen = arrAttachId == null ? 0 : arrAttachId.length;
//		if(attachLen > 0) {
//			for(int i=0; i<attachLen; i++) {
//				attMap = new CmMap();
//				movePath = CmPathInfo.getUPLOAD_FILE_PATH() + arrAttachMgtId[i] + "/";
//				String tmpRecordId  = CmFunction.isEmpty(arrAttachPk1[i])||"undefined".equals(arrAttachPk1[i])?recordId:arrAttachPk1[i];
//				pathTemp = arrAttachPath + arrAttachPNm[i];
//				pathMove = movePath + arrAttachPNm[i];
//				CmMap tempMap = new CmMap();
//				tempMap.put("i_sAttachId",arrAttachId[i]);
//				tempMap.put("i_sRecordId",tmpRecordId);
//				tempMap.put("i_sUploadCd",arrAttachPk2[i]);
//				if(this.cmOdmDao.getCount("CmOdmMapper.selectAttachListCount", tempMap)<=0) {
//					attMap.put("i_sAttachId", arrAttachId[i]);
//					attMap.put("i_sAttachMgtId", arrAttachMgtId[i]);
//					attMap.put("i_sAttachPnm", arrAttachPNm[i]);
//					attMap.put("i_sAttachLnm", arrAttachLNm[i]);
//					attMap.put("i_sAttachType", arrAttachType[i]);
//					attMap.put("i_sAttachSize", arrAttachSize[i]);
//										
//					attMap.put("i_sAttachPk1", tmpRecordId);
//					attMap.put("i_sAttachPk2", arrAttachPk2[i]);
//					attMap.put("i_sAttachPk3", arrAttachPk3[i]);
//					attMap.put("i_sAttachPk4", arrAttachPk4[i]);
//					attMap.put("i_sAttachPk5", arrAttachPk5[i]);
//					attMap.put("i_sAttachPath", movePath);
//					attMap.put("i_sLoginUserId", SessionUtils.getUserNo());
//					this.cmOdmDao.insert("CmOdmMapper.insertAttachFile", attMap);						
//				}
//				
//			}			
//		}		
//	}
	
	protected void insertFileList(String uploadCd, CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		if(CmFunction.isEmpty(uploadCd) || CmFunction.isEmpty(reqVo.getString("i_sRecordId"))) {
			throw new SwafException("파일등록을 위한 필수값이 없습니다.");
		}
		
		reqVo.put("i_sUploadCd", uploadCd);		
		String recordId = reqVo.getString("i_sRecordId");
		String[] arrAttachId = reqVo.getStringArray(uploadCd + "_attach_id");
		String[] arrAttachType = reqVo.getStringArray(uploadCd + "_attach_type");
		String[] arrAttachLNm = reqVo.getStringArray(uploadCd + "_attach_lnm");
		String[] arrAttachSize = reqVo.getStringArray(uploadCd + "_attach_size");
		String[] arrAttachPk1 = reqVo.getStringArray(uploadCd + "_attach_pk1");
		String[] arrAttachPk2 = reqVo.getStringArray(uploadCd + "_attach_pk2");
		String[] arrAttachPk3 = reqVo.getStringArray(uploadCd + "_attach_pk3");
		String[] arrAttachPk4 = reqVo.getStringArray(uploadCd + "_attach_pk4");
		String[] arrAttachPk5 = reqVo.getStringArray(uploadCd + "_attach_pk5");
		String[] arrAttachPNm = reqVo.getStringArray(uploadCd + "_attach_pnm");
		String[] arrAttachMgtId = reqVo.getStringArray(uploadCd + "_attach_mgtid");
		
		String[] arrBuffer1 = reqVo.getStringArray(uploadCd + "_attach_buffer1");
		String[] arrBuffer2 = reqVo.getStringArray(uploadCd + "_attach_buffer2");
		String[] arrBuffer3 = reqVo.getStringArray(uploadCd + "_attach_buffer3");
		String[] arrBuffer4 = reqVo.getStringArray(uploadCd + "_attach_buffer4");
		String[] arrBuffer5 = reqVo.getStringArray(uploadCd + "_attach_buffer5");
		String arrAttachPath = CmPathInfo.getUPLOAD_FILE_TEMP_PATH() + CmFunction.getRegDate6() + "/";

		String pathTemp = "";
		String pathMove = "";
		String movePath = "";

		String[] delArrAttachId = reqVo.getStringArray(uploadCd + "_del_attach_id");
		int delAttachLen = delArrAttachId == null ? 0 : delArrAttachId.length;
		
		if(delAttachLen > 0) {
			CmMap tempMap = new CmMap();
			
			for(int i=0; i<delAttachLen; i++) {
				tempMap.put("i_sAttachId", delArrAttachId[i]);				
				this.cmDao.delete("CommonDao.deleteAttachFile", tempMap);
			}
		}
		
		CmMap attMap = null;
		int attachLen = arrAttachId == null ? 0 : arrAttachId.length;
		if(attachLen > 0) {
			for(int i=0; i<attachLen; i++) {
				attMap = new CmMap();
				movePath = CmPathInfo.getUPLOAD_FILE_PATH() + arrAttachMgtId[i] + "/";
				String tmpRecordId  = CmFunction.isEmpty(arrAttachPk1[i])||"undefined".equals(arrAttachPk1[i])?recordId:arrAttachPk1[i];
				pathTemp = arrAttachPath + arrAttachPNm[i];
				pathMove = movePath + arrAttachPNm[i];
				CmMap tempMap = new CmMap();
				tempMap.put("i_sAttachId",arrAttachId[i]);
				tempMap.put("i_sRecordId",tmpRecordId);
				tempMap.put("i_sUploadCd",arrAttachPk2[i]);
				if(this.cmDao.getCount("CommonDao.selectAttachListCount", tempMap)<=0 && CmFunction.fileMove(pathTemp, pathMove)) {
					attMap.put("i_sAttachId", arrAttachId[i]);
					attMap.put("i_sAttachMgtId", arrAttachMgtId[i]);
					attMap.put("i_sAttachPnm", arrAttachPNm[i]);
					attMap.put("i_sAttachLnm", arrAttachLNm[i]);
					attMap.put("i_sAttachType", arrAttachType[i]);
					attMap.put("i_sAttachSize", arrAttachSize[i]);
										
					attMap.put("i_sAttachPk1", tmpRecordId);
					attMap.put("i_sAttachPk2", arrAttachPk2[i]);
					attMap.put("i_sAttachPk3", arrAttachPk3[i]);
					attMap.put("i_sAttachPk4", arrAttachPk4[i]);
					attMap.put("i_sAttachPk5", arrAttachPk5[i]);

					attMap.put("i_sBuffer1", arrBuffer1[i]);
					attMap.put("i_sBuffer2", arrBuffer2[i]);
					attMap.put("i_sBuffer3", arrBuffer3[i]);
					attMap.put("i_sBuffer4", arrBuffer4[i]);
					attMap.put("i_sBuffer5", arrBuffer5[i]);
					
					//attMap.put("i_sAttachPath", movePath);
					attMap.put("i_sAttachPath", arrAttachMgtId[i] + "/");
					attMap.put("i_sLoginUserId", SessionUtils.getUserNo());
					this.cmDao.insert("CommonDao.insertAttachFile", attMap);						
				}
				
			}			
		}		
	}

	public void insertReqDocMailLog(CmMap reqVo) throws Exception{
		this.cmDao.insert("CommonDao.insertMailLog", reqVo);
		//this.cmOdmDao.insert("CmOdmMapper.insertMailLog", reqVo);
	}

	public void insertMailLog(CmMap reqVo) throws Exception{
		String[] revUser = reqVo.getString("i_sRevUserIdAll").split(";");
		this.cmDao.insert("CommonDao.insertMailMst", reqVo);
		for(String str :revUser) {
			reqVo.put("i_sRevUserId", str);			
			this.cmDao.insert("CommonDao.insertMailRevUser", reqVo);
		}
		this.cmDao.insert("CommonDao.insertMailContent", reqVo);
	}

	protected  CmMap decsCripto(CmMap map) {
		// TODO Auto-generated method stub
		String descFilter = "_dec_aes";
		Iterator it = map.keySet().iterator();
		CmMap tempMap = new CmMap();
		while(it.hasNext()) {
			String key = (String) it.next();
			Object valueTemp = map.get(key);
			if(key.indexOf(descFilter)>-1) {				
				if (valueTemp instanceof java.lang.String && StringUtils.isNotEmpty((String) valueTemp)) {
					valueTemp = cryptoService.decZeroSalt((String) valueTemp,true);
				}
				tempMap.put(key.replaceAll(descFilter, ""),valueTemp);				
			}else {
				tempMap.put(key,valueTemp);				
			}
		}
		return tempMap;
	}
	protected  List<CmMap> decsCripto(List<CmMap> list) {
		// TODO Auto-generated method stub
		List<CmMap> tempList = new ArrayList<CmMap>();
		for(CmMap map : list) {
			tempList.add(decsCripto(map));
		}
		return tempList;
	}
}
