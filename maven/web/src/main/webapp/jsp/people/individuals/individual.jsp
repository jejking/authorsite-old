<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.authorsite.org/tags/authorsite" prefix="as" %>
<%@ taglib uri="http://acegisecurity.org/authz" prefix="authz" %>

<%@ include file="/jsp/fragments/header.jspf" %>
<%@ include file="/jsp/fragments/nav.jspf" %>    

<div id="main">
    <fmt:bundle basename="org.authorsite.web.resources.bib.people">
        <h1>
            ${individual.name}
            <c:if test="${! empty individual.givenNames}">
                , ${individual.givenNames}
            </c:if>
        </h1>
        
        <table>
            <tbody>
                <tr>
                    <td>
                        <fmt:message key="name"/>
                    </td>
                    <td>
                        ${individual.name}
                    </td>
                </tr>
                <c:if test="${! empty individual.givenNames}">
                    <tr>
                        <td>
                            <fmt:message key="givenNames"/>
                        </td>
                        <td>
                            ${individual.givenNames}
                        </td>
                    </tr>
                </c:if>
                <c:if test="${! empty individual.nameQualification}">
                    <tr>
                        <td>
                            <fmt:message key="nameQualification"/>
                        </td>
                        <td>
                            ${individual.nameQualification}
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        
    </fmt:bundle>
    
</div>


<%@ include file="/jsp/fragments/extra.jspf" %>    
<%@ include file="/jsp/fragments/footer.jspf" %>