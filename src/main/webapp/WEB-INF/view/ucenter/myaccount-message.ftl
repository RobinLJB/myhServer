<@override name="head">
</@override>
<@override name="body">
<div class="r_main">
		<div class="tabtil">
			<ul>
			     <li class="on">系统消息</li>
          		 <li><a href="${context}/ucenter/messageset.html">通知设置</a></li>
			</ul>
        </div>
		<div class="box">
		



<div class="boxmain2">
	<div class="srh" id="srh">
	<input class="btn btn-sm btn-danger" onclick="delSys();" value="删除" type="button" id="delSys">
	<input class="btn btn-sm btn-default" onclick="readedSys();" value="标记为已读" type="button" id="readedSys">
	<input class="btn btn-sm btn-default" onclick="unReadSys();" value="标记为未读" type="button" id="unReadSys">
	<a href="javascript:void(0);" class="btn btn-sm btn-default" onclick="showUnReadSys();">未读邮件</a>
	</div>
	<span id="sys_mail_list"><div class="biaoge" id="biaoge">
<input type="hidden" id="curPage" value="1">
	<table class="table table-bordered">
		<thead>
			<tr>
				<th style="text-align: center;"><input type="checkbox" name="checkbox" id="checkbox" onclick="checkAll_Sys(this)"></th>
				<th>标记</th>
				<th>发件人</th>
				<th colspan="6">标题</th>
				<th>发送时间</th>
			</tr>
		</thead>
		<tbody><tr><td colspan="10" align="center">暂无信息</td></tr>
	</tbody></table>
	<div class="page">
	<p>
	</p>
	</div> 
</div>
</span>
	<span id="show_mail"></span> 
</div>
		</div>		
		
		

</div>
</@override>
<@override name="script">
<script>
	$(document).ready(function() {
	$('.myaccount').addClass('active').parents().show();
	$('.submeun-7 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />