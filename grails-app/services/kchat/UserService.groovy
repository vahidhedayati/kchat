package kchat



import javax.servlet.http.HttpSession

import org.springframework.web.context.request.RequestContextHolder

class UserService {
	def grailsApplication

	def emailAddress=''

	def returnRealUser(String userId) {
		def u=UserDetails?.findByUserId(userId.toString())
		def cuser=u?.usersname
		emailAddress=u?.emailAddress
		if (u?.usersname.equals(u?.userId)) {
			def cuser1=u?.emailAddress
			if (cuser1) {
				if (cuser1.indexOf('@')>-1) {
					cuser=cuser1.substring(0,cuser1.indexOf('@'))
				}else{
					cuser=cuser1
				}
			}	
		}
		if (cuser) { 
			if (cuser.indexOf('(')>-1) {
				cuser=cuser.substring(0,cuser.indexOf('('))
			}
		}else{
			cuser=userId
		}
		return cuser
	}
	
	def stripHtml(String content) { 
		if(content.indexOf('<p>') == 0) {
			content = content.substring(3)
		} 
		content.replaceAll(/<p>/, "\r\n")
		content = content.replaceAll(/<br \/>/, "\r\n")
		content = content.replaceAll(/<br>/, "\r\n")
		content=content.replaceAll("&nbsp;","")
		content=content.replaceAll("&quot;","\"")
		content=content.replaceAll("&#39;","'")
		content=content.replaceAll("&gt;",">")
		content=content.replaceAll("&lt;","<")
		return  content.replaceAll( /<[^<|>]+?>/,'' )
	}
	
	

	private String parseFileName(String input) {
		String g=''
		input=input.replace('<br>','')
		String pattern='/'
		if (input.lastIndexOf(pattern)>-1) {
			g+=input.substring(input.lastIndexOf(pattern)+pattern.length(),input.length())
		}
		return g
	}
	
	
	
	private String parseFullFile(String pattern,String sres) {
		sres=sres.replace('<br>','\n')
		List<String> list = Arrays.asList(sres.split("\\n"));
		String g=""
		list.each() {
			if (it.indexOf(pattern)>-1) {
				g+=it.substring(it.indexOf(pattern)+pattern.length(),it.length())
			}
		}
		return g
	}
	
	private String parseResults(String pattern,String sres) {
		sres=sres.replace('<br>','\n')
		List<String> list = Arrays.asList(sres.split("\\n"));
		String g=""
		list.each() {
			if (it.indexOf(pattern)>-1) {
				g+=it.substring(it.indexOf(pattern)+pattern.length(),it.length())+"<br>"
			}
		}
		return g
	}
	
	
	private String parseRest(String pattern,String sres) {
		sres=sres.replace('<br>','\n')
		List<String> list = Arrays.asList(sres.split("\\n"));
		String g=""
		list.each() {
			if (it.indexOf(pattern)>-1) {
			}else{	
				g+=it+"<br>"
			}
		}	
		return g
	}
	
	
	private Boolean checkHostTypes(def sstype,def htype) {
		Boolean authorised=false
		sstype.each { st ->
			htype.each { ht ->
				if (ht.toString().equals(st.toString())) {
					authorised=true
				}
			}
		}	
	 return authorised
	}
	private HttpSession getSession() {
		return RequestContextHolder.currentRequestAttributes().getSession()
	}
}
