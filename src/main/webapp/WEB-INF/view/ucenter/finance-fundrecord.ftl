<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/public/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" />
<style>
	.table tbody .odd{height: 38px;}
	.form-group .form-control {
    margin-top: 0px;
}
.box .boxmain2 .input-sm{
	width:50px;
}
.dataTables_length label{
	font-size:8px;
}
</style>
</@override>
<@override name="body">
    <div class="nymain">
  <div class="wdzh">
    <div class="r_main">
		<div class="ci-title">
            <div class="ci-title-inner">
                <h2>资金记录</h2>
                <b class="line"></b>
            </div>
        </div>
      <div class="box">
      <div class="boxmain2">
        <div class="tabmain">
        <div class="biaoge">
		<table class="table table-bordered">
			<tr>
			<th>账户总额</th>
			<th>可用余额</th>
			<th>冻结金额</th>
			<th>待收金额</th>
			</tr>
			<tr>
			 
			<td align="center">￥${finance.total!0}</td>
			<td align="center">￥${finance.usableBalance!0}</td>
			<td align="center">￥${finance.freezeBalance!0}</td>
			<td align="center">￥${finance.dueinSum!0}</td> 
			
			</tr>
		</table>
    </div>
    </div>
    </div>
   
      <div class="boxmain2">
        <div class="srh">
		<form class="form-inline">
 
		<div class="form-group">
		 
			<div class="controls" style="">  
 
                <div id="reportrange" class="pull-left dateRange" style="width:200px">                       
					<input type="text" data-toggle="table-search"   id="searchDateRange" data-column="0" class="form-control" placeholder="点击选择日期" style="width: 100%;" readonly>  
                </div>  
            </div>  
		</div>
        <a href="javascript:void(0)" class="btn btn-default btn-sm" id="bt_searchaa">查询</a>
		</form>
        </div>
        <div class="biaoge" id="fundRecord">
		<table id="table" class="table   table-bordered table-hover" style="font-size:12px;">
			<thead>
				<tr>
					<th>时间</th>
					<th>操作类型</th>
					<th>资金变动</th>
					<th>备注</th>
					<th>可用余额</th>
					<th>冻结金额</th>
					<th>待收金额</th>
				</tr>
			</thead>
		</table>
        </div>
      </div>
      </div>
    </div>
  </div>
</div>

</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script> 
<script src="${context}/asset/public/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script src="${context}/asset/public/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="${context}/asset/public/plugins/dataTable.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		
		var table = dataTable('#table','${context}/ucenter/finance/queryFundrecordList.json',{
		 	"columns": [
				{"data":"add_time" },
				{"data":"fundMode"},
				{"data":"amount"},
				{"data":"remark"},
				{"data":"usableSum"},
				{"data":"freezeSum"},
				{"data":"dueinSum"}
			],
			"columnDefs":[
				{
					"targets":[2],
					"render":function(data,type,full){
						if(full.type=="1"){
							return "<span class='text-success'>+ "+data+"</span>";
						}else if(full.type=="2"){
							return "<span class='text-danger'>- "+data+"</span>";
						}else{
							return "<span class='text-default'> "+data+"</span>";
						}
					}
				}
			],
			drawCallback:function(){
				
			},
		});
		$('#bt_searchaa').click(function(){
			$('[data-toggle="table-search"]').each(function(){
				var index = parseInt($(this).attr('data-column'));
				table.columns(index).search($(this).val());
			})
			table.draw();
		});
	} );
</script>
<script>
	$(document).ready(function() {
		$('.mymoney').addClass('active').parents().show();
		$('.submeun-3 a').addClass('active').parents().show();
	})
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
<@layout name="/ucenter/layout/ucenter-base.ftl" />