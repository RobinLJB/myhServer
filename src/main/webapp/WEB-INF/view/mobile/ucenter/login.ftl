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
<style type="text/css">

	#headImg {
		height: 55px;
		width: 100px;
		position: absolute;
		left: 50%;
		transform: translate(-50%, -50%);
		top: 50%;
		opacity: 0;
	}
	.mui-input-row{
		margin-top: 5px;
		padding-top:0 !important;
	}
	.mui-input-row input{
		background:#fff;
		height: 45px !important;
		border-radius: 10px;
	}
	.mui-input-group .mui-input-row:after{
		position: unset;
	}
	.mui-bar-nav~.mui-content {
		padding-top: 0px;
	}
	ul{
		list-style: none;
		padding:0 20px;
		height: 85px;
		margin-top:10px;
	}
	ul li{
		float:left;
		text-align: center;
		width: 25%;
	}
	ul li img{
		width:60px;
	}
	header {
		color: #fff;
		text-align: center;
		line-height: 65px;
	}
	.mui-input-row .mui-input-clear~.mui-icon-clear, .mui-input-row .mui-input-password~.mui-icon-eye, .mui-input-row .mui-input-speech~.mui-icon-speech{
		right: 5px;
	}
	.mui-input-row #checkcode~.mui-icon-clear{
		right: 120px;
	}
	.login-li .mui-input-row span{
		top:10px !important;
	}
</style>

<body>
	<div class="mui-content" style="background:#2286f8;height:100%;">
		<div style="padding-top:100%;background: url(${context}/asset/mobile/img/xsjdbg.jpg) no-repeat;background-size: cover;">
			
		</div>
		<form class="mui-input-group login-li" style="background:#2286f8;margin-top:20px;" method="post" action="login.do" id="formid" style="margin-top: 20px;">
		<input type="hidden" id="requestCode" value="${requestCode!0}">
		<input type="hidden" id="reruestType" value="${reruestType!0}">
					<div class="mui-input-row">
						<input type="text" name="username" id="username" class="mui-input-clear" placeholder="注册手机号" />
					</div>
					<div class="mui-input-row" style="position: relative;">
						<input type="text" style="width: 60%" id="checkcode" class="mui-input-clear" name="checkcode" placeholder="请输入验证码" />
						<input type="button" style="margin-left:10px;width: 35%;height:35px;font-size: 14px;position: absolute;background:#FFD801;top:0;right:0;padding-left: 12px;z-index: 999;border-left: 1px solid #c8c7cc;" id="getCheckCode" value="获取验证码" class="yanzheng" onclick="settime(this)">
					</div>
					<div style="margin-top:15px;" class="mui-button-row login-but">
						<a style="padding:10px 41%;background:url(${context}/asset/mobile/img/button.png) no-repeat;background-size: contain;" id="btnLogin" type="button"></a>
					</div>
		</form>
		<div style="height: 20px;width:280px;margin: 0 auto;margin-top: 20px;" class="box1-title mui-text-center">
		    	<span style="float:left;line-height:1px;margin-top:10px;width:45px;border-top:1px solid #eee;"></span>
		    	<label style="float:left;color:#fff;width:180px;text-align: center;font-size: 11px;">已有账号可直接登录雷达钱包APP</label>
		    	<span style="float:left;line-height:1px;margin-top:10px;width:45px;border-top:1px solid #eee;"></span>
    		</div>
    		<div class="mui-text-center">
    			<p style="font-size: 12px;color:#c8c7cc;margin-bottom: 0">雷达钱包APP</p>
    			<p id="ABOUT_COPYRIGHT" style="font-size: 12px;color: #c8c7cc;">2017©雷达钱包网络科技</p>
    		</div>
	</div>
</body>
<script type="text/javascript" src="/asset/mobile/js/mui.min.js"></script>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script type="text/javascript" src="/asset/mobile/js/clipboard.min.js"></script>

<script type="text/javascript">
	mui.init({
		swipeBack: true
	});
	
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
		$("#btnLogin").click(function() {
			var params = {};
			params['username'] = $('input[name="username"]').val();
			params['checkcode'] = $('input[name="checkcode"]').val();
			params['requestCode'] = $("#requestCode").val();
			params['reruestType'] = $("#reruestType").val();
			if($('input[name="username"]').val() == "") {
				mui.alert("用户名不能为空");
				return false;
			}
			if($('input[name="checkcode"]').val() == "") {
				mui.alert("验证码不能为空");
				return false;
			}else {
				$.post("${context}/mobile/login.do", params, function(data) {
					if(data.code == '0') {
                        location.href = "https://itunes.apple.com/cn/app/%E5%BA%94%E6%80%A5%E8%B4%B7-%E6%89%8B%E6%9C%BA%E4%BF%A1%E7%94%A8%E5%80%9F%E9%92%B1%E5%BF%AB%E9%80%9F%E8%B4%B7%E6%AC%BEapp/id1317694176?mt=8";
					} else {
					mui.alert(data.message);
					}
				}, 'json');

			}

		});

		$('#getCheckCode').on('click',function(){
			var phone=$('#username').val();
			if(phone==null || phone==""){
			    countdown=0;
				mui.alert("请输入手机号");
				return;
			}
			$.ajax({
				url:"${context}/mobile/sendPhoneCode2.do",
				data:{phone:phone},
				type:"post",
				dataType:"json",
				success:function(res){
					if(res.code == 0){
						//startTick();
					}
					else {
						mui.alert(res.message,"提示");
						$('#getCheckCode').attr('disabled',false);
					}
				},
				error:function(){
					mui.alert(res.message)
					$(this).attr('disabled',false);
				}
			});
			
			
		});	
</script>

</html>