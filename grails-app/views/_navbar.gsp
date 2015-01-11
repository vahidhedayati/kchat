	
<nav id="Navbar" class="navbar navbar-fixed-top navbar-inverse" role="navigation">
	<div class="container">
	
	    <div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
        		<span class="sr-only">Toggle navigation</span>
        		<span class="icon-bar"></span>
	           	<span class="icon-bar"></span>
	           	<span class="icon-bar"></span>
			</button>
			
			<img src="${createLinkTo(dir:'images',file:'spinner.gif')}" id="loading-indicator11" style="display:none" />
			<%    def rn = new Random().nextInt(25) %>
			<div id="loading-indicator" class="spinner-${rn}" style="display:none;">
				<img src="${createLinkTo(dir:'images',file:'spinner'+rn+'.gif')}" alt="Spinner" />
			</div>
			<div id="loading-process" class="processing" style="display:none;">
				<img src="${createLinkTo(dir:'images',file:'processing.gif')}" alt="Spinner" />
			</div>
			
			
				<a class="navbar-brand" href="${createLink(uri: '/')}" id="size3">
				${meta(name:'app.name')}
				</a>
				
		</div>

		<div class="collapse navbar-collapse navbar-ex1-collapse" role="navigation">

		<ul class="nav navbar-nav">

	
					<li>
					<a  href="${createLink(uri: '/')}"><i class="fa fa-home fa-2x icon-color fa-inverse" title="${meta(name:'app.name')} Home"></i></a>
					</li>
	
					<li>
					<g:link  controller="chatter"  action="index"><i class="fa fa-comment fa-2x icon-color fa-inverse" title="Chat"></i></g:link>
					</li>
				
		</ul>
		<g:if test="${session.user}">			
					<g:if test="${session.lastPlace &&  session.redirectit.equals('yes')}">
						<g:set var="redirectit" value="no"  scope="session" />	
						<g:set var="lastPlace" value=""  scope="session" />
						<g:javascript>
							window.location.href = "${session.lastPlace}";
						</g:javascript>
						<g:set var="redirectit" value="no"  scope="page" />	
						<g:set var="lastPlace" value=""  scope="page" />
					</g:if>	
					</g:if>

    	<ul class="nav navbar-nav navbar-right nav-pills">
 			<g:render template="/_menu/admin"/>
			<g:render template="/_menu/user"/>
	    </ul>			
		</div>
		<div  id=hselect>
		</div>
	</div>
</nav>