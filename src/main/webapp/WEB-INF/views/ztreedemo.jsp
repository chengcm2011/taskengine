<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<SCRIPT type="text/javascript">
	// 异步加载setting
	var setting = {
		async: {
			enable: true,
			url: "/management/authority/tree.do",
			autoParam: ["id"]
		},
		data: {
			simpleData: {
				enable: true,
				idKey: 'id',
				pIdKey: 'pId',
				rootPID: 0
			}
		},
		callback: {
			onRightClick: OnRightClick
		}
	};
	
	var zTree, rMenu;
	$(document).ready(function(){
		$.fn.zTree.init($("#authorityTree"), setting);
		zTree = $.fn.zTree.getZTreeObj("authorityTree");
		rMenu = $("#rMenu");
	});
	
	// 右键菜单开始
	function OnRightClick(event, treeId, treeNode) {
		if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
			zTree.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY);
		} else if (treeNode && !treeNode.noR) {
			zTree.selectNode(treeNode);
			showRMenu("node", event.clientX, event.clientY);
		}
	}
	
	function showRMenu(type, x, y) {
		$("#rMenu ul").show();
		if (type=="root") {
			$("#m_del").hide();
			$("#m_edit").hide();
			$("#m_view").hide();
		} else {
			$("#m_del").show();
			$("#m_edit").show();
			$("#m_view").show();
		}
		rMenu.css({"top":y-130+"px", "left":x-190+"px", "visibility":"visible"});
		$("body").bind("mousedown", onBodyMouseDown);
	}
	
	function hideRMenu() {
		if (rMenu) rMenu.css({"visibility": "hidden"});
		$("body").unbind("mousedown", onBodyMouseDown);
	}
	
	function onBodyMouseDown(event){
		if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
			rMenu.css({"visibility" : "hidden"});
		}
	}
	// 右键菜单结束
	
	//增删改查操作
	function addTreeNode() {
		hideRMenu();
		var href = $("#add").attr("href");
		if (zTree.getSelectedNodes()[0]) {
			$("#add").attr("href", href+zTree.getSelectedNodes()[0].id);
		} else {
			$("#add").attr("href", href+0);
		}
		$("#add").click();
		$("#add").attr("href", href);
	}
	
	function editTreeNode() {
		hideRMenu();
		if (zTree.getSelectedNodes()[0]) {
			var href = $("#edit").attr("href");
			$("#edit").attr("href", href+zTree.getSelectedNodes()[0].id);
			$("#edit").click();
			$("#edit").attr("href",href);
		} else {
			zTree.addNodes(null, newNode);
		}
	}
	
	function delTreeNode() {
		hideRMenu();
		if (zTree.getSelectedNodes()[0]) {
			var href = $("#delete").attr("href");
			$("#delete").attr("href", href+zTree.getSelectedNodes()[0].id);
			$("#delete").click();
			$("#delete").attr("href", href);
		} else {
			zTree.addNodes(null, newNode);
		}
	}
	
	function viewTreeNode() {
		hideRMenu();
		if (zTree.getSelectedNodes()[0]) {
			var href = $("#view").attr("href");
			$("#view").attr("href", href+zTree.getSelectedNodes()[0].id);
			$("#view").click();
			$("#view").attr("href", href);
		} else {
			zTree.addNodes(null, newNode);
		}
	}
</SCRIPT>
<style type="text/css">
	div#rMenu {
		position:absolute; 
		visibility:hidden; 
		top:0; 
		background-color:#fff;
		text-align:left;padding: 2px;
		border:1px solid #ddd;
	}
	div#rMenu ul li{
		margin: 1px 0;
		padding: 3px 5px;
		cursor: pointer;
		list-style: none outside none;
		background-color: #fff;
	}
</style>

<div layoutH="60px" >
	<ul id="authorityTree" class="ztree" style="margin-bottom:150px;"></ul>
</div>

<div id="rMenu">
	<ul>
		<li id="m_add" onclick="addTreeNode();">增加权限</li>
		<li id="m_edit" onclick="editTreeNode(true);">修改</li>
		<li id="m_del" onclick="delTreeNode();">删除</li>
		<li id="m_view" onclick="viewTreeNode();">查看详情</li>
	</ul>
</div>
<a title="增加权限" style="display:none" id="add" target="navTab" href="/management/authority/addPage?parent_id=">增加权限</a>
<a title="修改权限信息" style="display:none" id="edit" target="navTab" href="/management/authority/updatePage?id_authority=">修改</a>
<a title="删除权限" style="display:none" id="delete" target="ajaxTodo" href="/management/authority/del?id_authority=">删除</a>
<a title="查看权限信息" style="display:none" id="view" target="navTab" href="/management/authority/detail.do?id_authority=">查看</a>
