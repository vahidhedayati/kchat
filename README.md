kchat
==============

Grails web project built on Grails 2.4.4

Is inbuilt with Bootstrap and has its own mechanism to produce a near enough  kickstart-with-bootstrap theme as the site container.

It also uses apache-shiro as an authentication mechanism where shiro authentication has been modified to query first multiple Active Directory/LDAP servers that can be configured and mainted in provided example kchatConfig.groovy that should really be externalised.

Shiro modified to keep user logged in for a lengthy period, found (configured in the same config file and shiro bean override and kchatFilters.groovy)

User details from AD retained in local DB and reused for chat user etc...

so two examples 1 for external config locatin and serverurl that you could add to your tomcat setenv.sh

```

/*
* This is the most important configuration required for websocket calls
* in my current version the hostname is being defined by tomcat start up setenv.sh
* In my tomcat setenv.sh I have
* HOSTNAME=$(hostname)
* JAVA_OPTS="$JAVA_OPTS -DSERVERURL=$HOSTNAME"
*
* Now as per below the hostname is getting set to this value
* if not defined wschat will default it localhost:8080
*
* Configuration location for external config - called in Config.groovy
* JAVA_OPTS="$JAVA_OPTS -DCONFIGLOC=conf/"
* now it will look for {tomcatbase}/conf/kchat/kchatConfig.groovy
*/
````


##### SecurityFilters  

The example given is very very basic if you wanted to expand and make it into a proper verification / authorisation system - you could do something like this:

```
....
}else if ((controllerName.equals('auth'))&&(actionName.equals('signOut'))) {
					request.cookies.find({ it.name == "rememberMe" }).each {
						getSession()
						//log.info "Removing rememberMe cookie: ${it.value}"
						it.maxAge = 0
						response.addCookie it
						// def subject = SecurityUtils.subject
						log.info "Logging user '${subject.principal}' out"
						subject.logout()
					}
					request.accessAllowed = true
					return
				} else{

					def originalRequestParams = [controller:controllerName, action:actionName]
					originalRequestParams.putAll(params)
					def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()
					def confirmurl= g.createLink(controller: controllerName, action: actionName, params:params,  absolute: 'true' )
					session.redirectit="no"
					session.originalRequestParams = originalRequestParams

					session.fromPlace=request.getHeader('referer')
					session.currentPlace=confirmurl

					if(!session.user) {
						boolean not_found = checkSession(session,request)
						boolean userAllowed=PermitUser(subject.principal, params, appid, actionName,controllerName)
						if (userAllowed==false) {
							request.accessAllowed = false
							redirect(controller:'admin',action:'denied')
							return false
						}else{
							request.accessAllowed = true
							return
						}
						if (not_found) {
							session.lastURL=request.getHeader('referer')
							if (!session.lastURL.equals(confirmurl)) {
								session.redirectit="yes"
								session.lastPlace=confirmurl
							}
							redirect(controller:'admin',action:'denied')
							request.accessAllowed = false
							return false
						}
					}else{
						boolean userAllowed=PermitUser(subject.principal, params, appid, actionName,controllerName)
						if (userAllowed==false) {
							request.accessAllowed = false
							redirect(controller:'admin',action:'denied')
							return false
						}else{
							request.accessAllowed = true
							return
						}
					}

				}
			}
		}
	}
	
	```
	
	
	
	the various calls within above block:
	
	```
	private Boolean checkSession(session,request) {
		boolean not_found = true
		request.cookies.find({ it.name == "rememberMe" }).each {
			not_found = false
			getSession() // Ensure a Session exists before we start the response
			def userid =  SecurityUtils.subject.principal
			log.info "Found rememberMe cookie (reauthenticating user ${userid})"
			//response.addCookie it
			if (log.isDebugEnabled()) {
				//log.debug "principal for ${cmd.username}=${userid}"
			}
			session.user=userid
			if (!session.usersemail) {
				def uemail=UserDetails.findByUserId(userid)
				session.usersemail=uemail.emailAddress
				session.usersname=uemail.usersname
			}
		}
		return not_found
	}

	def checkUser() {
		// Abstract classes i.e. what we require in this scenario
		def config = Holders.config
		def locations = config.grails.config.locations
		def subject = SecurityUtils.subject
		locations.each {
			String configFileName = it.split("file:")[1]
			config.merge(new ConfigSlurper().parse(new File(configFileName).text))
		}
		def originalRequestParams = [controller:controllerName, action:actionName]
		originalRequestParams.putAll(params)
		def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()
		def confirmurl= g.createLink(controller: controllerName, action: actionName, params:params,  absolute: 'true' )
		session.redirectit="no"
		session.originalRequestParams = originalRequestParams

		session.fromPlace=request.getHeader('referer')
		session.currentPlace=confirmurl

		if(!session.user) {
			session.lastURL=request.getHeader('referer')
			if (!session.lastURL.equals(confirmurl)) {
				session.redirectit="yes"
				session.lastPlace=confirmurl
			}
			redirect(controller:'admin',action:'denied')
			return false
		}else{
			def appid = params.id ?: params.aid

			boolean userAllowed=PermitUser(subject.principal, params, appid, actionName,controllerName)
			if (userAllowed==false) {
				redirect(controller:'admin',action:'denied')
				return false
			}
		}
	}

	public Boolean PermitUser(String userId, def params,String application, String action,String controller) {
		boolean authorised=false
		def usergroups=getSession().usersgroups
		def userdepartment=getSession().usersdepartment
		if (!appId) {
			appId=params.aid
		}

		def Uperm=UserPermissions.where {
			userId==userId
		}
		def Udept=UserPermissions.where {
			departmentId==userdepartment
		}
		def foundperms=[]
		int founduperm=0
		founduperm=Uperm.list().size()
		int foundudept=0
		foundudept=Udept.list().size()
		if  ( (founduperm>0) || (foundudept>0)) {
			Uperm.each{ foundu->
				foundu.permissiongroups.each {
					foundperms.add(it.id)
				}
			}
		}
		int foundugroup=0
		usergroups.each { gr ->
			def gg=UserPermissions.withCriteria {
				or {
					//like('ldapGroup', '%' + gr + '%')
					eq('ldapGroup',  gr)
				}
			}
			gg.each{ foundu->
				foundu.permissiongroups.each {
					foundperms.add(it.id)
				}
			}
		}
		def permGroups
		foundugroup=foundperms.size()
		if ( (foundugroup>0)||  (founduperm>0) || (foundudept>0)) {
			foundperms.each{ foundu->
				def gid=foundu
				permGroups=PermissionGroups.where {
					id==gid as long
				}
				int foundugrp=0
				foundugrp=permGroups.list().size()
				if (foundugrp>0) {
					permGroups.each{ foundg->
						foundg.permissions.each { foundgp->
							def pid=foundgp.id
							def perms=Permissions.where {
								id==pid
							}
							int foundper=0
							foundper=perms.list().size()
							if (foundper>0) {
								perms.each{ founduper->
								
									if (
									( (founduper.environment==params.environment) || (founduper.environment=='*'))
									&&
									( ( founduper.group==params.group)  ||  (founduper.group=='*'))
									&&
									( (founduper.controllerAction==action)  ||  (founduper.controllerAction=='*'))
									&&
									( (founduper.controllerName==controller) || (founduper.controllerName=='*'))
									&&
									( (founduper.application==application) || (founduper.application=='*'))
									)  {
										authorised=true
									}
								}
							}
						}
					}
				}
			}
		}
		return authorised
	}

```
	
