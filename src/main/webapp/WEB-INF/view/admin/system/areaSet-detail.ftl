<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/public/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">
<link href="${context}/asset/admin/css/processor_bar.css" rel="stylesheet">

</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="areaSet:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">地区限制</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">地区限制</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	
	
	
		
		
		<form class="form-horizontal" id="articleform" style="margin-top:20px;">
			<input type="hidden" name="id" value="${result.id!}" >
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-2 control-label">地区名称</label>
				<div class="col-sm-5">
				  <input type="text" class="form-control" name="title" id="inputEmail3" readonly value="${result.province!}">
				</div>
			</div>
			
			
			
			
		
			
			
			<div class="form-group">
		    	<label class="col-sm-2 control-label">限制状态</label>
		     	<div class="col-sm-3">
			        <label class="radio-inline">
						<input type="radio" name="isShow" value="0"	<#if result.islimit=="0">checked</#if> >没有限制
					</label>
					<label class="radio-inline">
						<input type="radio" name="isShow" value="1" <#if result.islimit=="1">checked</#if> />已限制
					</label>
					
				</div>
		    </div>
		
			
		  	<div class="form-group">
		  		<div class="col-sm-10 col-sm-offset-2">
		  			 <button id="save" type="button" class="btn btn-primary" data-loading-text="正在保存..." autocomplete="off">保存</button>
		  		</div>
		  	</div>
	</form>
		
		
		
		
	
	
	
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>

	
	
	
	
	$('#save').click(function(){
		var isShow=$('input[name=isShow]:checked').val();
		$.ajax({
			url:'${context}/admin/areaSet/update/${result.id}.do',
			type:'post',
			data:{status:isShow},
			success:function(data){
				if(data.code == 0){
					spark.notify('操作成功','success');
					location.href="${context}/admin/areaSet.html";
				}else{
					spark.notify('操作失败','error');
				}
				setTimeout(function(){
					$btn.button('reset');
				},3000);
			}
		});
	});
	
	
</script>
	
</@override>
<@layout name="/admin/layout/main.ftl" />










