
	<li class="dropdown"  >
		<a href="#" data-toggle="dropdown" class="dropdown-toggle"> 
			<i class="fa fa-gear icon-color" title="Admin menu"></i><b class="caret"></b>
		</a>

		<ul class="dropdown-menu" >
			<li class="dopdown-menu">
				<a tabindex="-1" href="#"><b>Admin Options</b></a>
			</li>
			<li class="divider"></li>
			<g:if env="development">
			<li class="">
				<a href="${createLink(uri: '/dbconsole')}">
					<i class="icon-dashboard"></i>
					<g:message code="default.dbconsole.label" default="db console"/>
				</a>
			</li>
			<li class="divider"></li>
			</g:if>
		
	</ul>
	</li>