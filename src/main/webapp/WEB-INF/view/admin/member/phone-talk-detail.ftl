<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="member:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">用户管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">手机通话详单</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		手机通话详单
	</h3>
	
	<table id="memberTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>地址</th>
                <th>时间</th>
                <th>通话时长(秒)</th>
				<th>呼叫类别</th>
				<th>手机号</th>
				
            </tr>
        </thead>
    </table>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script src="${context}/asset/public/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		var table = spark.dataTable('#memberTable','${context}/admin/phoneTalkDetail/list/${uid}.json',{
			"columns": [
				{ "data": "callAddress" },
				{ "data": "callDateTime" },
				{ "data": "callTimeLength" },
				{ "data": "callType" },
				{ "data": "mobileNo" } 
			],
			"columnDefs":[
				{
					"targets":[2],
					"data":'member_status',
					"render":function(data,type,full){
						data = parseInt(data);
						var str = data+"秒";
						if(data >= 60){
							var str = parseInt(data / 60)+"分"+(data%60)+"秒";
						}
						return str;
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
		})
	} );
	
	
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />
