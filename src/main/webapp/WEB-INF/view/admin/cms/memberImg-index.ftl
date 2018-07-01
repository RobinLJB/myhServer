<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="${context}/asset/public/plugins/imgzoom/bootstrap-grid.min.css">
<link rel="stylesheet" href="${context}/asset/public/plugins/imgzoom/zoomify.min.css">
<link rel="stylesheet" href="${context}/asset/public/plugins/imgzoom/style.css">
<style>
 .bordered.dataTable tbody th, 
 table.table-bordered.dataTable tbody td {
  vertical-align:middle;
}
</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="memberImg:index" />
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
		会员图片列表
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<select class="form-control"  data-toggle="table-search" data-column="4" >
				<option  value="">全部</option>
				<@select group="CATE_IMAGE"></@select>
			</select>
		</div>
	</form>
	<table id="imageTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>会员编号</th>
                <th>联系方式</th>
                <th>会员状态</th>
                <th>移动端头像</th>
                <th>身份证照片</th>
                <th>手持身份证</th>
            </tr>
        </thead>
    </table>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/imgzoom/zoomify.min.js"></script>
<script>
	$(document).ready(function() {
		var table = spark.dataTable('#imageTable','${context}/admin/cms/memberImg/list.json',{
			"columns":[
				{"data":"id"},
				{"data":"mobilePhone"},
				{"data":"member_status"},
				{"data":"memberImgPath"},
				{"data":"card_imgurl"},
				{"data":"people_imgurl"}
			],
			"columnDefs":[
			{
					"targets":[2],
					"data":'member_status',
					"render":function(data,type,full){
						if(data == 1){
							 return  '<span class="label label-success">正常用户</span>';
						}else if(data == 2) return  '<span class="label label-info">争议用户</span>';
						else if(data == 3) return  '<span class="label label-danger">黑名单</span>';
						else return '<span class="label label-danger">待审用户</span>';
					}
				},
		        {
		            "targets": [3],
		            "data":'memberImgPath',
		            "render":function(data,type,full){
		                return '<a href="'+data+'" target="_blank"><img style="width:100px;" src="'+data+'" ></a>';
		            }  
		        },
				{
		            "targets": [4],
		            "data":'card_imgurl',
		            "render":function(data,type,full){
		                return '<a href="'+data+'" target="_blank"><img style="width:100px;" src="'+data+'" ></a>';
		            }  
		        },
		        {
		            "targets": [5],
		            "data":'people_imgurl',
		            "render":function(data,type,full){
		                return '<a href="'+data+'" target="_blank"><img style="width:100px;" src="'+data+'" ></a>';
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