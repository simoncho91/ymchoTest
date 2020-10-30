// 로컬 개발용 루트 패스워드
var	jsLocalRootPATH	= "/";

if (null != window.jsRootPATH) {
	jsLocalRootPATH	= jsRootPATH;
}

// 빈 공백 문자열 CHECK
function isEmpty(P) {
    if (P != null) {
      P = ReplaceChar(P,"\n","");
      P = ReplaceChar(P,"\r","");
   }
   return ((P == null) || (P.replace(/ /gi,"").length == 0));
}

function isEmpty2(P) {
	
	if (P != null) 
	{
		P = ReplaceChar(P,"\n","");
	    P = ReplaceChar(P,"\r","");
	    P = ReplaceChar(P," ","");
	    
	    return (P == "");
	}
	return true;  
}

// 문자형 자료 CHECK
function isString(P) {
   var len = P.length;

   for (i=0; i<len; i++) {
      if ( ((P.charAt(i) >= "a") && (P.charAt(i) <= "z")) || ((P.charAt(i) >= "A") && (P.charAt(i) <= "Z")) ) {
      }
      else {
         return false;
      }
   }
   return true;
}

// 숫자만 리턴 (숫가가 없을 경우 0)
function fnOnlyNumber(str){
	var	len		= str.length;
	var oRtn	= {};
	var iRtn	= 0;
	var lRtn	= 0;
	var sRtn	= "";
	
	for (var i = 0; i < len; i++)
	{
		if ((str.charAt(i) >= "0" && str.charAt(i) <= "9") || (str.charAt(i) == "-") || (str.charAt(i) == "."))
		{
			sRtn	+= str.charAt(i);
		}
	}
	
	if (sRtn != "" && sRtn != "." &&  sRtn != "-"){
		iRtn		= parseInt(sRtn, 10);
		lRtn		= parseFloat(sRtn);
	}
	
	oRtn.number	= iRtn;
	oRtn.float	= lRtn;
	oRtn.string	= sRtn;
	
	return oRtn;
}

// 숫자형 자료 CHECK
function isDigit (P) {
   var len = P.length;

   for (i=0; i<len; i++) {
      if ( (P.charAt(i) >= "0") && (P.charAt(i) <= "9") ) {
      }
      else {
         return false;
      }
   }
   return true;
}

// 숫자형 자료 CHECK2
function isDigit2 (P) {
   if(isNaN(P.value) && P.value != 0){
      alert("숫자를 입력하십시오");
      P.focus();
      return false;
   }
   return true;
}

// 단순 PASSWORD Check
// 6자 미만, 영숫자 미 혼용, ID+글자사용, ID 와 동일 사용, 동일문자열 4회 이상연속
function simplePASSWORDCheck(strpass, id)
{
   // 단순 Password Check 2003/4/17 윤미진
   var chkStr = new Array();
   var chkFlag = 0;
   var chkCnt = 0;
   var digitFlag = 0;
   var alphaFlag = 0;

   if(strpass.length < 6)
   {
      // 영 숫자 혼용으로 6자 이상 입력 하십시요.
      return "MIN_LENGTH";
   }

   if(strpass == id || strpass.search(id) != -1)
   {
      return "INCLUDE_ID";
   }


   for(i= 0; i < strpass.length; i++)
   {
      if(chkStr[chkCnt] == strpass.charAt(i)) chkCnt += 1;
      else chkCnt = 0;

      chkStr[chkCnt] = strpass.charAt(i);
      if(chkFlag == 0 && (chkCnt + 1) > 3) chkFlag = 1;

      if ( (strpass.charAt(i) >= "0") && (strpass.charAt(i) <= "9") )
          digitFlag = 1;

      if ( (strpass.charAt(i) >= "a") && (strpass.charAt(i) <= "z") ||
           (strpass.charAt(i) >= "A") && (strpass.charAt(i) <= "Z") )
          alphaFlag = 1;

   }
   // 영 숫자 혼용이 아닌경우
   if(digitFlag == 0 || alphaFlag == 0)
   {
      // 영 숫자 혼용으로 6자 이상 입력 하십시요.
      return "NOT_MIX";
   }

   // 동일 문자 연속 4회 이상인 경우
   if(chkFlag == 1)
   {
     // 동일 문자열이 4회 이상 연속되었습니다.
     return "SAME_STRING";
   }

   return "OK";
}

// 숫자만 입력
function SetNumObj(obj)
{
    obj.value   = SetNum(obj.value);
    obj.select();

    return;
}
// 콤마 제거
function SetNum(str)
{
    return ReplaceChar(str, ",", "");
}

function SetNumComma(str)
{
	var tmpStr, sResult, endStr; 
	
	if (typeof str == "number")
		str		= ""+ str;
	
	if ( str.indexOf(".") > -1 ) {
		tmpStr	= str.substring(0, str.indexOf("."));
		endStr	= str.substring(str.indexOf("."), str.length);
	}
	else {
		tmpStr	= str;
		endStr	= "";
	}
		
    tmpStr  = onlyNumber( tmpStr );
    sResult = "";

    if (tmpStr != "")
    {
        tmpStr  = "" + parseInt(tmpStr, 10);        // 0부터 시작할 경우 앞에 0 모두 제거

        var len     = tmpStr.length;
        var cnt     = 0;

        for (i = len - 1; i >= 0; i--) 
        {
            if (cnt > 0 && cnt % 3 == 0 )
            {
                sResult  = "," + sResult;
            }
            sResult  = tmpStr.charAt(i) + sResult;
            cnt++;
        }
    }

    return sResult + endStr;
}

// 숫자값만 넘겨준다.
function onlyNumber (str) {
    var len      = str.length;
    var sReturn  = "";

    for (var i=0; i<len; i++) 
    {
        if ( (str.charAt(i) >= "0") && (str.charAt(i) <= "9") ) 
        {
            sReturn += str.charAt(i);
        }
    }
    return sReturn;
}

function onlyNuberKeyDownCheck(event, evt, p) {
	var sValue	  = evt.target.value;
	var isPoint	  = false;
	var sKeyCode  = event.keyCode;
	var sKeyValue = String.fromCharCode(sKeyCode);
	var sKeyCheck = "";
	var sTmpKey1  = 0;
	var sTmpKey2  = 0;
	
	if (sValue.indexOf(".") > -1) {
		isPoint		= true;
	}
	
	// 소수점은 하나만 허용
	if (isPoint && (sKeyCode == 110 || sKeyCode == 190)) {
		 event.keyCode = 0;
		 event.returnValue = false;
		 return;
	}
	
	if (p == "Y") {
	    sKeyCheck = "-0123456789";
	    sTmpKey1  = 109;
	    sTmpKey2  = 189;
	}
	else {
	    sKeyCheck = "0123456789";
	    sTmpKey1  = 8;
	    sTmpKey2  = 8;
	}
	if (sKeyCheck.indexOf(sKeyValue) > -1 
			|| sKeyCode == sTmpKey1 
			|| sKeyCode == sTmpKey2 
			|| sKeyCode == 8 
			|| sKeyCode == 9 
			|| sKeyCode == 46 
			|| sKeyCode == 16 
			|| sKeyCode == 17 
			|| sKeyCode == 110 || sKeyCode == 190 
			|| (sKeyCode >= 96 && sKeyCode <= 105)
			|| (sKeyCode >= 35 && sKeyCode <= 40) ) {
	    event.returnValue = true;
	}
	else {
	    event.keyCode = 0;
	    event.returnValue = false;
	}
}

// 숫자만 입력 KeyDown 이벤트 (keyPressCheck와 동시사용해야함, 한글은 적용 안됨)
function keyDownCheck(event, P) {
	
   var sKeyCode  = event.keyCode;
   var sKeyValue = String.fromCharCode(sKeyCode);
   var sKeyCheck = "";
   var sTmpKey1  = 0;
   var sTmpKey2  = 0;

   if (P == "Y") {
      sKeyCheck = "-0123456789";
      sTmpKey1  = 109;
      sTmpKey2  = 189;
   }
   else {
      sKeyCheck = "0123456789";
      sTmpKey1  = 8;
      sTmpKey2  = 8;
   }
   if (sKeyCheck.indexOf(sKeyValue) > -1 || sKeyCode == sTmpKey1 || sKeyCode == sTmpKey2 || sKeyCode == 8 || sKeyCode == 9 || sKeyCode == 110 || sKeyCode == 190 || sKeyCode == 46 || sKeyCode == 16 || sKeyCode == 17 || (sKeyCode >= 96 && sKeyCode <= 105)|| (sKeyCode >= 35 && sKeyCode <= 40) ) {
      event.returnValue = true;
   }
   else {
    event.keyCode = 0;
      event.returnValue = false;
   }
}

// 숫자만 입력 KeyPress 이벤트 (keyDownCheck와 동시사용해야함, 한글은 적용 안됨)
function keyPressCheck( event, P) {
   var sKeyCode  = event.keyCode;
   var sKeyValue = String.fromCharCode(sKeyCode);
   var sKeyCheck = "";
   if (P == "Y") {
      sKeyCheck = "-0123456789";
   }
   else {
      sKeyCheck = "0123456789";
   }
   if (sKeyCheck.indexOf(sKeyValue) > -1 || sKeyCode == 46) {
      event.returnValue = true;
   }
   else {
    event.keyCode = 0;
      event.returnValue = false;
   }
}

// 자료의 길이 CHECK
function ValidLength(Data_Val, len) {
   return (Data_Val.length >= len);
}

function InValidLength(Date_Val, len, len2) {
   return ((Date_Val.length >= len) && (Date_Val.length <= len2));
}

// 단일문자 대체 (원본문자열, 대체대상 문자, 대체할 문자)
function ReplaceChar(str, tarCh, repCh) {
	
	var nowCh  = "";
	var sumStr = "";
	
	if (typeof str == "string")
	{
		var len    = str.length;
		
		for (i=0; i<len; i++) 
		{
			if (str.charAt(i) == tarCh)
				nowCh = repCh;
			else
				nowCh = str.charAt(i);

	      sumStr = sumStr + nowCh;
		}
	}
	return sumStr;
}

// 좌우공백 제거 (공백만 있는 경우는 안됨)
function trim(str) {
    var start = 0;
    var end   = str.length - 1;

    for (var i=0; i < str.length; i++) {
         if (str.substring(i,i+1) != " ") {
             start = i;
             break;
         }
    }

    for (var i=str.length - 1; i >= 0; i--) {
         if (str.substring(i,i+1) != " ") {
             end = i + 1;
             break;
         }
    }

    return str.substring(start, end);
}

// 경로명과 확장자를 제외한 파일 이름 얻기 - 정성우 2002.10.24 추가
function fnGetFileId(p) {
   var sFileName = p.location.pathname.substring(location.pathname.lastIndexOf('/')+1);
   var sFileId   = sFileName.substring(0, sFileName.lastIndexOf('.'));
   return sFileId;
}


// 문자열 길이 체크
function fnCheckStringLength(stringElement,nowLenElement,MAX)
{
  	var origin_str = stringElement.value;
  	var return_str ="";
  	var strLen = 0;
  	var return_total = 0;
  	var temp_len=0;
  	    
  	strLen = origin_str.length;
  	    
  	for( i=0 ; i < strLen ; i++ )
  	{
  	   var ch = origin_str.charAt(i);
  
  	   if( escape(ch).length > 4 ) return_total += 2;
  	   else if( ch != '\r' ) return_total++;
  		
  	   if( return_total > MAX )
  	   {
  	      alert(MAX + " 바이트 이하로 입력해 주세요.");
  	      stringElement.value=return_str;
  					  stringElement.focus();
  					  return_total=temp_len;
  	      break;
  	   }
  	   else
  	   {
  				   return_str += ch;
  				   temp_len=return_total;
  	   }
  	}
  	
  	if(!isEmpty(nowLenElement)){
  	   alert("여기");
  	   nowLenElement.value = return_total;
  	}
  
  	return;
}


// 일정크기만큼 문자열 길이 반환(문자열, 길이, 확장자 포함 여부-Y/N)
function fnCutString(sData, iLen, isExt)
{
  	var origin_str = sData;
  	var strExt = "";
  	var return_str ="";
  	var strLen = 0;
  	var return_total = 0;
  	var temp_len=0;
  	
  	strLen = origin_str.length;
  	
  	if(isExt == "Y"){
  	   arrTemp = sData.split('.');
  	   strExt = arrTemp[arrTemp.length-1];
  	   strLen = strLen - (strExt.length + 1);
  	   iLen = iLen - (strExt.length + 1);
  	}
  	
  	for( i=0 ; i < strLen ; i++ )
  	{
  	   var ch = origin_str.charAt(i);
  
  	   if( escape(ch).length > 4 ) return_total += 2;
  	   else if( ch != '\r' ) return_total++;
  		
  	   if( return_total > iLen )
  	   {
  					  return_total=temp_len;
  	      break;
  	   }
  	   else
  	   {
  				   return_str += ch;
  				   temp_len=return_total;
  	   }
  	}
  	
  	if(isExt == "Y"){
  	   return_str = return_str + "." + strExt;
  	}
  	
  	return return_str;
}

/*******************************************************************************
 * Function Name : jsValidBizRegNo Description : 사업자등록번호 검사 Parameters :
 * bizRegNo - '-'를 제외한 사업자등록번호 10자리의 문자열 Example : if
 * (!jsValidBizRegNo("1231212345")) return; Comment :
 * *****************************************************************************
 * History : 2008. 06. 25 방지한 (최초 작성)
 ******************************************************************************/
function jsValidBizRegNo(bizRegNo) {
	var	i, sum		= 0;
	var	check		= parseInt(bizRegNo.charAt(9), 10);
	var	tempBizRegNo	= new Array(10);
	var	checkValue	= new Array(1, 3, 7, 1, 3, 7, 1, 3, 5, 1);

	if (bizRegNo == "0000000000") {
		alert("입력항목 [사업자등록번호]의 형식이 맞지 않습니다.");
		return false;
	}

	for (i = 0; i < 10; i++)	tempBizRegNo[i]	= parseInt(bizRegNo.charAt(i), 10);

	for (i = 0; i < 10; i++)
		sum += (tempBizRegNo[i] * checkValue[i]);

	sum	+= Math.floor((tempBizRegNo[8] * 5) / 10);
	sum	%= 10;
	sum	= (sum == 0) ? 0 : (10 - sum);

	if (sum == 0)	return true;
	else {
		alert("입력항목 [사업자등록번호]의 형식이 맞지 않습니다.");
		return false;
	}
}

/*******************************************************************************
 * Function Name : jsMakeAndCheckTelNo Description : 전화번호 검사 및 한 문자열로 변환하는 기능
 * Parameters : tel_no - 전체 전화번호를 받을 form id tel_no0 - 지역번호 또는 휴대폰 ID 번호의 form
 * id tel_no1 - 국번의 form id tel_no2 - 번호의 form id title - 전화번호 필드의 명칭 need - 필수
 * 입력 여부 (true : 필수, false : 필수 아님) Example : if
 * (jsMakeAndCheckTelNo(document.myForm.tel_no, document.myForm.tel_no0,
 * document.myForm.tel_no1, document.myForm.tel_no2, "휴대폰", false) return;
 * Comment :
 * *****************************************************************************
 * History : 2008. 06. 25 방지한 (최초 작성)
 ******************************************************************************/
function jsMakeAndCheckTelNo(tel_no, tel_no0, tel_no1, tel_no2, title, need) {
	var	count	= 0;

	if (tel_no0.value != "") count++;
	if (tel_no1.value != "") count++;
	if (tel_no2.value != "") count++;

	if (need && (count == 0)) {
		alert("입력항목 [" + title + "]을(를) 입력하십시오.");
// tel_no0.focus();
		return false;
	}

	if ((count != 0) && (count != 3)) {
		alert("입력항목 [" + title + "]의 형식이 맞지 않습니다.");
// tel_no0.focus();
		return false;
	}

	tel_no.value	= tel_no0.value + "-" + tel_no1.value + "-" + tel_no2.value;
	if ((count != 0) && ((tel_no.value.length < 11) || (tel_no.value.length > 13))) {
// tel_no.value = "";
		alert("입력항목 [" + title + "]의 형식이 맞지 않습니다.");
// tel_no0.focus();
		return false;
	}

	if (tel_no.value == "--")	tel_no.value	= "";

	return true;
}

/*******************************************************************************
 * Function Name : jsCheckTelNo Description : 전화번호 검사 기능 Parameters : tel_no0 -
 * 지역번호 또는 휴대폰 ID 번호의 form id tel_no1 - 국번의 form id tel_no2 - 번호의 form id title -
 * 전화번호 필드의 명칭 need - 필수 입력 여부 (true : 필수, false : 필수 아님) Example : if
 * (jsCheckTelNo(document.myForm.tel_no0, document.myForm.tel_no1,
 * document.myForm.tel_no2, "휴대폰번호", false) return; Comment :
 * *****************************************************************************
 * History : 2008. 06. 25 방지한 (최초 작성)
 ******************************************************************************/
function jsCheckTelNo(tel_no0, tel_no1, tel_no2, title, need) {
	var	count	= 0;

	if (tel_no0.value != "") count++;
	if (tel_no1.value != "") count++;
	if (tel_no2.value != "") count++;

	if (need && (count == 0)) {
		alert("입력항목 [" + title + "]을(를) 입력하십시오.");
// tel_no0.focus();
		return false;
	}

	if ((count != 0) && (count != 3)) {
		alert("입력항목 [" + title + "]의 형식이 맞지 않습니다.");
// tel_no0.focus();
		return false;
	}

	var	tel_no	= tel_no0.value + "-" + tel_no1.value + "-" + tel_no2.value;
	if ((count != 0) && ((tel_no.length < 11) || (tel_no.length > 13))) {
// tel_no.value = "";
		alert("입력항목 [" + title + "]의 형식이 맞지 않습니다.");
// tel_no0.focus();
		return false;
	}

	return true;
}

/* 체크박스 전체 선택, 전체 해제 */
function fnChkAll(obj1, tmpStr) {
	var obj2	= document.getElementsByName(tmpStr);
	var totCnt	= obj2.length;

	if (obj1.checked == true) {
		for (var i = 0; i < totCnt; i++ ) {
			if ( obj2[i].disabled == false) {
				obj2[i].checked = true;
			}
		}
	} else {
		for (var i = 0; i < totCnt; i++ ) {
			obj2[i].checked = false;
		}
	}
}

/* 체크박스 전체 선택 유무 체크 */
function fnChkEach(tmpStr1, tmpStr2) {
	var obj1	= document.getElementsByName(tmpStr1);
	var obj2	= document.getElementById(tmpStr2);		// 전체 선택, 전체 해제 체크박스
	var totCnt	= obj1.length;
	var bresult	= true;

	for(var i = 0; i < totCnt; i++) {
		if ( obj1[i].disabled == false && obj1[i].checked == false) {
			bresult = false;
		}
	}

	obj2.checked = bresult;
}

function check_length(sInputStr, strLength) {

	nStrLen = calculate_byte(sInputStr);

	if ( nStrLen > strLength ) {
		return false;
	} else {
		return true;
	}

}

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

/* 메시지의 길이를 체크 txtObj 길이 체크할 객체, sTarget : span id, maxLength 최대 길이 */
function fnMsgLength(txtObj, sTarget, maxLength) {
	if (txtObj == undefined)
		return;
	
	var sObj				= document.getElementById(sTarget);
	var txtLength			= 0;
	txtLength				= calculate_byte(txtObj.value);
	sObj.innerHTML			= SetNumComma(txtLength);

	if (maxLength < txtLength) {
		
		sObj.innerHTML	=	"<font color='red'>" + SetNumComma(calculate_byte(txtObj.value)) + "</font>";
		
	}
}

/* 메시지의 길이를 체크후 submit frm form이름, txtObj 길이 체크할 객체, maxLength 최대 길이, url 주소 */
function fnMsgLengthVal(frm, txtObj, maxLength, url) {
	var txtLength			= 0;
	txtLength				= calculate_byte(txtObj.value);
	
	if (maxLength > txtLength) {
		frm.action	=	url;
		frm.submit();
	}else{
		alert("최대 허용 문자수를 초과하였습니다.");
		txtObj.focus();
		return;
	}
}

/* 메시지의 길이를 체크후 submit frm form이름, txtObj 길이 체크할 객체, maxLength 최대 길이, url 주소 */
function fnTextAreaMaxLengthCheck(txtObj) {
	var txtLength			= 0;
	txtLength				= calculate_byte(txtObj.val());
	
	if (txtObj.attr("maxlength") < txtLength) {
		showMessageBox({
			message : txtObj.attr("alt")+" 입력값의 최대 허용길이를 초과하였습니다."
		});
		return false;
	}else{
		return true;
	}
}

/**
 * 문자 변경
 * 
 * @param tmpStr
 * @param searchStr
 * @param replaceStr
 */
function fnReplaceAll ( tmpStr, searchStr, replaceStr) {

	while( tmpStr.indexOf( searchStr ) != -1 )	{
		tmpStr = tmpStr.replace( searchStr, replaceStr );
	}
	return tmpStr;

}

// css 클래스 추가
function fnAddClass( obj, addClsName )
{
	try {
		var cls			= obj.className;
		var arrClsName	= cls.split(/\s+/);
		var len			= arrClsName.length;
		var bResult		= true;
		
		addClasName = trim(addClsName);
		
		for (var i = 0 ; i < len; i++)
		{
			if (arrClsName[i] == addClasName)
			{
				bResult		= false;
				break;
			}
		}
		
		if (bResult)
		{
			arrClsName.push(addClasName);
			
			obj.className	= arrClsName.join(" ");
		}
		
	} catch (e) {
		alert(e.message);
	}
}

// css 클래스 삭제
function fnRemoveClass( obj, addClsName )
{
	try {
		var cls			= obj.className;
		var arrClsName	= cls.split(/\s+/);
		var len			= arrClsName.length;
		var bResult		= false;
		
		addClasName = trim(addClsName);
		
		for (var i = len -1 ; i >= 0; i--)
		{
			if (arrClsName[i] == addClasName)
			{
				arrClsName[i]	= "";
				bResult			= true;
			}
		}
		
		if (bResult)
		{
			obj.className = arrClsName.join(" ");
		}
		
	} catch (e) {
		alert(e.message);
	}
}

// index 값 구하기
function fnGetIndex( obj )
{
	try {
		var arrObj		= document.getElementsByName(obj.name);
		var len			= arrObj.length;
		
		for (var i = 0 ; i < len; i++)
			if (arrObj[i] == obj)
				return i;
		
		return -1;
	} catch (e) {
		alert(e.message);
		return -1;
	}
}

// 개월수 계산
function getMonthCnt(startYm, endYm) {
	
	startYm = onlyNumber(startYm);
	endYm = onlyNumber(endYm);
	
	if (startYm.length < 6 || endYm.length < 6) {
		return 0;
	}
	
	var startYear = parseInt(startYm.substr(0, 4), 10);
	var startMonth = parseInt(startYm.substr(4, 2), 10);
	var endYear = parseInt(endYm.substr(0, 4), 10);
	var endMonth = parseInt(endYm.substr(4, 2), 10);
	var monthCnt = 0;
	
	if (parseInt(startYm, 10) <= parseInt(endYm, 10)) {
		if ( startYear == endYear) {
			return endMonth - startMonth + 1;
		}
		
		monthCnt = 0;
		
		for (var i = startYear; i <= endYear; i++) {
			if (startYear == i) {
				monthCnt += 12 - startMonth + 1; 
			}
			else if (endYear == i) {
				monthCnt += endMonth;
			}
			else {
				monthCnt += 12;
			}
		}
		return monthCnt;
	}
	else {
		if ( startYear == endYear) {
			return (startMonth - endMonth + 1) * -1;
		}
		
		monthCnt = 0;
		
		for (var i = endYear; i <= startYear; i++) {
			if (endYear == i) {
				monthCnt += 12 - endMonth + 1; 
			}
			else if (startYear == i) {
				monthCnt += startMonth;
			}
			else {
				monthCnt += 12;
			}
		}
		return monthCnt * -1;
	}
}

// 마지막 일자 가져오기
function getMonthLastDay( year, month ) 
{
	var iYear, iMonth;
	
	if (typeof year == "number")
	{
		iYear	= year;
	}
	else
	{
		try {
			iYear	= parseInt(year, 10);
		} catch (e) {
			return 0;
		}
	}
	
	if (typeof month == "number")
	{
		iMonth	= month;
	}
	else
	{
		try {
			iMonth	= parseInt(month, 10);
		} catch (e) {
			return 0;
		}
	}
	
	if (iMonth == 12)
		return 31;
	
	var dt	= new Date(iYear, iMonth, 0);
	
	return dt.getDate();
}


//cookie 생성1
function setCookie (name, value)
{	
	document.cookie = name + "=" + escape( value ) + ";";	
}


//cookie 생성2
function setCookie (name, value, expiredays)
{
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays ); 
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";";
}

// cookie 확인
function getCookie(name) 
{
	var nameOfCookie = name + '='; 
	var x = 0;
	while ( x <= document.cookie.length ) 
	{
		var y = (x + nameOfCookie.length);
		
		if ( document.cookie.substring( x, y ) == nameOfCookie ) 
		{
			if ( (endOfCookie=document.cookie.indexOf( ';',y )) == -1 )
				endOfCookie = document.cookie.length;
			return unescape( document.cookie.substring(y, endOfCookie ) );
		}
		x = document.cookie.indexOf( ' ', x ) + 1;
		if ( x == 0 )break;
	}
	return '';
}

// E-mail 체크
function emailChk(email) 
{
	if (!asciiChk(email)) {
		alert("E-mail을 다시 확인해 주세요");
		return false;
	}

	var invalidStr = "\"|&;<>!*\'\\"   ;

	for (var i = 0; i < invalidStr.length; i++) 
	{
		if (email.indexOf(invalidStr.charAt(i)) != -1) 
		{
			alert("E-mail을 다시 확인해 주세요");
			return false;
		}
	}

	if (email.indexOf("@")==-1)
	{
		alert("E-mail형식이 맞지 않습니다.");
		return false;
	}

	if (email.indexOf(" ") != -1){
		alert("E-Mail에 공백이 있습니다.");
		return false;
	}

	if (window.RegExp) 
	{
		var reg1str = "(@.*@)|(\\.\\.)|(@\\.)|(\\.@)|(^\\.)";
	// var reg2str =
	// "^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)jQuery";
		var reg2str = "^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$";

		var reg1 = new RegExp (reg1str);
		var reg2 = new RegExp (reg2str);
		if (reg1.test(email) || !reg2.test(email)) 
		{
			alert("E-Mail에 잘못된 문자가 있습니다.");
			return false;
		}
	}
	return true;
}


function getMinDate(date1, date2, sChar)
{
	if ( isDate(date1, sChar) && isDate(date2, sChar) )
	{
		var dt1		= getDate(date1, sChar);
		var dt2		= getDate(date2, sChar);
		
		if (dt1.getTime() < dt2.getTime())
			return date1;
		else
			return date2;
	}
	else if ( isDate(date1, sChar) && !isDate(date2, sChar) )
	{
		return date1;
	}
	else if ( !isDate(date1, sChar) && isDate(date2, sChar) )
	{
		return date2;
	}
	else
	{
		return "";
	}
}

function getMaxDate(date1, date2, sChar)
{
	if ( isDate(date1, sChar) && isDate(date2, sChar) )
	{
		var dt1		= getDate(date1, sChar);
		var dt2		= getDate(date2, sChar);
		
		if (dt1.getTime() > dt2.getTime())
			return date1;
		else
			return date2;
	}
	else if ( isDate(date1, sChar) && !isDate(date2, sChar) )
	{
		return date1;
	}
	else if ( !isDate(date1, sChar) && isDate(date2, sChar) )
	{
		return date2;
	}
	else
	{
		return "";
	}
}

function getDate( sDate, sChar )
{
	var strLen		= sDate.length;
	var tmpDate		= new Date();
	var year		= 0;
	var month		= 0;
	var date		= 0;
	
	if (strLen != 10 && strLen != 8)
		return tmpDate;
	
	if (strLen == 10)
	{
		var arrDate		= sDate.split(sChar);
		
		if (arrDate.length != 3)
			return tmpDate;

		year		= parseInt(arrDate[0], 10);
		month		= parseInt(arrDate[1], 10) - 1;
		date		= parseInt(arrDate[2], 10);
	}
	else
	{
		year		= parseInt(sDate.substring(0, 4), 10);
		month		= parseInt(sDate.substring(4, 6), 10) - 1;
		date		= parseInt(sDate.substring(6, 8), 10);
	}
	
	tmpDate.setFullYear(year);
	tmpDate.setMonth(month);
	tmpDate.setDate(date);
	
	return tmpDate; 
}

// date => string
function getDateToString(dt, sChar) {
	
	if (sChar == undefined) {
		sChar = "";
	}
	
	var year	= dt.getFullYear();
	var month	= dt.getMonth() + 1;
	var date	= dt.getDate();
	var arr		= [];
	
	arr.push(year);
	arr.push(month < 10 ? "0" + month : month);
	arr.push(date < 10 ? "0" + date : date);
	
	return arr.join(sChar);
}

function getStrDatePattern (str_dt, sChar) {
	
	if (str_dt == undefined || str_dt == "") {
		return "";
	}
	
	var str_dt = fnOnlyNumber(str_dt).string;
	var str_len = str_dt.length;

	if (str_len >= 8) {
		var year		= str_dt.substring(0, 4);
		var month		= str_dt.substring(4, 6);
		var date		= str_dt.substring(6, 8);
		return year + sChar + month + sChar + date;  
	}else if (str_len >= 6) {
		var year		= str_dt.substring(0, 4);
		var month		= str_dt.substring(4, 6);
		return year + sChar + month;  
	}  
	else {
		return str_dt;
	}
}

// 날짜 정상유무 체크
function isDate( sDate, sChar)
{
	var strLen		= sDate.length;
	var tmpDate		= new Date();
	var year		= 0;
	var month		= 0;
	var date		= 0;
	
	if (strLen != 10 && strLen != 8)
		return false;
	
	if (strLen == 10)
	{
		var arrDate		= sDate.split(sChar);
		
		if (arrDate.length != 3)
			return false;

		year		= parseInt(arrDate[0], 10);
		month		= parseInt(arrDate[1], 10) - 1;
		date		= parseInt(arrDate[2], 10);
	}
	else
	{
		year		= parseInt(sDate.substring(0, 4), 10);
		month		= parseInt(sDate.substring(4, 6), 10) - 1;
		date		= parseInt(sDate.substring(6, 8), 10);
	}
	
	tmpDate.setFullYear(year);
	tmpDate.setMonth(month);
	tmpDate.setDate(date);
	
	if (tmpDate.getFullYear() == year && tmpDate.getMonth() == month && tmpDate.getDate() == date)
		return true;
	else
		return false;
}


function fnStrMaxCheck ( inputNode, message)
{
	var sMaxLength;
	var iMaxLength		= 0;
	try {
		sMaxLength	= inputNode.getAttribute("maxlength");
	} catch (e) {
		alert("잘못된 객체입니다.\n" + e.message);
		return false;
	}
	
	if (sMaxLength == null)
	{
		alert("maxlength 값 미설정");
		return false;
	}
	
	try {
		iMaxLength = parseInt(sMaxLength, 10);
	} catch (e) {
		alert("maxlengt 값이 없거나 숫자가 아닌 값이 있습니다.");
		return false;
	}
	
	if (calculate_byte(inputNode.value) > iMaxLength)
	{
		if (message != null && message != "")
		{
			alert(message + "\n\n" + iMaxLength + "byte 이내로 작성해 주세요.\n영문 : " + iMaxLength + "자, 한글 : " + Math.floor(iMaxLength/2) + "자" );
		}
		return false;
	}
	return true;
}

// 숫자만 입력해야 하는 input
function addMoneyEvent(el, p_opt){
	
	if(el == null || typeof el != "object")
		el		= jQuery(".money");
	
	var defaults = {
		isCopy : false
	};
	var	options = jQuery.extend(defaults, p_opt);
	
	if (options.isCopy) {
		el.focus(function (){
			jQuery(this).val( SetNum(jQuery(this).val()) );
			jQuery(this).select();
		}).blur(function (){
			jQuery(this).val(SetNumComma(jQuery(this).val()));
		}).css("ime-mode", "disabled");
	}
	else {
		el.keydown(function (){
			keyDownCheck(event, 'N');
		}).keypress(function (){
			keyPressCheck(event, 'N');
		}).focus(function (){
			jQuery(this).val( SetNum(jQuery(this).val()) );
			jQuery(this).select();
		}).blur(function (){
			jQuery(this).val(SetNumComma(jQuery(this).val()));
		}).css("ime-mode", "disabled");
	}
}

function addOnlyNumberEvent(el) {
	
	if(typeof el != "object")
		el		= jQuery(".onlyNumber");
	
	el.keydown(function (evt){
		onlyNuberKeyDownCheck(event, evt, 'N');
	}).keypress(function (){
		keyPressCheck(event, 'N');
	}).css("ime-mode", "disabled");
}

function fnPopupResize(thisX, thisY) {
   if(thisX < 0) { thisX = parseInt(document.body.scrollWidth); }
   if(thisY < 0) { thisY = parseInt(document.body.scrollHeight); }
   
   var maxThisX = screen.width - 50;
   var maxThisY = screen.height - 50;
   var marginY = 0;
   // alert(thisX + "===" + thisY);
   if (navigator.userAgent.indexOf("MSIE 6") > 0) marginY = 45;        // IE
																		// 6.x
   else if(navigator.userAgent.indexOf("MSIE 7") > 0) marginY = 75;    // IE
																		// 7.x
   else if(navigator.userAgent.indexOf("MSIE 8") > 0) marginY = 75;    // IE
																		// 7.x
   else if(navigator.userAgent.indexOf("Firefox") > 0) marginY = 50;   // FF
   else if(navigator.userAgent.indexOf("Opera") > 0) marginY = 30;     // Opera
   else if(navigator.userAgent.indexOf("Netscape") > 0) marginY = -2;  // Netscape

   if (thisX > maxThisX) {
       window.document.body.scroll = "yes";
       thisX = maxThisX;
   }
   if (thisY > maxThisY - marginY) {
       window.document.body.scroll = "yes";
       thisX += 19;
       thisY = maxThisY - marginY;
   }
   window.resizeTo(thisX + 10, thisY + marginY + 15);
}

/**
 * input txt 엔터 처리
 * 
 * @param inputID
 *            input 태그 id
 * @param buttonID
 *            버튼 id ( img, button, etc...)
 */
function cmEnterKey(inputID, buttonID) {
    j$('#'+inputID).keydown(function(event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            j$('#'+buttonID).click();
        }
    });
 }

function checkForDuplicates(arr){
    var x;
    for(var i=0;i<arr.length;i++){
        x=arr[i];
        for(var j=i+1;j<arr.length;j++){
            if(x==arr[j]){
                return false;
            }
        }
    }
    return true;
}

/**
 * target 이름 완성
 * @param name
 * @param str1
 * @param str2
 * @returns
 */
function makeTargetName(name, str1, str2) {
	var lastName, strArr;
	if (name != "") {
		lastName	= name.substr(name.length-1 , 1);
		strArr		= js_han_split(lastName);
		
		if (typeof strArr != "boolean") {
			if (strArr[2] == "")
				name	+= str1;
			else
				name	+= str2;
		}
		else {
			name += str1 + "(" + str2 + ")";
		}
	}
	return name;
}

//한글을 자름
function js_han_split(char)
{  
	var char_st = 44032; //'가'의 유니코드 넘버(10진수) 
	var char_ed = 55203; //'힝'의 유니코드 넘버(10진수)

	var arr_1st = new Array('ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ','ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ');//초성 19개 
	var arr_2nd = new Array('ㅏ','ㅐ','ㅑ','ㅒ','ㅓ','ㅔ','ㅕ','ㅖ','ㅗ','ㅘ','ㅙ','ㅚ','ㅛ','ㅜ','ㅝ','ㅞ','ㅟ','ㅠ','ㅡ','ㅢ','ㅣ');//중성 21개 
	var arr_3th = new Array('','ㄱ','ㄲ','ㄳ','ㄴ','ㄵ','ㄶ','ㄷ','ㄹ','ㄺ','ㄻ','ㄼ','ㄽ','ㄾ','ㄿ','ㅀ','ㅁ','ㅂ','ㅄ','ㅅ','ㅆ','ㅇ','ㅈ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ');//종성 28개 

	if(char.length>2)
		char=char.charAt(0);
		
	var uninum = escape(char); 

	if(uninum.length<4) 
		return false;//한글이 아니다 

	uninum = parseInt(uninum.replace(/\%u/,''),16);

	if(uninum < char_st || uninum > char_ed) 
		return false;//한글이 아니다 

	var uninum2		= uninum-char_st; 
    var arr_1st_v	= Math.floor( uninum2 / 588); 

    uninum2			= uninum2 % 588; 

    var arr_2nd_v	= Math.floor(uninum2 / 28); 

    uninum2			= uninum2 % 28; 
    var arr_3th_v	= uninum2; 
    var return_arr	= new Array(arr_1st[arr_1st_v],arr_2nd[arr_2nd_v],arr_3th[arr_3th_v]); 

    return return_arr; 
}

// 화면 펼치고 접기
function btnShowHideToggle(val){
	if(val == "plus") {											// 전체 펼치기
		for(var i=0;i<99;i++) {
        	var t_btn			= "btn_" + i;
        	var t_input			= "input_" + i;

			j$('#'+t_btn).attr('src', ImgPATH + 'minus.gif');
			j$('#'+t_btn).attr('title', '접기');

			j$("#"+t_input).show();
		}
	} else if(val == "minus") {									// 전체 접기
		for(var i=0;i<99;i++) {
        	var t_btn			= "btn_" + i;
        	var t_input			= "input_" + i;
			
			j$('#'+t_btn).attr('src', ImgPATH + 'plus.gif');
			j$('#'+t_btn).attr('title', '펼치기');

			j$("#"+t_input).hide();
		}
	} else {													// 각각 토글
    	var t_btn			= "btn_" + val;
    	var t_input			= "input_" + val;

		if(j$('#'+t_btn).attr('src').indexOf('minus.gif') == -1) {
			j$('#'+t_btn).attr('src', ImgPATH + 'minus.gif');
			j$('#'+t_btn).attr('title', '접기');
		} else {
			j$('#'+t_btn).attr('src', ImgPATH + 'plus.gif');
			j$('#'+t_btn).attr('title', '펼치기');
		}

    	j$("#"+t_input).toggle();
	}
}

//파일 다운로드Zip
function CmFileDownZip ( pObjName , menuId , contentCode )
{	
	if (0 == j$("input[name='" + pObjName + "']").size()) {
		showMessageBox({ message : "파일을 선택 해 주세요." });
		return ;
	}
	
	showConfirmBox({
		title : "download"
		, message : "파일을 다운로드 하시겠습니까?"
		, ok_str : "확인"
		, cancel_str : "취소"
		, ok_func : function() {
			var urlPath = j$.url.attr("path");
			var parsArr = [];
			
			j$("input[name='" + pObjName + "']").each(function(){
				parsArr.push('i_arrRecordId=' + this.value);
			});
			
			parsArr.push('i_sDownType=' + j$("input[name='i_sDownType']:checked").val());
			parsArr.push('i_sMenuId=' + j$("input[name='" + menuId + "']").val());
			parsArr.push('i_sContentCode=' + j$("input[name='"+contentCode+"']").val());

			var url		= jsLocalRootPATH + "zip_download.do?" + parsArr.join("&");
			url += "&urlPath=" + urlPath;

			self.location = url;
		}
		, cancel_func : function() {
		}
	});
}

function CmHtmlToPdfFileDown (pJqueryObj)
{	
	
	var frm				= document.frm;
	var oldTarget		= frm.target;
	var oldAction		= frm.action; 
	var iframe			= j$("#pdf_down_iframe");
	var flagConetnt		= j$("input[name='i_sHtmlContent']", "form[name='frm']");

	if (iframe.size() == 0) {
		iframe		= j$("<iframe id='pdf_down_iframe' name='pdf_down_iframe' src='about:blank' style='display:none;'></iframe>").appendTo("body");
	}
	
	if (flagConetnt.size() == 0) {
		flagConetnt = j$("<input type='hidden' name='i_sHtmlContent' value=''/>").appendTo("form[name='frm']");
	}
	
	j$('input[name=i_sHtmlContent]').val(j$("input[name='"+pJqueryObj+"']").val());
	
	frm.target = "pdf_down_iframe";
	frm.action = jsLocalRootPATH+"html_to_pdf_download.do";
	frm.submit();

	frm.target = oldTarget;
	frm.action = oldAction;
}
/**
*
*  Base64 encode / decode
*  http://www.webtoolkit.info/
*
**/
 
var Base64 = {
 
	// private property
	_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
 
	// public method for encoding
	encode : function (input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;
 
		input = Base64._utf8_encode(input);
 
		while (i < input.length) {
 
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
 
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
 
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
 
			output = output +
			this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
			this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);
 
		}
 
		return output;
	},
 
	// public method for decoding
	decode : function (input) {
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;
 
		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
 
		while (i < input.length) {
 
			enc1 = this._keyStr.indexOf(input.charAt(i++));
			enc2 = this._keyStr.indexOf(input.charAt(i++));
			enc3 = this._keyStr.indexOf(input.charAt(i++));
			enc4 = this._keyStr.indexOf(input.charAt(i++));
 
			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;
 
			output = output + String.fromCharCode(chr1);
 
			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}
			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}
 
		}
 
		output = Base64._utf8_decode(output);
 
		return output;
 
	},
 
	// private method for UTF-8 encoding
	_utf8_encode : function (string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";
 
		for (var n = 0; n < string.length; n++) {
 
			var c = string.charCodeAt(n);
 
			if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
 
		}
 
		return utftext;
	},
 
	// private method for UTF-8 decoding
	_utf8_decode : function (utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;
 
		while ( i < utftext.length ) {
 
			c = utftext.charCodeAt(i);
 
			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			}
			else if((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i+1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			}
			else {
				c2 = utftext.charCodeAt(i+1);
				c3 = utftext.charCodeAt(i+2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
 
		}
 
		return string;
	}
 
};

function cmPointDate(date, sChar) {
	var sResult	=	"";
	
	if(date != null && date.length >= 8) {
		var sYear	=	date.substring(0, 4);
		var sMonth	=	date.substring(4, 6);
		var sDay	=	date.substring(6, 8);
		
		sResult	=	sYear + sChar + sMonth + sChar + sDay;
	}
	return sResult;
}

//digit : 원본문자
//size : 새로운 문자의 길이
//attatch : 왼쪽에 채워넣을 문자
function LPad(digit, size, attatch) {

       var add = "";
       digit = digit.toString();

       if (digit.length < size) {

           var len = size - digit.length;

           for (var i = 0; i < len; i++) {

               add += attatch;

           }

       }

       return add + digit;
}

//원단위 #,###
function setComma(str){
       str = ""+str+"";
       var retValue = "";
       for(var i=0; i<str.length; i++) {
           if(i > 0 && (i%3)==0) {
               retValue = str.charAt(str.length - i -1) + "," + retValue;
           } else {
               retValue = str.charAt(str.length - i -1) + retValue;
           }
       }
       return retValue;
}

// 체크 안된 checkbox 값 넘기기.  (배열 시 갯수 마추기 위한)
function checkboxToHidden(f,ele) {
    var ele_h;
    var val;
    
    if (typeof(ele.length) != "undefined") {// checkbox가 배열일경우
        for (var i = 0; i < ele.length; i++) {
            // hidden객체생성, 이름은 checkbox와 같게한다.
            ele_h = document.createElement("input");
            ele_h.setAttribute("type","hidden");
            ele_h.setAttribute("name",ele[i].name);
            ele[i].checked ? val = ele[i].value : val = "";
            ele_h.setAttribute("value",val);
            f.appendChild(ele_h);
    
            // 기존 checkbox의 이름을 이름_dummy로 변경한후 checked = false해준다.
            ele[i].checked = false;
            ele[i].setAttribute("name",ele[i].name + "_dummy");
        }
    } else {// checkbox가 한개
            ele_h = document.createElement("input");
            ele_h.setAttribute("type","hidden");
            ele_h.setAttribute("name",ele.name);
            ele.checked ? val = ele.value : val = "";
            ele_h.setAttribute("value",val);
            f.appendChild(ele_h);
    
            ele.checked = false;
            ele.setAttribute("name",ele.name + "_dummy");   
    }
}


function printConsoleLog(str) {
	if (console != undefined) {
		console.log(str);
	}
}

function getFileSize(fileSize) {
	fileSize = parseInt(fileSize);
	var sReturn = "";
	if(fileSize >= (1 * 1024 * 1024 * 1024)) {
		sReturn = SetNumComma(Math.ceil(fileSize / 1024 / 1024 / 1024)) + "GB";
	} else if(fileSize >= (1 * 1024 * 1024)) {
		sReturn = SetNumComma(Math.ceil(fileSize / 1024 / 1024)) + "MB";
	} else if(fileSize >= (1 * 1024)) {
		sReturn = SetNumComma(Math.ceil(fileSize / 1024)) + "KB";
	} else if(fileSize > 0) {
		sReturn = "1KB";
	}
	return sReturn;
}


/**
 * Date		:	2016-04-05
 * Author	:	Kim giyoon
 * Comment	:	날짜 포맷을 바꿔준다.
 */
Number.prototype.to2 = function() { return (this > 9 ? "" : "0")+this; };
Date.MONTHS = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
Date.DAYS   = ["Sun", "Mon", "Tue", "Wed", "Tur", "Fri", "Sat"];
Date.prototype.getDateString = function(dateFormat) {
  var result = "";
  
  dateFormat = dateFormat == 8 && "YYYY.MM.DD" ||
               dateFormat == 6 && "hh:mm:ss" ||
               dateFormat ||
               "YYYY.MM.DD hh:mm:ss";
  for (var i = 0; i < dateFormat.length; i++) {
    result += dateFormat.indexOf("YYYY", i) == i ? (i+=3, this.getFullYear()                     ) :
              dateFormat.indexOf("YY",   i) == i ? (i+=1, String(this.getFullYear()).substring(2)) :
              dateFormat.indexOf("MMM",  i) == i ? (i+=2, Date.MONTHS[this.getMonth()]           ) :
              dateFormat.indexOf("MM",   i) == i ? (i+=1, (this.getMonth()+1).to2()              ) :
              dateFormat.indexOf("M",    i) == i ? (      this.getMonth()+1                      ) :
              dateFormat.indexOf("DDD",  i) == i ? (i+=2, Date.DAYS[this.getDay()]               ) :
              dateFormat.indexOf("DD",   i) == i ? (i+=1, this.getDate().to2()                   ) :
              dateFormat.indexOf("D"   , i) == i ? (      this.getDate()                         ) :
              dateFormat.indexOf("hh",   i) == i ? (i+=1, this.getHours().to2()                  ) :
              dateFormat.indexOf("h",    i) == i ? (      this.getHours()                        ) :
              dateFormat.indexOf("mm",   i) == i ? (i+=1, this.getMinutes().to2()                ) :
              dateFormat.indexOf("m",    i) == i ? (      this.getMinutes()                      ) :
              dateFormat.indexOf("ss",   i) == i ? (i+=1, this.getSeconds().to2()                ) :
              dateFormat.indexOf("s",    i) == i ? (      this.getSeconds()                      ) :
                                                   (dateFormat.charAt(i)                         ) ;
  }
  return result;
};



/***********************************************
 * NWLEE : doT Template 삽입 반복 로직 
 * @param doT_TemplateId
 * @param injectionObj
 * @returns {String}
***********************************************/
function doTjs_Injection(doT_TemplateId, injectionObj){
	var arrHtml = [];
	var pagefn = doT.template(document.getElementById(doT_TemplateId).text, undefined, undefined);
	arrHtml.push(pagefn(injectionObj));
	return arrHtml.join("");
};

function attachDown(attachId, division, formName,logYn) {
	
	if(division == "" || division == undefined) {
		division = "ODM";
	}
	
	if(formName == "" || formName == undefined) {
		formName = "frm";
	}
	
//	var urlPath = j$.url.attr("path");
	
	if(division == "TIUM") {
		var url		= jsLocalRootPATH + "attach_download.do?attachTemp=" + attachId;
		url += "&urlPath=" + urlPath;
		
		self.location = url;
	} else {
		var url		= jsLocalRootPATH + "attach_odm_link_down.do";
		//var url		= jsLocalRootPATH + "attach_download.do";
		
		$.ajax({
			url : url
			, type : "POST"
			, data : {
				attachTemp : attachId
				,logYn : logYn
				//, urlPath : urlPath
			}
			//, isBlock : true
			, dataType : "json"
			, success : function(data) {
				var frm = j$("form[name='" + formName + "']");
				var oldAction = frm.attr("action");
				
				frm.attr("action", data.data);
				frm.submit();
				
				frm.attr("action", oldAction);
			},error : function(jqXHR, textStatus, errorThrown){
		        //fn_s_failMsg(jqXHR, textStatus, errorThrown);
			}
		});
	}
}

function attachDel(obj, uploadCd, formName) {
	var id = obj.attr("id");
	var tr = obj.parents("tr:eq(0)");
	var action_type = tr.find(".span_action_type").text();
	
	if(action_type == "M") {
		var html = [];
		html.push("<div class='div_attach_del' id='div_"+id+"'>");
		html.push("		<input type='hidden' name='"+uploadCd+"_del_attach_id' value='"+id+"'/>");
		html.push("</div>");
		
		j$(html.join("\n")).appendTo(j$("form[name='"+formName+"']"));
	}
	
	tr.remove();
}

function loadScript(url, succCallBack, failCallBack){
	j$.getScript(url).done(function( script, textStatus ) {
		if(typeof succCallBack == "function"){
			succCallBack();
		}else{
			alert("callback이 function으로 지정되지 않았습니다");
		}
	}).fail(function( jqxhr, settings, exception ) {
		if(typeof failCallBack == "function"){
			failCallBack();
		}else{
			alert( "지정된 스크립트를 가져올 수 없습니다" );
		}
	});
}

function loadCss(url){
	j$("head").append("<link rel='stylesheet' type='text/css' href='"+url+"' />");
}

function fnNoImage(target, fix_w, fix_h) {
	//return;
	var img = jQuery(target);
	
	if (img.next().hasClass("span_no_image")) {
		return;
	}
	
	var span = jQuery("<span class='span_no_image'>No Image</span>").insertAfter(img);
	var fontSize = "9pt";
	var w = img.width();
	var h = img.height();
	
	if (fix_w != undefined) {
		w = fix_w;
	}
		
	if (fix_h != undefined) {
		h = fix_h;
	}
	
	if (w < 80) {
		fontSize = "8pt";
	}
	else if (w < 100) {
		fontSize = "9pt";
	}

	if (w < 50) {
		span.text("No");
	}
	
	span.css({
		"display" : "inline-block"
		, "background-color" : "#efeded"
		, "color" : "#c2c0c1"
		, "line-height" : (h -2) + "px"
		, "text-align" : "center"
		, "font-family" : "Lucida Sans Unicode"
		, "font-size" : fontSize
		, "border" : "1px solid #d8d8d8"
	});
	
	span.width(w - 2);
	span.height(h - 2);
	img.hide();
}

function fnNoUserImage(target, size) {
	//return;
	var img = jQuery(target);
	
	if (img.next().hasClass("span_no_userimage")) {
		return;
	}
	
	var _defulat = {
		width : "90px"
		, height : "90px"
	};
	
	if(size != undefined){
		_defulat.width = size;
		_defulat.height = size;
	}
	
	var non_img = jQuery("<img class='span_no_userimage' src='/IMG/2017/noimg_user.png'/>").insertAfter(img);
	non_img.css(_defulat);
	img.hide();
}

function fnNoUserImage_tong(target, size) {
	//return;
	var img = jQuery(target);
	var span = img.parent();
	var _defulat = {
		width : "90px"
		, height : "90px"
		, "background-image" : "url('/IMG/2017/tong/icon_usrFace.png')"
	};
	
	if(size != undefined){
		_defulat.width = size;
		_defulat.height = size;
	}
	
	span.css(_defulat);
	
}


function cmGetFormData(form){
    var unindexed_array = form.serializeArray();
    var indexed_array = {};

    j$.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
};

//해당 날짜를 현재시간 기준 ~~분전 ~시간 전 어제~시 등의 형식으로 나타내기
function changeBeforeDate(date){
	var sResult = "";
		
		var	starDate	= new Date(date.substring(0, 4), parseInt(date.substring(4, 6), 10) - 1, date.substring(6, 8),
				date.substring(8, 10), date.substring(10, 12), 00);
		
		if (12 <= date.length) {
//			date	= date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6, 8)
//					+ " " + date.substring(8, 10) + ":" + date.substring(10, 12); 
			
			var d = "";
			
			if(date.substring(8, 10) > 11){
				d = "오후";
			}
			else{
				d = "오전";
			}
			
			date	= date.substring(4, 6) + "월 " + date.substring(6, 8)
			+ "일 " + date.substring(8, 10) + ":" + date.substring(10, 12);
			
			var	endDate		= new Date();
			var gap	= parseInt((endDate.getTime() - starDate.getTime()) / 1000 /60);
			
			if(gap == 0){
				sResult = "방금";
			}
			else if(gap<60){
				sResult = gap + "분전";
			}
//			else if(gap>=60 && gap<(60*24)){
//				sResult = parseInt((gap / 60)) + "시간전";
//			}
			else if(gap >= (60 * 24) && gap <(60 * 48)){
				
				var time =starDate.getHours();
			var minute = starDate.getMinutes();
			sResult = time >12 ? "어제 오후 " + (time - 12) + ":" + minute : "어제 오전 " + time +":" + minute;
			}
			else{
				sResult = date;
			}						
		}
		return sResult;
};



/** YHCHOI : 2017.06.12 추가
 * 분류 : 문자함수
 * 요약 : 문자열에서 태그 삭제
 * @param P
 * @returns
 */
function removeHTMLTag(P) {
	return P.replace(/<(\/)?([a-zA-Z]*)(\s[a-zA-Z]*=[^>]*)?(\s)*(\/)?>/g,"");
}

/** YHCHOI : JAVA에 있는 부분 2017.06.12 추가
 * 분류 : 날짜 포맷 변경
 * 요약 : 년월일시분초 [YYYYMMDDHHMMSS]
 * @param P
 * @returns
 */
function getPointDtm(source){
	
	if (trim(source).length < "YYYYMMDDhhmmss".length) { 
		return i_sSource;
	}
	
	return source.substring( 0,  4)
	+ "."        + source.substring( 4,  6)
	+ "."        + source.substring( 6,  8)
	+ "  " + source.substring( 8, 10)
	+ ":"        + source.substring(10, 12)
	+ ":"        + source.substring(12, 14);	
}

/** NWLEE : 2017.08.24
 * 분류 : 티움넷 공통 팝업 리사이즈
 * @param P
 * @returns
 */
function cmPopupResize(popupid, min){
	var layerBox 	= parent.j$("#div_"+popupid);
	var iframeWrap	= parent.j$(".div_iframe_wrap", layerBox);
	var iframe 		= parent.j$("iframe", layerBox);
	var popBody = iframe.contents().find("body");
	
	var height = iframe.contents().find("#popup_wrap").innerHeight();
	
	if(min != undefined){
		if(height < min){
			height = min;
		}
	}
	
	j$(".popup_box_x").height(height);
	layerBox.css("height", height);
	iframeWrap.css("height", height);
	iframeWrap.css("overflow-y", "hidden");
	iframe.css("height", height);
};

/** soheej : 2019.04.15
 * 분류 : javascript에서 엔터값을 BR 태그로 변환 
 * @param str
 * @returns 
 */
function removeHTMLChangeBr(str){
	return str.replace(/(?:\r\n|\r|\n)/g, '<br />');
};

function dhxExcelDownload(opt) {
	var	header	= [];
	header.push(opt.header);
	var	columns	= opt.columns;
	var	excelData	= {};
	var	dataArr		= [];
	excelData.name		= opt.fileName;
	excelData.header	= header;
	excelData.columns	= columns;

	fn_ajax({
		  url		 : opt.url
		, postParam  : opt.postParam
		, success 	 : function(data){
			var	result	= data.result.data;
			for (var i = 0; i < result.length; i++) {
				var	vo	= [];
				for (var j = 0; j < excelData.columns.length; j++) {
					vo.push(result[i][excelData.columns[j]]);
				}
				dataArr.push(vo);
			}
			excelData.data	= dataArr;

			var excel_form		= $("#excel_form");
			var iframe			= $("#excel_down_iframe");

			if (excel_form.size() == 0) {
				excel_form		= $("<form id='excel_form' name='excel_form' action='' method='post'></form>").appendTo("body");
			}
			if (iframe.size() == 0) {
				iframe		= $("<iframe id='excel_down_iframe' name='excel_down_iframe' src='about:blank' style='display:none;'></iframe>").appendTo("body");
			}

			var postData	= $("input[name='data']", excel_form);
			
			if (postData.size() == 0) {
				postData = $("<input type='hidden' name='data' value=''/>").appendTo(excel_form);
			}

			postData.eq(0).val(JSON.stringify(excelData));
			excel_form.attr({"target" : "excel_down_iframe", "action" : '/dhtmlx/commExcelDownload.do' });
			excel_form.submit();
		}
	});
}

function fn_excel_download(url, jsonParams) {
	var html ="<div class=\"layer_dialog layer_alert\">"       
        +"<p class=\"msg\" style=\"text-align: center;padding: 30px 0;color: #707070;font-weight: 700;font-size: 20px;\">다운로드 하시겠습니까?</p>"
        +"<div class=\"layer_btns\" style=\"margin-top: 25px;text-align: center;\">"
          +"<a href=\"javascript:;\" class=\"btnB bg_gray btn_all \" style=\" margin: 0 1px; \">전체 페이지</a>"
          +"<a href=\"javascript:;\" class=\"btnB bg_green btn_cur \" style=\" margin: 0 1px; \">현재 페이지</a>"
        +"</div>"
        +"<button type=\"button\" data-pop=\"btn-close-pop\" data-target=\"#alert_001\" class=\"btn_pop_close\"><span class=\"blind\">팝업 닫기</span></button>"
        +"</div>"
	dhxWindow = new dhx.Window({
	    modal: true	    
	    ,title : "Excel 다운로드"
	    ,width: "500"
	    ,height: "300"
	    ,resizable: true
	    ,movable: true
	    ,header: true
	    ,footer: false
	    ,html : html
	});
	dhxWindow.events.on("AfterShow", function(){
		var excelWin = $(dhxWindow.getContainer());
		dhx.awaitRedraw().then(function(){
			excelWin.find('.btn_all').click(function(){
				jsonParams.i_sFlagExcelAll	= 'Y';
				fn_excel_download_exe(url, jsonParams);
				dhxWindow.hide();
				dhxWindow.destructor();
			});
			excelWin.find('.btn_cur').click(function(){
				fn_excel_download_exe(url, jsonParams);
				dhxWindow.hide();
				dhxWindow.destructor();
			});
		});
	});
	dhxWindow.paint();
	dhxWindow.show();

	/* dhtmlx confirm */
//	dhx.alert({
//		header: "Excel 다운로드",
//		text: "다운로드 하시겠습니까?",
//		buttons: ["전체 페이지", "현재 페이지"],
//		buttonsAlignment:"center",
//	}).then(function(answer){
//		if (answer){
//			jsonParams.i_sFlagExcelAll	= 'Y';
//			fn_excel_download_exe(url, jsonParams);
//		} else {
//			fn_excel_download_exe(url, jsonParams);
//		}
//	});
}
function fn_excel_download_exe(url, jsonParams) {
	var excel_form		= $("#excel_form");
	var iframe			= $("#excel_down_iframe");

	if (excel_form.size() == 0) {
		excel_form		= $("<form id='excel_form' name='excel_form' action='' method='post'></form>").appendTo("body");
	}
	if (iframe.size() == 0) {
		iframe		= $("<iframe id='excel_down_iframe' name='excel_down_iframe' src='about:blank' style='display:none;'></iframe>").appendTo("body");
	}

	excel_form.html('');
	$.each(jsonParams, function(key, value){
		$("<input type='hidden' name='"+key+"' value='"+value+"'/>").appendTo(excel_form);
	});

	excel_form.attr({"target" : "excel_down_iframe", "action" : url });
	excel_form.submit();
}
//캘린더
function getToday(){
    var date = new Date();
    var year = date.getFullYear();
    var month = (1 + date.getMonth());
    var day = date.getDate();

    return year+":"+month+":"+day;
}

function fn_dhxDispToday(date) {
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	var day = date.getDate();
	if (year+":"+month+":"+day == getToday()) {
		return "highlight-date";
	}
	return "";
}

function fn_dhxLinkCss(cell, data, row, col) {
	return "hyperlink";
}