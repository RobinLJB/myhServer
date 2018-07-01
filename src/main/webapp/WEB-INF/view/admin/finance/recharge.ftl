<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/public/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" />
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="finance:recharge" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">财务管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">充值管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		用户充值
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="control-label">用户名</label>
			<input type="text" data-toggle="table-search" data-column="1" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<label for="control-label">真实姓名</label>
			<input type="text" data-toggle="table-search" data-column="2" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<label for="control-label">手机号</label>
			<input type="text" data-toggle="table-search" data-column="3" class="form-control" placeholder="">
		</div>
		 
		<div class="form-group">
			<div class="controls">  
                <div id="reportrange" class="pull-left dateRange" style="width:200px">                       
					<input type="text" data-toggle="table-search"   id="searchDateRange" data-column="7" class="form-control" placeholder="点击选择日期" style="width: 100%;" readonly>  
                </div>  
            </div>  
		</div>
		<button type="button" id='search' class="btn btn-default">查找</button>
	</form>
	<table id="roleTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>充值编号</th>
                <th>用户名</th>
                <th>真实姓名</th>
				<th>手机号</th>
				<th>充值金额</th>
				<th>充值状态</th>
				<th>记录时间</th>
				<th>备注</th>
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
		var table = spark.dataTable('#roleTable','${context}/admin/finance/recharge/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "username" },
				{ "data": "real_name" },
				{ "data": "mobilePhone" },
				{ "data": "amount" },
				{"data":"status"},
				{ "data": "add_time" },
				{ "data": "remark" }
			],
			"columnDefs":[
				{
					"targets":[5],
					"render":function(data,type,full){
						 switch(data){
							case "-1": return "<span class='label label-danger'>充值失败</span>";break;
							case "1": return "<span class='label label-warning'>处理中</span>";break;
							case "2": return "<span class='label label-success'>充值成功</span>";break;
							case "0": return "<span class='label label-default'>已取消</span>";break;
						 }
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#roleTable');
			}
		})
		$('#search').click(function(){
			$('[data-toggle="table-search"]').each(function(){
				var index = parseInt($(this).attr('data-column'));
				table.columns(index).search($(this).val());
			})
			table.draw();
		})
	});
 
</script>
<script type="text/javascript">  
    $(document).ready(function (){  
$('#reportrange').daterangepicker(  
        {  
            // startDate: moment().startOf('day'),  
            //endDate: moment(),  
            //minDate: '01/01/2012',    //最小时间  
            maxDate : moment(), //最大时间   
            dateLimit : {  
                days : 90  
            }, //起止时间的最大间隔  
            showDropdowns : true,  
            showWeekNumbers : false, //是否显示第几周  
            timePicker : false, //是否显示小时和分钟  
            timePickerIncrement : 1, //时间的增量，单位为分钟  
            timePicker12Hour : false, //是否使用12小时制来显示时间  
            ranges : {  
                //'最近1小时': [moment().subtract('hours',1), moment()],
                '今日': [moment().startOf('day'), moment()],  
                '昨日': [moment().subtract(1,'days').startOf('day'), moment().subtract(1,'days').endOf('day')], 
				'最近3日': [moment().subtract(2,'days'), moment()],									
                '最近7日': [moment().subtract(6,'days'), moment()],
                '最近30日': [moment().subtract(29,'days'), moment()]  
            },  
            opens : 'right', //日期选择框的弹出位置  
            buttonClasses : [ 'btn btn-default' ],  
            applyClass : 'btn-small btn-primary blue',  
            cancelClass : 'btn-small',  
            format : 'YYYY-MM-DD', //控件中from和to 显示的日期格式  
            separator : ' to ',  
            locale : {  
                applyLabel : '确定',  
                cancelLabel : '取消',  
                fromLabel : '起始时间',  
                toLabel : '结束时间',  
                customRangeLabel : '自定义',  
                daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],  
                monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',  
                        '七月', '八月', '九月', '十月', '十一月', '十二月' ],  
                firstDay : 1  
            }  
        }, function(start, end, label) {//格式化日期显示框  
              
            $('#searchDateRange').val(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'));  
       });  
    })  
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />