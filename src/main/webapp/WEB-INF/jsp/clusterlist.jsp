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
        <title>cluster list</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
    </head>
    <body>
        <h1>集群列表</h1>
        <table width="100%" cellpadding="0" cellspacing="0" border="1">
            <thead>
                <tr>
                    <th>集群名称</th>
                    <th>broker名称</th>
                    <th>brokerId</th>
                    <th>ip</th>
                    <th>版本</th>
                    <th>输入TPS</th>
                    <th>输出TPS</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${cibl}" var="cib">
                <tr align="center">
                    <td>${cib.clusterName}</td>
                    <td>${cib.brokerName}</td>
                    <td>${cib.bid}</td>
                    <td>${cib.addr}</td>
                    <td>${cib.version}</td>
                    <td><fmt:formatNumber pattern="#######.##" value="${cib.inTps}"/></td>
                    <td><fmt:formatNumber pattern="#######.##" value="${cib.outTps}"/></td>                    
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <br/>   
        <a href="index.jsp  ">返回</a>
    </body>
</html>
