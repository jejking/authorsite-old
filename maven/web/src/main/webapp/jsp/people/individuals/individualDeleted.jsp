<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.authorsite.org/tags/authorsite" prefix="as" %>
<%@ taglib uri="http://acegisecurity.org/authz" prefix="authz" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/jsp/fragments/header.jspf" %>
<%@ include file="/jsp/fragments/nav.jspf" %>    

<div id="main">
    <fmt:bundle basename="org.authorsite.web.resources.bib.people">
        <h1>
            <fmt:message key="deleted"/> 
            ${individual.name}
            <c:if test="${! empty individual.givenNames}">
                , ${individual.givenNames}
            </c:if>
        </h1>

    </fmt:bundle>
    
</div>


<%@ include file="/jsp/fragments/extra.jspf" %>    
<%@ include file="/jsp/fragments/footer.jspf" %>