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
        
        <authz:authorize ifAnyGranted="ROLE_ADMINISTRATOR, ROLE_EDITOR">
            <!-- link to create -->
            <p>
                <fmt:message key="create.individual" var="createIndividualMessage"/>
                <c:url var="createUrl" value="/people/individuals/create"/>
                <a href="${createUrl}">${createIndividualMessage}</a>
            </p>
        </authz:authorize>
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
        
        <c:url var="indexUrl" value="/people/individuals/index"/>
        <%--
            Note, the values passed in here should be set by IndividualsIndexController
        --%>
        <as:PagingTag count="${count}" indexUrl="${indexUrl}" pageSize="${pageSizeUsed}" pageNumber="${pageNumberUsed}"/>
        
    </fmt:bundle>
        
</div>


<%@ include file="/jsp/fragments/extra.jspf" %>    
<%@ include file="/jsp/fragments/footer.jspf" %>