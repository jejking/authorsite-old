/*
 * PublishedMediaItemSearchFacadeBean.java
 *
 * Created on 03 December 2002, 10:16
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/facade/PublishedMediaItemSearchFacadeBean.java,v 1.7 2003/03/29 12:01:48 jejking Exp $
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

package org.authorsite.bib.ejb.facade;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import org.authorsite.bib.dto.*;
import org.authorsite.bib.ejb.entity.*;
import org.authorsite.bib.ejb.services.dto.*;

/**
 * @ejb.bean    name="PublishedMediaItemSearchFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/PublishedMediaItemSearchFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.facade.PublishedMediaItemSearchFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.facade.PublishedMediaItemSearchFacadeHome"
 *                  generate="remote"
 *
 * @ejb:ejb-ref ejb-name="MediaItemEJB"
 *              ref-name="ejb/MyMediaItemEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="LanguageEJB"
 *              ref-name="ejb/MyLanguageEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="PersonEJB"
 *              ref-name="ejb/MyPersonEJB"
 *              view-type="local"
 *
 * @ejb.ejb-ref ejb-name="OrganisationEJB"
 *              ref-name="ejb/MyOrganisationEJB"
 *              view-type="local"
 *
 * @author  jejking
 * @version $Revision: 1.7 $
 */
public class PublishedMediaItemSearchFacadeBean implements SessionBean {
    
    private SessionContext ctx;
    private MediaItemLocalHome mediaItemLocalHome;
    private LanguageLocalHome languageLocalHome;
    private PersonLocalHome personLocalHome;
    private OrganisationLocalHome organisationLocalHome;
    
    /**
     * @ejb.interface-method view-type="remote"
     *
     * Locates a MediaItem by its primary key. If the item is not actually published,
     * then returns null. This is because this facade is for public consumption and the 
     * public should only be able to see complete, published items.
     */
    public MediaItemDTO findMediaItemByMediaItemID(int mediaItemID) throws FinderException {
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(new Integer(mediaItemID));
        if (item.getPublishedFlag()) {
            MediaItemDTOAssembler assembler = new MediaItemDTOAssembler(item);
            return assembler.assembleDTO();
        }
        else {
            return null;
        }
    }
    
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitle (String title) throws FinderException {
        Collection items = mediaItemLocalHome.findByTitle(title, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleType (String title, String type) throws FinderException {
        Collection items = mediaItemLocalHome.findByTitleType(title, type, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleLanguage(String title, String iso639) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTitleLanguage(title, language, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleExactYear(String title, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByTitleExactYear(title, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleBeforeYear(String title, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByTitleBeforeYear(title, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleAfterYear(String title, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByTitleAfterYear(title, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleBetweenYears(String title, int firstYear, int lastYear) throws FinderException {
        Collection items = mediaItemLocalHome.findByTitleBetweenYears(title, firstYear, lastYear, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitle(String title) throws FinderException {
        Collection items = mediaItemLocalHome.findByLikeTitle(title, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleType(String title, String type) throws FinderException {
        Collection items = mediaItemLocalHome.findByLikeTitleType(title, type, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleLanguage(String title, String iso639) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLikeTitleLanguage(title, language, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleExactYear(String title, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByLikeTitleExactYear(title, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleBeforeYear(String title, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByLikeTitleBeforeYear(title, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleAfterYear(String title, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByLikeTitleAfterYear(title, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleBetweenYears(String title, int firstYear, int lastYear) throws FinderException {
        Collection items = mediaItemLocalHome.findByLikeTitleBetweenYears(title, firstYear, lastYear, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByType(String type) throws FinderException {
        Collection items = mediaItemLocalHome.findByType(type, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTypeLanguage(String type, String iso639) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTypeLanguage(type, language, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTypeExactYear(String type, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByTypeExactYear(type, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTypeBeforeYear(String type, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByTypeBeforeYear(type, year, true);
        return buildDTOList(items);
    }
    
    /** 
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTypeAfterYear(String type, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByTypeAfterYear(type, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTypeBetweenYears(String type, int firstYear, int lastYear) throws FinderException {
        Collection items = mediaItemLocalHome.findByTypeBetweenYears(type, firstYear, lastYear, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLanguage(String iso639) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLanguage(language, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLanguageExactYear(String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLanguageExactYear(language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLanguageBeforeYear(String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLanguageBeforeYear(language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLanguageAfterYear(String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLanguageAfterYear(language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLanguageBetweenYears(String iso639, int firstYear, int lastYear) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLanguageBetweenYears(language, firstYear, lastYear, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleTypeLanguage(String title, String type, String iso639) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTitleTypeLanguage(title, type, language, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleTypeExactYear(String title, String type, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByTitleTypeExactYear(title, type, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleTypeBeforeYear(String title, String type, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByTitleTypeBeforeYear(title, type, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleTypeAfterYear(String title, String type, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByTitleTypeAfterYear(title, type, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleTypeBetweenYears(String title, String type, int firstYear, int lastYear) throws FinderException {
        Collection items = mediaItemLocalHome.findByTitleTypeBetweenYears(title, type, firstYear, lastYear, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleTypeLanguage(String title, String type, String iso639) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLikeTitleTypeLanguage(title, type, language, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleTypeExactYear(String title, String type, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByLikeTitleTypeExactYear(title, type, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleTypeBeforeYear(String title, String type, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByLikeTitleTypeExactYear(title, type, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleTypeAfterYear(String title, String type, int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByLikeTitleTypeAfterYear(title, type, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleTypeBetweenYears(String title, String type, int firstYear, int lastYear) throws FinderException {
        Collection items = mediaItemLocalHome.findByLikeTitleTypeBetweenYears(title, type, firstYear, lastYear, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTypeLanguageExactYear(String type, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTypeLanguageExactYear(type, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTypeLanguageBeforeYear(String type, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTypeLanguageBeforeYear(type, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTypeLanguageAfterYear(String type, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTypeLanguageAfterYear(type, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTypeLanguageBetweenYears(String type, String iso639, int firstYear, int lastYear) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTypeLanguageBetweenYears(type, language, firstYear, lastYear, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleLanguageExactYear(String title, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTitleLanguageExactYear(title, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleLanguageBeforeYear(String title, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTitleLanguageBeforeYear(title, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleLanguageAfterYear(String title, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTitleLanguageAfterYear(title, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleLanguageBetweenYears(String title, String iso639, int firstYear, int lastYear) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTitleLanguageBetweenYears(title, language, firstYear, lastYear, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleLanguageExactYear(String title, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLikeTitleLanguageExactYear(title, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleLanguageBeforeYear(String title, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLikeTitleLanguageBeforeYear(title, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleLanguageAfterYear(String title, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLikeTitleLanguageAfterYear(title, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleLanguageBetweenYears(String title, String iso639, int firstYear, int lastYear) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLikeTitleLanguageBetweenYears(title, language, firstYear, lastYear, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleTypeLanguageExactYear(String title, String type, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTitleTypeLanguageExactYear(title, type, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleTypeLanguageBeforeYear(String title, String type, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTitleTypeLanguageBeforeYear(title, type, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleTypeLanguageAfterYear(String title, String type, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTitleTypeLanguageAfterYear(title, type, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByTitleTypeLanguageBetweenYears(String title, String type, String iso639, int firstYear, int lastYear) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByTitleTypeLanguageBetweenYears(title, type, language, firstYear, lastYear, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleTypeLanguageExactYear(String title, String type, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLikeTitleTypeLanguageExactYear(title, type, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleTypeLanguageBeforeYear(String title, String type, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLikeTitleTypeLanguageBeforeYear(title, type, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleTypeLanguageAfterYear(String title, String type, String iso639, int year) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLikeTitleTypeLanguageAfterYear(title, type, language, year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByLikeTitleTypeLanguageBetweenYears(String title, String type, String iso639, int firstYear, int lastYear) throws FinderException {
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByLikeTitleTypeLanguageBetweenYears(title, type, language, firstYear, lastYear, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByExactYear(int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByExactYear(year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByBeforeYear(int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByBeforeYear(year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByAfterYear(int year) throws FinderException {
        Collection items = mediaItemLocalHome.findByAfterYear(year, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.inteface-method view-type="remote"
     */
    public Collection findByBetweenYears(int firstYear, int lastYear) throws FinderException {
        Collection items = mediaItemLocalHome.findByBetweenYears(firstYear, lastYear, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByPerson(int personID) throws FinderException {
        PersonLocal person = personLocalHome.findByPrimaryKey(new Integer(personID));
        Collection items = mediaItemLocalHome.findByPerson(person, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByPersonRelationship(int personID, String relationshipType) throws FinderException {
        PersonLocal person = personLocalHome.findByPrimaryKey(new Integer(personID));
        Collection items = mediaItemLocalHome.findByPersonRelationship(person, relationshipType, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByPersonTitle(int personID, String title) throws FinderException {
        PersonLocal person = personLocalHome.findByPrimaryKey(new Integer(personID));
        Collection items = mediaItemLocalHome.findByPersonTitle(person, title, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByPersonLikeTitle(int personID, String title) throws FinderException {
        PersonLocal person = personLocalHome.findByPrimaryKey(new Integer(personID));
        Collection items = mediaItemLocalHome.findByPersonLikeTitle(person, title, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByPersonLanguage(int personID, String iso639) throws FinderException {
        PersonLocal person = personLocalHome.findByPrimaryKey(new Integer(personID));
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByPersonLanguage(person, language, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByPersonType(int personID, String type) throws FinderException {
        PersonLocal person = personLocalHome.findByPrimaryKey(new Integer(personID));
        Collection items = mediaItemLocalHome.findByPersonType(person, type, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByOrganisation(int orgID) throws FinderException {
        OrganisationLocal org = organisationLocalHome.findByPrimaryKey(new Integer(orgID));
        Collection items = mediaItemLocalHome.findByOrganisation(org, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByOrganisationRelationship(int orgID, String relationship) throws FinderException {
        OrganisationLocal org = organisationLocalHome.findByPrimaryKey(new Integer(orgID));
        Collection items = mediaItemLocalHome.findByOrganisationRelationship(org, relationship, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByOrganisationTitle(int orgID, String title) throws FinderException {
        OrganisationLocal org = organisationLocalHome.findByPrimaryKey(new Integer(orgID));
        Collection items = mediaItemLocalHome.findByOrganisationTitle(org, title, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByOrganisationLikeTitle(int orgID, String title) throws FinderException {
        OrganisationLocal org = organisationLocalHome.findByPrimaryKey(new Integer(orgID));
        Collection items = mediaItemLocalHome.findByOrganisationLikeTitle(org, title, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByOrganisationLanguage(int orgID, String iso639) throws FinderException {
        OrganisationLocal org = organisationLocalHome.findByPrimaryKey(new Integer(orgID));
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Collection items = mediaItemLocalHome.findByOrganisationLanguage(org, language, true);
        return buildDTOList(items);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findByOrganisationType(int orgID, String type) throws FinderException {
        OrganisationLocal org = organisationLocalHome.findByPrimaryKey(new Integer(orgID));
        Collection items = mediaItemLocalHome.findByOrganisationType(org, type, true);
        return buildDTOList(items);
    }
    
    
    public void ejbCreate() throws CreateException {
    }
    
    public void ejbActivate() throws EJBException {
        getLocalHomes();
    }
    
    public void ejbPassivate() throws EJBException {
        mediaItemLocalHome = null;
    }
    
    public void ejbRemove() throws EJBException {
        mediaItemLocalHome = null;
    }
    
    public void setSessionContext(SessionContext sessionContext) throws EJBException {
        ctx = sessionContext;
    }
    
    private void getLocalHomes() {
        try {
            InitialContext ctx = new InitialContext();
            mediaItemLocalHome = (MediaItemLocalHome) ctx.lookup("java:comp/env/ejb/MyMediaItemEJB");
            languageLocalHome = (LanguageLocalHome) ctx.lookup("java:comp/env/ejb/MyLanguageEJB");
            personLocalHome = (PersonLocalHome) ctx.lookup("java:comp/env/ejb/MyPersonEJB");
            organisationLocalHome = (OrganisationLocalHome) ctx.lookup("java:comp/env/ejb/MyOrganisationEJB");
        }
        catch (NamingException ne) {
            // oops. we don't want this to happen...
            ne.printStackTrace();
        }
    }
    
    private ArrayList buildDTOList(Collection items) {
        ArrayList dtoList = new ArrayList(items.size());
        Iterator itemsIt = items.iterator();
        while (itemsIt.hasNext()) {
            MediaItemLocal currentItem = (MediaItemLocal) itemsIt.next();
            MediaItemDTOAssembler assembler = new MediaItemDTOAssembler(currentItem);
            dtoList.add(assembler.assembleLightweightDTO());
        }
        return dtoList;
    }
    
    /** Creates a new instance of MediaItemConsumptionFacade */
    public PublishedMediaItemSearchFacadeBean() {
        getLocalHomes();
    }
    
}
