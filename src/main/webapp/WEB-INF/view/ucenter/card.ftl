<@override name="head">
<style>
.form-group .col-sm-10{width:60%;}
.form-group input{border-radius: 5px;}
.form-group select{ border-radius: 5px;}
.form-group .form-control{margin-top:0px;}
.form-group .radio-inline{padding-left: 35px;}
.form-border{
	padding: 4px 12px;
    margin: 10px 0;
    font-weight: 500;
    font-size: 20px;
    border-left: 7px solid #fd852f;
}
.control-label .required {
    color: #e02222;
    font-size: 12px;
    padding-left: 2px;
}
</style>
<style>
#imgPreview{
height:100px;
margin-bottom:10px;
}
.btn-upload{
  position:relative;
}
.btn-upload input[type="file"] {
    position: absolute;
    opacity: 0;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
    width: 100%;
    margin: 0;
    padding: 0;
    cursor: pointer;
}
.file-holder img{
  width: 80px;
}

.jccebn{
	    float: left;
    width: 48%;
}
.nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover {
    color: #555;
    line-height: 35px;
    height: 36px;
    text-align: center;
    width: 146px;
    cursor: default;
    background-color: #fff;
    border: 1px solid #ddd;
    border-bottom-color: rgba(253, 133, 47, 0);
}
.nav-tabs>li {
    float: left;
    line-height: 35px;
    height: 36px;
    text-align: center;
    width: 146px;
    /* height: 43px; */
}
.nav>li>a {
    padding: 0!important;
    height: 36px;
    line-height: 36px;
}
.tab-c{
	padding-left: 45px;
}
.tab-c ul li{
	background: url('${context}/asset/front/images/hb.png') no-repeat;
	height: 125px;
	width:330px;
}
.box{
	height: 87px;
	width: 100%;
	display: inline-block;
	color: #FFF787;
	border: none;
}
.je{
	display: inline-block;
	width: 50%;
	text-align: center;
	font-size: 23px;
}
.jes{
	font-family: '微软雅黑';
	font-size:58px;
	
}
.qx{
	display: inline-block;
	width: 48%;
	font-size: 18px;
}
.zt{
	display: inline-block;
	height: 37px;
}
.fe{
	display: inline-block;
}
.xx{
	display: inline-block;
    text-align: center;
    
    width: 100%;
    color: #fff787;
}
</style>
</@override>
<@override name="body">
 <div class="lef_lb">
  	<ul id="myTab" class="nav nav-tabs" style="margin-top: 0px;">
	<li class="active"><a href="#home" data-toggle="tab">
			 红包
		</a>
	</li>
	

	
	
</ul>
</div>
<div class="rig_lb">
<div id="myTabContent" class="tab-content">
	<div class="tab-pane fade in active" id="home">
     <div class="tab-c">
     	<ul>
     		<#if hongbaoList?exists>
			<#list hongbaoList as card>			
			      
     		<li>
     			<div class="box">
     				<div class="je">
     					￥<span class="jes">${card.faceValue!}</span>
     				</div>
     				<div class="qx">
     					<div class="zt">
     						<#if card.status == 1>
									<img src="${context}/asset/front/images/wsy.png" />
								<#elseif card.status == 2>
									<img src="${context}/asset/front/images/ysy.png" />
								<#elseif card.status == 3>
									<img src="${context}/asset/front/images/ygq.png" />								
								</#if>     						
     					</div>
     					<div class="fe">元红包</div>
     				</div>
     			</div>
     			<div class="xx">投资红包  &nbsp;&nbsp;注册赠送</div>
     		</li>
     		</#list> 
		    </#if>
     		
     		
     	</ul>
     </div>
	</div>

	
	</div>
</div>
  
</@override>
<@override name="script">
<script>
	$(document).ready(function() {
		$('.myaccount').addClass('active').parents().show();
		$('.submeun-4 a').addClass('active').parents().show();
		changePage();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />