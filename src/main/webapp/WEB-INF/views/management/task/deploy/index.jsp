<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<form id="pagerForm" method="post" action="management/task/deploy/index#taskdeploy">
  <input type="hidden" name="pageNum" value="${pageVO.pageNum}" />
  <input type="hidden" name="numPerPage" value="${pageVO.pageSize}" />
</form>

<div class="tabs">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li><a href="javascript:;"><span>查询</span></a></li>
			</ul>
		</div>
	</div>
	<div class="tabsContent">
		<form rel="pagerForm" onsubmit="return navTabSearch(this);" class=" pageForm required-validate" action="management/task/deploy/index#taskdeploy" method="post">
		    <div class="searchBar">
		      <ul class="searchContent">
				  <li>
					  <label>插件名称：</label>
					  <input type="hidden" id="orgLookup.pkTaskplugin" name="pkTaskplugin"/>
					  <input type="text" style="float: left" readonly="readonly" class="required"
							 id="orgLookup.orgNamepkTaskplugin"/>
					  <a class="btnLook" href="management/uicomponent/ref/index?reftype=taskplugin&itemkey=pkTaskplugin"
						 rel="pkTaskplugin" lookupGroup="orgLookup">查找带回</a>
				  </li>
				  <li>
					  <label>任务名称：</label>
					  <input type="text" name="taskname" class="textInput" />
				  </li>
		      </ul>
		      <div class="subBar">
		        <ul>
		          <li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
		        </ul>
		      </div>
		    </div>
		</form>
	</div>
	<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
	</div>
</div>

<div class="panelBar">
	<ul class="toolBar">
       <li><a class="add" href="management/task/deploy/edit" target="dialog" rel="addplugin"><span>添加</span></a></li>
       <li class="line">line</li>
		<li><a class="edit" href="management/task/deploy/edit?pk={pk}" target="dialog"  rel="updateplugin"><span>修改</span></a></li>
		<li class="line">line</li>
		<li><a class="delete" href="management/task/deploy/del?pk={pk}" target="ajaxTodo" title="确定要删除吗？" rel="del"><span>删除</span></a></li>
		<li class="line">line</li>
		<li><a class="delete" href="management/task/deploy/disable?pk={pk}" target="ajaxTodo" title="确定要禁用吗？" rel="disable"><span>禁用</span></a></li>
		<li class="line">line</li>
		<li><a class="edit" href="management/task/deploy/enable?pk={pk}" target="ajaxTodo" title="确定要启用吗？"  rel="enable"><span>启用</span></a></li>
		<li class="line">line</li>
		<%--<li><a class="edit" href="management/task/deploy/resume?pk={pk}" target="ajaxTodo" title="确定要恢复吗？"  rel="resume"><span>恢复</span></a></li>--%>
		<%--<li class="line">line</li>--%>
		<li><a class="edit" href="management/task/paramvalue/index?pk={pk}" target="navTab"  rel="paramvalueindex"><span>任务参数值管理</span></a></li>
	</ul>
</div>

<div class="pageContent">
    <table class="table" width="100%" layoutH="165" nowrapTD="false">
      <thead>
	      <tr>
	        <th width="">序号</th>
			<th width="">任务插件名称</th>
			<th width="">任务名称</th>
			<th width="">任务描述</th>
			<th width="">任务cron表达式</th>
			<th width="">运行状态</th>
	      </tr>
      </thead>
      <tbody>
	      <c:if test="${not empty pageVO.data }">
	        <c:forEach var="item" items="${pageVO.data }" varStatus="sta">
				<tr target="pk" rel="${item.pkTaskdeploy }">
	            <td>${(pageVO.pageNum-1)*pageVO.pageSize+sta.count}</td>
	            <td>${item.pluginname }</td>
	            <td>${item.taskname }</td>
				  <td>${item.taskdescription }</td>
				  <td>${item.triggerstr }</td>
				  <td><c:if test="${'Y' eq item.runnable }">已启用</c:if><c:if test="${'N' eq item.runnable }">已禁用</c:if></td>
	          </tr>
	        </c:forEach>
	      </c:if>
      </tbody>
    </table>

  <div class="panelBar" >
    <div class="pages">
      <span>显示</span>
      <select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
        <option value="${pageVO.pageSize }">${pageVO.pageSize }</option>
      </select>
      <span>条，共${pageVO.totalCount }条</span>
    </div>
    <div class="pagination" targetType="navTab" totalCount="${pageVO.totalCount }" numPerPage="${pageVO.pageSize }" pageNumShown="10" currentPage="${pageVO.pageNum}"></div>
  </div>

</div>
