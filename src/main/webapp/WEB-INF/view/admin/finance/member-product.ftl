<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="member:product" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">财务管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">会员钻头</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		会员钻头
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
		<button type="button" class="btn btn-default">查找</button>
	</form>
	<table id="roleTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>编号</th>
                <th>用户名</th>
                <th>真实姓名</th>
				<th>手机号</th>
				<th>钻头类型</th>
				<th>添加时间</th>
				<th>已收益/最大收益</th>
				<th>状态</th>
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
		var table = spark.dataTable('#roleTable','${context}/admin/finance/member-product/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "username" },
				{ "data": "realName" },
				{ "data": "mobilePhone" },
				{ "data": "product_name" },
				{ "data": "add_time" },
				{ "data": "max_profit" },
				{ "data": "status" }
			],
			"columnDefs":[
				{
					"targets":[7],
					"render":function(data,type,full){
						if(full.status == 1){
							return '<span class="label label-primary">正在挖矿</span>'
						}
						else if(full.result == 2){
							return '<span class="label label-success">挖矿完成</span>'
						}
						else return '';
					}
				},
				{
					"targets":[6],
					"render":function(data,type,full){
						return full.has_profit+'/'+full.max_profit;
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
	} );
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />