// 제품정보 팝업화면 컨트롤
var CmCdSearch = function(name,url,opt){
	this.idx = 0;
	this.objNm = name;
	this.url = url;
	this.inputCode = opt.inputCode;
	this.inputNameKo = opt.inputNameKo;
	this.inputNameEn = opt.inputNameEn;
	this.inputNameCn = opt.inputNameCn;
	this.searchInput = opt.searchInput;
	this.callback = opt.callback;
}
CmCdSearch.prototype ={
	getIndex : function(obj){
		var arrObj		= $('input[id='+obj.id+']');
		var len			= arrObj.length;
		
		var idx			= -1;			
		for (var i = 0; i < len; i++)
		{
			if (obj == arrObj[i])
			{
				this.idx = i;
				console.log(this.idx);
				return;
			}
		}
	}
}

//업로드관련
var OreqReg = {
	addEvent :{
		attachSuccEvent : function(attData, uploadCd){
			var attach_size = attData.v_attach_size;
			var attach_lnm = attData.v_attach_lnm;
			var attach_pnm = attData.v_attach_pnm;
			var attach_type = attData.v_attach_type;
			var attach_mgtid = attData.v_attach_mgtid;
			var attach_id = attData.v_attach_id;
			var attach_idx = attData.v_attach_idx;
//			var pk1 = j$("input[name='i_sRecordId']").val();	
			var table = j$(".table_" + uploadCd).eq(attach_idx);
			
			var obj = {
				v_attach_id : attach_id
				, v_attach_lnm : attach_lnm
				, v_attach_type : attach_type
				, n_attach_size : attach_size
				, v_attach_pnm : attach_pnm
				, v_attach_mgtid : attach_mgtid
				, v_upload_cd : uploadCd
				, v_pk1 : ""
				, v_pk2 : uploadCd
				, v_pk3 : ""
				, v_pk4 : ""
				, v_pk5 : ""
				, v_buffer1 : ""
				, v_buffer2 : ""
				, v_buffer3 : ""
				, v_buffer4 : ""
				, v_buffer5 : ""				
				, del_img_url : ImgPATH + "btn/btn_del_small.gif"
			};
			var pagefn = doT.template(document.getElementById("dot_upload").text, undefined, undefined);
			j$(pagefn(obj)).appendTo(table.find("tbody"));
		}
	}
}