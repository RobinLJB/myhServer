<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/admin/css/processor_bar.css" rel="stylesheet">
<link href="${context}/asset/ucenter/css/Uploadphotos.css" rel="stylesheet" />
<style>
.checkbox, 
.form-horizontal 
.checkbox-inline, 
.form-horizontal .radio, 
.form-horizontal .radio-inline {
     padding-top: 0; 
     margin-top:4px;
 }
 td{width: 50%}
.form-group{
	margin:0;
}
.size1of5{width:50%}
.oldImg li{
	list-style-type:none;
	float: left;
    width: 120px;
	margin-left:10px;
}
#material>div:nth-child(2)
{
    position: relative !important;
    left: 0!important;
    top: -42px !important;
}
</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="loan:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">借款管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">修改</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<form class="form-horizontal" id="loanform"  style="margin-top:20px;" action="${context}/admin/loan/add.do"  method="post">
		<table class="table table-bordered">
		<tr>
			<td>
				<div class="form-group">
					<label for="username" class="col-sm-3 control-label">用户名<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="username" id="username" placeholder="用户名" value="<#if loanMap??>${loanMap.username!}</#if>" disabled>
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="realName" class="col-sm-3 control-label">真实姓名<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="realName" id="realName" placeholder="真实姓名" value="<#if loanMap??>${loanMap.realName!}</#if>"  disabled>
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="title" class="col-sm-3 control-label">借款标题<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="title" id="title" placeholder="借款标题" value="<#if loanMap??>${loanMap.title!}</#if>">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="cycle" class="col-sm-3 control-label">借款类型<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					 	<select name="productType" id="productType" class="form-control">
					 		<#if loanMap??>
					 		<#if loanMap.loan_cate == "2">
					 			<option value="2" selected="selected">民生标</option>
						    	<option value="10">供链标</option>
						    <#elseif  loanMap.loan_cate == "10">
						    	<option value="2">民生标</option>
						    	<option value="10" selected="selected">供链标</option>
					 		</#if>
					 		</#if>
						</select>
					</div>
				</div>	
			</td>		 
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-3 control-label">借款金额(元)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="amount" id="amount" placeholder="借款金额" value="<#if loanMap??>${loanMap.amount!}</#if>">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="annual_rate" class="col-sm-3 control-label">年化收益(%)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8  ">
						 <input type="text" class="form-control required number" name="annualRate"  id="annualRate"  placeholder="年化收益" value="<#if loanMap??>${loanMap.annual_rate!}</#if>">
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="cycle" class="col-sm-3 control-label">借款期限<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="cycle" id="cycle" placeholder="借款期限" value="<#if loanMap??>${loanMap.cycle!}</#if>">
					</div>
				</div>	
			</td>	
			<td>
				<div class="form-group">
					<label for="cycle" class="col-sm-3 control-label">期限类型<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					 	<select name="cycle_type" id="cycle_type" class="form-control">
					 		<#if loanMap??>
					 		<#if loanMap.cycle_type == "1">
					 			<option value="1" selected="selected">按月</option>
						    	<option value="2">按天</option>
						    <#elseif  loanMap.cycle_type == "2">
						    	<option value="1">按月</option>
						    	<option value="2" selected="selected">按天</option>
					 		</#if>
					 		</#if>
						</select>
					</div>
				</div>	
			</td>		 
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-3 control-label">还款方式<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <select name="paymentMode" id="paymentMode" class="form-control">
						<@select group="CATE_PAYMENT" selected="${loanMap.payment_mode}" />
						
					</select>
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="annual_rate" class="col-sm-3 control-label">筹标期限(天)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8  ">
						 <input type="text" class="form-control required number"  name="raiseTerm" id="raiseTerm" placeholder="年化收益" value="<#if loanMap??>${loanMap.raise_term!}</#if>">
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-3 control-label">最低可投(元)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					 	<input type="text" class="form-control required number" name="minTenderedSum" id="minTenderedSum" placeholder="年化收益" value="<#if loanMap??>${loanMap.min_invest_amount!}</#if>">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="annual_rate" class="col-sm-3 control-label">最多可投(元)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8  ">
						 <input type="text" class="form-control required number" name="maxTenderedSum" id="maxTenderedSum" placeholder="年化收益" value="<#if loanMap??>${loanMap.max_invest_amount!}</#if>">
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td >
				<div class="form-group">
					<label for="overDueRate" class="col-sm-3 control-label">逾期还款利息上浮(%)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					 	<input type="text" class="form-control required number" name="overDueRate" id="overDueRate" placeholder="逾期罚息" value="<#if loanMap?? && loanMap.overDueRate??>${(loanMap.overDueRate?number -1) * 100}<#else>10</#if>">
						<span ></span>
					</div>
				</div>	
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-3 control-label">封面图片<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
						<input type="hidden" id="imgPath" name="imgPath" class="required"  value="<#if loanMap??>${loanMap.cover!}</#if>" />
						<img id="yscover" width="150" height="120" src="<#if loanMap??>${loanMap.cover!}</#if>">
						<div class="demo">
							<div id="cover" ></div>
						</div>
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="annual_rate" class="col-sm-3 control-label">信息披露<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8" id="loanMap_cover">
						<input type="hidden" id="Uploadphotos" name="Uploadphotos" value="<#if loanMap??>${loanMap.img_material!}</#if>">
						<ul id="oldImg" class="oldImg">
						</ul>
						<div class="demo">
							<div id="material"></div>
						</div>
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="form-group">
					<label for="amount" class="col-sm-2 control-label">借款详情<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-9">
					  <textarea name="detail" style="height:420px;width:100%;" id="detail" class="required">
					  	<#if loanMap??>${loanMap.detail!}</#if>
					  </textarea>
					</div>
				</div>	
			</td>
		</tr>
		</table>
		<button   type="submit" class="btn btn-primary btn-lg"   style="margin-left: 141px;">保存</button>
		<input type="hidden" name="id" id="id" value="${loanMap.id!}">
		<input type="hidden" name="uid" id="uid" value="${loanMap.borrower!}">
	</form>	
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
<script type="text/javascript"  src="${context}/asset/ucenter/js/Uploadphotos1.js"></script>
<script type="text/javascript"  src="${context}/asset/ucenter/js/Uploadphotos2.js"></script>
<script type="text/javascript">
//标的封面
$('#cover').diyUpload({
	url:'${context}/upload/image.do?type=1',
	success:function( data ) {
			$("#yscover").css("display","none");
			$("#imgPath").val(data.absPath);
	},error:function( err ) {
		alert(err);
	},
	buttonText : '选择图片',
	chunked:true,
	// 分片大小
	chunkSize:50000 * 1024,
	//最大上传的文件数量
	fileNumLimit:1,
	//总文件大小
	fileSizeLimit:500000 * 1024,
	// /单个文件大小(单位字节)
	fileSingleSizeLimit:50000 * 1024,
	accept: {}
});

//标的信息
$('#material').diyUpload({
	url:'${context}/upload/image.do?type=1',
	success:function( data ) {
		var Uploadphotos = $("#Uploadphotos").val();
		if (Uploadphotos == "") {
			$("#Uploadphotos").val(data.absPath);
		}else{
			Uploadphotos+=","+data.absPath;
			$("#Uploadphotos").val(Uploadphotos);
		}
		$("#loanMap_cover>img").css("display","none");
	},error:function( err ) {
		alert(err);
	},
	buttonText : '选择图片',
	chunked:true,
	// 分片大小
	chunkSize:50000 * 1024,
	//最大上传的文件数量
	fileNumLimit:50,
	//总文件大小
	fileSizeLimit:500000 * 1024,
	// /单个文件大小(单位字节)
	fileSingleSizeLimit:50000 * 1024,
	accept: {}
});
 
function delImg(index){
var num = $("#oldImg_"+index).index();
	var imgArr = $("#Uploadphotos").val().split(",");
	if(imgArr && imgArr.length > 0){
		imgArr.splice(num, 1);
		$("#oldImg_"+index).remove();
	}
	$("#Uploadphotos").val(imgArr.join(","));
}

 var ue = UE.getEditor('detail',{
		serverUrl:"${context}/ueditor/dispatch.html"
 });
</script>

<script type="text/javascript">
	$(function(){
		func();
	})
		function func(){
			var cover = $("#Uploadphotos").val().split(",");
			if (cover != null) {
				for(var i=0;i<cover.length;i++){
					$("#oldImg").append("<li id='oldImg_"+i+"'><a style='display: block;' alt='删除图片' href='JavaScript:;' onclick='delImg("+i+")'><img src="+cover[i]+" style='width:120px;height:100px;margin:5px;'/></a></li>");
				}
			}
		}
 
</script>
<script>
 
$(function(){
	FormValidation.validation('#loanform',function(resp){
		if(resp.code == 0){
			spark.notify('操作成功','success');
			window.location.href="${context}/admin/loan/index.html";
		}else{
			spark.notify(resp.message,'error');
		}
	});
});


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