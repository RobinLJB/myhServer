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
		<link href="${context}/asset/mobile/css/mui.picker.css" rel="stylesheet" />
		<link href="${context}/asset/mobile/css/mui.poppicker.css" rel="stylesheet" />
	</head>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">个人信息</h1>
			<!--<a class="mui-icon mui-per-right" data-href="javascript:;"></a>-->
		</header>
		<div class="mui-content">
				<form class="mui-input-group personal" id="commentForm">
					<div class="info-title">基本资料</div>
					<div class="mui-input-row">
						<label>QQ号码</label>
						<input type="text"  name="qqno" placeholder="请输入您的QQ号码">
					</div>
					<div class="mui-input-row">
						<label>微信号码</label>
						<input type="text" name="weino" placeholder="请输入您的微信号码">
					</div>
					<div class="mui-input-row wu" id="academic">
						<label>学历</label>
						<input type="text" name="xueli" placeholder="请选择学历" value="" id="academicinput">
						<img src="${context}/asset/mobile/img/xiala.png">
					</div>
					<div class="info-title">紧急联系人</div>
					<div class="mui-input-row">
						<label>姓名</label>
						<input type="text" name="name" placeholder="请输入紧急联系姓名">
					</div>
					<div class="mui-input-row">
						<label>手机号码</label>
						<input type="text" name="phone" placeholder="请输入手机号码">
					</div>
					<div class="mui-input-row wu" id="relation">
						<label>与本人关系</label>
						<input type="text" name="relation" placeholder="请选择" id="relationinput">
						<img src="${context}/asset/mobile/img/xiala.png">
					</div>
				</form>
				<button type="button" id="save" class="mui-btn mui-btn-blue mui-btn-block btn-bear">提 交</button>
		</div>
	</body>
<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.picker.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.poppicker.js"></script>
	<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		
	</script>
	<script type="text/javascript">
		mui.init({
			swipeBack:true
		});
		(function($, doc) {
				$.init();
				$.ready(function() {

					//学历选择
					var academicPicker = new $.PopPicker();
					academicPicker.setData([{
						value: 'cz',
						text: '初中及以下'
					}, {
						value: 'gz',
						text: '高中'
					}, {
						value: 'dz',
						text: '大专'
					}, {
						value: 'bk',
						text: '本科'
					}, {
						value: 'yjs', 
						text: '研究生及以上'
					}]);

					//关系选择
					var relationPicker = new $.PopPicker();
					relationPicker.setData([{
						value: 'js',
						text: '家属'
					}, {
						value: 'py',
						text: '朋友 '
					}, {
						value: 'ts',
						text: '同事'
					}, {
						value: 'tx',
						text: '同学'
					}]);
					
					var academic = doc.getElementById('academic');
					var relation = doc.getElementById('relation');
					var academicinput = doc.getElementById('academicinput');
					var relationinput = doc.getElementById('relationinput');
					academic.addEventListener('tap', function(event) {
						academicPicker.show(function(items) {
							var rowStr = JSON.stringify(items[0].text).replace(/\"/g, "");
							academicinput.value =  rowStr;
						});
					}, false);

					relation.addEventListener('tap', function(event) {
						relationPicker.show(function(items) {
							var rowStr = JSON.stringify(items[0].text).replace(/\"/g, "");
							relationinput.value =  rowStr;
						});
					}, false);
				});
			})(mui, document);
			
			
			
			//保存
		$("#save").click(function() {
		
			var params = {};
			params['qqno'] = $('input[name="qqno"]').val();
			params['weino'] = $('input[name="weino"]').val();
			params['xueli'] = $('input[name="xueli"]').val();
			params['name'] = $('input[name="name"]').val();
			params['phone'] = $('input[name="phone"]').val();
			params['relation'] = $('input[name="relation"]').val();
			
			if($('input[name="qqno"]').val() == "") {
				mui.alert("QQ不能为空");
				return false;
			}
			if($('input[name="weino"]').val() == "") {
				mui.alert("微信不能为空");
				return false;
			}
			
				$.post("${context}/mobile/borrow/savePersonInfo.do", params, function(data) {
					if(data.code == '0') {
						location.href = "${redirect!'${context}/mobile/borrow/attestation.html'}";
					} else {
						alert(data.message);
					}
				}, 'json');	

		});
 
	</script>
	
	
	</html>