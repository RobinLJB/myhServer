<@override name="head">
 
</@override>
<@override name="body">
	<!-- 会员中心主页 -->
		
	<h3>修改手机号</h3>
	<br/><br/>

		
	<form class="form-horizontal" >
		<div class="form-group">
			<label class="col-sm-2 control-label">当前手机号</label>
			<div class="col-md-4">
				<p class="form-control-static"><b>${mobilePhone!}</b><!--&nbsp;&nbsp;<a class="btn btn-xs btn-danger">更改</a>--></p>
			</div>
		</div>
			
		<div class="" id="verify">
			<div class="form-group">
				<label class="control-label col-md-3">新手机号
				<span class="required" aria-required="true">*</span>
				</label>
				<div class="col-md-4">
					<input id="phone" type="text" class="form-control required chinaMobile" name="phone" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3">验证码
				<span class="required" aria-required="true">*</span>
				</label>
				<div class="col-md-4">
					<div class=" verify">
						<div class=" verifyimg"><input type="text"  placeholder="验证码" class="form-control"  name="checkcode" id="checkcode" />
						<img id="captchaImg" onclick="refreshCaptcha()" style="position: relative;top: -36px;left: 220px;height:35px;" title="看不清楚？点击刷新" src="${context}/captcha.do?cid=CAPTCHA" class=" pull-left  " />
					   </div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3">手机验证码：
				<span class="required" aria-required="true">*</span>
				</label>
				<div class="col-md-4">
					<input type="text" id="sncode" class="form-control required" name="sncode" value="" />
				</div>
				<div class="col-md-2">
					<button id="getCheckCode" type="button" class="btn btn-default">获取验证码</button>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
	 
				<button  type="button" id="submit" class="btn btn-primary"  >修改</button>
			</div>
			 
		</div>	
	</form>	

</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
	$('.submeun-4').addClass('selected').parents().show();

	//发送验证码
	$('#getCheckCode').on('click',function(){
		var phone = $("#phone").val();
		if(phone==""){
			spark.alert("手机号不能为空","error");
			return false;
		}
		if(!/^1[34578]\d{9}$/.test(phone)){
			return false;
		}
		var code = $("#checkcode").val();
		if(code==""){
			spark.alert("验证码不能为空","error");
			return false;
		}
		$(this).attr('disabled',true);		 
		$.ajax({
			url:"${context}/ucenter/sendCode.do",
			data:{phone:phone,code:code,verify:"CAPTCHA"},
			type:"post",
			dataType:"json",
			success:function(res){
				if(res.code == 0){
					startTick();
				}
				else {
					 spark.alert(res.message,"error");
					$('#getCheckCode').attr('disabled',false);
				}
			},
			error:function(){
				$(this).attr('disabled',false);
			}
		});
	});
	
	$.validator.addMethod("chinaMobile", function(value, element) {      
        return /^1[34578]\d{9}$/.test(value);       
    }, "非法手机号");
	
	function startTick(){
		window.tick = 60;
		window.clock = setInterval(function(){
			if(window.tick >0){
				$('#getCheckCode').text('剩余时间('+window.tick+'s)');
				window.tick--;
			}
			else {
				$('#getCheckCode').text('获取验证码');
				$('#getCheckCode').attr('disabled',false);
				clearInterval(window.clock);
			}
		},1000);
	}
	
	$(function(){
		//检查验证码
		$("#submit").click(function(){
		var sncode = $("#sncode").val();
		var phone = $("#phone").val();
			if(sncode=="" || phone==""){
			 spark.alert("请输入正确的验证码和手机号","error");
			 return false;
			}
			$.post("${context}/ucenter/safety/mobile/update.do",{"sncode":sncode,"phone":phone},function(ret){				 
					
					if(ret.code==0){
						window.location.href="${context}/ucenter/safety.html";
					}else{
						spark.alert(ret.message,"error");
					}
					
			})
			
		
		});
	})
function refreshCaptcha(){
		$('#captchaImg').attr('src','${context}/captcha.do?cid=CAPTCHA&_time='+new Date().getTime());
	}
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-update.ftl" />