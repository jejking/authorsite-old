/*
 * RulesFacadeBean.java
 *
 * Created on 15 February 2003, 20:34
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/facade/RulesFacadeBean.java,v 1.2 2003/03/01 13:36:45 jejking Exp $
 *
 * Copyright (C) 2003  John King
 *
 * This file is part of the authorsite.org bibliographic
 * application.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.authorsite.bib.ejb.facade;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import org.authorsite.bib.ejb.services.rules.*;
import org.authorsite.bib.exceptions.*;

/**
 * Facade which controls access to the RulesManagerBean
 *
 * @ejb.bean    name="RulesFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/RulesFacadeEJB"
 *              view-type="remote"
 *
 * @ejb.interface   remote-class="org.authorsite.bib.ejb.facade.RulesFacade"
 *                  
 * @ejb.home        remote-class="org.authorsite.bib.ejb.facade.RulesFacadeHome"
 *
 * @ejb:ejb-ref ejb-name="RulesManagerEJB"
 *              ref-name="ejb/MyRulesManagerEJB"
 *              view-type="local"                  
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class RulesFacadeBean implements SessionBean {
    
    private SessionContext sessionCtx;
    private InitialContext ctx;
    private RulesManagerLocalHome rulesManagerLocalHome;
    private RulesManagerLocal rulesManagerLocal;
    
    private void getHomes() throws EJBException {
        try {
            ctx = new InitialContext();
            rulesManagerLocalHome = (RulesManagerLocalHome) ctx.lookup("java:comp/env/ejb/MyRulesManagerEJB");;
        }
        catch (NamingException ne) {
            throw new EJBException(ne.getMessage());
        }
        catch (ClassCastException cce) {
            throw new EJBException(cce.getMessage());
        }
    }
    
    /** Creates a new instance of RulesFacadeBean */
    public RulesFacadeBean() throws Exception {
        getHomes();
        rulesManagerLocal = rulesManagerLocalHome.create();
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public HashSet getFields(String mediaItemType) {
        return rulesManagerLocal.getFields(mediaItemType);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public ArrayList getFieldDescriptions(String mediaItemType) {
        return rulesManagerLocal.getFieldDescriptions(mediaItemType);
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public HashSet getFieldsOfPriority(String mediaItemType, String priority) {
        return rulesManagerLocal.getFieldsOfPriority(mediaItemType, priority);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public ArrayList getFieldDescriptionsOfPriority(String mediaItemType, String priority) {
        return rulesManagerLocal.getFieldDescriptionsOfPriority(mediaItemType, priority);
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public HashSet getProductionRelationships(String mediaItemType) {
        return rulesManagerLocal.getProductionRelationships(mediaItemType);
    }
    
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public HashSet getProductionRelationshipsOfPriority(String mediaItemType, String priority) {
        return rulesManagerLocal.getProductionRelationshipsOfPriority(mediaItemType, priority);
    }
    
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public HashMap getIntraMediaRelationships(String mediaItemType) {
        return rulesManagerLocal.getIntraMediaRelationships(mediaItemType);
    }
    
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public HashMap getIntraMediaRelationshipsOfPriority(String mediaItemType, String priority) {
        return rulesManagerLocal.getIntraMediaRelationshipsOfPriority(mediaItemType, priority);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public HashSet getAllMediaTypes() {
        return rulesManagerLocal.getAllMediaTypes();
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public HashSet getAllMediaProductionRelationships() {
        return rulesManagerLocal.getAllMediaProductionRelationships();
    }
    
        
    public void ejbCreate() {
    }
    
    public void ejbActivate() throws javax.ejb.EJBException {
        getHomes();
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException {
    }
    
    public void ejbRemove() throws javax.ejb.EJBException {
    }
    
    public void setSessionContext(javax.ejb.SessionContext newSessionContext) throws javax.ejb.EJBException {
        sessionCtx = newSessionContext;
    }
    
}
