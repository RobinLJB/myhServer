<@override name="head">
</@override>
<@override name="body">

    <div class="r_main">
      <div class="tabtil">
        <ul><li class="on">我的红包</li>
        </ul>
        </div>
      <div class="box">
        <div class="boxmain2">
			<p class="tips" style="font-size: 13px;color: black;"><font style="font-weight: bold;">使用说明：</font>
				<br/>当单笔投资额度满足条件后，红包金额会自动发放至账户。投资仅限于月标。红包有效期内无任何投资，红包将自动失效。
			</p>
        <div class="biaoge">
			<table class="table table-bordered table-condensed ">
				<tr>
				<th>编号</th>
				<th>面值</th>
				<th>最低投资金额</th>
				<th>类型</th>
				<th>获取时间</th>
				<th>过期时间</th>
				
				<th>状态</th>
				</tr>
		
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
				<tr>
					<td align="center">001</td>
					<td align="center">50</td>
					<td align="center">100</td>
					<td align="center">
						注册奖励
					</td>
					<td align="center">2016-6-10</td>
					<td align="center">2016-7-10</td>
					<td align="center">
						待解锁    	
					</td>
				</tr>
			  
		</table> 
    </div>
    </div>
</div>
    </div>
</@override>
<@override name="script">
<script>
	$(document).ready(function() {
	$('.myaccount').addClass('active').parents().show();
	$('.submeun-6 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />