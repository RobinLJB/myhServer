<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="order:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">财务管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">用户报单</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		用户报单
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="control-label">订单号</label>
			<input type="text" data-toggle="table-search" data-column="0" class="form-control" placeholder="">
		</div>
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
                <th>订单号</th>
                <th>用户名</th>
                <th>真实姓名</th>
				<th>手机号</th>
				<th>钻头型号</th>
				<th>价格(FB)</th>
				<th>交易中心</th>
				<th>下单时间</th>
				<th>状态</th>
            </tr>
        </thead>
    </table>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		var table = spark.dataTable('#roleTable','${context}/admin/order/list.json',{
			"columns": [
				{ "data": "order_sn" },
				{ "data": "username" },
				{ "data": "realName" },
				{ "data": "mobilePhone" },
				{ "data": "product_title" },
				{ "data": "price" },
				{ "data": "ucName" },
				{ "data": "add_time" }
			],
			"columnDefs":[
				{
					"targets":[8],
					"data":'status',
					"render":function(data,type,row){
						if(data == 0){
							return '<span class="label label-success">未完成</span>';
						}
						else return  '<span class="label label-danger">已完成</span>';
					}
				}
			],
			drawCallback:function(){
				//spark.handleToggle('#roleTable');
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