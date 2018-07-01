<@override name="head">
</@override>
<@override name="body">
<!--
<a href="${context}/ucenter/safety/realname.html">实名认证</a> 
<a href="${context}/ucenter/safety/passwd.html">修改密码</a> 
<a href="${context}/ucenter/safety/mobile.html">手机绑定</a> 

<a href="${context}/ucenter/safety/email.html">邮箱绑定</a> 
<a href="${context}/ucenter/safety/dealpwd.html">修改交易密码</a> 
<a href="${context}/ucenter/safety/bankCardBind.html">修改交易密码</a> 
-->
		<div class="r_main">
			<div class="tbatil">
				<div class="ci-title">
            <div class="ci-title-inner">
                <h2>安全中心</h2>
                <b class="line"></b>
            </div>
        </div>
				<ul>
					<li>
						<span class="mingzi">手机绑定&nbsp;&nbsp;</span>
						<#if safety.mobilePhone??>
							<div class="set_rightb">
								<span style="">您绑定的手机号为<span style="color:#090;">${safety.mobilePhone!}</span></span>
								<div class="setright">
									<span class="glyphicon glyphicon-ok textsuccess"></span>
									<span class="textsuccess">已绑定</span>
									<span style="color:gray;">&nbsp;|&nbsp;</span>
									<a href="mobile.html">修改</a>
								</div>
							</div>
						<#else>
							<div class="set_rightb">
								<span style="">您还未绑定手机，请绑定!</span>
								<div class="setright">
									<span class="glyphicon glyphicon-ok textfailed"></span>
									<span class="textfailed">未绑定</span>
									<span style="color:gray;">&nbsp;|&nbsp;</span>
									<a href="mobile.html">绑定</a>
								</div>
							</div>
						</#if>
					</li> 
					
					
					
					<li>
						<span class="mingzi">邮箱绑定&nbsp;&nbsp;</span>
						<#if safety.email??>
							<div class="set_rightb">
								<span style="">您绑定的邮箱地址为<span style="color:#090;">${safety.email!}</span></span>
								<div class="setright">
									<span class="glyphicon glyphicon-ok textsuccess"></span>
									<span class="textsuccess">已绑定</span>
									<span style="color:gray;">&nbsp;|&nbsp;</span>
									<a href="email.html">修改</a>
								</div>
							</div>
						<#else>
							<div class="set_rightb">
								<span style="">请绑定您常用的邮箱，用于接收系统通知，密码忘记时可找回密码!</span>
								<div class="setright">
									<span class="glyphicon glyphicon-ok textfailed"></span>
									<span class="textfailed">未绑定</span>
									<span style="color:gray;">&nbsp;|&nbsp;</span>
									<a href="email.html">绑定</a>
								</div>
							</div>
						</#if>					
					</li>
					
					<li>
						<span class="mingzi">新浪托管账户</span>
						<div class="set_rightb">
							<span style="">可使用新浪托管账户</span>
							<div class="setright">
								<span class="glyphicon glyphicon-ok textsuccess"></span>
								<span class="textsuccess">已开通</span>
								<span style="color:gray;">&nbsp;|&nbsp;</span>
								<a href="${context}/ucenter/finance/sinapage.html" target="_blank" >登录</a>
							</div>
						</div>												
					</li>
					<#if safety.real_name??>
					<li>
						<span class="mingzi">实名认证&nbsp;&nbsp;</span>
						<div class="set_rightb">
							<span style="">您已完成实名认证 </span>
							<div class="setright">
								<span class="glyphicon glyphicon-ok textsuccess"></span>
								<span class="textsuccess">已认证</span>
								<span style="color:gray;">&nbsp;|&nbsp;</span>
									<a href="realname.html" name="flag">查看</a>
								
							</div>
						</div>
					</li>
					<#else>
					<li>
						<span class="mingzi">实名认证&nbsp;&nbsp;</span>
						<div class="set_rightb">
							<span style="">认证之后可进行投资 </span>
							<div class="setright">
								<span class="glyphicon glyphicon-ok textfailed"></span>
								<span class="textfailed">未认证</span>
								<span style="color:gray;">&nbsp;|&nbsp;</span>
								<#if safety.role == "1">
									<a href="realname.html" name="flag">认证</a>
								<#else>
									<a href="${context}/ucenter/info.html" name="flag">认证</a>
								</#if>
							</div>
						</div>
					</li>
					</#if>	
					<li>
						<span class="mingzi">修改密码&nbsp;&nbsp;</span>
						<div class="set_rightb">
							<span style="">您可以修改
							<span style="color:#090;">会员登录密码</span>。安全性高的密码可以使帐号更安全,建议您定期更换密码。</span>
							<div class="setright">
								<span class="glyphicon glyphicon-ok textsuccess"></span>
								<span class="textsuccess">已设置</span>
								<span style="color:gray;">&nbsp;|&nbsp;</span>
									<a href="passwd.html" name="flag">修改</a>
								
							</div>
						</div>
					</li>
					<!-----
					<#if safety.bank_card??>
						<li>
							<span class="mingzi">绑定银行卡&nbsp;&nbsp;</span>
							<div class="set_rightb">
								<span style="">银行卡绑定</span>
								<div class="setright">
									<span class="glyphicon glyphicon-ok textsuccess"></span>
									<span class="textsuccess">已绑定</span>
									<span style="color:gray;">&nbsp;|&nbsp;</span>
										<a href="bindBank.html" name="flag">修改</a>
									
								</div>
							</div>
						</li>
					<#else>
						<li>
							<span class="mingzi">绑定银行卡&nbsp;&nbsp;</span>
							<div class="set_rightb">
								<span style="">银行卡绑定</span>
								<div class="setright">
									<span class="glyphicon glyphicon-ok textfailed"></span>
									<span class="textfailed">未绑定</span>
									<span style="color:gray;">&nbsp;|&nbsp;</span>
										<a href="bindBank.html" name="flag">绑定</a>
									
								</div>
							</div>
						</li>
					</#if>
					---->
				</ul>
			</div>
		</div>

</@override>
<@override name="script">
<script>
	$(document).ready(function() {
		$('.myaccount').addClass('active').parents().show();
		$('.submeun-5 a').addClass('active').parents().show();
		changePage();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />