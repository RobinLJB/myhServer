<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="funds:commision" />
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
	<div class="alert alert-info" style="margin-top:20px;">
		<p>平台累计发放佣金：${fee!0}元</p>
	</div>
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
                <th>佣金编号</th>
                <th>佣金金额</th>
                <th>发放时间</th>
				<th>发放地点</th>
				<th>拥有者</th>
				<th>贡献者</th>
				<th>操作</th>
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
		var table = spark.dataTable('#memberTable','${context}/admin/funds/commision/list.json',{
			"columns": [
			
				{ "data": "id" },
				{ "data": "amount" },
				{ "data": "occurTime" },
				{ "data": "occurIp" },
				{ "data": "tmobilePhone" },
				{ "data": "ireal_name" },	
		
				
			],
			"columnDefs":[
				
				
				
				{
					"targets":[6],
					"data":'mid',
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