<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/admin/css/processor_bar.css" rel="stylesheet">
<link href="${context}/asset/public/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">
<style>
	.detail-info {
		width: 871px;
		line-height: 20px;
		border-style: solid;
		border-width: 0 0 1px 1px;
		border-color: transparent transparent #CCC #CCC;
		margin: 10px 0 20px 0;
		box-shadow: 2px 2px 2px rgba(204,204,204,0.25);
	}
	.detail-info tbody th {
		color: #777;
		background-color: #F7F7F7;
		text-align: right;
		width: 119px;
		height: 20px;
		padding: 8px 5px;
		border-style: solid;
		border-width: 1px 1px 0 1px;
		border-color: #CCC;
	}
	.detail-info thead th {
		font-weight: 600;
		color: #FFF;
		background-color: #364150;
		height: 20px;
		padding: 8px 5px;
		border-style: solid;
		border-width: 1px 1px 0 0;
		border-color: #CCC #CCC transparent transparent;
	}
	#loanImgs,#eventImgs{
		overflow: hidden;
		list-style: none;
		margin: 0px;
		padding: 0px;
	}
	#loanImgs li,#eventImgs li{
		float: left;
		margin-right: 10px;
	}
	#loanImgs li img,#eventImgs li img{
		width:100px;
		border:1px solid #999;
	}
</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="welfare:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">募集管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="${context}/admin/welfare/index.html">全部募集</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">募集详情</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<#if loan.status = "6">
	<div class="alert alert-danger" style="margin-top:20px;">
		<h3>该笔募集已流标</h3>
	</div>
	<#else>
	<div id="stepItems">
		<ul class="processor_bar grid_line">
			<li data-id="0" class="step grid_item size1of5">
				<h4>1 待编辑</h4>
			</li>
			<li data-id="1" class="step grid_item size1of5">
				<h4>2 初审中</h4>
			</li>
			<li data-id="2" class="step grid_item size1of5">
				<h4>3 招标中</h4>
			</li>
			<li data-id="3" class="step grid_item size1of5">
				<h4>4 复审中</h4>
			</li>
			<li data-id="4" class="no_extra step grid_item size1of5">
				<h4>5 已结束</h4>
			</li>
		</ul>
	</div>
	</#if>
	
	<table class="table detail-info">
			<thead>
				<th colspan="4">募集信息</th>
			</thead>
			<tbody>
				<tr>
					<th>募集标题：</th>
					<td>
						${loan.title}
					</td>
					<th>开标时间：</th>
					<td>
						${loan.open_time!}
					</td>
				</tr>
				<tr>
					<th>封面图片：</th>
					<td colspan="3">
					<#if loan.cover??>
					<img src="${context}${loan.cover!}" style="width:120px;height:100px;">
					<#else>
					无
					</#if>
					</td>
				</tr>
				<tr>
					<th>用户名：</th>
					<td>
						${loan.username!}
					</td>
					<th>真实姓名：</th>
					<td>
						${loan.realName!}
					</td>
				</tr>
				<tr>
					<th>募集金额：</th>
					<td>
						${loan.amount!}&nbsp;元
					</td>
					<th>实际募捐：</th>
					<td>
						${loan.invested_amount!}&nbsp;元
					</td>
				</tr>
		 
				<tr>
					<th>最低投标金额：</th>
					<td class="blue12 left">
						${loan.min_invest_amount!0}元
					</td>
					<th>最高投标金额：</th>
					<td class="f66 leftp200">
						${loan.max_invest_amount!0}元                                         
					</td>
				</tr>
				<tr>
					<th>募集期限：</th>
					<td>
						${loan.raise_term}天
					</td>
					<th>投标密码：</th>
					<td>
						<#if loan.invest_pwd?has_content>
						${loan.invest_pwd!}
						<#else>
						无
						</#if>
					</td>
				</tr>
				<tr>
					<th>公益机构介绍：</th>
					<td colspan="3">
						${loan.organization!}
					</td>
				</tr>
				<tr>
					<th>募集详情：</th>
					<td colspan="3">
						${loan.detail!}
					</td>
				</tr>
				<tr>
					<th>发布时间：</th>
					<td>
						${loan.publish_time!}
					</td>
					<th>发布IP：</th>
					<td>${loan.publish_ip!}</td>
				</tr>
 
				<tr>
					<th>公益机构图片：</th>
					<td colspan="3">
						<#if loanImgs??>
						<ul id="loanImgs">
							<#list loanImgs as img>
							<li><img  style="width: 200px;height:150px;" src="${img}" src="${img}" /></li>
							</#list>
						</ul>
						</#if>
					</td>
				</tr>
				<tr>
				<th>公益事件图片：</th>
					<td colspan="3">
						<#if eventImgs??>
						<ul id="eventImgs">
							<#list eventImgs as img>
							<li><img  style="width: 200px;height:150px;" src="${img}" src="${img}" /></li>
							</#list>
						</ul>
						</#if>
					</td>
				</tr>
				<#if loan.status == "1">
				<tr>
					<th>开标时间：</th>
					<td>
					<input type="text" class="form-control"   name="open_time" id="open_time" data-date-format="yyyy-mm-dd hh:ii"   placeholder="不填则为初审时间" value="${loan.open_time!}">  
					</td>
				</tr>
				
				</#if>
			</tbody>
		</table>

		<table class="table detail-info">
			<thead>
				<th colspan="4">审核信息</th>
			</thead>
			<tbody>
				<#if loan.status = "6">
				<#else>
				<#if loan.status?number == 1>
				<tr>
					<th>
						初审意见：
					</th>
					<td colspan="3">
						<textarea id="auditOpinion" class="form-control" placeholder="请输入风控意见"></textarea>
					</td>
				</tr>
				<#elseif (loan.status?number > 1)>
				<tr>
					<th>初审时间：
					<td>
						${loan.audit_time!''}
					</td>
					<th>
						初审意见：
					</th>
					<td>
						<p>${loan.audit_opinion!''}</p>
					</td>
				</tr>
				<tr>
					<th>开标时间：</th>
					<td>${loan.open_time!}</td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
				</#if>
				<#if (loan.status?number = 3)>
				<tr>
					<th>
						复审意见：
					</th>
					<td colspan="3">
						<textarea id="auditOpinion" class="form-control" placeholder="请输入复审意见"></textarea>
					</td>
				</tr>
				<#elseif (loan.status?number > 3)>
				<tr>
					<th>复审时间：
					<td>
						${loan.fullscale_audit_time!''}
					</td>
					<th>
						复审意见：
					</th>
					<td>
						<p>${loan.fullscale_audit_opinion!''}</p>
					</td>
				</tr>
				</#if>
				</#if>
			</tbody>
		</table>
		<#if (loan.status?number > 1)>
		<table class="table detail-info">
			<thead>
			   <tr>
				  <th colspan="4">投资信息</th>
			   </tr>
			</thead>
			<tbody>
				<tr>
					<th>
						投资进度：
					</th>
					<td colspan="3">
						<div class="progress">
						  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="min-width: 3em;width: ${loan.progress_scale}%;">
							${loan.progress_scale}%
						  </div>
						</div>
					</td>
				</tr>
				<tr>
					<th>
						募集总额：
					</th>
					<td>${loan.amount}&nbsp;元</td>
					<th>
						投资总额：
					</th>
					<td>${loan.invested_amount}&nbsp;元</td>
				</tr>
				<tr>
					<th>
						投资人数：
					</th>
					<td>${loan.invest_num}</td>
					<th>
						浏览人数：
					</th>
					<td>${loan.pv}</td>
				</tr>
			</tbody>
		</table>
		<table class="table table-bordered" style="width: 871px;">
			<thead>
				<th>用户名</th>
				<th>真实姓名/手机号</th>
				<th>投资金额</th>
				<th>投资时间</th>
				<th>投资方式</th>
			</thead>
			<tbody>
			
			<#list investList as invest>
				<tr>
					<td>${invest.username}</td>
					<td>${invest.realName!''}/${invest.mobilePhone}</td>
					<td>${invest.amount}</td>
					<td>${invest.investTime}</td>
					<td>手动投标</td>
				</tr>
			
			<#else>
			<tr><td colspan="5" style="text-align:center;">暂无投资信息</td></tr>
			</#list>
			</tbody>
		</table>
		</#if>
		<#if loan.status == "0">
			<a class="btn btn-success" href="${context}/admin/welfare/edit/${loan.id}.html" >编辑</a>
		<#elseif loan.status == "1">
		<button class="btn btn-success" type="button" onclick="audit(1)">初审通过</button>
		<button class="btn btn-error" type="button" onclick="audit(0)">初审不通过</button>
		<#elseif loan.status == "2">
		<button class="btn btn-success" type="button" onclick="audit(4)">审核为完成</button>
		<button class="btn btn-danger" type="button" onclick="cancel()">流标</button>
		<#elseif loan.status == "3">
		<button class="btn btn-success" type="button" onclick="audit(4)">审核为完成</button>
		<button class="btn btn-danger" type="button" onclick="cancel()">流标</button>
		</#if>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
	$(function(){
		var status  = ${loan.status};
		var prev = status -1;
		var next = status +1;
		$('.step').each(function(index,el){
			var s = parseInt($(this).attr('data-id'));
			if(s == status){
				$(this).addClass('current');
			}
			else if(s == prev){
				$(this).addClass('prev');
			}
			else if(s == next){
				$(this).addClass('next');
			}
			else if(s < prev){
				$(this).addClass('pprev');
			}
			else if(s > next){
				$(this).addClass('nnext');
			}
		});
	});
	<#if loan.status == "1">
	function audit(status){
		var param = {};
		param['id'] = ${loan.id};
		param['opinion'] = $('#auditOpinion').val();
		param['open_time'] = $('#open_time').val();
		param['status'] = status;
		$.post('${context}/admin/welfare/firstaudit.do',param,function(resp){
			if(resp.code == 0){
				spark.notify(resp.message);
				spark.redirect('${context}/admin/welfare/'+${loan.id}+'.html');
			} else{
				spark.notify(resp.message,'error');
			}
		});
	}
	<#elseif loan.status == "2" || loan.status == "3">
	function cancel(){
		spark.confirm("流标后无法撤销，确定继续吗？",function(){
			var param = {};
			param['id'] = ${loan.id};
			$.post('${context}/admin/welfare/cancel.do',param,function(resp){
				if(resp.code == 0){
					spark.notify(resp.message);
					spark.redirect('${context}/admin/welfare/'+${loan.id}+'.html');
				} else{
					spark.notify(resp.message,'error');
				}
			});
		})
	}
	function audit(status){
		var param = {};
		param['id'] = ${loan.id};
		param['opinion'] = $('#auditOpinion').val();
		param['status'] = status;
		$.post('${context}/admin/welfare/fullScaleAudit.do',param,function(resp){
			if(resp.code == 0){
				spark.notify(resp.message);
				spark.redirect('${context}/admin/welfare/'+${loan.id}+'.html');
			} else{
				spark.notify(resp.message,'error');
			}
		});
	}
	</#if>
 $(function(){
	$('#open_time').datetimepicker({lang:'zh-CN',format: 'yyyy-mm-dd hh:ii',startDate:new Date(),autoclose:true});
 });	
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />