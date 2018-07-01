<@override name="head">
<link href="${context}/asset/public/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">
<style type="text/css">
	.form-group .col-sm-2{text-align: left;}
	.help-block{display: inline-block;}
</style>
</@override>
<@override name="body">
 <div class="r_main">
 	<div class="aqi"><div class="aq">个人资料</div></div>
 	<div class="box">
	<form class="form-horizontal" id="form" action="${context}/ucenter/person/profile.do"  method="POST">
	
		<div class="form-group">
			<label  class="col-sm-offset-3 col-sm-2 control-label">选择银行
			</label>
			<div class="col-sm-4">
			    <select class="form-control" id="bankName" name="bankName">
			    	<#list bankList as banks>
			    		<option value="${banks.name}" <#if bankMap??><#if banks.name=="${bankMap.bankName!}">selected</#if></#if>>${banks.name}</option>
			    	</#list>
				</select>
			</div>
    	</div>
		
	
		<div class="form-group">
			<label for="branchBank" class="col-sm-2 control-label">支行名称</label>
			<div class="col-sm-5">
				<input type="text" class="form-control required"  name="branchBank" id="branchBank" placeholder="支行名称" value="<#if bankMap??>${bankMap.branchBank!}<#else></#if>">
			</div>
		</div>
		
		<div class="form-group">
			<label for="cardNo" class="col-sm-2 control-label">银行卡号</label>
			<div class="col-sm-5">
				<input type="text" class="form-control required" minlength="10"   maxlength="30" name="cardNo" id="cardNo" placeholder="银行卡号" value="<#if bankMap??>${bankMap.cardNo!}<#else></#if>">
			</div>
		</div>
		
		
		<div class="form-group">
			<label for="phone" class="col-sm-2 control-label">预留手机号</label>
			<div class="col-sm-5">
				<input type="text" class="form-control required number" minlength="11"   maxlength="11" name="phone" id="phone" placeholder="预留手机号" value="<#if bankMap??>${bankMap.phone!}<#else></#if>">
			</div>
		</div>
		
		<#if bankMap??>
			<div class="form-group">
				<label class="col-sm-offset-3 col-sm-2 control-label">所在城市:</label>
				<div class="col-md-4"  id="element_id"> 
					<input type="text" class="form-control "  name="province" id="province"  value="<#if bankMap??>${bankMap.province!}<#else></#if>">
					<input type="text" class="form-control "  name="city" id="city"  value="<#if bankMap??>${bankMap.city!}<#else></#if>">
				</div> 
			</div>
		<#else>
			<div class="form-group">
				<label class="col-sm-offset-3 col-sm-2 control-label">所在城市:</label>
				<div class="col-md-4"  id="element_id"> 
					<select class="form-control required province" style="width: 48%;float: left;" name="province"  id="province"></select> 
					<select class="form-control required city" style="width: 48%;float: right;" name="city"  id="city"></select> 
				</div> 
			</div>
		</#if>
		
		<#if bankMap??>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<span class="btn btn-primary">等待审核</span>
			</div>
		</div>
		<#else>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button id="savea" type="button" class="btn btn-primary">提交审核</button>
			</div>
		</div>
		</#if>
	
	</form>
	</div>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script type="text/javascript" src='${context}/asset/public/plugins/uploadify/swfobject.js'></script>  
<script type="text/javascript" src='${context}/asset/public/plugins/uploadify/jquery.uploadify.min.js'></script> 
<script type="text/javascript" src='${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js'></script> 
<script type="text/javascript" src='${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap.min.js'></script> 
<script type="text/javascript"  src="${context}/asset/public/plugins/cxselect/js/jquery.cxselect.min.js"></script>


<script>
$(function(){
	FormValidation.validation('#form',function(resp){
		if(resp.code==0){
			alert("提交成功");
			window.location.reload();
		}else{
			spark.alert("提交失败","error");
		}
	});
});


 
$('#element_id').cxSelect({ 
	url: '${context}/asset/public/plugins/cxselect/js/city.min.js' ,
	selects: ['province', 'city'], 
	nodata: 'none' 
}); 


$('#savea').click(function(){
		var bankName=$("#bankName").val();
		var branchBank=$("#branchBank").val();
		var cardNo=$("#cardNo").val();
		var phone=$("#phone").val();
		var province=$("#province").val();
		var city=$("#city").val();
		$.ajax({
			url:'${context}/ucenter/bankCardDisplayPost.do',
			type:'post',
			data:{bankName:bankName,branchBank:branchBank,cardNo:cardNo,phone:phone,province:province,city:city},
			success:function(data){
				if(data.code == 0){
					alert('操作成功');
					location.href="${context}/ucenter/bankCardDisplay.html";
				}else{
					alert('操作失败');
				}
			}
		});
	})

</script>

</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />