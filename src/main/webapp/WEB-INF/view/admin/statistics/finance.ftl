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

#memberChart {
	width	: 100%;
	height	: 500px;
}																		

</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="statistics:finance" />
<div class="page-content">
	<div class="page-bar" style="margin-bottom:20px;">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">数据管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">资金统计</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>

	<div class="portlet light">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-bar-chart font-green-haze"></i>
				<span class="caption-subject bold uppercase font-green-haze">
					用户充值提现分析
				</span>
			</div>
			<div class="actions">
				<input type="text" style="width: 200px"  id="searchDateRange" class="form-control" placeholder="点击选择日期" readonly />
			</div>
		</div>
		<div class="portlet-body">
			<div style="height:400px" id="dailyChart"></div>
			<hr/>
			<div style="height:400px"  id="totalChart"></div>
		</div>
	</div>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script src="${context}/asset/public/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<!-- Resources -->
<script src="${context}/asset/public/plugins/amcharts/amcharts/amcharts.js"></script>
<script src="${context}/asset/public/plugins/amcharts/amcharts/serial.js"></script>
<script src="${context}/asset/public/plugins/amcharts/amcharts/plugins/export/export.js"></script>
<link rel="stylesheet" href="${context}/asset/public/plugins/amcharts/amcharts/plugins/export/export.css" type="text/css" media="all" />
<script src="${context}/asset/public/plugins/amcharts/amcharts/themes/light.js"></script>
<!-- Chart code -->
<script>
function renderChart(){
	var vals = $('#searchDateRange').val().split("~");
	var data = {'dateBegin':vals[0].trim(),'dateEnd':vals[1].trim()};
	$.ajax({
		url:'${context}/admin/statistics/finance/data.do',
		data:data,
		success:function(resp){
			AmCharts.makeChart("dailyChart", {
			    "type": "serial",
			    "theme": "light",
			    "marginTop":10,
			    "marginRight": 80,
			    "path":"${context}/asset/public/plugins/amcharts/amcharts/",
			    "pathToImages": "${context}/asset/public/plugins/amcharts/amcharts/images/",
			    "dataProvider": resp.data,
			    "legend": {
			        "equalWidths": false,
			        "useGraphSettings": true,
			        "valueAlign": "left",
			        "valueWidth": 100
			    },
			    "valueAxes": [
			    	{
				        "axisAlpha": 1,
				        //"axisAlpha": 0,
        				"gridAlpha": 0.2,
				        "position": "left",
				        "axisColor": "#FF6600",
				        "stackType": "3d"
			    	}
			    ],
			    "graphs": [
			    {
			    	"title":"充值金额(元)",
			    	"valueAxis":"withdrawAxis",
			        "id":"g2",
			        "balloonText": "充值[[value]] RMB",
			       	"fillAlphas": 0.9,
        			"lineAlpha": 0.2,
			        "type": "column",
			        "valueField": "recharge_amount"
			    },
			    {
			    	"title":"提现金额(元)",
			    	"alphaField": "alpha",
			        "balloonText": "提现 [[value]] RMB",
			        "fillAlphas": 0.9,
        			"lineAlpha": 0.2,
			        "type": "column",
			        "valueField": "withdraw_amount",
			        "valueAxis": "rechargeAxis"
			    }
			    ],
			   
			    "chartCursor": {
			        "categoryBalloonDateFormat": "MM/DD",
			        "cursorAlpha": 0,
			        "valueLineEnabled":true,
			        "valueLineBalloonEnabled":true,
			        "valueLineAlpha":0.5,
			        "fullWidth":true
			    },
			    "plotAreaFillAlphas": 0.1,
			    "depth3D": 90,
			    "angle": 25,
			    "categoryField": "date",
			    "categoryAxis": {
			        "minPeriod": "DD",
			        "dateFormats":[{period:'DD',format:'DD'},{period:'MM',format:'MM月'},{period:'YYYY',format:'YYYY年'}],
			        "parseDates": true,
			        "minorGridAlpha": 0.1,
			        "minorGridEnabled": true
			    },
			    "export": {
			        "enabled": true
			    }
			});
			
			var chart = AmCharts.makeChart("totalChart", {
			    "type": "serial",
			    "theme": "light",
			    "marginTop":10,
			    "path":"${context}/asset/public/plugins/amcharts/amcharts/",
			    "marginRight": 80,
			    "pathToImages": "${context}/asset/public/plugins/amcharts/amcharts/images/",
			    "dataProvider": resp.data,
			    "legend": {
			        "equalWidths": false,
			        "useGraphSettings": true,
			        "valueAlign": "left",
			        "valueWidth": 120
			    },
			    "valueAxes": [
			    	{
				    	"id":"recharge",
				        "axisAlpha": 1,
				        "position": "left",
				        "axisColor": "#FF6600"
			    	},
			    	{
				    	"id":"withdraw",
				        "axisAlpha": 1,
				        "position": "right",
				        "axisColor": "#FF6600"
			    	}
			    ],
			    "graphs": [{
			    	"title":"累计充值(元)",
			    	"valueAxis":"recharge",
			        "id":"g1",
			        "balloonText": "[[category]]<br><b><span style='font-size:14px;'>[[value]]</span></b>元",
			        "fillAlphas": 0.6,
			        "type": "smoothedLine",
			        "bullet": "round",
			        "bulletSize": 8,
			        //"lineColor": "#FF6600",
			        //"lineThickness": 2,
			        "valueField": "total_recharge_amount"
			    },
			    {	"title":"累计提现(元)",
			    	"valueAxis":"withdraw",
			        "id":"g2",
			        "balloonText": "[[category]]<br><b><span style='font-size:14px;'>[[value]]</span></b>元",
			       "fillAlphas": 0.6,
			        "type": "smoothedLine",
			        "bullet": "round",
			        "bulletSize": 8,
			        "valueField": "total_withdraw_amount"
			    }
			    ],
			   
			    "chartCursor": {
			        "categoryBalloonDateFormat": "MM/DD",
			        "cursorAlpha": 0,
			        "valueLineEnabled":true,
			        "valueLineBalloonEnabled":true,
			        "valueLineAlpha":0.5,
			        "fullWidth":true
			    },
			    //"dataDateFormat": "YYYY-MM-DD",
			    "categoryField": "date",
			    "categoryAxis": {
			        "minPeriod": "DD",
			        "dateFormats":[{period:'DD',format:'DD'},{period:'MM',format:'MM月'},{period:'YYYY',format:'YYYY年'}],
			        "parseDates": true,
			        "minorGridAlpha": 0.1,
			        "minorGridEnabled": true
			    },
			    "export": {
			        "enabled": true
			    }
			});
			removeBrand();
		}
	});
}

function removeBrand(){
	$('a[title="JavaScript charts"]').remove();
}

    $(document).ready(function (){
        $('#searchDateRange').daterangepicker({  
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
                    //'今日': [moment().startOf('day'), moment()],  
                    //'昨日': [moment().subtract(1,'days').startOf('day'), moment().subtract(1,'days').endOf('day')], 
					'最近3日': [moment().subtract(2,'days'), moment()],									
                    '最近7日': [moment().subtract(6,'days'), moment()],
                    '最近30日': [moment().subtract(29,'days'), moment()]  
                },  
                opens : 'left', //日期选择框的弹出位置  
                buttonClasses : ['btn btn-default'],  
                applyClass : 'btn-small btn-primary blue',  
                cancelClass : 'btn-small',  
                format : 'YYYY-MM-DD', //控件中from和to 显示的日期格式  
                separator : ' ~ ',  
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
        $('#searchDateRange').on("change",renderChart);
        $('#searchDateRange').val(moment().subtract(30,'days').format('YYYY-MM-DD') + ' ~ ' + moment().format('YYYY-MM-DD')).change();
    })  
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />