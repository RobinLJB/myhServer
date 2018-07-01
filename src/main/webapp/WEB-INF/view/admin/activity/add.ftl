<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/public/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="article:detail" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="${context}/admin/activity.html">活动管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">文章管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<div class="note note-success">
		<h3>添加活动</h3>
	</div>
	<form class="form-horizontal" action="${context}/admin/activity/add.do" method="post" style="margin-top:20px;">
		<div class="form-group">
			<label for="title" class="col-sm-2 control-label">活动标题</label>
			<div class="col-sm-5">
			  <input type="text" class="form-control" name="title" placeholder="活动标题" value="">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-2 control-label">活动类型</label>
			<div class="col-sm-3">
			 	<select class="form-control" name="type">
			 		<option value="1">红包</option>
			 	</select>
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-2 control-label">卡券面额</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="faceValue" placeholder="" value="">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-2 control-label">最小投资金额</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="minInvestAmt" placeholder="" value="">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-2 control-label">卡券有效时间</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="usablePeriod" placeholder="" value="">
				<span class="help-block">自领取后的有效天数</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">编码前缀</label>
			<div class="col-sm-3">
				<input type="text" name="codePrefix" class="form-control" value="">
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-sm-2 control-label">发放类型</label>
			<div class="col-sm-3">
			 	<select class="form-control" name="mode">
			 		<option value="1">注册时发放</option>
			 		<option value="10">手动发放</option>
			 	</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">失效日期</label>
			<div class="col-sm-3">
				<input type="text" data-minview="0" data-format="yyyy-mm-dd hh:ii:ss" data-toggle='datetime-picker' name="expiredTime" class="form-control" value="">
			</div>
		</div>
		<div class="form-group">
	    	<label class="col-sm-2 control-label">状态</label>
	     	<div class="col-sm-3">
		     	<label class="radio-inline">
					<input type="radio" name="status" value="1" checked />可用
				</label>
				<label class="radio-inline">
					<input type="radio" name="status" value="0"> 不可用
				</label>
			</div>
	    </div>
	  	<div class="form-group">
	  		<div class="col-sm-10 col-sm-offset-2">
	  			<button id="save" type="submit"  class="btn btn-primary" data-loading-text="正在保存..." autocomplete="off">保存</button>
	  		</div>
	  	</div>
	</form>
</div>	
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${context}/asset/public/plugins/jquery.form.js"></script>
<script>
	$(function(){
		spark.handleToggle('form');
		$('form').ajaxForm({
			success:function(resp){
				spark.autoNotify(resp,false);
				location.href = "${context}/admin/activity/index.html"
			}
		});
	})
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />