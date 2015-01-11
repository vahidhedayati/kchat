package kchat


class UserDetailStorageService {
	

	def storeUserDetails(String uid, String uname, String email, String deptid, String manager ) {
		def output=""
		def findlrh=UserDetails.findByUserIdAndEmailAddress(uid,email )
		if (!findlrh) {	
			def newEntry = new UserDetails(userId:uid, usersname:uname, emailAddress:email,deptId:deptid,  manager:manager)
			if (! newEntry.save(flush: true)) {
				newEntry.errors.allErrors.each{println it}
				output="Error saving UserDetails "
				}
		}
		return output
	}
	

	
	def getUserDetails (String uid) {
		def output=""
		def finduser=UserDetails.findByUserId(uid)
		if  (finduser) {
			def ret_usersname=finduser.usersname
			def ret_emailAddress=finduser.emailAddress
			output="<a href=\"mailto:"+ret_emailAddress+"\">"+ret_usersname+"</a>"
		}else{ 
			output=uid
		}
		output
	}
}
