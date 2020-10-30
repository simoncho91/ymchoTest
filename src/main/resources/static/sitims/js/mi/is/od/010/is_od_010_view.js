var odmMatrView = {
	init : function() {
		odmMatrView.fn.fn_sumConper();
		odmMatrView.fn.fn_allerSum();
	},
	fn : {
		/**
		 * 함수 설명 : 향알러젠 함량 합계 계산
		 * 작성일자 : 2020.09.07
		 */
		fn_allerSum : function(){
			var conTr = $("#con_table .con_tr");
			var trSize = conTr.size();
			var tmpCnt = -1;
			
			if(trSize > 0) {
				for(var i=0; i<trSize; i++){
					var row_num = (i == 0) ? "" : "_"+i;
					var aller_per_input = $(".arrAllergenPer"+row_num);
					
					if(aller_per_input.length == 0) {
						continue;
					}else {
						tmpCnt++;
					}
					
					var sum = 0.0;
					var T = Number('1e' + 10);
					
					aller_per_input.each(function() {
						var per = 0;
						per = $(this).text();
						sum = Math.round((sum + per * 1) * T) / T;
					});
					
					$(".allergen_sum_td").eq(tmpCnt).text(sum.toFixed(14));
					
				}
			}
		},
		/**
		 * 함수 설명 : 구성성분 함량 합계 계산
		 * 작성일자 : 2020.09.07
		 */
		fn_sumConper : function() {
			var con_tr = $(".con_tr");
			var sum = 0.0;
			var T = Number('1e'+10);
			con_tr.each(function(){
				var per = $(".conper_td", this).text();
				sum = Math.round((sum + per*1)*T)/T;
			});
			$("#conper_sum").text(sum.toFixed(14));
		}
	}
};

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
			var pk1 = j$("input[name='i_sRecordId']").val();	
			var table = j$(".table_" + uploadCd).eq(attach_idx);
			
			var obj = {
				v_attach_id : attach_id
				, v_attach_lnm : attach_lnm
				, v_attach_type : attach_type
				, n_attach_size : attach_size
				, v_attach_pnm : attach_pnm
				, v_attach_mgtid : attach_mgtid
				, v_upload_cd : uploadCd
				, v_pk1 : pk1
				, v_pk2 : uploadCd
				, v_pk3 : ""
				, v_pk4 : ""
				, v_pk5 : ""
				, del_img_url : ImgPATH + "btn/btn_del_small.gif"
			};
			var pagefn = doT.template(document.getElementById("dot_upload").text, undefined, undefined);
			j$(pagefn(obj)).appendTo(table.find("tbody"));
		}
	}
}