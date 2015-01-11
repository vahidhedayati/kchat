import org.apache.shiro.web.mgt.CookieRememberMeManager
def DEFAULT_MAX_REMEMER_ME = 60 * 86400 //one day

security.shiro.authc.required = false
shiroRememberMeManager(CookieRememberMeManager) {
        //delegate.'cookie.setPath' = System.getProperty('UPLOADLOC')
        delegate.'cookie.maxAge' = DEFAULT_MAX_REMEMER_ME
}



// Datasources external now
dataSource {
	pooled = true
	driverClassName ="com.mysql.jdbc.Driver"
	username = "kchat"
	password = "MYPASS"
	zeroDateTimeBehavior="convertToNull"
	dbCreate = "update"
	url = "jdbc:mysql://localhost/kchat?zeroDateTimeBehavior=convertToNull"
	properties {
		maxActive = -1
		minEvictableIdleTimeMillis=1800000
		timeBetweenEvictionRunsMillis=1800000
		numTestsPerEvictionRun=3
		testOnBorrow=true
		testWhileIdle=true
		testOnReturn=true
		validationQuery="SELECT 1"
	 }
}

log4j = {
        error   'org.codehaus.groovy.grails.web.servlet',        // controllers
                'org.codehaus.groovy.grails.web.pages',          // GSP
                'org.codehaus.groovy.grails.web.sitemesh',       // layouts
                'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
                'org.codehaus.groovy.grails.web.mapping',        // URL mapping
                'org.codehaus.groovy.grails.commons',            // core / classloading
                'org.codehaus.groovy.grails.plugins',            // plugins
                'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
                'org.springframework',
                'org.hibernate',
                'net.sf.ehcache.hibernate'
        info    'grails',
                'grails.app.task',  // this is for quartz jobs
                'grails.app.service',
                'grails.app.controller'

}




ldap {
	servers = [
			[url:'ldap://LDAP!:389/',user:'USER\\myUser',pwd:'PASS'],
			[url:'ldap://LDAP!:389/',user:'USER\\myUser1',pwd:'PASS1'],
			[url:'ldap://LDAP!:389/',user:'USER\\myUser2',pwd:'PASS2'],
			[url:'ldap://LDAP!:389/',user:'USER\\myUser3',pwd:'PASS3']
			
	]
	// Search base
	base='DC=my,DC=company,DC=com'
}


testldap {
	servers = [
		[url:'ldap://LDAP!:389/',user:'USER\\myUser',pwd:'PASS']
	]
	// Search base
	base='DC=my,DC=company2,DC=com'
}


otherldap {
	servers = [
			[url:'ldap://LDAP!:389/',user:'USER\\myUser',pwd:'PASS']
	]
	//search base
	base='DC=my,DC=company1,DC=com'
}




wschat.title='My Chat'
wschat.heading='My Chat'
wschat.disable.login = "no"
wschat.hostname=(System.getProperty('SERVERURL') ?: '127.0.0.1') + (":8080")
wschat.defaultperm='admin'
wschat.dbsupport="YES"
wschat.rooms = ['rest_room','sing_room','dance_room']
wschat.showtitle="NO"

//wschat.iceservers="{'iceServers':[{'url':'stun:23.21.150.121'}]} : {'iceServers': [{'url': 'stun:stun.l.google.com:19302'}]};";

stunServers {
	iceServers=[
		[url: 'stun:stun.l.google.com:19302']
	]
}


