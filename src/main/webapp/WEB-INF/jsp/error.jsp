<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<!DOCTYPE HTML>
 
<html>
 
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="shortcut icon" type="image/png" href="favicon.ico">
    <title>Pagina de error</title>
</head>
 
<body>
    <h1>Pagina de error</h1>
 
    <ul>
        <li>status: <c:out value="${requestScope.status}" /></li>
        <li>error: <c:out value="${requestScope.error}" /></li>
        <li>message: <c:out value="${requestScope.message}" /></li>
        <li>path: <c:out value="${requestScope.path}" /></li>
        <li>trace: <c:out value="${requestScope.trace}" /></li>
    </ul>
 
</body>
 
</html>