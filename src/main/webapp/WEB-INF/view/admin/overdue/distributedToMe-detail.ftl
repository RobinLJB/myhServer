<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/public/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">
<link href="${context}/asset/admin/css/processor_bar.css" rel="stylesheet">
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
	#loanImgs{
		overflow: hidden;
		list-style: none;
		margin: 0px;
		padding: 0px;
	}
	#loanImgs li{
		float: left;
		margin-right: 10px;
	}
	#loanImgs li img{
		width:100px;
		border:1px solid #999;
	}
	
</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="mymanager:index" />
<input type="hidden" id="borrowId" value="${borrowMap.id!}" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">逾期管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">我的催收</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">催收详情</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	
	<div id="stepItems">
		<ul class="processor_bar grid_line">
			<li data-id="1" class="step grid_item size1of5">
				<h4>1 未催收</h4>
			</li>
			<li data-id="2" class="step grid_item size1of5">
				<h4>2已催收</h4>
			</li>
			<li data-id="3" class="step grid_item size1of5">
				<h4>3 已还款</h4>
			</li>
			<li data-id="4" class="no_extra step grid_item size1of5">
				<h4>4 死账</h4>
			</li>
			
		</ul>
	</div>
	
	
		
	<table class="table detail-info">
		<thead>
			<th colspan="4">借款人信息</th>
		</thead>
		<tbody>
			<tr>
				<th>姓名</th>
				<td>${member.realName!}</td>
				<th>身份证</th>
				<td>${member.identNo!}</td>
			</tr>
				
			<tr>
				<th>手机号</th>
				<td>${member.mobilePhone!}</td>
				<th>注册时间</th>
				<td>${member.createTime!}</td>
			</tr>
			
			<tr>
				<th>会员性别</th>
				<td>${areaMap.sex!}</td>
				<th>会员年龄</th>
				<td>${areaMap.age!}</td>
			</tr>
			
			<tr>
				<th>所属地区</th>
				<td>${areaMap.area!}</td>
				<th>其他信息</th>
				<td>空</td>
			</tr>
			
			<tr>
				<th>会员状态</th>
				<td>
					<#if member.memberStatus ==0>
						<span class="label label-info">等待审核</span>
					<#elseif member.memberStatus ==1>
						<span class="label label-success">正常用户</span>
					<#elseif member.memberStatus ==2>
						<span class="label label-warning">争议用户</span>
					<#elseif member.memberStatus ==3>
						<span class="label label-danger">黑名单</span>
					</#if>
				</td>
				<th>成功借款次数</th>
				<td>${member.successBorrowTimes!0}</td>
				</tr>
				
				<tr>
					<th>邀请人数</th>
					<td class="blue12 left">${member.invateSum!0}</td>
					<th>剩余佣金</th>
					<td class="f66 leftp200">${member.commisionSum!0}</td>
				</tr>

            <tr>
                <th>身份证照片A：</th>
                <td>
					<#if cardMap.card_imgA??>
                        <ul id="loanImgs">
                            <li><img src="${cardMap.card_imgA!}" /></li>
                        </ul>
					<#else>
                        <span class="label label-warning">无图片</span>
					</#if>
                </td>
                <th>身份证照片B：</th>
                <td>
					<#if cardMap.card_imgB??>
                        <ul id="loanImgs">
                            <li><img src="${cardMap.card_imgB!}" /></li>
                        </ul>
					<#else>
                        <span class="label label-warning">无图片</span>
					</#if>
                </td>

            </tr>
            <tr>
                <th>手持身份证照片：</th>
                <td>
					<#if cardMap.handle_img??>
                        <ul id="loanImgs">
                            <li><img src="${cardMap.handle_img!}" /></li>
                        </ul>
					<#else>
                        <span class="label label-warning">无图片</span>
					</#if>
                </td>
            </tr>
			</tbody>
		</table>
	
		
		<table class="table detail-info">
			<thead>
				<th colspan="4">借款人认证信息</th>
			</thead>
			<tbody>
				<#--<tr>
					<th>QQ</th>
					<td><#if infoMap??> ${infoMap.qqno!} </#if></td>
					<th>微信</th>
					<td><#if infoMap??> ${infoMap.weino!} </#if></td>
				</tr>
				<tr>
					<th>学历</th>
					<td><#if infoMap??> ${infoMap.xueli!} </#if></td>
					<th>紧急联系人</th>
					<td><#if infoMap??> ${infoMap.phone!} </#if>  <#if infoMap??> ${infoMap.relation!} </#if></td>
				</tr>-->
				<tr>
					<#--<th>手机通话详单</th>
					<td><#if auditMap?? && auditMap.phone_status=='1' ><a href="${context}/admin/member/phoneTalkDetail/${member.id?c}.html">查看通话详单</a><#else>没有生成通话详单</#if></td>-->
					<th>手机报告</th>
					<#--<td><#if member?? && member.callStatus=='1'><a href="${context}/admin/member/callDetail/${member.id?c}.html">查看运营商信息</a><#else>没有运营商信息</#if></td>-->
					<td><#if member.taskId??><a href="https://portal.shujumohe.com/nolayout/customerReport/${member.taskId}" target="_blank">查看运营商信息</a><#else>没有运营商信息</#if></td>
					<#--<th>通讯录</th>
					<td><a href="${context}/admin/member/${member.id}.html?displayStatus=4">查看通讯录</a> ${linkmansize.linkmansize!}</td>-->
				</tr>
			</tbody>
		</table>
	
		
		<table class="table detail-info">
			<thead>
				<th colspan="4">借款信息</th>
			</thead>
			<tbody>
				<tr>
					<th>借款编号</th>
					<td>${borrowMap.borrowNo!}</td>
				</tr>
				<tr>
					<th>本金</th>
					<td>${borrowMap.benJin!}</td>
					<th>借款天数</th>
					<td>${borrowMap.borrowDate!}</td>
				</tr>
				<tr>
					<th>打款时间</th>
					<td>${borrowMap.secondAuditTime!}</td>
					<th>实际还款时间</th>
					<td>${borrowMap.finalRepayTime!}</td>
				</tr>
			</tbody>
		</table>
		
		
		<table class="table detail-info">
			<thead>
				<th colspan="4">审核信息</th>
			</thead>
			<tbody>
				<tr>
					<th>审核意见</th>
					<td><#if reviewMap??>${reviewMap.frist_review!}<#else><#if member.memberStatus==1>自动审核<#else>没有审核</#if></#if></td>
					<th>初审时间</th>
					<td><#if reviewMap??>${reviewMap.frist_audit_time!}<#else>${borrowMap.fristSubmitTime!}</#if></td>
				</tr>
				
				<tr>
					<th>复审意见</th>
					<td><#if reviewMap??>${reviewMap.second_review!}<#else><#if member.memberStatus==1>自动审核<#else>没有审核</#if></#if></td>
					<th>复审时间</th>
					<td><#if reviewMap??>${reviewMap.second_audit_time!}<#else>${borrowMap.secondAuditTime!}</#if></td>
				</tr>
			</tbody>
		</table>
		
		<table class="table detail-info">
			<thead>
				<th colspan="8">还款记录</th>
			</thead>
			<thead>
        		<tr>
        			<th>还款序号</th>
          			<th>还款总额</th>
					<th>剩余本金</th>
					<th>操作人员</th>
					<th>还款时间</th>
        		</tr>
      		</thead>
      		<#if apartList??>
       			<tbody>
        			<tr>
          				<#list apartList as lists>
							<tr>
								<td >${lists.id!}</td>
								<td >${lists.amount!}</td>
								<td>${lists.remainBenjin!}</td>
								<td>${lists.username!}</td>
								<td>${lists.repayTime!}</td>
							</tr>
						</#list>
        			</tr>
       			</tbody>
       		<#else>
       			没有还款记录
       		</#if>
       		
		</table>
		
		
		<#if renewalList??>
		<table class="table detail-info">
			<thead>
				<th colspan="8">续期记录</th>
			</thead>
			<thead>
        		<tr>
        			<th>续期编号</th>
          			<th>剩余本金</th>
					<th>续期天数</th>
					<th>续期费用</th>
					<th>申请时间</th>
					<th>申请地址</th>
					<#--<th>扣款卡号</th>-->
        		</tr>
      		</thead>
       		<tbody>
        		<tr>
          			<#if renewalList??>
						<#list renewalList as lists>
							<tr>
								<td >${lists_index+1}</td>
								<td >${lists.benJin!0}</td>
								<td>${lists.renewalDays!}</td>
								<td>${lists.renewalFee!}</td>
								<td>${lists.renewalTime!}</td>
								<td>${lists.renewalIp!}</td>
								<#--<td>${lists.bankCardNo!}</td>-->
							</tr>
						</#list>
					<#else>
						<tr>
							<th colspan="4">没有部分还款记录</th>
						</tr>
					</#if>
        		</tr>
       		</tbody>
		</table>
		</#if>
		
		
		
		<table class="table detail-info">
			<thead>
				<th colspan="8">逾期记录</th>
			</thead>
			<tbody>
				<tr>
					<th>逾期编号</th>
					<td>${overdueMap.id}</td>
					<th>剩余本金</th>
					<td>${overdueMap.benjin!0}</td>
					<th>逾期天数</th>
					<td>${overdueMap.overDays!}天</td>
					<th>逾期费用</th>
					<td>${overdueMap.overdueFee!0}</td>
				</tr>
				
			</tbody>
		</table>
		
		
		
		<form class="form-horizontal" id="articleform" style="margin-top:20px;margin-right: 572px;">
			
			
			
		<div class="form-group">
		    <label class="col-sm-2 control-label">电话沟通</label>
				<label class="checkbox-inline">
					<input type="radio" name="phoneCall" value="1"  >已沟通
				</label>
				<label class="checkbox-inline">
					<input type="radio" name="phoneCall"   value="2" >未沟通
				</label>
		</div>
		
		
		<div class="form-group">
		    <label class="col-sm-2 control-label">是否还款</label>
				<label class="checkbox-inline">
					<input type="radio" name="repayStatus" value="1"  >已还款
				</label>
				<label class="checkbox-inline">
					<input type="radio" name="repayStatus"   value="2" >未还款
				</label>
				<label class="checkbox-inline">
					<input type="radio" name="repayStatus"   value="3" >死账
				</label>
		</div>
		
		
				
			
		
		<div class="form-group">
				<label for="inputEmail3" class="col-sm-2 control-label">沟通结果</label>
				<div class="col-sm-10">
				  <textarea id="auditOpinion" class="form-control" placeholder="请输入催收结果"></textarea>
				</div>
			</div>
		
		
		
		<div class="form-group">
			<div class="col-sm-10 col-sm-offset-2">
		  		<button id="save" type="button" class="btn btn-primary" data-loading-text="正在保存..." autocomplete="off">保存催收记录</button>
		  	</div>
		</div>
	</form>
		
	
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
	$(function(){
		var status  = ${borrowMap.pressStatus};
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
	
	
	$("#save").click(function(){
		var param = {};
		var phoneCall=$('input[name=phoneCall]:checked').val();
		var repayStatus=$('input[name=repayStatus]:checked').val();
		param['borrowId'] = $("#borrowId").val();
		param['auditOpinion'] = $("#auditOpinion").val();
		param['phoneCall'] = phoneCall;
		param['repayStatus'] = repayStatus;
		
		$.post('${context}/admin/overdue/pressRecordUpdate.do',param,function(resp){
			if(resp.code == 0){
				spark.notify(resp.message);
				spark.redirect('${context}/admin/overdue/pressedAlreadyepay.html');
			} else if(resp.code == -2){
				spark.notify(resp.message,'error');
				spark.redirect('${context}/admin/overdue/pressedUnrepay.html');
			} else if(resp.code == -3){
				spark.notify(resp.message,'error');
				spark.redirect('${context}/admin/overdue/pressedDieaBorrow.html');
			}
		});
		
	})

	
	
	
</script>
	
</@override>
<@layout name="/admin/layout/main.ftl" />