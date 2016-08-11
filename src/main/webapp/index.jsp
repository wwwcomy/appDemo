<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
	<h2>Hello AppDemo!</h2>
	<h3>AppDemo的应用场景：</h3>
	<h4>角色包括以下三个:</h4>
	<pre>
	 a.用户 
	 b.Tomcat主页(OAuth2中的Client) 
	 c.本Demo-appDemo(ResourceServer&AuthorizationServer),
	   resource是一个controller：http://localhost:8080/resource/getUserInfo
	</pre>

	<h4>OAuth2 Authorization code(4种GrantType之一，最常用也最复杂)步骤如下</h4>
	<pre>
	1. 用户正在访问Tomcat主页这个"网站"。 http://localhost:8080/。这个网站在appDemo中已经注册了client_id和client_secret
	2. 用户希望授权"网站"能获取用户存在appDemo中的Resource，链接为http://localhost:8080/appDemo/resource/getUserInfo
	        同时，又不想把自己在appDemo的用户名、密码告诉"网站"
	3. "网站"正好提供了一个超链接，名字叫"Login using appDemo"，链接为：
	   http://localhost:8080/appDemo/oauth/authorize?client_id=m1&redirect_uri=http%3a%2f%2flocalhost%3a8080%2f&response_type=code&scope=read
	4. appDemo发现注册名为"m1"的client正在申请授权(这个申请实际上是由用户发起的)，但是并没有用户的登录信息
	5. appDemo先跳转到登录界面让用户登录
	6. 用户登录成功，appDemo询问用户：m1要XXXX你的resource，你，愿意吗？(这一步显示的是oauth_approval.jsp)
	7. 用户：我，愿意(用户点击Authorize按钮)
	8. appDemo跳转回了刚才的returnUri，并且添加了code参数：http://localhost:8080/?code=VOUIRc
	9. 现在，client拿到了code，这个code就是Authorization Code，下面这步是client要做的
	       由于没有现成的client,我们可以用一些REST tool来模拟client向appDemo申请AccessToken的过程
	   tool包括：firefox的REST client，Chrome的POSTMan，真正使用的时候，client需要使用后台代码发http请求，前端Ajax会有跨域限制
	10. Client可以直接发起个POST请求来获取Access Token:
		http://localhost:8080/appDemo/oauth/token?code=g6hW13&client_id=m1&client_secret=s1&grant_type=authorization_code&redirect_uri=http%3a%2f%2flocalhost%3a8080%2f
		注意把这个URL中的code替换成前面一步生成的code
	11. 返回的JSON格式如下
{"access_token":"68df2aed-a84d-4f8e-b3d6-2520c4c6222e","token_type":"bearer","refresh_token":"9c585f15-58d4-402a-b325-495cf5477364","expires_in":43199,"scope":"read"}
	12. Client可以使用这个access token去访问被保护的资源：
	http://localhost:8080/appDemo/resource/getUserInfo?access_token=68df2aed-a84d-4f8e-b3d6-2520c4c6222e
	13. 可以尝试把access token去掉或者删掉两位，都是访问不了的。
	</pre>

</body>
</html>
