package com.shinsegae_inc.sitims.mi.br.pr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.common.util.CmPathInfo;

@Service("sitims.BrPr012Service")
@SuppressWarnings("rawtypes")
public class BrPr012Service extends CmService{

	public List<CmMap> selectPartnoList(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getList("BrPr012Mapper.selectPartnoList", reqVo);
	}

	public CmMap getBrPr012Info(CmMap reqVo) {
		// TODO Auto-generated method stub
		//return cmDao.getObject("BrPr010Mapper.getBrPr010ProdList", reqVo);
		return this.decsCripto(cmDao.getObject("BrPr010Mapper.getBrPr010ProdList", reqVo));
	}

	public CmMap selectCompanyInfo(CmMap reqVo) {
		// TODO Auto-generated method stub
		
		return this.decsCripto(cmDao.getObject("BrPr012Mapper.selectCompanyInfo", reqVo));
	}

	public List<CmMap> selectAllergenIngrtList(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getList("BrPr012Mapper.selectAllergenIngrtList", reqVo);
	}

	public List<CmMap> selectOdmConSingleListWithoutAllergen(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getList("BrPr012Mapper.selectOdmConSingleListWithoutAllergen", reqVo);
	}

	public List<CmMap> selectOdmConSingleListAllergen(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getList("BrPr012Mapper.selectOdmConSingleListAllergen", reqVo);
	}

	public List<CmMap> selectOdmFragranceList(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getList("BrPr012Mapper.selectOdmFragranceList", reqVo);
	}

	public List<CmMap> getSectionList(CmMap reqVo) {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPr012Mapper.getSectionList",reqVo);
	}

	public CmMap selectOdmMsds(CmMap reqVo) {
		// TODO Auto-generated method stub
		return new CmMap();
		//return this.cmDao.getObject("BrPr012Mapper.selectOdmMsds", reqVo);
	}

	public List<CmMap> getHazardInfo(CmMap reqVo) {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPr012Mapper.getHazardInfo", reqVo);
	}

	public CmMap selectOdmMayContainInfo(CmMap reqVo) {
		// TODO Auto-generated method stub
		return this.cmDao.getObject("BrPr012Mapper.selectOdmMayContainList", reqVo);
	}

	public void coDocFomulaExcel(CmMap reqVo,ModelAndView mav) throws Exception {
		// TODO Auto-generated method stub

		List<CmMap> allergenList = new ArrayList<CmMap>();
		List<CmMap> fragList = new ArrayList<CmMap>();

		CmMap productInfo = this.getBrPr012Info(reqVo);
		String productCd ="";
		if(productInfo!=null) {
			reqVo.put("i_sProductCd",productInfo.getString("v_product_cd"));
			productCd= productInfo.getString("v_product_cd");
			reqVo.put("i_sMatrcd", productCd);
			reqVo.put("i_sPartTab", "Y");
			reqVo.put("i_iVsn", reqVo.getString("i_iVerSeq"));
			reqVo.put("i_sCompanyCd", productInfo.getString("v_vendor_id"));
			
			CmMap companyInfo = this.selectCompanyInfo(reqVo);
			if(companyInfo != null) {
				if(CmFunction.isEmpty(companyInfo.getString("v_logo_attachid"))){
					reqVo.put("i_sLogo_flag", "N");
					//companyInfo.put("v_logo_attachid", "no_logo2.jpg");
				}
			} else {
				companyInfo = new CmMap();
				reqVo.put("i_sLogo_flag", "N");
				//companyInfo.put("v_logo_attachid", "no_logo2.jpg");
			}
			if(companyInfo != null) {
				if(CmFunction.isEmpty(companyInfo.getString("v_logo_attachid"))){
					reqVo.put("i_sLogo_flag", "N");
					//companyInfo.put("v_logo_attachid", "no_logo2.jpg");
				}
			} else {
				companyInfo = new CmMap();
				reqVo.put("i_sLogo_flag", "N");
				//companyInfo.put("v_logo_attachid", "no_logo2.jpg");
			}
			
			if("Y".equals(reqVo.getString("i_sAllergenFlag"))){
				allergenList = this.selectAllergenIngrtList(reqVo);
//				mav.addObject("allergenList", allergenList);
				reqVo.put("allergenList", allergenList);
			}
			
			List<CmMap> list = null;
			reqVo.put("i_sLeaveonYn", productInfo.getString("v_part_leaveon_yn"));
			reqVo.put("i_sSortCol", "N_RCONT");
			
			if("Y".equals(reqVo.getString("i_sRefYn"))){
				list = this.selectOdmConSingleListWithoutAllergen(reqVo);
			}else{
				list = this.selectOdmConSingleListAllergen(reqVo);
			}

			fragList = this.selectOdmFragranceList(reqVo);

			CmMap maycontain = this.selectOdmMayContainInfo(reqVo);
			reqVo.put("maycontain", maycontain==null?new CmMap():maycontain);
			reqVo.put("companyInfo", companyInfo);
			reqVo.put("productInfo", productInfo);
			reqVo.put("list",list);
			reqVo.put("fragList", fragList);
			
			reqVo.put("i_sSignDate", CmFunction.getSignDate());

			this.setAttachFilePath(companyInfo.getString("v_sign_attachid"),"vSignFile",reqVo);
			this.setAttachFilePath(companyInfo.getString("v_logo_attachid"),"vLogoFile",reqVo);
		}
	}

	private void setAttachFilePath(String attachId, String setNm, CmMap reqVo) throws Exception {
		if (CmFunction.isNotEmpty(attachId)) {
			CmMap tempVo = new CmMap();
			tempVo.put("i_sAttachId", attachId);
			CmMap attachInfo = commonService.getAttachInfo(tempVo);
			String filePath = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_attach_path")+attachInfo.getString("v_attach_id")+attachInfo.getString("v_attach_ext");
			reqVo.put(setNm, filePath);
		}
	}

	public void coDocFomulaKoExcel(CmMap reqVo, ModelAndView mav) throws Exception {
		// TODO Auto-generated method stub
		CmMap productInfo = this.getBrPr012Info(reqVo);
		reqVo.put("i_sProductCd", productInfo.getString("v_product_cd"));
		reqVo.put("i_sPartTab", "Y");
		
		String companyCd = null;

		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		
		//if("Y".equals(productInfo.getString("v_import_yn"))){
		//	reqVo.put("i_sPk1", productInfo.getString("v_product_cd"));
		//}else{
		//	reqVo.put("i_sPk1", companyCd);
		//}
		reqVo.put("i_sPk1", companyCd);
		
		if("Y".equals(reqVo.getString("i_sAllergenFlag"))){
			List<CmMap> allergenList = this.selectAllergenIngrtList(reqVo);
			reqVo.put("allergenList",allergenList);
//				mav.addObject("allergenList", allergenList);
		}
		
		CmMap companyInfo = this.selectCompanyInfo(reqVo);
		
		if(companyInfo == null ){
			companyInfo = new CmMap();
		}
		
		if(companyInfo.getString("v_logo").isEmpty()){
			companyInfo.put("v_logo", CmPathInfo.getIMG_PATH()+"no_logo2.jpg");
		}else{
			companyInfo.put("v_logo", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_logo_attachid"));
			companyInfo.put("v_sign_url", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_sign_attachid"));
		}
		
		List<CmMap> list = this.selectOdmPifViewForKorea(reqVo);
		List<CmMap> fragList = this.selectOdmFragranceList(reqVo);
		
		reqVo.put("companyInfo", companyInfo);
		reqVo.put("productInfo", productInfo);
		reqVo.put("list",list);
		reqVo.put("fragList", fragList);
		//CmFunction.getSignDate();
		reqVo.put("i_sSignDate", CmFunction.getSignDate());

		this.setAttachFilePath(companyInfo.getString("v_sign_attachid"),"vSignFile",reqVo);
		this.setAttachFilePath(companyInfo.getString("v_logo_attachid"),"vLogoFile",reqVo);
	}

	public List<CmMap> selectOdmPifViewForKorea(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectOdmPifViewForKorea", reqVo);
	}

	public void coDocFomulaJpExcel(CmMap reqVo, ModelAndView mav) throws Exception {
		// TODO Auto-generated method stub
		//CmMap productInfo = pifOdmTddService.selectOdmProductInfo(reqVo);
		CmMap productInfo = this.getBrPr012Info(reqVo);
		reqVo.put("i_sProductCd", productInfo.getString("v_product_cd"));
		reqVo.put("i_sPartTab", "Y");
		
		String companyCd = null;
		
		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		
		//if("Y".equals(productInfo.getString("v_import_yn"))){
		//	reqVo.put("i_sPk1", productInfo.getString("v_product_cd"));
		//}else{
		//	reqVo.put("i_sPk1", companyCd);
		//}
		reqVo.put("i_sPk1", companyCd);
		
		if("Y".equals(reqVo.getString("i_sAllergenFlag"))){
			List<CmMap> allergenList = this.selectAllergenIngrtList(reqVo);
			reqVo.put("allergenList",allergenList);
		}
		
		CmMap companyInfo = this.selectCompanyInfo(reqVo);
		
		if(companyInfo == null ){
			companyInfo = new CmMap();
		}
		
		if(companyInfo.getString("v_logo").isEmpty()){
			companyInfo.put("v_logo", CmPathInfo.getIMG_PATH()+"no_logo2.jpg");
		}else{
			companyInfo.put("v_logo", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_logo_attachid"));
			companyInfo.put("v_sign_url", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_sign_attachid"));
		}
		
		List<CmMap> list = this.selectOdmPifViewForJapan(reqVo);
		List<CmMap> fragList = this.selectOdmFragranceList(reqVo);
		
		reqVo.put("companyInfo", companyInfo);
		reqVo.put("productInfo", productInfo);
		reqVo.put("list",list);
		reqVo.put("fragList", fragList);
		
		reqVo.put("i_sSignDate", CmFunction.getSignDate());

		this.setAttachFilePath(companyInfo.getString("v_sign_attachid"),"vSignFile",reqVo);
		this.setAttachFilePath(companyInfo.getString("v_logo_attachid"),"vLogoFile",reqVo);		
	}

	public List<CmMap> selectOdmPifViewForJapan(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectOdmPifViewForJapan", reqVo);
	}

	public void coDocFomulaCnExcel(CmMap reqVo, ModelAndView mav) throws Exception {
		// TODO Auto-generated method stub
		//CmMap productInfo = pifOdmTddService.selectOdmProductInfo(reqVo);
		CmMap productInfo = this.getBrPr012Info(reqVo);
		reqVo.put("i_sMatrcd", productInfo.getString("v_product_cd"));
		reqVo.put("i_sPartTab", "Y");
		
		String companyCd = null;

		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		
		//if("Y".equals(productInfo.getString("v_importyn"))){
		//	reqVo.put("i_sPk1", productInfo.getString("v_product_cd"));
		//}else{
		//	reqVo.put("i_sPk1", companyCd);
		//}
		reqVo.put("i_sPk1", companyCd);
		
		if("Y".equals(reqVo.getString("i_sAllergenFlag"))){
			List<CmMap> allergenList = this.selectAllergenIngrtList(reqVo);
			reqVo.put("allergenList",allergenList);
		}
		
		CmMap companyInfo = this.selectCompanyInfo(reqVo);
		
		if(companyInfo == null ){
			companyInfo = new CmMap();
		}
		
		if(companyInfo.getString("v_logo").isEmpty()){
			companyInfo.put("v_logo", CmPathInfo.getIMG_PATH()+"no_logo2.jpg");
		}else{
			companyInfo.put("v_logo", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_logo_attachid"));
			companyInfo.put("v_sign_url", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_sign_attachid"));
		}
		
		reqVo.put("i_iVerSeq", productInfo.getString("n_vsn"));
		reqVo.put("i_sExcelFlag", "Y");
		
		List<CmMap> list = this.selectOdmPifViewForChina(reqVo);
		List<CmMap> fragList = this.selectOdmFragranceList(reqVo);
		
		reqVo.put("companyInfo", companyInfo);
		reqVo.put("productInfo", productInfo);
		reqVo.put("list",list);
		reqVo.put("fragList", fragList);

		
		reqVo.put("i_sSignDate", CmFunction.getSignDate());

		this.setAttachFilePath(companyInfo.getString("v_sign_attachid"),"vSignFile",reqVo);
		this.setAttachFilePath(companyInfo.getString("v_logo_attachid"),"vLogoFile",reqVo);
		
	}

	public List<CmMap> selectOdmPifViewForChina(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectOdmPifViewForChina", reqVo);
	}

	public void coDocProcessExcel(CmMap reqVo, ModelAndView mav) throws Exception {
		// TODO Auto-generated method stub
		CmMap						prodVo		=	null;
		CmMap						processVo		=	null;
		List<CmMap>					folderList		=	null;
		List<CmMap>					conList		=	null;
		CmMap						companyVo	=  new CmMap();
		//prodVo = odmInciService.selectOdmProductInfo(reqVo);
			prodVo = this.getBrPr012Info(reqVo);
			prodVo.put("v_now_date", CmFunction.getUsDate1(CmFunction.getToday()));
			
			String companyCd = null;
			
			if(CmFunction.isNotEmpty(prodVo.getString("v_packing_companycd"))){
				companyCd = prodVo.getString("v_packing_companycd");
			}else{
				companyCd = prodVo.getString("v_vendor_id");
			}
			
			reqVo.put("i_sCompanyCd", companyCd);
			
			//if("Y".equals(prodVo.getString("v_import_yn"))){
			//	reqVo.put("i_sPk1", prodVo.getString("v_product_cd"));
			//}else{
			//	reqVo.put("i_sPk1", companyCd);
			//}
			reqVo.put("i_sPk1", companyCd);
			
			companyVo = this.selectCompanyInfo(reqVo);
			
			if(companyVo != null) {
				if(CmFunction.isEmpty(companyVo.getString("v_logo"))){
					reqVo.put("i_sLogo_flag", "N");
					companyVo.put("v_logo", CmPathInfo.getIMG_PATH()+"no_logo2.jpg");
				}else{
					companyVo.put("v_logo", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyVo.getString("v_logo_attachid"));
				}
				
				if(CmFunction.isEmpty(companyVo.getString("v_sign"))){
					reqVo.put("i_sSign_flag", "N");
					companyVo.put("v_sign", CmPathInfo.getIMG_PATH()+"footer.gif");
				}else{
					companyVo.put("v_sign", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyVo.getString("v_sign_attachid"));
				}
				
			} else {
				companyVo = new CmMap();
				reqVo.put("i_sLogo_flag", "N");
				reqVo.put("i_sSign_flag", "N");
				
				companyVo.put("v_logo", CmPathInfo.getIMG_PATH()+"no_logo2.jpg");
				companyVo.put("v_sign", CmPathInfo.getIMG_PATH()+"footer.gif");
			}
			
			reqVo.put("companyVo", companyVo);
			reqVo.put("i_iVerSeq", prodVo.getString("n_vsn"));
			processVo = this.selectProcessMstInfo(reqVo);
			folderList = this.selectProcessFolderInfo(reqVo);
			
			reqVo.putDefault("i_sRecipeType", "S");
			reqVo.put("i_sConDiv","S".equals(reqVo.getString("i_sRecipeType")) ? "V_CON_CD" : "V_RAW_CD");
			
			conList = this.getSavedConListForPif(reqVo);
			// Product 기본정보
			reqVo.put("prodVo", prodVo);
			// Process Mst
			reqVo.put("processVo", processVo);
			// Process part
			reqVo.put("folderList", folderList);
			// Process component
			reqVo.put("conList", conList);
			
			

			this.setAttachFilePath(companyVo.getString("v_sign_attachid"),"vSignFile",reqVo);
			this.setAttachFilePath(companyVo.getString("v_logo_attachid"),"vLogoFile",reqVo);
	}

	public CmMap selectProcessMstInfo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getObject("BrPw020Mapper.selectProcessMstInfo",reqVo);
	}

	public List<CmMap> selectProcessFolderInfo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectProcessFolderInfo",reqVo);
	}
	public List<CmMap> getSavedConListForPif(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.getSavedConListForPif",reqVo);
	}

	public void coDocMsdsExcel(CmMap reqVo, ModelAndView mav) throws Exception {
		// TODO Auto-generated method stub
		CmMap productInfo = this.getBrPr012Info(reqVo);
		reqVo.put("i_sMatrcd", productInfo.getString("v_product_cd"));
		reqVo.put("i_iVsn", reqVo.getString("i_iVerSeq"));
		
		String companyCd = null;

		companyCd = productInfo.getString("v_vendor_id");

		reqVo.put("i_sCompanyCd", productInfo.getString("v_vendor_id"));
		
		reqVo.put("productInfo", productInfo);
		
		CmMap companyInfo = this.selectCompanyInfo(reqVo);
		
		if(companyInfo != null) {
			if(CmFunction.isEmpty(companyInfo.getString("v_logo"))){
				reqVo.put("i_sLogo_flag", "N");
				companyInfo.put("v_logo", "no_logo2.jpg");
			}
			
			if(CmFunction.isEmpty(companyInfo.getString("v_sign"))){
				reqVo.put("i_sSign_flag", "N");
				companyInfo.put("v_sign", "no_sign2.jpg");
			}
		} else {
			companyInfo = new CmMap();
			reqVo.put("i_sLogo_flag", "N");
			reqVo.put("i_sSign_flag", "N");
			
			companyInfo.put("v_logo", "no_logo2.jpg");
			companyInfo.put("v_sign", "no_sign2.jpg");
		}
		
		reqVo.put("companyInfo", companyInfo);
		reqVo.put("v_now_date", CmFunction.getUsDate1(CmFunction.getToday()));
		
		reqVo.put("i_iVerSeq", "1");			
		reqVo.put("uClassCd", "S000001");
		reqVo.put("v_sub_flag", "N");
		
		//SECTION 항목
		List<CmMap> sectionList = this.getSectionList(reqVo);
		reqVo.put("sectionList", sectionList);
		
		reqVo.put("v_sub_flag", "Y");
		List<CmMap> sectionListSub = this.getSectionList(reqVo);
		reqVo.put("sectionList_sub", sectionListSub);
		reqVo.put("i_sDate", CmFunction.getUsDate1(CmFunction.getToday()));

		this.setAttachFilePath(companyInfo.getString("v_sign_attachid"),"vSignFile",reqVo);
		this.setAttachFilePath(companyInfo.getString("v_logo_attachid"),"vLogoFile",reqVo);
	}

}
