<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="site:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">站点信息</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<div class="note note-success">
		<h3>系统参数设置</h3>
		<p>设置后会立即生效，请谨慎操作</p>
	</div>
	<div class="tabbable-line">
		<ul class="nav nav-tabs">
			<#list groups as group>
			<li <#if currGroup == group.group_key>class="active"</#if>>
				<a href="${context}/admin/site/${group.group_key}.html">${group.group_name}</a>
			</li>
			</#list>
		</ul>
	</div>
	<table id="siteTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>组名</th>
                <th>标题</th>
                <th>键名</th>
				<th>键值</th>
				<th>备注</th>
				<th>操作</th>
            </tr>
        </thead>
    </table>
</div>
<div class="modal fade" data-backdrop="static" id="updateModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="exampleModalLabel">更新键值</h4>
			</div>
			<form id="updateForm" action="${context}/admin/site/option/update.do" method="post">
				<div class="modal-body">
					<div class="form-group">
						<p class="form-control-static" style="font-weight:bold;" id="keyTitle"></p>
						<input type="hidden" name="keyName" id="keyName"/>
					</div>
					<div class="form-group">
						<input type="text" name="keyValue" class="form-control" id="keyValue"/>
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
		table = spark.dataTable('#siteTable','${context}/admin/site/option/${currGroup}.json',{
			"columns": [
				{ "data": "group_key" },
				{ "data": "title" },
				{ "data": "key" },
				{ "data": "value" },
				{ "data": "remark" }
			],
			"columnDefs":[
				{
					"targets":[5],
					"render":function(data,type,obj){
						return '<a data-title="'+obj.title+'" data-key="'+obj.key+'" data-value="'+obj.value+'" class="update-kv btn btn-primary btn-xs">设置</a>';
					}
				},
				{
					targets:[0],
					render:function(data,type,obj){
						return obj.group_key+"<br/>"+obj.group_name;
					},
					visible:true
				}
			],
			drawCallback:function(){
				$('.update-kv','#siteTable').click(function(){
					var key = $(this).attr('data-key');
					var value = $(this).attr('data-value');
					var title = $(this).attr('data-title');
					$('#keyTitle').text(title);
					$('#keyName').val(key);
					$('#keyValue').val(value);
					$('#updateModal').modal('show');
				});
			}
		})
		
		$('#updateForm').ajaxForm({
			success:function(resp){
				spark.autoNotify(resp,true);
			}
		});
		
		$('#groupKey').change(function(){
			table.columns(0).search($(this).val()).draw();
		})
	} );
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />