<#assign context="${rc.contextPath}">
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="utf-8">
		<title>${webname}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<!--标准mui.css-->
-
		<link rel="stylesheet" href="${context}/asset/mobile/css/app.css" />
		<link rel="stylesheet" href="${context}/asset/mobile/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="${context}/asset/mobile/css/style2.css" />
<style>
	.mui-card {
    font-size: 14px;
    position: relative;
    overflow: hidden;
    margin: 0px;
    border-radius: 2px;
    background-color: #efeff4;
    background-clip: padding-box;
    box-shadow: 0 1px 2px rgba(0,0,0,.3);
}
.mui-card .mui-table-view {
    margin-bottom: 8px;
    border-top: 0;
    border-bottom: 0;
    border-radius: 6px;
}
</style>
	</head>

	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" href="${context}/ucenter/home.html"></a>
			<h1 class="mui-title">邀请好友</h1>
		</header>
		<li class="mui-table-view-cell">
		<p class="tips">邀请链接：<span id="yq_address_input">http://116.62.26.167/ucenter/register.html?param=17</span><a  id="yq_address_btn" class="btn btn-sm btn-success" style="margin-left:20px;">复制</a></p>
						
		</li>
 
	
	</body>

	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
   <script type="text/javascript" src="${context}/asset/mobile/js/myslideup.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.pullToRefresh.material.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.pullToRefresh.js"></script>
	<script src="${context}/asset/public/plugins/jquery-zclip/jquery.zclip.js" type="text/javascript"></script>

		
	</html>