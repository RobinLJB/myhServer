<@override name="head">

</@override>
<@override name="body">
	<#if identity??>
		<#if identity.status == "2">
			<h3>您已通过实名认证</h3>
		</#if>
		<#if identity.status == "1">
			<h3>等待审核</h3>
		</#if>
		<#if identity.status == "3">
			<h3>审核失败，请再次提交材料</h3>
		</#if>
	<#else>
		<h3>请填写认证资料</h3>
	</#if>
	
	<br><br>
	
	<div class="page-content">
	<form class="form-horizontal" id="articleform" action="${context}/ucenter/aaa/cardIdCheckedPost.do"  method="POST">
		<input type="hidden" id="id" name="id" value="<#if identity??>${identity.id!-1}<#else>-1</#if>" />
		<div class="form-group">
			<label for="realName" class="col-sm-2 control-label">真实姓名</label>
			<div class="col-sm-5">
				<input type="text" class="form-control required" minlength="2"   maxlength="30" name="realName" id="realName" placeholder="真实姓名" value="<#if identity??>${identity.real_name!}<#else></#if>">
			</div>
		</div>
		
		<div class="form-group">
			<label for="cardNo" class="col-sm-2 control-label">身份证号</label>
			<div class="col-sm-5">
				<input type="text" class="form-control required" minlength="18"   maxlength="18" name="cardNo" id="cardNo" placeholder="身份证号" value="<#if identity??>${identity.card_no!}<#else></#if>">
			</div>
		</div>
		
		<div class="form-group">
			<input id="imgPatha" type="hidden" name="cardImgurl" value="<#if identity??>${identity.card_imgurl!}<#else></#if>" />
			<label class="control-label col-sm-2">身份证照</label>
			<div class="col-sm-4">
				<div class="img-holder">
					<img id="imgPreviewa" src="<#if identity??>${identity.card_imgurl!}<#else></#if>" />
					<span class="btn-upload btn btn-primary btn-shadow">
						<input type="file" accept="image/jpg,image/jpeg,image/png" dataImg="imgPreviewa" dataInput="imgPatha" class="InputFile"  id="InputFile" name="file" />
						<i class="fa fa-image"></i>&nbsp;选择图片
					</span>
				</div>
			</div>
		</div>
		
				
		
		
		<div class="form-group">
			<input id="imgPathb" type="hidden" name="peopleImgurl" value="<#if identity??>${identity.people_imgurl!}<#else></#if>" />
			<label class="control-label col-sm-2">人像照片</label>
			<div class="col-sm-4">
				<div class="img-holder">
					<img id="imgPreviewb" src="<#if identity??>${identity.people_imgurl!}<#else></#if>" />
					<span class="btn-upload btn btn-primary btn-shadow">
						<input type="file" accept="image/jpg,image/jpeg,image/png" dataImg="imgPreviewb" dataInput="imgPathb" class="InputFile"  id="InputFile" name="file" />
						<i class="fa fa-image"></i>&nbsp;选择图片
					</span>
				</div>
			</div>
		</div>
		
		<#if identity??>
			<#if identity.status == "2" || identity.status == "3">
				<div class="form-group">
					<label for="checkView" class="col-sm-2 control-label">审核意见</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" name="checkView" id="checkView" placeholder="身份证号" value="<#if identity??>${identity.check_view!}<#else></#if>">
					</div>
				</div>
			</#if>
			<#if identity.status == "1">
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-4">
						<button id="savec" type="button" class="btn btn-primary">修改提交</button>
					</div>
				</div>
			</#if>
			<#if identity.status == "3">
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-4">
						<button id="saveb" type="button" class="btn btn-primary">重新提交</button>
					</div>
				</div>
			</#if>
		<#else>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-4">
					<button id="savea" type="button" class="btn btn-primary">提交审核</button>
				</div>
			</div>
		</#if>
		
	</form>	
	</div>
	
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>


<script>
		
	
	$('#savea,#saveb,#savec').click(function(){
		var ids=$("#id").val();
		var realName=$("#realName").val();
		var cardNo=$("#cardNo").val();
		var cardImgurl=$("#imgPatha").val();
		var peopleImgurl=$("#imgPathb").val();
		$.ajax({
			url:'${context}/ucenter/aaa/cardIdCheckedPost.do',
			type:'post',
			data:{ids:ids,realName:realName,cardNo:cardNo,cardImgurl:cardImgurl,peopleImgurl:peopleImgurl},
			success:function(data){
				if(data.code == 0){
					alert('操作成功');
					location.href="${context}/admin/cms/article.html";
				}else{
					alert('操作失败');
				}
			}
		});
	})
	
	$('.InputFile').AjaxFileUpload({
			//处理文件上传操作的服务器端地址
			action: '${context}/upload/image.do?type=3',
			onComplete: function(filename, resp) { //服务器响应成功时的处理函数
				if(resp.code == 0){
					var dataImg=$(this).attr("dataImg");
					var dataInput=$(this).attr("dataInput");
					$("#"+dataImg).attr('src',resp.absPath);
					$("#"+dataInput).val(resp.absPath);
				}else {
					alert('保存图片失败');
				}
			}
		});
		
		
	
			
		
	
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-update.ftl" />