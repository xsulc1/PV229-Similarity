<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="searchExecutor" class="web.SearchExecutorBean" scope="page"/>
<jsp:setProperty name="searchExecutor" property="imageId"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <form action="index.jsp">
            Image ID: <input type="text" name="imageId" value="${param['imageId']}">
            <input type="submit" value="Search similar">
        </form>
        
        <c:forEach items="${searchExecutor.results}" var="result">
            <div style="display: inline-block">
                ${result.distance}<br>
                <img src="images/${result.object.locatorURI}" alt="${result.distance}" style="max-width: 100px">
            </div>
        </c:forEach>
    </body>
</html>
