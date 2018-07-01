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
<input type="hidden" id="MENU_ACTIVE_ID" value="attach:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">内容管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">附件管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		附件列表
	</h3>
	<a href="${context}/admin/cms/attach/-1.html" class="btn btn-success">上传附件</a>
	<table id="imageTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>序号</th>
                <th>标题</th>
                <th>文件名称</th>
                <th>上传时间</th>
				<th>操作</th>
            </tr>
        </thead>
    </table>
</div>
<div id="zclipWrapper"></div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-zclip/jquery.zclip.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		var index = 0;
		var t = spark.dataTable('#imageTable','${context}/admin/cms/attach/list.json',{
			"columnDefs":[
				{
					"searchable": false,
					"orderable": false,
					"targets": [0],
					"render":function(data,type,full){
					   return ++index;
					}
				},
				{
		            "targets": [1],
		            "data":'title'
		        },
		        {
					"targets":[2],
					"data":'filename',
					render:function(data,type,full){
						return '<a href="${SITE_URL}'+full.path+'" target="_blank">'+full.filename+'</a>'
					}
				},
				{
					"targets":[3],
					"data":'publishTime'
				},
				{
					"targets":[4],
					"data":'id',
					"render":function(data,type,full){
						return '<p style="position:relative;"><a class="btn btn-xs btn-danger" data-toggle="ajax-link" data-tip="删除后不能恢复，确定继续吗？" data-href="${context}/admin/cms/attach/delete/'+data+'.do">删除</a><a class="btn-copy btn btn-xs btn-default" data="${SITE_URL!}'+full.path+'">复制地址</a></p>';
					}
				}
			],
			"order": [[1, 'asc']],
			drawCallback:function(){
				spark.handleToggle('#imageTable');
				$('.btn-copy').zclip({
					path: "${context}/asset/public/plugins/jquery-zclip/ZeroClipboard.swf",
					copy:function(){
						return $(this).attr('data');
					},
					afterCopy:function(){
						spark.notify('复制成功');
					}
				});
			},
			"deferRender": true
		});
	} );
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />