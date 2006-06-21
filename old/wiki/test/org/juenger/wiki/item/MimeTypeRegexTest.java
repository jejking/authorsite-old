package org.juenger.wiki.item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class MimeTypeRegexTest extends TestCase {

	Pattern tokenPattern = Pattern.compile("([\\p{Graph}&&[^()/<>@,;:\\\"/\\[\\]?=\\\\]])+");
    Pattern mimeTypePattern = Pattern.compile("([\\p{Graph}&&[^()/<>@,;:\\\"/\\[\\]?=\\\\]])+/([\\p{Graph}&&[^()/<>@,;:\\\"/\\[\\]?=\\\\]])+");
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(MimeTypeRegexTest.class);
	}

	public void testTokenMatchingCorrectTokens() {
		/**
		 * token := 1*<any (US-ASCII) CHAR except SPACE, CTLs,
                 or tspecials>

     tspecials :=  "(" / ")" / "<" / ">" / "@" /
                   "," / ";" / ":" / "\" / <">
                   "/" / "[" / "]" / "?" / "="
                   
                   
         = all visible chars \p{Graph} - the tspecials          
                   
		 */
		
		Matcher allLetters = tokenPattern.matcher("abcde");
		assertTrue(allLetters.matches());
		
		Matcher allNumbers = tokenPattern.matcher("12345");
		assertTrue(allNumbers.matches());
		
		Matcher mixedLettersAndNumbers = tokenPattern.matcher("abcd1234");
		assertTrue(mixedLettersAndNumbers.matches());
		
		Matcher singleLetter = tokenPattern.matcher("A");
		assertTrue(singleLetter.matches());
		
		Matcher singleNumber = tokenPattern.matcher("1");
		assertTrue(singleNumber.matches());
		
		Matcher lettersWithPlusSign = tokenPattern.matcher("abcd+edf");
		assertTrue(lettersWithPlusSign.matches());
		
		Matcher lettersWithMinusSign = tokenPattern.matcher("abcd-123");
		assertTrue(lettersWithMinusSign.matches());
		
	}
	
	public void testTokenMatchingWrongChars() {
		
		Matcher emptyMatcher = tokenPattern.matcher("");
		assertFalse(emptyMatcher.matches());
		
		Matcher oneSpaceMatcher = tokenPattern.matcher(" ");
		assertFalse(oneSpaceMatcher.matches());
		
		Matcher stringWithSpaceMatcher = tokenPattern.matcher("abc def");
		assertFalse(stringWithSpaceMatcher.matches());
		
		Matcher stringWithTabMatcher = tokenPattern.matcher("abc\tedf");
		assertFalse(stringWithTabMatcher.matches());
		
		Matcher stringWithCarriageReturnMatcher = tokenPattern.matcher("a\r2");
		assertFalse(stringWithCarriageReturnMatcher.matches());
		
		Matcher stringWithNewLineMatcher = tokenPattern.matcher("a\n2");
		assertFalse(stringWithNewLineMatcher.matches());
		
		Matcher openRoundBracketMatcher = tokenPattern.matcher("a(b");
		assertFalse(openRoundBracketMatcher.matches());
		
		Matcher closeRoundBracketMatcher = tokenPattern.matcher("a)b");
		assertFalse(closeRoundBracketMatcher.matches());
		
		Matcher lessThanMatcher = tokenPattern.matcher("a<b");
		assertFalse(lessThanMatcher.matches());
		
		Matcher greaterThanMatcher = tokenPattern.matcher("a>b");
		assertFalse(greaterThanMatcher.matches());
		
		Matcher atSignMatcher = tokenPattern.matcher("a@b");
		assertFalse(atSignMatcher.matches());
		
		Matcher commaMatcher = tokenPattern.matcher("a,b");
		assertFalse(commaMatcher.matches());
		
		Matcher semiColonMatcher = tokenPattern.matcher("a;b");
		assertFalse(semiColonMatcher.matches());
	
		Matcher colonMatcher = tokenPattern.matcher("a:b");
		assertFalse(colonMatcher.matches());
		
		Matcher backSlashMatcher = tokenPattern.matcher("a\\b");
		assertFalse(backSlashMatcher.matches());
		
		Matcher forwardSlashMatcher = tokenPattern.matcher("a/b");
		assertFalse(forwardSlashMatcher.matches());
		
		Matcher quoteMatcher = tokenPattern.matcher("a\"b");
		assertFalse(quoteMatcher.matches());

		Matcher openSquareBracketMatcher = tokenPattern.matcher("a[b");
		assertFalse(openSquareBracketMatcher.matches());
		
		Matcher closeSquareBracketMatcher = tokenPattern.matcher("a]b");
		assertFalse(closeSquareBracketMatcher.matches());
		
		Matcher questionMarkSquareBracketMatcher = tokenPattern.matcher("a?b");
		assertFalse(questionMarkSquareBracketMatcher.matches());
	
		Matcher equalsSignMatcher = tokenPattern.matcher("a=c");
		assertFalse(equalsSignMatcher.matches());
		
		Matcher nonAsciiCharacterMatcher = tokenPattern.matcher("a\u05D9b"); // a hebrew char
        assertFalse(nonAsciiCharacterMatcher.matches());
		
	}
    
    public void testMimeTypesCorrect() {
        
        // some of the weirder mime types from iana
        Matcher atomXml = mimeTypePattern.matcher("application/atom+xml");
        assertTrue(atomXml.matches());
        
        Matcher batchSMTP = mimeTypePattern.matcher("application/batch-SMTP");
        assertTrue(batchSMTP.matches());
        
        Matcher dialogInfoXML = mimeTypePattern.matcher("application/dialog-info+xml");
        assertTrue(dialogInfoXML.matches());
        
        Matcher DIX12 = mimeTypePattern.matcher("application/DI-X12");
        assertTrue(DIX12.matches());
        
        Matcher prsAlv = mimeTypePattern.matcher("application/prs.alvestrand.titrax-sheet");
        assertTrue(prsAlv.matches());
        
        Matcher wordPerfect = mimeTypePattern.matcher("application/wordperfect5.1");
        assertTrue(wordPerfect.matches());
        
        Matcher vndSyncMl = mimeTypePattern.matcher("application/vnd.syncml.+xml");
        assertTrue(vndSyncMl.matches());
    }
    
    public void testMimeTypePatternWrong() {
        
        Matcher noSlash = mimeTypePattern.matcher("applicationoctet-stream");
        assertFalse(noSlash.matches());
        
        Matcher twoSlashes = mimeTypePattern.matcher("application//octet-stream");
        assertFalse(twoSlashes.matches());
        
        Matcher nothingBeforeSlash = mimeTypePattern.matcher("/octet-stream");
        assertFalse(nothingBeforeSlash.matches());
        
        Matcher nothingAfterSlash = mimeTypePattern.matcher("application/");
        assertFalse(nothingAfterSlash.matches());
        
        Matcher multipleSlashes = mimeTypePattern.matcher("application/octet/stream");
        assertFalse(multipleSlashes.matches());
        
    }
}
