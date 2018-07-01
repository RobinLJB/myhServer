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
			<h1 class="mui-title">信汇钱宝</h1>
		</header>
		<div class="mui-content">
			<div class="commission-top" style="background-color: white;">
				<img src="${context}/asset/mobile/img/yq.png" style="width: 100%;" />
				<div class="mui-row" style="width: 100%;padding: 8px 10px;">
					<div class="mui-col-sm-3 mui-col-xs-3 mui-text-right">
						<img src="${context}/asset/mobile/img/smile2.png" style="width: 50px;" alt="" />
					</div>
					<div class="mui-col-sm-8 mui-col-xs-8 mui-text-center commission-pBox">
						<img src="${context}/asset/mobile/img/dialog.png" width="190px" alt="" />
						<p class="commission-p"><b style="color: #6F6F6F;">您的好友</b>邀请您加入信汇钱宝！</p>
					</div>
				</div>
			</div>
			<div class="commission-bottom" style="margin-top: 10px;padding: 20px;background-color: white;">
				<form class="mui-input-group " method="post" action="login.do">
					<div class="mui-input-row" style="margin-bottom: 15px;">
						<input type="text" name="username" class="mui-input-clear commission-input" placeholder="13925492081">
					</div>
					<div class="mui-input-row" style="position: relative;margin-bottom: 15px;">
						<input type="button" id='commission-verification' class="mui-btn" value="获取验证码" onclick="settime(this)" />
						<input type="text" name="checkcode" style="width: 60%;padding-left: 15px;" class="mui-input-clear commission-input" placeholder="请输入验证码">
					</div>
				</form>
				<button type="button" class="mui-btn mui-btn-blue mui-btn-block commission-btn">立即领钱</button>
				<div class="commission-foot">
					<p class="mui-text-center">已注册的用户请直接使用APP登录</p>
					<p class="mui-text-center" style="white-space:nowrap">客服电话：13925492081&nbsp;&nbsp;客服时间：8:00-20:00</p>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript">
		$(function() {
			mui('body').on('tap', 'a', function() {
				var url = this.getAttribute("data-href");
				window.location.href = url;
			});
		})
		//验证码倒计时
		var countdown = 60;
		function settime(obj) {
			if(countdown == 0) {
				obj.removeAttribute("disabled");
				obj.value = "获取验证码";
				countdown = 60;
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
		
		//登录
		$(".commission-btn").click(function() {
			var params = {};
			params['username'] = $('input[name="username"]').val();
			params['checkcode'] = $('input[name="checkcode"]').val();
			if(params['username'] == "") {
				mui.alert("用户名不能为空");
				return false;
			}
			if(params['checkcode'] == "") {
				mui.alert("验证码不能为空");
				return false;
			}
//			$.post("login.do", params, function(data) {
//					if(data.code == '0') {
//						mui.alert("提交成功")
//						location.href = "#";
//					} else {
//						alert(data.message);
//					}
//			}, 'json');

		});
	</script>

	</html>