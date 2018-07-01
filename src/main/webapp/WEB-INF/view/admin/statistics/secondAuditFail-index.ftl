<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="statistics:secondAuditFail" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="${context}/admin/borrow/forFristAudit.html">统计管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="${context}/admin/borrow/forFristAudit.html">复审统计</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		复审列表
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="control-label">真实姓名</label>
			<input type="text" data-toggle="table-search" data-column="1" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<label for="control-label">手机号</label>
			<input type="text" data-toggle="table-search" data-column="2" class="form-control" placeholder="">
		</div>
		
		
		<div class="form-group">
			<div class="controls">  
                <div id="reportrange" class="pull-left dateRange" style="width:200px">                       
					<input type="text" data-toggle="table-search"   id="searchDateRange" data-column="5" class="form-control" placeholder="点击选择日期" style="width: 100%;" readonly>  
                </div>  
            </div>  
		</div>
		<button type="button" class="btn btn-default">查找</button>
	</form>
	<table id="memberTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>借款编号</th>
                <th>真实姓名</th>
                <th>联系方式</th>
                <th>申请额度</th>
                <th>申请期限</th>
                <th>会员状态</th>
                <th>申请日期</th>
                <th>借款状态</th>
                <th>初审人员</th>
                <th>初审日期</th>
                <th>复审人员</th>
                <th>复审日期</th>
                <th>操作</th>
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
		var table = spark.dataTable('#memberTable','${context}/admin/secondAuditFail/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "real_name"},
				{ "data": "mobilePhone" },
				{ "data": "benJin" },
				{ "data": "borrowDate" },
				{ "data": "member_status" },
				{ "data": "fristAuditTime" },
				{ "data": "borrowStatus" },
				{ "data": "fristAdminUsername" },
				{ "data": "fristAuditTime" },
				{ "data": "secondAdminUsername" },
				{ "data": "secondAuditTime" },
				
			],
			"columnDefs":[
			{
					"targets":[4],
					"data":'borrowDate',
					"render":function(data,type,full){
						return data+'天';
					}
				},
				
				{
					"targets":[5],
					"data":'member_status',
					"render":function(data,type,full){
						if(data == 1){
							 return  '<span class="label label-success">正常用户</span>';
						}else if(data == 2) return  '<span class="label label-info">争议用户</span>';
						else if(data == 3) return  '<span class="label label-danger">黑名单</span>';
						else return '<span class="label label-info">注册未申请</span>';
					}
				},
				
				
				{
					"targets":[7],
					"data":'borrowStatus',
					"render":function(data,type,full){
						if(data == 1){
							return '<span class="label label-success">提交审核</span>';
						}else if(data == 2){
							return '<span class="label label-success">已认领</span>';
						}else if(data == 3){
							return '<span class="label label-danger">初审失败</span>';
						}else if(data == 4){
							return '<span class="label label-success">初审成功</span>';
						}else if(data == 5){
							return '<span class="label label-success">等待复审</span>';
						}else if(data == 6){
							return '<span class="label label-success">复审成功</span>';
						}else if(data == 7){
							return '<span class="label label-danger">复审失败</span>';
						}else if(data == 8){
							return '<span class="label label-success">还款期间</span>';
						}else if(data == 9){
							return '<span class="label label-danger">已经逾期</span>';
						}else if(data == 10){
							return '<span class="label label-success">还款完成</span>';
						}else if(data == 11){
							return '<span class="label label-success">未还完</span>';
						}else if(data == 12){
							return '<span class="label label-danger">已取消</span>';
						}
						else return  '<span class="label label-danger">其他状态</span>';
					}
				},
				{
					"targets":[8],
					"data":'fristAdminUsername',
					"render":function(data,type,full){
						if(data == "" || data == null){
							return  '自动审核';
						}else{
							return  data;
						}
					}
				},
				{
					"targets":[10],
					"data":'secondAdminUsername',
					"render":function(data,type,full){
						if(data == "" || data == null){
							return  '自动审核';
						}else{
							return  data;
						}
					}
				},
				{
					"targets":[12],
					"data":'id',
					"render":function(data,type,full){
						return	'<a class="btn btn-xs btn-default" href="${context}/admin/borrowAllStatueDetail/'+data+'.html">查看</a>';
						
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
                              
                            $('#searchDateRange').val(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD')).change();  
                       });  


    })  
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />