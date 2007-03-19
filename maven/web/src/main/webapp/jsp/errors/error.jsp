<%@ page errorPage="finalError.jsp" %>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="extraCss" scope="page" value="error.css"/>


<%@ include file="/jsp/fragments/header.jspf" %>
<%@ include file="/jsp/fragments/nav.jspf" %>    

<div id="main" class="error">
    
        <div>
            <fmt:bundle  basename="org.authorsite.web.resources.errors">
                <h1><fmt:message key="${requestScope['javax.servlet.error.status_code']}"/></h1>
                <p>
                    <fmt:message key="${requestScope['javax.servlet.error.status_code']}-text"/>
                </p>
            </fmt:bundle>
        </div>
</div>


<%@ include file="/jsp/fragments/extra.jspf" %>    
<%@ include file="/jsp/fragments/footer.jspf" %>