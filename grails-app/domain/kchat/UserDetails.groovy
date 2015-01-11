package kchat

import java.util.Date;

class UserDetails {
	String  userId
	String  usersname
	String 	emailAddress
	String 	deptId
	String 	manager
	Date dateCreated
	///Date lastUpdated
    static constraints = {
		userId (maxLength: 100, blank: false, unique: true)
	}
	String toString() { "${userId}"}
}
