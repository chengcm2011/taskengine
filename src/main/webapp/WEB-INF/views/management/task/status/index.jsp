<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<form id="pagerForm" method="post" action="management/task/status/index">
  <input type="hidden" name="pageNum" value="${pageVO.pageNum}" />
  <input type="hidden" name="numPerPage" value="${pageVO.pageSize}" />
</form>
<div class="panelBar">
	<ul class="toolBar">
     	<li><a class="edit" href="management/task/status/pause?pk={pk}" target="ajaxTodo" title="确定要暂停吗？"  rel="pause"><span>暂停</span></a></li>
		<li class="line">line</li>
		<li><a class="edit" href="management/task/status/resume?pk={pk}" target="ajaxTodo" title="确定要恢复吗？"  rel="resume"><span>恢复</span></a></li>
		<li class="line">line</li>
		<li><a class="edit" href="management/task/status/trigger?pk={pk}" target="ajaxTodo"  title="确定要运行一次吗？" rel="trigger" ><span>运行一次</span></a></li>
	</ul>
</div>

<div class="pageContent">
    <table class="table" width="100%" layoutH="80" nowrapTD="false">
      <thead>
	      <tr>
	        <th width="">序号</th>
			<th width="">任务名称</th>
			<th width="">任务cron表达式</th>
			<th width="">下次执行时间</th>
			<th width="">运行状态</th>
	      </tr>
      </thead>
      <tbody>
	      <c:if test="${not empty data }">
	        <c:forEach var="item" items="${data }" varStatus="sta">
	          <tr target="pk" rel="${item.jobcode }">
	            <td>${sta.count}</td>
	            <td title="${item.jobclass}">${item.jobname }</td>
	            <td>${item.cronExpression }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${item.NextFireTime }"></fmt:formatDate></td>
				<td>${item.TriggerStateName }</td>
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
