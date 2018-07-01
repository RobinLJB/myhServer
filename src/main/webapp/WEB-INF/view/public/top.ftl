<div class="menu-container">
	<div class="container" style="height: 80px;">
		<div class="zhonghui-logo">
			<a href="${context}/index.html" title="功德融"><img src="${context}/asset/front/invest/images/LOGOgd.png" height="53" alt="功德融"></a>
		</div>
		<div class="navigator">
			<ul class="clearfix header-nav">
				<li class="ground-item1">
					<a href="${context}/index.html">首页</a>
				</li>
				<li class="ground-item-xg" style="width: 35px;"><img src="${context}/asset/front/invest/images/xg.png"></li>
				<!--"-->
				<li id="tz" class="ground-item2">
					<a href="#" class="lia">投融资</a>

				</li>
				<ul class="top_t" style="    top: 72px;">
					<li style="float: none;height:20px;">
						<a href="${context}/loan.html">我要投资</a>
					</li>
					<li style="float: none;height:20px;">
						<a href="${context}/ucenter/loan/apply.html">我要融资</a>
					</li>
					<li style="float: none;height:49px;">
						<a href="${context}/loca.html?screen=1">债权流转</a>
					</li>
				</ul>
				<li class="ground-item-xg" style="width: 35px;"><img src="${context}/asset/front/invest/images/xg.png"></li>
				<li class="ground-item3">
					<a href="${context}/page/baozhang.html">我要功德</a>
				</li>
				<li class="ground-item-xg" style="width: 35px;"><img src="${context}/asset/front/invest/images/xg.png"></li>
				<li class="ground-item4">
					<a href="${context}/category/9.html">信息披露</a>
				</li>
				<li class="ground-item-xg" style="width: 35px;"><img src="${context}/asset/front/invest/images/xg.png"></li>
				<li class="ground-item5" style="position: relative;">
					<#if Session.MEMBER??>
					<a href="${context}/ucenter/home.html">${Session.MEMBER.username!}</a>
					<#else>
					<a href="${context}/ucenter/login.html">我的账户</a>
				<span style="position: absolute;top: 30px;left: 105px;"><img src="${context}/asset/front/invest/images/bs.png"></span></li>
					
					</#if>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
		$(".lia").hover(function() {
		$(".top_t").show();
	}, function() {
		$(".top_t").hide();
	});


	$(".top_t").hover(function(){
		$(".top_t").show();
	}, function() {
		$(".top_t").hide();
	});
/*		var pathname = window.location.pathname.slice(1);
	if(pathname.indexOf('index.html') != -1) {
		$('.ground-item:eq(0)').children('a').addClass("active");

	} else if(pathname.indexOf('ucenter/') != -1){
		$('.ground-item:eq(4)').children('a').addClass("active");
		
 	} else if(pathname.indexOf('loan.html') != -1 || pathname.indexOf('loca.html') != -1|| pathname.indexOf('creditor/') != -1|| pathname.indexOf('welfare/') != -1|| pathname.indexOf('loan/') != -1) {
		$('.ground-item:eq(1)').children('a').addClass("active");

	} else if(pathname.indexOf('baozhang') != -1 || pathname.indexOf('meritda') != -1) { 
		$('.ground-item:eq(2)').children('a').addClass("active");

	} else if(pathname.indexOf('category') != -1) {
		$('.ground-item:eq(3)').children('a').addClass("active");
		
    }*/

</script>