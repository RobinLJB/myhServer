
<style>
	.yh{
 text-align:center;

    width:1200px;
    margin:0 auto;
    height: 64px;
    line-height: 64px;

}
.tools00, .tools01 {
    background: #a37344;
    height: 48px;

    cursor: pointer;
}
.tools01 {
        height: 48px;
    background: #a37344;
    
    cursor: pointer;
}
.tools {
   border: none;
}
.tools00, .tools05 {
    background: #a37344;
        height: 48px;
        margin-bottom: 5px;

    cursor: pointer;
}
.tools02 {
        height: 48px;
    cursor: pointer;
    margin-top: 5px;
    background:#a37344 ;
}
.flexslider {
    position: relative;
    height: 100px;
        width: 100%;
    overflow: hidden;
}
.slides {
    position: relative;
    z-index: 1;
}
.i{
	height: 32px;
}
.bigredfont2 {
    color: #ff3e35!important;
    font-size: 21px!important;
    font-weight: bold!important;
    font-family: Arial!important;
    margin-top: 5px;
}
a {
    text-decoration: none;
    cursor: pointer;
    outline: none;
    color: #3b2213;
}
img {
    vertical-align: middle;
}
.tg{
	width: 17%;
	display: inline-block;
	height: 20px;
}
.flex-control-nav {
   display: none;
}
.p{
	height:56px ;
}
.yqlj a{
	color: #737373;
}
#top{display: none}
</style>
<div class="footer1">
	<div class="container">
		<div class="footer-cell footer-cell-a">
			<div class="kefu-logo">
				<img src="${context}/asset/front/invest/css/img/logo.png">
			</div>
			<div style="    font-size: 15px;    padding-top:8px;">一站式“公益＋金融”互金平台</div>
			<div class="txt-h1-max"></div>
		</div>
		<div class="footer-cell footer-cell-b" style="padding-left: 5%;">
			<div class="ft-cell-b-p txt-normal-min address" style="    height: 20px;">服务时间：工作日9:00-17:00</div>
			<div class="ft-cell-b-p txt-normal-min service" style="margin-top: 2em;">客服QQ：1336631814</div>
			<div class="ft-cell-b-p txt-normal-min service">客服邮箱：1336631814@qq.com</div>
		</div>
		<div class="footer-cell footer-cell-c">
			<div class="sqr-cell" style="float: right;">
				<img src="${context}/asset/front/images/dswei.jpg" alt="微店">
				<div class="txt-normal-min">关注微店</div>
			</div>
			<div class="sqr-cell" style="float: right;">
				<img src="${context}/asset/front/invest/css/img/app_ewm.png" alt="功德融">
				<div class="txt-normal-min">关注公众号</div>
			</div>
			
		</div>
	</div>
	<div class="yh">
			
    <div class="flexslider">
	
	<ul class="slides">
			
			<li>	
				<#if (partnerList?size>0)>
				<#list partnerList as list>
					<#if (list_index < 5)>
							<a href="http://${list.link!}"  target="_blank">
							<div class="tg"><img class="p"  width="156" height="56" src="${list.path!}" alt="${list.title!}"></div>
							</a>
					</#if>		
				</#list>
				</#if>
			</li>
			<li>
				<#if (partnerList?size>0)>
				<#list partnerList as list>
					<#if (list_index >= 5)>
							<a href="http://${list.link!}"  target="_blank">
							<div class="tg"><img class="p" width="156" height="56" src="${list.path!}" alt="${list.title!}"></div>
							</a>
					</#if>		
				</#list>
				</#if>
			</li>
	</ul>
   </div>

	</div>
	<div class="fo1_bottom container" style="width:810px;color: #3b2213;min-height: 6px;border: none;font-size:19px;">
		<ul>
			<li><a href="${context}/category/19.html#id_19">关于我们</a></li>
			<li><a href="${context}/category/9.html#id_9">信息披露</a></li>
			<li><a href="${context}/category/17.html#id_17">帮助中心</a></li>
			<li><a href="${context}/category/18.html#id_18">名词解释</a></li>
			<li><a href="${context}/category/20.html#id_20">新闻资讯</a></li>
			<li><a href="${context}/category/21.html#id_21">联系我们</a></li>
		</ul>
	</div>
	
</div>
<div class="footer_bottom">
    <div style="width:1000px;font-size: 15px;margin:0 auto;  color: #737373;  text-align: center;line-height: 30px;">
	<div class="yqlj">友情链接：
	<#if (friendlyList?size>0)>
		<#list friendlyList as list>
				<a  target="_blank" href="http://${list.link!}"> ${list.title!} </a>
		</#list>
	</#if>	
	</div>
	<div>版权所有  功德融 @2017 备案号：皖ICP备17005224号-1   <script src="https://s95.cnzz.com/z_stat.php?id=1261692443&web_id=1261692443" language="JavaScript"></script>	</div>
    </div>
    </div>


<div class="tools" style="height: auto;bottom: 130px;background: none">
	<ul>
		<li class="tools05">
			<a href="http://wpa.qq.com/msgrd?v=3&amp;uin=1336631814&amp;site=qq&amp;menu=yes">
			<div class="tools03"><img class="i" style="margin-bottom: 16px;" src="${context}/asset/front/images/qq.png" /></div>
			</a>
			<div class="tools04"></div>
			<ul class="tools0301">
				
			</ul>
			

		</li>
		<li class="tools00">
			<div class="tools03"><img class="i" src="${context}/asset/front/images/ip.png" /></div>
			
			<ul class="tools0301">
				<li class="tools0302">
					<div class="win90">
						<div class="regulations2">客服热线</div>
						<p class="space"></p>
						<p class="bigredfont2">181-0969-3373</p>
					</div>

				</li>
			</ul>
		</li>
		<li class="tools01">
			<div class="tools03"><img class="i" src="${context}/asset/front/images/ch.png" /></div>
			
			<ul class="tools0301">
				<li class="tools0302">
					<div class="win90">
						
						<img style="height:115px;" src="${context}/asset/front/images/code.jpg" />
						<div style="text-align: center;line-height: 6px;color: #585856;">扫一扫关注我们</div>
					</div>

				</li>
			</ul>
		</li>
		<li class="tools02" id="top">
			<div class="tools03"><img class="i" src="${context}/asset/front/images/myin03.png" /></div>
			
		</li>
	</ul>
</div>
	<script type="text/javascript" src="${context}/asset/front/js/jquery.SuperSlide2.js"></script>
	<script type="text/javascript" src="${context}/asset/front/js/jquery.flexslider-min.js"></script>
	<script type="text/javascript" src="${context}/asset/front/js/modernizr-custom-v2.7.1.min.js"></script>
	<script type="text/javascript" src="${context}/asset/front/js/cookie.js"></script>
	<script type="text/javascript" src="${context}/asset/front/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="${context}/asset/front/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$('.flexslider').flexslider({
			directionNav: false,
			pauseOnAction: false,
		});
	});
	</script>
<script>
	  window.onscroll = function () {
		  if (document.documentElement.scrollTop + document.body.scrollTop > 100) {
			  document.getElementById("top").style.display = "block";
		}else {
		    document.getElementById("top").style.display = "none";
	    }
	};
		var speed = 400;//滚动速度
		$("#top").click(function () {
			$('html,body').animate({
				scrollTop: '0px'
			},speed);			
		});
		
</script>
</script>
	<script type="text/javascript">
		$(function(){
			$(".tools02").hover(function(){
				$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/myin03.png");
				$(this).css("background","#e3e3e3");
			},function(){
				$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/myin03.png");
				$(this).css("background","#a37344");
			})
			
			$(".tools05").hover(function(){
				$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/qq.png");
				$(this).css("background","#e3e3e3");
				$(this).find(".tools0301").fadeIn();
			},function(){
				$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/qq.png");
				$(this).css("background","#a37344");
				$(this).find(".tools0301").fadeOut();
			})
			$(".tools01").hover(function(){
				$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/ch.png");
				$(this).css("background","#e3e3e3");
				$(this).find(".tools0301").fadeIn();
			},function(){
				$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/ch.png");
				$(this).css("background","#a37344");
				$(this).find(".tools0301").fadeOut();
			})
			
			$(".tools00").hover(function(){
				$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/ip.png");
				$(this).css("background","#e3e3e3");
				$(this).find(".tools0301").fadeIn();
			},function(){
				$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/ip.png");
				$(this).css("background","#a37344");
				$(this).find(".tools0301").fadeOut();
			})
		})
	</script>
