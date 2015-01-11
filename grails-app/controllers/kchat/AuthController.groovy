package kchat

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.subject.Subject
import org.apache.shiro.web.util.WebUtils


class AuthController {
	def shiroSecurityManager
	def grailsApplication
	UserDetailStorageService uds=new UserDetailStorageService()
	
	static navigation = [
		title:'logout',
		action:'signOut',
		order:900,
		isVisible:{SecurityUtils.subject.principal != null}
	]

	def denied() { 
		
	}
	def return_ip() {
		def ip=request.getRemoteAddr()
		
		if ((ip==null) || (ip.equals('')) || (ip.equals("127.0.0.1"))) {
			ip=request.getHeader("X-Forwarded-For")
		
		}
		if ((ip==null) || (ip.equals('')) || (ip.equals("127.0.0.1"))) {
			ip=request.getHeader("Client-IP")
		}
		if (ip==null) { ip="127.0.0.1"}
		session.userip=ip
		 return ip
		
	}
	
	def saveRecord(String userid,Boolean success) {
		def ip=return_ip()
		def newEntry = new AuthLogs(userId: userid, successful: success, ipAdd: ip)
		if (! newEntry.save(flush: true)) {
			newEntry.errors.allErrors.each{println it}
		}
	}
	
	def index = {
		forward action:'login',params:params
	}
	
	/**
	 * Present the login page
	 */
	def login = {
		def model=new LDAPLoginCommand()
		model.authProvider=params.authProvider
		model.username=params.username
		model.rememberMe=params.rememberMe?:true
		model.targetUri=params.targetUri
		display(model)
	}

	void display(LDAPLoginCommand cmd) {
		render( template:'loginForm', model:[loginCmd:cmd])
	}
	
	void display1(LDAPLoginCommand cmd) {
		render (view:'login',model:[model:cmd])
	}
	
	/**
	 * Invoked by login page
	 */
	def signIn = {LDAPLoginCommand cmd->
		if (cmd.hasErrors()) {
			cmd.errors.allErrors.each{println it}
			flash.message = message(code: "login.failed"+cmd.username)
			display(cmd)
			saveRecord(cmd.username,false) 
			return
		} 
		
		//session.authProvider=cmd.authProvider
		String host=request.getHeader('host')
		// Create a password token for captured credentials
		cmd.username=cmd.username?.trim()?.toLowerCase()
		//def authToken = new UsernamePasswordToken(username:cmd.username,
		//		password:cmd.password,host:cmd.authProvider)
		
		def authToken = new CustomAuthenticationToken(username:cmd.username,
			password:cmd.password,host:host, authProvider:cmd.authProvider )
		
		// Support for "remember me"
		if (cmd.rememberMe) {
			authToken.rememberMe = true
		}
		
		// If a controller redirected to this page, redirect back
		// to it. Otherwise redirect to the root URI.
		def targetUri = cmd.targetUri ?: "/"

		// Handle requests saved by Shiro filters.
		def savedRequest = WebUtils.getSavedRequest(request)
		if (savedRequest) {
			targetUri = savedRequest.requestURI - request.contextPath
			if (savedRequest.queryString) targetUri = targetUri + '?' + savedRequest.queryString
		}
		def msg
		try{
			//SecurityUtils.subject.login(new UsernamePasswordToken(cmd.username,cmd.password))
			SecurityUtils.subject.login(authToken) //Login was successful so perform session setup
			def userid=SecurityUtils.subject.principal
			SecurityUtils.subject.session.setAttribute('authProvider',authToken.authProvider)
			
			if (log.isDebugEnabled()) {
				log.debug "principal for ${cmd.username}=${userid}"
			}
			//session.userid=userid
			session.user=userid
			//session.username=userid
			def getLastSuccess=AuthLogs.findByUserIdAndSuccessful(userid,true, [sort: 'dateCreated', order: 'desc', offset:0, max:1])
			getLastSuccess.each{
				session.lastsuccess=it
			}
			def getLastFailure=AuthLogs.findByUserIdAndSuccessful(userid,false, [sort: 'dateCreated', order: 'desc', offset:0, max:1])
			getLastFailure.each{
				session.lastfail=it
			}
			uds.storeUserDetails(userid.toString(), session.usersname.toString(), session.usersemail.toString(), session.usersdepartment.toString(), session.usersmanager.toString())
			saveRecord(cmd.username,true)
			render(template:"welcomeMessage")
			if (log.isDebugEnabled()) {
				log.debug "Redirecting successful authentication"
			}
			return
		}
		catch (Exception ex){
			log.info "Authentication failure for user '${cmd.username}'\n${ex}"
			flash.message = msg?:message(code: "login.failed")
			saveRecord(cmd.username,false)
		}
		finally {
			authToken.clear()
		}
		// Not authorised - back to login page
		if (log.isDebugEnabled()) {
			log.debug "User ${cmd.username} not authorised"
		}
		display(cmd)
	}

	/**
	 * sign out option
	 */
	def signOut = {
		//println "-- WE ARE SIGNING OUT USER"
		//SecurityUtils.subject?.logout()
		//session?.invalidate()
		
		if (SecurityUtils.getSubject()) {
			SecurityUtils.subject.logout()
		}
		boolean invalidated = false
		try {
			request.getSession(false)
		} catch(IllegalStateException ex) {
			invalidated = true
		}
		if (invalidated==false) {
			session?.invalidate()
		}
		
		
		redirect(uri:'/')
	}

	def unauthorized = {
		render (status:403,text:"${message code:'login.denied',default:'Not authorized'}")
	}
}


class LDAPLoginCommand implements Serializable {
	private static final long serialVersionUID = 1L
	String authProvider
	String username
	String password
	Boolean rememberMe
	String targetUri

	// n.b. Constraints are deliberately looser than authentication actually
	// demands. This is to prevent giving too many clues to BlackHats.
	static constraints={
		username(blank:false,minSize:1,maxSize:30,matches:/^[\-A-Za-z0-9 ]+$/)
		password(nullable:false,blank:false,minSize:1,maxSize:256)
	}
	String toString() {
		"${username}:${rememberMe}"
	}
}

