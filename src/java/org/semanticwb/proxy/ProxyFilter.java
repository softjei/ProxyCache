/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.proxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author javiersolis
 */
public class ProxyFilter implements Filter
{
    public static String server="http://www.semanticwebbuilder.org.mx";
    public static String cachePath="/data/cache";

    public void init(FilterConfig filterConfig) throws ServletException 
    { 
        server=filterConfig.getInitParameter("server");
        cachePath=filterConfig.getInitParameter("cachePath");
        System.out.println("Init ProxyFilter:"+server);
    }

    public void doFilter(ServletRequest _request, ServletResponse _response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)_request;
        HttpServletResponse response=(HttpServletResponse)_response;
        String uri = request.getRequestURI();
        //String cntx = request.getContextPath();
        //String path = uri.substring(cntx.length());
        //String host = request.getServerName();   
        
        System.out.println("uri:"+uri);
        if(uri.startsWith("/AdminProxy/"))
        {            
            chain.doFilter(_request, _response);
            return;
        }
        
        File f=Utils.getFile(uri, cachePath);
        
        //System.out.println("host:"+host+" uri:"+uri+" cntx:"+cntx+" path:"+path);
                
        SWBBridge bridge=new SWBBridge();
        //System.out.println("Remote URL:"+proxy.getUrl()+uri);
        bridge.setAcceptEncoding(false);
//            {
//                addHeaders(bridge,headers);
//                addCookies(bridge,cookies);
//            }
        //System.out.println(""+System.currentTimeMillis()+": "+"remoteURL:"+remoteURL);

        String loginurl=null;
        String replace=null;
        
        //String iniPath=base.getAttribute("iniPath","/");
        String encode="utf-8";
//        String basePath=base.getAttribute("basePath");
//        String replace=base.getAttribute("replace");
//        String direct=base.getAttribute("direct");
//        String userinsert=base.getAttribute("userinsert");
//        String loginurl=base.getAttribute("loginurl");
//        String initext=base.getAttribute("initext",null);
//        String endtext=base.getAttribute("endtext",null);
//        String headers=base.getAttribute("headers");
//        //String removePath=base.getAttribute("removePath","http://200.33.31.6;http://172.20.174.55");
//        String cookies=base.getAttribute("cookies");
//        String instance=base.getAttribute("instance",""+base.getId());
        

        SWBBridgeResponse res = bridge.bridge(server+uri, loginurl, request, response,null,true, "proxycache");
        String code = "" + res.getResponseCode();
//
        //System.out.println("getContentType:"+res.getContentType());
        //System.out.println("getErrorMessage:"+res.getErrorMessage());
        //System.out.println("getResponseMessage:"+res.getResponseMessage());
        //System.out.println("getHeaderSize:"+res.getHeaderSize());
        //System.out.println("getResponseCode:"+res.getResponseCode());


//        System.out.println(""+System.currentTimeMillis()+": "+"getResponseCode:"+code);

        if (code.startsWith("3"))
        {
            String redi = res.getHeaderField("Location");
            if (redi != null)
            {
                redi=redi.replace(server, "");
                //System.out.println("redi:"+redi);
                response.sendRedirect(redi);
                return;
            } else
            {
                //response.sendError(res.getResponseCode());
            }
        } else if (code.startsWith("2"))
        {
            String contentType = res.getContentType();
            
            //System.out.println("contentType:"+contentType);
            if(contentType!=null && contentType.indexOf("text")>-1)
            {
                String txt=null;
                int c=contentType.indexOf("charset=");
                if(c>0)
                {
                    txt=Utils.readInputStream(res.getInputStream(),contentType.substring(c+8));
                }else
                {
                    txt=Utils.readInputStream(res.getInputStream());
                }
                
                //System.out.println("text1:"+txt);
                txt=txt.replace(server, "");
                //System.out.println("text2:"+txt.length());
                
                //response.setHeader("Content-Length", null);
                response.getWriter().print(txt);
                
                if(!f.isFile())
                {
                    FileOutputStream fout=new FileOutputStream(f);
                    fout.write(txt.getBytes());
                    fout.close();
                }
            }else
            {
                if(!f.isFile())
                {
                    FileOutputStream fout=new FileOutputStream(f);
                    Utils.copyStream(res.getInputStream(), fout);
                    FileInputStream fin=new FileInputStream(f);
                    Utils.copyStream(fin, response.getOutputStream());
                }else
                {                
                    Utils.copyStream(res.getInputStream(), response.getOutputStream());
                }
            }
        } else
        {
            //bloque de error duplicado
            String err = "Error:" + code + " " + res.getResponseMessage();

            String errm=res.getErrorMessage();
            
            //System.out.println("error:"+err);
            //System.out.println("errm:"+errm);
            
        }     
    }

    public void destroy() {
    }
    
}
