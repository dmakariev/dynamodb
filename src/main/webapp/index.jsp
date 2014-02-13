<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Amazon DynamoDB application</title>
    </head>
    <body>
         <div style="position: relative;margin: 20px;margin-bottom: 30px;">
            <div style="font-weight: bold;font-size: 1.2em;margin-bottom: 10px;">Amazon DynamoDB application</div>
            <div style="font-size: 0.7em;margin-left: 15px;">
                <fmt:bundle basename="build-info">
                    platform.artifactId=<b><fmt:message key="platform.artifactId" /></b><br/>
                    platform.version=<b><fmt:message key="platform.version" /></b><br/>
                    platform.buildDate=<b><fmt:message key="platform.buildDate" /></b><br/>
                </fmt:bundle>

                Local Address: <b>${pageContext.request.localAddr}</b><br/>
                Local Name:    <b>${pageContext.request.localName}</b><br/>
                Local Port:    <b>${pageContext.request.localPort}</b><br/>
                Server Name:   <b>${pageContext.request.serverName}</b><br/>
                Server Port:   <b>${pageContext.request.serverPort}</b><br/>

            </div>
        </div>
        
        <a href="faces/home.xhtml">ds/home.xhtml</a>
    </body>
</html>
