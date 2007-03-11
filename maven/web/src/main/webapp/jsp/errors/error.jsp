<%@ page errorPage="finalError.html" %>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="extraCss" scope="page" value="error.css"/>

<%@ include file="/jsp/fragments/header.jspf" %>
<%@ include file="/jsp/fragments/nav.jspf" %>    

<div id="main" class="error">
    <fmt:bundle basename="org.authorsite.web.resources.errors">
        <div>
            <h1>${requestScope['javax.servlet.error.status_code']} - <fmt:message key="${requestScope['javax.servlet.error.status_code']}"/>"</h1>
            <p>
                <fmt:message key="${requestScope['javax.servlet.error.status_code']}-text"/>
            </p>
        </div>
    </fmt:bundle>
</div>


<%@ include file="/jsp/fragments/extra.jspf" %>    
<%@ include file="/jsp/fragments/footer.jspf" %>