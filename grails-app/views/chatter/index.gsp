<html>
<head>
<meta name="layout" content="main" />
<title><g:message code="kchat.title" default="My Chat" /></title>
</head>
<body>
<div class="container">
<br>
	<g:if test="${flash.message}">
		<div class="message" role="status">${flash.message}</div>
	</g:if>
		
<div id="homeBox">
					
</div>



<g:if test="${!session.user }">
	<g:render template="/auth/loginForm" model="[did: 'homeBox']"/>
</g:if>
<g:else>

<g:render template="/navbar"/>	
<div class="container">
<br><br>
	<chat:connect chatuser="${chatuser}" room="${room }"/>
	</div>
</g:else>
</div>

		
	
</body>
</html>