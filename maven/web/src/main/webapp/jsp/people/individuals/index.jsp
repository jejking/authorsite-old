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
            <fmt:message key="individuals-header"/>
        </h1>
        
        <table>
            <thead>
                <tr>
                    <th>
                        <fmt:message key="name"/>
                    </th>
                    <th>
                        <fmt:message key="givenNames"/>
                    </th>
                    <th>
                        <fmt:message key="nameQualification"/>
                    </th>
                    <th>
                        <fmt:message key="action"/>
                    </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${individuals}" var="individual">
                    <tr>
                        <td>
                            ${individual.name}
                        </td>
                        <td>
                            ${individual.givenNames}
                        </td>
                        <td>
                            ${individual.nameQualification}
                        </td>
                        <td>
                            <fmt:message key="view"/> <as:LinkTag entry="${individual}" />
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        
    </fmt:bundle>
        
</div>


<%@ include file="/jsp/fragments/extra.jspf" %>    
<%@ include file="/jsp/fragments/footer.jspf" %>