<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>App1</title>
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<h1>This is the index page of app1 using Authorization Code Grant</h1>
<h2> Step 1:</h2>
<p>The link is: http://localhost:8080/idm-oauth/oauth/authorize?client_id=m1&redirect_uri=http://localhost:8080/app1/&response_type=code&scope=read</p>
<a class="btn btn-primary" href="http://localhost:8080/idm-oauth/oauth/authorize?client_id=m1&redirect_uri=http%3a%2f%2flocalhost%3a8080%2fapp1%2f&response_type=code&scope=read" role="button">Login using IdM-oAuth</a>
<h2> Step 2:</h2>
After user login, App1 will get an "Authorized Code" in the URL:  <b><em id="authCode"></em></b>
<h2> Step 3:</h2>
<p>App1 will Post a REST Call to:</p>
<div class="form-group">
	<label for="url">URL</label>
	<input class="form-control" id="url" placeholder="URL" value="http://localhost:8080/idm-oauth/oauth/token?code=XXXXXX&client_id=m1&client_secret=s1&grant_type=authorization_code&redirect_uri=http://localhost:8080/app1/">
</div>
<button id="getAccessToken" type="button" class="btn btn-info">GetAccessToken</button>
<p>Response:</p>
<textarea class="form-control" rows="5" id="textarea"></textarea>
<h2> Step 4:</h2>
<p>So that App1 can use the access token to call the PROTECTED rest API(Usually the user's information):</p>
<div class="form-group">
	<label for="resourceUrl">URL</label>
	<input class="form-control" id="resourceUrl" placeholder="URL" value="http://localhost:8080/idm-oauth/resource/getUserInfo?access_token=XXXXXX">
</div>
<button id="getResource" type="button" class="btn btn-info">Get Protected Resource</button>
<p>Response:</p>
<textarea class="form-control" rows="5" id="textarea1"></textarea>


</div>

	<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript">
			(function ($) {
                $.getUrlParam = function (name) {
                    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                    var r = window.location.search.substr(1).match(reg);
                    if (r != null) return unescape(r[2]); return null;
                }
				var authCode = $.getUrlParam('code');
				var urlTemplate = "http://localhost:8080/idm-oauth/oauth/token?code=XXXXXX&client_id=m1&client_secret=s1&grant_type=authorization_code&redirect_uri=http://localhost:8080/app1/";
				if(authCode!=null){
					$("#authCode").html(authCode);
					$("#url").val(urlTemplate.replace("XXXXXX",authCode));
				}else{
					$("#authCode").html("No value");
					$("#url").val(urlTemplate)
				}
				
				$("#getAccessToken").click(function(){
					var resourceUrlTemplate = "http://localhost:8080/idm-oauth/resource/getUserInfo?access_token=XXXXXX";
					$.ajax({
					  type : "POST",
					  url: $("#url").val()
					}).done(function(data) {
					  $("#textarea").val(JSON.stringify(data));
					  $("#resourceUrl").val(resourceUrlTemplate.replace("XXXXXX",data.access_token));
					}).fail(function(jqXHR, textStatus, errorThrown) {
					  $("#textarea").val(errorThrown);
					});
				});
				
				$("#getResource").click(function(){
					$.ajax({
					  type : "POST",
					  url: $("#resourceUrl").val()
					}).done(function(data) {
					  $("#textarea1").val(JSON.stringify(data));
					}).fail(function(jqXHR, textStatus, errorThrown) {
					  $("#textarea1").val(errorThrown);
					});
				});
				
            })(jQuery);
	</script>
</body>
</html>
