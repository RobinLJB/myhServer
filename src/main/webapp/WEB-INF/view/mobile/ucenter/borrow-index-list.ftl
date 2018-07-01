
<#assign context="${rc.contextPath}">
	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>${webname}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${context}/asset/mobile/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="${context}/asset/mobile/css/mobile-front.css" />
	</head>
	<body>
		<input type="hidden" id="memberStatus" value="${memberStatus!0}">
		
		<header class="mui-bar mui-bar-nav index-bar-nav">
			<h1 class="mui-title">信汇钱宝</h1>
			<a class="mui-icon mui-pull-right" data-href="${context}/mobile/ucenter/moreQuestion.html?type=2"></a>
		</header>
		
		<nav class="mui-bar mui-bar-tab">
			<a class="mui-tab-item mui-active" data-href="${context}/mobile/borrow/borrowPath.html">
				<span class="mui-icon loan"></span>
				<span class="mui-tab-label">我要借款</span>
			</a>
			<a class="mui-tab-item" data-href="${context}/mobile/borrow/attestation.html">
				<span class="mui-icon approve"></span>
				<span class="mui-tab-label">认证中心</span>
			</a>
			<a class="mui-tab-item" data-href="${context}/mobile/home.html">
				<span class="mui-icon home"></span>
				<span class="mui-tab-label">个人中心</span>
			</a>
		</nav>
			<div class="mui-content index-content">
				<!--500-->
			<div class="mui-card card beforeCheck" datas="500" style="height: 165px;" id="item500" clicked='false'>
				<div id="beforeCheck" datas="500" style=" position: absolute;width: 100%;height: 100%;top: 0;left: 0;">
					<div class="mui-card-header mui-row" style="padding-bottom: 0;">
				        <div class="mui-col-sm-5 mui-col-xs-5">
				        	<!--<div class="card-header-logo">
				        		<img src="${context}/asset/mobile/img/logo02.png" width="100%" height="100%">
				        	</div>
				        	<div class="card-header-title">
				        		<h2>飞速下款</h2>
				        		<p>PAPID EXHAUSTING</p>
				        	</div>-->
				        	<span class="card-header-left">一键下款</span>
				        </div>
				        <div class="mui-col-sm-7 mui-col-xs-7">
				        	<div class="card-header-right">
				        		<a data-href="">成功借款${borrowSuccessCount!0}次<img src="${context}/asset/mobile/img/cg-ioc.png"></a>
				        	</div>
				        </div>
					</div>
					<div class="mui-card-content" style="padding-bottom: 22px;">
						<div class="mui-card-content-inner">
							<span class="xinyong">信用额度</span>
							<h1>￥500</h1>
						</div>
						<div class="xyed mui-text-left">信用借款极速到账</div>
						<div class="xyed mui-text-right">立即借款</div>
					</div>
					<!--<div class="mui-card-footer mui-row">
						<div class="mui-col-sm-6 mui-col-xs-6">
				        	成功借款${borrowTimes!0}次
				    	</div>
				    	<div class="mui-col-sm-6 mui-col-xs-6 mui-text-right">
				       	   <a data-href="" class="lj-loan">认证0/5次<img src="${context}/asset/mobile/img/fanhui.png"></a>
				    	</div>
				    </div>-->
				
				</div>
			</div>
			<!--1000-->
			<div class="mui-card card beforeCheck" datas="1000" style="height: 165px;" id="item1000" clicked='false'>
				<div id="beforeCheck" datas="1000" style=" position: absolute;width: 100%;height: 100%;top: 0;left: 0;">
					<div class="mui-card-header mui-row" style="padding-bottom: 0;">
				        <div class="mui-col-sm-5 mui-col-xs-5">
				        	<!--<div class="card-header-logo">
				        		<img src="${context}/asset/mobile/img/logo02.png" width="100%" height="100%">
				        	</div>
				        	<div class="card-header-title">
				        		<h2>飞速下款</h2>
				        		<p>PAPID EXHAUSTING</p>
				        	</div>-->
				        	<span class="card-header-left">一键下款</span>
				        </div>
				        <div class="mui-col-sm-7 mui-col-xs-7">
				        	<div class="card-header-right">
				        		<a data-href="">成功借款${borrowSuccessCount!0}次<img src="${context}/asset/mobile/img/cg-ioc.png"></a>
				        	</div>
				        </div>
					</div>
					<div class="mui-card-content" style="padding-bottom: 22px;">
						<div class="mui-card-content-inner">
							<span class="xinyong">信用额度</span>
							<h1>￥1000</h1>
						</div>
						<div class="xyed mui-text-left">信用借款极速到账</div>
						<div class="xyed mui-text-right">立即借款</div>
					</div>
					<!--<div class="mui-card-footer mui-row">
						<div class="mui-col-sm-6 mui-col-xs-6">
				        	成功借款${borrowTimes!0}次
				    	</div>
				    	<div class="mui-col-sm-6 mui-col-xs-6 mui-text-right">
				       	   <a data-href="" class="lj-loan">认证0/5次<img src="${context}/asset/mobile/img/fanhui.png"></a>
				    	</div>
				    </div>-->
				
				</div>
			</div>
			<!--1500-->
			<div class="mui-card card beforeCheck" datas="1500" style="height: 165px;" clicked='false'>
				<div id="beforeCheck" datas="1500" style=" position: absolute;width: 100%;height: 100%;top: 0;left: 0;">
					<div class="mui-card-header mui-row" style="padding-bottom: 0;">
				        <div class="mui-col-sm-5 mui-col-xs-5">
				        	<!--<div class="card-header-logo">
				        		<img src="${context}/asset/mobile/img/logo02.png" width="100%" height="100%">
				        	</div>
				        	<div class="card-header-title">
				        		<h2>飞速下款</h2>
				        		<p>PAPID EXHAUSTING</p>
				        	</div>-->
				        	<span class="card-header-left">一键下款</span>
				        </div>
				        <div class="mui-col-sm-7 mui-col-xs-7">
				        	<div class="card-header-right">
				        		<a data-href="">成功借款${borrowSuccessCount!0}次<img src="${context}/asset/mobile/img/cg-ioc.png"></a>
				        	</div>
				        </div>
					</div>
					<div class="mui-card-content" style="padding-bottom: 22px;">
						<div class="mui-card-content-inner">
							<span class="xinyong">信用额度</span>
							<h1>￥1500</h1>
						</div>
						<div class="xyed mui-text-left">信用借款极速到账</div>
						<div class="xyed mui-text-right">立即借款</div>
					</div>
					<!--<div class="mui-card-footer mui-row">
						<div class="mui-col-sm-6 mui-col-xs-6">
				        	成功借款${borrowTimes!0}次
				    	</div>
				    	<div class="mui-col-sm-6 mui-col-xs-6 mui-text-right">
				       	   <a data-href="" class="lj-loan">认证0/5次<img src="${context}/asset/mobile/img/fanhui.png"></a>
				    	</div>
				    </div>-->
				
				</div>
			</div>
			<!--2000-->
			<div class="mui-card card beforeCheck" datas="2000" style="height: 165px;" id="item1000" clicked='false'>
				<div id="beforeCheck" datas="2000" style=" position: absolute;width: 100%;height: 100%;top: 0;left: 0;">
					<div class="mui-card-header mui-row" style="padding-bottom: 0;">
				        <div class="mui-col-sm-5 mui-col-xs-5">
				        	<!--<div class="card-header-logo">
				        		<img src="${context}/asset/mobile/img/logo02.png" width="100%" height="100%">
				        	</div>
				        	<div class="card-header-title">
				        		<h2>飞速下款</h2>
				        		<p>PAPID EXHAUSTING</p>
				        	</div>-->
				        	<span class="card-header-left">一键下款</span>
				        </div>
				        <div class="mui-col-sm-7 mui-col-xs-7">
				        	<div class="card-header-right">
				        		<a data-href="">成功借款${borrowSuccessCount!0}次<img src="${context}/asset/mobile/img/cg-ioc.png"></a>
				        	</div>
				        </div>
					</div>
					<div class="mui-card-content" style="padding-bottom: 22px;">
						<div class="mui-card-content-inner">
							<span class="xinyong">信用额度</span>
							<h1>￥2000</h1>
						</div>
						<div class="xyed mui-text-left">信用借款极速到账</div>
						<div class="xyed mui-text-right">立即借款</div>
					</div>
					<!--<div class="mui-card-footer mui-row">
						<div class="mui-col-sm-6 mui-col-xs-6">
				        	成功借款${borrowTimes!0}次
				    	</div>
				    	<div class="mui-col-sm-6 mui-col-xs-6 mui-text-right">
				       	   <a data-href="" class="lj-loan">认证0/5次<img src="${context}/asset/mobile/img/fanhui.png"></a>
				    	</div>
				    </div>-->
				
				</div>
			</div>
			<!--3000-->
			<div class="mui-card card beforeCheck" datas="3000" style="height: 165px;" id="" clicked='false'>
				<div id="beforeCheck" datas="3000" style=" position: absolute;width: 100%;height: 100%;top: 0;left: 0;">
					<div class="mui-card-header mui-row" style="padding-bottom: 0;">
				        <div class="mui-col-sm-5 mui-col-xs-5">
				        	<!--<div class="card-header-logo">
				        		<img src="${context}/asset/mobile/img/logo02.png" width="100%" height="100%">
				        	</div>
				        	<div class="card-header-title">
				        		<h2>飞速下款</h2>
				        		<p>PAPID EXHAUSTING</p>
				        	</div>-->
				        	<span class="card-header-left">一键下款</span>
				        </div>
				        <div class="mui-col-sm-7 mui-col-xs-7">
				        	<div class="card-header-right">
				        		<a data-href="">成功借款${borrowSuccessCount!0}次<img src="${context}/asset/mobile/img/cg-ioc.png"></a>
				        	</div>
				        </div>
					</div>
					<div class="mui-card-content" style="padding-bottom: 22px;">
						<div class="mui-card-content-inner">
							<span class="xinyong">信用额度</span>
							<h1>￥3000</h1>
						</div>
						<div class="xyed mui-text-left">信用借款极速到账</div>
						<div class="xyed mui-text-right">立即借款</div>
					</div>
					<!--<div class="mui-card-footer mui-row">
						<div class="mui-col-sm-6 mui-col-xs-6">
				        	成功借款${borrowTimes!0}次
				    	</div>
				    	<div class="mui-col-sm-6 mui-col-xs-6 mui-text-right">
				       	   <a data-href="" class="lj-loan">认证0/5次<img src="${context}/asset/mobile/img/fanhui.png"></a>
				    	</div>
				    </div>-->
				
				</div>
			</div>
			<!--<div class="range">
				<h5 style="clear: left;color: #000;font-size: 18px;">借款金额：<span id="block-range-val">500元</span></h5>
				<div class="mui-input-row" style="padding: 15px 0;">
			        <input type="range" id="block-range">
			    </div>
			    <p class="mui-pull-left">500</p>
			    <p class="mui-pull-right">2000</p>
			</div>
			<button type="button" id="borrow_btn" class="mui-btn mui-btn-primary mui-btn-block">开始借款</button>-->
	
			<!--<div class="mui-card card mui-alerat">
				<div class="mui-card-header mui-row">
				        <div class="mui-col-sm-7 mui-col-xs-7" style="width: 100%;">
				        	<div class="card-header-logo">
				        		<img src="${context}/asset/mobile/img/logo02.png" width="100%" height="100%">
				        	</div>
				        	<div class="card-header-title">
				        		<h2>信用不足 敬请期待</h2>
				        		<p>PAPID EXHAUSTING</p>
				        	</div>
				        </div>
				</div>
				<div class="mui-card-content">
					<div class="mui-card-content-inner">
						<h1 style="color: #78CCFD;"><span>信用额度 ￥ </span>1500</h1>
					</div>
				</div>
				<div class="mui-card-footer mui-row" style="color: #78CCFD;">
					<div class="mui-col-sm-6 mui-col-xs-6">
				        免息券：0 张
				    </div>
				</div>
			</div>-->
			
			</div>
			
		<!-- 提示不足弹窗 -->
		<div id="alerat">
			<div class="alerat-content">
				<div class="closure">
					<img src="${context}/asset/mobile/img/close.png" class="close">
				</div>
				<div class="content">
					<div class="tishi">
						<img src="${context}/asset/mobile/img/tishi.png" height="40px">
						<span>提 示</span>
					</div>
					<p>您的信用额度不足，请选择<br/>其他额度</p>
				</div>
				<button type="button" class="mui-btn mui-btn-primary mui-btn-block close">取 消</button>
			</div>
		</div>
	</body>

	<script type="text/javascript" src="${context}/asset/mobile/js/mui.js"></script>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/range.js"></script>
	<script type="text/javascript">
		function aBtnClick(id){
				mui.alert(id);
			}
		$(function() {
			mui('body').on('tap','a', function() {
			var url = this.getAttribute("data-href");  
			window.location.href=url;
			});
			var memberStatus= $("#memberStatus").val();
			if(memberStatus==3){
				mui.alert("你是黑名单用户，请联系后台");
				return;
			}
		})
		$(".mui-alerat").click(function(){
			$("#alerat").show();
		});
		$(".close").click(function(){
			$("#alerat").hide();
		});
		
		
		//借款前的检查
		$(".mui-card").click(function() {
			if($(this).attr('clicked')=='true'){return};
			var datas=$(this).attr('datas');
			$(this).attr('clicked','true')
			var params = {};
			var benjin=parseInt(datas);
			$(this).attr('disabled',true);
			mui.toast('正在跳转...');
			
			$.post("${context}/mobile/borrow/beforeBorrowCheck.do", params, function(data) {
				
				//未认证
				if(data.code<=-2 &&  data.code>=-11){
					mui.alert(data.message,function(){
						location.href = "${context}/mobile/borrow/attestation.html";
					});
				}
				if(data.code==-13 || data.code==-14 || data.code==-15 || data.code==-1){
					mui.alert(data.message);
				}
				//传递本金参数
				if(data.code==0){
					console.log(benjin)
					location.href = "${context}/mobile/borrow/borrowApply.html?benjin="+benjin;
				}
				if(data.code>0){
					mui.alert(data.message,function(){
						location.href = "${context}/mobile/borrow/borrowStatus.html?borrowId="+data.data;
					});
				}
				
				
				
			}, 'json');

		});
		
		$(function(){
			//监听input事件，获取range的value值，也可以直接element.value获取该range的值
		    var rangeList = document.querySelectorAll('input[type="range"]');
		    for(var i=0,len=rangeList.length;i<len;i++){
		        rangeList[i].addEventListener('input',function(){
		            if(this.id.indexOf('field')>=0){
		                document.getElementById(this.id+'-input').value = this.value;
		            }else{
		                document.getElementById(this.id+'-val').innerHTML = this.value +'元';
		            }
		            
		
		        });
		    }
			
			//range
			 var change = function($input) {
                /*内容可自行定义*/
            }

            $('#block-range').RangeSlider({ min: 500,   max: 2000,  step: 100,  callback: change});
		
		    
		})
		
		
		
	</script>
	</html>
