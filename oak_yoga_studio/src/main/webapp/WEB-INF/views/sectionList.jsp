<%-- 
    Document   : sectionList
    Created on : Oct 18, 2014, 4:31:59 PM
    Author     : Weldino
--%>
<%@taglib  prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>All sections</title>
    </head>
    <body>
        <h2> Sections for Course </h2>
        <table >
                 
            <tr><th>Section Name</th>
                <th>Room Number</th>
                <th>Instructor</th>
            </tr>
            <tr></tr>
            <c:forEach var="s" items="${sections}">
                 <tr>
                    
                    <td>${s.sectionName }</td> 
                    <td>${s.roomNumber}</td>
                    <td></td>
                   
                 </tr>
                 <tr>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <td> <a href="newSection">add a new section</a></td>
                  </sec:authorize>
                 </tr>
            
            </c:forEach>
        </table>
    </body>
</html>

