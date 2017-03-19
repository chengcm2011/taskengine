<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String nav = request.getParameter("nav") != null ? request.getParameter("nav") : "";
    String username = request.getParameter("username") != null ? request.getParameter("username") : "";
    int message = request.getParameter("message") != null && !("".equals(request.getParameter("message"))) ? Integer.parseInt(request.getParameter("message")) : 0;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />
<html>
<head>
    <base href="${basePath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404_打不开月光宝盒啦</title>
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <link href="${htzConfig.youpai_static_url}resources/${htzConfig.prdVersion}/front/css/base.css" rel="stylesheet"
          type="text/css"/>

    <style type="text/css">
        a {
            color: #000;
        }

        a:hover {
            color: #dbbc6a;
        }

        .msgWrap {
            width: 716px;
            height: 375px;
            margin: 0 auto;
        }

        .infoWrap {
            margin: 0px auto;
            padding-top: 10px;
            width: 280px;
        }

        .btn {
            border: 1px solid #dbbc6a;
            border-radius: 5px;
            line-height: 30px;
            display: inline-block;
            text-align: center;
            padding: 0px 15px;
            margin-right: 10px;
        }

        .btn img {
            margin-right: 5px;
        }
    </style>
</head>
<body>
<!--[if IE 6]>
<script language="JavaScript">
    function correctPNG() // correctly handle PNG transparency in Win IE 5.5 & 6.
    {
        for (var j = 0; j < document.images.length; j++) {
            var img = document.images[j]
            var imgName = img.src.toUpperCase()
            if (imgName.substring(imgName.length - 3, imgName.length) == "PNG") {
                var imgID = (img.id) ? "id='" + img.id + "' " : ""
                var imgClass = (img.className) ? "class='" + img.className + "' " : ""
                var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' "
                var imgStyle = "display:inline-block;" + img.style.cssText
                if (img.align == "left") imgStyle = "float:left;" + imgStyle
                if (img.align == "right") imgStyle = "float:right;" + imgStyle
                if (img.parentElement.href) imgStyle = "cursor:hand;" + imgStyle
                var strNewHTML = "<span " + imgID + imgClass + imgTitle
                        + " style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";"
                        + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
                        + "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>"
                img.outerHTML = strNewHTML
                j = j - 1
            }
        }
    }
    window.attachEvent("onload", correctPNG);
</script>
<![endif]-->
<!--head-->
<!--/head-->

<!--main-->
<div class="main ">
    <div class="inner">
        <div class="msgWrap clearfix">
            <img src="${htzConfig.youpai_static_url}resources/${htzConfig.prdVersion}/front/imgs/common/404.png"
                 class="error_img"/>

        </div>
        <div class="infoWrap clearfix">
            <a href="javascript:history.go(-1);" class="btn"><img
                    src="${htzConfig.youpai_static_url}resources/${htzConfig.prdVersion}/front/imgs/back.png">返回之前页面</a>
            <a href="${basePath}" class="btn">去首页<img
                    src="${htzConfig.youpai_static_url}resources/${htzConfig.prdVersion}/front/imgs/goarrow.png"
                    style="margin-left: 5px;vertical-align: middle; "></a>
        </div>
    </div>

</div>
<!--/main-->

<!--footer-->


<script src="${htzConfig.youpai_static_url}public/lib/jquery-1.8.2.min.js"></script>
<!--/footer-->
</body>
</html>
