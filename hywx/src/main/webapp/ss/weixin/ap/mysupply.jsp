<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1, maximum-scale=1">
    <title>我的供货商</title>
    <link rel="stylesheet" type="text/css" href="../../../mobile/static/css/main.css"/>

</head>
<body class="f01">
<div id="layout-wrap" class="mysupply">
	<div class="layout-content">
	<c:if test="${resultList.size() > 0 }">
		<c:forEach var="item" items="${resultList}">
			<div class="mysupply-area">
				<div class="mysupply-title">${item.ghsName}</div>
				<c:forEach var="linkMan" items="${item.linkMenList}">
					<div class="mysupply-content-row">
						<div class="mysupply-content">
							<label>联系人：</label><span>${linkMan.emp_name }</span>
						</div>
						<div class="mysupply-content">
							<label>联系电话：</label><span>${linkMan.emp_mobile }</span>
						</div>				
					</div>
				</c:forEach>
				<div class="mysupply-content-row">
					<div class="mysupply-content">
						<label>标识门店：</label><span>${item.cmMap.cm_name}</span>
					</div>
					<div class="mysupply-content">
						<label>标识身份：</label><span>${item.cmMap.role_name}</span>
					</div>				
				</div>						
			</div>
		</c:forEach>
		<div class="mobile-text mysupply-dis">您已验证的手机号为</div>
	</c:if>

		<c:if test="${resultList==null || resultList.size() == 0 }">
			<div class="nosupply-describe">手机号没有匹配到供货商，请联系供货商业务员</div>
			<div class="mobilearea"><img src="../../../mobile/static/images/mobile.png"/></div>
			<div class="mobile-text">您已验证的手机号为</div>
		</c:if>	
		
		<div class="mobile-num">${user.mobile }</div>
		<div class="change-mobile" onclick="window.location.href='/ci/weixin/reg/beginChangeMobile.action?gzhOpenId=${user.gzhOpenid }&userOpenId=${user.openid }'">更换手机号</div>
	</div>
	
	
</div>
</body>
</html>

<script type="text/javascript">


</script>