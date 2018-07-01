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
	<!-- Theme styles END -->
	<style>
		.wz {
    width: 100%;
    background: #391d0a;
    color: white;
}
.wz_1 {
    width: 1200px;
    margin: 0 auto;
    text-align: left;
        padding: 10px 0;
    font-size: 16px;
}


.tb{
    position: absolute;
    height: 20px;
    width: 20px;
    left: 63px;
    top:13px;
}
	</style>
	<@block name="head"></@block>
</head>
<body>
    <#--导航菜单 -->
    <#include "/public/top.ftl" >
	<#--个人中心左侧菜单 -->
	<div class="wrap">
		<div class="container" style="    margin-top: 29px;margin-bottom: 40px;">
			<div id="page-sidebar-menu">
				<div class="menu">
			    <a href="${context}/ucenter/home.html"><h3 class="mymoney" style="color: white;background: #fd852f;">会员中心</h3></a>
			    <h3 style="height: 20px;background: white;"></h3>
				<h3 class="mymoney">我的资金</h3>
				
					<ul class="menu-son">
							<li class="submeun-1">
								<img class="tb" src="${context}/asset/ucenter/img/recharge.png"  />
								<a class="l-m-icon" href="${context}/ucenter/finance/recharge.html">充值</a></li>
							<li class="submeun-2">
								<img class="tb" src="${context}/asset/ucenter/img/tx.png"  />
								<a class="l-m-icon" href="${context}/ucenter/finance/withdraw.html">提现</a></li>
							<li class="submeun-3">
								<img class="tb" src="${context}/asset/ucenter/img/record.png"  />
								<a class="l-m-icon" href="${context}/ucenter/finance/fundrecord.html">资金记录</a></li>
					</ul>
					<h3 class="myaccount">我的账户</h3>
					<ul class="menu-son">
							<li class="submeun-4">
								<img class="tb" src="${context}/asset/ucenter/img/wdkj.png"  />
								<a class="l-m-icon" href="${context}/ucenter/card.html">我的红包</a></li>
							<li class="submeun-5">
								<img class="tb" src="${context}/asset/ucenter/img/anzx.png"  />
								<a class="l-m-icon" href="${context}/ucenter/safety/index.html">安全中心</a></li>
							<#if Session.MEMBER??>
								<li class="submeun-6">
									<img class="tb" src="${context}/asset/ucenter/img/qyrz.png"  />
									<a class="l-m-icon" href="${context}/ucenter/info.html">个人认证</a></li>
							</#if>
							
							<li class="submeun-7">
								<img class="tb" src="${context}/asset/ucenter/img/pt.png"  />
								<a class="l-m-icon" href="${context}/ucenter/notice.html">平台消息</a></li>
							<li class="submeun-15">
								<img class="tb" src="${context}/asset/ucenter/img/yqhy.png"  />
								<a class="l-m-icon" href="${context}/ucenter/friend.html">邀请好友</a></li>
					</ul>
					<h3 class="myinvest">我的投资</h3>
					<ul class="menu-son">
							<li class="submeun-9">
								<img class="tb" src="${context}/asset/ucenter/img/tzlb.png"  />
								<a class="l-m-icon" href="${context}/ucenter/invest/tendering.html">投资列表</a></li>
							<!--
							<li class="submeun-8">
								<img class="tb" src="${context}/asset/ucenter/img/zdtb.png"  />
								<a class="l-m-icon" href="${context}/ucenter/zdtb.html">自动投标</a></li>
							-->	
							<li class="submeun-10">
								<img class="tb" src="${context}/asset/ucenter/img/tbtj.png"  />
								<a class="l-m-icon" href="${context}/ucenter/invest/statistics.html">投资统计</a>
							</li>
					</ul>
					
					
					<h3 class="myloanhk">我的借贷</h3>
					<ul class="menu-son">
							<li class="submeun-12">
								<img class="tb" src="${context}/asset/ucenter/img/jksq.png"  />
								<a class="l-m-icon" href="${context}/ucenter/myloan/applying.html">借款申请</a>
							</li>
							<li class="submeun-13">
								<img class="tb" src="${context}/asset/ucenter/img/jklb.png"  />
								<a class="l-m-icon" href="${context}/ucenter/myloan/repay-info.html">还款列表</a>
							</li>
					</ul>
					<h3 class="myloanall">债权转让</h3>
					<ul class="menu-son">
							<li class="submeun-11">
								<img class="tb" src="${context}/asset/ucenter/img/zrlb.png"  />
								<a class="l-m-icon" href="${context}/ucenter/zflb.html">转让列表</a>
							</li>
							<li class="submeun-11-2">
								<img class="tb" src="${context}/asset/ucenter/img/zrlb.png"  />
								<a class="l-m-icon" href="${context}/ucenter/debtInvestList.html">债权投资列表</a>
							</li>
					</ul>
					<h3 class="myloanmj" style="color: #c23531;">募捐申请</h3>
					<ul class="menu-son">
							<li class="submeun-14">
								<img class="tb" src="${context}/asset/ucenter/img/jksq.png"  />
								<a class="l-m-icon" href="${context}/ucenter/addWelfare.html" style="color: #c23531;">申请募捐</a></li>
							<li class="submeun-15">
								<img class="tb" src="${context}/asset/ucenter/img/jklb.png"  />
								<a class="l-m-icon" href="${context}/ucenter/welfare.html" style="color: #c23531;">募捐列表</a></li>
					</ul>
					
				</div>
			</div>
			<div id="page-content-wrapper">
			    <@block name="body"></@block>
			</div>
		</div>
	</div>
 
	<#include "/public/footer.ftl" >
	<!-- BEGIN PAGE SCRIPT-->
	<#-- 底部 -->
<!-- END PAGE SCRIPT -->
</body>

<script type="text/javascript" src="${context}/asset/public/plugins/jquery-migrate.min.js"></script>
<script type="text/javascript" src="${context}/asset/public/bootstrap/js/bootstrap.min.js"></script>	
<script type="text/javascript" src="${context}/asset/front/invest/js/themes.js"></script>
<!--[if lt IE 9]>
<script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
<script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script src="${context}/asset/public/plugins/sweetalert/sweetalert.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${context}/asset/front/js/front.js" type="text/javascript"></script>
<script>spark.init('${context}');</script>
<script type="text/javascript" src="${context}/asset/front/js/jquery.SuperSlide2.js"></script>
	<script type="text/javascript" src="${context}/asset/front/js/jquery.flexslider-min.js"></script>
	<script src="${context}/asset/front/js/modernizr-custom-v2.7.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${context}/asset/front/js/cookie.js"></script>
	<script type="text/javascript" src="${context}/asset/front/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="${context}/asset/front/bootstrap/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
     $(document).ready(function(){
     	$(".ground-item5>a").addClass("active");
	$('.flexslider').flexslider({
		directionNav: true,
		pauseOnAction: false,
	});
});


</script>
<@block name="script"></@block>
</html>