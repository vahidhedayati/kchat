<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'admin.label', default: 'Admin')}" />
		<title><g:message code="Permission Denied" args="[entityName]" /></title>
	</head>
	<body>	
		<div class="nav" role="navigation">
		 	<dropnav><ul>
			<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
		</ul></dropnav></div>	
				<div id="buildHistoryBoxD" class="buildHistoryBoxD">
		<g:if test="${flash.message}">			
			<div class="message" role="status">${flash.message}</div>
		</g:if>
		
		<h1>ACCESS DENIED!</h1>
		
		
			<div class="embossed-heavy" role="complementary">
				<g:if test="${session.user}">	
					<div class="red"><b>You are not authorised to view or take action on last choice</b></div>	
				</g:if>
				<g:else>
					In order to use this you must now sign in using your Active Directory Account (Windows Credentials).
					<br/><br/>
					Please use the login box provided on the top right hand corner of this site !
				</g:else>	
			</div>
		</div>
	</body>
</html>