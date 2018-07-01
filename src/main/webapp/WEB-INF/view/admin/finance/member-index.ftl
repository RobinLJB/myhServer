<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="funds:member" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">财务管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">资金管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		用户资金
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
	<table id="memberTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>用户编号</th>
                <th>真实姓名</th>
                <th>手机号</th>
				<th>佣金总额</th>
				<th>冻结佣金</th>
				<th>用户状态</th>
				<th>佣金操作</th>
				
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
		var table = spark.dataTable('#memberTable','${context}/admin/funds/member/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "real_name" },
				{ "data": "mobilePhone" },
				{ "data": "commisionSum" },
				{ "data": "freezeCommision" },
				{ "data": "member_status" },		
				
			],
			"columnDefs":[
				
				
				{
					"targets":[5],
					"data":'member_status',
					"render":function(data,type,full){
						if(data == 1){
							return '<span class="label label-success">正常用户</span>';
						}else if(data == 2){
							return '<span class="label label-success">争议客户</span>';
						}else if(data == 3){
							return '<span class="label label-success">黑名单</span>';
						}else if(data == 0){
							return '<span class="label label-success">待审核</span>';
						}
					}
				},
				{
					"targets":[6],
					"data":'id',
					"render":function(data,type,full){
						return	'<a class="btn btn-xs btn-default" href="${context}/admin/funds/member/detail/'+data+'.html">佣金操作</a>';
						
						
					}
				}
				
				
				
			],
			drawCallback:function(){
				spark.handleToggle('#memberTable');
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