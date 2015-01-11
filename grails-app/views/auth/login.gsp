<html>
<head>
<meta name="layout" content="main" />
<title><g:message code="kchat.title" /></title>
</head>
<body>
	<div id="loginPage">
		<g:form action="signIn">
			<input type="hidden" name="targetUri" value="${model?.targetUri}" />
			<table align="center">
				<tbody id="loginPage">
					<tr>
						<td class="label"><label for="username"><g:message
									code="kchat.nuid" /></label></td>
						<td class="value"><g:textField name="username"
								autocomplete="off" value="${model?.username}" size="20" /></td>
					</tr>
					<tr>
						<td class="label"><label for="password"><g:message
									code="kchat.pwd" /></label></td>
						<td class="value"><g:passwordField autocomplete="off"
								name="password" size="20" /></td>
					</tr>
					<tr class="error">
						<td colspan="2"><g:message code="${flash.message}"
								args="${flash.args}" default="${flash.default}" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><g:submitButton accesskey="s" class="gsButton"
								name="login" value="${g.message(code:'kchat.login') }" /></td>
					</tr>
				</tbody>
			</table>
		</g:form>
	</div>
</body>
</html>
