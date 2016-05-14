<%--
  Created by IntelliJ IDEA.
  User: Anonym
  Date: 19. 4. 2016
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
        <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Mission Manager</title>
        </head>

<body>
<h1>Mission list</h1>
<table border="1">
    <tr>
        <th>mission_name</th>
        <th>level_required</th>
        <th>capacity</th>
        <th>available</th>

    </tr>
    <c:forEach items="${mission}" var="mission">
        <tr>
            <td><c:out value="${mission.mission_name}"/></td>
            <td><c:out value="${mission.levelRequired}"/></td>
            <td><c:out value="${mission.capacity}"/></td>
            <td><c:out value="${mission.available}"/></td>
        </tr>
    </c:forEach>
</table>

<h2>Add mission:</h2>
<c:if test="${not empty error}">
    <div style="border: solid 1px black; background-color: dodgerblue; padding: 10px">
        <c:out value="${error}"/>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/mission/add" method="post">
    <table>
        <tr>
            <th>Mission name:</th>
            <td><input type="text" name="mission_name" value="<c:out value='${param.mission_name}'/>"/></td>
        </tr>
        <tr>
            <th>Mission level required:</th>
            <td><input type="text" name="level_required" value="<c:out value='${param.level}'/>"/></td>
        </tr>
        <tr>
            <th>Mission capacity:</th>
            <td><input type="text" name="capacity" value="<c:out value='${param.capacity}'/>"/></td>
        </tr>
        <tr>
            <th>Mission availability:</th>
            <td><input type="text" name="available" value="<c:out value='${param.available}'/>"/></td>
        </tr>
    </table>
    <input type="Submit" value="Add" />
</form>

</body>

</html>

