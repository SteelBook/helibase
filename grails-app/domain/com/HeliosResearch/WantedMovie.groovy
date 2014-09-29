package com.HeliosResearch

import java.util.Date;

class WantedMovie {
	String title
	String fsk
	Boolean available_lf  // available at lovefilm.de
	Boolean available_vd  // available at Videothek Düren
	String status
	String remark         // (anything)
	String infoLink
	String orderLink
	String buyLink
	
	Date dateCreated // (added; nullable for imported data)
	
    static constraints = {
		title(blank: false, size: 1..100)
		fsk(nullable: true, inList: ['x', '0', 'x0', '6', 'x6', '12', '15', '16', '18']) // 15: z.B. engl. Filme; 'x': (nur bei Kxxx) ?eigene Einschätzung?
		available_lf(nullable: true)
		available_vd(nullable: true)
		status(nullable: true, size: 1..20)
		remark(nullable: true)
		infoLink(nullable: true, url: true)
		orderLink(nullable: true, url: true)
		buyLink(nullable: true, url: true)
		dateCreated()
    }
}
