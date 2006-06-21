/*
 * MediaItemManagementGetDetailsAction.java
 *
 * Created on 27 March 2003, 16:58
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/MediaItemManagementGetDetailsAction.java,v 1.1 2003/03/29 13:00:31 jejking Exp $
 */

package org.authorsite.bib.web.struts.action;
import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.authorsite.bib.dto.*;
import org.authorsite.bib.ejb.facade.*;
import org.authorsite.bib.web.struts.form.*;
import org.authorsite.bib.web.struts.util.*;
import org.authorsite.bib.web.util.EJBHomeFactory;
/**
 * Fetches media item details from the UnpublishedMeidaItemSearchFacade.
 * 
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class MediaItemManagementGetDetailsAction extends BibAbstractAction {
    
    /** Creates a new instance of MediaItemPublicGetDetailsAction */
    public MediaItemManagementGetDetailsAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // get mediaItemID from form
        String mediaItemID = ((MediaItemGetDetailsForm)form).getMediaItemID();
        
        // get a reference to the published media item search facade EJB
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        UnpublishedMediaItemSearchFacadeHome home = (UnpublishedMediaItemSearchFacadeHome) ejbHomeFactory.lookupHome("ejb/UnpublishedMediaItemSearchFacadeEJB", UnpublishedMediaItemSearchFacadeHome.class);
        UnpublishedMediaItemSearchFacade facade = home.create();
                
        MediaItemDTO dto = facade.findMediaItemByMediaItemID(Integer.parseInt(mediaItemID));
        
        if (dto == null) {
            return mapping.findForward("nothingFound");
        }
        else {
            request.setAttribute("CurrentMediaItemDTO", dto);
            return mapping.findForward("gotDetails");
        }
        
    }
    
}
