/*
 * RulesManager.java
 *
 * Created on 05 November 2002, 11:44
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/services/rules/RulesManagerBean.java,v 1.1 2003/03/29 12:50:00 jejking Exp $
 *
 * Copyright (C) 2002  John King
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

package org.authorsite.bib.ejb.services.rules;
import javax.ejb.*;
import javax.naming.*;
import java.util.*;
import org.authorsite.bib.ejb.entity.*;
import org.authorsite.bib.ejb.facade.details.*;
import org.authorsite.bib.exceptions.*;
/**
 * @ejb:bean    name="RulesManagerEJB"
 *              type="Stateless"
 *              jndi-name="ejb/RulesManagerEJB"
 *              local-jndi-name="ejb/RulesManagerLocalEJB"
 *              view-type="both"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.services.rules.RulesManager"
 *                  local-class="org.authorsite.bib.ejb.services.rules.RulesManagerLocal"
 *                  
 * @ejb:home        remote-class="org.authorsite.bib.ejb.services.rules.RulesManagerHome"
 *                  local-class="org.authorsite.bib.ejb.services.rules.RulesManagerLocalHome"
 *                  
 * @ejb:ejb-ref ejb-name="IntraMediaRelationshipEJB"
 *              ref-name="ejb/MyIntraMediaRelationshipEJB"
 *              view-type="local"
 *
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class RulesManagerBean implements SessionBean {
    
    private SessionContext ctx;
    private RulesProcessor myProcessor;
    private IntraMediaRelationshipLocalHome imrLocalHome;
    
    private void getHomes() throws EJBException {
        try {
            Context context = new InitialContext();
            Object obj = context.lookup("java:comp/env/ejb/MyIntraMediaRelationshipEJB");
            imrLocalHome = (IntraMediaRelationshipLocalHome) obj;
        }
        catch (NamingException ne) {
            throw new EJBException(ne.getMessage());
        }
        catch (ClassCastException cce) {
            throw new EJBException(cce.getMessage());
        }
    }
    
    /*
     * util method to load up the correct mediaItemDetailsHandler class to do various things with the details objects
     */
    private MediaItemDetailsHandler getMediaItemDetailsHandler(MediaItemLocal item) {
        MediaItemDetailsHandler handler = null;
        try {
            String DetailsHandlerName = "org.authorsite.bib.ejb.facade.details." + capitaliseFirstLetter(item.getMediaType()) + "DetailsHandler";
            // get the class
            Class MediaItemHandlerClass = Class.forName(DetailsHandlerName);
            handler = (MediaItemDetailsHandler) MediaItemHandlerClass.newInstance();
        }
        catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        catch (InstantiationException ie) {
            ie.printStackTrace();
        }
        catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
        return handler;
    }
    
    private String capitaliseFirstLetter(String capitaliseThisString) {
        char[] chars = capitaliseThisString.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public boolean validateProductionRelationshipAddition(org.authorsite.bib.ejb.entity.MediaItemLocal mediaFrom, String relType) {
        HashSet validProdRels = myProcessor.getProductionRelationships(mediaFrom.getMediaType());
        if (validProdRels.contains(relType)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public boolean validateProductionRelationshipRemoval(org.authorsite.bib.ejb.entity.MediaItemLocal mediaFrom, String relType) {
        HashSet obligProdRels = myProcessor.getProductionRelationshipsOfPriority(mediaFrom.getMediaType(), "obligatory");
        if (obligProdRels.contains(relType)) {
            return false; // we cannot remove an obligatory relationship
        }
        else {
            return true;
        }
    }
    
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public boolean validateIntraMediaRelationshipAddition(org.authorsite.bib.ejb.entity.MediaItemLocal mediaFrom, String relType, org.authorsite.bib.ejb.entity.MediaItemLocal mediaTo) {
        HashMap validIMRels = myProcessor.getIntraMediaRelationships(mediaFrom.getMediaType());
        if (validIMRels.containsKey(relType)) {
            HashSet validTargets = (HashSet) validIMRels.get(relType);
            if (validTargets.contains(mediaTo.getMediaType())) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public boolean validateIntraMediaRelationshipRemoval(org.authorsite.bib.ejb.entity.MediaItemLocal mediaFrom, String relType) {
        HashMap obligIMRels = myProcessor.getIntraMediaRelationshipsOfPriority(mediaFrom.getMediaType(), "obligatory");
        if (obligIMRels.containsKey(relType)) {
            return false;
        }
        else {
            return true;
        }
    }
    
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public void validatePublication(org.authorsite.bib.ejb.entity.MediaItemLocal mediaItemToValidate) throws InsufficientDetailException {
        /*
         * we need to check three things
         * - all obligatory production relationships exist (and that there's at least one person or organisation attached)
         * - all obligatory intraMedia relationships exist 
         * - if there are obligatory fields in the MediaItemDetails field definitions, there must be a corresponding Details object
         * obligatory fields should all be filled. If nothing else, the database will stop insertion of null values
         */
        
        /***********************************************************************
         * STAGE ONE
         * - check whether the item is in at least one language
         **********************************************************************/
        if (mediaItemToValidate.getLanguages().size() < 1) {
            throw new InsufficientDetailException("bib.error.insufficientdetail.language.NoLanguages");
        }
        
        /***********************************************************************
         * STAGE TWO
         * - check if we need a details object... i.e are there any obligatory type specific fields?
         **********************************************************************/
        if (myProcessor.getFieldsOfPriority(mediaItemToValidate.getMediaType(), "obligatory").size() > 0) {
            MediaItemDetailsHandler handler = getMediaItemDetailsHandler(mediaItemToValidate);
            if (!handler.mediaItemDetailsExists(mediaItemToValidate.getMediaItemID())) {
                // ooops. we have obligatory fields but no details object to describe them. Can't publish
                throw new InsufficientDetailException("bib.error.insufficientdetail.details.Nodetails");
            }
        }
        
        /***********************************************************************
         * STAGE THREE
         * - check that all obligatory production relationships exist and that if they do that they contain
         * at least one person or organisation reference
         **********************************************************************/
        HashSet obligProdRels = myProcessor.getProductionRelationshipsOfPriority(mediaItemToValidate.getMediaType(), "obligatory");
        Set existingProdRels = mediaItemToValidate.getMediaProductionRelationships();
        
        HashMap existingProdRelsMap = new HashMap();
        Iterator existingProdRelsIt = existingProdRels.iterator();
        while (existingProdRelsIt.hasNext()) {
            MediaProductionRelationshipLocal currentProdRel = (MediaProductionRelationshipLocal) existingProdRelsIt.next();
            existingProdRelsMap.put(currentProdRel.getRelationshipType(), currentProdRel);
        }
        
        Iterator obligProdRelsIt = obligProdRels.iterator();
        while (obligProdRelsIt.hasNext()) {
            String key = (String) obligProdRelsIt.next();
            if (existingProdRelsMap.containsKey(key)) {
                // ok, the relationship exists. Now, is there at least one person or one organisation in it?
                MediaProductionRelationshipLocal currentProdRel = (MediaProductionRelationshipLocal) existingProdRelsMap.get(key);
                if (currentProdRel.getPeople().size() + currentProdRel.getOrganisations().size() >= 1) {
                    continue; // yes there is at least one person or org, carry on checking
                }
                else {
                    throw new InsufficientDetailException("bib.error.insufficientdetail.prodRel.emptyObligProdRel", currentProdRel.getRelationshipType());
                }
            }
            else {
                throw new InsufficientDetailException("bib.error.insufficientdetail.prodRel.obligProdRelUndefined", key);
            }
        }
        
        /***********************************************************************
         * STAGE FOUR
         * - validate that the item has all the necessary IntraMediaRelationships that are 
         * obligatory for its mediaType
         **********************************************************************/
        HashMap obligIMRels = myProcessor.getIntraMediaRelationshipsOfPriority(mediaItemToValidate.getMediaType(), "obligatory");
        
        try {
            // now, get the IntraMediaRelationships associated with the mediaItemToValidate
            Collection existingIMRels = imrLocalHome.findByMediaFromID(mediaItemToValidate.getMediaItemID().intValue());
            // build a Set of the names of the existing intra media relationships
            HashSet existingIMRelsNames = new HashSet();
            Iterator existingIMRelsIt = existingIMRels.iterator();
            while (existingIMRelsIt.hasNext()) {
                IntraMediaRelationshipLocal currentIMRel = (IntraMediaRelationshipLocal) existingIMRelsIt.next();
                existingIMRelsNames.add(currentIMRel.getRelationshipType());
            }
            
            // go through the obligatoryIMrels and check that these are listed in the set of names of the existing intra media relationships
            Set namesOfObligIMRels = obligIMRels.keySet();
            Iterator namesOfObligIMRelsIt = namesOfObligIMRels.iterator();
            while (namesOfObligIMRelsIt.hasNext()) {
                String currentObligName = (String) namesOfObligIMRelsIt.next();
                if (existingIMRelsNames.contains(currentObligName)) {
                    continue;
                }
                else {
                    throw new InsufficientDetailException("bib.errors.insufficientdetail.imr.obligIMRnotDefined", currentObligName);
                }
            }
            
            /*******************************************************************
             * IT's OK. return
             ******************************************************************/
            return;
        }
        catch (FinderException fe) {
            throw new EJBException(fe.getMessage());
        }
    }
    
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public HashSet getFields(String mediaItemType) {
        return myProcessor.getFields(mediaItemType);
    }
    
    /**
     * @ejb.interface-method view-type="local"
     */
    public ArrayList getFieldDescriptions(String mediaItemType) {
        return myProcessor.getFieldDescriptions(mediaItemType);
    }
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public HashSet getFieldsOfPriority(String mediaItemType, String priority) {
        return myProcessor.getFieldsOfPriority(mediaItemType, priority);
    }
    
    /**
     * @ejb.interface-method view-type="local"
     */
    public ArrayList getFieldDescriptionsOfPriority(String mediaItemType, String priority) {
        return myProcessor.getFieldDescriptionsOfPriority(mediaItemType, priority);
    }
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public HashSet getProductionRelationships(String mediaItemType) {
        return myProcessor.getProductionRelationships(mediaItemType);
    }
    
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public HashSet getProductionRelationshipsOfPriority(String mediaItemType, String priority) {
        return myProcessor.getProductionRelationshipsOfPriority(mediaItemType, priority);
    }
    
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public HashMap getIntraMediaRelationships(String mediaItemType) {
        return myProcessor.getIntraMediaRelationships(mediaItemType);
    }
    
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public HashMap getIntraMediaRelationshipsOfPriority(String mediaItemType, String priority) {
        return myProcessor.getIntraMediaRelationshipsOfPriority(mediaItemType, priority);
    }
    
    /**
     * @ejb.interface-method view-type="local"
     */
    public HashSet getAllMediaTypes() {
        return myProcessor.getAllMediaTypes();
    }
    
    /**
     * @ejb.interface-method view-type="local"
     */
    public HashSet getAllMediaProductionRelationships() {
        return myProcessor.getAllMediaProductionRelationships();
    }
    
    /** Creates a new instance of RulesManager */
    public RulesManagerBean() {
        myProcessor = RulesProcessor.getInstance();
        getHomes();
    }
    
    public void ejbCreate() {
    }
    
    public void ejbActivate() throws javax.ejb.EJBException {
        myProcessor = RulesProcessor.getInstance();
        getHomes();
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException {
        myProcessor = null;
    }
    
    public void ejbRemove() throws javax.ejb.EJBException {
        myProcessor = null;
    }
    
    public void setSessionContext(javax.ejb.SessionContext sessionContext) throws javax.ejb.EJBException {
        ctx = sessionContext;
    }
}
