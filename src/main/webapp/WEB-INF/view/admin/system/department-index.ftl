<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="department:list" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">部门管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		部门列表
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="exampleInputEmail2">部门名称</label>
			<input type="email" class="form-control" id="exampleInputEmail2" placeholder="">
		</div>
		<button type="button" class="btn btn-default">查找</button>
		<a href="department/-1.html" class="btn btn-success">添加部门</a>
	</form>
	<table id="departmentTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>编号</th>
                <th>部门名称</th>
				<th>部门负责人</th>
                <th>备注</th>
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
		spark.dataTable('#departmentTable','${context}/admin/department/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "name" },
				{ "data": "leader" },
				{ "data": "remark" }
			],
			"columnDefs":[
				{
					"targets":[4],
					"data":'id',
					"render":function(data,type,full){
						if(data>0){return '<a class="btn btn-xs btn-danger" data-toggle="ajax-link" data-tip="删除后不能恢复，确定继续吗？" data-href="${context}/admin/department/delete/'+data+'.do">删除</a><a class="btn btn-xs btn-default" href="${context}/admin/department/'+data+'.html">编辑</a>';}
						else return '';
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#departmentTable');
			}
		})
	} );
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />