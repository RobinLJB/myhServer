<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="cost:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">平台收费信息</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		平台收费管理
	</h3>
 
	<table id="costTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>序号</th>
                <th>标题</th>
                <th>数值</th>
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
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
	var table;
	$(document).ready(function() {
		table = spark.dataTable('#costTable','${context}/admin/platformCost/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "name" },
				{ "data": "value" },
				{ "data": "review" },
			],
			"columnDefs":[
				{
					"targets":[4],
					"render":function(data,type,obj){
						return '<a href="${context}/admin/platformCost/'+obj.id+'.html"  class="update-kv btn btn-primary btn-xs">设置</a>';
					}
				}
			
				
			]
			
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