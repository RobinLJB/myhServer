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
<input type="hidden" id="MENU_ACTIVE_ID" value="distribution:index" />
<input type="hidden" id="borrowId" value="${borrowMap.id}" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">逾期管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">分配逾期</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">分配详情</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	
	
	
	<table class="table detail-info">
			<thead>
				<th colspan="4">借款信息</th>
			</thead>
			<tbody>
				<tr class="active">
					<th>借款金额</th>
					<td>${borrowMap.benJin!}元</td>
					<th>借款周期</th>
					<td>${borrowMap.borrowDate!}天</td>
				</tr>
				
				<tr class="active">
					<th>真实姓名</th>
					<td>${member.realName!}</td>
					<th>身份证号</th>
					<td>${member.identNo!}</td>
				</tr>
				
				<tr class="active">
					<th>手机号</th>
					<td>${member.mobilePhone!}</td>
					<th>会员年龄</th>
					<td><#if infoMap??> ${infoMap.age!} </#if></td>
				</tr>
				
				<tr class="active">
					<th>会员性别</th>
					<td><#if infoMap??> ${infoMap.sex!} </#if></td>
					<th>所属地区</th>
					<td><#if infoMap??> ${infoMap.area!} </#if></td>
				</tr>
				<tr>
					<#--<th>手机通话详单</th>
					<td><#if auditMap?? && auditMap.phone_status=='1' ><a href="${context}/admin/member/phoneTalkDetail/${member.id?c}.html">查看通话详单</a><#else>没有生成通话详单</#if></td>-->
					<th>手机报告</th>
					<#--<td><#if member?? && member.callStatus=='1'><a href="${context}/admin/member/callDetail/${member.id?c}.html">查看运营商信息</a><#else>没有运营商信息</#if></td>-->
					<td><#if member.taskId??><a href="https://portal.shujumohe.com/nolayout/customerReport/${member.taskId}" target="_blank">查看运营商信息</a><#else>没有运营商信息</#if></td>
					<th>通讯录</th>
					<td><a href="${context}/admin/member/${member.id}.html?displayStatus=4">查看通讯录</a></td>
				</tr>
				<tr class="active">
					<th>续期天数</th>
					<td>${borrowMap.addBorrowDay!}</td>
					<th>约定还款时间</th>
					<td>${borrowMap.appointmentTime!}</td>
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
				<th colspan="6">逾期详情</th>
			</thead>
			<tbody>
				
				<tbody>
					<tr class="active">
						<th>逾期开始时间</th>
						<td>${overdueMap.beginTime!}</td>
						<th>逾期天数</th>
						<td>${overdueMap.overDays!}天</td>
						<th>逾期费用</th>
						<td>${overdueMap.overdueFee!}元</td>
					</tr>
					
			</tbody>
		</table>
		
	
		<#if adminList??>
		<div class="form-group">
		    <label for="type" class="col-sm-2 control-label">选择催收人员</label>
		    <div class="col-sm-3">
		       <select class="form-control" name="adminId" id="adminId">
		       		<#list adminList as lists>
		       			<option value="${lists.id}">${lists.username!}</option>
		       		</#list>
			   </select>
		    </div>
		</div>

		
		<div class="form-group">
		  	<div class="col-sm-12">
		  		 <button class="btn btn-success" type="button" onclick="audit(8)">保存分配信息</button>
		  	</div>
		</div>
		</#if>
		
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
	
	
	
	function audit(status){
		var cmsg=status==8?'保存分配信息':'';
    	if(confirm('确定要'+cmsg+'?')){
			var param = {};
			param['borrowId'] =$('#borrowId').val();
			param['adminId'] =$('option:selected').val();
			var adminId=$('option:selected').val();
			var flag=1;
			
			if(adminId==1){
				if( confirm('是否指派给系统管理员?')){
					flag=1;
				}else{
					flag=0;
				}
			}
			if(flag==1){
				$.post('${context}/admin/overdue/distributionUpdate.do',param,function(resp){ 
					if(resp.code == 0){
						window.location="${context}/admin/overdue/distributed.html";
					} else{
						spark.notify(resp.message,'error');
					}
				});
			}
		}
		
	}
	
	
	
	
</script>
	
</@override>
<@layout name="/admin/layout/main.ftl" />
