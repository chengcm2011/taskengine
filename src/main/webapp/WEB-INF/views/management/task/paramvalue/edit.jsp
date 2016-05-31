<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<div class="pageContent">
	<form method="post" action="management/task/paramvalue/save" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" name="pk" value="${pk}">
			<p>
				<label>参数名称：</label>
				<input type="text"  name="paramname" value="${item.paramname}" class="required" readonly="readonly" />
			</p>
			<p>
				<label>参数编码：</label>
				<input type="text"  name="paramkey" value="${item.paramkey}" class="required" readonly="readonly" />
			</p>
			<p>
				<label>参数值：</label>
				<input type="text"  name="paramvalue" value="${item.paramvalue}" class="required"  />
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
