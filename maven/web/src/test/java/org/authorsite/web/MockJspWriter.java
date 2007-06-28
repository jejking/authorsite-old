/*
 * MockJspWriter.java
 *
 * Created on 16 June 2007, 23:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.jsp.JspWriter;

/**
 *
 * @author jejking
 */
public class MockJspWriter extends JspWriter {
    
    
    private PrintWriter printWriter;
    
    private StringWriter stringWriter;
    
    public MockJspWriter(int bufferSize, boolean autoFlush)  {
        super(bufferSize, autoFlush);
        this.stringWriter = new StringWriter();
        this.printWriter = new PrintWriter (stringWriter);
    }
    
    
    public void newLine() throws IOException {
        this.printWriter.println();
    }

    public void print(boolean b) throws IOException {
        this.printWriter.print(b);
    }

    public void print(char c) throws IOException {
        this.printWriter.print(c);
    }

    public void print(int i) throws IOException {
        this.printWriter.print(i);
    }

    public void print(long l) throws IOException {
        this.printWriter.print(l);
    }

    public void print(float f) throws IOException {
        this.printWriter.print(f);
    }

    public void print(double d) throws IOException {
        this.printWriter.print(d);
    }

    public void print(char[] c) throws IOException {
        this.printWriter.print(c);
    }

    public void print(String string) throws IOException {
        this.printWriter.print(string);
    }

    public void print(Object object) throws IOException {
        this.printWriter.print(object);
    }

    public void println() throws IOException {
        this.printWriter.println();
    }

    public void println(boolean b) throws IOException {
        this.printWriter.println(b);
    }

    public void println(char c) throws IOException {
        this.printWriter.println(c);
    }

    public void println(int i) throws IOException {
        this.printWriter.println(i);
    }

    public void println(long l) throws IOException {
        this.printWriter.println(l);
    }

    public void println(float f) throws IOException {
        this.printWriter.println(f);
    }

    public void println(double d) throws IOException {
        this.printWriter.println(d);
    }

    public void println(char[] c) throws IOException {
        this.printWriter.println(c);
    }

    public void println(String string) throws IOException {
       this.printWriter.println(string); 
    }

    public void println(Object object) throws IOException {
        this.printWriter.println(object);
    }

    public void clear() throws IOException {
        // do nothing
    }

    public void clearBuffer() throws IOException {
        // do nothing
    }

    public void flush() throws IOException {
        // do nothing
    }

    public void close() throws IOException {
        this.printWriter.close();
    }

    public int getRemaining() {
        return -1;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        this.printWriter.write(cbuf, off, len);
    }
    
    public String getPrinted() {
        return this.stringWriter.toString();
    }
    
}
