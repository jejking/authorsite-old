/*
 * EmailLanguage.java, created on 07-Mar-2004 at 21:51:41
 * 
 * Copyright John King, 2004.
 *
 *  EmailLanguage.java is part of authorsite.org's MailArchive program.
 *
 *  VocabManager is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  VocabManager is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.mailarchive.model;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;
import org.apache.log4j.*;
import org.authorsite.mailarchive.model.impl.*;


/**
 * Abstraction of a Language.
 * 
 * <p>Abstracts a language without the complexity of <code>java.util.Locale</code>.
 * A language is represented simply by its two-letter ISO 639 code.</p>
 * 
 * <p>Implementation uses the TypeSafeEnum pattern. Only one instance of each language should only 
 * occur in the MailArchive system and each one should be immutable, unless of course, someone
 * documents the emergence of a brand new language with a two-letter ISO code.</p>
 * 
 * <p>The use of only two-letter ISO codes is a limitation, but should be sufficient for
 * an email archive. Additionally, the two-letter codes can be used in the contructor
 * of <code>java.util.Locale</code> - for instance to obtain a natural language representation
 * of the Language in question.</p>
 * 
 * @hibernate.class table="Language" mutable="false"
 * @hibernate.cache usage="read-only"
 *
 * @see java.util.Locale
 * @author jejking
 * @version $Revision: 1.7 $
 */
public class Language implements Serializable {
	
    private String isoCode;
    private String englishName;
    private Integer languageID;
    
    private Logger logger = Logger.getLogger(org.authorsite.mailarchive.model.Language.class);
    
    /**
     * Required by Hibernate's reflection so we'd better have it
     */
    private Language() {
        logger.debug("no-arg constructor on Language called");
    }
    
    private void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
        englishName=Locale.ENGLISH.getDisplayLanguage(new Locale(isoCode));
    }
    
    private Language(String isoCode) {
        setIsoCode(isoCode);
    }
    
    private Language(String isoCode, Integer newLanguageID) {
        this(isoCode);
        this.languageID = newLanguageID;
    }
    
    /**
	 * @hibernate.id column = "LanguageID" generator-class="native"
	 * @see org.authorsite.mailarchive.model.Identifiable#getID()
	 */
	private Integer getLanguageID() {
		return languageID;
	}
	
	private void setLanguageID(Integer newLanguageID) {
	    languageID = newLanguageID;
	}
    
    /**
     * @hibernate.property column="isoCode" not-null="true" unique="true"
     * @return
     */
	public String getIsoCode() {
	    return isoCode;
	}
	
	public String toString() {
	    return englishName;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof Language) {
			Language input = (Language) o;
			return new EqualsBuilder().append(isoCode, input.isoCode).isEquals();
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(isoCode).toHashCode();
	}
	
	// generated from Locale.getISOLanguages();
	public static final Language AA = new Language("aa", new Integer(0));
	public static final Language AB = new Language("ab", new Integer(1));
	public static final Language AF = new Language("af", new Integer(2));
	public static final Language AM = new Language("am", new Integer(3));
	public static final Language AR = new Language("ar", new Integer(4));
	public static final Language AS = new Language("as", new Integer(5));
	public static final Language AY = new Language("ay", new Integer(6));
	public static final Language AZ = new Language("az", new Integer(7));
	public static final Language BA = new Language("ba", new Integer(8));
	public static final Language BE = new Language("be", new Integer(9));
	public static final Language BG = new Language("bg", new Integer(10));
	public static final Language BH = new Language("bh", new Integer(11));
	public static final Language BI = new Language("bi", new Integer(12));
	public static final Language BN = new Language("bn", new Integer(13));
	public static final Language BO = new Language("bo", new Integer(14));
	public static final Language BR = new Language("br", new Integer(15));
	public static final Language CA = new Language("ca", new Integer(16));
	public static final Language CO = new Language("co", new Integer(17));
	public static final Language CS = new Language("cs", new Integer(18));
	public static final Language CY = new Language("cy", new Integer(19));
	public static final Language DA = new Language("da", new Integer(20));
	public static final Language DE = new Language("de", new Integer(21));
	public static final Language DZ = new Language("dz", new Integer(22));
	public static final Language EL = new Language("el", new Integer(23));
	public static final Language EN = new Language("en", new Integer(24));
	public static final Language EO = new Language("eo", new Integer(25));
	public static final Language ES = new Language("es", new Integer(26));
	public static final Language ET = new Language("et", new Integer(27));
	public static final Language EU = new Language("eu", new Integer(28));
	public static final Language FA = new Language("fa", new Integer(29));
	public static final Language FI = new Language("fi", new Integer(30));
	public static final Language FJ = new Language("fj", new Integer(31));
	public static final Language FO = new Language("fo", new Integer(32));
	public static final Language FR = new Language("fr", new Integer(33));
	public static final Language FY = new Language("fy", new Integer(34));
	public static final Language GA = new Language("ga", new Integer(35));
	public static final Language GD = new Language("gd", new Integer(36));
	public static final Language GL = new Language("gl", new Integer(37));
	public static final Language GN = new Language("gn", new Integer(38));
	public static final Language GU = new Language("gu", new Integer(39));
	public static final Language HA = new Language("ha", new Integer(40));
	public static final Language HE = new Language("he", new Integer(41));
	public static final Language HI = new Language("hi", new Integer(42));
	public static final Language HR = new Language("hr", new Integer(43));
	public static final Language HU = new Language("hu", new Integer(44));
	public static final Language HY = new Language("hy", new Integer(45));
	public static final Language IA = new Language("ia", new Integer(46));
	public static final Language ID = new Language("id", new Integer(47));
	public static final Language IE = new Language("ie", new Integer(48));
	public static final Language IK = new Language("ik", new Integer(49));
	public static final Language IN = new Language("in", new Integer(50));
	public static final Language IS = new Language("is", new Integer(51));
	public static final Language IT = new Language("it", new Integer(52));
	public static final Language IU = new Language("iu", new Integer(53));
	public static final Language IW = new Language("iw", new Integer(54));
	public static final Language JA = new Language("ja", new Integer(55));
	public static final Language JI = new Language("ji", new Integer(56));
	public static final Language JW = new Language("jw", new Integer(57));
	public static final Language KA = new Language("ka", new Integer(58));
	public static final Language KK = new Language("kk", new Integer(59));
	public static final Language KL = new Language("kl", new Integer(60));
	public static final Language KM = new Language("km", new Integer(61));
	public static final Language KN = new Language("kn", new Integer(62));
	public static final Language KO = new Language("ko", new Integer(63));
	public static final Language KS = new Language("ks", new Integer(64));
	public static final Language KU = new Language("ku", new Integer(65));
	public static final Language KY = new Language("ky", new Integer(66));
	public static final Language LA = new Language("la", new Integer(67));
	public static final Language LN = new Language("ln", new Integer(68));
	public static final Language LO = new Language("lo", new Integer(69));
	public static final Language LT = new Language("lt", new Integer(70));
	public static final Language LV = new Language("lv", new Integer(71));
	public static final Language MG = new Language("mg", new Integer(72));
	public static final Language MI = new Language("mi", new Integer(73));
	public static final Language MK = new Language("mk", new Integer(74));
	public static final Language ML = new Language("ml", new Integer(75));
	public static final Language MN = new Language("mn", new Integer(76));
	public static final Language MO = new Language("mo", new Integer(77));
	public static final Language MR = new Language("mr", new Integer(78));
	public static final Language MS = new Language("ms", new Integer(79));
	public static final Language MT = new Language("mt", new Integer(80));
	public static final Language MY = new Language("my", new Integer(81));
	public static final Language NA = new Language("na", new Integer(82));
	public static final Language NE = new Language("ne", new Integer(83));
	public static final Language NL = new Language("nl", new Integer(84));
	public static final Language NO = new Language("no", new Integer(85));
	public static final Language OC = new Language("oc", new Integer(86));
	public static final Language OM = new Language("om", new Integer(87));
	public static final Language OR = new Language("or", new Integer(88));
	public static final Language PA = new Language("pa", new Integer(89));
	public static final Language PL = new Language("pl", new Integer(90));
	public static final Language PS = new Language("ps", new Integer(91));
	public static final Language PT = new Language("pt", new Integer(92));
	public static final Language QU = new Language("qu", new Integer(93));
	public static final Language RM = new Language("rm", new Integer(94));
	public static final Language RN = new Language("rn", new Integer(95));
	public static final Language RO = new Language("ro", new Integer(96));
	public static final Language RU = new Language("ru", new Integer(97));
	public static final Language RW = new Language("rw", new Integer(98));
	public static final Language SA = new Language("sa", new Integer(99));
	public static final Language SD = new Language("sd", new Integer(100));
	public static final Language SG = new Language("sg", new Integer(101));
	public static final Language SH = new Language("sh", new Integer(102));
	public static final Language SI = new Language("si", new Integer(103));
	public static final Language SK = new Language("sk", new Integer(104));
	public static final Language SL = new Language("sl", new Integer(105));
	public static final Language SM = new Language("sm", new Integer(106));
	public static final Language SN = new Language("sn", new Integer(107));
	public static final Language SO = new Language("so", new Integer(108));
	public static final Language SQ = new Language("sq", new Integer(109));
	public static final Language SR = new Language("sr", new Integer(110));
	public static final Language SS = new Language("ss", new Integer(111));
	public static final Language ST = new Language("st", new Integer(112));
	public static final Language SU = new Language("su", new Integer(113));
	public static final Language SV = new Language("sv", new Integer(114));
	public static final Language SW = new Language("sw", new Integer(115));
	public static final Language TA = new Language("ta", new Integer(116));
	public static final Language TE = new Language("te", new Integer(117));
	public static final Language TG = new Language("tg", new Integer(118));
	public static final Language TH = new Language("th", new Integer(119));
	public static final Language TI = new Language("ti", new Integer(120));
	public static final Language TK = new Language("tk", new Integer(121));
	public static final Language TL = new Language("tl", new Integer(122));
	public static final Language TN = new Language("tn", new Integer(123));
	public static final Language TO = new Language("to", new Integer(124));
	public static final Language TR = new Language("tr", new Integer(125));
	public static final Language TS = new Language("ts", new Integer(126));
	public static final Language TT = new Language("tt", new Integer(127));
	public static final Language TW = new Language("tw", new Integer(128));
	public static final Language UG = new Language("ug", new Integer(129));
	public static final Language UK = new Language("uk", new Integer(130));
	public static final Language UR = new Language("ur", new Integer(131));
	public static final Language UZ = new Language("uz", new Integer(132));
	public static final Language VI = new Language("vi", new Integer(133));
	public static final Language VO = new Language("vo", new Integer(134));
	public static final Language WO = new Language("wo", new Integer(135));
	public static final Language XH = new Language("xh", new Integer(136));
	public static final Language YI = new Language("yi", new Integer(137));
	public static final Language YO = new Language("yo", new Integer(138));
	public static final Language ZA = new Language("za", new Integer(139));
	public static final Language ZH = new Language("zh", new Integer(140));
	public static final Language ZU = new Language("zu", new Integer(141));
		
}
