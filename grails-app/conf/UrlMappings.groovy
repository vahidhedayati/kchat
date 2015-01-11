class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
		"/" {
			controller = "chatter"
			action = "index"
		 }
       ///"/"(view:"/index")
        "500"(view:'/error')
	}
}
