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
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">认证中心</h1>
		</header>
		<nav class="mui-bar mui-bar-tab">
			<a class="mui-tab-item" data-href="${context}/mobile/borrow/borrowPath.html">
				<span class="mui-icon loan"></span>
				<span class="mui-tab-label">我要借款</span>
			</a>
			<a class="mui-tab-item mui-active" data-href="${context}/mobile/borrow/attestation.html">
				<span class="mui-icon approve"></span>
				<span class="mui-tab-label">认证中心</span>
			</a>
			<a class="mui-tab-item" data-href="${context}/mobile/home.html">
				<span class="mui-icon home"></span>
				<span class="mui-tab-label">个人中心</span>
			</a>
		</nav>
		
			<div class="mui-content">
				<p class="p_tip1">基础认证<span class="color_red">必填</span></p>
				<ul class="mui-table-view mui-grid-view attestation-ul">
			        <li class="mui-table-view-cell mui-media" style="border-right: 1px solid #e2e2e2;;border-bottom: 1px solid #e2e2e2;;">
			            <a class="mui-navigate-right" data-href="<#if self_info_status=="0">${context}/mobile/borrow/personInfo.html<#else>javascript:void(0)</#if>" >
			            	<!--<#if self_info_status=="1">
			            		<img class="mui-media-object" src="${context}/asset/mobile/img/renzheng1-2.png">
			            	<#else>
			            		<img class="mui-media-object" src="${context}/asset/mobile/img/renzheng1-1.png">
			            	</#if>-->
			                <img class="mui-media-object" src="${context}/asset/mobile/img/oo (2).png">
			                <div class="mui-media-body">
				                <h2>个人信息</h2>
				                <#if self_info_status=="0">
				                <p>请完善个人信息<img src="${context}/asset/mobile/img/click.png" width="20px"><p>
				                <span class="row_span_r">未完善</span>
				                <#else>
				                <span class="row_span_r color_black">已完善</span>
				                </#if>
			                </div>
			            </a>
			        </li>
			        <li class="mui-table-view-cell mui-media" style="border-bottom: 1px solid #e2e2e2;">
			            <a class="mui-navigate-right" data-href="<#if identity_status=="0" || alipay_status=="0">${context}/mobile/borrow/identityPage.html<#else>javascript:void(0)</#if>">
			            	<!--<#if identity_status=="1">
			            		<img class="mui-media-object" src="${context}/asset/mobile/img/renzheng2-2.png">
			            	<#else>
			            		<img class="mui-media-object" src="${context}/asset/mobile/img/renzheng2-1.png">
			            	</#if>-->
			            	 <img class="mui-media-object" src="${context}/asset/mobile/img/oo (4).png">
			                <div class="mui-media-body">
				                <h2>身份认证</h2>
				                <#if identity_status=="0">
				                <p>请根据要求完善身份认证<img src="${context}/asset/mobile/img/click.png" width="20px"><p>
				                 <span class="row_span_r">未完善</span>
				                 <#else>
				                <span class="row_span_r color_black">已完善</span>
				                </#if>
			                </div>
			            
			            </a>
			        </li>
			        <li class="mui-table-view-cell mui-media" style="border-right: 1px solid #e2e2e2;;border-bottom: 1px solid #e2e2e2;;">
			            <a class="mui-navigate-right" data-href="<#if phone_status=="0">${context}/mobile/borrow/mobileRec.html<#else>javascript:void(0)</#if>">
			            
			            	<!--<#if phone_status=="1">
			            		<img class="mui-media-object" src="${context}/asset/mobile/img/renzheng3-2.png">
			            	<#else>
			            		<img class="mui-media-object" src="${context}/asset/mobile/img/renzheng3-1.png">
			            	</#if>-->
			                 <img class="mui-media-object" src="${context}/asset/mobile/img/oo (5).png">
			                <div class="mui-media-body">
				                <h2>手机认证</h2>
				                <#if phone_status=="0">
				                <p>请根据要求完善手机认证<img src="${context}/asset/mobile/img/click.png" width="20px"><p>
				                 <span class="row_span_r">未完善</span>
				                 <#else>
				                <span class="row_span_r color_black">已完善</span>
				                </#if>
			                </div>
			            
			            </a>
			        </li>
			        <li class="mui-table-view-cell mui-media" style="display:none;border-right: 1px solid #e2e2e2;;border-bottom: 1px solid #e2e2e2;;">
			            <!--<a class="mui-navigate-right" id="zmxy_a" data-href="<#if alipay_status=="0">${aplipayUrl!}<#else>javascript:void(0)</#if>">-->
			            	<a class="mui-navigate-right" id="zmxy_a" data-href="javascript:void(0)">
			            
			            	<!--<#if alipay_status=="1">
			            		<img class="mui-media-object" src="${context}/asset/mobile/img/renzheng4-2.png">
			            	<#else>
			            		<img class="mui-media-object" src="${context}/asset/mobile/img/renzheng4-1.png">
			            	</#if>-->
			                 <img class="mui-media-object" src="${context}/asset/mobile/img/oo (1).png">
			                <div class="mui-media-body">
				                <h2>芝麻认证</h2>
				                <#if alipay_status=="0">
				                <p>芝麻认证有助于提升额度<img src="${context}/asset/mobile/img/click.png" width="20px"><p>
				                 <span class="row_span_r">未完善</span>
				                 <#else>
				                <span class="row_span_r color_black">已完善</span>
				                </#if>
			                </div>
			            
			            </a>
			        </li>
			        <!--人脸识别-->
			         <li class="mui-table-view-cell mui-media" style="display:none;border-right: 1px solid #e2e2e2;;border-bottom: 1px solid #e2e2e2;;">
			            <a class="mui-navigate-right" data-href="${context}/mobile/borrow/faceRec.html">
			            
			            	<!--<#if alipay_status=="1">
			            		<img class="mui-media-object" src="${context}/asset/mobile/img/renzheng4-2.png">
			            	<#else>
			            		<img class="mui-media-object" src="${context}/asset/mobile/img/renzheng4-1.png">
			            	</#if>-->
			                 <img class="mui-media-object" src="${context}/asset/mobile/img/oo (3).png">
			                <div class="mui-media-body">
				                <h2>人脸识别</h2>
				                <#if alipay_status=="0">
				                <p>人脸识别安全级别性高<img src="${context}/asset/mobile/img/click.png" width="20px"><p>
				                <span class="row_span_r">未完善</span>
				                <#else>
				                <span class="row_span_r color_black">已完善</span>
				                </#if>
			                </div>
			            
			            </a>
			        </li>
		   		</ul>
		   		<!--<div class="mui-text-center" style="font-size: 13px;padding: 8px 0;">可选认证<font color="#FA9432">（提交认证后可提高通过率）</font></div>
		   		<ul class="mui-table-view mui-grid-view attestation-ul">
			        <li class="mui-table-view-cell mui-media  " style="border-right: 1px solid #e2e2e2;;border-bottom: 1px solid #e2e2e2;;">
			            <a data-href="#" class="noaudit">
			                <img class="mui-media-object" src="${context}/asset/mobile/img/renzheng5-1.png">
			                <div class="mui-media-body">
				                <h2>淘宝认证</h2>
				                <p>前往提交<img src="${context}/asset/mobile/img/click.png" width="20px"><p>
			                </div>
			            </a>
			        </li>
			        <li class="mui-table-view-cell mui-media  " style="border-bottom: 1px solid #e2e2e2;">
			            <a data-href="#" class="noaudit">
			                <img class="mui-media-object" src="${context}/asset/mobile/img/renzheng6-1.png">
			                <div class="mui-media-body">
			                	<h2>京东认证</h2>
			                	<p>前往提交<img src="${context}/asset/mobile/img/click.png" width="20px"></p>
			                </div>
			            </a>
			        </li>
			        <li class="mui-table-view-cell mui-media  " style="border-right: 1px solid #e2e2e2;;">
			            <a data-href="#" class="noaudit">
			            	<img class="mui-media-object" src="${context}/asset/mobile/img/renzheng7-1.png">
			                <div class="mui-media-body">
			                	<h2>工作信息</h2>
			                	<p>前往提交<img src="${context}/asset/mobile/img/click.png" width="20px"></p>
			                </div>
			            </a>
			        </li>
			        <li class="mui-table-view-cell mui-media  ">
			            <a data-href="#" class="noaudit">
			                <img class="mui-media-object" src="${context}/asset/mobile/img/renzheng8-1.png">
			                <div class="mui-media-body">
			                	<h2>学信认证</h2>
			                	<p>前往提交<img src="${context}/asset/mobile/img/click.png" width="20px"></p>
			                </div>
			            </a>
			        </li>
		   		</ul>-->
			</div>
		
	</body>

	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
	<script type="text/javascript">
		mui.init({
			swipeBack:true
		});
		$(function() {
			mui('body').on('tap','a', function() {
			var url = this.getAttribute("data-href");  
			window.location.href=url;
		});
		})
		
		$('#zmxy_a').on('tap',function(){
			$.post("${context}/mobile/zmxy/certify.do", {}, function(data) {
				if(data.code == 0) {
					window.location.href = data.data;
				} else {
					mui.alert(data.message);
				}
			});
		})
		
		$(".noaudit").click(function(){
			mui.alert("暂不需认证");
		})
		
 
	</script>
	</html>