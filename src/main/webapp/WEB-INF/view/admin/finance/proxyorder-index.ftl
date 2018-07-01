<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="finance:fbTransc" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">财务管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">提现管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		用户委托
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="control-label">用户名</label>
			<input type="text" data-toggle="table-search" data-column="1" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<label for="control-label">真实姓名</label>
			<input type="text" data-toggle="table-search" data-column="2" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<label for="control-label">手机号</label>
			<input type="text" data-toggle="table-search" data-column="3" class="form-control" placeholder="">
		</div>
		<button type="button" class="btn btn-default">查找</button>
	</form>
	<table id="roleTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>委托编号</th>
                <th>用户名</th>
                <th>真实姓名</th>
				<th>手机号</th>
				<th>交易类型</th>
				<th>委托价格</th>
				<th>委托数量</th>
				<th>交易数据</th>
				<th>委托时间</th>
				<th>状态</th>
				<th>操作</th>
            </tr>
        </thead>
    </table>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		var table = spark.dataTable('#roleTable','${context}/admin/finance/proxyorder/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "username" },
				{ "data": "realName" },
				{ "data": "mobilePhone" },
				{ "data": "type" },
				{ "data": "price" },
				{ "data": "totalAmount" },
				{ "data": "dealAmount" },
				{ "data": "add_time" }
			],
			"columnDefs":[
				{
					"targets":[4],
					"render":function(data,type,full){
						if(full.type == 1){
							return '<span class="label label-danger">买入</span>';
						}
						else if(full.type == 2){
							return '<span class="label label-success">卖出</span>';
						}
						else return '';
					}
				},
				{
					"targets":[7],
					"render":function(data,type,full){
						var html = '';
						if(full.type == 1){
							html = "买入FB："+full.dealAmount+'<br/>花费RMB'+full.usedRMB;
						}
						else if(full.type == 2){
							html = "卖出FB："+full.dealAmount+'<br/>收入RMB'+full.usedRMB;
						}
						return html;
					}
				},
				{
					"targets":[9],
					"render":function(data,type,full){
						if(full.status == 1){
							return '<span class="label label-info">正在交易</span>'
						}
						else if(full.status == 2){
							return '<span class="label label-success">交易成功</span>'
						}
						else if(full.status == 6){
							return '<span class="label label-default">已取消</span>'
						}
						else if(full.status == 5){
							return '<span class="label label-danger">已失败</span>'
						}
						else return '';
					}
				},
				{
					"targets":[10],
					"render":function(data,type,full){
						return "";
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#roleTable');
			}
		})
		$('[data-toggle="table-search"]').change(function(){
			$('[data-toggle="table-search"]').each(function(){
				var index = parseInt($(this).attr('data-column'));
				table.columns(index).search($(this).val());
			})
			table.draw();
		})
	});
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />