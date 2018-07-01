<@override name="body">

<input type="hidden" id="MENU_ACTIVE_ID" value="member:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<a href="#">用户管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">运营商信息详情</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<div style="margin-top: 20px;padding: 0 20px;">
		
		<ul class="nav nav-pills" role="tablist">
			<li role="presentation" class="active">
				<a href="#basic" aria-controls="basic" role="tab" data-toggle="tab">运营商信息</a>
			</li>			
		</ul>
		
		<div class="tab-content">
			<div role="tabpanel" class="tab-pane active" id="basic">
			
				<table class="table detail-info">
						<thead>
							<th colspan="4">基本信息</th>
						</thead>
						<tbody>
							<tr>
								<th>姓名</th>
								<td>${detailMap.real_name!}</td>
							</tr>
							<tr>
								<th>手机号</th>
								<td>${detailMap.user_mobile!}</td>
							</tr>
							<tr>
								<th>入网时间</th>
								<td>${detailMap.mobile_net_time!}</td>
							</tr>
							<tr>
								<th>账户状态</th>
								<td>${detailMap.account_status!}</td>
							</tr>
							<tr>
								<th>套餐类型</th>
								<td>${detailMap.package_type!}</td>
							</tr>
							<tr>
								<th>最近常用通话地</th>
								<td>${detailMap.call_area_city!}</td>
							</tr>
							<tr>
								<th>入网归属地</th>
								<td>${detailMap.mobile_net_addr!}</td>
							</tr>
							<tr>
								<th>联系地址</th>
								<td>${detailMap.home_addr!}</td>
							</tr>
						</tbody>
					</table>


			<div role="tabpanel" class="tab-pane" id="carrier_consumption">
				<table id="carrier_consumption" class="table table-striped table-bordered">
					<thead>
						<th colspan="9">运营商数据统计</th>
						<tr>
							<th>月份</th>
							<th>主叫时长(分钟)</th>
							<th>被叫时长(分钟)</th>
							<th>主叫次数</th>
							<th>被叫次数</th>
							<th>短信次数</th>
							<th>话费金额(元)</th>
							<th>缴费金额(元)</th>
							<th>月充值次数</th>
						</tr>
					</thead>
					<#if carrier_consumption??>
					<#list carrier_consumption as l> 
						<tr>
						<td>${l.month!}</td>
						<td>${l.call_time_active!}</td>
						<td>${l.call_time_passive!}</td>
						<td>${l.call_count_active!}</td>
						<td>${l.call_count_passive!}</td>
						<td>${l.msg_count!}</td>
						<td>${l.consume_amount!}</td>
						<td>${l.recharge_amount!}</td>
						<td>${l.recharge_count!}</td>
						</tr>
					</#list>
					</#if>			
				</table>
			</div>

			<div role="tabpanel" class="tab-pane" id="contact_area_stats">
				<table id="contact_area_stats" class="table table-striped table-bordered">
					<thead>
						<th colspan="7">联系人地区分布</th>
						<tr>
							<th>地区</th>		 
							<th>主叫时长(分钟)</th>
							<th>主叫时长占比(%)</th>
							<th>被叫时长(分钟)</th>
							<th>被叫时长占比(%)</th>
							<th>主叫次数</th>
							<th>被叫次数</th>
						</tr>
					</thead>
					<#if contact_area_stats??>
					<#list contact_area_stats as l> 
						<tr>
						<td>${l.contact_area_city!}</td> 
						<td>${l.call_time_active_6month!}</td>
						<td>${l.talkTimeEevry!}</td>
						<td>${l.call_time_passive_6month!}</td>
						<td>${l.talkTimeEevryPassive!}</td>
						<td>${l.call_count_active_6month!}</td>
						<td>${l.call_count_passive_6month!}</td>
						</tr>
					</#list>
					</#if>			
				</table>
			</div>

			<div role="tabpanel" class="tab-pane" id="all_contact_stats">
				<table id="all_contact_stats" class="table table-striped table-bordered">
					<thead>
						<th colspan="10">常用联系人TOP 50</th>
						<tr>
							<th>序号</th>
							<th>手机号码</th>
							<th>归属地</th>
							<th>主叫时长(分钟)</th>
							<th>主叫时长占比(%)</th>
							<th>被叫时长(分钟)</th>
							<th>被叫时长占比(%)</th>
							<th>主叫次数</th>
							<th>被叫时长</th>
							<th>短信次数</th>
						</tr>
					</thead>
					<#if all_contact??>
					<#list all_contact as l> 
						<tr>
						<td>${l.contact_seq_no!}</td>
						<td>${l.contact_number!}</td>
						<td>${l.contact_area!}</td>
						<td>${l.call_time_active_6month!}</td>
					    <td>${l.talkTimeEevryAll!}</td>
						<td>${l.call_time_passive_6month!}</td>
						<td>${l.talkTimeEevryPassiveAll!}</td>
						<td>${l.call_count_active_6month!}</td>
						<td>${l.call_count_passive_6month!}</td>
						<td>${l.msg_count_6month!}</td>
						</tr>
					</#list>
					</#if>			
				</table>
			</div>



			</div>	
		</div>
	</div>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>

</@override>
<@layout name="/admin/layout/main.ftl" />