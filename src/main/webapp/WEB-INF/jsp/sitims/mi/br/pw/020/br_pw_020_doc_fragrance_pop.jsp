<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_poBody_start.jsp" %>

<script type="text/javascript">
j$(document).ready(function(){
    j$("#close_btn").unbind("click").click(function(event){
        event.preventDefault();
        fn_popClose();
    });
});
</script>
<form name="frm" id="frm" action="" method="post" >
    <table style="width: 100%">
        <colgroup>
            <col width="50%">
            <col width="*">
        </colgroup>
        <tbody>
            <tr>
                <td style="text-align: left;">
		<c:choose>
			<c:when test="${reqVo.i_sLogo_flag eq 'N'}">
				<img alt="" src="${ImgPATH }${companyInfo.v_logo}" width="203" height="74" onerror="fnNoImage(this);">
			</c:when>
			<c:otherwise>
				<img src="${RootODMFullPATH }showImg.do?i_sAttachId=${companyInfo.v_logo_attachid}" width="203" height="74" >
			</c:otherwise>
		</c:choose>
                </td>
                <td style="text-align: right; font-size: 13px;">
					<span>${productInfo.v_company_addr1 }</span><br>
                    <span>${productInfo.v_company_addr2}</span><br>
                    <span>${productInfo.v_company_addr3}</span><br>
                    <span>${productInfo.v_company_nm_en}</span><br>
					<span>Tel : ${companyInfo.v_phone_no}</span><br>
					<span>Fax : ${companyInfo.v_fax}</span>
                </td>
            </tr>
            <tr style="height: 100px;">
                <td colspan="2" style="text-align:center; font-size: 24px;font-weight:bold; line-height:200%;" >
                    <img src="${ImgPATH}line2.png" width="100%"><br>
                    IFRA CERTIFICATE<br>
                    <img src="${ImgPATH}line2.png" width="100%">
                </td>
            </tr>
            <tr style="height:50px;">
            </tr>
            <tr>
                <td colspan="2" style="font-size: 17px; line-height:200%; font-weight:bold;">
                    <span>Fragrance Name : ${productInfo.v_fragrance_nm}</span><br>
                    <span>Fragrance Code : ${productInfo.v_fragrance_cd }</span><br>
                    <span>Application : ${productInfo.v_category }</span>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="font-size: 17px; height:400px;line-height:200%;">
                    <span>We certify that the above fragrance compound complies with the 46th fragrance safety Standards of the International Fragrance Association (IFRA) when it is used in the above application.</span><br>
                    <span>The IFRA Standards regarding use restrictions are standard for the safe use of fragrance compound and are based on an evaluation by independent experts of all toxicological and dermatological data relating to the ingredient concentrations and exposure to the final product and are enforced by the IFRA Scientific Committee.</span><br>
                    <span>Please contact us for clarification or in case any restrictions are potentially problematic for compliance.</span>
                </td>
            </tr>
            <tr style="height:200px;">
                <td></td>
		<c:choose>
			<c:when test="${reqVo.i_sSign_flag eq 'N'}">
				<td style="font-size: 13px;">
					<div style="position: relative;">
						<img src="${ImgPATH }${companyInfo.v_sign}" width="320" height="240" onerror="fnNoImage(this);">
						<p style="margin-top: -171px;margin-left: 100px;color: black;">${reqVo.v_now_date }</p>
						<p style="margin-top: 87px;margin-left: 92px;color: black;margin-bottom: 80px;">Signature : <span style="text-decoration: underline;">${empty productInfo.v_repr_usernm_en? companyInfo.v_repr_usernm_en:productInfo.v_repr_usernm_en}</span></p>
					</div>
				</td>
			</c:when>
			<c:otherwise>
                <td style="font-size: 17px;">
					<span>${reqVo.v_now_date }</span><br>
					Signature : <span style="text-decoration: underline;">${!empty productInfo.v_repr_usernm_en? productInfo.v_repr_usernm_en:companyInfo.v_repr_usernm_en }</span>
					<img src="${RootODMFullPATH }showImg.do?i_sAttachId=${companyInfo.v_sign_attachid}" width="120" height="60"  onerror="fnNoImage(this);">
                </td>
			</c:otherwise>
		</c:choose>
            </tr>
            <tr style="height:20px;"></tr>
            <tr>
                <td style="text-align: left;">
		<c:choose>
			<c:when test="${reqVo.i_sLogo_flag eq 'N'}">
					<img alt="" src="${ImgPATH }${companyInfo.v_logo}" width="203" height="74" onerror="fnNoImage(this);">
			</c:when>
			<c:otherwise>
					<img src="${RootODMFullPATH }showImg.do?i_sAttachId=${companyInfo.v_logo_attachid}" width="203" height="74" >
			</c:otherwise>
		</c:choose>
                </td>
                <td style="text-align: right; font-size: 13px;">
                	<span>${productInfo.v_company_addr1 }</span><br>
                    <span>${productInfo.v_company_addr2}</span><br>
                    <span>${productInfo.v_company_addr3}</span><br>
                    <span>${productInfo.v_companynm_en}</span><br>
                    <span>Tel : ${productInfo.v_company_phone}</span><br>
                    <span>Fax : ${productInfo.v_company_fax}</span>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td colspan="2" style="text-align: right; font-size: 17px; font-weight: bold;">ANNEX : Definition of IFRA Category</td>
            </tr>
            <tr class="sub_table_area">
                <td colspan="2">
	                <table class="pif_part3_pop_pro" style="border: 1px solid #000;">
	                    <colgroup>
	                        <col width="80%">
	                        <col width="20%">
	                    </colgroup>
	                    <tbody>
	                        <tr>
	                            <td style="text-align: center;">Product Type</td>
	                            <td style="text-align: center;">IFRA Category</td>
	                        </tr>
	                        <tr>
	                            <td>Lip Products of all types (solid and liquid lipsticks, balms, clear or colored, etc.), Toys</td>
	                            <td>Class 1</td>
	                        </tr>
	                        <tr>
	                            <td>Deodorant and Antiperspirant Products of all types (pump spray, aerosol spray, stick, roll-on, underarm and body, etc.)</td>
	                            <td>Class 2</td>
	                        </tr>
	                        <tr>
	                            <td>Hydroalcoholic Products applied to recently shaved skin (EDT)</td>
	                            <td>Class 3A</td>
	                        </tr>
	                        <tr>
	                            <td>Hydroalcoholic Products applied to recently shaved skin (Fine Fragrance)</td>
	                            <td>Class 3B</td>
	                        </tr>
	                        <tr>
	                            <td>Eye Products of all types (eye shadow, mascara, eyeliner, eye make-up, etc.)including eye care, Men’s Facial Creams and Balms, Baby Creams, Lotions, Oils</td>
	                            <td>Class 3C</td>
	                        </tr>
	                        <tr>
	                            <td>Tampons</td>
	                            <td>Class 3D</td>
	                        </tr>
	                        <tr>
	                            <td>Hydroalcoholic Products applied to unshaved skin (EDT), Ingredients of Perfume Kits, Scent Pads, Foil packs, Scent Strips for hydroalcoholic products</td>
	                            <td>Class 4A</td>
	                        </tr>
	                        <tr>
	                            <td>Hydroalcoholic Products applied to unshaved skin (Fine Fragrance)</td>
	                            <td>Class 4B</td>
	                        </tr>
	                        <tr>
	                            <td>Hair Styling Aids Sprays of all types (pumps, aerosol sprays, etc.), Body Creams, Oils, Lotions of all types (except baby creams, lotions, oils), Fragrance Compounds for Cosmetic Kits, Foot Care Products, Hair deodorant,</td>
	                            <td>Class 4C</td>
	                        </tr>
	                        <tr>
	                            <td>Fragrancing cream</td>
	                            <td>Class 4D</td>
	                        </tr>
	                        <tr>
	                            <td>Women’s Facial Creams/Facial Make-up, Hand Cream, Facial Masks, Baby Powder and Talc, Hair permanent and other hair chemical treatments (e.g. relaxers) but not hair dyes, Wipes or Refreshing tissues for face, neck, hands and body</td>
	                            <td>Class 5</td>
	                        </tr>
	                        <tr>
	                            <td>Mouthwash, Toothpaste</td>
	                            <td>Class 6</td>
	                        </tr>
	                        <tr>
	                            <td>Intimate Wipes, Baby Wipes</td>
	                            <td>Class 7A</td>
	                        </tr>
	                        <tr>
	                            <td>Insect Repellent (intended to be applied to the skin)</td>
	                            <td>Class 7B</td>
	                        </tr>
	                        <tr>
	                            <td>Make-up Removers of all types (not including face cleansers), Hair Styling Aids Non-Spray of all types (mousse, gels, leave-in conditioners, etc.), Nail Care, Powders and talcs (not including baby powders and talcs)</td>
	                            <td>Class 8A</td>
	                        </tr>
	                        <tr>
	                            <td>Hair Dyes</td>
	                            <td>Class 8B</td>
	                        </tr>
	                        <tr>
	                            <td>Conditioner (Rinse-Off), Liquid Soap, Shampoos of all types (including baby shampoos), Face Cleansers of all types (washes, gels, scrubs, etc.), Shaving Creams of all types (stick, gels, foams, etc.), Depilatory, Body Washes of all types (including baby washes) and Shower Gels of all types, Bar Soap (Toilet Soap), Bath Gels, Foams, Mousses, Salts, Oils and Other Products added to bathwater</td>
	                            <td>Class 9A</td>
	                        </tr>
	                        <tr>
	                            <td>Feminine hygiene - pads, liners, Toilet paper</td>
	                            <td>Class 9B</td>
	                        </tr>
	                        <tr>
	                            <td>Facial tissues, Napkins, Paper towels, Other' Aerosols (incl. air freshener sprays but not deodorants/ antiperspirants, hair styling aids spray and animal sprays)</td>
	                            <td>Class 9C</td>
	                        </tr>
	                        <tr>
	                            <td>Handwash Laundry Detergents of all types, Fabric Softeners of all types including fabric softener sheets, Other Household Cleaning Products (fabric cleaners, soft surface cleaners, carpet cleaners, etc.), Machine W ash Laundry Detergents (liquids, powders, tablets, etc.) including laundry bleaches, Hand Dishwashing Detergent, Hard Surface Cleaners of all types (bathroom and kitchen cleansers, furniture polish, etc.), Shampoo for pets, Dry cleaning kits</td>
	                            <td>Class 10A</td>
	                        </tr>
	                        <tr>
	                            <td>Diapers, Toilet seat wipes</td>
	                            <td>Class 10B</td>
	                        </tr>
	                        <tr>
	                            <td>All non-skin contact including Candles, Air Fresheners and Fragrancing of all types (plug-ins, solid substrate, membrane delivery, ambient, electrical..) excluding aerosol products, Liquid refills for air fresheners (cartridge systems), Insecticides (mosquito coil, paper, electrical, for clothing etc.) excluding aerosols Toilet Blocks, Joss Sticks, Incense, Machine only Laundry detergent (e.g. liquitabs), Plastic articles (excluding toys), Fuels, Fragranced lamp ring, Scent pack, Paints, Floor wax</td>
	                            <td>Class 11A</td>
	                        </tr>
	                        <tr>
	                            <td>All incidental skin contact including: Pot pourri, fragrancing sachets, liquid refills for air fresheners (non-cartridge systems), Reed diffusers, Shoe Polishes, Deodorizers/Maskers not intended for skin contact (e.g. fabric drying machine deodorizers, carpet powders), Machine dish-wash detergent and deodorizers, Scratch and Sniff (sampling technology), Cat litter, Animal sprays (all types), Treated Textiles (e.g. starch sprays, fabric treated with fragrances after wash, deodorizers for textiles or fabrics, tights with moisturizers), Odored distilled water (that can be added to steam irons)</td>
	                            <td>Class 11B</td>
	                        </tr>
	                    </tbody>
	                </table>
                </td>
            </tr>
        </tbody>
    </table>
    <div class="pd_top10"></div>
    <ul class="btn_area">
        <li class="right">
            <a class="btnA bg_dark" href="#none" id="close_btn"><span>닫기</span></a>
        </li>
    </ul>
</form>

<style>
.pif_part3_pop_pro td{padding:3px; border:1px solid;bordercolor:black; text-align: left;}
</style>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>