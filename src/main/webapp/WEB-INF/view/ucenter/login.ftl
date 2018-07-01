<@override name="head">
<link rel="stylesheet" href="${context}/asset/ucenter/css/login.css" />
<link rel="stylesheet" href="${context}/asset/ucenter/css/Pager.css" />
<link rel="stylesheet" href="${context}/asset/ucenter/css/select2.css" />
<style>
	.g{
		    position: relative;
    width: 100%;
    height:650px;
		background: url("${context}${imgMap.path!'/asset/front/images/GDRlogin.png'}") center center no-repeat;
		background-size: cover;
	}
	.btn52{font-size: 16px}
</style>
</@override>
<@override name="body">
  <div class="bg g">
        <div class="content-fulit log-in-box">
            <div class=" clearfix">
                <div class="log-in-form pull-right">
                	
                    <form class="form-group form-group2" id="login-form">
                    	
                      
                            <div class="text-style1 rel">
                                <div class="icon-box"><i class="phone"></i></div>
								<input type="text" placeholder="输入手机号/用户名" tabindex="1" name="username" id="name" value=""/>
                            </div>
                        
                        
	                       
                        
	                        <div class="form-group">
								<div class="col-md-2">
									<input type="text" id="checkcode" class="form-control required" name="code" value="" placeholder="请输入验证码"/> 
								</div>
								<div class="col-md-2">
									<button class="btn getcode" id="getCheckCode" type="button">获取验证码</button>
								</div>
							</div>
                        
                        
                        <p class="log-in-btn">
							<button id="btn_login" type="button" class="btn btn52 login-btn" >立即登录</button>
						</p>
						
                    </form>

                </div>
            </div>
        </div>
    </div>
</@override>
<@override name="script">
<script type="text/javascript" src="${context}/asset/front/invest/js/themes.js"></script>
<script>

	$(document).keyup(function(e){ 
		if(e.keyCode==13){ 
			$('#btn_login').trigger("click");
		} 
	}); 
	
</script>
<script>
	$(document).ready(function(){
		$("form :input").blur(function(){
		
			if($(this).is("#name")){   
				if(this.value==""){
					$("#s_email").html("*请輸入您的密码手机号");
				}else if((/^1\d{10}$/.test(this.value)) == 0){
					$("#s_email").html("*请输入正确的手机号码");
				}else{
					$("#s_email").html("");
				}
			}
			//password
			if($(this).attr("id")=="password"){
				if(this.value==""){
					$("#s_password").html("*请輸入您的密码");  
				}else if(this.value.length < 6){ 
					$("#s_password").html("*密码长度不能小于6位字符"); 
				}else{
					$("#s_password").html("");
				}
			}
			//验证码
			if($(this).attr("id")=="checkcode"){
				if(this.value==""){
					$("#s_code").html("*请输入验证码"); 
				}else{   
					$("#s_code").html(""); 
				} 
			}
		});
		
		$('#getCheckCode').on('click',function(){
			debugger;
			var name=$('#name').val();
			$.ajax({
				url:"${context}/loginSendMbCode.do",
				data:{verify:"LOGIN_PHONE_CODE",name:name},
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
		
	});
</script>
<script>
$(document).ready(function(){
	$(".ground-item5>a").addClass("active");
		var param={};
		$("#btn_login").click(function(){
			$('#btn_login').attr('value','登录中...');
			param['username']=$('#name').val();
			param["checkcode"] = $("#checkcode").val();
			$.post("${context}/ucenter/login.do",param,function(data){
				if(data.code != 0){
					spark.alert(data.message);
				}else{
					window.location.href = "${redirect!'${context}/ucenter/home.html'}"	;
				}
		
			},'json');
		})

});
</script>
</@override>
<@layout name="/public/front-base.ftl" />