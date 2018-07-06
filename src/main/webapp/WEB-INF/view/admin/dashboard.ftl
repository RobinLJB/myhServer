<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="dashboard:index" />
<div class="page-content">
  	<@shiro.hasPermission name="dashboard:index">
	<div class="tabbable-line">
		<ul class="nav nav-tabs ">
			<li class="active">
				<a href="#tab_15_1" data-toggle="tab">今日指标</a>
			</li>
			<li>
				<a href="#tab_15_2" data-toggle="tab">累计指标</a>
			</li>
		</ul>
		
		<div class="tab-content" >
			<div class="tab-pane active" id="tab_15_1">
				<div class="row">
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat blue-madison">
							<div class="visual">
								<i class="fa fa-comments"></i>
							</div>
							<div class="details">
								<div class="number">
									 ${memberMap.newMemberIos!0}人/${memberMap.newMemberAndroid!0}人
								</div>
								<div class="desc">
									苹果量/安卓量
								</div>
							</div>
							<a class="more" href="javascript:volid(0);">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat purple-plum">
							<div class="visual">
								<i class="fa fa-globe"></i>
							</div>
							<div class="details">
								<div class="number">
									${applyTimesMap.borrowSum!0}元/${applyTimesMap.applyTimes!0}次
								</div>
								<div class="desc">
									今日申请总额
								</div>
							</div>
							<a class="more" href="javascript:volid(0);">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat red-intense">
							<div class="visual">
								<i class="fa fa-bar-chart-o"></i>
							</div>
							<div class="details">
								<div class="number">
									${borrowMap.borrowSum!0}元/${borrowMap.times!0}次
								</div>
								<div class="desc">
									今日放款总额
								</div>
							</div>
							<a class="more" href="javascript:volid(0);">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat purple-plum">
							<div class="visual">
								<i class="fa fa-globe"></i>
							</div>
							<div class="details">
								<div class="number">
									${repayMap.borrowSum!0}元/${repayMap.times!0}次
								</div>
								<div class="desc">
									今日还款总额
								</div>
							</div>
							<a class="more" href="javascript:volid(0);">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat purple-plum">
							<div class="visual">
								<i class="fa fa-globe"></i>
							</div>
							<div class="details">
								<div class="number">
									${renewalrMap.borrowSum!0}元 /${renewalrMap.renewalTimes!0}次 
								</div>
								<div class="desc">
									续期总额
								</div>
							</div>
							<a class="more" href="javascript:volid(0);">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat purple-plum">
							<div class="visual">
								<i class="fa fa-globe"></i>
							</div>
							<div class="details">
								<div class="number">
									${rejuseMap.borrowSum!0}元 /${rejuseMap.renewalTimes!0}次 
								</div>
								<div class="desc">
									今日拒绝次数
								</div>
							</div>
							<a class="more" href="javascript:volid(0);">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat green-haze">
							<div class="visual">
								<i class="fa fa-shopping-cart"></i>
							</div>
							<div class="details">
								<div class="number">
									${overdueMap.borrowSum!0}元 /${overdueMap.times!0}次
								</div>
								<div class="desc">
									今日逾期总额
								</div>
							</div>
							<a class="more" href="javascript:volid(0);">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
				</div>
			</div>
			
			
			<div class="tab-pane" id="tab_15_2">
			
			
			
			<div class="row">
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat blue-madison">
							<div class="visual">
								<i class="fa fa-comments"></i>
							</div>
							<div class="details">
								<div class="number">
									${memberMap.totalMember!0}
								</div>
								<div class="desc">
									注册用户
								</div>
							</div>
							<a class="more" href="${context}/admin/member.html">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat purple-plum">
							<div class="visual">
								<i class="fa fa-globe"></i>
							</div>
							<div class="details">
								<div class="number">
									${applyTimesMap.borrowTotal!0}元/${applyTimesMap.totalTimes!0}次   
								</div>
								<div class="desc">
									申请总数
								</div>
							</div>
							<a class="more" href="${context}/admin/borrow/forFristAudit.html">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					
					
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat red-intense">
							<div class="visual">
								<i class="fa fa-bar-chart-o"></i>
							</div>
							<div class="details">
								<div class="number">
									${borrowMap.borrowTotal!0}元/${borrowMap.ttimes!0}次  
								</div>
								<div class="desc">
									 放款总额
								</div>
							</div>
							<a class="more" href="${context}/admin/repay/alllist.html">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					
					
					
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat purple-plum">
							<div class="visual">
								<i class="fa fa-globe"></i>
							</div>
							<div class="details">
								<div class="number">
									${repayMap.borrowTotal!0}元/${repayMap.ttimes!0}次    
								</div>
								<div class="desc">
									累计还款
								</div>
							</div>
							<a class="more" href="${context}/admin/repay/repayComplete.html">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					
					
					
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat purple-plum">
							<div class="visual">
								<i class="fa fa-globe"></i>
							</div>
							<div class="details">
								<div class="number">
									${renewalrMap.borrowTotal!0}元 /${renewalrMap.rrTimes!0}次   
								</div>
								<div class="desc">
									续期总额
								</div>
							</div>
							<a class="more" href="${context}/admin/statistics/finance.html">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					
					
					
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat purple-plum">
							<div class="visual">
								<i class="fa fa-globe"></i>
							</div>
							<div class="details">
								<div class="number">
									${rejuseMap.borrowTotal!0}元 /${rejuseMap.tlTimes!0}次   
								</div>
								<div class="desc">
									 拒绝次数
								</div>
							</div>
							<a class="more" href="${context}/admin/statistics/finance.html">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<div class="dashboard-stat green-haze">
							<div class="visual">
								<i class="fa fa-shopping-cart"></i>
							</div>
							<div class="details">
								<div class="number">
									${overdueMap.borrowTotal!0}元 /${overdueMap.ttimes!0}次  
								</div>
								<div class="desc">
									逾期总额
								</div>
							</div>
							<a class="more" href="${context}/admin/statistics/invest.html">
							更多<i class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</@shiro.hasPermission>
	
	
	
	
	
	
	
	<div class="tabbable-line" style="margin-top:10px">
		<ul class="nav nav-tabs ">
			<li class="active">
				<a href="#tab_15_3" data-toggle="tab">借款还款统计</a>
			</li>
			
		</ul>
		
		<div class="tab-content" style="background-color: transparent;">
			<div class="tab-pane active" id="tab_15_3">
				<div class="row">
					<div class="col-md-6 col-sm-6">
						<div class="portlet light ">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-bar-chart font-green-haze"></i>
									<span class="caption-subject bold uppercase font-green-haze">
										一周借还款
									</span>
								</div>
							</div>
							<div class="portlet-body">
								<canvas id="chartRechargeWithdraw" style="height:350px"></canvas>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-sm-6">
						<div class="portlet light ">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-bar-chart font-green-haze"></i>
									<span class="caption-subject bold uppercase font-green-haze">
										一周申请拒绝
									</span>
								</div>
							</div>
							<div class="portlet-body">
								<canvas id="chartInvest" style="height:350px"></canvas>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="tab-pane" id="tab_15_4">
				<div class="row">
					<div class="col-md-6 col-sm-6">
						<div class="portlet light ">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-bar-chart font-green-haze"></i>
									<span class="caption-subject bold uppercase font-green-haze">
										一周借款数据
									</span>
								</div>
							</div>
							<div class="portlet-body">
								<canvas id="chartBorrow" style="height:350px"></canvas>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-sm-6">
						<div class="portlet light ">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-bar-chart font-green-haze"></i>
									<span class="caption-subject bold uppercase font-green-haze">
										一周平台待还
									</span>
								</div>
							</div>
							<div class="portlet-body">
								<canvas id="chartDueout" style="height:350px"></canvas>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	


	<div class="row">
		<div class="col-md-12 col-sm-12">
			<div class="portlet light tasks-widget">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-share font-green-haze hide"></i>
						<span class="caption-subject font-green-haze bold uppercase">等待审核</span>
						<span class="caption-helper"></span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="task-content">
						<ul class="task-list">
							<#if daibanList??>
							<#list daibanList as lists>
							<li>
								<div class="task-title">
								<a >
									<span class="task-title-sp">
										<a href="#">你在${lists.claim_time}认领的借款编号为${lists.borrowId}等待审核</a>
										
									</span>
								</a>
								</div>
								
							</li>
							</#list>
							<#else>
								没有待办事件
							</#if>
							
						
						
						</ul>
					</div>
					<div class="task-footer">
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="portlet light tasks-widget">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-share font-blue-steel hide"></i>
						<span class="caption-subject font-blue-steel bold uppercase">逾期提醒</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="task-content">
						<ul class="task-list">
						<#if overdueList??>
							<#list overdueList as lists>
							<li>
								<div class="task-title">
								<a >
									<span class="task-title-sp">
										<a href="#">借款编号${lists.borrowNo!},借款日为 ${lists.secondAuditTime!}，借款周期为${lists.overduedays!}天，本金为${lists.benJin!} ，姓名 ${lists.realname!},电话 ${lists.phone!}</a>
										
									</span>
								</a>
								</div>
								
							</li>
							</#list>
							<#else>
								没有待办事件
							</#if>
						
							
						</ul>
					</div>
				</div>
				
				<div class="scroller-footer">
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	
		
		
		
	
 
</div>
</@override>
<@override name="script">
<script src="//cdn.bootcss.com/Chart.js/2.4.0/Chart.bundle.min.js"></script>
<script>
	$.ajax({
		url:'${context}/admin/chart/rechargeWithdraw.do',
		success:function(resp){
			if(resp.code == 0){
				var myChart1 = new Chart(document.getElementById("chartRechargeWithdraw"), {
				    type: 'line',
				   
				    
				    
				    data: {
				        labels: resp.data.labels,
				        datasets: [{
				            label: '今日借款金额',
				            data: resp.data.rechargeData,
				            backgroundColor: 'rgba(68, 182, 174,0.2)',
				            borderColor: 'rgba(68, 182, 174,1)',
				            borderWidth: 2,
				            pointColor:"rgba(68, 182, 174,1)",
				            pointStrokeColor:"#fff"
				        },
				        {
				            label: '今日还款金额',
				            data: resp.data.withdrawData,
				            backgroundColor: 'rgba(227, 91, 90, 0.2)',
				            borderColor: 'rgba(227, 91, 90,1)',
				            borderWidth: 2,
				            pointStrokeColor:"#fff"
				        }
				        ]
				    },
				    
				    
				    
				    options: {
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero:false
				                },
								scaleLabel: {
									display: true,
									labelString: '单位(元)'
								}
				            }]
				        }
				    }
				});
			}
		}
	});

	$.ajax({
		url:'${context}/admin/chart/rechargeWithdraw.do',
		success:function(resp){
			if(resp.code == 0){
				var myChart1 = new Chart(document.getElementById("chartInvest"), {
				    type: 'line',
				    data: {
				        labels: resp.data.labels,
				        datasets: [{
				            label: '申请次数',
				            data: resp.data.applyData,
				            backgroundColor: 'rgba(68, 182, 174,0.2)',
				            borderColor: 'rgba(68, 182, 174,1)',
				            borderWidth: 2,
				            pointColor:"rgba(68, 182, 174,1)",
				            pointStrokeColor:"#fff"
				        },
				        {
				            label: '拒绝次数',
				            data: resp.data.rejuseData,
				            backgroundColor: 'rgba(227, 91, 90, 0.2)',
				            borderColor: 'rgba(227, 91, 90,1)',
				            borderWidth: 2,
				            pointStrokeColor:"#fff"
				        }
				        ]
				    },
				    
				    options: {
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero:false
				                },
								scaleLabel: {
									display: true,
									labelString: '单位(次)'
								}
				            }]
				        }
				    }
				});
			}
		}
	});
	$.ajax({
		url:'${context}/admin/chart/invest.do',
		success:function(resp){
			if(resp.code == 0){
				var myChart1 = new Chart(document.getElementById("chartDueout"), {
				    type: 'line',
				    data: {
				        labels: resp.data.labels,
				        datasets: [{
				            label: '待还',
				            data: resp.data.investData,
				            backgroundColor: 'rgba(72, 132, 184,0.2)',
				            borderColor: 'rgba(72, 132, 184,1)',
				            borderWidth: 2
				        }
				        ]
				    },
				    options: {
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero:false
				                },
								scaleLabel: {
									display: true,
									labelString: '单位(元)'
								}
				            }]
				        }
				    }
				});
			}
		}
	});
	$.ajax({
		url:'${context}/admin/chart/invest.do',
		success:function(resp){
			if(resp.code == 0){
				var myChart1 = new Chart(document.getElementById("chartBorrow"), {
				    type: 'line',
				    data: {
				        labels: resp.data.labels,
				        datasets: [{
				            label: '借款',
				            data: resp.data.investData,
				            backgroundColor: 'rgba(72, 132, 184,0.2)',
				            borderColor: 'rgba(72, 132, 184,1)',
				            borderWidth: 2
				        }
				        ]
				    },
				    options: {
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero:false
				                },
								scaleLabel: {
									display: true,
									labelString: '单位(元)'
								}
				            }]
				        }
				    }
				});
			}
		}
	});
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />