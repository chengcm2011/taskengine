<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<form id="pagerForm" method="post" action="management/task/status/shardinginfo?pk=${item.pkTaskdeploy}">
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
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>任务名称：</label>
                    <input type="text" style="float: left" readonly="readonly" value="${item.taskName}"/>

                </li>
                <li>
                    <label>执行表达式：</label>
                    <input type="text" name="taskName" class="textInput" value="${item.cronExpression}"/>
                </li>
                <li>
                    <label>任务描述：</label>
                    <input type="text" name="taskName" class="textInput" value="${item.taskDescription}"/>
                </li>
            </ul>
        </div>
    </div>
    <div class="tabsFooter">
        <div class="tabsFooterContent"></div>
    </div>
</div>


<div class="panelBar">
    <ul class="toolBar">
        <%--<li><a class="edit" href="management/task/status/disableServerJob?jobCode=${item}&serverIp={serverIp}" target="ajaxTodo" title="确定要暂停吗？" rel="pause"><span>暂停</span></a></li>--%>
        <%--<li class="line">line</li>--%>
        <%--<li><a class="edit" href="management/task/status/enableServerJob?jobCode=${item}&serverIp={serverIp}" target="ajaxTodo" title="确定要恢复吗？" rel="resume"><span>恢复</span></a></li>--%>
        <li><a class="edit"
               href="management/task/status/disableSharding?jobCode=${item.pkTaskdeploy}&itemindex={itemindex}"
               target="ajaxTodo" title="确定要暂停吗？" rel="pause"><span>暂停</span></a></li>
        <li class="line">line</li>
        <li><a class="edit"
               href="management/task/status/enableSharding?jobCode=${item.pkTaskdeploy}&itemindex={itemindex}"
               target="ajaxTodo" title="确定要恢复吗？" rel="resume"><span>恢复</span></a></li>
    </ul>
</div>

<div class="pageContent">
    <table class="table" width="100%" layoutH="80" nowrapTD="false">
        <thead>
        <tr>
            <th width="">分片序号</th>
            <th width="">服务器ip</th>
            <th width="">运行状态</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${not empty data }">
            <c:forEach var="item" items="${data }" varStatus="sta">
                <tr target="itemindex" rel="${item.item }">
                    <td>${item.item }</td>
                    <td>${item.serverIp }</td>
                    <td>${item.status }</td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>
