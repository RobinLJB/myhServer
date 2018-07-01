<#assign context="${rc.contextPath}">
	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>${webname}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${context}/asset/mobile/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="${context}/asset/mobile/css/mobile-front.css" />
	</head>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">手机认证</h1>
		</header>
		<div class="mui-content">
		<input type="hidden" value="${phoneToken!}" id="phoneToken"> 
		<input type="hidden" value="${sid!}" id="sid">
			<form class="mui-input-group mobile">
				<h5>手机号码</h5>
				<div class="mui-input-row mui-password">
					<input type="text"  value="${mobile!}" readonly>
				</div>
				<h5>服务密码</h5>
				<div class="mui-input-row mui-password">
					<input type="text" id="servicePwd" placeholder="请输入手机号">
				</div>
				
				<#if flag=="1">
				<div class="mui-input-row" style="position: relative;">
						<img src="${context}/asset/mobile/img/mima.png">
						<input type="text" id="checkcode" class="mui-input-clear" name="checkcode" placeholder="请输入验证码" />
						<span class="mui-icon mui-icon-clear mui-hidden" style="right: 140px;"></span>
						<input type="button" style="width: 30%;font-size: 14px;position: absolute;color: #24B2FE;top: 20px;
    right: 20px;background-color: #fff;padding-left: 2px;z-index: 999;border-left: 1px solid #c8c7cc;" id="getCheckCode" value="获取验证码" class="yanzheng" onclick="settime(this)">
					</div>
					<#else>
					<input type="hidden" id="checkcode" class="mui-input-clear" value="0" />
				</#if>
			<br>
				<button type="button" id="mobileLogin" class="mui-btn mui-btn-primary mui-btn-block button btn-bear">登陆验证</button>
			</form>
		</div>
	</body>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript">
	
		
		//验证码倒计时
		var countdown = 120;

		function settime(obj) {
			if(countdown == 0) {
				obj.removeAttribute("disabled");
				obj.value = "获取验证码";
				countdown = 120;
				return;
			} else {
				obj.setAttribute("disabled", true);
				obj.value = "重新发送(" + countdown + ")";
				countdown--;
			}
			setTimeout(function() {
				settime(obj)
			}, 1000)
		}
		
		
		$("#mobileLogin").click(function() {
			var params = {};
			var phoneToken=$("#phoneToken").val();
			var sid=$("#sid").val();
			var servicePwd=$("#servicePwd").val();
			params['phoneToken'] = phoneToken;
			params['checkcode'] = $("#checkcode").val();
			
			params['sid'] = sid;
			params['servicePwd'] =$("#servicePwd").val();
			if($("#servicePwd").val() == "") {
				mui.alert("请输入服务密码");
				return false;
			}
			
			$.post("${context}/mobile/borrow/mobileLogin.do", params, function(data) {
				if(data.code == 0) {
					window.location = "${context}/mobile/borrow/selectNext.html";
				} else {
					mui.alert(data.message);
				}
			});

		});
		
		//刷新验证码
		$("#getCheckCode").click(function() {
			var params = {};
			
			
			$.post("${context}/mobile/borrow/refreshMobileCode.do", params, function(data) {
				if(data.code == '0') {
					mui.alert("短信已发送，请查收");
				} else {
					mui.alert(data.message);
				}
			}, 'json');

		});
		
	</script>
	</html>