package com.HeliosResearch

import java.util.Date;

class Movie {
	String mediaId // the former "ID", not unique (e.g. "K"=Kauf-DVD, or unlabelled DVDs); todo: make input uppercase
		//$HR todo: for HD-Kopien: HD-Name + filename
	String mediaFormat // VHS, DVD, etc. (BluRay, mp4 ...)
	String title
	String fsk // todo: eigene Einschätzungen oder (besser) tags (language18, sex16 etc.)
	String source  // how it was created (source, tools, compression ratio etc.)
	String remark    // (anything)
	Boolean copyOnHD // whether we have a copy in our hard disk archive => should be an extra table  $HR todo nullable Boolean
	String infoLink  //$HR niy: n>1
	// removed: "Fam. Frank"
	String physStorage // added: the place where the movie is stored, z.B. 'DVD K' = "Schatzkiste(n)"; P: some places are unknown
	String languages // language codes: 'D'=deutsch, 'E'=english, 'M'=Musik, 'F'=Französisch, 'O'=OmU //$HR weak, improve (subtitles etc.); Kombinationen werden nicht abgebildet (z.B. 'E D' etc.)
	String offset // hh:mm:ss (für VHS-Bänder) oder [1, 2, ...] für DVDs mit mehreren Titeln
	String quality // hint on quality; VHS: 'SP'=short play, 'LP'=long play (inferior quality); DVD: e.g. compression percentage
	Date dateCreated // (added; nullable for imported data)
	// much more: zuletzt geguckt + Bewertung, Dauer, Tags, Regisseur, Darsteller etc.
	// missing: Audio info (de, en, ...), subtitles etc.
	// String show    // for TV shows etc.:  e.g. show='Raumschiff Enterprise'
	// String season  // for TV shows etc.:  e.g. season='V'
	// String episode // for TV shows etc.:  e.g. episode='233'
	// Bsp.: (IMDB) "Raumschiff Enterprise: Season 1, Episode 22, Space Seed (21 Oct. 1972)", http://www.imdb.com/title/tt0708447/?ref_=fn_tt_tt_1
	
//	static int NoOfFields = 10
	
	static constraints = {
		title(blank: false, size: 1..100)
		mediaFormat(nullable: false, inList: ['VHS', 'DVD'])
		physStorage(nullable: true, inList: ['VHS Bibliothek', 'DVD E', 'DVD K', 'DVD A'])  // nullable: some storage places are not known; $HR  niy: 'HD' => create extra record if a copy on HD exists
		mediaId(nullable: true, size: 1..7) // note: is not unique: one medium may contain n>=1 titles; some media do not have an id
		fsk(nullable: false, inList: ['0', '6', '12', '16', '18', '']) // ''=unknown; 15: z.B. engl. Filme; 'x': (nur bei Kxxx) ?eigene Einschätzung?
		source(nullable: true, inList: ['TV', 'lovefilm.de', 'K', 'E']) // VHS: 'K'=Kaufkassette, 'E'=Eigenaufnahme (von VHS oder DVD oder TV)
		remark(nullable: true)
		copyOnHD(nullable: true)  // true: only for DVDs; (video/) has been copied to hard disk   note: boolean is not nullable (?!)
		infoLink(nullable: true, url: true)
//		storage(nullable: true, inList: ['TV', 'lovefilm.de'])
		languages(nullable: true, inList: ['E', 'D', 'M', 'F', 'O'])
		offset(nullable: true, size: 1..8)
		quality(nullable: true, size: 1..3)
		dateCreated()
	}
}
