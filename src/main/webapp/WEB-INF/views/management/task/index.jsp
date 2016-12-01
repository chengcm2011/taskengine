<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<form id="pagerForm" method="post" action="management/task/index">
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
		<form rel="pagerForm" onsubmit="return navTabSearch(this);" class=" pageForm required-validate" action="management/task/index" method="post">
		    <div class="searchBar">
		      <ul class="searchContent">
				  <li>
					  <label>插件名称：</label>
					  <input type="text" name="pluginname" class="textInput required" value="${item}"/>
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
		<li><a class="add" href="management/task/edit" target="dialog"><span>增加</span></a></li>
       <li class="line">line</li>
		<li><a class="edit" href="management/task/edit?pk={pk}" target="dialog"><span>编辑</span></a></li>
		<li class="line">line</li>
		<li><a class="delete" href="management/task/del?pk={pk}" target="ajaxTodo" title="确定要删除吗？" rel="run"><span>删除</span></a></li>
		<li class="line">line</li>
		<li><a class="edit" href="management/task/paramkey/index?pk={pk}" target="navTab"><span>任务插件参数管理</span></a></li>
	</ul>
</div>

<div class="pageContent">
    <table class="table" width="100%" layoutH="165" nowrapTD="false">
      <thead>
	      <tr>
	        <th width="">序号</th>
			<th width="">任务插件名称</th>
			<th width="">任务插件处理类</th>
			<th width="">任务插件描述</th>
			<%--<th width="">任务插件类型</th>--%>
	      </tr>
      </thead>
      <tbody>
	      <c:if test="${not empty pageVO.data }">
	        <c:forEach var="item" items="${pageVO.data }" varStatus="sta">
				<tr target="pk" rel="${item.pkTaskplugin }">
	            <td>${(pageVO.pageNum-1)*pageVO.pageSize+sta.count}</td>
	            <td>${item.pluginname }</td>
	            <td>${item.pluginclass }</td>
	            <td>${item.plugindescription }</td>
	            <%--<td>${item.plugintype }</td>--%>
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
