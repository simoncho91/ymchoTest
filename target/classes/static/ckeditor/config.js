/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {

	  
  config.toolbarGroups = [ {
		name : 'styles',
		groups : [ 'styles' ]
	}, {
		name : 'basicstyles',
		groups : [ 'basicstyles', 'cleanup' ]
	}, {
		name : 'colors',
		groups : [ 'colors' ]
	}, {
		name : 'paragraph',
		groups : [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ]
	},
	//    { name: 'insert', groups: [ 'insert' ] },
	//    { name: 'links', groups: [ 'links' ] },
	{
		name : 'clipboard',
		groups : [ 'undo', 'clipboard' ]
	}, {
		name : 'document',
		groups : [ 'mode', 'document', 'doctools' ]
	}, {
		name : 'editing',
		groups : [ 'find', 'selection', 'spellchecker', 'editing' ]
	}, {
		name : 'forms',
		groups : [ 'forms' ]
	}, '/', '/', {
		name : 'tools',
		groups : [ 'tools' ]
	}, {
		name : 'others',
		groups : [ 'others' ]
	}, {
		name : 'about',
		groups : [ 'about' ]
	} ];
	config.removeButtons = 'Styles,Format,Save,NewPage,Preview,Print,Templates,PasteText,PasteFromWord,Find,Replace,SelectAll,Scayt,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Subscript,Superscript,CopyFormatting,RemoveFormat,Outdent,Indent,Blockquote,CreateDiv,BidiLtr,BidiRtl,Language,Unlink,Anchor,Flash,HorizontalRule,Smiley,PageBreak,Iframe,Maximize,ShowBlocks,About,JustifyBlock,Strike';

	config.removePlugins = 'elementspath';

	//config.extraPlugins = 'autogrow';
	//config.extraPlugins = 'uploadimage';
	//config.autoGrow_onStartup = true;
	config.resize_enabled = false;
	config.basicEntities = false;
	//config.height = 400;
	config.filebrowserUploadUrl = '/attach/ckeditor/imageupload.do'; // UploadImage Plugin
	config.imageUploadUrl = '/attach/ckeditor/imageupload2.do'; // Copy & Paste or Drag & Drop

	//config.contentsCss = ['../bootstrap-3.3.6-dist/css/bootstrap.min.css',  '../bootstrap-3.3.6-dist/css/bootstrap-theme.min.css'];
	config.pasteFilter = null; // Disable paste filter to avoid confusion on browsers on which it is enabled by default and may affect results.
	config.tabSpaces = 4; // insert tab space.
	//config.codeSnippet_theme = 'sunburst'; // Code Snippet Theme.
	config.font_names = '맑은 고딕; 돋움; 굴림; 바탕; 궁서;' + config.font_names; // 폰트 설정
	config.allowedContent = {
		$1 : {
			// Use the ability to specify elements as an object.
			elements : CKEDITOR.dtd,
			attributes : true,
			styles : true,
			classes : true
		}
	};
	config.disallowedContent = 'script; *[on*]; iframe';
};
