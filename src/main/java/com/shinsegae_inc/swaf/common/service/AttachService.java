package com.shinsegae_inc.swaf.common.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.shinsegae_inc.core.exception.SwafException;
import com.shinsegae_inc.core.service.AtchService;
import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.core.util.DateUtils;
import com.shinsegae_inc.core.util.FileUtils;
import com.shinsegae_inc.core.util.StringUtils;
import com.shinsegae_inc.core.vo.AtchVO;
import com.shinsegae_inc.dhtmlx.support.DhtmlxUtils;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.swaf.common.ckeditor.AttachFile;
import com.shinsegae_inc.swaf.common.ckeditor.AttachFilePack;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;

/**
 * @Package Name : com.swaf.common.service
 * @FileName     : SampleService.java
 * @date         : 2016. 7. 22. 
 * @author       : wjLee7
 * 설명 : 
 * 변경이력 : 
 */

@Service("AttachService")
public class AttachService extends SwafService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name="CommonMapper")
    private CommonMapper commonMapper;
	
	@Resource(name = "AtchService")
    private AtchService atchService;

    @Value("${globals.file.uploadPath}")
    private String fileUploadPath;

    @Value("${globals.file.ckEditorImagePath}")
    private String imageFileUploadPath;
	
    
    
    
    /**
	*
	* @param request
	*
	* <pre>
	* Commnets : 파일업로드 수행
	* </pre>
	*/
    public Map fileUpload(HttpServletRequest request) throws Exception {
    	List errorList = new ArrayList();
    	List succList = new ArrayList();
    	Map rsltMap = new HashMap();
    	
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
		
		Iterator iter = mhsr.getFileNames(); 
		MultipartFile mfile = null; 
		String fieldName = ""; 
		
		String fileName = "";
		String newName = "";
		int pos = 0;
		String ext = "";
		
		int atchNo = 0;
		
		// 파일저장경로
		String sUploadPath = fileUploadPath;
		String stordFilePath = "";
		
		Map fileMap = new HashMap();
		
		int atchSeq = -1;
		
		// 값이 나올때까지 
		while (iter.hasNext()) {
			//atchSeq++;
			
			fieldName = (String)iter.next(); // 내용을 가져와서
			mfile = mhsr.getFile(fieldName); 
			//첨부파일의 확장자를 체크
			// 1. BlackList 체크
			//	FileUtils.fileExtentionBlackList(mfile)
			
	    	if(FileUtils.fileExtentionBlackList(mfile)){
//		    		errorList.add(mfile.getOriginalFilename());
//					continue;
	    		//허용되지 않는 확장자의 업도르 파일이 존재합니다.
	    		throw new SwafException("Warn.W119");
	    	}
	    		
	    	// 2. WhiteList 체크
	    	//	!FileUtils.fileExtentionWhiteList(mfile)
			
	    	if(!FileUtils.fileExtentionWhiteList(mfile)){
//		    		errorList.add(mfile.getOriginalFilename());
//					continue;
	    		//허용되지 않는 확장자의 업도르 파일이 존재합니다.
	    		throw new SwafException("Warn.W119");
	    	}
			
			//첨부파일 저장에 대한 공통 로직이 필요 
	    	fileName = mfile.getOriginalFilename();
	    	if(CmFunction.isNotEmpty(fileName)) {
				pos = fileName.lastIndexOf( "." );
				ext = fileName.substring( pos + 1 );	    		
	    	}
			
			//서버에 저장될 파일 명
	    	//newName = UUID.randomUUID().toString() + "." + ext;
	    	newName = UUID.randomUUID().toString();
	    	
	    	//서버 저장 경로
	    	//stordFilePath = sUploadPath + "test";
	    	stordFilePath = sUploadPath;
	    	
	    	//서버에 저장
			atchService.writeUploadedFile(mfile, newName, stordFilePath);
			
			//DB에 저장
			//최초 등록인 경우 AtchM 등록 하여 AtchNo 값을 가져온다.
			AtchVO fileVO = new AtchVO();
			fileVO.setRegUserNo("TEST");
			fileVO.setRegIp("0.0.0.0");
			
			
			String strAtchNo = request.getParameter("atchNo");
			if(strAtchNo != null) {
				atchNo = Integer.parseInt(strAtchNo);
			}

			// atchNo가 있을경우 디테일 테이블에만 인서트한다.
			if(succList.size() == 0 && atchNo == 0){
				commonMapper.insert("Attach.insertAtchM", fileVO);
				atchNo = fileVO.getAtchNo();
			}

			fileVO.setAtchNo(atchNo);
			fileVO.setAtchSeq(atchSeq);
			fileVO.setOrgFileNm(fileName);
			fileVO.setFilePath(stordFilePath);
			fileVO.setFileNm(newName);
			fileVO.setFileExtn("."+ext);
			fileVO.setFileSize(mfile.getSize());
			
			commonMapper.update("Attach.insertAtchD", fileVO);
			atchSeq = commonMapper.selectOne("Attach.selectFileSeq", fileVO);
			
			succList.add(mfile.getOriginalFilename());
		}

		rsltMap.put("errorList",errorList);
		rsltMap.put("succList",succList);
		rsltMap.put("atchNo",atchNo);					// 첨부파일 M의 키
		rsltMap.put("atchNoSeq",atchNo+"-"+atchSeq);	// 첨부파일 D의 키X
		rsltMap.put("atchSeq",atchSeq);	// 첨부파일 D의 키
		
		return rsltMap;
	}
    
    /**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 첨부파일 정보 수정
	* </pre>
	*/
	public void updateAttachInfo(Map hm) throws Exception {		
		try {
			hm.put("atchNo", Integer.parseInt((String) hm.get("atchNo")));
			commonMapper.insert("Attach.updateAttachInfo", hm);
		} catch (Exception e) {
			throw e;
		}
	}
    
    /**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 첨부파일 목록 조회
	* </pre>
	*/
	public Map selectAttachList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		//log.debug(propertyService.getString("Globals.encFilePath1"));
		
		try {
			List<Map> list = commonMapper.selectList("Attach.selectAtchList", hm);
			mapReturn.put("data", DhtmlxUtils.toListJson(list, false));
		} catch (Exception e) {
			throw e;
		}
		return mapReturn;
	}
	
	/**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 첨부파일 상세 조회
	* </pre>
	*/
	public Map selectAttachInfo(String atchNoSeq) throws Exception {		
		Map hm 			= new HashMap<String, Object>();	
		Map mapReturn 	= new HashMap<String, Object>();
		String arrAtchNoSeq[];
		
		try {
			arrAtchNoSeq = atchNoSeq.split("-");
			hm.put("atchNo", Integer.parseInt(arrAtchNoSeq[0]));
			hm.put("atchSeq", Integer.parseInt(arrAtchNoSeq[1]));
			
			mapReturn = commonMapper.selectOne("Attach.selectAtchInfo", hm);
		} catch (Exception e) {
			throw e;
		}
		return mapReturn;
	}
	
	/**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 첨부파일 마스터포함 삭제
	* </pre>
	*/
	public void deleteAttach(String attachNo) throws Exception {	
		
		String atchNo = attachNo;
		try {
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put("atchNo", atchNo);
			
			// 물리파일 삭제(목록 조회 후 전체삭제)
			List<Map> list = commonMapper.selectList("Attach.selectAtchList", hm);
			for(int i=0 ; i < list.size() ; i++) {
				Map<String, Object> param = list.get(i);
				log.debug("SERVER FILE INFO : "+param.get("FILE_PATH") + "/"+ param.get("FILE_NM"));
				AtchService.deleteFile(param.get("FILE_PATH") + "/"+ param.get("FILE_NM"));
			}

			
			commonMapper.delete("Attach.deleteAllFileDtlDhtmlx", hm);
			commonMapper.delete("Attach.deleteFileDhtmlx", hm);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 첨부파일 삭제
	* </pre>
	*/
	public void deleteAttachDtl(String atchNoSeq) throws Exception {	
		String arrAtchNoSeq[];	
		try {
			arrAtchNoSeq = atchNoSeq.split("-");
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put("atchNo", Integer.parseInt(arrAtchNoSeq[0]));
			hm.put("atchSeq", Integer.parseInt(arrAtchNoSeq[1]));
			
			// 물리파일 삭제 (상세 조회 후 삭제)
			Map attachInfo = new HashMap();
			attachInfo = selectAttachInfo(atchNoSeq);
			if(attachInfo != null) {
				log.debug("SERVER FILE INFO : "+attachInfo.get("FILE_PATH") + "/"+ attachInfo.get("FILE_NM"));
				AtchService.deleteFile(attachInfo.get("FILE_PATH") + "/"+ attachInfo.get("FILE_NM"));
			}
			

			commonMapper.delete("Attach.deleteFileDtlDhtmlx", hm);
			
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/*public String getAtchUploadPath(String atchKnd){
        log.debug("Getting attach kind upload path : {}", atchKnd);
        // 파일 유형에 따라 분기처리
        return filePath;
    }*/
	 
	/**
    * 파일 다운로드
    * @param response
    * @param attachFile
    * @throws Exception
    */
   public void streamingAttachFile(HttpServletResponse response, Map attachFile) throws Exception {
       String fileName = (String) attachFile.get("FILE_NM");
       String downloadFileName = new String(((String)attachFile.get("ORG_FILE_NM")).getBytes("UTF-8"), "ISO-8859-1");
       response.setContentType("application/octet;charset=utf-8");

       response.setHeader("Content-Disposition", "attachment; filename = \"" + downloadFileName + "\"");
       response.setHeader("Content-Transper-Encoding", "binary");
       response.setHeader("Pargma", "no-cache");
       response.setHeader("Expires", "-1");

       String fileFullPath = fileUploadPath + "/" + fileName;

       log.info("Downloading : downloadFileName={}", new Object[]{downloadFileName});
       log.info("Downloading : fileFullPath={}", new Object[]{fileFullPath});

       File file = org.apache.commons.io.FileUtils.getFile(fileFullPath);
       if(file.exists()) {
           FileCopyUtils.copy(org.apache.commons.io.FileUtils.openInputStream(file), response.getOutputStream());
       } else {
           log.warn("File {} does not exist", file);
       }
   }
   
   /**
    * 파일 업로드를 통해 전송된 첨부파일을 저장한다.
    *
    * <p>첨부파일 업로드의 1단계
    * <li>파일 첨부 저장시에 먼저 Multipart 로 파일이 전송되고 이 때 호출되는 메소드이다.
    * <li>Multipart 파일은 AttachFileController 의 createFiles 메소드에서 List<AttachFile> 형태로 변환된다. AttachFileId 의 생성도 이 단계에서 생성한다.
    * <li>첨부파일 테이블에 정보 등록 후 그 결과를 AttachFilePack 형태로 리턴한다.
    * <li>리턴된 AttachFilePack 객체가 서비스 메소드로 전달된다.
           */
   public AttachFilePack saveMultipartFiles(List<AttachFile> files) throws SwafException {
       AttachFilePack filePack = new AttachFilePack(files);
       log.debug("saving AttachFilePack(ID : {}, TYPE : {}, COUNT : {}", filePack.getAtchNo(), filePack.getAtchKnd(), filePack.getAttachFiles().size());

       Map<String, String> param = new HashMap<String, String>();

       savePhysicalFiles(filePack);

       filePack.setAttachFiles(files);
       return filePack;
   }
   
   /**
    * 물리적인 첨부파일의 저장처리 메소드.
    *
    * <p>물리적인 첨부파일을 첨부파일 유형에 따른 경로로 이동시킨다.
    */
   private void savePhysicalFiles(AttachFilePack filePack) throws SwafException {
       if(filePack != null) {
           String atchKnd = filePack.getAtchKnd();
           org.springframework.core.io.Resource uploadDir = getAtchUploadResource(atchKnd);

           log.debug("Saving AttachFilePack [getAtchNo : {}, file count : {}, uploadDir : {}]",
                   new Object[]{filePack.getAtchNo(), filePack.getAttachFiles().size(), uploadDir});

           //신규 등록
           for(AttachFile attachFile: filePack.getAttachFiles()) {
              attachFile.save(uploadDir);
           }
       }
   }
   
   public org.springframework.core.io.Resource getAtchUploadResource(String atchKnd) throws SwafException {
       String uploadPath = imageFileUploadPath;
       if(StringUtils.isNotEmpty(uploadPath)){
           return (org.springframework.core.io.Resource) new FileSystemResource(uploadPath);
       }else{
           return getDefaultUploadResource();
       }
   }
   
   /**
    * 기본 첨부파일 저장 위치.
    * @return
    */
   private org.springframework.core.io.Resource getDefaultUploadResource() throws SwafException {
       String[] paths = org.apache.commons.lang.StringUtils.split(DateUtils.getDateString("yyyy/yyyyMM/yyyyMMdd"), "/");
       try {
           File file = org.apache.commons.io.FileUtils.getFile(new File(imageFileUploadPath), paths);           
           if(file != null && !file.exists()) {
               org.apache.commons.io.FileUtils.forceMkdir(file);
               return new FileSystemResource(file);
           } else {
        	   return getDefaultUploadResource();
           }
           
       } catch (IOException e) {
           throw new SwafException("failed to get default upload resource", e);
       }
   }
}
