/*
 * PersonManagementCreationAction.java
 *
 * Created on 25 January 2003, 00:35
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/PersonManagementCreationAction.java,v 1.2 2003/03/01 13:34:13 jejking Exp $
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

package org.authorsite.bib.web.struts.action;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.authorsite.bib.dto.*;
import org.authorsite.bib.ejb.facade.*;
import org.authorsite.bib.web.struts.form.*;
import org.authorsite.bib.web.struts.util.*;
import org.authorsite.bib.web.util.EJBHomeFactory;
/**
 * Processes input from /management/createPerson.jsp and creates a new person reference
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class PersonManagementCreationAction extends BibAbstractAction {
    
    /** Creates a new instance of PersonManagementCreationAction */
    public PersonManagementCreationAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // extract variables from ActionForm
        String prefix = ((PersonManagementCreationForm)form).getPrefix();
        String mainName = ((PersonManagementCreationForm)form).getMainName();
        String givenName = ((PersonManagementCreationForm)form).getGivenName();
        String otherNames = ((PersonManagementCreationForm)form).getOtherNames();
        String suffix = ((PersonManagementCreationForm)form).getSuffix();
        String genderCode = ((PersonManagementCreationForm)form).getGenderCode();
        int genderCodeInt = Integer.parseInt(genderCode); // we will assume this works as it's gone through the validation
        
        // get reference to PersonRemote interface
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        PersonAndOrganisationManagementFacadeHome home = (PersonAndOrganisationManagementFacadeHome) ejbHomeFactory.lookupHome("ejb/PersonAndOrganisationManagementFacadeEJB", PersonAndOrganisationManagementFacadeHome.class);
        PersonAndOrganisationManagementFacade facade = home.create();
        
        // create the person
        Integer newPersonPK = new Integer(facade.createPerson(mainName, givenName, otherNames, prefix, suffix, genderCodeInt));
        IntegerBean newPersonPKBean = new IntegerBean();
        newPersonPKBean.setInteger(newPersonPK);
        request.setAttribute("newPersonPK", newPersonPKBean);
        return mapping.findForward("personCreated");
    }
    
}
