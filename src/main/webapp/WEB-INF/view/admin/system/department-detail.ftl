<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="department:detail" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="${context}/admin/department.html">部门管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">部门详情</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<form id="departmentForm" action="${context}/admin/department/update/${department.id}.do" class="form-horizontal" role="form" style="margin-top:20px;">
		<div class="form-body">
			<@token name="DEPARTMENT_DETAIL" />
			<div class="form-group">
				<label class="col-md-3 control-label">部门名称
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<input id="email" value="${department.name!}" type="text" name="name" class="form-control required" placeholder="请输入部门名称">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">部门负责人
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<input id="leader" value="${department.leader!}" type="text" name="leader" class="form-control required" placeholder="请输入部门负责人">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">描述</label>
				<div class="col-md-4">
					<textarea id="desc" name="remark" class="form-control" rows="3">${department.remark!}</textarea>
				</div>
			</div>
		</div>
		<div class="form-actions">
			<div class="row">
				<div class="col-md-offset-3 col-md-9">
					<button type="submit" class="btn green">提交</button>
				</div>
			</div>
		</div>
	</form>
	<br/><br/><br/>
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
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script>
	$(function(){
		FormValidation.validation('#departmentForm',function(resp){
			if(resp.code == 0){
				spark.notify(resp.message);
			}
			else spark.notify(resp.message,'error');
		});
		
	})
	
//部门员工	
	$(document).ready(function() {
		spark.dataTable('#userTable','${context}/admin/department/queryUserBydepartmentId/${department.id}.json',{
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