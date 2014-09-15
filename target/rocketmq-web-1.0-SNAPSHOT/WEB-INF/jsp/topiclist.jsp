<%-- 
    Document   : clusterlist
    Created on : 2014-9-10, 17:12:03
    Author     : wangxiangnan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>topicList</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
    </head>
    <body>
        <h1>topic列表</h1>
        <table width="100%" cellpadding="0" cellspacing="0" border="1">
            <thead>
                <tr>
                    <th>topic</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${result}" var="topic">
                <tr align="center">
                    <td><a href="topicStats/${topic}">${topic}</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <br/>   
        <a href="index.jsp">返回</a>
    </body>
</html>
