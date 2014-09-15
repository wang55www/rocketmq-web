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
        <title>topicStats</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
    </head>
    <body>
        <h1>topic状态信息</h1>
        <table width="100%" cellpadding="0" cellspacing="0" border="1">
            <thead>
                <tr>
                    <th>broker名称</th>
                    <th>队列ID</th>
                    <th>最小偏移量</th>
                    <th>最大偏移量</th>
                    <th>最近更新时间</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${result}" var="ts">
                <tr align="center">
                    <td>${ts.brokerName}</td>
                    <td>${ts.qid}</td>
                    <td>${ts.minOffset}</td>
                    <td>${ts.maxOffset}</td>
                    <td><fmt:formatDate value="${ts.lastUpdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <br/>   
        <a href="<%=request.getContextPath()%>/topicList">返回</a>
    </body>
</html>
