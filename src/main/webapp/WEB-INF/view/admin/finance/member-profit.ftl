<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="member:profit" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">财务管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">会员挖矿收益</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		会员挖矿收益
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
				<th>本次收益</th>
				<th>收益时间</th>
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
		var table = spark.dataTable('#roleTable','${context}/admin/finance/member-profit/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "username" },
				{ "data": "realName" },
				{ "data": "mobilePhone" },
				{ "data": "product_name" },
				{ "data": "amount" },
				{ "data": "time" }
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