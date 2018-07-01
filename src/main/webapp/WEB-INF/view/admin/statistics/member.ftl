<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="${context}/asset/public/plugins/amcharts/amcharts/plugins/export/export.css" type="text/css" media="all" />
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
<input type="hidden" id="MENU_ACTIVE_ID" value="statistics:member" />
<div class="page-content">
	<div class="page-bar" style="margin-bottom:20px;">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="${context}/admin/statistics/member.html">数据管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="${context}/admin/statistics/member.html">用户统计</a>
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
					用户注册
				</span>
			</div>
			<div class="actions">
				<input type="text" style="width: 200px"  id="searchDateRange" class="form-control" placeholder="点击选择日期" readonly />
			</div>
		</div>
		<div class="portlet-body">
			<div id="memberChart"></div>
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
<script src="${context}/asset/public/plugins/amcharts/amcharts/themes/light.js"></script>
<script>
function renderChart(){
	var vals = $('#searchDateRange').val().split("~");
	var data = {'dateBegin':vals[0].trim(),'dateEnd':vals[1].trim()};
	$.ajax({
		url:'${context}/admin/statistics/member/data.do',
		data:data,
		success:function(resp){
			var chart = AmCharts.makeChart("memberChart", {
			    "type": "serial",
			    "theme": "light",
			    "marginTop":10,
			    "marginRight": 80,
			    "path":"${context}/asset/public/plugins/amcharts/amcharts/",
			    "pathToImages": "${context}/asset/public/plugins/amcharts/amcharts/images/",
			    "dataProvider": resp.data,
			    "legend": {
			        "useGraphSettings": true
			    },
			    "valueAxes": [
			    	{
				    	"id":"total",
				        "axisAlpha": 1,
				        "position": "left",
				        "axisColor": "#FF6600"
			    	},
			    	{
				    	"id":"daily",
				        "axisAlpha": 1,
				        "axisColor": "#1caf9a",
				        "position": "right"
			    	}
			    ],
			    "graphs": [{
			    	"title":"累计注册(人)",
			    	"valueAxis":"total",
			        "id":"g1",
			        "balloonText": "[[category]]<br><b><span style='font-size:14px;'>[[value]]</span></b>人",
			        "bullet": "round",
			        "bulletSize": 8,
			        "lineColor": "#FF6600",
			        "lineThickness": 2,
			        "type": "smoothedLine",
			        "valueField": "member_total"
			    },
			    {
			    	"title":"当日注册(人)",
			    	"valueAxis":"daily",
			        "id":"g2",
			        "balloonText": "[[category]]<br><b><span style='font-size:14px;'>[[value]]</span></b>人",
			        "bullet": "round",
			        "bulletSize": 8,
			        "lineColor": "#1caf9a",
			        "lineThickness": 1,
			        "type": "smoothedLine",
			        "valueField": "member_register"
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

function zoomChart(){
    chart.zoomToIndexes(Math.round(chart.dataProvider.length * 0.4), Math.round(chart.dataProvider.length * 0.55));
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
                $('#searchDateRange').val(start.format('YYYY-MM-DD') + ' ~ ' + end.format('YYYY-MM-DD'));
                $('#searchDateRange').change();
           });
        $('#searchDateRange').on("change",renderChart);
        $('#searchDateRange').val(moment().subtract(30,'days').format('YYYY-MM-DD') + ' ~ ' + moment().format('YYYY-MM-DD')).change();
        //renderChart();
    })  
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />