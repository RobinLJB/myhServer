<#assign context="${rc.contextPath}">
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>${SEO_TITLE}</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta content="width=device-width,initial-scale=1" name=viewport>
	<meta http-equiv="keywords" content="${SEO_KEY}">
	<meta http-equiv="description" content="${SEO_DESC}">
	<link rel="shortcut icon" href="${context}/favicon.ico">
	<link href="${context}/favicon.ico" rel="SHORTCUT ICON" type="image/ico">
	<!-- Global styles START -->
	<link href="${context}/asset/public/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${context}/asset/public/font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<!-- Global styles END --> 
	<link href="${context}/asset/front/layout/css/components.css" rel="stylesheet">
	<link rel="stylesheet" href="${context}/asset/front/invest/css/themes.css" />
    <link href="${context}/asset/ucenter/css/common.css" rel="stylesheet">
	<!-- Theme styles END -->
	<@block name="head"></@block>
</head>
<body>
   <div class="container">
   		<div class="alert alert-success" style="padding:30px;margin:0 auto;max-width: 640px;text-align: center;margin-top:5%;">
   			<p><i style="font-size: 36px;" class="fa fa-check-circle"></i></p>
		 	<h1 style="margin:10px 0;">处理成功</h1>
		 	<a href="${redirect!'${context}/ucenter/home.html'}" class="btn btn-success visible-md visible-lg">确定</a>
		</div>
   </div>
</body>
<script type="text/javascript" src="${context}/asset/public/plugins/jquery.min.js"></script>
<script type="text/javascript" src="${context}/asset/public/plugins/jquery-migrate.min.js"></script>
<script type="text/javascript" src="${context}/asset/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${context}/asset/front/invest/js/themes.js"></script>
<!--[if lt IE 9]>
<script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
<script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script src="${context}/asset/public/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${context}/asset/front/js/front.js" type="text/javascript"></script>
<script>spark.init('${context}');</script>
<@block name="script"></@block>
</html>