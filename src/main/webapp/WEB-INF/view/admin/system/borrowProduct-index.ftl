<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="${context}/asset/public/plugins/jquery-treeview/css/jquery.treeview.css" /> 
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="borrow:loan" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">借款设置</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">借款管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		借款产品列表
	</h3>
	<form class="form-inline" role="form">
		
		<a href="${context}/admin/borrowSet/borrow/-1.html" class="btn btn-success">添加借款产品</a>
	</form>
	<table id="articleTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>编号</th>
                <th>借款金额</th>
                <th>借款周期</th>
				<th>信审费率</th>
				<th>服务费率</th>
				<th>手续费率</th>
				<th>逾期费率</th>
				<th>使用状态</th>
				<th>更新时间</th>
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
		var table = spark.dataTable('#articleTable','${context}/admin/borrowSet/borrow/list.json',{
		 	"columns": [
				{ "data": "id" },
				{"data":"amount"},
				{"data":"borrowdays"},
				{"data":"xinfee"},
				{"data":"shoufee"},
				{"data":"servicefee"},
				{"data":"overduefee"},
				{"data":"status"},
				{"data":"updateTime"},
			],
			"columnDefs":[
				{
					"targets":[2],
					"data":'borrowdays',
					"render":function(data,type,full){
						return data+'天';
					}
				},
				{
					"targets":[7],
					"data":'status',
					"render":function(data,type,full){
						if(data == 1){
							return '<span class="label label-success">正在使用</span>';
						}else if(data==2){
							return '<span class="label label-danger">停止使用</span>';
						}else{
							return '<span class="label label-danger">为使用</span>';
						}
					}
				},
				{
					"targets":[9],
					"data":'id',
					"render":function(data,type,full){
						 return '<a class="btn btn-xs btn-default" href="${context}/admin/borrowSet/borrow/'+data+'.html">编辑</a>';
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#articleTable');
			},
		});
		$('[data-toggle="table-search"]').change(function(){
			$('[data-toggle="table-search"]').each(function(){
				var index = parseInt($(this).attr('data-column'));
				table.columns(index).search($(this).val());
			})
			table.draw();
		});
	} );
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />