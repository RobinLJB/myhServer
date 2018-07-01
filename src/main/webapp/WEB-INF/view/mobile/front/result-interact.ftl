<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>认证结果</title>
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="//res.wx.qq.com/open/libs/weui/1.1.1/weui.min.css" />
</head>
<body>
	<div class="weui-msg" id="app">
	    <div class="weui-msg__icon-area">
	    	<i v-if="result.code == 0" class="weui-icon-success weui-icon_msg"></i>
	    	<i v-else class="weui-icon-warn weui-icon_msg"></i>
	    </div>
	    <div class="weui-msg__text-area">
	    	<h2 class="weui-msg__title">{{ result.message }}</h2>
	    	<p v-if="result.code == 0">预计需要等待1分钟左右以确保数据采集成功。如果失败，请稍后再试！</p>
	    </div>
	    <div class="weui-msg__opr-area">
	        <p class="weui-btn-area">
		    	<a v-if="result.code == 0" v-bind:click="queryReport" class="weui-btn weui-btn_primary">确定返回</a>
		    	<a v-else href="javascript:result(0)" class="weui-btn weui-btn_primary">重试</a>
	        </p>
	    </div>
	    <div class="weui-msg__extra-area">
	       
	    </div>
	</div>
	<div id="loadingToast" style="opacity: 1;display: none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">正在获取报告</p>
        </div>
    </div>
    <div id="toast" style="opacity: 1; display: none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-icon-info weui-icon_toast"></i>
            <p class="weui-toast__content"></p>
        </div>
    </div>
</body>
<script src="//cdn.bootcss.com/vue/2.4.2/vue.min.js"></script>
<script type="text/javascript" src="/asset/public/plugins/jquery.min.js"></script>
<script>
	$(function(){
		showToast();
		var isLoading = false;
		var isSuccess = false;
		var _interval = window.setInterval(function(){
			if(!isLoading && !isSuccess){
				isLoading = true;
				$.ajax({
					url:"/vendor/auth/report/query/${uid?c}-${type}-<#if params.task_id??>${params.task_id}<#elseif params.outUniqueId??>${params.outUniqueId}</#if>",
					type:'post',
					success:function(resp){
						isLoading = false;
						if(resp.code == 0){
							isSuccess = true;
							hideToast();
							result(1);
						}
					},
					error:function(){
						isLoading = false;
						hideToast();
						showMessage('系统采集失败，请稍后再试或联系客服人员');
					}
				});
			}
		},10*1000);
	})
	
	function showMessage(msg){
		$('#toast').show().find('.weui-toast__content').text(msg);
		setTimeout(function(){
			$('#toast').hide();
		},3000);
	}
	function showToast(){
		$('#loadingToast').show();
	}
	function hideToast(){
		$('#loadingToast').hide();
	}
	var app = new Vue({
		el:'#app',
		data:{
			result:${result}
		},
		methods:{
			queryReport:function(){

			}
		}
	});
	<#if Session.authReturnUrl??>
	function result(status){
		location.href = "${Session.authReturnUrl}";
	}
	<#else>
	function result(status){
		if(window.android){
			window.android.authResult(status);
		}
		else {
			authResult(status);
		}
	}
	</#if>
</script>
</html>