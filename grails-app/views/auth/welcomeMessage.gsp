
	<div style="float:right;">
		| <g:link controller="auth" action="signOut">Logout</g:link><br>						
	</div>
	Welcome back <span id="userFirstName">${session?.user}!<br/>${session?.usersname }<br>
	</span>
	

<div  id=btn><a onclick="<g:remoteFunction controller="Users"  action="usergroups" update="returnPanel"/>">Your groups</a></div>