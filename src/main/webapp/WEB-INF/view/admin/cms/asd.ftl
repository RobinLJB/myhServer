<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="member:index" />
<div class="page-content">
			      <textarea name="detail" style="height:420px;" id="detail"></textarea>

		
</div>
</@override>
<@override name="script">
<script type="text/javascript"  src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
<script type="text/javascript" charset="utf-8" src="${context}/asset/public/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
var ue = UE.getEditor('detail',{
		serverUrl:"${context}/ueditor/dispatch.do",
		imageUrlPrefix:"${context}"
	});
	</script>
</@override>
<@layout name="/admin/layout/main.ftl" />