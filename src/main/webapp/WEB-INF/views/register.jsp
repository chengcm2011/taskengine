<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>注册页面</title>
    <script language="javascript" src="<c:url value='/styles/resource/js/reg_lgn.js'/>"></script>
    <link href="<c:url value='/styles/resource/css/reg_lgn.css'/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="back">
    <!-- <div id="header"> <div class="first"> <a href="index">回首页</a> </div> </div> -->
    <div id="content">
        <div id="logo"></div>
        <div id="registerform">
            <form id="register" action="<c:url value='/passport/register'/>" method="post">
                <div id="hidden"><input type="hidden" name="type" value="register"/></div>
                <div><input onblur="valueCheck(this)" class="reg_input_text" type="text" id="username" name="u_mail"
                            placeholder="注册邮箱"/></div>
                <div id="error"><span id="username_error">用户名不能为空！</span> <span id="username_patternerror">格式不正确！</span>
                    <span id="username_patternerror1"> 用户已经存在！</span></div>
                <div><input onblur="valueCheck(this)" class="reg_input_text" type="password" id="password"
                            name="u_password" placeholder="密码"/></div>
                <div id="error"><span id="password_error">密码不能为空！</span><span id="password_rep">密码不一致！</span></div>
                <div><input onblur="valueCheck(this)" class="reg_input_text" type="password" id="cofirmpassword"
                            name="u_def1" placeholder="确认密码"/></div>
                <div id="error"><span id="cofirmpassword_error">确认密码不能为空！</span><span
                        id="cofirmpassword_rep">密码不一致！</span></div>
                <!--<div><input  onblur="valueCheck(this)" class="reg_input_text" type="hidden" id="checkcode" name="checkcode" value="11"  placeholder="验证"  /></div>-->
                <!--<div id="error"><span id="checkcode_error">验证码不能为空！</span> </div>-->
                <!--  <div><img class="img" src="../resource/images/valid.png" width="140" height="35" /></div> -->
                <div><input class="reg_input_text_button" type="submit" value="注册"/></div>
                <div id="error"><span> &nbsp;</span></div>
                <div id="tologin"><a href="login">返回登入</a></div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
