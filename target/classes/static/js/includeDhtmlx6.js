
// Grid내 헤더의 표현 스타일정의
var hStyleR = "color:#ff186a;"; // 빨강
var hStyleE = "color:#3b5ef7;"; // 파랑
var hStyleP = " <i class='fa fa-external-link'/>"; // 팝업링크 컬럼 표시

document.write("<link id='cssTemplate' rel='stylesheet' type='text/css' href='/css/dhtmlx/template_6.css'>");
document.write("<link id='cssDhtmlx'   rel='stylesheet' type='text/css' href='/css/layout.css'>");
document.write("<link id='cssDhtmlx'   rel='stylesheet' type='text/css' href='/dhtmlx6/codebase/suite.min.css'>");
document.write("<link id='cssDhtmlx'   rel='stylesheet' type='text/css' href='/sitims/CSS/common.css'>");

document.write("<script type='text/javascript' src='/dhtmlx6/codebase/suite.min.js'></script>      		    ");
document.write("<script type='text/javascript' src='/js/jquery/jquery-1.11.1.min.js'></script>  		  	");
document.write("<script type='text/javascript' src='/js/pdfobject/pdfobject.min.js'></script>  		  		");
document.write("<script type='text/javascript' src='/js/json2.js'></script>  							  	");

/*
 * 필수요소 정의
 * 		그외 페이징(commonPaging), 첨부파일처리(commonAttach), 리포트폼호출(commonReport)은 페이지 내 개별 선언
 */
document.write("<script type='text/javascript' src='/js/commonUtils.js'></script>        ");
document.write("<script type='text/javascript' src='/js/dhtmlx6/common.js'></script>");
document.write("<script type='text/javascript' src='/js/dhtmlx6/commonGrid.js'></script>");
document.write("<script type='text/javascript' src='/js/dhtmlx6/commonTransaction.js'></script>  ");
document.write("<script type='text/javascript' src='/js/dhtmlx6/commonValidation.js'></script>  ");
document.write("<script type='text/javascript' src='/js/dhtmlx6/commonPop.js'></script>  ");
document.write("<script type='text/javascript' src='/js/dhtmlx6/commonPaging.js'></script>  ");
document.write("<script type='text/javascript' src='/sitims/js/util/cm_function.js'></script>  ");

// 조회폼 영역의 가로 디자인 설정(한줄에 조회항목이 3개 ~ 4개 인 경우 구분)
var SearchFormStyle3 = { type:"settings", position:"label-left", labelWidth:"115", inputWidth:"285", offsetLeft:"10", offsetTop:"1"};
var SearchFormStyle4 = { type:"settings", position:"label-left", labelWidth:"115", inputWidth:"172", offsetLeft:"10", offsetTop:"1"};

// 등록폼 영역의 너비값 정보를 저장한 배열
var RegisterFormWidth = [    0
					    ,  420
						,  670
						,  970
					   ];


//등록폼 영역의 가로 디자인 설정(한줄에 입력 항목이 1개 ~ 3개 인 경우 구분)
var RegisterFormStyle1 = { type:"settings", position:"label-left", labelWidth:"120", inputWidth:"200", offsetLeft:"10", offsetTop:"1"  };
var RegisterFormStyle2 = { type:"settings", position:"label-left", labelWidth:"120", inputWidth:"150", offsetLeft:"10", offsetTop:"1"  };
var RegisterFormStyle3 = { type:"settings", position:"label-left", labelWidth:"120", inputWidth:"150", offsetLeft:"10", offsetTop:"1"  };

// 조회폼 영역의 높이값 정보를 저장한 배열
var SearchFormHeight = [    0
						,  87
						, 123
						, 174
					   ];

// 그리드 편집여부 판단을위한 colum 데이터 저장 변수
var g_gridEditColVal = new Object();

var GirdCellHeight 		= 28; 
var GirdHeaderHeight 	= 30;