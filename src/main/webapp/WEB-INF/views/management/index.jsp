<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${basePath}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>任务引擎</title>

	<link href="resources/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="resources/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="resources/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
	<link href="resources/themes/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
	<!--[if IE]>
	<link href="resources/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
	<![endif]-->

	<!--[if lte IE 9]>
	<script src="resources/js/dwz/js/speedup.js" type="text/javascript"></script>
	<![endif]-->

	<script src="resources/js/dwz/js/jquery-1.7.2.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/jquery.cookie.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/jquery.validate.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
	<script src="resources/js/dwz/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
	<script src="resources/js/dwz/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
	<script src="resources/js/dwz/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>
	<script src="resources/js/dwz/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

	<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
	<script type="text/javascript" src="resources/js/dwz/chart/raphael.js"></script>
	<script type="text/javascript" src="resources/js/dwz/chart/g.raphael.js"></script>
	<script type="text/javascript" src="resources/js/dwz/chart/g.bar.js"></script>
	<script type="text/javascript" src="resources/js/dwz/chart/g.line.js"></script>
	<script type="text/javascript" src="resources/js/dwz/chart/g.pie.js"></script>
	<script type="text/javascript" src="resources/js/dwz/chart/g.dot.js"></script>

	<script src="resources/js/dwz/js/dwz.core.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.util.date.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.validate.method.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.barDrag.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.drag.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.tree.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.accordion.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.ui.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.theme.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.navTab.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.tab.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.resize.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.dialog.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.cssTable.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.stable.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.taskBar.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.ajax.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.pagination.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.database.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.datepicker.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.effects.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.panel.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.checkbox.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.history.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.combox.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/dwz.print.js" type="text/javascript"></script>
	<script src="resources/js/dwz/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>

	<!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换是下面dwz.regional.zh.js还需要引入)
    <script src="bin/dwz.min.js" type="text/javascript"></script>
    -->
	<script src="resources/js/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

	<script type="text/javascript">
		$(function(){
			DWZ.init("resources/dwz.frag.xml", {
				loginUrl:"loginDialog.jsp", loginTitle:"登录",	// 弹出登录对话框
				statusCode:{ok:200, error:300, timeout:301}, //【可选】
				pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
				debug:false,	// 调试模式 【true|false】
				callback:function(){
					initEnv();
					$("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
				}
			});
		});

	</script>
</head>

<body scroll="no">
<div id="layout">
	<div id="header">
		<div class="headerNav">
			<a class="logo" href="">标志</a>
			<ul class="nav">
			<li><span style="color:white;">${item.user_name }</span></li>
				<li><a href="loginout" style="color:white;">退出</a></li>
			</ul>
			<ul class="themeList" id="themeList">
				<li theme="default"><div class="selected">蓝色</div></li>
			</ul>
		</div>

		<!-- navMenu -->

	</div>

	<div id="leftside">
		<div id="sidebar_s">
			<div class="collapse">
				<div class="toggleCollapse"><div></div></div>
			</div>
		</div>
		<div id="sidebar">
			<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
			<div class="accordion" fillSpace="sidebar">
				<div class="accordionHeader">
					<h2 ><span>Folder</span>任务中心</h2>
				</div>
				<div class="accordionContent">
					<ul class="tree treeFolder">
						<li><a href="management/task/index?rel=taskregister"  target="navTab" rel="taskregister">任务注册</a></li>
						<li><a href="management/task/deploy/index?rel=taskdeploy"  target="navTab" rel="taskdeploy">任务部署</a></li>
						<li><a href="management/task/status/index?rel=taskstatus"  target="navTab" rel="taskdeploy">任务状态</a></li>
						<li><a href="management/task/log/index?rel=tasklog"  target="navTab" rel="tasklog">任务日志</a></li>
					</ul>
				</div>
		    </div>
		</div>
	</div>
	<div id="container">
		<div id="navTab" class="tabsPage">
			<div class="tabsPageHeader">
				<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
					<ul class="navTab-tab">
						<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
					</ul>
				</div>
				<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
				<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
				<div class="tabsMore">more</div>
			</div>
			<ul class="tabsMoreList">
				<li><a href="javascript:;">我的主页</a></li>
			</ul>
			<div class="navTab-panel tabsPageContent layoutBox">
				<div>
					<div class="accountInfo">
						<div class="right">
							<p></p><!-- pattern="yyyy MMMM dd , EEEE" -->
						</div>
						<p><span>Welcome, ${item.user_name} </span></p>
						<p> </p>
					</div>

					<div class="pageFormContent" layoutH="80">
						<p>
							<label>用户名称:</label><span class="unit">${item.user_name}</span>
						</p>
						<p>
							<label>登陆账户:</label><span class="unit">${item.login_name}</span>
						</p>
						<p>
							<label>手机号:</label><span class="unit">${item.phone}</span>
						</p>
						<p>
							<label>QQ:</label><span class="unit">${item.qq}</span>
						</p>
						<p>
							<label>email:</label><span class="unit">${item.email}</span>
						</p>
						<p>
							<label>备注:</label><span class="unit">${item.remark}</span>
						</p>
						<p>
							<label>最后登录时间:</label><span class="unit">${item.last_logintime}</span>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="footer">Copyright &copy; 2015 <a href="http://www.chengyingsheng.com" target="dialog">cheng</a>&nbsp;&nbsp;&nbsp;&nbsp;http://www.chengyingsheng.com</div>
</body>
</html>