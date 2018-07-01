<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="activity:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">营销管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="${context}/admin/activity/index.html">活动管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">活动卡券</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<div class="note note-success">
		<h3>${activity.title!}</h3>
		<p></p>
	</div>
	
	<table id="memberTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>编号</th>
                <th>编码</th>
                <th>领取人</th>
				<th>面额</th>
				<th>最小投资</th>
				<th>创建时间</th>
				<th>过期时间</th>
				<th>使用时间</th>
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
		var table = spark.dataTable('#memberTable','${context}/admin/activity/coupon/list/${actId}.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "token" },
				{ "data": "uid" },
				{ "data": "faceValue" },
				{ "data": "minInvestAmt" },
				{ "data": "createTime" },
				{ "data": "expiredTime" },
				{ "data": "usedTime" },
				{ "data": "status" }
			],
			"columnDefs":[
				{
					"targets":[2],
					"data":'uid',
					"render":function(data,type,full){
						if(data > 0){
							return full.username+'<br/>'+full.realName+'/'+full.mobilePhone;
						}
						else return  '无';
					}
				},
				{
					"targets":[8],
					"data":'status',
					"render":function(data,type,full){
						if(data == 0){
							return '<span class="label label-default">未领取</span>';
						}
						else if(data == 1){
							return '<span class="label label-info">未使用</span>';
						}
						else if(data == 2){
							return '<span class="label label-success">已使用</span>';
						}
						else return  '<span class="label label-danger">禁用</span>';
					}
				},
				{
					"targets":[9],
					"data":'id',
					"render":function(data,type,full){
						if(data>0){return '<a class="btn btn-xs btn-danger" data-toggle="ajax-link" data-tip="删除后不能恢复，确定继续吗？" data-href="${context}/admin/activity/coupon/delete/'+data+'.do">删除</a>';}
						else return '';
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#memberTable');
			}
		})
		$('[data-toggle="table-search"]').change(function(){
			$('[data-toggle="table-search"]').each(function(){
				var index = parseInt($(this).attr('data-column'));
				table.columns(index).search($(this).val());
			})
			table.draw();
		});
		
	});
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />