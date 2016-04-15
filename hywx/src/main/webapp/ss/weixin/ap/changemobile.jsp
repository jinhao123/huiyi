<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1, maximum-scale=1">
    <title>修改手机号</title>
    <link rel="stylesheet" type="text/css" href="../../../mobile/static/css/main.css"/>
</head>
<body>
<div id="layout-wrap" >
	<div class="layout-content">
		<div class="validation-describe">请输入要更换的新手机号。</div>
		<div class="validation-row">
			<input type="number" class="input-text" placeholder="请输入手机号" id="mobile-num"/>
		</div>
		<div class="validation-row">
			<input type="number" class="input-text identifying-code" placeholder="验证码" id="identifying-code" />
			<input type="button" class="send-code" value="获取验证码" id="sendcode"/>
		</div>
		<input type="button" class="ok-area" id="ok" value="确定" />
	</div>
	
	
</div>
</body>
</html>
<script type="text/javascript" src="../../../mobile/static/js/core/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function(){
		function valmobilelength(obj){
			if($(obj).val().length==11) $('#sendcode').addClass('send-code-normal');
			else $('#sendcode').removeClass('send-code-normal');
		};
	
		$('#mobile-num').keyup(function(){
			valmobilelength(this);
		}).blur(function(){
			valmobilelength(this);			
		});
		
		$('#sendcode').click(function(){
			if($(this).hasClass('send-code-normal')){
				 var mobilenum=$('#mobile-num').val();
				 if(!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(mobilenum))){
					 alert('手机号码不正确，请重新输入');
					 $('#sendcode').removeClass('send-code-normal');
					 $('#ok').removeClass('ok-area-normal');
				 }
				 else{
				 	$('#sendcode').prop('disabled','disabled');				 
					 $.get('/ci/weixin/reg/sendValidateCode.action',{'mobile':mobilenum},function(data){
					 	if(data.code=='0'){
					 		alert(data.message);
					 		$('#sendcode').removeProp('disabled');
					 	}
					 	else {
					 		var i = 60;
							 $('#sendcode').prop('disabled','disabled');
							 var cleartimeout=setInterval(function(){
									if(i>=0 && i<=60){
									 	$('#sendcode').val(i+'s');
									 	i--;							
									}
									else {
										$('#sendcode').val('重新获取');
										clearInterval(cleartimeout);
										$('#sendcode').removeProp('disabled');
										return false;
									}						 
							 },1000);					 	
					 	}
					 });					 					 					 
				 }
			}
		});
		
		$('#identifying-code').keyup(function(){
			if(($(this).val().length==4) && $(sendcode).hasClass('send-code-normal')) $('#ok').addClass('ok-area-normal');
			else $('#ok').removeClass('ok-area-normal');
		});		
		
		$('#ok').click(function(){
			if($(this).hasClass('ok-area-normal')){
				$(this).prop('disabled','disabled').val('正在加载请稍候...');
				 var $mobilenum=$('#mobile-num').val();
				 var $sendcode=$('#identifying-code').val();
				 $.get('/ci/weixin/reg/checkValidateCode.action',{'mobile':$mobilenum,'code':$sendcode},function(data){
				 	$('#ok').removeProp('disabled').val('确定');
				 	if(data.code=='0')alert(data.message);
				 	else {
				 		WeixinJSBridge.call('closeWindow');
				 	}
				 });
			}
		});
	});
	
	
</script>