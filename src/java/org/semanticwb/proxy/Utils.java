/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.proxy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author javiersolis
 */
public class Utils {

    /**
     * Defines the size used for creating arrays that will be used in I/O
     * operations.
     * <p>
     * Define el tama&ntilde;o utilizado en la creaci&oacute;n de arrays que
     * ser&aacute;n utilizados en operaciones de entrada/salida.</p>
     */
    private static int bufferSize = 8192;

    /**
     * Copies an input stream into an output stream using the buffer size
     * defined by {@code SWBUtils.bufferSize} in the reading/writing operations.
     * <p>
     * Copia un flujo de entrada en uno de salida utilizando el tama&ntilde;o de
     * buffer definido por {@code SWBUtils.bufferSize} en las operaciones de
     * lectura/escritura.</p>
     *
     * @param in the input stream to read from
     * @param out the output stream to write to
     * @throws IOException if either the input or the output stream is
     * {@code null}.
     * <p>
     * Si el flujo de entrada o el de salida es {@code null}.</p>
     */
    public static void copyStream(InputStream in, OutputStream out) throws IOException {
        copyStream(in, out, bufferSize);
    }

    /**
     * Copies an input stream into an output stream using the specified buffer
     * size in the reading/writing operations.
     * <p>
     * Copia un flujo de entrada en uno de salida utilizando el tama&ntilde;o de
     * buffer especificado en las operaciones de lectura/escritura.</p>
     *
     * @param in the input stream to read from
     * @param out the output stream to write to
     * @param bufferSize the number of bytes read/writen at the same time in
     * each I/O operation
     * @throws IOException if either the input or the output stream is
     * {@code null}.
     * <p>
     * Si el flujo de entrada o el de salida es {@code null}.</p>
     */
    public static void copyStream(InputStream in, OutputStream out, int bufferSize) throws IOException {
        if (in == null) {
            throw new IOException("Input Stream null");
        }
        if (out == null) {
            throw new IOException("Ouput Stream null");
        }
        byte[] bfile = new byte[bufferSize];
        int x;
        while ((x = in.read(bfile, 0, bufferSize)) > -1) {
            out.write(bfile, 0, x);
        }
        in.close();
        out.flush();
        out.close();
    }

    /**
     * Reads an input stream and creates a string with that content.
     * <p>
     * Lee un objeto inputStream y crea un objeto string con el contenido
     * le&iacute;do.</p>
     *
     * @param in an input stream to read its content
     * @return a string whose content is the same as for the input stream read.
     * un objeto string cuyo contenido es el mismo que el del objeto inputStream
     * le&iacute;do.
     * @throws IOException if the input stream received is {@code null}.
     * <p>
     * Si el objeto inputStream recibido tiene un valor {@code null}.</p>
     */
    public static String readInputStream(InputStream in) throws IOException {
        if (in == null) {
            throw new IOException("Input Stream null");
        }
        StringBuilder buf = new StringBuilder();
        byte[] bfile = new byte[bufferSize];
        int x;
        while ((x = in.read(bfile, 0, bufferSize)) > -1) {
            String aux = new String(bfile, 0, x);
            buf.append(aux);
        }
        in.close();
        return buf.toString();
    }

    /**
     * Reads a reader and creates a string with that content.
     * <p>
     * Lee un objeto Reader y crea un objeto string con el contenido
     * le&iacute;do.</p>
     *
     * @param in an input stream to read its content
     * @return a string whose content is the same as for the input stream read.
     * un objeto string cuyo contenido es el mismo que el del objeto inputStream
     * le&iacute;do.
     * @throws IOException if the input stream received is {@code null}.
     * <p>
     * Si el objeto inputStream recibido tiene un valor {@code null}.</p>
     */
    public static String readReader(Reader in) throws IOException {
        if (in == null) {
            throw new IOException("Input Stream null");
        }
        StringBuffer buf = new StringBuffer();
        char[] bfile = new char[bufferSize];
        int x;
        while ((x = in.read(bfile, 0, bufferSize)) > -1) {
            String aux = new String(bfile, 0, x);
            buf.append(aux);
        }
        in.close();
        return buf.toString();
    }

    /**
     * Reads an input stream and creates a string with the content read using
     * the charset especified by name through {@code enc}.
     * <p>
     * Lee un objeto inputStream y crea una cadena con el contenido le&iacute;do
     * utilizando el conjunto de caracteres especificado por nombre a
     * trav&eacute;s de
     *
     * @param inp the input stream to read
     * @param enc the charset's name to use for representing the input stream's
     * content
     * @return a string representing the input stream's content with the charset
     * specified. un objeto string que representa el contenido del objeto
     * inputStream con el conjunto de caracteres especificado.
     * @throws java.io.UnsupportedEncodingException if {@code enc} is
     * {@code null}.
     * <p>
     * si el valor de {@code enc} es {@code null}.</p>
     * @throws java.io.IOException if {@code inp} is {@code null}.
     * <p>
     * si el valor de {@code inp} es {@code null}.</p>
     * @throws UnsupportedEncodingException the unsupported encoding exception
     * @throws IOException Signals that an I/O exception has occurred.
     * {@code enc}.
     */
    public static String readInputStream(InputStream inp, String enc)
            throws java.io.UnsupportedEncodingException, java.io.IOException {

        if (inp == null) {
            throw new IOException("Input Stream null");
        }
        if (enc == null) {
            throw new UnsupportedEncodingException("Encoding null");
        }
        InputStreamReader in = new InputStreamReader(inp, enc);

        StringBuffer ret = new StringBuffer();

        char[] bfile = new char[bufferSize];
        int x;
        while ((x = in.read(bfile, 0, bufferSize)) > -1) {
            ret.append(new String(bfile, 0, x));
        }
        in.close();
        return ret.toString();
    }
    
    public static void main(String args[])
    {
        String uri="/work/models/swb/Template/2/12/clusterarticulos.css";
        File f=getFile(uri,"/data/cache");
        uri="/work/models/swb/Template/2/12/";
        f=getFile(uri,"/data/cache");
        uri="/work/models/swb/Template/2/12/home";
        f=getFile(uri,"/data/cache");
        
    }
    
    public static File getFile(String uri, String basePath)
    {
        //System.out.println("uri:"+uri);
        File f=new File(basePath+uri);
        if(uri.endsWith("/"))
        {
            uri=uri+"index.html";
        }else
        {
            String name=f.getName();
            if(name.indexOf(".")==-1)
            {
                uri=uri+"/index.html";
            }
        }
        f=new File(basePath+uri);
        if(!f.getParentFile().isDirectory())f.getParentFile().mkdirs();        
        
        String name=f.getName();
        String path=f.getParent();
        //System.out.println("file:"+f.getPath());
        //System.out.println("path:"+path);        
        //System.out.println("name:"+name);
        //System.out.println("isDir:"+f.isDirectory());
        //System.out.println("isFile:"+f.isFile());
        //System.out.println();
        return f;
    }
}
