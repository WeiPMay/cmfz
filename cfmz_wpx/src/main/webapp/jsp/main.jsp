<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>

<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../jqgrid/extend/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="../boot/js/jquery-3.3.1.min.js"></script>
    <script src="../boot/js/bootstrap.3.3.7.min.js"></script>
    <script src="../jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="../jqgrid/extend/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script src="../jqgrid/extend/js/bootstrap-datepicker.js"></script>
    <title>持明法洲后台管理系统</title>

    <style>
        .pc_imgBox{
            display: block;
            height: 400px;
            width: 100%;
            background-size: cover;
            background-position:  center;
            background-repeat: no-repeat;
        }
        .carousel-indicators{
            background: #ccc;
        }
    </style>
</head>
<body>
    <%--标题导航栏--%>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                持明法洲后台管理系统v1.0
            </a>
            <div class="collapse navbar-collapse navbar-right" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a  href="#" class="dropdown-toggle" data-toggle="dropdown">
                            欢迎：<span><font color="red"><shiro:principal></shiro:principal></font></span>
                            <span class="glyphicon glyphicon-user"/>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/logout">
                            退出登录
                            <span class="glyphicon glyphicon-share"></span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <%--页面主体--%>
    <div class="container-fluid" id="myCarousel">
        <div class="col-lg-2">
            <div class="panel-group" id="panelgroup1">
                <shiro:hasPermission name="carousel:add">
                <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title" data-target="#collapse1" data-toggle="collapse" data-parent="#panelgroup1">
                                    <a href="#">轮播图</a>
                                </h4>
                            </div>
                            <div id="collapse1" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <ul class="nav nav-pills nav-stacked">
                                        <li role="presentation"><a href="javascript:$('#contentDiv').load('${pageContext.request.contextPath}/jsp/carousel.jsp')">轮播图管理</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                </shiro:hasPermission>
                <shiro:hasRole name="admin">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title" data-target="#collapse2" data-toggle="collapse" data-parent="#panelgroup1">
                                    <a href="#">专辑</a>
                                </h4>
                            </div>
                            <div id="collapse2" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <ul class="nav nav-pills nav-stacked">
                                        <li role="presentation"><a href="javascript:$('#contentDiv').load('${pageContext.request.contextPath}/jsp/album.jsp')">专辑和章节管理</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>


                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title" data-target="#collapse3" data-toggle="collapse" data-parent="#panelgroup1">
                                <a href="#">文章</a>
                            </h4>
                        </div>
                        <div id="collapse3" class="panel-collapse collapse">
                            <div class="panel-body">
                                <ul class="nav nav-pills nav-stacked">
                                    <li role="presentation"><a href="javascript:$('#contentDiv').load('${pageContext.request.contextPath}/jsp/article.jsp')">文章管理</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </shiro:hasRole>
                <shiro:hasRole name="vip">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title" data-target="#collapse4" data-toggle="collapse" data-parent="#panelgroup1">
                                <a href="#">用户</a>
                            </h4>
                        </div>
                        <div id="collapse4" class="panel-collapse collapse">
                            <div class="panel-body">
                                <ul class="nav nav-pills nav-stacked">
                                    <li role="presentation"><a href="javascript:$('#contentDiv').load('${pageContext.request.contextPath}/jsp/user.jsp')">用户管理</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                </shiro:hasRole>
                </div>
            </div>
        <div class="col-lg-10" id="contentDiv" >

                <div class="jumbotron">
                    <h2>欢迎来到持明法洲后台管理系统</h2>
                </div>

                <div id="img1" class="carousel slide" data-ride="carousel">
                <%--<div class="carousel slide" id="img1" data-ride="carousel>--%>
                    <ol class="carousel-indicators radiou">
                        <li data-target="#img1" data-slide-to="0" class="active"/>
                        <li data-target="#img1" data-slide-to="1" class="active"/>
                        <li data-target="#img1" data-slide-to="2" class="active"/>
                        <li data-target="#img1" data-slide-to="3" class="active"/>
                    </ol>
                    <div class="carousel-inner" role="listbox">
                        <div class="item active">
                            <img src="${pageContext.request.contextPath}/img/1.png" class="img-responsive center-block"/>
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/img/2.png" class="img-responsive center-block"/>
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/img/3.png" class="img-responsive center-block"/>
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/img/4.png" class="img-responsive center-block"/>
                        </div>
                    </div>
                    <a class="left carousel-control" href="#img1" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <!-- 轮播导航，即左右按钮 -->
                    <a class="right carousel-control" href="#img1" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
    <%--页尾--%>
    <nav class="navbar navbar-default navbar-fixed-bottom">
        <div class="container text-center"><br>
            @百知教育 baizhi@zparkhr.com.cn<br><br>
        </div>
    </nav>
</body>
</html>