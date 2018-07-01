<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="finance:userFinance" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">财务管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<i class="fa fa-home"></i>
				<a href="${context}/admin/finance.html">资金管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">资金记录</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		用户资金详情
	</h3>
	<form class="form-inline" role="form">
		<!--
		<div class="form-group">
			<label for="exampleInputEmail2">用户名</label>
			<input type="email" class="form-control" id="exampleInputEmail2" placeholder="">
		</div>
		<button type="button" class="btn btn-default">查找</button>
		-->
	</form>
	<table id="roleTable" class="table table-striped table-bordered">
        <thead>
			<tr>
				<th>交易类型</th>
				<th>操作金额</th>
				<th>可用余额</th>
				<th>冻结金额</th>
				<th>待收金额</th>
				<th>待还金额</th>
				<th>类型</th>
				<th>记录时间</th>
				<th>备注</th>
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
		spark.dataTable('#roleTable','${context}/admin/finance/record/list/${id?c}.json',{
			"columns": [
				{ "data": "fundMode" },
				{ "data": "amount" },
				{ "data": "usableSum" },
				{ "data": "freezeSum" },
				{ "data": "dueinSum"},
				{ "data": "dueoutSum" },
				{ "data": "type" },
				{ "data": "add_time" },
				{ "data": "remark" }
			],
			"columnDefs":[
				{
					"targets":[6],
					"render":function(data,type,full){
						 switch(data){
							case "1": return "收入";break;
							case "2": return "支出";break;
							case "3": return "冻结";break;
							case "4": return "解冻";break;
						 }
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#roleTable');
			}
		})
	} );
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />