<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<div class="pageContent">
    <form method="post" action="management/task/conf/save" class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <input type="hidden" name="pkTaskConf" value="${item.pkTaskConf}">
            <input type="hidden" name="dr" value="${item.dr}">
            <input type="hidden" name="ts" value="${item.ts}">

            <p>
                <label>注册中心名称：</label>
                <input type="text" name="registryCenterName" class="required textInput valid"
                       value="${item.registryCenterName}" placeholder="测试注册中心"/>
            </p>

            <p style="height: auto">
                <label>注册中心地址：</label>
                <input type="text" name="zkAddressList" class="required textInput" value="${item.zkAddressList}"
                       placeholder="127.0.0.1:2181">
            </p>

            <p style="height: auto">
                <label>命名空间：</label>
                <input type="text" name="namespace" class="required textInput valid" value="${item.namespace}"/>
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
