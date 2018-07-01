// JavaScript Document
$(function(){
	//页面头部下拉菜单
	$(".upperli,.marginleft15").hover(function(){
		$(this).find(".downmenu").fadeIn();
	},function(){
		$(this).find(".downmenu").fadeOut()
	})
	
	//导航 我要理财
	$("#wylc").hover(function(){
		$(this).find(".lcul").fadeIn();
	},function(){
		$(this).find(".lcul").fadeOut()
	})
	
	$("#gywm").hover(function(){
		$(this).find(".aboutul").fadeIn();
	},function(){
		$(this).find(".aboutul").fadeOut()
	})
	
	//我要理财 切换
	$(".amongqh").click(function(){
		var amongindex=$(this).index() + 1;
		$("div[class^='block00']").hide();
		$(".block00"+amongindex).show();
		$(".amongqh").removeClass("selecion");
		$(this).addClass("selecion");
	})
	
	
	//新闻中心
	$(".newsitem").hover(function(){
		$(this).css({"color":"#ff5a5a"})
	},function(){
		$(this).css({"color":"#666"})
	})
	
	//帮助中心
	$(".helpitem").click(function(){
		var helpindex=$(this).attr("data-id");
		$("div[class ^='help00']").hide();
	    $(".help00"+helpindex).show();
		$(".helpitem").removeClass("helped")
		$(this).addClass("helped")
	})
	
	//新手入门
	$(".arrays01a").hover(function(){
		$(this).attr("class","arrays01b");
		var dataid=$(this).attr("data-id");
		$(".modesimg").attr("src","images/gra/modes00"+dataid+".jpg");
	},function(){
		$(this).attr("class","arrays01a")
	})
	$(".arrays02a").hover(function(){
		 var dataid=$(this).attr("data-id");
		$(".modesimg").attr("src","images/gra/modes00"+dataid+".jpg");
		$(this).attr("class","arrays02b")
	},function(){
		$(this).attr("class","arrays02a")
	})
	$(".arrays03a").hover(function(){
		var dataid=$(this).attr("data-id");
		$(".modesimg").attr("src","images/gra/modes00"+dataid+".jpg");
		$(this).attr("class","arrays03b")
	},function(){
		$(this).attr("class","arrays03a")
	})	
	//个人中心左侧菜单
	$(".satnav").click(function(){
		$(this).next(".satnavnext").toggle();
		var thissrc=$(this).find("span.perspan").find("img").attr("src")
		if(thissrc=="../images/pic/arrdown.png"){
			 $(this).find("span.perspan").find("img").attr("src","../images/pic/arrup.png")
			}
			else{
			$(this).find("span.perspan").find("img").attr("src","../images/pic/arrdown.png")
			}
	})
	
	
	//我的投资筛选
	$(".investitem div[class ^= 'investitem02']").click(function(){
		$(this).parent(".investitem").find("div[class ^= 'investitem02']").attr("class","investitem02s");
		$(this).attr("class","investitem02");
	})
	
	$(".investext").one("focus",function(){
		$(this).val("")
	})
	
	//选择银行
	$(".banks img").click(function(){
		$(this).prev("input[type='radio']").attr("checked",true)
	})
	$(".credit00 div").click(function(){
		var creditindex=$(this).index() +1 ;
		$("div[class ^= 'select0']").hide();
		$(".select0"+creditindex).show();
		$(".credit00 div").removeClass("credit02");
		$(this).addClass("credit02")
	})
	
	$("#nextstep").click(function(){
		$(".credit").show()
		})
		
	$(".charge div").click(function(){
		var chargeindex=$(this).index() +1 ;
		 
		$("div[class ^= 'recharge0']").hide();
		$(".recharge0"+chargeindex).show();
		$(".charge div").removeClass("charge02");
		$(this).addClass("charge02")
	})
	
	
	//我要签到
	var signwidth=$(window).width();
	var signheight=$(window).height();
	$(".signinbor").css({"left":signwidth/2-450/2+"px","top":signheight/2-360/2+"px"});
	
	$(".spanclose,.signbtn").click(function(){
		$(".signinbg").hide();
	})
	
	$(".qiandao").click(function(){
		$(".signinbg").show();
	})
	
	$(".trpointer").click(function(){
		$(this).next("tr.hidetr").toggle()
	})
	
	
	$(".timeframeleft span").click(function(){
		$(".timeframeleft span").removeClass("timecolr");
		$(this).addClass("timecolr")
	})
	
	$("input[type=checkbox][name='mess2']").click(function(){
		var thischecked=$(this).attr("checked")
		if(thischecked=="checked"){
			$("input[type=checkbox][name='mess']").attr("checked",true)
		}
		else{
			$("input[type=checkbox][name='mess']").attr("checked",false)
		}
		
	})
	
	$(".tidings01").click(function(){
		$(this).next(".tidings02").toggle()
	})
	
	
	$(".mineaccount").hover(function(){
		$(this).find(".accout").show()
	},function(){
		$(this).find(".accout").hide()
	})
	
	$(".perfortitle div").click(function(){
		var perindex=$(this).index() + 1;
		$(".perfortitle div").attr("class","perfor2")
		$(this).attr("class","perfor")
		$("div[class ^= perfo0]").hide();
		$(".perfo0"+perindex).show()
	})
	
	//右侧工具栏
	//$(".tools").css("top",signheight/1.5-208/2+"px")
	$(".tools02").click(function(){
		$(window).scrollTop(0)
	})
	$(".tools02").hover(function(){
		$(this).find(".tools03").find("img").attr("src","images/pic/myin03a.png");
		$(this).find(".tools04").css("color","#eb4f38");
	},function(){
		$(this).find(".tools03").find("img").attr("src","images/pic/myin03.png");
		$(this).find(".tools04").css("color","#999");
	})
	
	
	$(".tools01").hover(function(){
		$(this).find(".tools03").find("img").attr("src","images/pic/myin02a.png");
		$(this).find(".tools04").css("color","#eb4f38");
		$(this).find(".tools0301").fadeIn();
	},function(){
		$(this).find(".tools03").find("img").attr("src","images/pic/myin02.png");
		$(this).find(".tools04").css("color","#999");
		$(this).find(".tools0301").fadeOut();
	})
	
	$(".tools00").hover(function(){
		$(this).find(".tools03").find("img").attr("src","images/pic/myin01a.png");
		$(this).find(".tools04").css("color","#eb4f38");
		$(this).find(".tools0301").fadeIn();
	},function(){
		$(this).find(".tools03").find("img").attr("src","images/pic/myin01.png");
		$(this).find(".tools04").css("color","#999");
		$(this).find(".tools0301").fadeOut();
	})
	
	
	//立即投资弹出框
	$(".pop").css({"left":signwidth/2-640/2+"px","top":signheight/2-240/2+"px",})
	$(".bewrite").click(function(){
		$(".mask2").show()
	})
	
	$(".guanbi,.xxbtn").click(function(){
		$(".mask2").fadeOut()
	})	
	
	//首页切换div的切换条位置
	$(".strdiv").css({"left":signwidth/2-150/2+"px"});
	
	$(".str001").click(function(){
		document.getElementById("indexsrh2").style.display='none';
		document.getElementById("indexsrh1").style.display='block';
		$(".str001").addClass("strselect")
		$(".str002").removeClass("strselect")
	})
	
	$(".str002").click(function(){
		document.getElementById("indexsrh1").style.display='none';
		document.getElementById("indexsrh2").style.display='block';
		$(".str001").removeClass("strselect")
		$(".str002").addClass("strselect")
	})
})


//倒计时
function GetRTime(){
       var EndTime= new Date('2016/06/20 00:00:00');
       var NowTime = new Date();
       var t =EndTime.getTime() - NowTime.getTime();
       var d=Math.floor(t/1000/60/60/24);
       var h=Math.floor(t/1000/60/60%24);
       var m=Math.floor(t/1000/60%60);
       var s=Math.floor(t/1000%60);

       document.getElementById("t_d").innerHTML = d + "天";
       document.getElementById("t_h").innerHTML = h + "时";
       document.getElementById("t_m").innerHTML = m + "分";
       document.getElementById("t_s").innerHTML = s + "秒";
   }
   setInterval(GetRTime,0);
   
//首页2个DIV切换
var i=1;
var tmpobj = setInterval("addtime()",3000); 
function addtime(){
if (i==1){
document.getElementById("indexsrh1").style.display='none';
document.getElementById("indexsrh2").style.display='block';
$(".str001").removeClass("strselect")
$(".str002").addClass("strselect")

i=2;
}
else{
document.getElementById("indexsrh2").style.display='none';
document.getElementById("indexsrh1").style.display='block';
$(".str001").addClass("strselect")
$(".str002").removeClass("strselect")
i=1;
}} 