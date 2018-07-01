<#assign context="${rc.contextPath}">
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>${SEO_TITLE}</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="keywords" content="${SEO_KEY}">
	<meta http-equiv="description" content="${SEO_DESC}">
	<link rel="shortcut icon" href="${context}/logo.ico">
	<link href="${context}/logo.ico" rel="SHORTCUT ICON" type="image/ico">
	<!-- Global styles START -->
	<!-- Global styles END --> 
	<link href="${context}/asset/front/css/article.css" rel="stylesheet">
	<link rel="stylesheet" href="${context}/asset/front/page/css/header-bar.css" />
	<link rel="stylesheet" href="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="${context}/asset/front/invest/css/themes.css" />
	<link rel="stylesheet" href="${context}/asset/front/invest/css/Pager.css" />
	<link rel="stylesheet" href="${context}/asset/front/invest/css/select2.css" />

	<link rel="stylesheet" href="${context}/asset/front/invest/css/odometer-theme-default.css"/>
	<link rel="stylesheet" href="${context}/asset/front/invest/css/animate.css" />
	<!--<link href='${context}/asset/front/invest/css/46972' rel='stylesheet' type='text/css'/>
	<link href="${context}/asset/front/invest/css/46972"  rel='stylesheet' type='text/css' />-->

	<script type="text/javascript" src="${context}/asset/front/invest/js/jquery-1.10.2.min.js"></script>
	<!--[if lt IE 9]>
	<script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	<!-- Theme styles END -->
	<link rel="shortcut icon" href="${context}/logo.ico">
	<link href="${context}/logo.ico" rel="SHORTCUT ICON" type="image/ico">
	<!-- Global styles START -->
 
	<!-- Global styles END --> 
	<link href="${context}/asset/front/css/common.css" rel="stylesheet" />
	<link href="${context}/asset/front/css/article.css" rel="stylesheet">
	
	
	
	<link rel="stylesheet" href="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="${context}/asset/front/invest/css/themes.css" />
	<link rel="stylesheet" href="${context}/asset/front/invest/css/Pager.css" />
	<link rel="stylesheet" href="${context}/asset/front/invest/css/select2.css" />

	<link rel="stylesheet" href="${context}/asset/front/invest/css/odometer-theme-default.css"/>
	<link rel="stylesheet" href="${context}/asset/front/invest/css/animate.css" />
	<!--<link href='${context}/asset/front/invest/css/46972' rel='stylesheet' type='text/css'/>
	<link href="${context}/asset/front/invest/css/46972"  rel='stylesheet' type='text/css' />-->

	<script type="text/javascript" src="${context}/asset/front/invest/js/jquery-1.10.2.min.js"></script>
	<style>
			.pagination {
	display: inline-block;
	padding-left: 0;
	margin: 20px 0;
	border-radius: 4px
}
.menu-container .container{width: 1170px}
.pagination>li {
	display: inline
}
.title5 a.loans_title{    display: inline-block;
    width: 450px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    line-height: initial;
    position: relative;
    top: 7px;}
.pagination>li>a,.pagination>li>span {
	position: relative;
	float: left;
	padding: 6px 12px;
	margin-left: -1px;
	line-height: 1.42857143;
	color: black;
	text-decoration: none;
	background-color: #fff;
	border: 1px solid #ddd;
	    margin-right: 15px;
}

.pagination>li:first-child>a,.pagination>li:first-child>span {
	margin-left: 0;
	border-top-left-radius: 4px;
	border-bottom-left-radius: 4px
}

.pagination>li:last-child>a,.pagination>li:last-child>span {
	border-top-right-radius: 4px;
	border-bottom-right-radius: 4px
}

.pagination>li>a:focus,.pagination>li>a:hover,.pagination>li>span:focus,.pagination>li>span:hover {
	z-index: 3;
	color: #ffffff;
    background-color: #337ab7;
    border-color: #337ab7;
}

.pagination>.active>a,.pagination>.active>a:focus,.pagination>.active>a:hover,.pagination>.active>span,.pagination>.active>span:focus,.pagination>.active>span:hover {
	z-index: 2;
	color: #fff;
	cursor: default;
	background-color: #337ab7;
	border-color: #337ab7
}

.pagination>.disabled>a,.pagination>.disabled>a:focus,.pagination>.disabled>a:hover,.pagination>.disabled>span,.pagination>.disabled>span:focus,.pagination>.disabled>span:hover {
	color: #777;
	cursor: not-allowed;
	background-color: #fff;
	border-color: #ddd
}

.pagination-lg>li>a,.pagination-lg>li>span {
	padding: 10px 16px;
	font-size: 18px;
	line-height: 1.3333333
}

.pagination-lg>li:first-child>a,.pagination-lg>li:first-child>span {
	border-top-left-radius: 6px;
	border-bottom-left-radius: 6px
}

.pagination-lg>li:last-child>a,.pagination-lg>li:last-child>span {
	border-top-right-radius: 6px;
	border-bottom-right-radius: 6px
}

.pagination-sm>li>a,.pagination-sm>li>span {
	padding: 5px 10px;
	font-size: 12px;
	line-height: 1.5
}

.pagination-sm>li:first-child>a,.pagination-sm>li:first-child>span {
	border-top-left-radius: 3px;
	border-bottom-left-radius: 3px
}

.pagination-sm>li:last-child>a,.pagination-sm>li:last-child>span {
	border-top-right-radius: 3px;
	border-bottom-right-radius: 3px
}
	.title{text-align: left;}
	.js {padding: 0}
.title h2 {
    margin: 0;
    border-bottom: 2px solid #eee;
    height: 52px;
    margin-bottom: 15px;
    padding-left: 35px;
}
.title h2 span {
    padding: 0 15px;
    border-bottom: 2px solid #000;
    display: inline-block;
    height: 52px;
    color: #000;
    line-height: 52px;
    font-size: 20px;
}
.content {
    padding: 0 50px 50px;
    background: #fff;
    color: #333;
        text-align: left;
}
.hot {
    padding: 25px 0;
    border-bottom: 1px solid #ddd;
    overflow: hidden;
}
.hot-pic {
    width: 25%;
    float: left;
}
.hot-text {
    width: 74%;
    float: right;
}
.hot-text h2 {
    padding-bottom: 12px;
    margin: 0;
}
.hot-text a:hover{    text-decoration: none}
.hot-text h2 a {
    color: #010101;
    font-size: 20px;
    font-weight: bold;
}
.content p {
    font-size: 14px;
    line-height: 26px;
    color: #666;
    margin: 0;
}
.content p.time {
    padding-top: 5px;    color: #bbb;
}
		
		.bjtj{
			position: relative;
			    height: 340px;
			    background-position: 100% 100%;
         
			background:url("${context}${imgMap.path!'/asset/front/images/tjxx.png'}") center no-repeat;

		}
		.tjxx{
    position: absolute;
    top: 176px;
    left: 50%;
    margin-left: -500px;
    width: 1000px;
    color: white;
		}
		.tjxx ul li{
			float: left;
			width: 25%;
			text-align: center;
		}
		.num{
			color:#F2F858;font-size: 25px;"
		}
		.bnum{
			font-size: 17px;
		}
		.rk{
			    padding-right: 8px;
		}
		.form-group .control-label {
    font-size: 16px;
    width: 161px;
    text-align: right;
    height: 40px;
    line-height: 40px;
    margin-right: 20px;
    display: inline-block;
}
.form-horizontal {
    margin: 50px auto;
    width: 1124px;
}
.intro{background-color: #a37344 !important;}
.intro>a{color:#fff !important}
	</style>
</head>
<body>
    <#--导航菜单 -->
    <#include "/public/top.ftl" >
	<#--个人中心左侧菜单 -->
		<div class="bjtj">
		
		<div class="tjxx">
			
		</div>
		</div>
	<div class="wrap">
		<div class="container">
			<div class="list-left">
				<ul class="list-menu" id="list">
				<#if (cate?size>0)>
				<#list cate as cate>
					<li>
						<a data-menu-id="category:${cate.key}" href="${context}/category/${cate.key}.html#id_${cate.key}" id="id_${cate.key}">${cate.name}</a>
					</li>
				</#list>
				</#if>
				</ul>
			</div>
		
		<div id="page-content-wrapper"><@block name="body"></@block></div>
		<div style="clear:both"></div>
		</div>
	</ div>
	<#-- 底部 -->
	<#include "/public/footer.ftl" >
	 <script type="text/javascript">
	 	$(function(){
	 	$(".ground-item4>a").addClass("active");
		var menuId = $('#MENU_ACTIVE_ID').val();
		if(menuId){
			var a = $('#list a[data-menu-id="'+menuId+'"]');
				a.parent().addClass('intro');
			}
		});
	</script>
<!-- END PAGE SCRIPT -->
</body>
</html>