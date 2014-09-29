class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index") //$HRc GiA p.153, 5.7.4: go directly to view, bypass controller
		"500"(view:'/error')
	}
}
