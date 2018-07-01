<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="${context}/asset/public/plugins/jquery-treeview/css/jquery.treeview.css" /> 
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="loan:application" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">借款管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">借款申请列表</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		借款申请列表
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="title">申请人</label>
			<input type="text" class="form-control" data-toggle="table-search" data-column="1"   placeholder="申请人">
		</div>
		<div class="form-group">
			<label for="title">真实姓名</label>
			<input type="text" class="form-control" data-toggle="table-search"  data-column="2" placeholder="真实姓名">
		</div>			
		<button type="button" id="search" class="btn btn-default">查找</button>
	</form>
	<table id="applicationTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>序号</th>
                <th>借款人</th>
				<th>真实姓名</th>
				<th>手机号</th>
                <th>借款金额</th>
				<th>年利率</th>
				<th>借款期限</th>
				<th>申请时间</th>
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
 
<script>

 

	$(document).ready(function() {
	    var index = 0;
		var table = spark.dataTable('#applicationTable','${context}/admin/loan/application/list.json',{
		  //向服务器传额外的参数
			"columns": [
				{ "data": "id" },
				{ "data": "username" },
				{ "data": "realName" },
				{ "data": "mobilePhone" },
				{ "data": "amount" },
				{ "data": "annual_rate" },
				{ "data": "cycle" },
				{ "data": "add_time" },
				{ "data": "status" },
				{ "data": "id" }
			],
			"columnDefs":[
				{
					"targets":[8],
					"data":'status',
					"render":function(data,type,full){
						switch(data){
						case  "1" : return '<span class="label label-success">待审核</span>';break;
						case  "2" : return '<span class="label label-success">审核通过</span>';break;
						case  "3" : return '<span class="label label-danger">审核未通过</span>';break;
						case  "4" : return '<span class="label label-success">完成</span>';break;
						default:return '<span class="label label-danger">审核未通过</span>';
						}
					}
				},
				{
					"targets":[9],
					"data":'id',
					"render":function(data,type,full){
						return '<a class="btn btn-xs btn-default" href="${context}/admin/loan/application/'+data+'.html">查看</a>';
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#applicationTable');
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