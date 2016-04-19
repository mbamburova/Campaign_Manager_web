<%--
  Created by IntelliJ IDEA.
  User: Michaela Bamburová
  Date: 18.04.2016
  Time: 0:20
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hero Manager</title>
</head>

<body>
<h1>Hero list</h1>
<table border="1">
    <tr>
        <th>name</th>
        <th>level</th>

    </tr>
    <c:forEach items="${hero}" var="hero">
        <tr>
            <td><c:out value="${hero.name}"/></td>
            <td><c:out value="${hero.level}"/></td>
            <td><form method="post" action="${pageContext.request.contextPath}/hero/initUpdate?id=${hero.id}"
                      style="margin-bottom: 0;"><input type="submit" value="Update"></form></td>
            <td><form method="post" action="${pageContext.request.contextPath}/hero/delete?id=${hero.id}"
                      style="margin-bottom: 0;"><input type="submit" value="Delete"></form></td>
        </tr>
    </c:forEach>
</table>


<c:if test="${empty update}">
<h2>Add hero:</h2>
    <c:if test="${not empty error}">
        <div style="border: solid 1px red; background-color: yellow; padding: 10px">
            <c:out value="${error}"/>
        </div>
    </c:if>

<form action="${pageContext.request.contextPath}/hero/add" method="post">
    <table>
        <tr>
            <th>Name of hero:</th>
            <td><input type="text" name="name" value="<c:out value='${param.name}'/>"/></td>
        </tr>
        <tr>
            <th>level:</th>
            <td><input type="text" name="level" value="<c:out value='${param.level}'/>"/></td>
        </tr>
    </table>
    <input type="Submit" value="Add" />
</form>
</c:if>

<c:if test="${not empty update}">
    <h2>Update hero:</h2>
    <c:if test="${not empty error}">
        <div style="border: solid 1px red; background-color: yellow; padding: 10px">
            <c:out value="${error}"/>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/hero/update" method="post">
        <table>
            <tr>
                <th>Name of hero:</th>
                <td><input type="text" name="name" value="<c:out value='${param.name}'/>"/></td>
            </tr>
            <tr>
                <th>level:</th>
                <td><input type="text" name="level" value="<c:out value='${param.level}'/>"/></td>
            </tr>
        </table>
        <input type="Submit" value="Add" />
    </form>
</c:if>






</body>

</html>

