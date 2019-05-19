package com.coat.common.servlet;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.fop.svg.PDFTranscoder;

import util.Util;

import com.coat.interceptor.JSONPResponseWrapper;
public class HighchartsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
 
	public static void main(String[] args) {
		String a="Content-Disposition: form-data; name='filename'";
		 
	}
	
	public static String merge(String reg,String value){
		Pattern pattern = Pattern.compile(reg);
		String result="";
		if (!Util.objIsNULL(value))
		{
		//	System.out.println(value);
			Matcher matcher = pattern.matcher(value);
			while (matcher.find()){
				result=matcher.group();
				//System.out.println("====="+result);
			}
		}
		return result;
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 request.setCharacterEncoding("utf-8");//注意编码
			
	      response.setCharacterEncoding("utf-8");
		 String filename="";
	      String type = request.getParameter("type");
	      String svg = request.getParameter("svg");
	      int width=0;
	      int scale=0;
		 StringBuffer jb = new StringBuffer();
		 String line = null;
		 try {
		    BufferedReader reader = request.getReader();
		    String pre="";
		    int i=0;
		    while ((line = reader.readLine()) != null){
		      jb.append(line);
		     if(i%4==1){
		    	 pre=merge("\".*\"", line).replace("\"", "");
		    	// System.out.print(pre+":");
		     }
		     if(i%4==3){
		    	 if(!Util.objIsNULL(pre)){
		    		 if("filename".equalsIgnoreCase(pre)){
		    			 filename=line;
		    		 }else if("type".equalsIgnoreCase(pre)){
		    			 type=line;
		    		 }else if("svg".equalsIgnoreCase(pre)){
		    			 svg=line;
		    		 }else if("scale".equalsIgnoreCase(pre)){
		    			 scale=Integer.parseInt(line);
		    		 }else if("width".equalsIgnoreCase(pre)){
		    			 width=Integer.parseInt(line);
		    		 }
		    	 }
		    	// System.out.println(line);
		     }
		     i++;
		    }
		    //System.out.println(jb.toString());
		 } catch (Exception es) { //report an error }
			 es.printStackTrace();
		 }
			   

	      ServletOutputStream out = response.getOutputStream();
	      if (null != type && null != svg){
	      svg = svg.replaceAll(":rect", "rect");
	      String ext = "";
	      Transcoder t = null;
	     if (type.equals("image/png")) {
	         ext = "png";
	         t = new PNGTranscoder();    
	      } else if (type.equals("image/jpeg")) {
	         ext = "jpg";
	          t = new JPEGTranscoder();
	      } else if (type.equals("application/pdf")) {
	         ext = "pdf";
	         t = new PDFTranscoder();
	      } else if (type.equals("image/svg+xml")) {
	         ext = "svg";  
	      }
	        response.addHeader("Content-Disposition", "attachment; filename="+filename+"."+ext);
	      response.addHeader("Content-Type", type);
	       if (null != t){
	            TranscoderInput input = new TranscoderInput(new StringReader(svg));
	            TranscoderOutput output = new TranscoderOutput(out);
	           
	            try {
	               t.transcode(input,output);
	               
	            } catch (TranscoderException e){
	               out.print("编码流错误.");
	               e.printStackTrace();
	            }
	           } else if (ext == "svg"){
	                     svg =  svg.replace("http://www.w3.org/2000/svg", "http://www.w3.org/TR/SVG11/");
	            out.print(svg);
	         } else {
	            out.print("Invalid type: " + type);
	         }
	      } else {
	         response.addHeader("Content-Type", "text/html");
	      }
	      out.flush();
	      out.close();  
	   }

}
