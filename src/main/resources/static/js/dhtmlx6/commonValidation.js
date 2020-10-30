
/**
 * Form내부 필수 체크
 * @param form
 * @returns {Boolean}
 */
function fn_s_validateForm(form) {
	var bValid = true;
	if (!form.validate()) {
		fn_s_alertMsg('필수항목이 있습니다.');
		bValid = false;
	}
	return bValid;
	
}

/**
 * 입력값 체크
 * @param val
 * @param colNm
 * @param len
 * @returns
 */
function fn_s_inputLengthChk(val,colNm,len) {
	var valLen = calculate_byte(val);
	var result = false;
	if(valLen > len){
		fn_s_alertMsg(colNm+'의 자릿수가 '+len+'byte 보다 큽니다.');
		return true;
	}
	return result;	

	function calculate_byte( sTargetStr ) {
		var sTmpStr, sTmpChar;
		var nOriginLen = 0;
		var nStrLength = 0;

		sTmpStr = new String(sTargetStr);
		nOriginLen = sTmpStr.length;

		for ( var i=0 ; i < nOriginLen ; i++ ) {
			sTmpChar = sTmpStr.charAt(i);
			
			if (escape(sTmpChar).length > 4) {
				nStrLength += 3;
			}else if (sTmpChar!='\r') {
				nStrLength ++;
			}
		}
		return nStrLength;
	}
}

/**
 * 
 * @param val
 * @param colNm
 * @returns
 */
function fn_s_inputOnlyEnNumChk(val,colNm) {
	var result = false;
	 var reg = new RegExp (/^[A-Za-z0-9+]*$/);
	 if(!reg.test(val)){
		fn_s_alertMsg(colNm+'에 영문,숫자만 입력바랍니다.');
		return true; 
	 }
	return result;	
}
function fn_s_inputKoChk(val,colNm) {
	var result = false;
	 var reg = new RegExp (/^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣+]*$/);
	 if(reg.test(val)){
		fn_s_alertMsg(colNm+'에 한글없이 입력바랍니다.');
		return true; 
	 }
	return result;	
}
