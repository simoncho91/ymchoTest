package com.shinsegae_inc.swaf.common.ckeditor;

import java.util.ArrayList;
import java.util.List;

/**
 * 멀티 파일 업로드 처리를 위한 도메인 오브젝트.
 * <ul>
 * <li>Created by : 김주찬
 * <li>Created Date : 2019. 12. 19. 오후 10:01:26
 * </ul>
 *
 * @author 김주찬
 */
public class AttachFilePack {
    private List<AttachFile> attachFiles;
    
    private String atchNo;

    private String atchKnd;
    
    public AttachFilePack() {
        attachFiles = new ArrayList<AttachFile>();
    }
    
    public AttachFilePack(AttachFile attachFile) {
        this();
        addAttachFile(attachFile);
        setAtchNo(attachFile.getAtchNo());
        setAtchKnd(attachFile.getAtchKnd());
    }
    
    public AttachFilePack(List<AttachFile> attachFiles) {
        this.attachFiles = attachFiles;
        if(attachFiles != null && !attachFiles.isEmpty()) {
            this.atchNo = attachFiles.get(0).getAtchNo();
            this.atchKnd = attachFiles.get(0).getAtchKnd();
        }
    }
    
    public List<AttachFile> getAttachFiles() {
        return this.attachFiles;
    }
    
    public void setAttachFiles(List<AttachFile> files) {
        this.attachFiles = files;
    }
    
    public boolean addAttachFiles(List<AttachFile> files) {
        return attachFiles.addAll(files);
    }
    
    public boolean addAttachFile(AttachFile file) {
        return attachFiles.add(file);
    }

    public void setAtchNo(String fileId) {
        this.atchNo = fileId;
    }
    
    public void setAtchKnd(String atchKnd) {
        this.atchKnd = atchKnd;
    }
    
    public String getAtchNo() {
        return this.atchNo;
    }
    
    public String getAtchKnd(){
        return atchKnd;
    }
    
    public boolean isEmpty() {
        return this.attachFiles.isEmpty();
    }

}