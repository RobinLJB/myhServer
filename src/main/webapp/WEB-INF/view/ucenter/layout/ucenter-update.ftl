<#assign context="${rc.contextPath}">
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>${SEO_TITLE}</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="keywords" content="${SEO_KEY}">
	<meta http-equiv="description" content="${SEO_DESC}">
	<link rel="shortcut icon" href="${context}/favicon.ico">
	<link href="${context}/favicon.ico" rel="SHORTCUT ICON" type="image/ico">
	<!-- Global styles START -->
	
	<link href="${context}/asset/public/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${context}/asset/public/font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<link href="${context}/asset/public/plugins/sweetalert/sweetalert.css" rel="stylesheet">
	<!-- Global styles END --> 
	<link href="${context}/asset/front/layout/css/components.css" rel="stylesheet">
	<link rel="stylesheet" href="${context}/asset/front/invest/css/themes.css" />
    <link href="${context}/asset/ucenter/css/common.css" rel="stylesheet">
	<link href="${context}/asset/ucenter/css/home.css" rel="stylesheet">
	<script type="text/javascript" src="${context}/asset/public/plugins/jquery.min.js"></script>
	<!--[if lt IE 9]>
    <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	<style  type="text/css">
		.auth {
    margin: 30px;
    margin-bottom: 100px;
}
		.auth h3{font-size: 23px;}
		.form-group .form-control{margin-top:0px;}
	</style>
	<!-- Theme styles END -->
	<@block name="head"></@block>
</head>
<body>
<!--<div class="top-header">
    <div class="content-fulit clearfix">
        <div class="hot-news pull-left">
            <span class="hot-number pull-left">客服电话<strong>181-0669-3373</strong></span>
            <div class="web-service-box rel pull-left shift">
                <a href="javascript:;" class="web-icon web-icon1 "></a>
                <div class="service-tip top-ico-wb shift-box" style="display:none">
                    <span class="arrow"></span>
                    <h5>点击关注“中徽理财”微博</h5>
                    <a href="javascript:;">@中徽理财</a>
                </div>
            </div>
            <div class="web-service-box rel pull-left shift">
                 <a href="javascript:;" class="web-icon web-icon2"></a>
                 <div class="service-tip top-ico-wb shift-box" style="display:none">
                      <span class="arrow"></span>
                      <h5>扫码关注“优选理财”微信</h5>
                      <span class="wx-qrcode"></span>
                </div>
            </div>
            <div class="rel pull-left shift qq-box">
                 <a href="javascript:;" class="txt1">官方群</a>
                 <div class="service-tip top-ico-wb shift-box top-ico-wb1" style="display:none">
                      <span class="arrow"></span>
                      <h5>用户交流群</h5>
                      <a target="_blank" href="javascript:;" class="qq-btn"></a>
                </div>
            </div>
        </div>
        <ul class="top-header-nav pull-right">
			<#if Session.MEMBER??>
				<li><a style="background:none;" href="${context}/ucenter/home.html">欢迎您，${Session.MEMBER.username!}</a></li>
				
				<li><a href="${context}/ucenter/logout.html">退出 </a></li>
				
				<#else>
				<li><a href="${context}/ucenter/login.html"> 登录 </a> </li>
				<li><a href="${context}/ucenter/register.html"  class="txt1"> 注册 </a></li>
				
			</#if>
			<li><a href="${context}/ucenter/loan.html"> 我要投资</a></li>
        </ul>
    </div>
</div>-->

    <!--heaher-->
    <div class="header">
    	<div class="content-fulit clearfix" style="z-index: 99;position: relative;">
        	<a href="/" alt="中徽理财" class="logo pull-left"><p><img alt="中徽理财" src="${context}/asset/front/invest/images/LOGOgd.png" style="width:150px;float:none;margin:0px;" title="中徽理财" /></p></a>
        </div>
    </div>
	<#--个人中心左侧菜单 -->
	<div class="wrap">
		<div class="container">
			<div class="auth">
				<@block name="body"></@block>
			</div>
		</div>
	</div>
	<#-- 底部 -->
	<#include "/public/footer.ftl" >
	<!-- BEGIN PAGE SCRIPT-->
	
	<script type="text/javascript" src="${context}/asset/public/plugins/My97DatePicker/WdatePicker.js"></script>
	<script src="${context}/asset/public/plugins/sweetalert/sweetalert.min.js" type="text/javascript"></script>
	<script src="${context}/asset/front/js/front.js" type="text/javascript"></script>
	<script>
	
	$(function(){
		$(".ground-item5>a").addClass("active");
		$('.menu-son h3').click(function(e) {
		 	$('.menu h3').removeClass('active');
	 		$(this).addClass('active');
			/*$(this).next().stop().slideToggle(300);*/
		}); 
	});

	</script>
	<@block name="script"></@block>
<!-- END PAGE SCRIPT -->
</body>
</html>