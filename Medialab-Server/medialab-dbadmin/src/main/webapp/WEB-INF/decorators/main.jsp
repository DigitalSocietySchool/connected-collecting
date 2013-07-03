<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<s:url action="team" var="teamLink">
</s:url>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><decorator:title default="Struts Starter"/></title>
    <link href="<s:url value='/styles/main.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <decorator:head/>
</head>
<body id="page-home">
    <div id="page">
        <div id="header" class="clearfix">
        	StickerAlbum Admin
            <hr />
        </div>
        
        <div id="content" class="clearfix">
            <div id="main">
            	<h3>Data</h3>
            	<decorator:body/>
                <hr />
            </div>
            
            <div id="sub">
            	<h3>Sub Content</h3>
            </div>
            
            
            <div id="local">
                <h3>Actions</h3>
                <ul>
                    <li><a href="#">Add</a></li>
                    <li><a href="#">Edit</a></li>
                    <li><a href="#">Delete</a></li>
                </ul>
            </div>
            
            
            <div id="nav">
                <div class="wrapper">
                <h3>Nav. bar</h3>
                <ul class="clearfix">
                     <li><a href="${teamLink}">Team</a></li>
                     <li><a href="#">Player</a></li>
                     <li><a href="#">Sticker</a></li>
                     <li><a href="#">Content</a></li>
                     <li><a href="#">Rank</a></li>
                     <li class="last"><a href="#">User</a></li>
                </ul>
                </div>
                <hr />
            </div>
        </div>
        
        <div id="footer" class="clearfix">
            MediaLAB Amsterdam
        </div>
        
    </div>
    
    <div id="extra1">&nbsp;</div>
    <div id="extra2">&nbsp;</div>
</body>
</html>
