<%@ page import="kchat.UserDetails" %>

<li class="">
		<a tabindex="-1" href="#">
		<b>${session?.usersname } (${session?.user})</b><br/>
		${session?.usersemail }
		</a>
</li>
<li class="divider"></li>

	<li class=""><a tabindex="-1" href="#">Your Manager [${session?.usersmanager }]</a></li>
	<li class=""><a tabindex="-1" href="#">Your Department [${session?.usersdepartment }]</a></li>
	<li class="divider"></li>
	<g:if test="${session?.lastsuccess ||session?.lastfail }">
	
	<g:if test="${session?.lastsuccess }">
		<li class="">	
			<g:link controller="AuthLogs" action="userlogs" id="true">success <prettytime:display date="${session?.lastsuccess?.dateCreated}" /></g:link>
		</li>
	</g:if>
	<g:if test="${session?.lastfail }">
		<li class="">
 			<g:link controller="AuthLogs" action="userlogs" id="false">Failed: <prettytime:display date="${session?.lastfail?.dateCreated}" /></g:link>
 		</li>
	</g:if>
	<li class="divider"></li>
	</g:if>
	
	
	<li class=""> 
		<g:link  controller="auth" action="signOut">Sign Off</g:link>
	</li> 
	
	<li class="divider"></li>
	
	<g:if test="${session.lastPlace &&  session.redirectit.equals('yes')}">
		<g:set var="redirectit" value="no"  scope="session" />	
		<g:set var="lastPlace" value=""  scope="session" />
		<g:javascript>
			window.location.href = "${session.lastPlace}";
		</g:javascript>
		<g:set var="redirectit" value="no"  scope="page" />	
		<g:set var="lastPlace" value=""  scope="page" />
	</g:if>	
		