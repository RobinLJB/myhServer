<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="areaSet:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">系统设置</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">初审设置</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		地区列表
	</h3>
	<form class="form-inline" role="form">
		
		<div class="form-group">
			<label for="control-label">地区名称</label>
			<input type="text" data-toggle="table-search" data-column="1" class="form-control" placeholder="">
		</div>
		
		<div class="form-group">
			<label for="control-label">限制状态</label>
			<select class="form-control"   data-toggle="table-search" data-column="2">
				<option  value="">全部</option>
				<option  value="0">无限制</option>
				<option  value="1">已限制</option>
			</select>
		</div>
	
		<button type="button" class="btn btn-default">查找</button>
	</form>
	
	<table id="memberTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>地区编号</th>
                <th>地区名称</th>
                <th>限制状态</th>
				<th>限制操作</th>
            </tr>
        </thead>
    </table>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script src="${context}/asset/public/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		var table = spark.dataTable('#memberTable','${context}/admin/areaSet/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "province" },
				{ "data": "islimit" }
				
			],
			"columnDefs":[
				
			
				
				
				{
					"targets":[2],
					"data":'islimit',
					"render":function(data,type,full){
						if(data == 0){
							return '<span class="label label-success">没有限制</span>';
						}else if(data == 1){
							return '<span class="label label-danger">已限制</span>';
						}else{
							return '<span class="label label-danger">其他状态</span>';
						}
					}
				},
				{
					"targets":[3],
					"data":'id',
					"render":function(data,type,full){
						return	'<a class="btn btn-xs btn-default" href="${context}/admin/areaSet/'+data+'.html">修改限制</a>';
						
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