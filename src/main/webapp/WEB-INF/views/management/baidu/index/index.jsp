<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<form id="pagerForm" method="post" action="management/baidu/index/index">
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
		<form rel="pagerForm" onsubmit="return navTabSearch(this);" class=" pageForm required-validate" action="management/baidu/index/index" method="post">
		    <div class="searchBar">
		      <ul class="searchContent">
				  <li>
					  <label>域名：</label>
					  <input type="text" name="siteurl" value="${siteurl}" class="textInput required"/>
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
		<li><a class="add" href="management/baidu/index/edit" target="dialog"><span>增加</span></a></li>
        <li class="line">line</li>
		<c:if test="${ not empty siteurl}">
		<li><a class="edit" href="management/baidu/index/doexport" target="dwzExport"><span>导出</span></a></li>
		</c:if>
		<%--<li class="line">line</li>--%>
		<%--<li><a class="edit" href="management/task/paramkey/index?pk={pk}" target="navTab"><span>任务插件参数管理</span></a></li>--%>
	</ul>
</div>

<div class="pageContent">
    <table class="table" width="100%" layoutH="165" nowrapTD="false">
      <thead>
	      <tr>
	        <th width="">序号</th>
			  <th width="">域名</th>
			<th width="">关键词</th>
			<th width="">百度指数</th>
			<th width="">百度移动端指数</th>
			<th width="">360指数</th>
			  <th width="">竞争力</th>
			<th width="">查询时间</th>
	      </tr>
      </thead>
      <tbody>
	      <c:if test="${not empty pageVO.data }">
	        <c:forEach var="item" items="${pageVO.data }" varStatus="sta">
	          <tr target="siteurl" rel="${item.siteurl }">
	            <td>${(pageVO.pageNum-1)*pageVO.pageSize+sta.count}</td>
				  <td>${item.siteurl }</td>
				  <td>${item.keyword }</td>
	            <td>${item.allindex }</td>
	            <td>${item.mobileindex }</td>
	            <td>${item.so360index }</td>
				  <td>${item.competitive}</td>
	            <td>${item.ts }</td>
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
