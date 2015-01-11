import javax.servlet.http.HttpSession

import kchat.UserDetails

import org.apache.shiro.SecurityUtils
import org.springframework.web.context.request.RequestContextHolder

class SecurityFilters {
	def filters = {
		catchRememberMeCookie(url: "/**") {
			before = {
				def subject = SecurityUtils.subject
				if ((controllerName.equals('auth'))||(controllerName.equals('authDef'))||(controllerName.equals('contactUs'))||(controllerName.equals('simpleCaptcha'))) {
					request.accessAllowed = true
					if ((controllerName.equals('auth'))&&(actionName.equals('signOut'))) {
						request.cookies.find({ it.name == "rememberMe" }).each {
							getSession()

							//log.info "Removing rememberMe cookie: ${it.value}"
							it.maxAge = 0
							response.addCookie it
							// def subject = SecurityUtils.subject
							log.info "Logging user '${subject.principal}' out"
							subject.logout()
						}
					}
					return
				}
				
				if (!session.user) {
					request.cookies.find({ it.name == "rememberMe" }).each {
						getSession() // Ensure a Session exists before we start the response
						def userid=subject.principal
						//log.info "Found rememberMe cookie (reauthenticating user): ${it.value}"
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
						request.accessAllowed = true
						return
					}
				}
			}
		}
	}


	private HttpSession getSession() {
		return RequestContextHolder.currentRequestAttributes().getSession()
	}
}
