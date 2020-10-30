/*************************************************************************
* 코난 달력기능구현
*-----------------------------
* 기능구현 : 정민철
* 날짜         : 2010.04.06
*
* History ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
* 20100405 #version1 : 주석강화
* 20100325 #version1 : 동일 달력보기 버튼 처리 펑션화 및 버그수정, loadCalendar() 주석강화
* 20100323 #version2 : 날짜선택시 hidden처리
* 20100323 #version1 : 달력보기 기능 토글기능구현
* 20100322 #version2 : 오늘 날짜선택 기능추가
* 20100322 #version1 :  + 처리가 string concat이 아닌 연산으로 된던 버그수정
***************************************************************************/

var konanCalendar = {
	/*
		//전역변수 관련
		var this.x;			// X축
		var this.y;			//Y축
		var this.divid;		//달력이 그려질 DIV-ID
		var this.textid;		//날짜가 입력될 TEXT-ID
		
		//   이벤트순서(큰로직상)
		//    loadCalendar() -->  writeCalendar() --> toggleCalendar() or hiddenCalendar() or showCalendar()
	*/
	
	/*****************************************************************
	* 디자인 변경시 수정이 필요한 펑션 목록 (딴 funciton은 수정하지마세요)
	* ----------------------------------------------------------------------------------------
	*  (1)  loadCalendar : function(textid, divid, x, y)     <--- 펑션의 진입점!!!!! (커스터마이징시수정)
	*  (2)  toggleCalendar : function () 
	*  (3)  hiddenCalendar : function ()
	*  (4)  showCalendar : function()
	*  (5)  writeCalendar : function (y, m, d)                  <-- 달력 html이 담겨있음
	******************************************************************/
	

	/**
	* 달력 로딩
	*
	* @ param   textid			-   달력날짜가 세팅될 <input type="text" ../> 의 ID
	* @ param   divid			-   달력이 그려질 <DIV> 의 ID
	* @ param   x			-   x축 px
	* @ param   y			-   y축 px
	*
	* @ return   			-   void
	**/	
	loadCalendar : function(textid, divid, x, y)   {

		// 커스터마이징된 기능 --(date라디오 체크해서 기간입력일때문 기능동작하게 처리함
		var dataobj = document.getElementsByName("date");
		var data = "";
		for (var i=0; i< dataobj.length; i++)
		{
			if (dataobj[i].checked)
			{
				data = dataobj[i].value;
				break;
			}
		}
		if (data != "select")
		{
			alert("'기간입력'을 선택후 달력이용이 가능합니다");
			return;
		}
		//-- 커스터마이징된 기능 --(date라디오 체크해서 기간입력일때문 기능동작하게 처리함
		

		var mydate 	= this.getTextBox(this.textid);	
		var y, m, d; 
		var boolReCall = this.isRecallAction(textid); //동일한 (달력보기) 버튼인지 체크
		
		//[1] 정보 세팅
		this.setInfo(textid,divid);		//레이어,텍스트 ID값 세팅
		this.setLocation(x,y);			//위치 정보 세팅 

		
		//[2] 날짜값 체크해서 text안의 날짜로 보여줄지, 오늘날짜로 보여줄지 체크함
		if (mydate.length == 8)
		{
			y 	= parseInt(mydate.substring(0, 4));
			m	= parseInt(mydate.substring(4, 6));
			d	= parseInt(mydate.substring(6, 8));
		}
		else
		{
			var tmp_date = new Date(); //오늘
			y	= tmp_date.getFullYear();
			m	= tmp_date.getMonth() + 1;
			d	= tmp_date.getDate();
		}
		
		//[3] 달력 그리기
		this.writeCalendar(y, m, d);
		
		//[4] 달력 보여주기 / 숨기기 
		if (boolReCall)
		{//동일한 버튼을 눌렀으므로 토글처리해야함
			this.toggleCalendar();
		}
		else
		{//딴 버튼을 눌렀으므로 무조건 보여줘야함
			this.showCalendar();
		}
			
	},
	
	/**
	* 달력 보여주고 숨기는 기능 
	*
	* this.divid
	* @ return   			-   void
	**/	
	toggleCalendar : function () {
		var obj = document.getElementById(this.divid);
		
		if (obj != null)
		{
			if (obj.className == "hidden")
			{
				this.showCalendar();
			}
			else
			{
				this.hiddenCalendar();
			}
		}
		
	},
	
	/**
	* 달력숨기는(hidden) 처리 (디자인에 따라 구성이 달라짐\)
	*
	* this.divid
	* @ return   			-   void
	**/	
	hiddenCalendar : function () {
		var obj = document.getElementById(this.divid);
		if (obj != null)
		{
			obj.className = "hidden";
		}
	},
	
	/**
	* 달력을 보여주는(show) 처리 (디자인에 따라 구성이 달라짐)
	*
	* this.divid
	* @ return   			-   void
	**/		
	showCalendar : function() {
		var obj = document.getElementById(this.divid); 
		if (obj != null)
		{
			obj.className = "";
		}
	},
	
	/**
	* 해당 레이어에 달력 그리기 (this.divid)
	*
	* @ param   y			-   년도 (yyyy)
	* @ param   m			-   월 (MM)
	* @ param   d			-   일 (DD)
	*
	* @ return   			-   void
	**/	
	//
	writeCalendar : function (y, m, d) {
		

		//오늘날짜 정보
		var todayis = new Date();
		var today_year 	= todayis.getFullYear();
        var today_month = todayis.getMonth() + 1;
        var today_day 	= todayis.getDate();

		
		
		var text = "";
		// [1] 검색상위창 (년도, 월선택)
		 text += '<div id="calendar_box" style="left:'+this.locationX+'px; top:'+this.locationY+'px;">' + '\n';
		text += ' <div class="cl_select">';
		text += '	<a class="" id="" href="javascript:'+ this.className +'.preYear('+y+','+m+','+d+',-1);"><img src="../../IMG/KO/sc/btn_cl_pre.gif" alt="전년도"/></a>';
		text += '     	<select onChange="'+this.className +'.writeCalendar(this.value,'+m+','+d+');" name="select4" id="select4" style="font-size:11px;font-family:"돋움", dotum;">';
		
		//년(YYYY) 출력
		for (var i=(parseInt(y)-3); i<=(parseInt(y)+3); i++)
		{
			if (i == y)
				text += '      		<option selected value='+i+'>'+ i +'년</option>';
			else
				text += '      		<option value='+i+'>'+ i +'년</option>';
		}
		text += '		</select>';
		text += '	<a onclick="" class="" id="" href="javascript:'+ this.className +'.preYear('+y+','+m+','+d+',1);"><img src="../../IMG/KO/sc/btn_cl_nxt.gif" alt="다음년도"  /></a>';
		text += '   <a onclick="" class="" id="" href="javascript:'+ this.className +'.preMonth('+y+','+m+','+d+',-1);"><img src="../../IMG/KO/sc/btn_cl_pre.gif" alt="전월"/></a>';
		text += ' 	<select onChange="'+this.className +'.writeCalendar('+y+',this.value,'+d+');" name="select5" id="select5" style="font-size:11px;font-family:"돋움", Dotum;">';
		
		//월(MM)처리
		for (var i=1; i<=12; i++)
		{
			if ( i == m )
				text += ' 	  <option selected value='+i+'>'+ i +'월</option>';
			else
				text += ' 	  <option value='+i+'>'+ i +'월</option>';
		}
		
		text += '   </select>';
		text += '   <a class="" id="" href="javascript:'+ this.className +'.preMonth('+y+','+m+','+d+',1);"><img src="../../IMG/KO/sc/btn_cl_nxt.gif" alt="다음월"  /></a>';
		text += ' </div>';
		
		//[2] 테이블 그리기
		text += '<table border="0" cellpadding="0" cellspacing="0" class="calendar">';
		text += '<tr>';
		text += '         <th class="sunday">일</th>';
		text += '         <th>월</th>';
		text += '         <th>화</th>';
		text += '         <th>수</th>';
		text += '         <th>목</th>';
		text += '         <th>금</th>';
		text += '         <th class="saturday">토</th>';
		//text += '</tr>';
		
		//일(DD)출력
		var d1 = (y+(y-y%4)/4-(y-y%100)/100+(y-y%400)/400 
			  +m*2+(m*5-m*5%9)/9-(m<3?y%4||y%100==0&&y%400?2:3:4))%7; 
		for (i = 0; i < 42; i++) { 
			if (i%7==0) text += '</tr>\n<tr>'; 
			
			if (i%7==0 && i>=31) 
				break;
			
			if (i < d1 || i >= d1+(m*9-m*9%8)/8%2+(m==2?y%4||y%100==0&&y%400?28:29:30)) 
				text += '<td> </td>'; 
			else 
			{
				var style_text = '';

				//--디자인컨트롤-------------------------------------------------------------------
				//[1] select된 날짜 디자인
				if ( (i+1-d1)  == d )
					style_text = 'class="select1"';
				else
					style_text = '';
				
				//[2] todaty 디자인
				if (( (i+1-d1) )==today_day && m==today_month && y==today_year)
				{
					if (style_text.length <= 0)
						style_text = 'class="today"';
					else	//select AND today
						style_text = 'class="today select1"';
				}
				//[3] 일요일 빨간색처리
				if ( !(i%7) )
					style_text += ' style="color:red;"';
				//--//디자인컨트롤-------------------------------------------------------------------	
				
				text += '<td><a href="javascript:'+this.className+'.choiceDay('+ y + ',' + m + ',' + (i+1-d1)  + ')" '+style_text+'>' + (i+1-d1) + '</a></td>'; 
			}
		} 
		
		text += '  </tr>';
		text += '  </table>';
		text += '  <span class="cl_close"><a href="javascript:'+this.className+'.hiddenCalendar(\''+ this.divid +'\')">닫기</a></span>';
		text += '  <span class="cl_close"><a href="javascript:'+this.className+'.writeCalendar('+today_year +','+today_month+','+today_day+')">오늘</a></span>';
		text += '  </div>';

		this.setDivhtml(text);
	},


	
	
	/***********************************************************************
	*
	*  ※ 주의 : 아래쪽 코드는  변경할 필요 없습니다 !!!!!!   ^_^
	*                  
	***********************************************************************/
	
	//////////////////////////////////////////////////////////////////
	// 버튼 이벤트
	//////////////////////////////////////////////////////////////////

	/**
	* ago년 전후 출력 (작년, 내년)
	*
	* @ param   y			- YYYY   년도
	* @ param   m			- MM      월
	* @ param   d			- DD        일
	* @ param   ago			- ago년 전후
	* 
	* @ return   			-   void
	**/	
	preYear : function(y,m,d,ago)		{
		var tmpdate = new Date(parseInt(y)+parseInt(ago), parseInt(m)-1, d);
		this.writeCalendar(tmpdate.getFullYear(),  tmpdate.getMonth() + 1,  tmpdate.getDate());

	},
	
	/**
	* ago월 전후 출력  (전달, 다음달)
	*
	* @ param   y			- YYYY   년도
	* @ param   m			- MM      월
	* @ param   d			- DD        일
	* @ param   ago			- ago월 전후
	* 
	* @ return   			-   void
	**/	
	preMonth : function (y,m,d,ago)	{
		var tmpdate = new Date(parseInt(y), parseInt(m)-1+parseInt(ago), d);
		this.writeCalendar(tmpdate.getFullYear(),  tmpdate.getMonth() + 1,  tmpdate.getDate());
	},
	
	/**
	* yy,mm,dd를 input박스에 옮겨줌 (YYYYMMDD)
	*
	* @ param   yy			- YYYY   년도
	* @ param   mm			- MM      월
	* @ param   dd			- DD        일
	* 
	* @ return   			-   void
	**/	
	choiceDay : function (yy,mm,dd) {
		var mydate = "";
		mydate = yy;
		
		if (mm<10)
			mydate += "0" + mm;
		else
			mydate += ""+mm;
		
		if (dd<10)
			mydate += "0" + dd;
		else
			mydate += ""+dd;
		
		this.setTextBox(mydate); 
		
		this.writeCalendar(yy,mm,dd);
		//날짜선택하면 숨겨버림
		this.hiddenCalendar();
	},
	//////////////////////////////////////////////////////////////////
	// 값 세팅,리턴  및 데이터 확인 
	//////////////////////////////////////////////////////////////////	
	/**
	* 달력 ID, 텍스트창ID 세팅
	*
	* @ param   textid			- 달력에서 선택한 날짜 YYYYMMDD값이 세팅될 <input type="text" .../> 의 ID 
	* @ param   divid			- 달력이 그려지는 div의 ID명
	* 
	* @ return   			-   void
	**/	
	setInfo : function(textid, divid) {
		this.className = "konanCalendar";
		this.divid = divid;
		this.textid = textid;
	},
	
	/**
	* 달력의 x축 y축 위치 잡기
	*
	* @ param   x			- 달력의 x축 위치
	* @ param   y			- 달력의 y축 위치
	* 
	* @ return   			-   void
	**/	
	setLocation : function(x, y){
		this.locationX = x;
		this.locationY = y;
	},
	
	/**
	* 다수의 달력 버튼을 컨트롤 할때, 동일한 [달력보기] 버튼을 클릭했는지 체크함 
	*
	* @ param   textid			- 달력에서 선택한 날짜 YYYYMMDD값이 세팅될 <input type="text" .../> 의 ID 
	* 
	* @ return   			-   true / false  (동일유무)
	**/	
	isRecallAction : function (textid)	{
	
		var equalsTextid = true;

		if (typeof(textid) == "undefined" && typeof(this.textid) == "undefined")
			equalsTextid = false;
		else if (textid == this.textid)
			equalsTextid = true;
		else
			equalsTextid = false;
			
		return equalsTextid;
	},
	
	/**
	* 텍스트박스에 세팅
	*
	* @ param   str			- YYYYMMDD 데이터
	* 
	* this.textid 를 가지는 input창에 값세팅
	* @ return   			-   void
	**/	
	setTextBox : function(str) {
		var obj = document.getElementById(this.textid);
		
		if (obj != null)
			obj.value  = str;
	},
	
	/**
	* 텍스트박스값 리턴
	*
	* this.textid 를 가지는 input창의 값 리턴
	* @ return   			- YYYYMMDD 데이터
	**/	
	getTextBox : function () {
		var obj = document.getElementById(this.textid);
		if (obj != null)
			return obj.value;
		else
			return "";
	},
	
	/**
	* 레이어에 달력 그리기
	*
	* @ param	html			- 달력의 html 데이터
	*
	* @ return   			- void
	**/	
	//레이어에 그리기
	setDivhtml : function(html) {
		var obj = document.getElementById(this.divid);
		if (obj!=null)
		{
			obj.innerHTML = html;
		}
	}
};