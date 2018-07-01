<@override name="head">
<link href="${context}/asset/ucenter/css/home.css" rel="stylesheet">
</@override>
<@override name="body">
	<!-- 会员中心主页 --> 
	<div class="alert alert-success">
		<h4>您已成功绑定银行卡</h4>
	</div>
	<div class="form-container">
		<table class="table table-bordered">
			<thead>
				<tr>
					<td>开户人</td>
					<td>账号</td>
					<td>开户银行</td>
					<td>开户支行</td>
				</tr>
			</thead>
			<tbody>
				<#if bankcard??>
				<tr>
					<td>${bankcard.cardUserName}</td>
					<td>${bankcard.cardNo}</td>
					<td>${bankcard.bankName}</td>
					<td>${bankcard.branchBankName}</td>
				</tr>
				</#if>
			</tbody>
		</table>
	</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/dataTable.js"></script>
<script>
	$('.submeun-3').addClass('selected');
</script>
<script>
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-finance.ftl" />