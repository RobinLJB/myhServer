<@override name="head">
<style>
.imgPreview{
height:100px;
margin-bottom:10px;
}
.btn-upload{
	position:relative;
}
.btn-upload input[type="file"] {
    position: absolute;
    opacity: 0;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
    width: 100%;
    margin: 0;
    padding: 0;
    cursor: pointer;
}
.form-group .control-label {
   margin-top: 10px;
}
 
.wrap .container{
	margin-top:30px;
	margin-bottom:30px;
}
</style>
</@override>
<@override name="body">
	<!-- 会员中心主页 --> 
	<#if person.status?? && person.status == '1'>
	<div class="alert alert-success">
		<h3>您已通过实名认证</h3>
	</div>
	<form class="form-horizontal">
	 
		<div class="form-group">
			<label class="col-sm-offset-2 col-sm-2 control-label">真实姓名</label>
			<div class="col-md-4">
				<p class="form-control-static">${person.realName!}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-offset-2 col-sm-2 control-label">身份证号</label>
			<div class="col-md-4">
				<p class="form-control-static"><@mask value="${person.idCardNum!}" start="4"  length="10" symbol="*"/></p>
			</div>
		</div>
	 
	</form>
	<#else>
	<div class="alert alert-success">
		<h3>请输入正确的信息</h3>
	</div>
	<form class="form-horizontal">
		<div class="form-group">
			<label class="col-sm-offset-2 col-sm-2 control-label">真实姓名</label>
			<span class="required" aria-required="true">*</span>
			<div class="col-md-4">
				<input type="text" class="form-control required" id="realName" name="realName"  >
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-offset-2 col-sm-2 control-label">身份证号</label>
			<span class="required" aria-required="true">*</span>
			<div class="col-md-4">
				<input type="text" class="form-control required" id="idNo" name="idNo" >
			</div>
		</div>
		<div class="form-group">
		<button type="submit" id="btn_sub" data-loading-text="正在提交..." class="col-sm-offset-4  btn btn-default">提交</button>
		</div>
	</form>	
	</#if>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
<script>
	$('.submeun-4').addClass('selected').parents().show();
	$(function(){
		$("#btn_sub").click(function(){
			$("#btn_sub").attr("disabled", true);
			var realName= $("#realName").val();
			var idNo= $("#idNo").val();
			$.post("realname.do",{"realName":realName,"idNo":idNo},function(result){
				if(result.code == 0){
					location.href = '${context}/ucenter/safety/index.html';
				}else{
					$("#btn_sub").attr("disabled", false);
					spark.alert(result.message);
				}
				
			});
			
		});
		//图片上传
		/*
		$('.InputFile').AjaxFileUpload({
			//处理文件上传操作的服务器端地址
			action: '${context}/upload/image.do?type=3',
			onComplete: function(filename, resp) { //服务器响应成功时的处理函数
				if(resp.code == 0){
					$(this).parent().siblings("input[class='cardImg']").val(resp.absPath);
					$(this).parent().siblings("img[class='imgPreview']").attr('src',resp.absPath);
				}
				else {
					spark.notify(resp.message,'error');
				}
			}
		});
		*/
	})
	
	
	
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-update.ftl" />