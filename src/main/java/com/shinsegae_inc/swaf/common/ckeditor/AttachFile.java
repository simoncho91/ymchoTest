package com.shinsegae_inc.swaf.common.ckeditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.shinsegae_inc.core.exception.SwafException;

/**
 * 첨부파일 도메인 객체.
 * <ul>
 * <li>Created by : 김주찬
 * <li>Created Date : 2019. 11. 12. 오전 12:27:09
 * </ul>
 *
 * @author 윤석진
 */
public class AttachFile implements Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String atchNo;

    private String atchSeq;
    
    private MultipartFile multipartFile;

    private File file;
    
    private String fileNm;
    
    private String fileExtn;
    
    private String filePath;
    
    private Integer fileSize;
    
    private String atchKnd;

    private String atchTpCd;

    private String atchCd;

    private String downloadUrl;
    
    private String isBLOB;

    private String regUserId;

    private String regIp;


    public AttachFile() {
    
    }
    
    public AttachFile(String atchNo, MultipartFile file) {
        this.atchNo = atchNo;
        this.multipartFile = file;
        this.fileNm = file.getOriginalFilename();
        this.fileExtn = FilenameUtils.getExtension(this.fileNm);
        this.fileSize = (int) file.getSize();
    }
    
    public AttachFile(String atchNo, File file) {
        this.atchNo = atchNo;
        this.file = file;
        this.fileNm = file.getName();
        this.fileExtn = FilenameUtils.getExtension(this.fileNm);
        this.fileSize = (int) file.length();
        this.filePath = file.getPath();
    }
    
    public AttachFile(File file) {
        this.file = file;
        this.fileNm = file.getName();
        this.fileExtn = FilenameUtils.getExtension(this.fileNm);
        this.fileSize = (int) file.length();
        this.filePath = file.getPath();
    }
    


    public String getFilePath() {
        return this.filePath;
    }
    
    public Integer getFileSize() {
        return this.fileSize;
    }
    
    public String getAtchKnd(){
        return atchKnd;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getIsBLOB() {
        return isBLOB;
    }

    public String getAtchNo() {
        return atchNo;
    }

    public void setAtchNo(String atchNo) {
        this.atchNo = atchNo;
    }

    public String getAtchSeq() {
        return atchSeq;
    }

    public void setAtchSeq(String atchSeq) {
        this.atchSeq = atchSeq;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getFileExtn() {
        return fileExtn;
    }

    public void setFileExtn(String fileExtn) {
        this.fileExtn = fileExtn;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public void setAtchKnd(String atchKnd) {
        this.atchKnd = atchKnd;
    }

    public String getAtchTpCd() {
        return atchTpCd;
    }

    public void setAtchTpCd(String atchTpCd) {
        this.atchTpCd = atchTpCd;
    }

    public String getAtchCd() {
        return atchCd;
    }

    public void setAtchCd(String atchCd) {
        this.atchCd = atchCd;
    }

    public String getRegUserId() {
        return regUserId;
    }

    public void setRegUserId(String regUserId) {
        this.regUserId = regUserId;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    
    public void setIsBLOB(String isBLOB) {
        this.isBLOB = isBLOB;
    }
    

    public void save(Resource dir) throws SwafException {
        try {
            //save를 통한 저장 시 물리적 파일명을 변경한다.
            //String serverFileName = FileNameGenerator.generateFileName(32);
            String serverFileName = UUID.randomUUID().toString();
            
            File serverFile = new File(dir.getFile(), serverFileName);
            this.filePath = serverFile.getPath();
            InputStream is = null;
            FileOutputStream fos = null;
            
            if(multipartFile != null){
                is = this.multipartFile.getInputStream();
                fos = FileUtils.openOutputStream(serverFile);
                FileCopyUtils.copy(is, fos);
            }else{
                //직접 생성한 File 을 이용한 AttachFile 객체
                is = new FileInputStream(file);
                fos = FileUtils.openOutputStream(serverFile);
                FileCopyUtils.copy(is, fos);
                logger.debug("File copy from {} to {}", file, serverFile);
            }
            this.file = serverFile;
            
            fos.flush();
            fos.close();
            is.close();
        } catch(IOException e) {
            throw new SwafException("Could not save file", e);
        }
    }
    
    public File getFile() {
        return new File(this.filePath);
    }
    
    /**
     * 디스크에서 파일을 삭제한다.
     */
    public boolean remove() {
        File serverFile = new File(this.filePath);
        logger.debug("Removing file {}", serverFile);
        
        boolean result = FileUtils.deleteQuietly(serverFile);
        if(!result) {
            logger.warn("Failed to delete file [{}] ", serverFile);
        }
        return result;
    }

    @Override
    public String toString() {
        return "AttachFile{" +
                "atchNo='" + atchNo + '\'' +
                ", atchSeq='" + atchSeq + '\'' +
                ", multipartFile=" + multipartFile +
                ", file=" + file +
                ", fileNm='" + fileNm + '\'' +
                ", fileExtn='" + fileExtn + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", atchKnd='" + atchKnd + '\'' +
                ", atchTpCd='" + atchTpCd + '\'' +
                ", atchCd='" + atchCd + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", isBLOB='" + isBLOB + '\'' +
                ", regUserId='" + regUserId + '\'' +
                ", regIp='" + regIp + '\'' +
                '}';
    }
}
