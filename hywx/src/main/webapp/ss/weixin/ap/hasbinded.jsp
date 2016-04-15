<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1, maximum-scale=1">
    <title>已绑定</title>
    <link rel="stylesheet" type="text/css" href="../../../mobile/static/css/main.css"/>

</head>
<body>
<div id="layout-wrap" >
	<div class="layout-content">
		<div class="nosupply-describe" style="font-size:20px;"><p style="height:50px;"></p>您已经绑定过了手机号!</div>
		<div class="mobilearea"><img src="../../../mobile/static/images/mobile.png"/></div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
	setTimeout(function(){
	
		WeixinJSBridge.call('closeWindow');
	
	},3000)
</script>