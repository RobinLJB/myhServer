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
<input type="hidden" id="MENU_ACTIVE_ID" value="statistics:apply" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">统计管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">申请管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">所有申请</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	
	<#if borrowMap.borrowStatus = "3">
		<div class="alert alert-danger" style="margin-top:20px;">
			<p>该借款初审失败</p>
		</div>
	</#if>
	<#if borrowMap.borrowStatus = "7">
		<div class="alert alert-danger" style="margin-top:20px;">
			<p>该借款复审失败</p>
		</div>
	</#if>
	
	<#if borrowMap.borrowStatus = "10">
	<div class="alert alert-info" style="margin-top:20px;">
		<p>该借款已经还完</p>
	</div>
	<#else>
	<div id="stepItems">
		<ul class="processor_bar grid_line">
			<li data-id="2" class="step grid_item size1of5">
				<h4>1 初审中</h4>
			</li>
			<li data-id="5" class="step grid_item size1of5">
				<h4>2复审中</h4>
			</li>
			<li data-id="8" class="step grid_item size1of5">
				<h4>3 还款中</h4>
			</li>
			<li data-id="9" class="no_extra step grid_item size1of5">
				<h4>4 逾期中</h4>
			</li>
			<li data-id="10" class="no_extra step grid_item size1of5">
				<h4>5 已完成</h4>
			</li>
			
		</ul>
	</div>
	</#if>
	
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
					<td>
						${member.mobilePhone!}
					</td>
					<th>注册时间</th>
					<td>
						${member.createTime!}
					</td>
				</tr>
				<tr>
					<th>所属地区</th>
					<td>
						${areaMap.area!}
					</td>
					<th>会员年龄</th>
					<td>
						${areaMap.age!}
					</td>
				</tr>
				<tr>
					<th>会员性别</th>
					<td>
						${areaMap.sex!}
					</td>
					<th>通讯录</th>
					<td>
						${member.createTime!}
					</td>
				</tr>
				
				<tr>
					<th>会员状态</th>
					<td>
						<#if member.memberStatus ==0>
							<span class="label label-info">待审核用户</span>
						<#elseif member.memberStatus ==1>
							<span class="label label-success">正常用户</span>
						<#elseif member.memberStatus ==2>
							<span class="label label-warning">争议用户</span>
						<#elseif member.memberStatus ==3>
							<span class="label label-danger">黑名单</span>
						</#if>
					</td>
					<th>成功借款次数</th>
					<td>
						${member.successBorrowTimes!0}
					</td>
				</tr>
				
				<tr>
					<th>邀请人数</th>
					<td class="blue12 left">
						${member.invateSum!0}
					</td>
					<th>剩余佣金</th>
					<td class="f66 leftp200">
						${member.commisionSum!0}                            
					</td>
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
					<#if iphoneMap.status="2">
                        <th>icloud认证：</th>
                        <td>
							<#if iphoneMap.icloud_imgurl??>
                                <ul id="loanImgs">
                                    <li><img src="${iphoneMap.icloud_imgurl!} "/></li>
                                </ul>
							<#else>
                                <span class="label label-warning">无图片</span>
							</#if>
                        </td>
					</#if>

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
					<td><#if infoMap??> ${infoMap.phone!} </#if> <#if infoMap??> ${infoMap.relation!} </#if></td>
				</tr>-->
				<#--<tr>
					<!--<th>芝麻信用</th>
					<td>${auditMap.alipay_score!}</td>
					<th>认证时间</th>
					<td>${auditMap.alipay_audit_time!}</td>&ndash;&gt;
					<th>人脸认证</th>
					<td>
						<#if auditMap.face_status =="1">
							<span>通过</span>
						<#elseif auditMap.face_status =="0">
							<span>未通过</span>
						</#if>
					</td>
					<th></th>
					<td></td>
				</tr>-->
				<tr>
					<!--<th>手机通话详单</th>
					<td><#if auditMap?? && auditMap.phone_status=='1' ><a href="${context}/admin/member/phoneTalkDetail/${member.id?c}.html">查看通话详单</a><#else>没有生成通话详单</#if></td>-->
					<th>运营商信息</th>
					<td><#if member?? && member.callStatus=='1'><a href="${context}/admin/member/callDetail/${member.id?c}.html">查看运营商信息</a><#else>没有运营商信息</#if></td>
					<th>通讯录</th>
					<td><a href="${context}/admin/member/${member.id}.html?displayStatus=4">查看通讯录</a> ${linkmansize.linkmansize!}</td>
				</tr>
			</tbody>
		</table>
		
		<table class="table detail-info">
			<thead>
				<th colspan="8">借款信息</th>
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
					<th>初审时间</th>
					<td>${borrowMap.fristSubmitTime!}</td>
					<th>复审时间</th>
					<td>${borrowMap.secondAuditTime!}</td>
				</tr>
				<tr>
					<th>打款时间</th>
					<td><span class="label label-danger">${borrowMap.secondAuditTime!}</span></td>
					<th>约定还款日期</th>
					<td><span class="label label-danger">${borrowMap.appointmentTime!}</span></td>
					<th>实际还款时间</th>
					<td><span class="label label-danger">${borrowMap.finalRepayTime!}</span></td>
				</tr>
				
			</tbody>
		</table>
		
		<table class="table detail-info">
			<thead>
				<th colspan="8">审核信息</th>
			</thead>
			<tbody>
				<tr>
					<th>初审人员</th>
					<td><#if fadmin??>${fadmin.username!}<#else><#if member.memberStatus==1>自动审核<#else>没有审核</#if></#if></td>
					<th>初审意见</th>
					<td><#if reviewMap??>${reviewMap.frist_review!}<#else><#if member.memberStatus==1>自动审核<#else>没有审核</#if></#if></td>
					<th>初审时间</th>
					<td><#if reviewMap??>${reviewMap.frist_audit_time!}<#else>${borrowMap.fristSubmitTime!}</#if></td>
				</tr>
				
				<tr>
					<th>复审人员</th>
					<td><#if sadmin??>${sadmin.username!}<#else><#if member.memberStatus==1>自动审核<#else>没有审核</#if></#if></td>
					<th>复审意见</th>
					<td><#if reviewMap??>${reviewMap.second_review!}<#else><#if member.memberStatus==1>自动审核<#else>没有审核</#if></#if></td>
					<th>复审时间</th>
					<td><#if reviewMap??>${reviewMap.second_audit_time!}<#else>${borrowMap.secondAuditTime!}</#if></td>
				</tr>
			</tbody>
		</table>
		
		<#--<table class="table detail-info">
			<thead>
				<th colspan="4">银行卡信息</th>
			</thead>
			<tbody>
				<tr class="active">
					<th>银行名称</th>
					<td><#if bankMap??>${bankMap.bankName!}<#else>等待输入</#if></td>
					<th>支行名称</th>
					<td><#if bankMap??>${bankMap.branchBank!}<#else>等待输入</#if></td>
				</tr>
				<tr>
					<th>银行卡号</th>
					<td><#if bankMap??>${bankMap.cardNo!}<#else>等待输入</#if></td>
					<th>预留手机号</th>
					<td><#if bankMap??>${bankMap.phone!}<#else>等待输入</#if></td>
				</tr>
			</tbody>
		</table>-->
		
		
		
			<table class="table detail-info">
				<thead>
					<th colspan="8">还款记录</th>
				</thead>
				<thead>
        			<tr>
        				<th style="background: #4c89bf;">还款序号</th>
	          			<th style="background: #4c89bf;">还款总额</th>
						<th style="background: #4c89bf;">剩余本金</th>
						<th style="background: #4c89bf;">操作人员</th>
						<th style="background: #4c89bf;">还款时间</th>
        			</tr>
      			</thead>
       			<tbody>
        			<tr>
          				<#if apartList??>
          					<#list apartList as lists>
								<tr>
									<td >${lists.id!}</td>
									<td >${lists.amount!}</td>
									<td>${lists.remainBenjin!}</td>
									<td>${lists.adminid!}</td>
									<td>${lists.repayTime!}</td>
								</tr>
							</#list>
          			
						<#else>
							<tr>
					<th colspan="8" style="text-align:center;">没有部分还款记录</th>
					</tr>
						</#if>
       				</tr>
       			</tbody>
			</table>
	
		
	
		
		
		<#if renewalList??>
		<table class="table detail-info">
			<thead>
				<th colspan="8">续期记录</th>
			</thead>
			<thead>
        		<tr>
        			<th style="background: #4c89bf;">续期编号</th>
          			<th style="background: #4c89bf;">剩余本金</th>
					<th style="background: #4c89bf;">续期天数</th>
					<th style="background: #4c89bf;">续期费用</th>
					<th style="background: #4c89bf;">申请时间</th>
					<th style="background: #4c89bf;">申请地址</th>
					<#--<th style="background: #4c89bf;">扣款卡号</th>-->
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
							<th colspan="8" style="text-align:center;">没有续期记录</th>
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
			<thead>
        		<tr>
        			<th style="background: #4c89bf;">逾期编号</th>
          			<th style="background: #4c89bf;">逾期费用</th>
					<th style="background: #4c89bf;">逾期天数</th>
					<th style="background: #4c89bf;">开始逾期日期 </th>
        		</tr>
      		</thead>
       		<tbody>
        		<tr>
          			<#if overdueMap??>
          			
						<tr>
							<td >${overdueMap.id}</td>
							<td >${overdueMap.overDays!0}</td>
							<td>${overdueMap.overdueFee!}</td>
							<td>${overdueMap.beginTime!}</td>
						</tr>
					
          			
						
					<#else>
						<tr>
							<th colspan="4" style="text-align:center;">没有逾期记录</th>
						</tr>
					</#if>
        		</tr>
       		</tbody>
		</table>
		
	
	
	
	
	
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
	$(function(){
		var status  = ${borrowMap.borrowStatus};
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
	
	
	function audit(status){

		var param = {};
		var checks=$('#auditOpinion').val();
		if(checks==null || checks.length==0){
			alert("初审意见为必填");
			$('#auditOpinion').focus();
			return;
		}
		param['id'] = ${borrowMap.id};
		param['opinion'] = $('#auditOpinion').val();
		param['status'] = status;
		$.post('${context}/admin/borrow/myClaim/fristAudit/updateStatus.do',param,function(resp){
			if(resp.code == 0){
				spark.notify(resp.message);
				window.location='${context}/admin/borrow/myClaimIndex.html';
			} else{
				spark.notify(resp.message,'error');
			}
		});
	}
	
	
</script>
	
</@override>
<@layout name="/admin/layout/main.ftl" />