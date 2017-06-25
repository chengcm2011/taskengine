<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<div class="pageContent">
    <form method="post" action="management/task/deploy/save" class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <input type="hidden" name="pkTaskdeploy" value="${item.pkTaskdeploy}">

            <p>
                <label>任务插件名称：</label>
                <input type="hidden" id="orgLookup.pkTaskplugin" name="pkTaskplugin" value="${item.pkTaskplugin}"/>
                <input type="text" value="${item.vdef1}" style="float: left" readonly="readonly" class="required"
                       id="orgLookup.orgNamepkTaskplugin"/>
                <a class="btnLook" href="management/uicomponent/ref/index?reftype=taskplugin&itemkey=pkTaskplugin"
                   rel="pkTaskplugin" lookupGroup="orgLookup">查找带回</a>
            </p>

            <p>
                <label>任务类型：</label>
                <select name="taskType">
                    <%--<option value="fix_simple" >单机</option>--%>
                    <option value="SIMPLE" selected="selected">弹性简单</option>
                        <%--<option value="DATAFLOW">弹性流式</option>--%>
                </select>
            </p>

            <p style="height: auto">
                <label>任务名称：</label>
                <input type="text" name="taskName" value="${item.taskName}" class="required textInput">
            </p>

            <p style="height: auto">
                <label>任务描述：</label>
                <textarea name="taskDescription" class="required textInput valid"
                          style="width: 240px">${item.taskDescription}</textarea>
            </p>

            <p style="height: auto">
                <label>任务cron表达式：</label>
                <input type="text" name="cronExpression" value="${item.cronExpression}" class="textInput required"
                       style="float: left"/>
            </p>

            <p style="height: auto">
                <label>是否运行：</label>
                <select name="runnable">

                    <c:if test="${item.runnable=='Y'}">
                        <option value="Y" selected="selected">运行中</option>
                        <option value="N">已停止</option>
                    </c:if>
                    <c:if test="${item.runnable!='Y'}">
                        <option value="Y">运行中</option>
                        <option value="N" selected="selected">已停止</option>
                    </c:if>
                </select>
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">保存</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>
