<@override name="head">
	<link href="${context}/asset/public/css/jquery.Jcrop.min.css" rel="stylesheet" type="text/css">
	<link href="${context}/asset/public/plugins/layer-v2.4/skin/layer.css" rel="stylesheet" type="text/css">

</@override>

<@override name="body">

	<input id="content" name="contents" >
	<button id="submit">提交</button>

</@override>
<@override name="script">
	<script type="text/javascript"  src="${context}/asset/public/plugins/layer-v2.4/layer.js"></script>
	<script type="text/javascript" src="${context}/asset/public/plugins/echarts.min.js"></script>
	<script type="text/javascript" src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
	<script type="text/javascript">
		
		
$('#submit').on('click', function() { 
debugger
      var content=$('#content').val();
      
      if(content==null || content==""){
      	alert('请输入内容');
      	return;
      }
      $.ajax({
			url:'${context}/ucenter/adviseSubmit.do',
			type:'post',
			data:{content:content},
			success:function(data){
				if(data.code == 0){
					alert('提交成功');
				}else{
					alert('提交失败');
				}
			}
		});
});     
	</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />