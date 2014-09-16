<%-- 
    Document   : index
    Created on : 15-sep-2014, 8:41:34
    Author     : javiersolis
--%>
<%@page import="org.semanticwb.proxy.ProxyFilter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String server=request.getParameter("server");
    String cachePath=request.getParameter("cachePath");
    if(server!=null)
    {
        ProxyFilter.server=server;
        ProxyFilter.cachePath=cachePath;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ProxyCache</title>
        <style>
* {
	margin: 0;
	padding: 0;
}

body {
	font-size: 62.5%;
	font-family: Helvetica, sans-serif;
	background: url(images/stripe.png) repeat;
}

p {
	font-size: 1.3em;
	margin-bottom: 15px;
}

#page-wrap {
	width: 660px;
	background: white;
	padding: 20px 50px 20px 50px;
	margin: 20px auto;
	min-height: 500px;
	height: auto !important;
	height: 500px;
}

#form {
	width: 600px;
	margin-top: 25px;
}

#form input, #form textarea {
	padding: 5px;
	width: 471px;
	font-family: Helvetica, sans-serif;
	font-size: 1.4em;
	margin: 0px 0px 10px 0px;
	border: 2px solid #ccc;
}

#form textarea {
	height: 90px;
}

#form textarea:focus, #form input:focus {
	border: 2px solid #900;
}

#form input.submit-button {
	width: 100px;
	float: right;
}

label {
	float: left;
	text-align: right;
	margin-right: 15px;
	width: 100px;
	padding-top: 5px;
	font-size: 1.4em;
}            
        </style>        
    </head>
    <body>
        <h1>ProxyCache Config</h1>
        <div id="form">
            <form action="">
                <label for="server">Server:</label>
                <input type="text" name="server" value="<%=ProxyFilter.server%>"/>
                <label for="cachePath">Cache Path:</label>
                <input type="text" name="cachePath" value="<%=ProxyFilter.cachePath%>"/>
                <input type="submit" class="submit-button"/>
            </form>
        </div>
    </body>
</html>
