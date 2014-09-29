package com.HeliosResearch

class Game {
	String title
	String platform
	String remark     // (anything)
	
	static constraints = {
		title(blank: false, size: 1..100)
		platform(nullable: true, inList: ['Wii']) // z.B. Nintendo DS, PC, Brettspiel
		remark(nullable: true)
	}
}
