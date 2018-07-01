<#assign context="${rc.contextPath}">
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9"> <![endif]-->
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title>${webname!}-后台</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport"/>
<meta http-equiv="keywords" content="${SEO_KEY!}">
<meta http-equiv="description" content="${SEO_DESC!}">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta content="width=device-width, initial-scale=1" name="viewport"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${context}/asset/public/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link href="${context}/asset/public/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${context}/asset/public/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="${context}/asset/public/plugins/pnotify/pnotify.custom.min.css" rel="stylesheet" type="text/css"/>
<link href="${context}/asset/public/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL PLUGIN STYLES -->
<!-- BEGIN PAGE STYLES -->
<link href="${context}/asset/admin/pages/css/tasks.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="${context}/asset/admin/css/components.css" rel="stylesheet" type="text/css"/>
<link href="${context}/asset/admin/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="${context}/asset/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link href="${context}/asset/admin/layout/css/themes/darkblue.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="${context}/asset/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="${context}/favicon.ico" />
<@block name="head"></@block>
</head>
<!-- END HEAD -->
<body class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo page-container-bg-solid page-sidebar-open">
<div class="page-header navbar navbar-fixed-top">
	<!-- BEGIN HEADER INNER -->
	<div class="page-header-inner">
		<!-- BEGIN LOGO -->
		<div class="page-logo">
			<a href="index.html">
				<img style="margin-top: 0;
    height: 42px;" src="${context}/asset/admin/img/spark-logo-s.png" alt="logo" class="logo-default"/>
			</a>
			<div class="menu-toggler sidebar-toggler hide">秒易花
			</div>
		</div>
		<!-- END LOGO -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		<!-- BEGIN TOP NAVIGATION MENU -->
		<div class="top-menu">
			<ul class="nav navbar-nav pull-right">
				<#--
				
				<li class="dropdown dropdown-extended dropdown-notification" id="header_notification_bar">
					<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<i class="icon-bell"></i>
					<span class="badge badge-default">
					7 </span>
					</a>
					<ul class="dropdown-menu">
						<li class="external">
							<h3><span class="bold">12 pending</span> notifications</h3>
							<a href="extra_profile.html">view all</a>
						</li>
						<li>
							<ul class="dropdown-menu-list scroller" style="height: 250px;" data-handle-color="#637283">
								<li>
									<a href="javascript:;">
									<span class="time">just now</span>
									<span class="details">
									<span class="label label-sm label-icon label-success">
									<i class="fa fa-plus"></i>
									</span>
									New user registered. </span>
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</li>
				
				<li class="dropdown dropdown-extended dropdown-inbox" id="header_inbox_bar">
					<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<i class="icon-envelope-open"></i>
					<span class="badge badge-default">
					4 </span>
					</a>
					<ul class="dropdown-menu">
						<li class="external">
							<h3>You have <span class="bold">7 New</span> Messages</h3>
							<a href="page_inbox.html">view all</a>
						</li>
						<li>
							<ul class="dropdown-menu-list scroller" style="height: 275px;" data-handle-color="#637283">
								<li>
									<a href="inbox.html?a=view">
									<span class="photo">
									<img src="../../../asset/admin/layout/img/avatar2.jpg" class="img-circle" alt="">
									</span>
									<span class="subject">
									<span class="from">
									Lisa Wong </span>
									<span class="time">Just Now </span>
									</span>
									<span class="message">
									Vivamus sed auctor nibh congue nibh. auctor nibh auctor nibh... </span>
									</a>
								</li>
								<li>
									<a href="inbox.html?a=view">
									<span class="photo">
									<img src="../../../asset/admin/layout/img/avatar3.jpg" class="img-circle" alt="">
									</span>
									<span class="subject">
									<span class="from">
									Richard Doe </span>
									<span class="time">46 mins </span>
									</span>
									<span class="message">
									Vivamus sed congue nibh auctor nibh congue nibh. auctor nibh auctor nibh... </span>
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</li>
				
				<li class="dropdown dropdown-extended dropdown-tasks" id="header_task_bar">
					<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<i class="icon-calendar"></i>
					<span class="badge badge-default">
					3 </span>
					</a>
					<ul class="dropdown-menu extended tasks">
						<li class="external">
							<h3>You have <span class="bold">12 pending</span> tasks</h3>
							<a href="page_todo.html">view all</a>
						</li>
						<li>
							<ul class="dropdown-menu-list scroller" style="height: 275px;" data-handle-color="#637283">
								<li>
									<a href="javascript:;">
									<span class="task">
									<span class="desc">New release v1.2 </span>
									<span class="percent">30%</span>
									</span>
									<span class="progress">
									<span style="width: 40%;" class="progress-bar progress-bar-success" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"><span class="sr-only">40% Complete</span></span>
									</span>
									</a>
								</li>
								<li>
									<a href="javascript:;">
									<span class="task">
									<span class="desc">Application deployment</span>
									<span class="percent">65%</span>
									</span>
									<span class="progress">
									<span style="width: 65%;" class="progress-bar progress-bar-danger" aria-valuenow="65" aria-valuemin="0" aria-valuemax="100"><span class="sr-only">65% Complete</span></span>
									</span>
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</li>
				-->
				
				<li class="dropdown dropdown-user">
					<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<img alt="" class="img-circle" src="${context}/asset/admin/layout/img/avatar3_small.jpg"/>
					<span class="username username-hide-on-mobile">${Session.ADMIN_MEMBER.userName!''}</span>
					<i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu dropdown-menu-default">
						<li>
							<a href="${context}/admin/profile/password.html">
							<i class="icon-user"></i>修改密码</a>
						</li>
						<!--
						<li>
							<a href="inbox.html">
							<i class="icon-envelope-open"></i>收件箱<span class="badge badge-danger">
							3 </span>
							</a>
						</li>-->
						<li class="divider">
						</li>
						<li>
							<a href="${context}/admin/logout.html">
							<i class="icon-key"></i>退出</a>
						</li>
					</ul>
				</li>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
		</div>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END HEADER INNER -->
</div>
<!-- END HEADER -->
<div class="clearfix">
</div>
<div class="page-container">
	<div class="page-sidebar-wrapper">
		<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
		<!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
		<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->
			<ul class="page-sidebar-menu " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
				<li class="sidebar-toggler-wrapper" style="margin-bottom: 10px;">
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler">
					</div>
					<!-- END SIDEBAR TOGGLER BUTTON -->
				</li>
				<#list ADMIN_MENU as menu>
				<li data-menu-id="${menu.name!}">
					<#if menu.subMenu?? && (menu.subMenu?size > 0)>
					<a href="javascript:;">
						<i class="${menu.icon!'icon-folder'}"></i>
						<span class="title">${menu.title!}</span>
						<#if menu.subMenu?? && (menu.subMenu?size > 0)>
						<span class="arrow "></span>
						</#if>
					</a>
					<ul class="sub-menu">
						<#list menu.subMenu as sm>
						<li data-menu-id="${sm.name!}">
							<a href="${context}${sm.url!}.html">
							<!--<i class="icon-bar-chart"></i>-->
							${sm.title}</a>
						</li>
						</#list>
					</ul>
					<#else>
					<a href="<#if menu.url?? && menu.url != ''>${context}${menu.url!}.html<#else>javascript:;</#if>">
						<i class="${menu.icon!'icon-link'}"></i>
						<span class="title">${menu.title}</span>
					</a>
					</#if>
				</li>
				</#list>
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>
	</div>
	<div class="page-content-wrapper">
		<@block name="body"></@block>
	</div>
</div>
<div class="page-footer">
	<div class="page-footer-inner">
		2014-2016 &copy; <a href="http://www.xhp2p.cn/" title="鑫火网贷" target="_blank">SPARK TECH</a>
	</div>
	<div class="scroll-to-top">
		<i class="icon-arrow-up"></i>
	</div>
</div>
<!--mask-->
<style type="text/css">
		#fancybox-overlay{position:fixed;top:0;left:0;bottom:0;right:0;background:#000;z-index:1100;display:none}
		#fancybox-overlay.active{opacity:.3;display:block}
		.bigImg{position:absolute;top:50px;left:0;margin:0;padding:20px;z-index:1101;display:none}
		.bigImg.active{opacity:1;width:628px;height:620px;top:250px;left:427.5px;display:block}
		#fancybox-img{width:100%;height:100%;padding:0;margin:0;border:none;outline:0;line-height:0;vertical-align:top}
</style>
<div class="bigImg">
	<img id="fancybox-img" src="" />
</div>
<div id="fancybox-overlay" class="" onclick="closeMask()"></div>
<!--mask over-->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${context}/asset/public/plugins/respond.min.js"></script>
<script src="${context}/asset/public/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="${context}/asset/public/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/bootstrap-dialog/js/bootstrap-dialog.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/pnotify/pnotify.custom.min.js" type="text/javascript"></script>
<script src="${context}/asset/admin/layout/js/spark.js" type="text/javascript"></script>
<script src="${context}/asset/admin/layout/js/layout.js" type="text/javascript"></script>
<script>
	$(function(){
		spark.init('${context}');
		var menuId = $('#MENU_ACTIVE_ID').val();
		if(menuId){
			var li = $('.page-sidebar-menu li[data-menu-id="'+menuId+'"]');
			li.addClass('active');
			if(li.parent().hasClass('sub-menu')){
				li.parents('li').addClass('open active').find('>a').append('<span class="selected"></span>');
			}
			else{
				li.addClass('open').find('>a').append('<span class="selected"></span>');
			}
		}
		Layout.init();
	});
	
//	mask
		$('table img').on('click',function(){
			imgSrc=this.src;
			$('#fancybox-img').attr('src',imgSrc);
			showMask();
		})
		 var showMask=function(){
		 	$('#fancybox-overlay,.bigImg').addClass('active');
		 }
		 var closeMask=function(){
		 	$('#fancybox-overlay,.bigImg').removeClass('active');
		 }
//	mask over
</script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE SCRIPT-->
<@block name="script"></@block>
<!-- END PAGE SCRIPT -->
</body>
</html>