<@override name="head">
<link href="${context}/asset/ucenter/css/home.css" rel="stylesheet" />
</@override>
<@override name="body">
	<!-- 会员中心主页 --> 
	
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
	$(function(){
		$('.submeun-4').addClass('selected').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />