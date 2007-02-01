/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 */
package org.authorsite.security;

import org.acegisecurity.intercept.AbstractSecurityInterceptor;
import org.acegisecurity.intercept.InterceptorStatusToken;
import org.acegisecurity.intercept.ObjectDefinitionSource;
import org.acegisecurity.intercept.method.MethodDefinitionAttributes;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


/**
 * Custom Acegi based interceptor to process annotation
 * based security constraints on methods.
 *
 * <p>Should become deprecated once Acegi supports Spring 2.0
 * and AspectJ style annotations natively.</p>
 * 
 * @author jking
 */
@Aspect
public class MethodSecurityInterceptorAspect extends AbstractSecurityInterceptor {
    
    private MethodDefinitionAttributes objectDefinitionSource;
    
    /** 
     * Creates a new instance of MethodSecurityInterceptorAspect 
     */
    public MethodSecurityInterceptorAspect() {
	super();
    }
    
    @Override
    public ObjectDefinitionSource obtainObjectDefinitionSource() {
        return this.objectDefinitionSource;
    }
    
    /**
     * @param objectDefinitionSource
     */
    public void setObjectDefinitionSource(MethodDefinitionAttributes objectDefinitionSource) {
        this.objectDefinitionSource = objectDefinitionSource;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class getSecureObjectClass() {
        return ProceedingJoinPoint.class;
    }
    
    /**
     * @param pjp
     * @return result of invocation
     * @throws Throwable
     */
    @Around("@annotation(org.acegisecurity.annotation.Secured)")
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

