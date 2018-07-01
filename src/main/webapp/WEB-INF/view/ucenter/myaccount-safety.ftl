<@override name="head">
</@override>
<@override name="body">

			
		<div class="r_main">
			<div class="tbatil">
				<ul>
					<li>
						<span class="mingzi">手机绑定&nbsp;&nbsp;</span>
							<div class="set_rightb">
								<span style="">您还未绑定手机，请绑定!</span>
								<div class="setright">
									<span class="glyphicon glyphicon-ok textfailed"></span>
									<span class="textfailed">未绑定</span>
									<span style="color:gray;">&nbsp;|&nbsp;</span>
									<a href="boundcellphone.html">设置</a>
								</div>
							</div>
					
					</li> 
					
					
					
					<li>
						<span class="mingzi">邮箱绑定</span>
							<div class="set_rightb">
								<span style="">请绑定您常用的邮箱，用于接收系统通知，密码忘记时可找回密码！</span>
								<div class="setright">
									<span class="glyphicon glyphicon-ok textfailed"></span>
									<span class="textfailed">未绑定</span>
									<span style="color:gray;">&nbsp;|&nbsp;</span>
									<a href="emailManagerInit.html">设置</a>
								</div>
							</div>
					
					</li>
					
					<li>
						<span class="mingzi">资金托管账户</span>
							<div class="set_rightb">
								<span style="">开通资金托管账户，保证您的交易安全！</span>
								<div class="setright">
									<span class="glyphicon glyphicon-ok textfailed"></span>
									<span class="textfailed">未开通</span>
									<span style="color:gray;">&nbsp;|&nbsp;</span>
									<a href="reg2.html" name="flag">设置</a>
								</div>
							</div>
						
					</li>
					
					<li>
						<span class="mingzi">修改密码&nbsp;&nbsp;</span>
						<div class="set_rightb">
							<span style="">您可以修改
							<span style="color:#090;">会员登录密码和会员交易密码</span>。安全性高的密码可以使帐号更安全,建议您定期更换密码。</span>
							<div class="setright">
								<span class="glyphicon glyphicon-ok textsuccess"></span>
								<span class="textsuccess">已设置</span>
								<span style="color:gray;">&nbsp;|&nbsp;</span>
									<a href="updatexgmm.html" name="flag">修改</a>
								
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>

</@override>
<@override name="script">
<script>
	$(document).ready(function() {
	$('.myaccount').addClass('active').parents().show();
	$('.submeun-4 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />