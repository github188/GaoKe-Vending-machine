<%--
  Created by IntelliJ IDEA.
  User: jws
  Date: 2017/4/23
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh" class="no-js">
<head>
    <title>管理员管理</title>
    <link rel="stylesheet" type="text/css" href="/css/leftorder.css">


</head>
<body id="bg">
<div class="container">
    <div class="leftsidebar_box">
        <div class="line"></div>
        <dl class="system_log">
            <dt onClick="changeImage()">系统记录<img src="/img/left/select_xl01.png"></dt>
            <dd class="first_dd"><a href="#">消费记录</a></dd>
            <dd><a href="#">金额统计</a></dd>
            <dd><a href="#">消费分布</a></dd>
            <dd><a href="#">操作记录</a></dd>
        </dl>
        <dl class="custom">
            <dt onClick="changeImage()">客户管理<img src="/img/left/select_xl01.png"></dt>
            <dd class="first_dd"><a href="#">会员管理</a></dd>
            <dd><a href="#">查看会员</a></dd>
            <dd><a href="#">会员交易</a></dd>
            <dd><a href="#">会员中心</a></dd>
        </dl>
        <dl class="channel">
            <dt>渠道管理<img src="/img/left/select_xl01.png"></dt>
            <dd class="first_dd"><a href="#">渠道主页</a></dd>
            <dd><a href="#">渠道标准管理</a></dd>
            <dd><a href="#">系统通知</a></dd>
            <dd><a href="#">渠道商管理</a></dd>
            <dd><a href="#">渠道商链接</a></dd>
        </dl>
        <dl class="cloud">
            <dt>大数据云平台<img src="/img/left/select_xl01.png"></dt>
            <dd class="first_dd"><a href="#">平台运营商管理</a></dd>
        </dl>
        <dl class="syetem_management">
            <dt>系统管理<img src="/img/left/select_xl01.png"></dt>
            <dd class="first_dd"><a href="#">后台用户管理</a></dd>
            <dd><a href="#">角色管理</a></dd>
            <dd><a href="#">员工管理</a></dd>
            <dd><a href="#">栏目管理</a></dd>
            <dd><a href="#">点位管理</a></dd>
            <dd><a href="#">商城模板管理</a></dd>
            <dd><a href="#">微功能管理</a></dd>
            <dd><a href="#">修改用户密码</a></dd>
        </dl>
        <dl class="statistics">
            <dt>统计分析<img src="/img/left/select_xl01.png"></dt>
            <dd class="first_dd"><a href="#">客户统计</a></dd>
        </dl>
    </div>
</div>
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript">
    $(".leftsidebar_box dt").css({"background-color":"#3992d0"});
    $(".leftsidebar_box dt img").attr("src","/img/left/select_xl01.png");
    $(function(){
        $(".leftsidebar_box dd").hide();
        $(".leftsidebar_box dt").click(function(){
            $(".leftsidebar_box dt").css({"background-color":"#3992d0"})
            $(this).css({"background-color": "#317eb4"});
            $(this).parent().find('dd').removeClass("menu_chioce");
            $(".leftsidebar_box dt img").attr("src","/img/left/select_xl01.png");
            $(this).parent().find('img').attr("src","/img/left/select_xl.png");
            $(".menu_chioce").slideUp();
            $(this).parent().find('dd').slideToggle();
            $(this).parent().find('dd').addClass("menu_chioce");
        });
    })
</script>
</body>
</html>
