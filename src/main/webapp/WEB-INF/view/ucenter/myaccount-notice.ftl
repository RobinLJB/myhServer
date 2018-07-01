<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<style>
.table tbody .odd{height: 38px;}
 .table thead th{height: initial;}
.dataTables_length label{
	font-size:8px;
}
</style>
</@override>
<@override name="body">
<div class="r_main">
	<div class="ci-title">
            <div class="ci-title-inner">
                <h2>全部消息</h2>
                <b class="line"></b>
            </div>
        </div>
	<div class="box">
		<div class="boxmain2">
			<table id="table" class="table table-bordered">
				<thead>
					<tr>
						<th>编号</th>
						<th>内容</th>
						<th>时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</@override>
<@override name="script">
<script>
	$(document).ready(function() {
		$('.myaccount').addClass('active').parents().show();
		$('.submeun-7 a').addClass('active').parents().show();
	})
</script>
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script> 
<script src="${context}/asset/public/plugins/dataTable.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		var table = dataTable('#table','${context}/ucenter/notice/list.json',{
		 	"columns": [
				{"data":null },
				{"data":"site_content" },
				{"data":"add_time"}
			],
			drawCallback:function(){
				var api = this.api();
			　　var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
			　　api.column(0).nodes().each(function(cell, i) {
			　　　　cell.innerHTML = startIndex + i + 1;
			　　}); 
			},
		});
	} );
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />