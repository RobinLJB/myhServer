<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/public/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" />
<style>
.span-font{color: red;
    font-weight: bold;
    font-size: 18px;}
.page-content{
}
th, td { white-space: nowrap; }
</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="statistics:index" />
<div class="page-content">
	<div class="page-bar" style="margin-bottom:20px;">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">数据管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>

	<form class="form-inline" role="form">
		<div class="form-group">
			<div class="controls">  
                <div id="reportrange" class="pull-left dateRange" style="width:200px">                       
					<input type="text" data-toggle="table-search"   id="searchDateRange" data-column="14" class="form-control" placeholder="点击选择日期" style="width: 100%;" readonly>  
                </div>  
            </div>  
		</div>
		<button type="button" id='search' class="btn btn-default">查找</button>
	</form>
	<table id="roleTable" class="table table-striped table-bordered" >
        <thead>
        	<tr>
				<th style="text-align: center;" colspan="7">新增数据</th>
				<th style="text-align: center;" colspan="7">累计数据</th>
				<th style="text-align: center;" colspan="2">日期</th>
            </tr>
            <tr>
				<th>注册(人数)</th>
				<th>充值(元)</th>
				<th>提现(元)</th>
				<th>投资(元)</th>
				<th>还款(元)</th>
				<th>借款(元)</th>
				<th>收益(元)</th>
				<th>注册(人数)</th>
				<th>充值(元)</th>
				<th>提现(元)</th>
				<th>投资(元)</th>
				<th>还款(元)</th>
				<th>借款(元)</th>
				<th>收益(元)</th>
				<th>日期</th>
				<th>统计时间</th>
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
		var table = spark.dataTable('#roleTable','${context}/admin/statistics/list.json',{
			"columns": [
				{"data": "member_register"},
				{"data": "recharge_amount"},
				{"data": "withdraw_amount"},
				{"data":"invest_amount"},
				{"data":"repayment_amount"},
				{ "data": "borrow_amount" },
				{ "data": "repayment_interest_amount" },
				{ "data": "member_total" },
				{"data": "total_recharge_amount"},
				{"data": "total_withdraw_amount"},
				{ "data": "total_invest_amount" },
				{ "data": "total_repayment_amount" },
				{ "data": "total_borrow_amount" },
				{ "data": "total_interest_amount" },
				{"data":"date"},
				{"data":"add_time"}
			],
			"columnDefs":[
			],
			drawCallback:function(){
				spark.handleToggle('#roleTable');
			},
			onLoad:function(data){
				var row = data.dataTable.addition;
			},
			scrollX:'300',
			stateSave:true
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
                  
                $('#searchDateRange').val(start.format('YYYY-MM-DD') + ' ~ ' + end.format('YYYY-MM-DD')).change();  
           });  


    })  
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />