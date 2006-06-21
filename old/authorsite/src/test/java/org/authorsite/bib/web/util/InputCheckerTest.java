/*
 * InputCheckerTest.java
 * JUnit based test
 *
 * Created on 31 January 2003, 09:42
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/web/util/InputCheckerTest.java,v 1.1 2003/01/31 16:51:23 jejking Exp $
 */

package org.authorsite.bib.web.util;

import junit.framework.*;

/**
 *
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class InputCheckerTest extends TestCase {
    
    public InputCheckerTest(java.lang.String testName) {
        super(testName);
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(InputCheckerTest.class);
        return suite;
    }
    
    public void testCleanName_singleWord() {
        String input = "word";
        String output= InputChecker.cleanName(input);
        Assert.assertEquals(input, output);
    }
    
    public void testCleanName_multipleWordsProperlySpaced() {
        String input = "more than one word";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(input, output);
    }
    
    public void testCleanName_randomWhiteSpace() {
        String input = "there is    some  random \t white space \n in this text";
        String expectedOutput = "there is    some  random  white space  in this text";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(expectedOutput, output);
    }
    
    public void testCleanName_htmlTags() {
        String input = "<h1>wibble</h1><p class=\"paragraph\">hello. this is a paragraph</p>";
        // we expect the tags and their content to be removed
        String expectedOutput = "wibblehello this is a paragraph";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(expectedOutput, output);
    }
    
    public void testCleanName_percent() {
        String input = "wibble blah% blah";
        String expectedOutput = "wibble blah blah";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(expectedOutput, output);
    }
    
    public void testCleanName_singleHyphenNotInName() {
        String input = "Wibble Wibble -Wibble - blahWibble";
        String expectedOutput = "Wibble Wibble Wibble  blahWibble";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(expectedOutput, output);
    }
    
    public void testCleanName_singleHypenInName() {
        String input = "Thingy-Wingy";
        String output = InputChecker.cleanName(input);
        // there should be no change
        Assert.assertEquals(input, output);
    }
    
    public void testCleanName_sqlComment() {
        String input = "Wibble blah; --select blah from thing";
        String expectedOutput = "Wibble blah select blah from thing";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(expectedOutput, output);
    }
    
    public void testCleanName_numbers() {
        String input = "wibble 665 blah666 blah 834";
        String expectedOutput = "wibble  blah blah ";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(expectedOutput, output);
    }
    
    public void testCleanName_randomPunctuation() {
        String input = "wibble, there is some blah. Is there? Yes!! How sh*t. (oh no, can't swear): {5+3}";
        String expectedOutput = "wibble there is some blah Is there Yes How sht oh no can't swear ";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(expectedOutput, output);
    }
    
    public void testCleanName_irishName() {
        String input = "O'Reilly";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(input, output);
    }
    
    public void testCleanName_singleQuoteInWrongPlace() {
        String input = "there is a ' quote here. and ' one 'here";
        String expectedOutput = "there is a  quote here and  one here";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(expectedOutput, output);
    }
    
    public void testCleanName_quotes() {
        String input = "there is a \" quote here and \"some round these words\"";
        String expectedOutput = "there is a  quote here and some round these words";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(expectedOutput, output);
    }
    
    public void testCleanName_backquotes() {
        String input = "this text contains `backquotes` this ' is very ``bad";
        String expectedOutput = "this text contains backquotes this  is very bad";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(expectedOutput, output);
    }
    
    public void testCleanName_pipes() {
        String input = "this text | has got || lots of pipe|s in it";
        String expectedOutput = "this text  has got  lots of pipes in it";
        String output = InputChecker.cleanName(input);
        Assert.assertEquals(expectedOutput, output);
    }
}