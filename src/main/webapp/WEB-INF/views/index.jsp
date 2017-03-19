<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="${basePath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>我的个性主页</title>
    <script type='text/javascript' src='resources/js/jquery.min.js'></script>
    <script type='text/javascript' src='resources/js/common.js'></script>
    <script type="text/javascript" src="resources/js/tool.js"></script>
    <script type="text/javascript" src="resources/js/base.js"></script>
    <script type="text/javascript" src="resources/js/index.js"></script>
    <script type="text/javascript" src="resources/js/jquery.dropdown.js"></script>
    <link href="resources/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/dropdown.css" rel="stylesheet" type="text/css" media="screen, projection"/>
</head>

<body background="http://1.su.bdimg.com/skin/12.jpg"
      style="background-position: center 0;  background-repeat: no-repeat;  background-attachment: fixed;  background-size: cover;">
<div id="page-wrap">
    <ul class="dropdown">
        <li><a href="#">工具</a>
            <ul class="sub_menu">
                <li><a title="火车票" href="http://huoche.kuxun.cn/">火车票</a></li>
                <li><a title="开源中国在线工具" href="http://www.ostools.net/">在线工具</a></li>
                <li><a title="163邮箱" href="http://mail.163.com/">163邮箱</a></li>
            </ul>
        </li>

        <li><a href="#">学习</a>
            <ul class="sub_menu">
                <li><a title="" href="http://www.csdn.net/">csdn</a></li>
                <li><a title="" href="http://se.csai.cn/">管理设计</a></li>
                <li><a title="" href="http://www.oschina.net/">开源社区</a></li>
                <li><a title="" href="http://www.java2s.com/">程序代码</a></li>
                <li><a title="" href="http://www.w3school.com.cn/">3school</a></li>
                <li><a title="css+js" href="http://www.cool80.com/">酷8</a></li>
            </ul>
        </li>
        <li><a href="#">API</a>
            <ul class="sub_menu">
                <li><a title="J2EE_API" href="http://localhost:8000/api/J2EE_API/index.html">J2EE_API</a></li>
                <li><a title="J2SE_API" href="http://localhost:8000/api/J2SE_API/index.html">J2SE_API</a></li>
            </ul>
        </li>
        <li><a href="#">娱乐</a>
            <ul class="sub_menu">
                <li><a title="" href="http://www.renren.com/">人 人 网</a></li>
                <li><a href="http://www.tom365.com">tom365</a></li>
            </ul>
        </li>
        <li><a href="#">生活</a>
            <ul class="sub_menu">
                <li><a href="http://www.360buy.com/">京东商城</a></li>
                <li><a href="http://www.taobao.com/">淘宝商城</a></li>
                <li><a href="http://www.vancl.com/">凡客诚品</a></li>
                <li><a href="se7en/index.html">控制台</a></li>
            </ul>
        </li>
        <li><a href="#">阅读</a>
            <ul class="sub_menu">
                <li><a href="http://xianguo.com/news">鲜果</a></li>
                <li><a href="http://www.xinli001.com/">心理网</a></li>
                <li><a href="http://www.apple4.cn/">惜墨</a></li>
                <li><a href="http://www.timetimetime.net/">阅读时间</a></li>
            </ul>
        </li>
        <li><a href="#">工作</a>
            <ul class="sub_menu">
                <li><a href="https://worktile.com/dashboard">WorkTitle</a></li>
                <li><a href="http://115.28.144.253:8090/secure/Dashboard.jspa">Bug系统</a></li>
            </ul>
        </li>
    </ul>
</div>

<div class="background">
    <div id="search">
        <form action="http://www.baidu.com/s" method="get" id="form"
              onsubmit=" return dataNotNullValidateForm('value')">
            <table class="searchTable" border="0">
                <tr>
                    <td><input name="q" type="text" class="searchInput" id="value"
                               onkeydown="return SubmitKeyClick(this,event)"/></td>
                </tr>
                <tr>
                    <td>
                        <input id="Baidu" name="type" class="searchBtn" type="button" value="Baidu"
                               onclick="search('form','value','baidu')"/>
                        <input id="Google" name="type" class="searchBtn" type="button" value="Google"
                               onclick="search('form','value','google')"/>
                    </td>
                </tr>

            </table>
        </form>
    </div>
</div>
<div id="toolbar">
    <img class="login" title="用户登入" src="resources/images/23.png"/>
    <a href="http://sae.sina.com.cn/" target="_blank"><img class="login1" title="Sina App Engine"
                                                           src="resources/images/23.png"/></a>
    <a href="login" target="_blank"><img class="login1" title="Sina App Engine" src="resources/images/23.png"/></a>
</div>

<div id="login">
    <form action="/login">
        <h2><img src="resources/images/IDR_CLOSE_DIALOG_H.png" alt="" class="close"/>用户登入</h2>

        <div class="user">帐 号：<input type="text" name="user_mail" class="text"/></div>
        <div class="pass">密 码：<input type="password" name="user_password" class="text"/></div>
        <div class="button"><input type="submit" class="submit" value="登入"/> <input type="hidden" name="type"
                                                                                    value="login"/></div>
        <div class="other"><a href="">注册新用户</a> | <a href="">忘记密码？</a></div>
    </form>
</div>

<div id="tip">
    <ul>
        <li id="t2"><span>XXMMM</span><span><a class="tipclose"></a></span></li>
        <li id="t1"><span>XXMMM</span><span><a class="tipclose"></a></span></li>
        <li id="t11"><span>XXMMM</span><span><a class="tipclose"></a></span></li>
        <li id="t12"><span>XXMMM</span><span><a class="tipclose"></a></span></li>
        <li id="t22"><span>XXMMM</span><span><a class="tipclose"></a></span></li>
    </ul>
</div>

<div id="screen"></div>

</body>
</html>
