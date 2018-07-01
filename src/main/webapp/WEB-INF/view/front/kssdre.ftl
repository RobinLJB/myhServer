<#assign context="${rc.contextPath}">
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="utf-8">
		<title>${webname}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">

		<link rel="stylesheet" href="${context}/asset/mobile/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="${context}/asset/mobile/css/mobile-front.css" />
		<style>
		.mui-content-padded { 
		margin: 0px;
		 background:#fe584b;}
	.mui-input-group {
    position: relative;
    padding: 0;
    border: 0;
    background: #618bff;
}
.login-li .mui-input-row input {
    width: 100%;
    height: 51px;
    padding-left: 20px;
    font-size: 17px;
    border: 1px solid #ffffff;
    background: white;
    border-radius:21px;
}
.login-li .login-but button {

    border-color: #DA251E;
    background:url("${context}/asset/mobile/img/button.png") no-repeat;
        font-size: 16px;
    border-color: rgba(218, 37, 30, 0);
    border-radius: 17px;
    color: white !important;
    background-size: 100% 100%;
    padding: 25px 0;
    line-height: 5px;
    margin: 0 auto;
}
.mui-btn-block {
    font-size: 18px;
    display: block;
    width: 100%;
    margin-bottom: 10px;
    padding: 15px 0;
}

form#formid {
       margin:0px 14px 0px 14px;
    border-radius: 17px;
    padding: 21px 10px;
    border: 6px solid #fe6e63;
    background: #fe6e63;
;
}
.login-li .mui-input-row {
    height: inherit;
  margin-top: 21px;
}
.login-li .login-but {
    margin-top: 21px;
    height: 40px;
}
.pikop{
	text-align: center;
    font-size: 13px;
    color: #ffffff;
    height:58px;
    line-height: 23px;
}
.mui-btn-blue.mui-active:enabled, .mui-btn-blue:enabled:active, .mui-btn-primary.mui-active:enabled, .mui-btn-primary:enabled:active, input[type=submit].mui-active:enabled, input[type=submit]:enabled:active {
    background-color: #f6352e;
    border: 1px solid #f5372b;
    background-color: #f8332b;
}
.mui-input-group .mui-input-row:after {
    position: absolute;
    right: 0;
    bottom: 0;
    left: 15px;
    height: 1px;
    content: '';
    -webkit-transform: scaleY(.5);
    transform: scaleY(.5);
    background-color: rgba(200, 199, 204, 0);;
}
.zs{
       padding-right: 15px;
    padding-left: 58px;
    display: inline-block;
    float: none;
    width: 100%;
    color: white;
    font-size: 15px;
    line-height: 35px;
    height: 33px;
}
.mui-checkbox input[type=checkbox]:checked:before, .mui-radio input[type=radio]:checked:before {
    color:#fff !important
}

		</style>
		
	</head>

	<body>
		<div class="mui-content index-content" style="background: #fe584b">
			<div class="mui-content-padded">
				<div >
					<img style="width: 100%;" src="${context}/asset/mobile/img/relogin.png">
				</div>
				<form class="mui-input-group login-li" method="post" action="kssdre.do" id="formid">
				<input type="hidden" id="requestCode" value="${requestCode!0}">
				<input type="hidden" id="reruestType" value="${reruestType!0}">
					<div class="mui-input-row">
						<input type="text" name="username" id="username" class="mui-input-clear" placeholder="请输入手机号码" />
						<span class="mui-icon mui-icon-clear mui-hidden"></span>
					</div>
					
			<!-- 		<div class="mui-input-row" style="position: relative;">
						<input style="width: 59%;" type="text" id="imgcode" class="mui-input-clear" name="checkcode" placeholder="请输入图形验证码" />
						 <span class="mui-icon mui-icon-clear mui-hidden" style="right: 140px;"></span>
                          <img style="width: 38%;border-radius:21px; float: right;height: 49px;padding: 0px;" id="getimg" alt="star" src="${context}/asset/front/img/yzmbj.png" />
					</div> -->
					
					
					<div class="mui-input-row" style="position: relative;">
						<input style="width: 59%;" type="text" id="checkcode" class="mui-input-clear" name="checkcode" placeholder="请输入验证码" />
						<span class="mui-icon mui-icon-clear mui-hidden" style="right: 140px;"></span>
						<input style="width: 38%;    font-size: 14px;"  style="border-right: none;" type="button" id="getCheckCode" value="获取验证码" class="yanzheng" onclick="settime(this)">
					</div>
					<div class="mui-input-row">
						<input type="password" class="mui-input-password" name="password" id="password"  placeholder="请输入密码" />
						<span class="mui-icon mui-icon-clear mui-hidden"></span>
					</div>
					<div class="mui-button-row login-but">
						<button id="btnLogin" type="button" class="mui-btn mui-btn-primary mui-btn-block"></button>
					</div>
					<div style="margin-top: 27px;">
					<div class=" mui-checkbox mui-left">
					<a class="zs" href="${context}/data/reg.html">注册代表同意《注册用户隐私协议》</a>
               <input  id="license" name="checkhbox1" value="Item 1" type="checkbox" checked>
                    </div>
                    </div>
				</form>
			</div>
<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1273964928'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s22.cnzz.com/z_stat.php%3Fid%3D1273964928%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script>
		</div>
		<div id="copyText" style="display: none;">111111</div>
		<!-- 提示换手机号弹窗 -->
		<!--<div id="alerat">
			<div class="alerat-content">
				<div class="content">
					<p>尊敬的用户，如果之前现金驿站</p>
					<p>账户手机号停用，请联系人工客服</p>
					<p>客服微信：xianjinyizhan</p>
				</div>
				<button type="button" class="mui-btn mui-btn-block close">我知道了</button>
				<button type="button" class="mui-btn mui-btn-block close">复制微信号</button>
			</div>
		</div>-->
	</body>
	
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/clipboard.min.js"></script>
	<script type="text/javascript">
		mui.init({
			swipeBack: true //右滑关闭功能
		});
		$(function() {
		/* 	mui('body').on('tap', 'a', function() {
			
				var url = this.getAttribute("data-href");
			 
				window.location.href = url;
			}); */
		})
		//l




		//验证码倒计时
		var countdown = 60;

	var timer = (function () {
		var count;
		return
	})();

	function settime(obj) {
		if (countdown == 0) {
			obj.removeAttribute("disabled");
			obj.value = "获取验证码";
			countdown = 60;
			return;
		} else {
			obj.setAttribute("disabled", true);
			obj.value = "重新发送(" + countdown + ")";
			countdown--;
		}
		setTimeout(function () {
			settime(obj)
		}, 1000)
	}

		//注册
		$("#register").click(function() {
			location.href = "${redirect!'${context}/regesister.html'}";
		});

		console.log()
		//登录
		
		$("#btnLogin").click(function() {
		if (!$("#license").prop("checked")) {
				mui.alert("请勾选我已阅读并同意《注册协议》");
				return false;
			}
		
		var newPassword = $("#password").val();
		var len=	newPassword.length;
		
          if(6 > len ){
             mui.alert("密码必须由 6-18位组成");
            return;
             }
              if( len > 18 ){
                mui.alert("密码必须由 6-18位组成");
            return;
             }
			//debugger;
			var params = {};
			params['phone'] = $('input[name="username"]').val();
			params['password'] = $('input[name="password"]').val();
			var  s =$('#checkcode').val()
			params['checkcode'] = s.trim();
			params['requestCode'] = $("#requestCode").val();
			params['reruestType'] = $("#reruestType").val();

			if($('input[name="checkcode"]').val() == "") {
				mui.alert("验证码不能为空");
				return false;
			}
			if($('input[name="password"]').val() == "") {
				mui.alert("密码不能为空");
				return false;
			}

			if($('input[name="username"]').val() == "") {
				mui.alert("用户名不能为空");
				return false;

			}else {

				$.post("${context}/kssdre.do", params, function(data) {
					if(data.code == '0') {

					var u = navigator.userAgent;
						var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                        if (isAndroid == true) {
                            var type = 1;
                           // mui.alert("没有安卓端");

                        }

                        if (isiOS == true) {
                            var type = 2;
                        }
                        var rerutype ="${reruestType!0}";
                        var rerucode= "${requestCode!0}";
                        var phone=$('input[name="username"]').val();
                        var params = {};
                        params['requestCode'] =rerucode;
                        params['reruestType'] = rerutype;
                        params['phone']= phone;
                        params['type'] = type;

                        console.log(type)
                        $.post("generalize/mobile/type.do", params, function (data) {
                            if (data.code == '0') {
                                var rerutype = "${reruestType!0}";
                                var rerucode = "${requestCode!0}";

                            } else {
                                mui.alert(data.message);
                            }
                        }, 'json');
                   location.href = "/myhdownload.html?reruestType="+rerutype+"&requestCode="+rerucode+
										"&phone="+phone;

				} else {
					mui.alert(data.message);
					}
				}, 'json');
			}
		});
		
		$('#getimg').on('click',function(){
			var phone=$('#username').val();
			if(phone==null || phone==""){
				alert("请输入手机号");
				return;
			}
			$.ajax({
				url:"${context}/generalize/captcha.do",
				data:{phone:phone},
				type:"post",
				dataType:"json",
				success:function(res){
					if(res.code == 0){   
					 var Imgsrc = res.data.base64Img;
		             $("#getimg").attr("src","data:image/png;base64,"+Imgsrc)
                        }
					
					else {
						mui.alert(res.message,"提示");
						
					}
				},
				error:function(){
					mui.alert(res.message)
					$(this).attr('disabled',false);
				}
			});
		});
          $('#getCheckCode').on('click',function(){
          	//alert(66)
			var phone=$('#username').val();
			if(phone==null || phone==""){
				alert("请输入手机号");
				return;
			}
		/* 	var  checkcode =$('#imgcode').val()
			if(checkcode==null || checkcode==""){
				alert("请输入图形验证码");
				return;
			} */
			//console.log(s,66666)
			//var codes = s.trim();
			$.ajax({
				url:"${context}/verification/codeNew.do",
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
					//alert(66)
					mui.alert(res.message)
					$(this).attr('disabled',false);
				}
			});
		});
	</script>

	</html>