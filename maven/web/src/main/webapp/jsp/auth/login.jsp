<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="/jsp/fragments/header.jspf" %>
<%@ include file="/jsp/fragments/nav.jspf" %>    

<div id="main">
    <fmt:bundle basename="org.authorsite.web.resources.auth">
        <h1>
            <fmt:message key="login-header"/>
        </h1>
        <c:url value="/j_acegi_security_check" var="j_acegi_security_check" />
        <form action="${j_acegi_security_check}" method="POST">
            <div id="login-form">
                
                <table>
                    <tr>
                        <td>
                            <fmt:message key="username"/>
                        </td>
                        <td>
                            <input type="text" name="j_username" size="50">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <fmt:message key="password"/>
                        </td>
                        <td>
                            <input type="password" name="j_password" size="50"/>
                        </td>
                    </tr>
                    <tr>
                        <tr>
                            <td width="2">
                                <fmt:message key="submit-login-form" var="submitLogin"/>
                                <input type="submit" value="${submitLogin}"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="2">
                                <fmt:message key="reset-login-form" var="resetLogin"/>
                                <input type="reset" value="${resetLogin}"/>
                            </td>
                        </tr>
                    </tr>
                </table>
                
            </div>
        </form>
    </fmt:bundle>
</div>


<%@ include file="/jsp/fragments/extra.jspf" %>    
<%@ include file="/jsp/fragments/footer.jspf" %>