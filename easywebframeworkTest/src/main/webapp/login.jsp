<html>
	<head>
		<title>Login Page</title>
	</head>
	<body>
		<h2>/login/action</h2>
		<form action="${pageContext.request.contextPath}/login/action.do" method="post">
		
			<label>User name</label>
			<input type="text" name="username" />
			<label>Password</label>
			<input type="password" name="pass" />
			
			<input type="submit" /><input type="reset" />
		</form>
		<h2>/login/action2</h2>
		<form action="${pageContext.request.contextPath}/login/action2.do" method="post">
		
			<label>User name</label>
			<input type="text" name="username" />
			<label>Password</label>
			<input type="password" name="pass" />
			
			<input type="submit" /><input type="reset" />
		</form>
	</body>
</html>