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
	<link href="${context}/asset/public/plugins/sweetalert/sweetalert.css" rel="stylesheet">
	<link rel="stylesheet" href="${context}/asset/front/invest/css/themes.css" />
	<link rel="stylesheet" href="${context}/asset/front/invest/css/Pager.css" />
	<link rel="stylesheet" href="${context}/asset/front/invest/css/select2.css" />

	<link rel="stylesheet" href="${context}/asset/front/invest/css/odometer-theme-default.css"/>
	<link rel="stylesheet" href="${context}/asset/front/invest/css/animate.css" />
	<script type="text/javascript" src="${context}/asset/front/invest/js/jquery-1.10.2.min.js"></script>
  

 
	<@block name="head"></@block>
	<!--[if lt IE 9]>
    <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>
<body>
<#include "/public/top.ftl" >
<#--common body -->
<@block name="body"></@block>
<!-- 底部 -->
<#include "/public/footer.ftl" >

<script src="${context}/asset/public/plugins/sweetalert/sweetalert.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${context}/asset/front/js/front.js" type="text/javascript"></script>
<script>spark.init('${context}');</script>
<@block name="script">

</@block>

<script>
$(function(){
	$('#menus li:eq(0)').addClass('active');
});
</script>
<script>



$(".tools02").click(function(){
	$(window).scrollTop(0);
});

$(".tools02").hover(function(){
	$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/myin03a.png");
	$(this).find(".tools04").css("color","#eb4f38");
},function(){
	$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/myin03.png");
	$(this).find(".tools04").css("color","#999");
})


$(".tools01").hover(function(){
	$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/myin02a.png");
	$(this).find(".tools04").css("color","#eb4f38");
	$(this).find(".tools0301").fadeIn();
},function(){
	$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/myin02.png");
	$(this).find(".tools04").css("color","#999");
	$(this).find(".tools0301").fadeOut();
})

$(".tools00").hover(function(){
	$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/myin01a.png");
	$(this).find(".tools04").css("color","#eb4f38");
	$(this).find(".tools0301").fadeIn();
},function(){
	$(this).find(".tools03").find("img").attr("src","${context}/asset/front/images/myin01.png");
	$(this).find(".tools04").css("color","#999");
	$(this).find(".tools0301").fadeOut();
})
</script>

</body>
</html>