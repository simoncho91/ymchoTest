/**
 * jQuery custom selectboxes
 *
 * Copyright (c) 2008 Krzysztof Suszyski (suszynski.org)
 * Licensed under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 *
 * @version 0.6.1
 * @category visual
 * @package jquery
 * @subpakage ui.selectbox
 * @author Krzysztof Suszyski <k.suszynski@wit.edu.pl>
**/
jQuery.fn.selectbox = function(options){
	var selectItem;
	/* Default settings */
	var settings = {
		className: 'jquery-selectbox',
		animationSpeed: "normal",
		listboxMaxSize: 10,
		replaceInvisible: false
	};
	var commonClass = 'jquery-custom-selectboxes-replaced';
	var listOpen = false;
	var showList = function(listObj) {
		var selectbox = listObj.parents('.' + settings.className + '');
		listObj.slideDown(150, function(){ //settings.animationSpeed
			listOpen = true;
		});
		selectbox.addClass('selecthover');
		jQuery(document).bind('click', onBlurList);

		/* Z-INDEX 2013-04-10 */
		selectbox.parent('.cont').css('z-index','10');

		if(selectItem != undefined)
		{
			selectItem.addClass('listelementSelect');
			selectItem.parent().scrollTop(25 * (selectItem.index()));
		}
		
		msgBoxHide(true);
		
		return listObj;
	}
	var hideList = function(listObj) {
		var selectbox = listObj.parents('.' + settings.className + '');
		listObj.slideUp(150, function(){ //settings.animationSpeed
			listOpen = false;
			jQuery(this).parents('.' + settings.className + '').removeClass('selecthover');
			/* Z-INDEX 2013-04-10 */
			jQuery(this).parents('.' + settings.className + '').parent('.cont').css('z-index','9');
		});
		jQuery(document).unbind('click', onBlurList);
		
		msgBoxHide(false);
		
		return listObj;
	}
	var onBlurList = function(e) {
		var trgt = e.target;
		var currentListElements = jQuery('.' + settings.className + '-list:visible').parent().find('*').andSelf();
		if(jQuery.inArray(trgt, currentListElements)<0 && listOpen) {
			hideList( jQuery('.' + commonClass + '-list') );
		}
		return false;
	}
	var clickAction = function(e){
		var thisMoreButton = jQuery(e.currentTarget);
		var otherLists = jQuery('.' + settings.className + '-list')
			.not(thisMoreButton.siblings('.' + settings.className + '-list'));
		hideList( otherLists );
		var thisList = thisMoreButton.siblings('.' + settings.className + '-list');
		if(thisList.filter(":visible").length > 0) {
			hideList( thisList );
		}else{
			showList( thisList );
		}
	}
	var mouseenterAction = function(e){
		//jQuery(e.currentTarget).addClass('morebuttonhover');
	}
	var mouseleaveAction = function(e){
		//jQuery(e.currentTarget).removeClass('morebuttonhover');
	}


	/* Processing settings */
	settings = jQuery.extend(settings, options || {});
	/* Wrapping all passed elements */
	return this.each(function() {
		var _this = jQuery(this);
		//if(_this.filter(':visible').length == 0 && !settings.replaceInvisible)
			//return;
		var replacement = jQuery(
			'<span class="' + settings.className + ' ' + commonClass + '">' +
				'<span class="' + settings.className + '-moreButton" />' +
				'<a href="#select" class="' + settings.className + '-currentItem" />' +
				'<span class="' + settings.className + '-list ' + commonClass + '-list" />' +				
			'</span>'
		);
		var cnt = 0;
		jQuery('option', _this).each(function(k,v){
			cnt++;
			var v = jQuery(v);
			var listElement =  jQuery('<a href="#select" class="' + settings.className + '-item value-'+v.val()+' item-'+k+'">' + v.text() + '</a>');

			listElement.click(function(){
				var thisListElement = jQuery(this);
				var thisReplacment = thisListElement.parents('.'+settings.className);
				var thisIndex = thisListElement[0].className.split(' ');

				for( k1 in thisIndex ) {
					if(/^item-[0-9]+$/.test(thisIndex[k1])) {
						thisIndex = parseInt(thisIndex[k1].replace('item-',''), 10);
						break;
					}
				};
				var thisValue = thisListElement[0].className.split(' ');
				for( k1 in thisValue ) {
					if(/^value-.+$/.test(thisValue[k1])) {
						thisValue = thisValue[k1].replace('value-','');
						break;
					}
				};
				thisReplacment
					.find('.' + settings.className + '-currentItem')
					.text(thisListElement.text());
				thisReplacment
					.find('select')
					.val(thisValue)
					.triggerHandler('change');
				var thisSublist = thisReplacment.find('.' + settings.className + '-list');
				if(thisSublist.filter(":visible").length > 0) {
					hideList( thisSublist );
				}else{
					showList( thisSublist );
				}

				if(selectItem != undefined)
				{
					selectItem.removeClass('listelementSelect');
				}
				selectItem = jQuery(this);
			}).bind('mouseenter',function(){
				jQuery(this).addClass('listelementhover');
			}).bind('mouseleave',function(){
				jQuery(this).removeClass('listelementhover');
			});

			jQuery('.' + settings.className + '-list', replacement).append(listElement);
			if(v.filter(':selected').length > 0) {
				jQuery('.'+settings.className + '-currentItem', replacement).text(v.text());
				selectItem = listElement;
			}
		});
		
		if(_this.attr("disabled") || _this.attr("disabled") == "disabled"){
			replacement.css({opacity:0.5, cursor:"default"});
			replacement.find('.' + settings.className + '-moreButton').css({cursor:"default"});
		}else if(_this.attr("readonly") || _this.attr("readonly") == "readonly"){
			replacement.css({opacity:0.5, cursor:"default"});
			replacement.find('.' + settings.className + '-moreButton').css({cursor:"default"});
		}else{
			replacement.find('.' + settings.className + '-currentItem').click(clickAction).bind('mouseenter',mouseenterAction).bind('mouseleave',mouseleaveAction);
			replacement.find('.' + settings.className + '-moreButton').click(clickAction).bind('mouseenter',mouseenterAction).bind('mouseleave',mouseleaveAction);
		}


		_this.hide().replaceWith(replacement).appendTo(replacement);
		var thisListBox = replacement.find('.' + settings.className + '-list');
		var thisListBoxSize = thisListBox.find('.' + settings.className + '-item').length;
		if(thisListBoxSize > settings.listboxMaxSize)
			thisListBoxSize = settings.listboxMaxSize;
		if(thisListBoxSize == 0)
			thisListBoxSize = 1;
		var thisListBoxWidth = Math.round(_this.width() + 35); //selectBoxWidthSize

		// browser.chrome & safari ????
		var userAgent = navigator.userAgent.toLowerCase();
		jQuery.browser.chrome = /chrome/.test(navigator.userAgent.toLowerCase());
		if(jQuery.browser.chrome){
			jQuery.browser.safari = false;
		}
		if(jQuery.browser.safari){
			thisListBoxWidth = thisListBoxWidth * 1.45;
		}
		
		replacement.css('width', thisListBoxWidth + 'px');
		if(cnt > 9)
		{
			thisListBox.css({
				width: Math.round(thisListBoxWidth) + 'px' //ListBoxWidthSize
				,height: '15em' //selectBoxHeightSize
			});
		}
		else
		{
			thisListBox.css({
				width: Math.round(thisListBoxWidth) + 'px' //ListBoxWidthSize
			});
		}
	});
}
jQuery.fn.unselectbox = function(){
	var commonClass = 'jquery-custom-selectboxes-replaced';
	return this.each(function() {
		var selectToRemove = jQuery(this).filter('.' + commonClass);
		selectToRemove.replaceWith(selectToRemove.find('select').show());
	});
}

// refresh selectbox
jQuery.fn.refresh_selectbox = function(e){
	var commonClass = 'jquery-selectbox';
	return this.each(function() {
		var selectToRemove = jQuery(this).parents('.' + commonClass);
		selectToRemove.unselectbox();
		jQuery(this).selectbox();
	});
}