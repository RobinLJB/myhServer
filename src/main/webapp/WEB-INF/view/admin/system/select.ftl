<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="site:select" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">分类管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<div class="note note-success">
		<h3>系统分类设置</h3>
		<p>系统自带分类请谨慎删除</p>
	</div>
	<div class="tabbable-line">
		<ul class="nav nav-tabs">
			<#list groups?keys as itemKey>
			<li <#if currGroup == itemKey>class="active"</#if>>
				<a href="${context}/admin/select.html?group=${itemKey}">${groups[itemKey]}</a>
			</li>
			</#list>
		</ul>
	</div>
	<button style="margin-top:10px" type="button" data-toggle="modal" data-target="#addModal" class="btn btn-success"><i class="fa fa-plus"></i>&nbsp;添加</button>
	<table id="siteTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>编号</th>
                <th>名称</th>
				<th>键值</th>
				<th>图片</th>
				<th>排序</th>
				<th>操作</th>
            </tr>
        </thead>
    </table>
</div>
<div class="modal fade" data-backdrop="static" id="addModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">添加选项</h4>
			</div>
			<form id="addForm" action="${context}/admin/select/option/add.do" method="post">
				<div class="modal-body">
					<input type="hidden" name="group" value="${currGroup}" />
					<div class="form-group">
						<label class="control-label">名称</label>
						<input type="text" name="name" class="form-control"/>
					</div>
					<div class="form-group">
						<label class="control-label">键值</label>
						<input type="text" name="key" class="form-control" />
					</div>
					<div class="form-group">
						<label class="control-label">排序</label>
						<input type="text" name="sort" value="255" class="form-control" />
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
			</form>
		</div>
	</div>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
	var table;
	$(document).ready(function() {
		table = spark.dataTable('#siteTable','${context}/admin/select/option/${currGroup}.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "name" },
				{ "data": "key" },
				{ "data": "img" },
				{ "data": "sort" }
			],
			"columnDefs":[
				{
					"targets":[5],
					"render":function(data,type,obj){
						return '<a data-toggle="ajax-link" data-tip="确定要删除吗？" data-href="${context}/admin/select/option/delete.do?id='+obj.id+ '" class="btn btn-danger btn-xs">删除</a>';
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#siteTable');
			}
		})
		
		$('#addForm').ajaxForm({
			success:function(resp){
				spark.autoNotify(resp,true);
			}
		});
	} );
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />