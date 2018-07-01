<#assign context="${rc.contextPath}">
	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>信贷</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${context}/asset/mobile/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="${context}/asset/mobile/css/mobile-front.css" />
		<style type="text/css">
			textarea{font-size: 14px;border-color: #eee;border-radius: 0;}
			button.mui-btn{margin-top: 25px;padding: 10px 0;border-radius: 5px;border: none;background-color: #F6672F;}
			#bg_shadow { width:100%; height:100%; background-color:#000; position:absolute;
			    top:0; left:0; z-index:2; opacity:0.3;  filter: alpha(opacity=30); display:none;
			}
			#d_load {
			    width:200px; height:200px; margin: auto; position: absolute;
			    z-index:3; top: 0; bottom: 0; left: 0; right: 0; display:none ; text-align:center;
			}
		
		</style>
	</head>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">手机认证</h1>
		</header>
		<div class="mui-content" style="background: #fdefe1;" >
		
			<div class="mui-content-padded" id="form1">
				<p>手机号：${mobilePhone}</p>
				<input type="password" id="password" name="password" placeholder="请输入手机查询密码" />
				<button type="button" id="busstton1" class="mui-btn mui-btn-primary mui-btn-block">提交查询密码</button>
			</div>
			
			<div class="mui-content-padded" id="form2" style="display:none;"> 
				<p>提交短信验证码</p>
				<input type="text" id="mobileCode" name="mobileCode" placeholder="请输入手机验证码" />
				<button type="button" id="busstton2" class="mui-btn mui-btn-primary mui-btn-block">提交验证码</button>
			</div>
			
			
		</div>
		
		<div id="bg_shadow"></div>
		<div id="d_load"><img src="${context}/asset/front/img/loader2.gif" /></div>
		
	</body>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		$(function() {
			mui('body').on('tap', 'a', function() {
				var url = this.getAttribute("data-href");
				window.location.href = url;
			});
						
			
		})
		
		//加载中...
		function loading(){
			$("#bg_shadow, #d_load").show();
		}
		//取消加载
		function cancelLoading(){
			$("#bg_shadow, #d_load").hide();
		}
		
		//提交手机查询密码
		$('#busstton1').click(function(){
			loading();
			var password=$("#password").val();
			if(!password || password.length < 6){
				mui.alert("请输入正确的查询密码");
				return false;
			}
			$.ajax({
				//接口1
				url:"${context}/mobile/borrow/mobileRec.do",
				data:{password:password},
				type:"post",
				dataType:"json",
				success:function(res){
					//1-1是100
					if(res.code == '100'){
						var m_token = res.data.taskId;
						if(!m_token || m_token == ''){
							mui.alert("数据异常");
							cancelLoading();
							return false;
						}
						//获取接口2
						setTimeout(function(){getAuthData(password,m_token);}, 2000);
						
					}
					//1-2不是100
					else {
						cancelLoading();
						mui.alert(res.message);
					}
				}
		});
			
	});	
	
	var m_token_ = "";
	var taskStage ='';
	
	//获取手机认证的消息（接口2）
	function getAuthData(password,m_token){
		loading();
		$.post("${context}/mobile/borrow/mobileAuthGet.do", {password:password,taskId:m_token}, function(res){
			//2-1联通
			if (res.code == '2007'||res.code == '137'){//联通登录成功
				//获取接口5
				setTimeout(function(){mobileResult(m_token);}, 2000);
			//2-1电信，移动（需要调用接口3）
			} else if(res.code == '105'){//需要提交验证码
				taskStage = res.data.taskStage;
				if(!taskStage || taskStage == ''){
					mui.alert("数据异常");
					cancelLoading();
					return false;
				}
				mui.alert("请提交收到的短信验证码");
				$("input").val("");
				$("#form1").hide();
				$("#form2").show();
				m_token_ = m_token;
				taskStage = taskStage;
				cancelLoading();
			//2-3 未获取
			} else if(res.code == '100'){//未获取到结果集，继续循环
//				loading();
				setTimeout(function(){getAuthData(password,m_token);}, 2000);
			//2-4错误
			}else{
				mui.alert(res.message);
				$("input").val("");
				$("#form1").show();
				$("#form2").hide();
				cancelLoading();
			}
		}, 'json');
	}
	
	
	//提交短信验证码
	$('#busstton2').click(function(){
			var mobileCode = $("#mobileCode").val();
			if(!mobileCode || mobileCode.length < 4){
				mui.alert("请输入短信验证码");
				return false;
			}
			loading();
			//接口3
			$.ajax({
				url:"${context}/mobile/borrow/mobileAuthSend.do",
				data:{taskId:m_token_, "mobileCode":mobileCode, "taskStage":taskStage},
				type:"post",
				dataType:"json",
				success:function(res){
					//3-1少数情况，成功直接调接口5
					if (res.code == '2007'||res.code == '137'){
						//获取接口5
						setTimeout(function(){mobileResult(m_token_);}, 2000);
					}
					//3-2大部分情况，成功调用接口4
					else if(res.code == '100'){
						//获取接口4
						setTimeout(function(){mobileFour(m_token_,mobileCode,taskStage);}, 2000);
					}
					//3-3其他
					else {
						mui.alert(res.message);
						$("input").val("");
						$("#form1").show();
						$("#form2").hide();
						cancelLoading();
					}
				}
		});
			
	});	
	
	//调接口4
	function mobileFour(m_token_,mobileCode,taskStage){
			loading();
			$.ajax({
				url:"${context}/mobile/borrow/mobileAuthQuery.do",
				data:{taskId:m_token_,"mobileCode":mobileCode,"taskStage":taskStage},
				type:"post",
				dataType:"json",
				success:function(res){
					if(res.code == '2007'){ 
						//获取接口5
						setTimeout(function(){mobileResult(m_token_);}, 2000);
					}
					else if(res.code == '137'||res.code == '100'){ 
						loading();
						//获取接口4
						setTimeout(function(){mobileFour(m_token_,mobileCode,taskStage);}, 2000);
					}
					else {
						mui.alert(res.message);
						$("input").val("");
						$("#form1").show();
						$("#form2").hide();
						cancelLoading();
					}
				}
		});
			
	};	
	
	//调接口5
	function mobileResult(m_token_){
			loading();
			$.ajax({
				url:"${context}/mobile/borrow/mobileSecondQuery.do",
				data:{taskId:m_token_},
				type:"post",
				dataType:"json",
				success:function(res){
					if(res.code == '0'){ 
						mui.alert(res.message,function(){
							location.href='${context}/mobile/borrow/attestation.html'
						});
					}
					else if(res.code == '137'||res.code == '2007'||res.code == '100'){
						//获取接口5
						setTimeout(function(){mobileResult(m_token_);}, 2000);
					}
					else {
						mui.alert(res.message);
						$("input").val("");
						$("#form1").show();
						$("#form2").hide();
						cancelLoading();
					}
				}
		});
			
	};	
	
	
	</script>
	</html>