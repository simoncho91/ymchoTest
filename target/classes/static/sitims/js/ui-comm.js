(function ($) {
  "use strict";

  var $doc = $(document),
    $win = $(window);

  // layerPop
  var layerPop = function ($win) {
    var $wins = $win || $doc,
      $html = $("html"),
      $wrap = $(".wrap"),
      $btn_popOpen = $wins.find("[data-pop=btn-open-pop]"),
      $btn_popClose = $wins.find("[data-pop=btn-close-pop]"),
      $scrollTop;

    $btn_popOpen.on("click", function () {
      $scrollTop = $(window).scrollTop();
      $html.addClass("is-scr-block");
      $(window).scrollTop(0);
      $wrap.scrollTop($scrollTop);
      var target = $(this).attr("data-target");
      $(target).addClass("is_show");
    });

    $btn_popClose.on("click", function () {
      var target = $(this).attr("data-target");
      $(target).queue(function () {
        $(this).removeClass("is_show");
        $(this).dequeue();
        $html.removeClass("is-scr-block");
        $(window).scrollTop($scrollTop);
        $wrap.scrollTop(0);
      });
    });
  };
  layerPop();  

  //lnb_menu on/off
  var lnb_onoff = function() {
    var lnb_menu = $(document).find('.lnb_menu');
    var btn_lnb_toggle = lnb_menu.find('.btn_lnb_toggle');

    btn_lnb_toggle.on('click',function(){
      var wrap = $(document).find('#wrap');
      var checkClass = wrap.hasClass('is_lnb_open');
      if(!checkClass){
        wrap.addClass('is_lnb_open');
      }else{
        wrap.removeClass('is_lnb_open');
      }
    });
  }
  lnb_onoff();

  //lnb_dep2_onoff
  var lnb_dep2_onoff = function() {
    var lnb_list = $(document).find('.lnb_menu .lnb_inner > ul');
    var btn_dep2 = lnb_list.find('button');
    var open_chk = lnb_list.find('button.on').siblings('.dep2').show().addClass('is_open');

    btn_dep2.on('click',function(){
      var checkOpen = $(this).hasClass('is_open');
      var this_dep2_list = $(this).siblings('.dep2');
      if(!checkOpen){
        $(this).addClass('is_open');
        this_dep2_list.slideDown(150).addClass('is_open');
      }else{
        $(this).removeClass('is_open');
        this_dep2_list.slideUp(150).removeClass('is_open');
      }
    });
  }
  lnb_dep2_onoff();
  
})(jQuery);
