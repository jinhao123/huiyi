<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="f02">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1, maximum-scale=1">
    <title>帮助</title>
    <link rel="stylesheet" type="text/css" href="../../../mobile/static/css/main.css"/>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body class="f02">
<div id="layout-wrap" >
	<div style="margin-top:20px;">
		<button onclick="saoyisao();" style="background:wheat;border:1px solid; height:30px; width:60px;">扫一扫</button>
	</div>
	<div class="layout-content">
		<div class="helparea-row">
			<div class="help-title">旺店365是什么？</div>
			<div class="help-content">旺店365是一个连接店铺与供货商的信息平台。您可通过该平台了解供货商发布的促销信息，向供货商订货，获取供货商的促销返利等。</div>
		</div>
		<div class="helparea-row">
			<div class="help-title">关注旺店365之后，如何去注册？</div>
			<div class="help-content">关注旺店365之后，需要验证您的手机号，手机号验证通过，即可使用旺店365的功能。您可以在关注旺店365后通过发送的消息链接，或者是点击服务号首页下方的菜单进入验证手机号的页面，只需输入手机号和验证码，就可以完成验证流程。</div>
		</div>	
		<div class="helparea-row">
			<div class="help-title">手机号可以更换吗？</div>
			<div class="help-content">手机号验证通过之后，是可以更换的。您可以点击我的供货商，页面就有更换手机号的链接，点击链接根据流程即可更换手机号。</div>
		</div>			
		<div class="helparea-row">
			<div class="help-title">手机号也验证了，没有供货商怎么办？</div>
			<div class="help-content">手机号验证之后，系统会根据您所在的店铺，与对应的供货商进行匹配。如果供货商没有将您店铺的信息以及您的联系方式录入到供货商的系统，那么您在旺店365中就不能看的供货商的信息。遇到这样的情况，您可以联系供货商的业务员。</div>
		</div>				
	</div>
</div>
</body>
</html>
<script type="text/javascript">
	wx.config({
	    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${signatureMap.appId}', // 必填，公众号的唯一标识
	    timestamp: ${signatureMap.timestamp}, // 必填，生成签名的时间戳
	    nonceStr: '${signatureMap.nonceStr}', // 必填，生成签名的随机串
	    signature: '${signatureMap.signature}',// 必填，签名，见附录1
	    jsApiList: ['scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	function saoyisao(){
		wx.scanQRCode({
		    needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
		    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
		    success: function (res) {
			    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
			}
		});
	}
</script>