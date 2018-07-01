<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="borrow:forFristAudit" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">借款列表</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">所有借款</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		借款列表
	</h3>
	<form class="form-horizontal" id="articleform" style="margin-top:20px;">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-2 control-label">佣金总额</label>
				<div class="col-sm-5">
				  <input type="text" class="form-control" name="title" id="inputEmail3" readonly value="${member.commisionSum!0}">
				</div>
			</div>
			
			
			<div class="form-group">
				<label class="control-label col-sm-2">冻结佣金</label>
				<div class="col-md-5">
					<input type="text" class="form-control" name="title" id="inputEmail3" readonly value="${member.freezeCommision!0}">
				</div>
			</div>
			
		
			<div class="form-group">
				<label class="col-sm-2 control-label">本次提现金额</label>
				<div class="col-sm-3">
					<input type="text" name="withdrawSum" class="form-control">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-2 control-label">提现备注</label>
				<div class="col-sm-3">
					<input type="text" name="withdrawReview" class="form-control">
				</div>
			</div>
			
			<#--<div class="form-group">
		    	<label for="type" class="col-sm-2 control-label">选择银行卡</label>
		   		<div class="col-sm-3">
		       		<select class="form-control" name="cardNo" id="cardNo">
						&lt;#&ndash;<#if list??>
							<#list list as lists>
								<option value="${lists.cardNo!}">${lists.cardNo!}</option>
							</#list>
						</#if>&ndash;&gt;
                        <option value="${bank.cardNo!}">${bank.cardNo!}</option>
			    	</select>
		    	</div>
			</div>-->
		
		  	<div class="form-group">
		  		<div class="col-sm-10 col-sm-offset-2">
		  			 <button id="withdraw" type="button" class="btn btn-primary" data-loading-text="正在保存..." autocomplete="off">提现</button>
		  		</div>
		  	</div>
	</form>
	<table id="memberTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>提现编号</th>
                <th>操作人员</th>
                <th>提现金额</th>
				<#--<th>提现时间</th>
				<th>提现地点</th>-->
				<th>银行卡号</th>
				<th>提现备注</th>
				
            </tr>
        </thead>
    </table>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		var table = spark.dataTable('#memberTable','${context}/admin/extension/member/detail/list/${member.id}.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "adminid" },
				{ "data": "amount" },
				/*{ "data": "time" },
				{ "data": "ip" },*/
				{ "data": "cardNo" },
				{ "data": "withdrawReview" },
			],
			"columnDefs":[
				
				
				
				
				
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
<script>
$('#withdraw').click(function(){
		var withdrawSum=$("input[name=withdrawSum]").val();
		var withdrawReview=$("input[name=withdrawReview]").val();
		var cardNo=$("#cardNo option:selected").val();
		var mid=${member.id};
		if(isNaN(withdrawSum)){
			alert("请输入正确的数字");
			$("input[name=withdrawSum]").focus();
			return;
		}
		
		if(withdrawSum<=0){
			alert("请输入大于0的数字");
			$("input[name=withdrawSum]").focus();
			return;
		}
		if(withdrawReview==null || withdrawReview.length==0){
			alert("提现备注为必填");
			$("input[name=withdrawReview]").focus();
			return;
		}
		
		if(cardNo==null || cardNo.length==0){
			alert("请选择银行卡");
			return;
		}
		
		$.ajax({
			url:'${context}/admin/funds/member/withdraw.do',
			type:'post',
			data:{withdrawSum:withdrawSum,withdrawReview:withdrawReview,cardNo:cardNo,mid:mid},
			success:function(data){
				if(data.code == 0){
					spark.notify('操作成功','success');
					location.href="${context}/admin/funds/member/detail/${member.id}.html";
				}else{
					spark.notify('操作失败','error');
				}
				setTimeout(function(){
					$btn.button('reset');
				},3000);
			}
		});
	});
	
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />





















