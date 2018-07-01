<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="${context}/asset/public/plugins/jquery-treeview/css/jquery.treeview.css" /> 
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="article:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">内容管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">文章管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		文章列表
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="title">标题</label>
			<input type="text" class="form-control" id="title" data-toggle="table-search" data-column="1" placeholder="标题">
		</div>
		<div class="form-group">
		   <label for="type">分类</label>
		   <select class="form-control" data-toggle="table-search" data-column="2">
		   		<option value="">--请选择--</option>
				<@select group="CATE_ARTICLE" />
			</select>
		</div>
		<button type="button" id="search" class="btn btn-default">查找</button>
		<a href="${context}/admin/cms/article/-1.html" class="btn btn-success">添加文章</a>
	</form>
	<table id="articleTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>编号</th>
                <th>标题</th>
                <th>分类</th>
				<th>发表时间</th>
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
		var table = spark.dataTable('#articleTable','${context}/admin/cms/article/list.json',{
		 	"columns": [
				{ "data": "id" },
				{"data":"title"},
				{"data":"cate_id"},
				{"data":"publishTime"}
			],
			"columnDefs":[
				{
					"targets":[2],
					"data":'cate_id',
					"render":function(data,type,full){
						var cates= <@select group="CATE_ARTICLE" type="json" />;
						return cates[full.cate_id] ? cates[full.cate_id] : '其它';
					}
				},
				{
					"targets":[4],
					"data":'id',
					"render":function(data,type,full){
						 return '<a class="btn btn-xs btn-danger" data-toggle="ajax-link" data-tip="删除后不能恢复，确定继续吗？" data-href="${context}/admin/cms/article/delete/'+data+'.do">删除</a><a class="btn btn-xs btn-default" href="${context}/admin/cms/article/'+data+'.html">编辑</a>';
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