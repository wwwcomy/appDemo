<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>App1</title>
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<h1>This is the index page of App2 using Client Credentials Grant</h1>
<h2> Step 1:</h2>
<p>Use the client credentials to get Access Token from IdM oAuth: http://localhost:8080/idm-oauth/oauth/token?grant_type=client_credentials</p>
<div class="form-group">
	<label for="url">URL</label>
	<input class="form-control" id="url" placeholder="URL" value="http://localhost:8080/idm-oauth/oauth/token?grant_type=client_credentials">
	<label for="clientId">Client ID</label>
	<input class="form-control" id="clientId" placeholder="clientId" value="m1">
	<label for="clientSecret">Client Secret</label>
	<input class="form-control" id="clientSecret" placeholder="clientSecret" value="s1">
</div>
<button id="getAccessToken" type="button" class="btn btn-info">GetAccessToken</button>
<p>Response:</p>
<textarea class="form-control" rows="5" id="textarea"></textarea>

<h2> Step 2:</h2>
<p>App2 CANNOT use the access token to call the first PROTECTED(by scope & user permission) rest API(Usually the user information):</p>
<p>Because the first API needs the permission of the end user.</p>
<div class="form-group">
	<label for="resourceUrl1">URL</label>
	<input class="form-control" id="resourceUrl1" placeholder="URL" value="http://localhost:8080/idm-oauth/resource/getUserInfo?access_token=XXXXXX">
</div>
<button id="getResource1" type="button" class="btn btn-info">Get Protected Resource</button>
<p>Response:</p>
<textarea class="form-control" rows="5" id="textarea1"></textarea>
<h2> Step 3:</h2>
<p>App2 CAN use the access token to call the second PROTECTED(only by scope) rest API:</p>
<div class="form-group">
	<label for="resourceUrl2">URL</label>
	<input class="form-control" id="resourceUrl2" placeholder="URL" value="http://localhost:8080/idm-oauth/resource/getUserInfo2?access_token=XXXXXX">
</div>
<button id="getResource2" type="button" class="btn btn-info">Get Protected Resource</button>
<p>Response:</p>
<textarea class="form-control" rows="5" id="textarea2"></textarea>


</div>

	<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="js/base64.js"></script>
	<script type="text/javascript">
			(function ($) {
				$("#getAccessToken").click(function(){
					var resourceUrlTemplate1 = "http://localhost:8080/idm-oauth/resource/getUserInfo?access_token=XXXXXX";
					var resourceUrlTemplate2 = "http://localhost:8080/idm-oauth/resource/getUserInfo2?access_token=XXXXXX";
					$.ajax({
					  type : "POST",
					  headers : {"Authorization":"Basic "+ BASE64.encoder($("#clientId").val()+":"+$("#clientSecret").val())},
					  url: $("#url").val()
					}).done(function(data) {
					  $("#textarea").val(JSON.stringify(data));
					  $("#resourceUrl1").val(resourceUrlTemplate1.replace("XXXXXX",data.access_token));
					  $("#resourceUrl2").val(resourceUrlTemplate2.replace("XXXXXX",data.access_token));
					}).fail(function(jqXHR, textStatus, errorThrown) {
					  $("#textarea").val(errorThrown);
					});
				});
				
				$("#getResource1").click(function(){
					$.ajax({
					  type : "POST",
					  url: $("#resourceUrl1").val()
					}).done(function(data) {
					  $("#textarea1").val(JSON.stringify(data));
					}).fail(function(jqXHR, textStatus, errorThrown) {
					  $("#textarea1").val(errorThrown);
					});
				});
				
				$("#getResource2").click(function(){
					$.ajax({
					  type : "POST",
					  url: $("#resourceUrl2").val()
					}).done(function(data) {
					  $("#textarea2").val(JSON.stringify(data));
					}).fail(function(jqXHR, textStatus, errorThrown) {
					  $("#textarea2").val(errorThrown);
					});
				});
				
            })(jQuery);
	</script>
</body>
</html>
