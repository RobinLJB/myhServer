<#assign context="${rc.contextPath}">
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9"> <![endif]-->
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title>后台登录</title>
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
<style>
.login-captcha{
	position: absolute;
    right: 5px;
    top: 5px;
	cursor:pointer;
	width: 100px;
}
</style>
</head>
<!-- END HEAD -->
<body class="login">
<div class="logo">
	<a href="index.html">
		
	</a>
</div>
<div class="content">
	<form class="login-form" action="${context}/admin/signon.do" method="post">
		<h3 class="form-title">登录</h3>
		<input type="hidden" name="sncode" value="11111" />
		<div class="alert-container"></div>
		<div class="form-group">
			<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			<label class="control-label visible-ie8 visible-ie9">用户名</label>
			<input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名/手机号" name="username"/>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">密码</label>
			<input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" name="password"/>
		</div>
		<div class="form-group" style="position:relative">
			<input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入验证码" name="captcha"/>
			<img id="captchaImg" onclick="refreshCaptcha()" title="看不清楚？点击刷新" src="${context}/captcha.do?cid=ADMIN_LOGIN" class="login-captcha" />
		</div>
		<div class="form-group">
			<button id="submit" data-loading-text="正在登录..." type="submit" class="btn btn-success btn-block">登录</button>
		</div>
	</form>
</div>
<div class="copyright">
	2014-2016 © SPARK TECH
</div>
</body>
<#include "/admin/layout/footer.ftl">
<script src="${context}/asset/public/plugins/jquery.form.js"></script>
<script type="text/javascript">
spark.init('${context}');

function refreshCaptcha(){
	$('#captchaImg').attr('src','${context}/captcha.do?cid=ADMIN_LOGIN&_time='+new Date().getTime());
}

$('.login-form').ajaxForm({
	beforeSubmit:function(){
		$('#submit').button('loading');
	},
	success:function(resp){
		$('#submit').button('reset');
		if(resp.code == 0){
			spark.redirect('${context}'+resp.data);
		}
		else{
			spark.showAlert({container:'.alert-container',message:resp.message,type:'danger'});
			refreshCaptcha();
		}
	}
});
</script>
</html>