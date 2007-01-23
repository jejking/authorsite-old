package org.authorsite.domain.aspects;

import org.acegisecurity.intercept.AbstractSecurityInterceptor;
import org.acegisecurity.intercept.InterceptorStatusToken;
import org.acegisecurity.intercept.ObjectDefinitionSource;
import org.acegisecurity.intercept.method.MethodDefinitionAttributes;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


/**
 *
 * @author jking
 */
@Aspect
public class MethodSecurityInterceptorAspect extends AbstractSecurityInterceptor {
    
    private MethodDefinitionAttributes objectDefinitionSource;
    
    /** Creates a new instance of MethodSecurityInterceptorAspect */
    public MethodSecurityInterceptorAspect() {
    }
    
    public ObjectDefinitionSource obtainObjectDefinitionSource() {
        return this.objectDefinitionSource;
    }
    
    public void setObjectDefinitionSource(MethodDefinitionAttributes objectDefinitionSource) {
        this.objectDefinitionSource = objectDefinitionSource;
    }

    public Class getSecureObjectClass() {
        return ProceedingJoinPoint.class;
    }
    

    @Around("execution (public * org.authorsite.security.test..Test*.*(..))")
    public Object invokeSecuredMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        
        InterceptorStatusToken token = super.beforeInvocation(pjp);
        try {
            result = pjp.proceed();
        }
        catch (Throwable t) {
            // eek, rethrow
            throw (t);
        }
        finally {
            result = super.afterInvocation(token, result);
        }
        return result;
    }

}

