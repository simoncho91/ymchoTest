
// Grid내 헤더의 표현 스타일정의
var hStyleR = "color:#ff186a;"; // 빨강
var hStyleE = "color:#3b5ef7;"; // 파랑
var hStyleP = " <i class='fa fa-external-link'/>"; // 팝업링크 컬럼 표시

document.write("<link id='cssTemplate' rel='stylesheet' type='text/css' href='/css/jquery-ui-1.10.4.custom.min.css'>");
document.write("<link id='cssFontAs'   rel='stylesheet' type='text/css' href='/dhtmlx/codebase/fonts/font_awesome/css/font-awesome.min.css'>	");
document.write("<link id='cssDhtmlx'   rel='stylesheet' type='text/css' href='/dhtmlx/codebase/fonts/font_roboto/roboto.css'>                   ");
document.write("<link id='cssDhtmlx'   rel='stylesheet' type='text/css' href='/dhtmlx/codebase/dhtmlxvault.css'>                                ");

document.write("<script type='text/javascript' src='/dhtmlx/codebase/dhtmlx.js'></script>      		      	");
document.write("<script type='text/javascript' src='/dhtmlx/codebase/dhtmlxvault.js'></script>      	  	");
document.write("<script type='text/javascript' src='/dhtmlx/codebase/ext/swfobject.js'></script>      		  	");
document.write("<script type='text/javascript' src='/js/jquery/jquery-1.11.1.min.js'></script>  		  	");
document.write("<script type='text/javascript' src='/js/jquery/jquery-ui-1.10.4.custom.min.js'></script>  	");
document.write("<script type='text/javascript' src='/js/json2.js'></script>  							  	");


/*
 * 필수요소 정의
 * 		그외 페이징(commonPaging), 첨부파일처리(commonAttach), 리포트폼호출(commonReport)은 페이지 내 개별 선언
 */
document.write("<script type='text/javascript' src='/js/dhtmlx/common.js'></script>             ");
document.write("<script type='text/javascript' src='/js/dhtmlx/commonGrid.js?20200513'></script>");
document.write("<script type='text/javascript' src='/js/dhtmlx/commonMsg.js'></script>          ");
document.write("<script type='text/javascript' src='/js/dhtmlx/commonPopup.js'></script>   		");
document.write("<script type='text/javascript' src='/js/dhtmlx/commonTransaction.js'></script>  ");
document.write("<script type='text/javascript' src='/js/dhtmlx/commonUtils.js'></script>        ");
document.write("<script type='text/javascript' src='/js/dhtmlx/commonValidation.js'></script>   ");
document.write("<script type='text/javascript' src='/js/dhtmlx/commonAttach.js'></script>  		");
document.write("<script type='text/javascript' src='/js/dhtmlx/commonPaging.js'></script>  		");


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



/**
   시스템 사용 스킨 설정
 * 1. 기존 디자인(dhtmlx 4.4 스킨) 
 */
var dhtmlx_skin = "dhx_skyblue";
var css_ref     = "/dhtmlx/codebase/dhtmlx_5.css";
var dhtmlx_image_path  = "/dhtmlx/codebase/imgs/";   
var toolbar_icons_path = "/images/dhtmlx/toolbar/dhx_skyblue/";


/**
 * 2. dhtmlx 5.2의 기본 스킨("material")
var dhtmlx_skin = "";   // material는 기본값이라 설정을 하지 말아야 함
var css_ref     = "/dhtmlx/codebase/dhtmlx.css";
var dhtmlx_image_path  = "/dhtmlx/codebase/imgs/";   
var toolbar_icons_path = "/images/dhtmlx/toolbar/dhx_material/";
 */

/**
   시스템 사용 스킨 설정
 * 3. dhtmlx 5.2의 확장 스킨("web")

var dhtmlx_skin = "web";
var css_ref     = "/dhtmlx/skins/"+dhtmlx_skin+"/dhtmlx_long.css";
var dhtmlx_image_path  = "/dhtmlx/skins/"+dhtmlx_skin+"/imgs/";   
var toolbar_icons_path = "/images/dhtmlx/toolbar/dhx_"+dhtmlx_skin+"/";
dhtmlx_skin     = "dhx_"+dhtmlx_skin;
 */

/*document.write("<link id='cssTemplate' rel='stylesheet' type='text/css' href='/css/layout_long.css'>");*/
document.write("<link id='cssTemplate' rel='stylesheet' type='text/css' href='/css/dhtmlx/template_5.css'>");
document.write("<link id='cssDhtmlx'   rel='stylesheet' type='text/css' href='"+css_ref+"'>");
//dhtmlxTree CSS 추가
document.write("<link id='cssDhtmlxTree'   rel='stylesheet' type='text/css' href='/sitims/CSS/dhtmlxTree.css'>");
// 조회폼 영역의 높이값 정보를 저장한 배열
var SearchFormHeight = [    0
						,  68
						,  99
						, 130
					   ];

