<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1, maximum-scale=1">
    <meta http-equiv=refresh content="3;url=/ci/weixin/reg/beginValidateMobile.action?gzhOpenId=${user.gzhOpenid }&userOpenId=${user.openid }">
    <title>未绑定手机</title>
    <link rel="stylesheet" type="text/css" href="../../../mobile/static/css/main.css"/>

</head>
<body>
<div id="layout-wrap" >
		
	
	<div class="layout-content">
		<div class="nosupply-describe"> 未绑定手机, 请先绑定手机后再进行操作。</div>
		<div class="mobilearea"><img src="../../../mobile/static/images/mobile.png"/></div>
		<div class="change-mobile" onclick="window.location.href='/ci/weixin/reg/beginValidateMobile.action?gzhOpenId=${user.gzhOpenid }&userOpenId=${user.openid }'">绑定手机号</div>
	</div>
	
</div>
</body>
</html>
<script type="text/javascript">


</script>