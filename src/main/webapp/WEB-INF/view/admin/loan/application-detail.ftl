<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/admin/css/processor_bar.css" rel="stylesheet">
<style>
.checkbox, 
.form-horizontal 
.checkbox-inline, 
.form-horizontal .radio, 
.form-horizontal .radio-inline {
     padding-top: 0; 
     margin-top:4px;
 }
.form-group{
	margin:0;
}
.size1of5{width:33.33%}
</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="loan:application" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">借款申请</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">借款申请详情</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<div id="stepItems">
		<ul class="processor_bar grid_line">
			<li data-id="1" class="step grid_item size1of5">
				<h4>1 初审</h4>
			</li>
			<li data-id="2" class="step grid_item size1of5">
				<h4>2 编辑</h4>
			</li>
			<li data-id="3" class="step grid_item size1of5">
				<h4>3 完成</h4>
			</li>
		</ul>
	</div>	
	<#if loanMap.status=="1" >
	
	<form class="form-horizontal"   style="margin-top:20px;"   method="post">
		<input type="hidden" name="applicationId"  id="applicationId" value="<#if loanMap??>${loanMap.id!}</#if>" >
		<input type="hidden" name="uid" value="<#if loanMap??>${loanMap.uid!}</#if>" >
		<table class="table table-bordered">
		<tr>
			<td>
				<div class="form-group">
					<label for="title" class="col-sm-2 control-label">借款标题<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="title" id="title" placeholder="借款标题" value="">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="cycle" class="col-sm-2 control-label">借款期限<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="cycle" id="cycle" placeholder="借款期限" value="<#if loanMap??>${loanMap.cycle!}</#if>">
					</div>
				</div>	
			</td>		 
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">用户名<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="username" id="username" placeholder="用户名" value="<#if loanMap??>${loanMap.username!}</#if>" disabled>
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="realName" class="col-sm-2 control-label">真实姓名<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="realName" id="realName" placeholder="真实姓名" value="<#if loanMap??>${loanMap.realName!}</#if>"  disabled>
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-2 control-label">借款金额<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="amount" id="amount" placeholder="借款金额" value="<#if loanMap??>${loanMap.amount!}</#if>">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="annual_rate" class="col-sm-2 control-label">年化收益(%)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8  ">
					  <input type="text" class="form-control required number" name="annual_rate" id="annual_rate" placeholder="年化收益" value="<#if loanMap??>${loanMap.annual_rate!}</#if>">
					</div>
				</div>		
			</td>
		</tr>
	 		
 
		</table>
 
		  <button type="button" onClick="audit(2)" class="btn btn-primary" data-loading-text="正在保存..." autocomplete="off" style="margin-left: 141px;">审核通过</button>
		  <button type="button" onClick="audit(3)" type="button" class="btn btn-danger" data-loading-text="正在保存..." autocomplete="off" style="margin-left: 141px;">审核不通过</button>
	</form>	
	<#elseif loanMap.status=="2">
	
	<form class="form-horizontal" id="loanform" style="margin-top:20px;" action="${context}/admin/loan/add.do" method="post">
		<input type="hidden" name="applicationId"  id="applicationId" value="<#if loanMap??>${loanMap.id!}</#if>" >
		<input type="hidden" name="uid" value="<#if loanMap??>${loanMap.uid!}</#if>" >
		<table class="table table-bordered">
		<tr>
			<td>
				<div class="form-group">
					<label for="title" class="col-sm-2 control-label">借款标题<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="title" id="title" placeholder="借款标题" value="">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="loan_cate" class="col-sm-2 control-label">借款类型<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <select name="loan_cate" id="loan_cate" class="form-control required" aria-required="true" aria-invalid="false" aria-describedby="loan_cate-error">
						<option value="" selected="selected">--请选择--</option>
						<#list loanType?keys as key>
							<option value="${key}" >${loanType[key]}</option>
						</#list>
						</select>
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">用户名<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="username" id="username" placeholder="用户名" value="<#if loanMap??>${loanMap.username!}</#if>" disabled>
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="realName" class="col-sm-2 control-label">真实姓名<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="realName" id="realName" placeholder="真实姓名" value="<#if loanMap??>${loanMap.realName!}</#if>"  disabled>
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-2 control-label">借款金额<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="amount" id="amount" placeholder="借款金额" value="<#if loanMap??>${loanMap.amount!}</#if>">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="annual_rate" class="col-sm-2 control-label">年化收益(%)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8  ">
					  <input type="text" class="form-control required number" name="annual_rate" id="annual_rate" placeholder="年化收益" value="<#if loanMap??>${loanMap.annual_rate!}</#if>">
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="min_invest_amount" class="col-sm-2 control-label">最低投标金额(元)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="min_invest_amount" id="min_invest_amount" placeholder="值为0表示不限制" value="<#if loanMap??>${loanMap.min_invest_amount!}</#if>">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="max_invest_amount" class="col-sm-2 control-label">最多投标金额(元)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="max_invest_amount" id="max_invest_amount" placeholder="值为0表示不限制" value="<#if loanMap??>${loanMap.max_invest_amount!}</#if>">
					</div>
				</div>		
			</td>
		</tr>		
		<tr>
			<td>
				<div class="form-group">
					<label for="cycle" class="col-sm-2 control-label">借款期限(<span id="cycleT">月</span>)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="cycle" id="cycle" placeholder="借款期限" value="<#if loanMap??>${loanMap.cycle!}</#if>">
					</div>
				</div>	
			</td>
			<td>
			<div class="form-group ">
				<div class="checkbox" style="margin-left:50px;">
				<label>
					<input type="checkbox" name="cycle_type" id="cycle_type" value="2">是否为天标
				</label>
				</div>
			</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="raise_term" class="col-sm-2 control-label">筹标期限<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					<select name="raise_term" id="raise_term" class="form-control required">
						<option value="" selected="selected">--请选择--</option>
						<#list raiseTerm?keys as key>
							<option value="${key}" >${raiseTerm[key]}</option>
						</#list>
					</select>
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="payment_mode" class="col-sm-2 control-label">还款方式<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					<select name="payment_mode" id="payment_mode" class="form-control required" aria-required="true" aria-invalid="false" aria-describedby="payment_mode-error">
						<option value="" selected="selected">--请选择--</option>
						<option value="1">等额本息还款</option>
						<option value="2">按先息后本还款</option>
						<option value="3">到期一次性还本付息</option>
						</select>
					</div>
				</div>	
			</td>
		</tr>
		<tr>
			<td>
			<div class="form-group ">
				<label for="excitation_type" class="col-sm-2 control-label">奖励类型<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					<select name="excitation_type" id="excitation_type" class="form-control required" aria-required="true" aria-invalid="false" aria-describedby="excitation_type-error">
						<option value="1">不设置奖励</option>
						<option value="2">按固定比例金额分摊</option>
						<option value="3">按投标金额比例</option>
					</select>
				</div>
			</div>
			</td>
			<td>
				<div class="form-group">
					<label for="invest_pwd" class="col-sm-2 control-label">投标密码</label>
					<div class="col-sm-8">
					  <input type="text" class="form-control" name="invest_pwd" id="invest_pwd" placeholder="若不加密，可不填" value="<#if loanMap??>${loanMap.invest_pwd!}</#if>">
					</div>
				</div>	
			</td>
		</tr>
		</table>
		<div class="form-group">
		    <label for="detail" class="col-sm-1 control-label">信息内容</label>
		    <div class="col-sm-10">
		      <textarea name="detail" style="height:420px;" id="detail"><#if loanMap??>${loanMap.detail!}</#if></textarea>
		    </div>
		</div>
		  <button id="save" type="submit" class="btn btn-primary" data-loading-text="正在保存..." autocomplete="off" style="margin-left: 141px;">保存</button>
		  <button type="button" onClick="audit(3)" type="button" class="btn btn-danger" data-loading-text="正在保存..." autocomplete="off" style="margin-left: 141px;">审核不通过</button>
	</form>
	<#elseif loanMap.status=="3" ||  loanMap.status=="4" >
	<form class="form-horizontal"  style="margin-top:20px;"   method="post">
		<table class="table table-bordered">
		<tr>
			<td>
				<div class="form-group">
					<label for="title" class="col-sm-2 control-label">借款标题<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="title" id="title" placeholder="借款标题" value="">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="cycle" class="col-sm-2 control-label">借款期限<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="cycle" id="cycle" placeholder="借款期限" value="<#if loanMap??>${loanMap.cycle!}</#if>">
					</div>
				</div>	
			</td>		 
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">用户名<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="username" id="username" placeholder="用户名" value="<#if loanMap??>${loanMap.username!}</#if>" disabled>
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="realName" class="col-sm-2 control-label">真实姓名<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="realName" id="realName" placeholder="真实姓名" value="<#if loanMap??>${loanMap.realName!}</#if>"  disabled>
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-2 control-label">借款金额<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="amount" id="amount" placeholder="借款金额" value="<#if loanMap??>${loanMap.amount!}</#if>">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="annual_rate" class="col-sm-2 control-label">年化收益(%)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8  ">
					  <input type="text" class="form-control required number" name="annual_rate" id="annual_rate" placeholder="年化收益" value="<#if loanMap??>${loanMap.annual_rate!}</#if>">
					</div>
				</div>		
			</td>
		</tr>
	 		
 
		</table>
	 
	</form>	
	</#if>
</div>	
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.min.js"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>

<script type="text/javascript" charset="utf-8" src="${context}/asset/public/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
	$(function(){
		var status  = <#if loanMap.status=="4">3<#else>${loanMap.status}</#if>;
		var prev = status -1;
		var next = status +1;
		$('.step').each(function(index,el){
			var s = parseInt($(this).attr('data-id'));
			if(s == status){
				$(this).addClass('current');
			}
			else if(s == prev){
				$(this).addClass('prev');
			}
			else if(s == next){
				$(this).addClass('next');
			}
			else if(s < prev){
				$(this).addClass('pprev');
			}
			else if(s > next){
				$(this).addClass('nnext');
			}
		});
	});
$(function(){
	FormValidation.validation('#loanform',function(resp){
		alert(resp.message);
	});
});
 var ue = UE.getEditor('detail',{
		serverUrl:"${context}/ueditor/dispatch.html"
 });
$("#auditNoPass").click(function(){
	$.post("auditNoPass.do",{"applicationId":$("#applicationId").val()},function(resp){
		alert(resp.message);
		window.location.reload()
	});
}); 
function audit(status){
	$.post("audit.do",{"applicationId":$("#applicationId").val(),"status":status},function(resp){
		alert(resp.message);
		window.location.reload()
	});	
}
$("#cycle_type").change(function(){
	if($("#cycle_type").is(":checked")){
		$("#cycleT").html("天");
	}else{
		$("#cycleT").html("月");
	}
});
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />