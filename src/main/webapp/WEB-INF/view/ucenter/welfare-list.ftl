<@override name="head">
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
	 
    <div class="r_main">
    	<div class="ci-title">
            <div class="ci-title-inner">
                <h2>募捐列表</h2>
                <b class="line"></b>
            </div>
        </div>
		<form class="form-inline">
		<div style="height:30px"></div>
		<div class="form-group">
			<div class="controls">  
                <div id="reportrange" class="pull-left dateRange" style="width:200px">                       
					<input type="text" data-toggle="table-search"  name="investTime"  id="searchDateRange" data-column="2" class="form-control" placeholder="点击选择日期" style="width: 100%;" readonly>  
                </div>  
            </div>  
		</div>
        <a href="javascript:void(0)" class="btn btn-default btn-sm" id="bt_searchaa">查询</a>
		</form>
   		
		<div class="box" >
        <div class="boxmain2">
         <div class="biaoge">
		    <table id="table" class="table table-bordered">
			<thead>
				<tr>
					<th>项目名称</th>
					<th>募捐金额</th>
					<th>时间</th>
					<th>状态</th>
				</tr>
			<thead>	
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
<script>
	$(document).ready(function() {
		var index = 0;
		var table = spark.dataTable('#table','${context}/ucenter/welfare/list.json',{
		 	"columns": [
				{"data":"title" },
				{"data":"amount"},
				{"data":"investTime"},
				{"data":"status"}
			],
			"columnDefs":[
				{
					"targets":[0],
					"render":function(data,type,full){
						return "<a target='_blank' href='${context}/welfare/"+full.loan_id+".html'> "+data+"</a>";	
					}
				},
				{
					"targets":[3],
					"render":function(data,type,full){
						if(data=='2'){
							return "<span class='text-default'>募集中</span>";
						}else if(data=='3'){
							return "<span class='text-default'>复审中</span>";
						}else if(data=='4'){
							return "<span class='text-success'>已完成</span>";
						}
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#table');
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
	<script type="text/javascript">  
$(document).ready(function() {
  		$('.myloanmj').addClass('active').parents().show();
  		$('.submeun-15 a').addClass('active').parents().show();
  		
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