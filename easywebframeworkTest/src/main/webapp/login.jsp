<html>
	<head>
		<title>Login Page</title>
	</head>
	<body>
		<form action="${pageContext.request.contextPath}/login/action.do">
		
			<label>User name</label>
			<input type="text" name="username" />
			<label>Password</label>
			<input type="password" name="pass" />
			
			<input type="submit" /><input type="reset" />
		</form>
	</body>
</html>