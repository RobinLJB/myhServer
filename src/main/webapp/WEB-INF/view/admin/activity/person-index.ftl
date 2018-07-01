<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="person:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">营销管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">平台活动</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<form class="form-inline" role="form">
		
		<div class="form-group">
			<label for="control-label">用户姓名</label>
			<input type="text" data-toggle="table-search" data-column="1" class="form-control" placeholder="">
		</div>
		
		<div class="form-group">
			<label for="control-label">联系电话</label>
			<input type="text" data-toggle="table-search" data-column="2" class="form-control" placeholder="">
		</div>
		
	
		<button type="button" class="btn btn-default">查找</button>
	</form>
	<table id="memberTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>用户编号</th>
                <th>用户姓名</th>
				<th>手机号码</th>
				<th>邀请人数</th>
				<th>借款人数</th>
				<th>佣金总额</th>
				<th>邀请码</th>
				<th>创建时间</th>
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
		var table = spark.dataTable('#memberTable','${context}/admin/extension/person/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "real_name" },
				{ "data": "mobilePhone" },
				{ "data": "inviteSum" },
				{ "data": "invateActive" },
				{ "data": "commisionSum" },
				{ "data": "memberNo" },
				{ "data": "create_time" },
			],
			"columnDefs":[
				
				
				{
					"targets":[8],
					"data":'id',
					"render":function(data,type,full){
						return	'<div class="dropdown"><a href="#" class="nodeco" data-toggle="dropdown">操作&nbsp;<span class="caret"></span><ul class="dropdown-menu"><li class="active"><a href="${context}/admin/extension/member/detail/'+data+'.html">提现</a></li><li><a href="${context}/admin/extension/subPersonA/'+data+'.html">下级详情</a></li></ul></a></div>';
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