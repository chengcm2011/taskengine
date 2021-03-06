<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>
<form id="pagerForm" action="demo/database/dwzOrgLookup.html">
    <input type="hidden" name="pageNum" value="1"/>
    <input type="hidden" name="numPerPage" value="${model.numPerPage}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>
    <input type="hidden" name="orderDirection" value="${param.orderDirection}"/>
</form>

<div class="pageHeader">
    <form rel="pagerForm" method="post" action="demo/database/dwzOrgLookup.html"
          onsubmit="return dwzSearch(this, 'dialog');">
        <%--<div class="searchBar">--%>
        <%--<ul class="searchContent">--%>
        <%--<li>--%>
        <%--<label>公司名称:</label>--%>
        <%--<input class="textInput" name="orgName" value="" type="text">--%>
        <%--</li>--%>
        <%--<li>--%>
        <%--<label>公司网址:</label>--%>
        <%--<input class="textInput" name="orgNum" value="" type="text">--%>
        <%--</li>--%>
        <%--</ul>--%>
        <%--<div class="subBar">--%>
        <%--<ul>--%>
        <%--<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>--%>
        <%--</ul>--%>
        <%--</div>--%>
        <%--</div>--%>
    </form>
</div>
<div class="pageContent">

    <table class="table" layoutH="118" targetType="dialog" width="100%">
        <thead>
        <tr>
            <th>任务名称</th>
            <th>任务描述</th>
            <th>任务执行策略</th>
            <th width="80">查找带回</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${data}" var="item">
            <tr>
                <td>${item.taskName}</td>
                <td>${item.taskDescription}</td>
                <td>${item.cronExpression}</td>
                <td>
                    <a class="btnSelect"
                       href="javascript:$.bringBack({${itemkey}:'${item.pkTaskdeploy}', orgName${itemkey}:'${item.taskName}'})"
                       title="查找带回">选择</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="panelBar">
        <div class="pages">
            <span>每页</span>

            <select name="numPerPage" onchange="dwzPageBreak({targetType:'dialog', numPerPage:'10'})">
                <option value="10" selected="selected">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select>
            <span>条，共2条</span>
        </div>
        <div class="pagination" targetType="dialog" totalCount="2" numPerPage="10" pageNumShown="1"
             currentPage="1"></div>
    </div>
</div>