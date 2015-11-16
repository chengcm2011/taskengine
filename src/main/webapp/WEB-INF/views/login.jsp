<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.inc.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登入页面</title>
<script language="javascript"
	src="<c:url value='/resources/js/reg_lgn.js'/>"></script>
<link href="<c:url value='/resources/css/reg_lgn.css'/>"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="back">
		<!-- <div id="header"> <div class="first"> <a href="index">回首页</a> </div> </div> -->
		<div id="logincontent">
			<div id="loginform">
				<form id="login" action="loginaction" method="post"
					onsubmit="">
					<div id="hidden">
						<input type="hidden" name="action" value="login" /><input
							type="hidden" name="type" value="login" />
					</div>
					<%-- <div>
						<select class="user_value_select" id="role" name="pk_role">
							<option>选择角色</option>
							<c:forEach items="${data}" var="item" varStatus="sta1">
								<option value="${item.pk_role }">${item.role_name }</option>
							</c:forEach>
						</select>
					</div> --%>
					<div id="error">
						<span id="role_error">角色不能为空！</span>
					</div>
					<div>
						<input onblur="valueCheck(this)" class="reg_input_text"
							type="text" id="username" name="username" placeholder="登入邮箱" />
					</div>
					<div id="error">
						<span id="username_error">用户名不能为空！</span>
					</div>
					<div>
						<input onblur="valueCheck(this)" class="reg_input_text"
							type="password" id="password" name="password" placeholder="密码" />
					</div>
					<div id="error">
						<span id="password_error">密码不能为空！</span>
					</div>
					<%--<div>--%>
						<%--<input  class="reg_input_text"--%>
							   <%--type="text" id="vailcode" name="codevalue" placeholder="验证码" />--%>
					<%--</div>--%>
					<%--<div >--%>
						<%--<input type="hidden" name="codetype"value="login"/>--%>
						<%--<img src="/cjmghsjehu?codetype=login" alt="验证码"/>--%>
					<%--</div>--%>
					<div>
						<input class="reg_input_text_button" type="submit" value="登入" />
					</div>
					<div id="error">
						<span> &nbsp;</span>
					</div>
					<!-- <div id="toregister"><a href="register">注册</a>&nbsp; </div>  -->
				</form>
			</div>
		</div>
	</div>
</body>
</html>
