<@override name="head">
</@override>
<@override name="body">
	<!-- 会员中心主页 -->

	<#if  email??>
	 
	<h3>您已绑定邮箱</h3>
	 <br/><br/>
	<form class="form-horizontal" >
		<div class="form-group">
			<label class="col-sm-2 control-label">当前邮箱</label>
			<div class="col-md-4">
				<p class="form-control-static"><b>${email!}</b><!--&nbsp;&nbsp;<a class="btn btn-xs btn-danger">更改</a>--></p>
			</div>
		</div>
			
		<div class="" id="verify"  >
			<div class="form-group">
				<label class="control-label col-md-3">新邮箱
				<span class="required" aria-required="true">*</span>
				</label>
				<div class="col-md-4">
					<input id="email" type="text" class="form-control required chinaMobile" name="email" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3">邮箱验证码：
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
 
				<button  type="button" id="submit" class="btn btn-primary" >修改</button>
			</div>
			 
		</div>	
	</form>
	<#else>	
	 
		<h3>邮箱绑定</h3>
	<br/><br/>
	<form class="form-horizontal" >
		 
			
		<div class="" id="verify">
			<div class="form-group">
				<label class="control-label col-md-3">邮箱账号
				<span class="required" aria-required="true">*</span>
				</label>
				<div class="col-md-4">
					<input id="email" type="text" class="form-control required chinaMobile" name="email" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3">邮箱验证码
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
				<button  type="button" id="submit" class="btn btn-primary"  >确定</button>
			</div>
			 
		</div>	
	</form>	
	</#if>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
	$('.submeun-1').addClass('selected').parents().show();

	//发送验证码
	$('#getCheckCode').on('click',function(){
		$(this).attr('disabled',true);
		var email =$("#email").val();
		var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		if(!reg.test(email)){
			spark.alert("请输入正确的邮箱","error");
			$(this).attr('disabled',false);
			return null;
		}
		
		$.ajax({
			url:"${context}/ucenter/sendEmail.do",
			data:{"verify":"EMAIL","email":$("#email").val()},
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
				$('#getCheckCode').css("color","#666");
				window.tick--;
			}
			else {
				$('#getCheckCode').text('获取验证码');
				$('#getCheckCode').removeAttr('disabled');
				clearInterval(window.clock);
			}
		},1000);
	}
	$('.submeun-4').addClass('selected');
	$(function(){
 
		//检查验证码
		$("#submit").click(function(){
		var sncode = $("#sncode").val();
		var email = $("#email").val();
			if(sncode=="" || email==""){
			 spark.alert("请输入正确的验证码和邮箱","error");
			 return false;
			}
			$.post("${context}/ucenter/safety/email.do",{"sncode":sncode,"email":$("#email").val()},function(ret){				 
					if(ret.code==0){
						spark.alert("操作成功","success");
						window.location.href="${context}/ucenter/safety/index.html";
					}else{
						spark.alert(ret.message,"error");
					}
			})
			
		
		});
	})

</script>
</@override>
<@layout name="/ucenter/layout/ucenter-update.ftl" />