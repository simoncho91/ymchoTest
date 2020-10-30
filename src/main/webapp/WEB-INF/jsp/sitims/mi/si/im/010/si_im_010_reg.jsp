<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_start.jsp" %>

<style>
	div.dhx_window__inner-html-content{
		width:100%;
		height:100%;
	}
</style>
<script type='text/javascript'>
var _duplicateChk = false;
 $(function(){
	$('.btn_list').on('click',function(){		
		window.location.href='/si/im/010/init.do?openMenuCd=MISIIM010';
	});
	$('.btn_tmpsave').on('click',function(){
		console.log('임시저장');
		fn_save('tmp');
	});
	$('.btn_save').on('click',function(){
		console.log('저장');
		fn_save();
	});

	$('.tab_view').on('click',function(){
		var tabId=$(this).attr('id').split('_')[1];
		if(Number($('#i_nMaxVerSeq').val())+1 ==tabId) return;
		window.location.href='/si/im/010/si_im_010_view.do?i_sConCd='+$('#i_sConCd').val()+"&i_nVerSeq="+tabId;
	});
	
	$('#btnCasTblAdd').on('click',function(){
	//	$('#subCasTable').append('<tr><td><input type="text" id="i_arrCasNo" name="i_arrCasNo" style="width: 90%;"/></td><td><input type=\'button\' id=\'btnCasTblRemove\' value=\'삭제\' /></td></tr>');
		$('#subCasTable').append('<tr><td><input type="text" class="inp_sty01" id="i_arrCasNo" name="i_arrCasNo" style="width: 90%;"/></td><td class="center"><button class="dhx_button"  id="btnCasTblRemove" type="button"><span class="dxi dxi-minus"></span></button></td></tr>');
	});
	
	$(document).on('click',"#btnCasTblRemove",function(){
		if($(this).parent().parent().hasClass('first')){
			$(this).parent().parent().find('#i_arrCasNo').val('');
		}else{
			$(this).parent().parent().remove();
		}
	});
	
	$('#btnFuncAdd').on('click',function(){
		var country = ['En','Ko','Cn'];
    	var	copy_row	= $("#subFuncTbl .tr_value").eq(0).clone(true);
    	$("#subFuncTbl tbody").append(copy_row.removeClass('first'));

	});
	$(document).on('click',"#btnFuncRemove",function(){
		if($(this).parent().parent().hasClass('first')){
			$(this).parent().parent().find('#i_arrCasNo').val('');
		}else{
			$(this).parent().parent().remove();
		}
	});

	$('input[name=i_sAutoYn]').on("click", function () {
		if($(this).val() == 'N') {
			$('#chk_dup').show();
			 $('#i_sConCd').val('');
			 $('#i_sConCd').attr('disabled',false);
		} else {
			$('#chk_dup').hide();
			 $('#i_sConCd').val('');
			 $('#i_sConCd').attr('disabled',true);
		}
	});
	
	$('#chk_dup').on('click',fn_chkDup);
	
	$('input[name=i_sFlag]').on("click", function () {
		if($(this).val() == 'O') {
			$('#chk_search').show();
// 			$('#btnCasTblAdd').hide();
// 			$('#subCasTable tr').each(function(i,obj){
// 				if(!$(obj).hasClass('header'))$(obj).remove();
// 			});
// 			$('#subCasTable').html('');
		}else{
			$('#chk_search').hide();	
// 			$('#btnCasTblAdd').show();	
// 			$('#subCasTable tr').each(function(i,obj){
// 				if(!$(obj).hasClass('header'))$(obj).remove();
// 			});
// 			$('#subCasTable');
		} 
	});
	$('#chk_search').on("click", function () {
		fn_pop({url:'/si/im/010/si_im_010_reg_pop.do',title:'성분 등록 요청목록',width:'700',height:'600'});
		//fn_pop({url:'/br/pr/010/br_pr_010_prod_list_pop.do?i_sCmFunction=setPopUpData&i_sInput=퍼펙트클렌징폼',title:'ODM 성분 요청목록',width:'700',height:'600'});
 	});	
	$('#tab_versionAdd').on('click',function(){
		$("#tab_view #ul_tab .tab .a").removeClass('on');
    	var	copy_row	= $("#tab_view #ul_tab .tab").eq(0).clone(true);
    	var maxVerSeq = Number($('#i_nMaxVerSeq').val()) + 1;
    	copy_row.attr("id","tab_"+maxVerSeq);
    	copy_row.html("<a href=\"#none\"><span>"+maxVerSeq+"</span></a>");    	
    	$("#tab_view #ul_tab .tab_button").before(copy_row.find('a').addClass('on'));
    	
    	$('#i_nMaxVerSeq').val(maxVerSeq);
    	$('#i_sFlagNewwVer').val("Y");    	
    	
    	$('#tab_versionAdd').hide();
		$('.btn_tmpsave').show();
		$('.btn_save').show();
		//toolbar.show(["btn_tmpsave","btn_save"]);
	});
	
	$('#i_sZcert').on('change',function(e){
		console.log(e);
		if($(this).prop('checked')){
			$('#i_sFlagBanLKo').prop('checked',true);
		}
	});
	

	$('form[name="frm"] textarea').each(function(){
		fnMsgLength(this, 'len_' + this.id, 2000);
	});

	$('form[name="frm"] textarea').on('keyup',function(e){
		fnMsgLength(this, 'len_' + this.id, 2000);
	});
	
	//사용금지 check시 textarea disabled true/false
	$('form[name="frm"]').find('input[name=i_arrFlagBanB]').on('change',function(e){
		var id = $(this).attr('id');
		//id = id=='i_sFlagBanBCn'?'i_sFlagBanBCh':id;
		if($(this).is(':checked')){
			$('textarea[id='+id+'Coment]').attr('disabled',false);
		}else{
			$('textarea[id='+id+'Coment]').val('');
			$('textarea[id='+id+'Coment]').attr('disabled',true);			
		}
	});
	//배합제한 check시 textarea disabled true/false
	$('form[name="frm"]').find('input[name=i_arrFlagBanL]').on('change',function(e){
		var id = $(this).attr('id');
		//id = id=='i_sFlagBanLCn'?'i_sFlagBanLCh':id;
		if($(this).is(':checked')){
			$('textarea[id='+id+'Coment]').attr('disabled',false);
		}else{
			$('textarea[id='+id+'Coment]').val('');
			$('textarea[id='+id+'Coment]').attr('disabled',true);			
		}
	});
	
	//배합목적 영문 변경시 국문/중문 변경
	$('form[name="frm"]').find('select[name=i_arrFuncCdEn]').on('change',function(){
		var	idx	= $('select[name=i_arrFuncCdEn]').index(this);
		var val = $(this).val();
		var koText = $('select[name=i_arrFuncCdKo]').eq(idx).find('option[value='+val+']').text()
		$('select[name=i_arrFuncCdKo]').eq(idx).val(val);		
		$('select[name=i_arrFuncCdCn]').eq(idx).val(val);
			
		if(koText=='살균 보존제' || koText =='보존제'){
			$('input[name=i_sMateViewYn]').val('Y');
		}else{
			$('input[name=i_sMateViewYn]').val('');
		}
	});
	$('form[name="frm"]').find('select[name=i_arrFuncCdKo]').on('change',function(){
		var val = $(this).val();
		var text = $(this).find(':selected').text();	
		if(text=='살균보존제' || text =='보존제'){
			$('input[name=i_sMateViewYn]').val('Y');
		}else{
			$('input[name=i_sMateViewYn]').val('');
		}
	});
	
	fn_init();                                                           
	                                                                       
	var reqConCd = '${reqVo.i_sReqConId}';                                   
	if(!fn_isNull(reqConCd)){                                              
		var reqData = {                                                    
				v_inci_nm_ko 	:	$('#i_sReqInciNmKo').val()
				, v_inci_nm_en  :   $('#i_sReqInciNmEn').val()
				, v_req_con_id  :   reqConCd
				, CAS_NO_ROW    :   $('#i_sReqCasNoRow').val()
		}
		setPopUpData(reqData,true);
	}

	$('.btn_del').on('click',function(){
		function callback(){
			//$('#frm').attr("action","/si/im/010/init.do").submit();
			location.href = "/si/im/010/init.do?openMenuCd=MISIIM010"
		}
		fn_s_saveMessage('성분DB 삭제',"삭제하시겠습니까?","/si/im/010/si_im_010_del.do",$('#frm').serialize(),callback);
	});
 });
 
 function fn_chkDup(){
	var vConCd = $('#i_sConCd').val();
	if(vConCd == '' || vConCd == null){
		fn_s_alertMsg("코드를 입력하세요.");
		return;
	}
	$.ajax({
		url : '/si/im/010/chkDupliConCd.do'
		,data : {'i_sConCd':vConCd}
		,type : 'POST'
		,dataType : 'json'
		,success : function(responseData){
			if(responseData.result.data.count > 0){
				fn_s_alertMsg("[ "+vConCd+" ] 중복된 코드입니다.");
				_duplicateChk= false;				
			}else{
				fn_s_alertMsg("사용 가능한 코드 입니다.");
				_duplicateChk= true;
			}
		},error : function(jqXHR, textStatus, errorThrown){
	        fn_s_failMsg(jqXHR, textStatus, errorThrown);
		}                                                                 
	});         
 }
 
 function setPopUpData(data,popChk){
	//cmDialogClose("si_im_010_reg_pop");
	if(fn_isNull(popChk)|| !popChk ){
		fn_popClose();
	}else{
		$('#i_sOdm').click();
	}

	$('#i_sReqConId').val(data.v_req_con_id);
	$('#i_sConNmEn').val(data.v_inci_nm_en);
	$('#i_sConNmKo').val(data.v_inci_nm_ko);
	
	if(data.CAS_NO_ROW == "") return;
	var casNo = data.CAS_NO_ROW.split(',');
	$('#subCasTable tr').each(function(i,obj){
		if(!$(obj).hasClass('header')) $(obj).remove();
	});
	casNo.forEach(function(name,i){
		var sTr = $('<tr></tr>');
		
		var sTd = $('<td></td>');
		sTd.append('<span>'+name+' </span>');
		sTd.append('<input type="hidden" style="width:500px;" id="i_arrCasNo" name="i_arrCasNo" value='+name+' />');
		sTr.append(sTd);

		var sTdButton = $('<td class="center"></td>');
		//sTdButton.append('<input type=\'button\' id=\'btnCasTblRemove\' class=\'btnCasTblRemove\' value=\'삭제\' />');
		sTdButton.append("<button class=\"dhx_button\"  id='btnCasTblRemove' type=\"button\"><span class=\"dxi dxi-minus\"></span></button>");
		sTr.append(sTdButton);
		$('#subCasTable').append(sTr);		
		
	});
		
 }
 
 function closePopUp(){
	cmDialogClose("si_im_010_reg_pop");
 }
 function fn_init(){
	 var confirmYn = $('#i_sConfirmYn').val();
	 var sFlagNewwVer = $('#i_sFlagNewwVer').val();
	 
	 if(confirmYn == "Y"){
		 console.log('$(.btn_tmpsave).hide()');
		$('.btn_tmpsave').hide();
		$('.btn_save').hide();
		$('.btn_del').hide();		
		//toolbar.hide(["btn_tmpsave","btn_save"]);
	 }
	 if($('input[name="i_sAutoYn"]').val()=='Y'){		
		 $('#i_sConCd').val('');
		 $('#i_sConCd').attr('disabled',true);
	}
 }
 function fn_validation(){
	 if(${empty rVo.v_con_cd } && $('input[name="i_sAutoYn"]:checked').val() !='Y' && !_duplicateChk){
		 fn_s_alertMsg('중복된 성분코드이거나 값이 없습니다.');
		 $('input[name="i_sAutoYn"]').focus();
		 return false;
// 	 }else if(fn_s_inputLengthChk($('#i_sConCd').val(),'성분코드',10)){
// 		return false;
	 }else if(fn_isNotNull($('#i_sConCd').val()) && fn_s_inputOnlyEnNumChk($('#i_sConCd').val(),'성분코드')){
		return false;
	 }
	 
	 if(Number($('#i_nMaxAllowWt').val()) > 100 || Number($('#i_nMaxAllowWt').val()) < 0 || fn_isNull($('#i_nMaxAllowWt').val())){
		 fn_s_alertMsg('배합한도의 값을 확인해주세요.');
		 $('#i_nMaxAllowWt').focus();
		 return false;
	 }
	 if(fn_isNull($('#i_sConNmEn').val())){
		 fn_s_alertMsg('성분명[영어]를 확인해주세요.');
		 $('#i_sConNmEn').focus();
		 return false;		 
	 }
	 if(fn_s_inputLengthChk($('#i_sConNmEn').val(),'성분명(영어)',200)){
		return false;
	 }
	 if(fn_isNull($('#i_sConNmKo').val())){
		 fn_s_alertMsg('성분명[한글]를 확인해주세요.');
		 $('#i_sConNmEn').focus();
		 return false;
	 }	 
	 if(fn_s_inputLengthChk($('#i_sConNmKo').val(),'성분명(한글)',200)){
		$('#i_sConNmKo').focus();
		return false;
	 }
	 
	 if(fn_s_inputLengthChk($('#i_sConNmCn').val(),'성분명(중문)',200)){
		$('#i_sConNmCn').focus();
		return false;
	 }
	 if(fn_s_inputLengthChk($('#i_sConNmEu').val(),'성분명(유럽)',200)){
		$('#i_sConNmEu').focus();
		return false;
	 }
	 if(fn_s_inputLengthChk($('#i_sConNmJp').val(),'성분명(일문)',200)){
		$('#i_sConNmJp').focus();
		return false;
	 }
 
	 var errFlag = false;
	 $('input[name=i_arrCasNo]').each(function(i,obj){
		 if(!errFlag &&  fn_isNull($(obj).val().trim())){
			 fn_s_alertMsg('Cas No 데이터를 입력해주세요.');
			 $(obj).focus();
			 errFlag=true;
			 return;
		 }
		 if(!errFlag &&  fn_s_inputKoChk($(obj).val().trim(),'Cas No')){
			 $(obj).focus();
			 errFlag=true;
			 return;
		 }
		 if(!errFlag &&  fn_s_inputLengthChk($(obj).val().trim(),'Cas No',50)){
			 $(obj).focus();
			 errFlag=true;
			 return;
		 }
	 });

	 if(errFlag){
		 return false;
	 }	 
	 if(!fn_isNull($('#i_sFlagBanLKo:checked').val()) && fn_isNull($('#i_sFlagBanLKoComent').val().trim())){
		 fn_s_alertMsg('배합제한[국내]를 체크 및 데이터 입력해주세요.');
		 $('#i_sFlagBanLKoComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanLKo:checked').val()) && fn_s_inputLengthChk($('#i_sFlagBanLKoComent').val().trim(),'배합제한[국내]',2000)){		 
		 $('#i_sFlagBanLKoComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanLCn:checked').val()) && fn_isNull($('#i_sFlagBanLCnComent').val().trim())){
		 fn_s_alertMsg('배합제한[중국]를 체크 및 데이터 입력해주세요.');
		 $('#i_sFlagBanLCnComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanLCn:checked').val()) && fn_s_inputLengthChk($('#i_sFlagBanLCnComent').val().trim(),'배합제한[중국]',2000)){		 
		 $('#i_sFlagBanLCnComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanLAe:checked').val()) && fn_isNull($('#i_sFlagBanLAeComent').val().trim())){
		 fn_s_alertMsg('배합제한[아세안]를 체크 및 데이터 입력해주세요.');
		 $('#i_sFlagBanLAeComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanLAe:checked').val()) && fn_s_inputLengthChk($('#i_sFlagBanLAeComent').val().trim(),'배합제한[아세안]',2000)){		 
		 $('#i_sFlagBanLAeComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanLEu:checked').val()) && fn_isNull($('#i_sFlagBanLEuComent').val().trim())){
		 fn_s_alertMsg('배합제한[유럽]를 체크 및 데이터 입력해주세요.');
		 $('#i_sFlagBanLEuComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanLEu:checked').val()) && fn_s_inputLengthChk($('#i_sFlagBanLEuComent').val().trim(),'배합제한[유럽]',2000)){		 
		 $('#i_sFlagBanLEuComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanLUs:checked').val()) && fn_isNull($('#i_sFlagBanLUsComent').val().trim())){
		 fn_s_alertMsg('배합제한[미국]를 체크 및 데이터 입력해주세요.');
		 $('#i_sFlagBanLUsComent').focus();
		 return false;		 
	 }	 
	 if(!fn_isNull($('#i_sFlagBanLUs:checked').val()) && fn_s_inputLengthChk($('#i_sFlagBanLUsComent').val().trim(),'배합제한[미국]',2000)){		 
		 $('#i_sFlagBanLUsComent').focus();
		 return false;		 
	 }

	 if(!fn_isNull($('#i_sFlagBanBKo:checked').val()) && fn_isNull($('#i_sFlagBanBKoComent').val().trim())){
		 fn_s_alertMsg('사용금지[국내]를 체크 및 데이터 입력해주세요.');
		 $('#i_sFlagBanBKoComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanBKo:checked').val()) && fn_s_inputLengthChk($('#i_sFlagBanBKoComent').val().trim(),'사용금지[국내]',2000)){		 
		 $('#i_sFlagBanBKoComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanBCn:checked').val()) && fn_isNull($('#i_sFlagBanBCnComent').val().trim())){
		 fn_s_alertMsg('사용금지[중국]를 체크 및 데이터 입력해주세요.');
		 $('#i_sFlagBanBCnComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanBCn:checked').val()) && fn_s_inputLengthChk($('#i_sFlagBanBCnComent').val().trim(),'사용금지[중국]',2000)){		 
		 $('#i_sFlagBanBCnComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanBAe:checked').val()) && fn_isNull($('#i_sFlagBanBAeComent').val().trim())){
		 fn_s_alertMsg('사용금지[아세안]를 체크 및 데이터 입력해주세요.');
		 $('#i_sFlagBanBAeComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanBAe:checked').val()) && fn_s_inputLengthChk($('#i_sFlagBanBAeComent').val().trim(),'사용금지[아세안]',2000)){		 
		 $('#i_sFlagBanBAeComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanBEu:checked').val()) && fn_isNull($('#i_sFlagBanBEuComent').val().trim())){
		 fn_s_alertMsg('사용금지[유럽]를 체크 및 데이터 입력해주세요.');
		 $('#i_sFlagBanBEuComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanBEu:checked').val()) && fn_s_inputLengthChk($('#i_sFlagBanBEuComent').val().trim(),'사용금지[유럽]',2000)){		 
		 $('#i_sFlagBanBEuComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanBUs:checked').val()) && fn_isNull($('#i_sFlagBanBUsComent').val().trim())){
		 fn_s_alertMsg('사용금지[미국]를 체크 및 데이터 입력해주세요.');
		 $('#i_sFlagBanBUsComent').focus();
		 return false;		 
	 }
	 if(!fn_isNull($('#i_sFlagBanBUs:checked').val()) && fn_s_inputLengthChk($('#i_sFlagBanBUsComent').val().trim(),'사용금지[미국]',2000)){		 
		 $('#i_sFlagBanBUsComent').focus();
		 return false;		 
	 }

	 $('select[name=i_arrFuncCdEn]').each(function(i,obj){
		 if(!errFlag && $('input[name="i_sConfirmYn"]').val()=='Y' && fn_isNull($(obj).val())){
			 fn_s_alertMsg('배합목적[영문]를 선택해주세요.');
			 errFlag=true;
			 return false;
		 } 
	 });
	 $('select[name=i_arrFuncCdKo]').each(function(i,obj){
		 if(!errFlag && $('input[name="i_sConfirmYn"]').val()=='Y' && fn_isNull($(obj).val())){
			 fn_s_alertMsg('배합목적[국문]를 선택해주세요.');
			 errFlag=true;
			 return false;
		 } 
	 });
	 $('select[name=i_arrFuncCdCn]').each(function(i,obj){
		 if(!errFlag && $('input[name="i_sConfirmYn"]').val()=='Y' && fn_isNull($(obj).val())){
			 fn_s_alertMsg('배합목적[중문]를 선택해주세요.');
			 errFlag=true;
			 return false;
		 } 
	 });
	 if(errFlag){
		 return false;
	 }	 
	 return true;
 }
 
 function fn_save(mode){	 
	 var i_sConfirmYn = (mode=='tmp'?'N':'Y');
	 $('input[name="i_sConfirmYn"]').val(i_sConfirmYn);
	 if(!fn_validation()) return;	 
	 if($('input[name="i_arrFlagBanB"]:checked').length>1){
		 $('input[name=\'i_sFlagBanB\']').val('GL');
	 }else{
		 $('input[name=\'i_sFlagBanB\']').val($('input[name="i_arrFlagBanB"]:checked').val());
	 }
	 if($('input[name="i_arrFlagBanL"]:checked').length>1){
		 $('input[name=\'i_sFlagBanL\']').val('GL');
	 }else{
		 $('input[name=\'i_sFlagBanL\']').val($('input[name="i_arrFlagBanL"]:checked').val());
	 }
	 if(!fn_isNull($('#i_sFlagBanLKo:checked').val())){
		 $('#i_sZcert').val('Y');
	 }

	 var postParam = $('#frm').serialize();

	function callback(data){
		location.replace('/si/im/010/si_im_010_view.do?i_sConCd='+data.data.i_sConCd+'&i_nVerSeq='+data.data.i_nVerSeq+'&i_sFlagNewwVer=');
	}
	fn_s_saveMessage('성분DB 등록',"성분DB를 등록하시겠습니까?","/si/im/010/si_im_010_save.do",postParam,callback);	 
// 	 $.ajax({
// 	     url: "/si/im/010/si_im_010_save.do",
// 	     data: postParam,
// 	     type: "POST",
// 	     dataType: "json",
// 	     success:function(data){
//     	 },error : function(jqXHR, textStatus, errorThrown){
// 	        fn_s_failMsg(jqXHR, textStatus, errorThrown);
// 		}
//      });
 }
</script>
<script type='text/javascript'>
	var layoutMain,toolbar;
	$(function(){
// 		layoutMain = new dhx.Layout("layoutToolbar",{
// 			css: "dhx_layout-cell--bordered",
// 			rows:[
// 					{
// 					rows: [
// 							{ id: "toolbarMain", gravity: false }
// 						]
// 					,resizable: true
// 					}
// 			]
// 		});

		// Form-툴바    
// 		toolbar = new dhx.Toolbar(null, {
// 		//	css: "dhx_widget--bordered dhx_widget--bg_gray",
// 			data: [
// 				 {	type: "spacer"}
// 				,{	id: "btn_tmpsave",icon: "dxi dxi-check",value: "저장", css: "btn_tmpsave" 	}
// 				,{	id: "btn_save",icon: "dxi dxi-checkbox-marked-circle",value: "확정", css: "btn_save"	}
// 				,{	id: "btn_list",icon: "dxi dxi-view-sequential",value: "목록", css: "btn_list"	}
// 			]
// 		});
// 		toolbar.events.on("Click", function(id, e){
// 			if(id == "btn_tmpsave"){
// 				fn_save('tmp');
// 			}else if(id == "btn_save"){
// 				fn_save();
// 			}else if(id == "btn_list"){
// 				window.location.href='/si/im/010/init.do?openMenuCd=MISIIM010';
// 			}
// 		});
// 		layoutMain.getCell("toolbarMain").attach(toolbar);

	});
</script>

<form name="frm" id="frm" method="post">
	<input type="hidden" name="i_sFlagBanB" id ="i_sFlagBanB" value="${rVo.v_flag_ban}" />
	<input type="hidden" name="i_sFlagBanL" id ="i_sFlagBanL" value="${rVo.v_flag_permit}" />
	<input type="hidden" name="i_sConfirmYn" id ="i_sConfirmYn" value="${rVo.v_confirm_yn}" />
	<input type="hidden" name="i_nVerSeq" id ="i_nVerSeq" value="${rVo.n_ver_seq}" />
	<input type="hidden" name="i_sReqConId" id ="i_sReqConId" value="${empty rVo.v_req_con_id?reqVo.i_sReqConId:rVo.v_req_con_id}" />
	<input type="hidden" name="i_nMaxVerSeq" id ="i_nMaxVerSeq" value="${rVo.n_max_ver_seq}" />
	<input type="hidden" name="i_sFlagNewwVer" id ="i_sFlagNewwVer" value="${reqVo.i_sFlagNewwVer}" />	
	<input type="hidden" name="i_sZcert" id="i_sZcert" value="${rVo.v_zcert }" />
	<input type="hidden" name="i_sMateViewYn" id="i_sMateViewYn" value="${rVo.v_mateview_yn }" />
		
	<c:if test="${!empty conInfo}">
		<input type="hidden" name="i_sReqInciNmKo" id ="i_sReqInciNmKo" value="${conInfo.v_inci_nm_ko}" />
		<input type="hidden" name="i_sReqInciNmEn" id ="i_sReqInciNmEn" value="${conInfo.v_inci_nm_en}" />
		<input type="hidden" name="i_sReqCasNoRow" id ="i_sReqCasNoRow" value="${conInfo.CAS_NO_ROW}" />
	</c:if>
		
	<!-- 버전 탭 뷰 -->
	<c:if test="${!empty reqVo.i_sConCd }">	
		<div id='tab_view'>
			<ul id="ul_tab" class="sty_tab">
				<c:forEach var="i" begin="1" end="${rVo.n_max_ver_seq }">
					<li id='tab_${i}' class="tab tab_view"><a href="#none" class="<c:if test="${rVo.v_confirm_yn eq 'N' and (i eq rVo.n_max_ver_seq)}">on</c:if>"><span>${i}</span></a></li>
				</c:forEach>
				
				<c:if test="${reqVo.i_sFlagNewwVer eq 'Y' }">
					<li id='tab_${rVo.n_max_ver_seq+1 }' class="tab tab_view"><a href="#none" class="on"><span>${rVo.n_max_ver_seq+1 }</span></a></li>
				</c:if>
			</ul>
		</div>
	</c:if>
	<!-- 버전 탭 뷰 -->
	<div class="top_btn_area" style="z-index:1;">
		<c:if test="${!empty reqVo.i_sConCd and empty reqVo.i_sFlagNewwVer}">
			<a href="#none" class="btnA bg_dark btn_del"><span>삭제</span></a>
		</c:if>
		<a style="cursor: pointer;" class="btnA bg_dark btn_tmpsave"><span>임시저장</span></a>
		<a style="cursor: pointer;" class="btnA bg_dark btn_save"><span>확정</span></a>
		<a style="cursor: pointer;" class="btnA bg_dark btn_list"><span>목록</span></a>
	</div>
	
	<table class="sty_02">
		<colgroup>
			<col width="15%"/>
			<col width="35%"/>
			<col width="15%"/>
			<col width="35%"/>
		</colgroup>
		<tr>
			<th>*성분코드</th>
			<td>
				<c:if test="${empty rVo.v_con_cd }">
					<input type="number" name="i_sConCd" id ="i_sConCd" value="${rVo.v_con_cd}" class="inp_sty01" style="margin-right:5px;" required />
					<input type="radio" id="i_sAutoNo" name="i_sAutoYn" value="Y" checked /> <label for="i_sAutoNo" style="cursor:pointer;margin-right:5px;" >자동채번</label>
	                <input type="radio" id="i_sManuNo" name="i_sAutoYn" value="N"/> <label for="i_sManuNo" style="cursor:pointer">수동채번</label>
					<a class="btnA bg_dark btn_sap" style="display: none" id="chk_dup"><span>중복체크</span></a>
				</c:if>
				<c:if test="${!empty rVo.v_con_cd}">
					<input type="hidden" name="i_sConCd" id ="i_sConCd" value="${rVo.v_con_cd}"></input>
					<span>${rVo.v_con_cd}</span>
				</c:if>
			</td>
			<th>알러젠 여부</th>
			<td class="last">			
				<input type="checkbox" name="i_sAllergenYn" id="i_sAllergenYn" value='Y' ${rVo.v_allergen_yn eq 'Y' ? 'checked=\'checked\'' : '' } />
			</td>			
		</tr>
		<tr>
			<th>요청구분</th>
			<td>
				<c:choose>
<%-- 					<c:when test="${(!empty rVo.v_con_cd and rVo.v_confirm_yn eq 'Y') or reqVo.i_sFlagNewwVer eq 'Y'}"> --%>
					<c:when test="${(empty rVo.n_ver_seq or rVo.n_ver_seq eq '1') and reqVo.i_sFlagNewwVer ne 'Y'}">
						<input type="radio" id="i_sOdm" name="i_sFlag" value="O" ${rVo.v_flag eq 'O' ? 'checked=\'checked\'' : (rVo.v_flag eq 'S' ? '' : 'checked=\'checked\'') } /> <label for="i_sOdm" style="cursor:pointe;margin-right:5px;r">ODM</label>
		                <input type="radio" id="i_sSi" name="i_sFlag" value="S" ${rVo.v_flag eq 'S' ? 'checked=\'checked\'' : '' } /> <label for="i_sSi" style="cursor:pointer;margin-right:5px;">SI</label>
		                <a class="btnA bg_dark btn_sap" style="display: ${(rVo.v_flag ne 'S') ? 'inline-block' : 'none' }" id="chk_search"><span>찾기</span></a>						
					</c:when>
					<c:otherwise>
						<input type="hidden" id="i_sFlag" name="i_sFlag" value="${rVo.v_flag }"/>
						<span>${rVo.v_flag eq 'O' ? 'ODM' : 'SI'}</span>
					</c:otherwise>
				</c:choose>
			</td>
			<th>배합한도</th>
			<td>
				<input type='number' id='i_nMaxAllowWt' name='i_nMaxAllowWt' class="inp_sty01" value="${rVo.n_max_allow_wt}"></input> %
			</td>
		</tr>
		<tr>
			<th>색소여부</th>			
			<td><input type="checkbox" name="i_sCiYn" id="i_sCiYn" value='Y' ${rVo.v_ci_yn eq 'Y' ? 'checked=\'checked\'' : '' } /></td>
<%-- 			<th>필수 서류여부</th>			
			<td><input type="checkbox" name="i_sZcert" id="i_sZcert" value='Y' ${rVo.v_zcert eq 'Y' ? 'checked=\'checked\'' : '' } /></td>
 --%>		
			<th>표시성분 여부</th>
			<td>
				<input type="checkbox" name="i_sDisplayYn" id="i_sDisplayYn" value='Y' ${rVo.v_display_yn eq 'Y' ? 'checked=\'checked\'' : '' } />
			</td>
		</tr>
		<tr>
<!-- 			<th>함량표시 여부</th>			 -->
<!-- 			<td colspan='3'> -->
<%-- 				<input type="checkbox" name="i_sMateViewYn" id="i_sMateViewYn" value='Y' ${rVo.v_mateview_yn eq 'Y' ? 'checked=\'checked\'' : '' } /> --%>
<!-- 			</td> -->
<%-- 			<th>표시성분 여부</th>
			<td colspan='3'>
				<input type="checkbox" name="i_sDisplayYn" id="i_sDisplayYn" value='Y' ${rVo.v_display_yn eq 'Y' ? 'checked=\'checked\'' : '' } />
			</td> --%>
		</tr>
		<tr>
			<th>성분명</th>
			<td>
				<table class="table_view">
					<colgroup>
						<col width="7%"/>
						<col width="43%"/>
					</colgroup>
					<tr>
						<th>*영어</th>						
						<td><input class="inp_sty01" style="width:94%;" type='text' id='i_sConNmEn' name='i_sConNmEn' value='${rVo.v_con_nm_en}' required /></td>
					</tr>
					<tr>
						<th>*한글</th>
						<td><input class="inp_sty01" style="width:94%;" type='text' id='i_sConNmKo' name='i_sConNmKo' value='${rVo.v_con_nm_ko}' required /></td>
					</tr>
					<tr>
						<th>중문</th>
						<td><input class="inp_sty01" style="width:94%;" type='text' id='i_sConNmCn' name='i_sConNmCn' value='${rVo.v_con_nm_cn}' /></td>
					</tr>
					<tr>
						<th>유럽</th>
						<td><input class="inp_sty01" style="width:94%;" type='text' id='i_sConNmEu' name='i_sConNmEu' value='${rVo.v_con_nm_eu}' /></td>
					</tr>
					<tr>
						<th>일문</th>
						<td><input class="inp_sty01" style="width:94%;" type='text' id='i_sConNmJp' name='i_sConNmJp' value='${rVo.v_con_nm_jp}' /></td>
					</tr>
				</table>
			</td>
			<th>*Cas No</th>
			<td>
				<table id='subCasTable' style="width:100%;"  class="table_view">
					<colgroup>
						<col width="80%"/>
						<col width="20%"/>
					</colgroup>
					<tr class='header'>
						<th><div>CAS NO.</div></th>
						<th>
							<button class="dhx_button"  id='btnCasTblAdd' type="button"><span class="dxi dxi-plus"></span></button>
						</th>
					</tr>
					<c:forEach items="${casNoList}" var="casVo" varStatus="s">
						<tr <c:if test="${s.index == 0}">class='first'</c:if> >
							<td>
								<input type="text" class="inp_sty01" id="i_arrCasNo" name="i_arrCasNo" value="${casVo.v_cas_no }" style="width: 90%;"/>
								<input type="hidden" id="i_arrCasSeq" name="i_arrCasSeq" value="${casVo.n_seq_no }" />
							</td>
							<td class="center">
								<button class="dhx_button"  id='btnCasTblRemove' type="button"><span class="dxi dxi-minus"></span></button>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty casNoList}">
						<tr class='first'>
							<td><input type="text" class="inp_sty01" id="i_arrCasNo" name="i_arrCasNo" style="width: 90%;"/></td>
							<td class="center">
								<button class="dhx_button"  id='btnCasTblRemove' type="button"><span class="dxi dxi-minus"></span></button>
							</td>
						</tr>
					</c:if>
				</table>
			</td>
		</tr>
		<tr>
			<th>사용금지</th>
			<td>
				<table class="table_view">
					<colgroup>
						<col width="10%"/>
						<col width="40%"/>
					</colgroup>
					<tr>
						<th>국가</th>
						<th>상세</th>
					</tr>
					<tr>
						<td class="center"><input type='checkbox' id='i_sFlagBanBKo' name='i_arrFlagBanB' value='KO' <c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'KO') >=0}">checked</c:if> /> <label for="i_sFlagBanBKo">국내</label> </td>
						<td>
							<textarea style="width:80%;height:120px;" id='i_sFlagBanBKoComent' class='textarea_sty01' name='i_sFlagBanBKoComent' <c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'KO') < 0}">disabled</c:if>>${banVo.v_b_ko_comment}</textarea>
							<p><span id="len_i_sFlagBanBKoComent">0</span>/2,000byte</p>
						</td>							
					</tr>
					<tr>
						<td class="center"><input type='checkbox' id='i_sFlagBanBCn' name='i_arrFlagBanB' value='CN' <c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'CN') >=0}">checked</c:if> /> <label for="i_sFlagBanBCn">중국</label></td>
						<td>
							<textarea style="width:80%;height:120px;" id='i_sFlagBanBCnComent' class='textarea_sty01' name='i_sFlagBanBCnComent' <c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'CN') < 0}">disabled</c:if>>${banVo.v_b_cn_comment}</textarea>
							<p><span id="len_i_sFlagBanBCnComent">0</span>/2,000byte</p>
						</td>
					</tr>
					<tr>
						<td class="center"><input type='checkbox' id='i_sFlagBanBAe' name='i_arrFlagBanB' value='AE' <c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'AE') >=0}">checked</c:if> /> <label for="i_sFlagBanBAe">아세안</label></td>
						<td>
							<textarea style="width:80%;height:120px;" id='i_sFlagBanBAeComent' class='textarea_sty01' name='i_sFlagBanBAeComent' <c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'AE') < 0}">disabled</c:if>>${banVo.v_b_ae_comment }</textarea>
							<p><span id="len_i_sFlagBanBAeComent">0</span>/2,000byte</p>
						</td>							
					</tr>
					<tr>
						<td class="center"><input type='checkbox' id='i_sFlagBanBEu' name='i_arrFlagBanB' value='EU' <c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'EU') >=0}">checked</c:if> /> <label for="i_sFlagBanBEu">유럽</label> </td>
						<td>
							<textarea style="width:80%;height:120px;" id='i_sFlagBanBEuComent' class='textarea_sty01' name='i_sFlagBanBEuComent' <c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'EU') < 0}">disabled</c:if>>${banVo.v_b_eu_comment }</textarea>
							<p><span id="len_i_sFlagBanBEuComent">0</span>/2,000byte</p>
						</td>							
					</tr>
					<tr>
						<td class="center"><input type='checkbox' id='i_sFlagBanBUs' name='i_arrFlagBanB' value='US' <c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'US') >=0}">checked</c:if> /> <label for="i_sFlagBanBUs">미국</label></td>
						<td>
							<textarea style="width:80%;height:120px;" class='textarea_sty01' id='i_sFlagBanBUsComent' name='i_sFlagBanBUsComent' <c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'US') < 0}">disabled</c:if>>${banVo.v_b_us_comment }</textarea>
							<p><span id="len_i_sFlagBanBUsComent">0</span>/2,000byte</p>
						</td>							
					</tr>
				</table>
			</td>
			<th>배합제한</th>
			<td>
				<table class="table_view">
					<colgroup>
						<col width="10%"/>
						<col width="40%"/>
					</colgroup>
					<tr>
						<th>국가</th>
						<th>상세</th>
					</tr>
					<tr>
						<td class="center"><input type='checkbox' id='i_sFlagBanLKo' name='i_arrFlagBanL' value='KO' <c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'KO') >=0}">checked</c:if> /> <label for="i_sFlagBanLKo">국내</label></td>
						<td>
							<textarea style="width:80%;height:120px;" class='textarea_sty01' id='i_sFlagBanLKoComent' name='i_sFlagBanLKoComent' <c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'KO') < 0}">disabled</c:if>>${ banVo.v_l_ko_comment}</textarea>
							<p><span id="len_i_sFlagBanLKoComent">0</span>/2,000byte</p>
						</td>							
					</tr>
					<tr>
						<td class="center"><input type='checkbox' id='i_sFlagBanLCn' name='i_arrFlagBanL' value='CN' <c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'CN') >=0}">checked</c:if> /> <label for="i_sFlagBanLCn">중국</label></td>
						<td>
							<textarea style="width:80%;height:120px;" class='textarea_sty01' id='i_sFlagBanLCnComent' name='i_sFlagBanLCnComent' <c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'CN') < 0}">disabled</c:if>>${banVo.v_l_cn_comment }</textarea>
							<p><span id="len_i_sFlagBanLCnComent">0</span>/2,000byte</p>
						</td>							
					</tr>
					<tr>
						<td class="center"><input type='checkbox' id='i_sFlagBanLAe' name='i_arrFlagBanL' value='AE' <c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'AE') >=0}">checked</c:if> /> <label for="i_sFlagBanLAe"> 아세안</label></td>
						<td>
							<textarea style="width:80%;height:120px;" class='textarea_sty01' id='i_sFlagBanLAeComent' name='i_sFlagBanLAeComent' <c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'AE') < 0}">disabled</c:if>>${banVo.v_l_ae_comment }</textarea>
							<p><span id="len_i_sFlagBanLAeComent">0</span>/2,000byte</p>
						</td>							
					</tr>
					<tr>
						<td class="center"><input type='checkbox' id='i_sFlagBanLEu' name='i_arrFlagBanL' value='EU' <c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'EU') >=0}">checked</c:if> /> <label for="i_sFlagBanLEu"> 유럽</label></td>
						<td>
							<textarea style="width:80%;height:120px;" class='textarea_sty01' id='i_sFlagBanLEuComent' name='i_sFlagBanLEuComent' <c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'EU') < 0}">disabled</c:if>>${banVo.v_l_eu_comment }</textarea>
							<p><span id="len_i_sFlagBanLEuComent">0</span>/2,000byte</p>
						</td>							
					</tr>
					<tr>
						<td class="center"><input type='checkbox' id='i_sFlagBanLUs' name='i_arrFlagBanL' value='US' <c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'US') >=0}">checked</c:if> /> <label for="i_sFlagBanLUs"> 미국</label></td>
						<td>
							<textarea style="width:80%;height:120px;" class='textarea_sty01' id='i_sFlagBanLUsComent' name='i_sFlagBanLUsComent' <c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'US') < 0}">disabled</c:if>>${banVo.v_l_us_comment }</textarea>
							<p><span id="len_i_sFlagBanLUsComent">0</span>/2,000byte</p>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<th>배합목적</th>
			<td colspan='3'>
				<table id='subFuncTbl' style="width:100%;" class="table_view">
					<colgroup>
						<col width="30%"/>
						<col width="30%"/>
						<col width="30%"/>
						<col width="10%"/>
					</colgroup>
					<tr class='header'>
						<th>기능(영문)</th>
						<th>기능(국문)</th>
						<th>기능(중문)</th>
						<th><button class="dhx_button"  id='btnFuncAdd' type="button"><span class="dxi dxi-plus"></span></button></th>
					</tr>
					<c:forEach items="${funcList}" var="funcVo" varStatus="s">
						<tr class='<c:if test="${s.index == 0}">first</c:if> tr_value' >
							<td>
								<select  class="select_sty01" style="width: 90%;" id='i_arrFuncCdEn' name='i_arrFuncCdEn'>	
									<option value="" <c:if test="${empty funcVo.v_func_id_en}">selected</c:if>>선택</option>
									<c:forEach items="${funcComboList}" var="funcComboVo" varStatus="s">
										<option value="${funcComboVo.v_func_id }" <c:if test="${funcComboVo.v_func_id eq funcVo.v_func_id_en}">selected</c:if> >${funcComboVo.v_func_nm_en }</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select class="select_sty01" style="width: 90%;" id='i_arrFuncCdKo' name='i_arrFuncCdKo'>		
									<option value="" <c:if test="${empty funcVo.v_func_id_ko}">selected</c:if>>선택</option>							
									<c:forEach items="${funcComboList}" var="funcComboVo" varStatus="s">
										<option value="${funcComboVo.v_func_id }" <c:if test="${funcComboVo.v_func_id eq funcVo.v_func_id_ko}">selected</c:if> >${funcComboVo.v_func_nm_ko }</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select class="select_sty01" style="width: 90%;" id='i_arrFuncCdCn' name='i_arrFuncCdCn'>	
									<option value="" <c:if test="${empty funcVo.v_func_id_cn}">selected</c:if>>선택</option>								
									<c:forEach items="${funcComboList}" var="funcComboVo" varStatus="s">
										<option value="${funcComboVo.v_func_id }" <c:if test="${funcComboVo.v_func_id eq funcVo.v_func_id_cn}">selected</c:if>>${funcComboVo.v_func_nm_cn }</option>
									</c:forEach>
								</select>
							</td>
							<td class="center">
								<button class="dhx_button"  id="btnFuncRemove" type="button"><span class="dxi dxi-minus"></span></button>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty funcList}">
						<tr class='first tr_value'>
							<td>
								<select class="select_sty01" style="width: 90%;" id='i_arrFuncCdEn' name='i_arrFuncCdEn'>
										<option value="">선택</option>
									<c:forEach items="${funcComboList}" var="funcComboVo" varStatus="s">
										<option value="${funcComboVo.v_func_id }">${funcComboVo.v_func_nm_en }</option>
									</c:forEach>
								</select>
							</td>
							<td>								
								<select class="select_sty01" style="width: 90%;" id='i_arrFuncCdKo' name='i_arrFuncCdKo'>	
									<option value="">선택</option>							
									<c:forEach items="${funcComboList}" var="funcComboVo" varStatus="s">
										<option value="${funcComboVo.v_func_id }">${funcComboVo.v_func_nm_ko }</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select class="select_sty01" style="width: 90%;" id='i_arrFuncCdCn' name='i_arrFuncCdCn'>	
									<option value="">선택</option>							
									<c:forEach items="${funcComboList}" var="funcComboVo" varStatus="s">
										<option value="${funcComboVo.v_func_id }" >${funcComboVo.v_func_nm_cn }</option>
									</c:forEach>
								</select>
							</td>
							<td class="center">
								<button class="dhx_button"  id="btnFuncRemove" type="button"><span class="dxi dxi-minus"></span></button>
							</td>
						</tr>
					</c:if>
				</table>
			<td>
		</tr>
		<tr>
			<th>비고</th>
			<td colspan='3'>
				<textarea style="width:90%;height:120px;" class='textarea_sty01' id='i_sComment' name='i_sComment'>${rVo.v_comment}</textarea>
				<p><span id="len_i_sComment">0</span>/2,000byte</p>
			</td>
		</tr>
	</table>	
		
	<ul class="btn_area">
		<li class="right">
			<c:if test="${!empty reqVo.i_sConCd and empty reqVo.i_sFlagNewwVer}">
				<a href="#none" class="btnA bg_dark btn_del"><span>삭제</span></a>
			</c:if>
			<a style="cursor: pointer;" class="btnA bg_dark btn_tmpsave"><span>임시저장</span></a>
			<a style="cursor: pointer;" class="btnA bg_dark btn_save"><span>확정</span></a>
			<a style="cursor: pointer;" class="btnA bg_dark btn_list"><span>목록</span></a>
		</li>
	</ul>
</form>


<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
