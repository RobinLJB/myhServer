//首公告滚动 可以控制一次滚动多个
(function($){
    $.fn.extend({
        Scroll:function(opt,callback){
                //参数初始化
                if(!opt) var opt={};
                var _this=this.eq(0).find("ul:first");
                var lineH=_this.find("li:first").height(), //获取行高
                line=opt.line?parseInt(opt.line,10):parseInt(this.height()/lineH,10), //每次滚动的行数，默认为一屏，即父容器高度
                speed=opt.speed?parseInt(opt.speed,10):500, //卷动速度，数值越大，速度越慢（毫秒）
                timer=opt.timer?parseInt(opt.timer,10):3000; //滚动的时间间隔（毫秒）
                if(line==0) line=1;
                var upHeight=0-line*lineH;
                //滚动函数
                scrollUp=function(){
                        _this.animate({
                                marginTop:upHeight
                        },speed,function(){
                                for(i=1;i<=line;i++){
                                        _this.find("li:first").appendTo(_this);
                                }
                                _this.css({marginTop:0});
                        });
                }
                //鼠标事件绑定
                _this.hover(function(){
                        clearInterval(timerID);
                },function(){
                        timerID=setInterval("scrollUp()",timer);
                }).mouseout();
        }        
    })
  })(jQuery);

/*$(function(){
    //轮播图
    $('.index_flexslider').flexslider({
        animation: "fade",
        directionNav: false,
        pauseOnAction: false,
        pauseOnHover:true,
        animationSpeed:650,
        slideshowSpeed:5000
      });
    //直投项目
    $('.project_flexslider').flexslider({
        animation: "slide",
        animationLoop: true,
        slideshow: false,
        itemWidth: 303,
        itemMargin: 30,
        minItems: 3,
        maxItems: 3,
        directionNav: true,
        prevText: "", //String: 上一项的文字
        nextText: "" //String: 下一项的文字
      });
    //债权转让
    $('.transfer_flexslider').flexslider({
        animation: "slide",
        animationLoop: true,
        slideshow: false,
        itemWidth: 303,
        itemMargin: 30,
        minItems: 3,
        maxItems: 3,
        directionNav: true,
        prevText: "", //String: 上一项的文字
        nextText: "" //String: 下一项的文字
      });
    
})

*/
