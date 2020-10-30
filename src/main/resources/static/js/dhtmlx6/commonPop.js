
/**
 * post방식 윈도우 오픈 함수
 * @param target		팝업명
 * @param url			호출 url
 * @param option		window.open option
 * @param paramKey[]	파라미터 key
 * @param paramVal[]	파라미터 val
 */
function windowOpenPost(target, url, option, paramKey, paramVal){
	var paramLen	= paramKey.length;
	var frm = $('<form></form>');

	frm.attr('id', '_frm');
	frm.attr('action', url);
	frm.attr('method', 'post');
	frm.attr('target', target);
	
	for(i=0 ; i < paramLen ; i++){
		frm.append($("<input type='hidden' value="+paramVal[i]+" name="+paramKey[i]+">"));
	}
	
	frm.appendTo('body');
	
	window.open("", target, option);
	frm.submit();
	$('#_frm').remove();
}
 
function fn_calendarSet(id,callback,bolDis){
//	dhx.i18n.setLocale("calendar", {
//		// short names of months
//		monthsShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
//		// full names of months
//		months: ['1월(JAN)', '2월(FEB)', '3월(MAR)', '4월(APR)', '5월(MAY)', '6월(JUN)', '7월(JUL)', '8월(AUG)', '9월(SEP)', '10월(OCT)', '11월(NOV)', '12월(DEC)'],
//		// short names of days
//		daysShort: ["일", "월", "화", "수", "목", "금", "토"],
//		// full names of days
//		days: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"]
//	});

	$('input[id='+id+']').each(function(i,obj){
		var calendar = new dhx.Calendar(null, {
		    disabledDates: function(date) {	        
		        return bolDis?(date.getTime()<(new Date()).getTime()):false;
		    }, dateFormat: "%Y-%m-%d", css:"dhx_widget--bg_white dhx_widget--bordered" });

		var popup = new dhx.Popup();
		popup.attach(calendar);
		calendar.events.on("change", function (date,oldDate,click) {
		    $(obj).val(calendar.getValue());
		    if(typeof callback=='function') callback(obj,date,oldDate,click);
		    popup.hide();
		});
		$(obj).on("click", function () {
			var	val	= $(obj).val();
			if (val) {
				val	= new Date($(obj).val());
			} else {
				val	= new Date();
			}
			calendar.setValue(val);
		    popup.show(this);
		});
	});
}

var dhxWindow;
function fn_pop(option){
	dhxWindow = new dhx.Window({
	    modal: true
	    ,title : option.title
	    ,width:option.width
	    ,height:option.height
	    ,resizable: true
	    ,movable: true
	    ,header: true
	    ,footer: false
	    ,html : "<iframe name='dhxPopWin' frameborder=\"0\" style=\"width: 100%; height: 100%; position: relative;\" src="+option.url+"></iframe>"
	});
	dhxWindow.show();
}

function fn_pop2(option){
	if (typeof option.id == 'undefined') {
		return ;
	}

	if (jQuery('#div_'+option.id).html() != null) {
		jQuery("#"+option.id).attr('src','');
	}

	if (jQuery('#div_'+option.id).html() == null) {
		var arrHtml	= [];
		
		arrHtml.push("<div id='div_"+option.id+"' style='display:;padding:0px;margin:0px;overflow-x:hidden;'>");
		arrHtml.push("<iframe id='"+option.id+"' name='"+option.id+"' style=\"width: 100%; height: 100%;\" src='about:blank' style='overflow-x:hidden' scrolling='auto' marginwidth='0' marginheight='0' frameborder='0' vspace='0' hspace='0'></iframe>");
		arrHtml.push("</div>");
		jQuery(arrHtml.join("")).appendTo("body");
	}

	jQuery("#"+option.id).attr({src: option.url}).load(function () {
		var popBody = jQuery("#"+option.id).contents().find("body");
		var height = option.height < popBody.height() ? popBody.height() + 220 : option.height;
		$("#div_"+option.id).hide();
		if (null == dhxWindow) {
			dhxWindow	= new dhx.Window({
				modal: true
				,title : option.title
				,width : option.width
				,height: height
				,resizable : true
				,movable : true
				,header : true
				,footer : true
			});
			dhxWindow.footer.data.add({
				type: "spacer",
			});
			dhxWindow.footer.events.on("click", function(id){
				if (id == "confirm") {
					dhxWindow.hide()
				}
			});
			dhxWindow.footer.data.add({
				type: "button",
				view: "flat",
				size: "medium",
				color: "primary",
				value: "닫기",
				id: "confirm",
			});
		}

		dhxWindow.attachHTML(jQuery("#div_"+option.id).html());
		dhxWindow.paint();
		dhxWindow.show();
	});
}

function fn_popClose(callback){
	if(typeof callback =='function') callback();
	if(!fn_isNull(dhxWindow)){
		dhxWindow.hide();	
		dhxWindow.destructor();
	}else{
		if(!fn_isNull(parent.dhxWindow)){
			parent.dhxWindow.hide();	
			parent.dhxWindow.destructor();			
		}
	}
}
