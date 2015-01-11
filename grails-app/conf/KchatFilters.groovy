
class KchatFilters {
	
		def filters = {
			KchatLogger() {
				before = {
					// Small "logging" filter for controller & actions
					if (log.infoEnabled) {
						if (!params.password ) {
							log.info(!params.controller ? '/: ' + params : params.controller +"."+(params.action ?: "index")+": "+params)
						}else{
							log.info (params.controller+","+params.action+":"+params?.username)
						}
					}
				}
					
			}
		}
	}
	
			