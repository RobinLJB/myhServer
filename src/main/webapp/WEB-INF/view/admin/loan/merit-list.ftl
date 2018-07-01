<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="${context}/asset/public/plugins/jquery-treeview/css/jquery.treeview.css" /> 
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="merit:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">功德墙</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">功德墙列表</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		功德墙
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="title">用户名</label>
			<input type="text" class="form-control" data-toggle="table-search" data-column="1"   placeholder="用户名">
		</div>
		<div class="form-group">
			<label for="title">真实姓名</label>
			<input type="text" class="form-control" data-toggle="table-search"  data-column="2" placeholder="真实姓名">
		</div>			
		<button type="button" id="search" class="btn btn-default">查找</button>
	</form>
	<table id="loanTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>编号</th>
                <th>用户名</th>
				<th>真实姓名</th>
				<th>手机号</th>
                <th>项目名称</th>
				<th>次数</th>
				<th>获得时间</th>
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
	    var index = 0;
		var table = spark.dataTable('#loanTable','${context}/admin/welfare/merit/list.json',{
		  //向服务器传额外的参数
			"columns": [
				{ "data": "id" },
				{ "data": "username" },
				{ "data": "realName" },
				{ "data": "mobilePhone" },
				{ "data": "title" },
				{ "data": "number" },
				{ "data": "addTime" }
			],
 
			drawCallback:function(){
				spark.handleToggle('#loanTable');
			},
		});
		$('[data-toggle="table-search"]').change(function(){
			$('[data-toggle="table-search"]').each(function(){
				var index = parseInt($(this).attr('data-column'));
				table.columns(index).search($(this).val());
			})
			table.draw();
		});
	});

</script>
</@override>
<@layout name="/admin/layout/main.ftl" />