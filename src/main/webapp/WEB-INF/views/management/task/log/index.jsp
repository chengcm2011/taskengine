<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<form id="pagerForm" method="post" action="management/task/log/index">
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
		<form rel="pagerForm" onsubmit="return navTabSearch(this);" class=" pageForm required-validate" action="management/task/log/index" method="post">
		    <div class="searchBar">
		      <ul class="searchContent">
				  <li>
					  <label>任务名称：</label>
					  <input type="hidden" id="orgLookup.pk_taskdeploy" name="pk_taskdeploy"  value="${key}"/>
					  <input type="text" name="deployname"  value="${item}" style="float: left"  readonly="readonly" class="required" id="orgLookup.orgNamepk_taskdeploy" />
					  <a class="btnLook" href="management/uicomponent/ref/index?reftype=taskdeploy&itemkey=pk_taskdeploy" rel="pk_taskdeploy" lookupGroup="orgLookup">查找带回</a>
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
<div class="pageContent">
    <table class="table" width="100%" layoutH="138" nowrapTD="false">
      <thead>
	      <tr>
	        <th width="">序号</th>
			<th width="">任务名称</th>
			<th width="">执行结果</th>
			<th width="">执行时间</th>
			<th width="">执行耗时</th>
			<th width="">执行服务器</th>
	      </tr>
      </thead>
      <tbody>
	      <c:if test="${not empty pageVO.data }">
	        <c:forEach var="item" items="${pageVO.data }" varStatus="sta">
	          <tr target="pk" rel="${item.pk_taskplugin }">
	            <td>${(pageVO.pageNum-1)*pageVO.pageSize+sta.count}</td>
	            <td>${item.taskname }</td>
	            <td>${item.returnstr }</td>
	            <td>${item.vdef1 }</td>
				<td>${item.runtime }</td>
	            <td>${item.runserver }</td>
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
