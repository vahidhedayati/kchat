import kchat.AuthProvider



class BootStrap {

    def init = { servletContext ->
    			// THESE NAMES MUST MATCH THE NAMES IN THE CONFIG FILE 
    			//https://github.com/vahidhedayati/kchat/blob/master/grails-app/conf/kchatConfig.groovy#L55
    			// which should really be an external configuration file
		AuthProvider.findOrSaveWhere(name: 'ldap', friendlyName: 'SERVER1')
		AuthProvider.findOrSaveWhere(name: 'testldap', friendlyName: 'SERVER2')
		AuthProvider.findOrSaveWhere(name: 'otherldap', friendlyName: 'SERVER3')
		
		
		
    }
    def destroy = {
    }
}
