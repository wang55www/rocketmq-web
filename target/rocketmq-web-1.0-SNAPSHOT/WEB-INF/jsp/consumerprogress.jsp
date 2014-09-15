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
        <title>consumerprogress</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
    </head>
    <body>
        <h1>消费进度</h1>
        <br/>
        <c:if test="${all}">
        <table width="100%" cellpadding="0" cellspacing="0" border="1">
            <thead>
                <tr>
                    <th>消费组名称</th>
                    <th>客户端连接数</th>
                    <th>客户端版本</th>
                    <th>消费类型</th>
                    <th>TPS</th>
                    <th>进度差</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${result}" var="cp">
                <tr align="center">
                    <td><a href="consumerProgress/${cp.group}">${cp.group}</a></td>
                    <td>${cp.count}</td>
                    <td>${cp.version}</td>
                    <td>${cp.type}</td>
                    <td>${cp.tps}</td>
                    <td>${cp.diff}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:if>
        <c:if test="${!all}">
            <label>消费TPS：${result.tps}</label><br/>
            <label>总进度差：${result.totalDiff}</label><br/>
            <table width="100%" cellpadding="0" cellspacing="0" border="1">
                <thead>
                    <tr>
                        <th>topic</th>
                        <th>brocker名称</th>
                        <th>队列ID</th>
                        <th>broker偏移量</th>
                        <th>消费偏移量</th>
                        <th>进度差</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${result.cdList}" var="cd">
                    <tr>
                        <td>${cd.topic}</td>
                        <td>${cd.brokerName}</td>
                        <td>${cd.mqId}</td>
                        <td>${cd.brokerOffset}</td>
                        <td>${cd.consumerOffset}</td>
                        <td>${cd.diff}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <br/>  
        <c:if test="${all}"><a href="index.jsp">返回</a></c:if>
        <c:if test="${!all}"><a href="../consumerProgress">返回</a></c:if>
    </body>
</html>
