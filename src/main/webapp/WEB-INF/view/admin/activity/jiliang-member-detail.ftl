<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="activity:index" />
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
	
	<h3 class="page-title">
		计量明细
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="control-label">用户姓名</label>
			<input type="text" data-toggle="table-search" data-column="1" class="form-control" placeholder="">
		</div>
		<label for="control-label">创建时间</label>
		<div class="form-group">
			<div class="controls">  
                <div id="reportrange" class="pull-left dateRange" style="width:200px">                       
					<input type="text" data-toggle="table-search"   id="searchDateRange" data-column="10" class="form-control" placeholder="点击选择日期" style="width: 100%;" readonly>  
                </div>  
            </div>  
		</div>
		<button type="button" class="btn btn-default">查找</button>
		<div class="form-group">
			<label for="control-label">手机号码</label>
			<input type="text" data-toggle="table-search" data-column="2" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<label for="control-label">手机类型</label>
				<select class="form-control"   data-toggle="table-search" data-column="3" placeholder="">
					<option  value="">全部</option>
					<option  value="苹果">苹果</option>
					<option  value="安卓">安卓</option>
				</select>
			 </label>
		</div>
		<div class="form-group">
			<label for="control-label">用户类型</label>
				<select class="form-control"   data-toggle="table-search" data-column="4" placeholder="">
					<option  value="">全部</option>
					<option  value="0">未申请</option>
					<option  value="1">正常用户</option>
					<option  value="2">争议用户</option>
					<option  value="3">黑名单</option>
				</select>
			 </label>
		</div>
		<div class="form-group">
			<label for="control-label">是否实名</label>
				<select class="form-control"   data-toggle="table-search" data-column="5" placeholder="">
					<option  value="">全部</option>
					<option  value="未实名">未实名</option>
					<option  value="已实名">已实名</option>
				</select>
			 </label>
		</div>
	</form>
	<table id="memberTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>邀请编号</th>
                <th>用户姓名</th>
                <th>手机号码</th>
                <th>手机类型</th>
                <th>用户类型</th>
                <th>是否实名</th>
				<th>借款次数</th>
				<th>借款总额</th>
				<th>邀请码</th>
				<th>创建时间</th>
				<th>借款详情</th>
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
		var table = spark.dataTable('#memberTable','${context}/admin/extension/jiliangDetail/list/${keys}.json',{
			"columns": [
				{ "data": "rid" },
				{ "data": "real_name" },
				{ "data" : "mobilePhone"},
				{ "data" : "type"},
				{ "data" : "member_status"},
				{ "data" : "is_real_name"},
				{ "data": "successBorrowTimes" },
				{ "data": "alreadyBorrowSum" },
				{ "data": "memberNo" },
				{ "data": "create_time" }
			],
			"columnDefs":[
			     {
					"targets":[2],
					"data":'mobilePhone',
					"render":function(data,type,full){
						if(data){
							return  data.substring(0,3) + "***" + data.substring(7,11);
						}else{
							return data;
						}
					}
				},
				{
					"targets":[3],
					"data":'type',
					"render":function(data,type,full){
						console.log(data)
					    if(data == 2) return  '<span class="label label-info">苹果</span>';
						else if(data == 1) return  '<span class="label label-success">安卓</span>';
						else return '<span class="label label-success">'+data+'</span>';
					}
				},
				{
					"targets":[4],
					"data":'member_status',
					"render":function(data,type,full){
						console.log(data)
					    if(data == 2) return  '<span class="label label-info">争议用户</span>';
						else if(data == 3) return  '<span class="label label-danger">黑名单</span>';
						else if(data == 1) return '<span class="label label-success">正常用户</span>';
						else if(data == 0) return '<span class="label label-warning">未申请</span>';
						else return <span class="label label-warning">未注册</span>
						
					}
				},
				{
					"targets":[5],
					"data":'is_real_name',
					"render":function(data,type,full){
						console.log(data)
					    if(data == '已实名') return  '<span class="label label-info">已实名</span>';
						else  return  '<span class="label label-danger">未实名</span>';
					}
				},
				
				{
					"targets":[10],
					"data":'mid',
					"render":function(data,type,full){
						if(${role}!=26){
							return	'<a href="${context}/admin/extension/group/subMember/'+data+'.html">查看</a>';
						}else{
							return '';
							}
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