//<![CDATA[

    //우측 마우스 및 드래그 금지 이미지 상세 보기 
	function doScrtyImgPop(img){
		img1= new Image(); 
		img1.src=(img); 
		imgScrtyControll(img); 
	}
	
	function imgScrtyControll(img){
		if((img1.width!=0)&&(img1.height!=0)){ 
			viewScrtyImage(img); 
		}else{
			controller="imgScrtyControll('"+img+"')"; 
			intervalID=setTimeout(controller,20); 
		}
	}

	function viewScrtyImage(img){ 
		var tImg = new Image();
		tImg.src=img; 
		
		var imgWidth = tImg.width;
		var imgHeight = tImg.height;
		var imgRate = imgHeight/imgWidth;
		
		var frameWidth;
		var frameHeight;
		
		if (imgWidth >=1024){
			imgWidth = 1024;
			imgHeight = 1024*imgRate;
		}
		
		if(imgWidth < 200){
			frameWidth = 250;
			frameHeight = 250;	
		}else{
			frameWidth = imgWidth +50;
			frameHeight = imgHeight + 50;
		}
		
		//티움넷 레이아웃으로 보여줄 때
		cmDialogOpen("ImageView", {
				url : "/comm/comm_imgView.do?i_sScrtyImageYn=Y&i_sImgWidth="+imgWidth+"&i_sImgHeight="+imgHeight+"&i_sImgSrc="+img ,
				width : frameWidth ,
				height : frameHeight ,
				modal : true ,
				changeViewAutoSize : true
			});	
	}
		
	function doImgPop(img){
		img1= new Image(); 
		img1.src=(img); 
		imgControll(img); 
	} 
	
	function imgControll(img){
		if((img1.width!=0)&&(img1.height!=0)){ 
			viewImage(img); 
		}else{
			controller="imgControll('"+img+"')"; 
			intervalID=setTimeout(controller,20); 
		}
	}

	function viewImage(img){ 
		var tImg = new Image();
		tImg.src=img; 
		
		var imgWidth = tImg.width;
		var imgHeight = tImg.height;
		var imgRate = imgHeight/imgWidth;
		
		var frameWidth;
		var frameHeight;
		
		if (imgWidth >=1024){
			imgWidth = 1024;
			imgHeight = 1024*imgRate;
		}
		
		if(imgWidth < 200){
			frameWidth = 250;
			frameHeight = 250;	
		}else{
			frameWidth = imgWidth +50;
			frameHeight = imgHeight + 50;
		}
		
		
		//윈도우 새창으로 보여줄 때 방법1
		/*tImg.onload = function() {
				window.open(tImg.src,"Images","width="+imgWidth+",height="+imgHeight);
		};*/
		
		//티움넷 레이아웃으로 보여줄 때
		cmDialogOpen("ImageView", {
				url : "/comm/comm_imgView.do?i_sImgWidth="+imgWidth+"&i_sImgHeight="+imgHeight+"&i_sImgSrc="+img ,
				width : frameWidth ,
				height : frameHeight ,
				modal : true ,
				changeViewAutoSize : true
			});	
		};
		
		function doTabletImgPop(bgImg, frontImg){ 
			bgImg1= new Image(); 
			frontImg1= new Image(); 
			bgImg1.src=(bgImg); 
			frontImg1.src=(frontImg); 
			
			imgTabletControll(bgImg, frontImg); 
		}
		
		function imgTabletControll(bgImg, frontImg){ 
			
			if((bgImg1.width!=0 && bgImg1.height!=0) && (frontImg1.width!=0 && frontImg.height!=0)){ 
				viewTabletImage(bgImg, frontImg); 
			}else{
				controller="imgTabletControll('"+bgImg+"','"+frontImg+"')"; 
				intervalID=setTimeout(controller,20); 
			}
		}
		
		function viewTabletImage(bgImg, frontImg){ 
			
			var tImg = new Image();
			
			var imgWidth;
			var imgHeight;
			var imgRate
			
			var frameWidth;
			var frameHeight;
			
			if(bgImg != 'NONE'){
				tImg.src=bgImg; 
				
				imgWidth = tImg.width;
				imgHeight = tImg.height;
				imgRate = imgHeight/imgWidth;
				
				frameWidth;
				frameHeight;
				
				if (imgWidth >=1024){
					imgWidth = 1024;
					imgHeight = 1024*imgRate;
				}
				
				if(imgWidth < 200){
					frameWidth = 250;
					frameHeight = 250;
				}else{
					frameWidth = imgWidth +50;
					frameHeight = imgHeight + 50;
				}
			}else if (frontImg != 'NONE'){
				tImg.src=frontImg; 
				
				imgWidth = tImg.width;
				imgHeight = tImg.height;
				imgRate = imgHeight/imgWidth;
				
				frameWidth;
				frameHeight;
				
				if (imgWidth >=1024){
					imgWidth = 1024;
					imgHeight = 1024*imgRate;
				}
				
				if(imgWidth < 200){
					 frameWidth = 250;
					 frameHeight = 250;
				}else{
					frameWidth = imgWidth +50;
					frameHeight = imgHeight + 50;
				}
			}
			
			if(bgImg == 'NONE'){
				bgImg = '';
			}
			
			if (frontImg == 'NONE'){
				frontImg ='';
			}
			
			
			//윈도우 새창으로 보여줄 때 방법1
			/*tImg.onload = function() {
					window.open(tImg.src,"Images","width="+imgWidth+",height="+imgHeight);
			};*/
			
			//티움넷 레이아웃으로 보여줄 때
			cmDialogOpen("ImageView", {
					url : "/comm/comm_imgView.do?i_sTabletView=Y&i_sImgWidth="+imgWidth+"&i_sImgHeight="+imgHeight+"&i_sBgImgSrc="+bgImg+"&i_sFrontImgSrc="+frontImg ,
					width : frameWidth ,
					height : frameHeight ,
					modal : true ,
					changeViewAutoSize : true
				});	
			};
		
	//윈도우 새창으로 보여줄 때 방법2
		/* var O="width="+frameWidth+",height="+frameHeight+",scrollbars=no"; 
		var imgWin=window.open("","",O); 
		imgWin.document.write("<html><head><title>:*:*:*: 이미지상세보기 :*:*:*:*:*:*:</title></head>");
		imgWin.document.write("<body topmargin=10 leftmargin=10>");
		imgWin.document.write("<img src="+img+"  width="+imgWidth+"height="+imgHeight+"onclick='self.close()' style='cursor:hand;'>");
		imgWin.document.close();*/
			  


//]]>
