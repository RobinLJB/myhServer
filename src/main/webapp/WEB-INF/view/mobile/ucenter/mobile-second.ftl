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
		<input type="hidden" value="${servicePwd!}" id="servicePwd">
		
			<form class="mui-input-group mobile">
			<input type="hidden" value="${phoneToken!}" id="phoneToken"> 
		<input type="hidden" value="${sid!}" id="sid">
				<h5>短信验证码</h5>
				<div class="mui-input-row mui-password">
					<input type="text" id="smscode" placeholder="请输入短信验证码">
				</div>
				<h5>图形验证码</h5>
				<div class="mui-input-row mui-password">
					<input type="text" id="imgcode" placeholder="请输入图形验证码">
				</div>
				
				<div class="mui-input-row mui-checkbox mui-left mobile-checkbox">
				  <label>同意<a data-href="${context}/answer.html">《运营商授权协议》</a></label>
				  <input name="checkbox1" value="Item 1" type="checkbox">
				</div>
				<button id="mobileLogin" class="mui-btn mui-btn-primary mui-btn-block button btn-bear">登陆验证</button>
			</form>
		</div>
	</body>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript">
		$(function() {
			mui('body').on('tap','a', function() {
			var url = this.getAttribute("data-href");  
			window.location.href=url;
			});
		})
		
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
			params['servicePwd'] = $("#servicePwd").val();
			params['phoneToken'] = $("#phoneToken").val();
			params['sid'] = $("#sid").val();
			params['smscode'] = $("#smscode").val();
			params['imgcode'] = $("#imgcode").val();
			if($("#smscode").val() == "") {
				mui.alert("请输入短信验证码");
				return false;
			}
			
			$.post("${context}/mobile/borrow/secondIdentity.do", params, function(data) {
				if(data.code == '0') {
					location.href = "${context}/mobile/borrow/selectNext.html?flag="+data.data;
				} else {
					alert(data.message);
				}
			}, 'json');

		});
		
	</script>
	</html>