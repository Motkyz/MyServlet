<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.lang.Math" %>
<%@ page import="java.lang.String" %>
<html>
<head>
    <title>Java Servlet App</title>
</head>
<body>
<h3 class="Date">${creationDate}</h3>
<h1 class="currentDir">${currentDir}</h1>

<c:if test="${not empty parentDir}">
    <a href="filelist?path=${URLEncoder.encode(parentDir, 'UTF-8')}">Вверх</a>
</c:if>

<table>
    <thead>
    <th align="left">Имя</th>
    <th align="left">Размер</th>
    <th align="left">Дата изменения</th>
    </thead>
    <tbody>
    <c:forEach items="${files}" var="file">
        <tr>
            <td>
                <c:choose>
                    <c:when test="${file.isDirectory()}">
                        <img src="https://img.icons8.com/?size=25&id=12160&format=png&color=000000"
                             class="icon">
                        <a href="filelist?path=${URLEncoder.encode(file.getAbsolutePath(), 'UTF-8')}">
                                ${file.getName()}
                        </a>
                    </c:when>
                    <c:otherwise>
                        <img src="https://img.icons8.com/?size=25&id=11651&format=png&color=000000"
                             class="icon">
                        <a href="filedownload?fileDownloadPath=${URLEncoder.encode(file.getAbsolutePath(), 'UTF-8')}">
                                ${file.getName()}
                        </a>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${file.isFile()}">
                        ${String.format("%.0f", Math.ceil(file.length() / 1024))} КБ
                    </c:when>
                </c:choose>
            </td>
            <td>
                <jsp:useBean id="dateValue" class="java.util.Date"/>
                <jsp:setProperty name="dateValue" property="time" value="${file.lastModified()}"/>
                <fmt:formatDate value="${dateValue}" pattern="dd.MM.yyyy HH:mm"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>