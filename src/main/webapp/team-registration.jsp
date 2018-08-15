<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Build-a-team</title>
</head>
<body>
${manager.firstName} ${manager.lastName} please register your team:
<br></br>
<form action="TeamRegistrationServlet" method="get">

<input type="hidden" name="command" value="REGISTER">
<input type="hidden" name="managerId" value="${manager.id}">

<input type="text" name="teamName">
<br></br>
<button type="submit" name="Submit">Register team</button>

</form>
</body>
</html>