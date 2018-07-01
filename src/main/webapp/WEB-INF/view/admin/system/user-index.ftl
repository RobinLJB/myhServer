<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="admin:list" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">用户管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		管理员列表
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="exampleInputEmail2">用户名</label>
			<input type="email" class="form-control" id="exampleInputEmail2" placeholder="">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail3">部门</label>
			<input type="email" class="form-control" id="exampleInputEmail3" placeholder="">
		</div>
		<button type="button" class="btn btn-default">查找</button>
		<a href="${context}/admin/user/-1.html" class="btn btn-success">添加管理员</a>
	</form>
	<table id="userTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>编号</th>
                <th>用户名</th>
				<th>部门</th>
                <th>角色</th>
				<th>最后登录时间</th>
				<th>最后登录IP</th>
				<th>备注</th>
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
		spark.dataTable('#userTable','${context}/admin/user/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "username" },
				{ "data": "department" },
				{ "data": "roleName" },
				{ "data": "last_login_time" },
				{ "data": "last_login_ip" },
				{ "data": "summary" }
			],
			"columnDefs":[
				{
					"targets":[7],
					"data":'enable',
					"render":function(data,type,full){
						return data == 1?'可用':'禁用';
					}
				},
				{
					"targets":[8],
					"data":'id',
					"render":function(data,type,full){
						if(data ==1)return '';else return '<a class="btn btn-xs btn-danger" data-toggle="ajax-link" data-tip="删除后不能恢复，确定继续吗？" data-href="${context}/admin/user/delete/'+data+'.do">删除</a><a class="btn btn-xs btn-default" href="${context}/admin/user/'+data+'.html">编辑</a>';
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#userTable');
			}
		})
	} );
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />