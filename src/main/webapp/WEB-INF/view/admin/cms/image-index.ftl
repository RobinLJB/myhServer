<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<style>
 .bordered.dataTable tbody th, 
 table.table-bordered.dataTable tbody td {
  vertical-align:middle;
}
</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="image:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">内容管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">图片管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		图片列表
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<select class="form-control"  data-toggle="table-search" data-column="4" >
				<option  value="">全部</option>
				<@select group="CATE_IMAGE"></@select>
			</select>
		</div>
		<a href="${context}/admin/cms/image/-1.html" class="btn btn-success">添加图片</a>
	</form>
	<table id="imageTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>序号</th>
                <th>标题</th>
                <th>图片</th>
                <th>外部链接</th>
                <th>分类</th>
                <th>上传时间</th>
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
		var table = spark.dataTable('#imageTable','${context}/admin/cms/image/list.json',{
			"columns":[
				{"data":"id"},
				{"data":"title"},
				{"data":"path"},
				{"data":"link"},
				{"data":"cate"},
				{"data":"publishTime"}
			],
			"columnDefs":[
		        {
		            "targets": [2],
		            "data":'path',
		            "render":function(data,type,full){
		                return '<a href="'+data+'" target="_blank"><img style="width:100px;" src="'+data+'" ></a>';
		            }  
		        },
				{
					"targets":[4],
					"data":'cate',
					"render":function(data,type,full){
						var cates= <@select group="CATE_IMAGE" type="json" />;
						return cates[full.cate] ? cates[full.cate] : '其它';
					}
				},
				{
					"targets":[6],
					"data":'id',
					"render":function(data,type,full){
						return '<a class="btn btn-xs btn-danger" data-toggle="ajax-link" data-tip="删除后不能恢复，确定继续吗？" data-href="${context}/admin/cms/image/delete/'+data+'.do">删除</a><a class="btn btn-xs btn-default" href="${context}/admin/cms/image/'+data+'.html">编辑</a>';
					}
				}
				
			],
			"order": [[1, 'asc']],
			drawCallback:function(){
				spark.handleToggle('#imageTable');
			},
			"deferRender": true
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