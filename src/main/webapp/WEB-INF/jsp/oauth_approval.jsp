<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML>

<html>
<head>
<title>Approval</title>
</head>
<body>
	<h1>Approval</h1>

	<p>Do you authorize '${authorizationRequest.clientId}' to access
		your protected resources?</p>

	<form id='confirmationForm' name='confirmationForm'
		action='${pageContext.request.contextPath}/oauth/authorize'
		method='post'>
		<input name='user_oauth_approval' value='true' type='hidden' /> <input
			name='authorize' value='Authorize' type='submit'
			class="btn btn-success" />
	</form>
	<form id='denialForm' name='denialForm'
		action='${pageContext.request.contextPath}/oauth/authorize'
		method='post'>
		<input name='user_oauth_approval' value='false' type='hidden' /> <input
			name='deny' value='Deny' type='submit' class="btn btn-warning" />
	</form>
</body>
</html>