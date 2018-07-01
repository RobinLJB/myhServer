<@override name="head">
<style>
	.row{
		    margin-top: 20px;
	}
</style>
</@override>
<@override name="body">
	
	<div id="page-content-wrapper">
		<div class="ci-title">
            <div class="ci-title-inner">
                <h2>自动投标</h2>
                <b class="line"></b>
            </div>
        </div>
      <div style="    margin: 0 auto;
    margin-left: 107px;
    width: 461px;
    text-align: left;">
      	<div class="row">
      	年化收益： <input type="text" />-<input type="text" />%
      	</div>
      	<div class="row">
      	借款期限： <input type="text" />-<input type="text" />个月
      	</div>
      	<div class="row">
      	每笔投资： <input type="text" />-<input type="text" />元
      	</div>
      	<div class="row">
      	保留余额：<input type="text" />元
      	</div>
      	<div class="row">
      	项目担保：<input type="checkbox" />需要担保
      	</div>
      	<div class="row" style="text-align: center;">
      	<div class="btn btn-default btn-sm">保存</div>
      	</div>
      </div>
	</div>

</@override>
<@override name="script">
	<script type="text/javascript">  
$(document).ready(function() {
  		$('.myinvest').addClass('active').parents().show();
  		$('.submeun-8 a').addClass('active').parents().show();
  		
  	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />