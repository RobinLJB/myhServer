<@override name="head">
<style>
	.table tbody .odd{height: 38px;}
	.form-group .form-control {
    margin-top: 0px;
}
.box .boxmain2 .input-sm{
	width:50px;
}
.dataTables_length label{
	font-size:8px;
}
</style>
</@override>
<@override name="body">
 <div class="r_main">
     <div class="ci-title">
            <div class="ci-title-inner">
                <h2>邀请好友</h2>
                <b class="line"></b>
            </div>
        </div>
    <div class="box">
	<div class="boxmain2">
        <p class="tips" style="font-size: 13px;color: black;"><font style="font-weight: bold;">温馨提示：</font>
        <br/>您可以复制下面的链接向好友推荐，可以通过QQ、微信、Email、Msn等工具传递信息，邀请的好友通过下面地址,点击按钮进入我们网站，进行注册登录。
        </p>
		<p class="tips">邀请链接：<span id="yq_address_input">${SITE_HOST}/ucenter/register.html?param=${userId!}</span><a  id="yq_address_btn" class="btn btn-sm btn-success" style="margin-left:20px;">复制</a></p>
	<div class="bdsharebuttonbox" style="margin-left: 100px;">
	<span style="float: left;font-weight: bold;    line-height: 35px;" >分享到：&nbsp;</span>
	<a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
	<a href="#" class="bds_tqf" data-cmd="tqf" title="分享到腾讯朋友"></a>
	<a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ好友"></a>
	<a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
	</div>
 
	<script>window._bd_share_config={"common":{bdText : '欢迎登录${SEO_TITLE}',	bdDesc : '欢迎登录${SEO_DESC}!!!',	bdUrl : '${SITE_HOST}/ucenter/register.html?param=${userId!}', },"share":{"bdSize":"24"},"image":{"viewList":["weixin","tqf","sqq","qzone"],"viewText":"分享到：","viewSize":"32"},"selectShare":{"bdSelectMiniList":["weixin","tqf","sqq","qzone"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
 
	<div class="boxmain2">
        
        
		<div class="biaoge" id="hyyq">
		   <table id="table" class="table table-bordered">
			<thead>
				<tr>
					<th>邀请的好友</th>
					<th>联系方式</th>
					<th>真实姓名</th>
					<th>关系</th>
				</tr>
			</thead>
			</table>		 
			
		</div>
		</div>
    </div>
	</div>
	<div id="userfrends" class="box" style="display:none;">
		<!--关注好友部分-->
	</div>
    </div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-zclip/jquery.zclip.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		$('#yq_address_btn').zclip({
			path: "${context}/asset/public/plugins/jquery-zclip/ZeroClipboard.swf",
			copy:function(){
				return '${SITE_HOST}/ucenter/register.html?param=${userId!}';
			},
			afterCopy:function(){
				alert('复制成功');
			}
		});
	})
</script>
<script>
	$(document).ready(function() {
		$('.myaccount').addClass('active').parents().show();
		$('.submeun-15 a').addClass('active').parents().show();
	})
</script>
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script> 
<script>
	$(document).ready(function() {
		var table = spark.dataTable('#table','${context}/ucenter/friend/list.json',{
		 	"columns": [
				{"data":"musername" },
				{"data":"mphone" },
				{"data":"mrealname"},
				{"data":"relation"},
			],
			"columnDefs":[
				{
					"targets":[3],
					"render":function(data,type,full){
						if(data==1){
							return "直接推荐";
						}else{
							return "间接推荐";
						}
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#table');
			},
		});
		$('#bt_searchaa').click(function(){
			$('[data-toggle="table-search"]').each(function(){
				var index = parseInt($(this).attr('data-column'));
				table.columns(index).search($(this).val());
			})
			table.draw();
		});
	} );
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />