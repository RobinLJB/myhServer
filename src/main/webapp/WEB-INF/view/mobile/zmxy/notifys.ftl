<#assign context="${rc.contextPath}">
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9"> <![endif]-->
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title>芝麻信用</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${context}/asset/public/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link href="${context}/asset/public/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${context}/asset/public/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="${context}/asset/admin/css/components.css" rel="stylesheet" type="text/css"/>
<link href="${context}/asset/admin/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="${context}/asset/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link href="${context}/asset/admin/layout/css/themes/darkblue.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="${context}/asset/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico"/>
<link href="${context}/asset/admin/pages/css/login.css" rel="stylesheet" type="text/css"/>

</head>
<!-- END HEAD -->

<body>
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
				<input type="text" value="${flag!222}">
					<#if flag==0>
						<div class="alert alert-danger">认证失败</div>
					<#else>
						<div class="alert alert-success">认证成功</div>
					</#if>
					
					<a href="${context}/mobile/borrow/attestation.html?uid=${uid!}">返回认证中心</a>
				</div>
			</div>
		</div>
	</body>

<script type="text/javascript" src="${context}/asset/front/invest/js/jquery-1.10.2.min.js"></script>
<script src="${context}/asset/public/plugins/jquery.form.js"></script>

</html>