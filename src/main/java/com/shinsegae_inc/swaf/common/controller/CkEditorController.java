package com.shinsegae_inc.swaf.common.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shinsegae_inc.core.exception.SwafException;
import com.shinsegae_inc.swaf.common.ckeditor.AttachFile;
import com.shinsegae_inc.swaf.common.ckeditor.AttachFilePack;
import com.shinsegae_inc.swaf.common.ckeditor.ImageUploadStatus;
import com.shinsegae_inc.swaf.common.service.AttachService;

/**
 * CKEditor 공통 처리를 위한 Spring Controller 객체.
 * <ul>
 * <li>Created by : 김주찬
 * <li>Created Date : 2019. 11. 27. 오전 9:05:20
 * </ul>
 *
 * @author 김주찬
 */
@Controller
public class CkEditorController {

    private final transient org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    //@Resource(name = "board.AttachService")
    //private AttachService attachService;
    
    @Resource(name = "AttachService")
    private AttachService attachService;

    @Autowired
    private ServletContext servletContext;
        
    @Value("${globals.domain}")
    private String servletContextPath;

    private static final String ATTACH_FILE_TYPE = "BOARD"; 

    /**
     * CKEditor 이미지 업로드 처리 (CKEditor 버튼을 이용한 이미지 업로드, 사용X)
     */
    @RequestMapping(value = "/attach/ckeditor/imageupload.do", method = RequestMethod.POST)
    @ResponseBody
    public String imageUpload(@RequestParam("upload") MultipartFile file, @RequestParam("CKEditorFuncNum") String callback) throws SwafException {
        log.debug("imageupload({}, {}, {})", file.getName(), file.getContentType(), file.getSize());

        AttachFilePack filePack = saveImage(file);
        AttachFile attachFile = filePack.getAttachFiles().get(0);
        String downloadURL = "/attach/ckeditor/imagedownload.do?filePath="+URLEncoder.encode(attachFile.getFilePath()) + "&fileNm="+attachFile.getFileNm();
        log.debug("servletContextPath : " + servletContextPath);
        
        return "<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction('" + callback + "', '" + downloadURL + "')</script>";
    }

    /**
     * CKEditor 이미지 업로드 처리 2 (CKEditor drag&drop 이용한 이미지 업로드)
     */
    @RequestMapping(value = "/attach/ckeditor/imageupload2.do", method = RequestMethod.POST)
    @ResponseBody
    public ImageUploadStatus imageUpload2(@RequestParam("upload") MultipartFile file) throws SwafException {
        log.debug("imageupload({}, {}, {})", file.getName(), file.getContentType(), file.getSize());

        AttachFilePack filePack = saveImage(file);
        AttachFile attachFile = filePack.getAttachFiles().get(0);

        String downloadURL = "/attach/ckeditor/imagedownload.do?filePath="+URLEncoder.encode(attachFile.getFilePath()) + "&fileNm="+attachFile.getFileNm();
        log.debug("downloadURL : " + downloadURL);
        return new ImageUploadStatus(1, attachFile.getFileNm(), downloadURL);
    }
    /**
     * CKEditor 이미지 다운로드 처리
     */
    @RequestMapping({"/attach/ckeditor/imagedownload.do"})
    public String imageDownload(@RequestParam String filePath, @RequestParam String fileNm, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.debug("Downloading :  filePath={}, fileNm={}", filePath, fileNm);

        //AttachFile file = attachService.getAttachFile(atchNo, atchSeq);
        AttachFile file = new AttachFile();
        file.setFilePath(filePath);
        file.setFileNm(fileNm);

        if (file == null) {
            throw new SwafException("Attach File Not Found [ filePath=" + filePath + ", fileNm=" + fileNm + "]");
        }

        streamImageFile(request, response, file);
        return null;
    }

    /**
     * 첨부된 이미지를 저장하고 AttachFilePack 객체를 리턴한다.
     */
    private AttachFilePack saveImage(MultipartFile file) throws SwafException {
        //String atchFileId = attachService.issueNewAtchIdByAtchKnd(ATTACH_FILE_TYPE);
        String atchFileId = UUID.randomUUID().toString();

        AttachFile attachFile = new AttachFile(atchFileId, file);
        attachFile.setAtchKnd(ATTACH_FILE_TYPE);

        List<AttachFile> list = new ArrayList<AttachFile>();
        list.add(attachFile);
        return attachService.saveMultipartFiles(list);
    }

    /**
     * 이미지 첨부파일을 streaming 한다.
     */
    private void streamImageFile(HttpServletRequest request, HttpServletResponse response, AttachFile attachFile) throws Exception {
        String userAgent = request.getHeader("User-Agent");
        //String fileName = URLEncoder.encode(attachFile.getFileName(), "utf-8").replace("+", " ");
        String fileName = attachFile.getFileNm();
        log.debug("Streaming image file : {}", fileName);

        response.setHeader("Content-Type", servletContext.getMimeType(attachFile.getFileNm()));
        response.setHeader("Content-Disposition", "inline; filename=" + fileName + ";");
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Pargma", "no-cache");
        response.setHeader("Expires", "-1");

        File file = new File(attachFile.getFilePath());
        if (file.exists()) {
            FileCopyUtils.copy(FileUtils.openInputStream(file), response.getOutputStream());
        } else if ("Y".equals(attachFile.getIsBLOB())) {
            //FileCopyUtils.copy(attachService.getBLOBInputStream(attachFile), response.getOutputStream());
        } else {
            log.warn("File {} does not exist", file);
        }
    }
}