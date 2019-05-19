package com.coat.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


public class JSONPResponseWrapper extends HttpServletResponseWrapper {  
    public static final int OT_NONE = 0, OT_WRITER = 1, OT_STREAM = 2;  
    private int outputType = OT_NONE;  
    private ServletOutputStream output = null;  
    private PrintWriter writer = null;  
    private ByteArrayOutputStream buffer = null;  
    private int statusCode;
    public JSONPResponseWrapper(HttpServletResponse resp) throws IOException {  
        super(resp);
        this.statusCode=200;
        buffer = new ByteArrayOutputStream();  
        
    }  
  
    @Override  
    public PrintWriter getWriter() throws IOException {  
        if (outputType == OT_STREAM)  
            throw new IllegalStateException();  
        else if (outputType == OT_WRITER)  
            return writer;  
        else {  
            outputType = OT_WRITER;  
            writer = new PrintWriter(new OutputStreamWriter(buffer,  
                    getCharacterEncoding()));  
            
            return writer;  
        }  
    }  
    
    public String getReturnType(){
    	
    	return outputType+"";
    }
  
    @Override
    public void sendError(int sc) throws IOException {
     statusCode = sc;
     super.sendError(sc);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
     statusCode = sc;
     super.sendError(sc, msg);
    }

    @Override
    public void setStatus(int sc) {
     this.statusCode = sc;
     super.setStatus(sc);
    }

    public int getStatusCode() {
     return this.statusCode;
    }
    
    
    
    
    
    
    
    
    
    @Override  
    public ServletOutputStream getOutputStream() throws IOException {  
        if (outputType == OT_WRITER)  
            throw new IllegalStateException();  
        else if (outputType == OT_STREAM)  
            return output;  
        else {  
            outputType = OT_STREAM;  
            output = new WrappedOutputStream(buffer);  
            return output;  
        }  
    }  
  
    @Override  
    public void flushBuffer() throws IOException {  
        if (outputType == OT_WRITER)  
            writer.flush();  
        if (outputType == OT_STREAM)  
            output.flush();  
    }  
  
    @Override  
    public void reset() {  
        outputType = OT_NONE;  
        buffer.reset();  
    }  
  
    public byte[] getResponseData() throws IOException {  
        flushBuffer();  
        return buffer.toByteArray();  
    }  
  
    class WrappedOutputStream extends ServletOutputStream {  
        private ByteArrayOutputStream buffer;  
  
        public WrappedOutputStream(ByteArrayOutputStream buffer) {  
            this.buffer = buffer;  
        }  
  
        public void write(int b) throws IOException {  
            buffer.write(b);  
        }  
  
        public byte[] toByteArray() {  
            return buffer.toByteArray();  
        }  
    }  
}  
