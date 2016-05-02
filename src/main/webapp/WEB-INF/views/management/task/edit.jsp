<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<div class="pageContent">
	<form method="post" action="management/task/save" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" name="pk_taskplugin" value="${item.pk_taskplugin}">
			<p>
				<label>任务插件名称：</label>
				<input type="text"  name="pluginname" value="${item.pluginname}" class="required"  />
			</p>
			<p>
				<label>任务插件处理类：</label>
				<textarea name="pluginclass"   class="required textInput valid">${item.pluginclass}</textarea>
			</p>
			<p>
				<label>任务插件描述：</label>
				<textarea name="plugindescription" class=" required textInput valid" >${item.plugindescription}</textarea>
			</p>
			<p style="display: none">
				<label>任务插件类型：</label>
				<input type="text" name="plugintype"  value="1" class="required textInput valid">
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
