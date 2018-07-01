<#assign context="">
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>认证结果</title>
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="//res.wx.qq.com/open/libs/weui/1.1.1/weui.min.css" />

<style>

</style>
</head>
<body>
	<div class="weui-msg">
	    <div class="weui-msg__icon-area">
	    	<#if status == 0>
	    	<i class="weui-icon-success weui-icon_msg"></i>
	    	<#else>
	    	<i class="weui-icon-warn weui-icon_msg"></i>
	    	</#if>
	    </div>
	    <div class="weui-msg__text-area">
	    	 <h2 class="weui-msg__title">${result}</h2>
	    </div> 
	    <div class="weui-msg__opr-area">
	        <p class="weui-btn-area">
	        	<#if status == 0>
		    	<a href="javascript:result(1)" class="weui-btn weui-btn_primary">确定返回</a>
		    	<#else>
		    	<a href="javascript:result(0)" class="weui-btn weui-btn_primary">确定返回</a>
		    	</#if>
	        </p>
	    </div>
	    <div class="weui-msg__extra-area">
	       
	    </div>
	</div>
</body>
<script>
	<#if weixinBindBankCard?? && weixinBindBankCard?string == '1'>
	function result(status){
		location.href = "${context}/mobile/borrow/borrowPath.html";
	}
	<#else>
	function result(status){
		if(window.android){
			window.android.llpayResult(status);
		}
		else {
			llpayResult(status);
		}
	}
	
	</#if>
</script>
</html>