<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<form id="pagerForm" method="post" action="management/task/paramvalue/index?pk=${pk}">
  <input type="hidden" name="pageNum" value="${pageVO.pageNum}" />
  <input type="hidden" name="numPerPage" value="${pageVO.pageSize}" />
</form>

<div class="tabs">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li><a href="javascript:;"><span>任务信息</span></a></li>
			</ul>
		</div>
	</div>
	<div class="tabsContent">
		<form rel="pagerForm" onsubmit="return navTabSearch(this);" class=" pageForm required-validate" action="management/task/paramkey/index?pk=${pk}" method="post">
		    <div class="searchBar">
		      <ul class="searchContent">
				  <li>
					  <label>任务名称：</label>
					  <input type="text"  class="textInput " value="${item.taskname}" readonly="readonly"/>
				  </li>
				  <li>
					  <label>任务描述：</label>
					  <input type="text"  class="textInput " value="${item.taskdescription}" readonly="readonly"/>
				  </li>
				  <li>
					  <label>任务执行策略：</label>
					  <input type="text"  class="textInput " value="${item.triggerstr}" readonly="readonly"/>
				  </li>
		      </ul>
		    </div>
		</form>
	</div>
	<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
	</div>
</div>

<div class="panelBar">
	<ul class="toolBar">
		<li><a class="edit" href="management/task/paramvalue/edit?pk_taskdeploy=${pk}&pk={pk}" target="dialog"><span>编辑</span></a></li>
	</ul>
</div>

<div class="pageContent">
    <table class="table" width="100%" layoutH="165" nowrapTD="false">
      <thead>
	      <tr>
	        <th width="">序号</th>
			<th width="">参数名称</th>
			<th width="">参数编码</th>
			<th width="">参数值</th>
			<th width="">修改时间</th>
	      </tr>
      </thead>
      <tbody>
	      <c:if test="${not empty pageVO.data }">
	        <c:forEach var="item" items="${pageVO.data }" varStatus="sta">
	          <tr target="pk" rel="${item.pk_taskparamkey };${item.pk_taskdeploy}">
	            <td>${(pageVO.pageNum-1)*pageVO.pageSize+sta.count}</td>
	            <td>${item.paramname }</td>
	            <td>${item.paramkey }</td>
				<td>${item.paramvalue }</td>
	            <td>${item.ts }</td>
	          </tr>
	        </c:forEach>
	      </c:if>
      </tbody>
    </table>

  <%--<div class="panelBar" >--%>
    <%--<div class="pages">--%>
      <%--<span>显示</span>--%>
      <%--<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">--%>
        <%--<option value="${pageVO.pageSize }">${pageVO.pageSize }</option>--%>
      <%--</select>--%>
      <%--<span>条，共${pageVO.totalCount }条</span>--%>
    <%--</div>--%>
    <%--<div class="pagination" targetType="navTab" totalCount="${pageVO.totalCount }" numPerPage="${pageVO.pageSize }" pageNumShown="10" currentPage="${pageVO.pageNum}"></div>--%>
  <%--</div>--%>

</div>
