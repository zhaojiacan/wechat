<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0">
	
	
	<title>H+ 后台主题UI框架 - 登录</title>
	<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
	<meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
	
	<link rel="shortcut icon" href="favicon.ico">
	<link href="css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
	<link href="css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
	
	<link href="css/animate.min.css" rel="stylesheet">
	<link href="css/style.min862f.css?v=4.1.0" rel="stylesheet">
	<!--[if lt IE 9]>
	    <meta http-equiv="refresh" content="0;ie.html" />
	    <![endif]-->
	<script>
		if (window.top !== window.self) {
			window.top.location = window.location;
		}
	</script>
</head>

<body class="gray-bg">

	<div class="middle-box text-center loginscreen  animated fadeInDown">
		<div>
			<div>

				<h1 class="logo-name"><a href="#"><img src="img/ace.png"/></a></h1>

			</div>
			<h3>欢迎使用方策科技管理后台</h3>
			<form class="m-t" role="form" action="login.do" method="post">
				<div class="form-group">
					<input type="text" class="form-control" name="username" placeholder="用户名" required="">
				</div>
				<div class="form-group">
					<input type="password" class="form-control" name="password" placeholder="密码" required="">
				</div>
				<div class="form-group">
					<input type="password" class="form-control" id="captcha" name="captcha" placeholder="验证码" required="" style="width:100px; float:left;">
					 <img src="code/captcha-image.do" onclick="this.src='code/captcha-image.do?d='+new Date().getTime()" class="code" alt="换一张" />
          				<a href="javascript:void(0);" onclick="$('.code').attr('src','code/captcha-image.do?d='+new Date())" title="换一张">换一张</a>
				</div>
				<button type="submit" class="btn btn-primary block full-width m-b">登 录</button>


				<p class="text-muted text-center">
					<a href="login.html#"><small>忘记密码了？</small></a> | <a href="register.html">注册一个新账号</a>
				</p>

			</form>
		</div>
	</div>
	<script src="js/jquery.min.js?v=2.1.4"></script>
	<script src="js/bootstrap.min.js?v=3.3.6"></script>
	<script type="text/javascript" src="" charset="UTF-8"></script>
</body>
</html>
