<@override name="head">
</@override>
<@override name="body">
    <div class="r_main">
      <div class="tabtil">
        <ul>
        	 <li><a href="${context}/ucenter/message.html">系统消息</a></li>
          	 <li class="on">通知设置</li>
        </ul>
     </div>
	<div class="box">
	<div class="boxmain2" style="padding-top:10px;">
        <div class="biaoge2" style="margin-top:0px;">
			<tr>
		<div class="alert alert-warning">
		<strong>注：尊敬的用户，您在医贷汇网的相关操作，会用以上三种方式通知您，请您选择适合自己的通知方式！</strong>
		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="line-height:42px;">
			<td align="left" style="padding-right:40px;">     
			   <input type="checkbox" id="message" onclick="checkAll_MG(this); "/> 站内信通知</td>
			<td>
			  <input type="checkbox" id="messageReceive" class="mg" onclick="mgcheck1(this);" />
			  收到还款 
			  <input type="checkbox" id="messageDeposit"  class="mg" onclick="mgcheck1(this);"/>
			  提现成功 
			  <input type="checkbox" id="messageBorrow" class="mg" onclick="mgcheck1(this);"/>
			  借款成功 
			  <input type="checkbox" id="messageRecharge"  class="mg" onclick="mgcheck1(this);"/>
			  充值成功 
			  <input type="checkbox" id="messageChange" class="mg" onclick="mgcheck1(this);"/>
			  资金变化</td>
			</tr>
			<tr>
			<td align="left" style="padding-right:40px;">
			<input type="checkbox" id="mail" onclick="checkAll_ML(this); "/> 邮件通知</td>
			<td>
			  <input type="checkbox" id="mailReceive"  class="ml" onclick="mlcheck1(this);"/>
			  收到还款 
			  <input type="checkbox" id="mailDeposit"  class="ml" onclick="mlcheck1(this);"/>
			  提现成功 
			  <input type="checkbox" id="mailBorrow" class="ml" onclick="mlcheck1(this);"/>
			  借款成功 
			  <input type="checkbox" id="mailRecharge" class="ml" onclick="mlcheck1(this);"/>
			  充值成功 
			  <input type="checkbox" id="mailChange" class="ml" onclick="mlcheck1(this);"/>
			  资金变化</td>
			</tr>
			<tr>
			<td align="left" style="padding-right:40px;">
			<input type="checkbox" id="note" onclick="checkAll_NT(this); "/> 短信通知</td>
			<td>
			  <input type="checkbox" id="noteReceive" class="nt" onclick="ntcheck1(this);"/>
			  收到还款 
			  <input type="checkbox" id="noteDeposit" class="nt" onclick="ntcheck1(this);"/>
			  提现成功 
			  <input type="checkbox" id="noteBorrow" class="nt" onclick="ntcheck1(this);"/>
			  借款成功 
			  <input type="checkbox" id="noteRecharge" class="nt" onclick="ntcheck1(this);"/>
			  充值成功 
			  <input type="checkbox" id="noteChange" class="nt" onclick="ntcheck1(this);"/>
			  资金变化</td>
			</tr>
			<tr>
			<td align="right">&nbsp;</td>
			<td style="padding-top:10px;">
			<a href="javascript:void(0);" class="btn btn-primary" onclick="addNoteSetting();">确认</a></td>
			</tr>
		</table>
		</div>
    </div>
    </div>
</div>
</@override>
<@override name="script">
<script>
	$(document).ready(function() {
	$('.myaccount').addClass('active').parents().show();
	$('.submeun-7 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />