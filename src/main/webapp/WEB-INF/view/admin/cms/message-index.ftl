<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="message:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">内容管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">信息管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		信息管理
	</h3>
	<table id="messageTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th></th>
                <th>栏目名称</th>
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
		var t = spark.dataTable('#messageTable','${context}/admin/cms/message/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "columName" },
			],
			"columnDefs":[
			   {
	            "searchable": false,
	            "orderable": false,
	            "targets": 0,
	            "render":function(data,type,full){
	               return ++index;
	             }
	            },
				{
					"targets":[2],
					"data":'id',
					"render":function(data,type,full){
						 return '<a class="btn btn-xs btn-danger" data-toggle="ajax-link" data-tip="删除后不能恢复，确定继续吗？" data-href="${context}/admin/cms/message/delete/'+data+'.do">删除</a><a class="btn btn-xs btn-default" href="${context}/admin/cms/message/'+data+'.html">编辑</a>';
					}
				}
			],
			"deferRender": true,
			"sInfoEmpty": "没有数据",
		});
	} );
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />