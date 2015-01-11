<%--<ul class="nav pull-right">--%>
	<li class="dropdown pull-right">
	
<%--<sec:ifNotLoggedIn>--%>
	<g:if test="${!session.user}">

		<a href="#" data-toggle="dropdown" class="dropdown-toggle"> 
			<!-- TODO: integrate Springsource Security etc. and show User's name ... -->
    		<i class="icon-user"></i>
    		<span class="fa fa-user icon-color"></span>
    		<span id="userMessage">
			<g:message code="security.signin.label" id="userMessage" default="Sign in"/>
			</span>
			<b class="caret"></b>
		</a>

		<ul class="dropdown-menu" role="menu"  id="authBox">
			<li class="form-container">
			
		<g:render template="/auth/loginForm" />


			
			</li>
			<li class="divider"></li>
		</ul>
</g:if>

<g:else>

<a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#" href="#" id="userBox">
			
			<i class="icon-user icon-white"></i>
			<span class="fa fa-user icon-color"></span>
			<span id="userMessage">
			<g:message code="default.user.label" default="${session.user}" />
			</span>
			 <b class="caret"></b>
		</a>
		
		<ul class="dropdown-menu" role="menu"  id="authBox">
			<li>
			<g:render template="/auth/welcomeMessage" />
			</li>
</ul>
			
</g:else>


	</li>
<%--</ul>--%>

<noscript>
<ul class="nav pull-right">
	<li class="">
		<g:link controller="user" action="show"><g:message code="default.user.details.label"  default="User info"/></g:link>
	</li>
</ul>
</noscript>
