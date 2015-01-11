import kchat.AuthProvider



class BootStrap {

    def init = { servletContext ->
		AuthProvider.findOrSaveWhere(name: 'server1', friendlyName: 'SERVER1')
		AuthProvider.findOrSaveWhere(name: 'server2', friendlyName: 'SERVER2')
		AuthProvider.findOrSaveWhere(name: 'server3', friendlyName: 'SERVER3')
		AuthProvider.findOrSaveWhere(name: 'server4', friendlyName: 'SERVER4')
		
		
    }
    def destroy = {
    }
}
