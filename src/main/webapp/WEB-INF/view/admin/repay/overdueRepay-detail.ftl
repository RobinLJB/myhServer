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
<input type="hidden" id="MENU_ACTIVE_ID" value="borrow:overdue" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">借款管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">借款列表</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">手动全额还款</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>

	<div class="alert alert-danger" style="margin-top:20px;">
		<p>已经逾期</p>
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
					<td>
						${member.mobilePhone!}
					</td>
					<th>注册时间</th>
					<td>
						${member.createTime!}
					</td>
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

                </tr>
			</tbody>
		</table>
		
		<table class="table detail-info">
			<thead>
				<th colspan="4">借款人认证信息</th>
			</thead>
			<tbody>
				<tr>
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
				</tr>
				<#--<tr>
					<!--<th>芝麻信用</th>
					<td>${auditMap.alipay_score!}</td>
					<th>认证时间</th>
					<td>${auditMap.alipay_audit_time!}</td>-->
					<#--<th>人脸认证</th>
					<td>
						<#if auditMap.face_status =="1">
							<span>通过</span>
						<#elseif auditMap.face_status =="0">
							<span>未通过</span>
						</#if>
					</td>
					<th></th>
					<td></td>
				</tr> 
				<tr>
					<th>手机使用天数</th>
					<td>${auditMap.phone_use_month!}</td>
					<th>认证时间</th>
					<td>${auditMap.phone_audit_time!}</td>
				</tr>-->
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
        			<th>还款序号</th>
          			<th>还款总额</th>
					<th>剩余本金</th>
					<th>操作人员</th>
					<th>还款时间</th>
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
							<th colspan="4">没有部分还款记录</th>
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
        			<th>逾期编号</th>
          			<th>剩余本金</th>
					<th>逾期天数</th>
					<th>逾期费用</th>
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
				<th colspan="4">逾期信息</th>
			</thead>
			<tbody>
				
				<tbody>
					<tr class="active">
						<th>逾期天数</th>
						<td><#if overdueMap??>${overdueMap.overDays!0}天</#if></td>
						<th>逾期费用</th>
						<td><#if overdueMap??>${overdueMap.overdueFee!0}元</#if></td>
					</tr>
					
					<tr>
					<th>应还总额</th>
					<td>
						<input type="text" readonly value="<#if overdueMap??>${overdueMap.total!0}元</#if>" id="countamount"/>
					</td>
					<th>实际还款总额</th>
					<td>
						<input type="text" value="<#if overdueMap??>${overdueMap.realRepayAmount!0}元</#if>" placeholder="请输入还款金额" id="realamount"/>
					</td>
					
				</tr>
				
				
					<tr>
					<th>
						手动还款备注
					</th>
					<td colspan="3">
						<textarea id="auditOpinion" class="form-control" placeholder="请输入还款备注"></textarea>
					</td>
					</tr>
			</tbody>
		</table>
			
		<#if borrowMap.borrowStatus=="9">
			<button class="btn btn-danger" type="button" onclick="audit(10)">全额还款</button>
		<#else>
			<div class="alert alert-info" style="margin-top:20px;">
				<p>已经还完</p>
			</div>
		</#if>
	
	
	
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
	
	
	
	function audit(status){
		var cmsg=status==10?'手动还款':'';
    	if(confirm('确定要'+cmsg+'?')){
			var param = {};
			var checks=$('#auditOpinion').val();
			var countamount=$('#countamount').val();
			var realamount=$('#realamount').val();
			if(checks==null || checks.length==0){
				alert("还款备注为必填");
				$('#auditOpinion').focus();
				return;
			}
			if(isNaN(realamount)){
				alert("请输入正确的数字");
				$('#realamount').focus();
				return;
			}
			if(realamount<=0){
				alert("请输入大于0的数字");
				$('#realamount').focus();
				return;
			}
			param['id'] = ${borrowMap.id};
			param['opinion'] = $('#auditOpinion').val();
			param['status'] = status;
			param['realamount'] = realamount;
			$.post('${context}/admin/repay/manalOverdueRepay.do',param,function(resp){
				if(resp.code == 0){
					spark.notify(resp.message);
				} else{
					spark.notify(resp.message,'error');
				}
			});
		}
	}
	
	
	
	$('#audit').click(function(){
		var bid=$("#bid").val();
		/*var bankid=$("#bankid").val();*/
		
		var benjin=$("input[name=benjin]:checked").val();
		var borrowDay=$("input[name=borrowDay]:checked").val();
		var borrowStatus=$("input[name=borrowStatus]:checked").val();
		alert(bid+"----"+status);
		var $btn = $(this).button('loading');
		$.ajax({
			url:'${context}/admin/borrow/secondAudit/updateStatus.do',
			type:'post',
			data:{bid:bid,benjin:benjin,borrowDate:borrowDay,status:borrowStatus/*,bankid:bankid*/},
			success:function(data){
				if(data.code == 0){
					spark.notify('操作成功','success');
					location.href="${context}/admin/repay/repayComplete.html";
				}else{
					spark.notify('操作失败','error');
				}
				
			}
		});
	});
</script>
	
</@override>
<@layout name="/admin/layout/main.ftl" />
