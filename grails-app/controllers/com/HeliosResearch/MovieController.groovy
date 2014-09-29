package com.HeliosResearch

import java.util.Date;

class MovieController {
    // Export service provided by Export plugin:
    def exportService
    def grailsApplication  //inject GrailsApplication
	
	//def scaffold = true // or: <name-of-domain-class> (required if not matching the controller name) 

	//static navigationScope = 'app' //'movie'
    // def index = { }  // redirected to list if undefined; else: error if index.jsp does not exist
	
	//$HR obsolete
	// search functionality: s. GiA pp.104
	def search = {
		// pass through to search.gsp
	}
	
	def results = { // called by search.gsp; search results displayed in results.gsp
		// $HR todo: '%' ersetzen, SQL injection vermeiden
		// todo: test: search is case insensitive 
		def movies = Movie.findAllByTitleIlike("%${params.title}%") // = "dynamic finder"; here: full text search with SQL wildcards '%'
/*		def all_movies = Movie.findAllByTitleIlike("%${params.title}%") // = "dynamic finder"; full text search with SQL wildcards '%'
		
		// $HR test: remove all 'adult' movies (depending on user type/role)
		def movies = new ArrayList()
		all_movies.each {  
			if(it.physStorage != 'DVD A')
				movies << it
		}
		println("${all_movies.size()} match(es), ${movies.size()} displayed")
*/
				
		[movies: movies, searchTerm: params.title]
	}
	
	// form action will return here (action=advSearch)
	def advSearch = {
		//  note: params = [action:advSearch, controller:movie, ...]
		def movies = null
		def fskVals = ['fsk0', 'fsk6', 'fsk12', 'fsk16', 'fsk18', 'fskUK']
		def mediaTypeVals = ['isVHS', 'isDVD']
		def langVals = ['langE', 'langD', 'langM', 'langF', 'langO']
		println(">>params = ${params}<<<<")

		if(params.containsKey('offset') && params.containsKey('max')) {
			// pagination: re-use the list which was saved to the session-context:
			movies = new ArrayList()
			def offset = (params.offset as int) 
			def max = (params.max as int)
			println("paginate with offset=${offset}, max=${max}:")
			// copy from list stored in session
			for(i in (offset .. (offset + max-1)) ) {
				println("${i}: ${session.movies[i]}")
				if(i < session.movies.size())
					movies.add(session.movies[i]) // $HR session.movies should exist here...
			}
			println("session.myParams: ${session.myParams}")
			params.putAll(session.myParams) // title, medis type, FSK etc.
		}
		else if(params.containsKey('search')) {
			// save form params in order to restore them when pagination occurs:
			session.myParams = new HashMap()
			if(params.containsKey('title')) 
				session.myParams.putAt('title', params.title)
			mediaTypeVals.each {  
				if( params.containsKey(it))
					session.myParams.putAt(it, params.getAt(it))
			}
			fskVals.each {  
				if( params.containsKey(it)) 
					session.myParams.putAt(it, params.getAt(it))
			}
			langVals.each {  
				if( params.containsKey(it)) 
					session.myParams.putAt(it, params.getAt(it))
			}

 			def c = Movie.createCriteria() // we use 'GORM Criteria DSL' to do a dynamic search
			session.movies = c.list() {
//			movies = c.list(max: 15, offset: 0) {
//			movies = Movie.withCriteria { // we use 'GORM Criteria DSL' to do a dynamic search
				"and" {
					if(params.containsKey('title')  &&  (params.title != '')) {//$HR besser lösen
						// Titel: mehrere Suchbegriffe möglich
						def searchTerms = params.title.tokenize(" \t")
						searchTerms.each {
//							println("  ilike: title = %${params.title}%")
							ilike("title", "%${it}%") // '%' = do full text search with wildcards
						}
					}
					"or" { //$HR super-clunky solution
						if(params.containsKey('isVHS') && (params.isVHS == 'on')) {
//							println("  eq: physStorage == VHS")
							like('mediaFormat', 'VHS' )
						}
						if( params.containsKey('isDVD') && (params.isDVD == 'on')) {
//							println("  eq: physStorage == DVD")
							eq('mediaFormat', 'DVD' )
						}
// niy
//						if( params.containsKey('isOther') && (params.isOther == 'on')) {
//							println("  eq: ''")
//							eq('mediaFormat', null)
//						}
					}
					"or" { //$HR super-clunky solution
						if( params.containsKey('fsk0')) {
//							session.myParams.putAt('fsk0', params.fsk0)
							if(params.fsk0 == 'on') {
	//							println("  ilike: fsk == 0")
								eq("fsk", "0" )
							}
						}
						if( params.containsKey('fsk6') && (params.fsk6 == 'on')) {
//							println("  ilike: fsk == 6")
							eq("fsk", "6" )
						}
						if( params.containsKey('fsk12') && (params.fsk12 == 'on')) {
//							println("  ilike: fsk == 12")
							eq("fsk", "12" )
						}
						if( params.containsKey('fsk16') && (params.fsk16 == 'on')) {
//							println("  ilike: fsk == 16")
							eq("fsk", "16" )
						}
						if( params.containsKey('fsk18') && (params.fsk18 == 'on')) {
//							println("  ilike: fsk == 18")
							eq("fsk", "18" )
						}
						if( params.containsKey('fskUK') && (params.fskUK == 'on')) {
//							println("  ilike: fsk == unbekannt")
							eq("fsk", "" )
						}
					}
					"or" { //$HR super-clunky solution  ('E', 'D', 'M', 'F', 'O')
						if( params.containsKey('langE') && (params.langE == 'on')) {
							eq("languages", 'E' )
						}
						if( params.containsKey('langD') && (params.langD == 'on')) {
							eq("languages", 'D' )
						}
						if( params.containsKey('langM') && (params.langM == 'on')) {
							eq("languages", 'M' )
						}
						if( params.containsKey('langF') && (params.langF == 'on')) {
							eq("languages", 'F' )
						}
						if( params.containsKey('langO') && (params.langO == 'on')) {
							eq("languages", 'O' )
						}
					}
					order("title", "asc") //$HR P: z.Zt. wird nicht im GSP sortiert
//					firstResult(params.containsKey('offset') ? params.offset : 0)
//					maxResults(20)
				}
			}
			movies = new ArrayList()
			for(i in (0 .. (session.maxRecords-1)) ) {
				if(i < session.movies.size()) {
					println("${i}: ${session.movies[i]}")
					movies.add(session.movies[i])
				}
			}
		}
		else if(params.containsKey('listAll')) { // used to list all (= 'search' without parameters)
			// GSP called for the first time (without parameters from GSP)
			session.movies = null  // => no list is shown
			session.myParams = new HashMap()
			session.maxRecords = 20
			
 			def c = Movie.createCriteria() // we use 'GORM Criteria DSL' to do a dynamic search
			session.movies = c.list() {
				order("title", "asc") //$HR P: z.Zt. wird nicht im GSP sortiert
			}
			
			movies = new ArrayList()
			for(i in (0 .. (session.maxRecords-1)) ) {
				if(i < session.movies.size()) {
					println("${i}: ${session.movies[i]}")
					movies.add(session.movies[i])
				}
			}
		}
		else if(params.containsKey('format') && params?.format && params.format != 'html') { // export all movies in the list
//			String basedir = /..\..\..\..\_Programming\_Groovy\parseCSV\videodb/
//			String filename = "${basedir}\\hr-movies.${params.extension}" 
//			response.contentType = grailsApplication.config.grails.mime.types[params.format]
//			response.setHeader("Content-disposition", "attachment; filename=${filename}")
////			println("here 111")
//			
//			
///*
//			List fields =
//		String mediaId // the former "ID", not unique (e.g. "K"=Kauf-DVD, or unlabelled DVDs); todo: make input uppercase
//			//$HR todo: for HD-Kopien: HD-Name + filename
//		String mediaFormat // VHS, DVD, etc. (BluRay, mp4 ...)
//		String title
//		String fsk // todo: eigene Einschätzungen oder (besser) tags (language18, sex16 etc.)
//		String source  // how it was created (source, tools, compression ratio etc.)
//		String remark    // (anything)
//		Boolean copyOnHD // whether we have a copy in our hard disk archive => should be an extra table  $HR todo nullable Boolean
//		String infoLink  //$HR niy: n>1
//		// removed: "Fam. Frank"
//		String physStorage // added: the place where the movie is stored, z.B. 'DVD K' = "Schatzkiste(n)"; P: some places are unknown
//		String languages // language codes: 'D'=deutsch, 'E'=english, 'M'=Musik, 'F'=Französisch, 'O'=OmU //$HR weak, improve (subtitles etc.); Kombinationen werden nicht abgebildet (z.B. 'E D' etc.)
//		String offset // hh:mm:ss (für VHS-Bänder) oder [1, 2, ...] für DVDs mit mehreren Titeln
//		String quality // hint on quality; VHS: 'SP'=short play, 'LP'=long play (inferior quality); DVD: e.g. compression percentage
//		Date dateCreated // (added; nullable for imported data)
//*/
//
//			
//			List fields = ["mediaId", "title"] 
//			Map labels = ["mediaId": "mediaId", "title": "Titel"]
//			println("here 222")
//			
//			/* Formatter closure in previous releases def upperCase = { value -> return value.toUpperCase() } */
//			
//			// Formatter closure def upperCase = { domain, value -> return value.toUpperCase() }
//			
//			Map formatters = null//[] 
//			println("here 333")
//			Map parameters = [title: "Cool books", "column.widths": [0.2, 0.3, 0.5]]
//			println("here 444")
//			exportService.export(params.format, response.outputStream, session.movies, fields, labels, formatters, parameters)
//
////			exportService.export(params.format, response.outputStream, session.movies, [:], [:])
//			println("exported: ${filename}")
//			
			
			def conn = new groovy.sql.Sql( // HRc initialise with existing connection from 'Spring context'
//				(java.sql.Connection) AH.application.mainContext.sessionFactory.currentSession.connection())
//				(java.sql.Connection) grailsApplication.mainContext.sessionFactory.currentSession.connection())
				(java.sql.Connection) grailsApplication.getMainContext().sessionFactory.currentSession.connection())
			
			try
			{
			  def date = new Date()
			  def formattedDate = date.format('yyyy-MM-dd-HHmmss')
			  println(formattedDate)
//			  conn.execute("BACKUP TO 'D:/_work_hr/experiments/_WebProgramming/_Grails/grails211/${formattedDate}-helibase-data.zip'")
			  String sqlcmd = "BACKUP TO '${formattedDate}-helibase-data.zip'"
			  println(sqlcmd)
			  conn.execute(sqlcmd)
			}
			catch (Exception e)
			{
			  System.out.println "Error " + e.toString()
//			  System.out.println proc
			}
			conn.close()
		} else { // GSP called for the first time (without parameters from GSP)
			session.movies = null  // => no list is shown
			session.myParams = null
			session.maxRecords = 20
		}


		[ params : params,  // sets initial form fields to previous parameters (= are not cleared after submitting)
		  movies : movies ] // populate movie list
	}


	/* schick: automatische Feldsuche statt expliziter Aufzählung
	// form action will return here (action=advSearch)
	def advSearch_weg = { //$HR todo: erweitern auf mehrere Suchbegriffe
		def movies = null
		if(params != null) { // null if called for the first time
			def movieProps = Movie.metaClass.properties*.name
			movies = Movie.withCriteria { // we use 'GORM Criteria DSL' to do a dynamic search
				// "${params.queryType}" {
				"and" { // we omit the query type and use only 'AND'
//					println("withCriteria: ${params.queryType}")
					params.each { field, value ->
	
						if (movieProps.grep(field) && value) { // GiA 4.3.2: check whether properties belong to the Movie class
//							println("    ilike(${field}, %${value}%)") // '%' = do full text search with wildcards
							ilike(field, "%${value}%") // '%' = do full text search with wildcards
						}
					}
				}
			}
		} else { // no parameters => first call => list all  $HR etwas unscharf
			movies = Movie.findAll()
		}
		[ params : params,  // sets initial form fields to previous parameters (= are not cleared after submitting) $HR todo radio buttons 
		  movies : movies ] // populate movie list
	}
*/

	def insert = { field -> 
		def Movie = new Movie()

		// $HR todo  all fields should be trimmed
		//field.each { it = it.trim() } // strip leading and trailing spaces  $HR does not work: 

		// auto: id
		Movie.mediaId = field[0]
		Movie.mediaFormat = 'DVD'
		Movie.title   = field[1]
		Movie.fsk     = field[2]
		Movie.source  = field[3].trim()
		Movie.remark  = field[4]
		Movie.copyOnHD= (field[5]=='x' ? true : false) // translate checkmark to boolean; $HR check null, uppercase
		Movie.infoLink= field[6] //$HR set to null if missing
		if(field[0]=='K') // 'K' = Kauf-DVD => Lagerort undefiniert
			Movie.physStorage = null // unknown for Kaufkassette
		else
			Movie.physStorage = (field[0]==~/K.*/ ? 'DVD K' : 'DVD E') // simple check of DVD-storage category ('Knnn' = Kinder)
		// ignore: "Fam. Frank"
		// added for VHS:
		Movie.languages= null
		Movie.offset   = null
		Movie.quality  = null
		// auto: dateCreated()  => $HR besser wäre das gleiche Dummy-Datum für alle importierten Records, z.B. Import-Datum
		
		return Movie
	}
	
	def insert_adult = { field -> // $HR mit insert zusammenlegen
		def Movie = new Movie()

		// auto: id
		Movie.mediaFormat = 'DVD'
		Movie.mediaId = field[0]
		Movie.title   = field[1]
		Movie.fsk     = field[2]
		Movie.source  = field[3]
		Movie.remark  = field[4]
		Movie.copyOnHD= (field[5]=='x' ? true : false) // translate checkmark to boolean; $HR check null, uppercase
		Movie.infoLink= null
		// ignore: "Fam. Frank"
		Movie.physStorage = 'DVD A'
		// added for VHS:
		Movie.languages= null
		Movie.offset   = null
		Movie.quality  = null
		// auto: dateCreated()  => $HR besser wäre das gleiche Dummy-Datum für alle importierten Records, z.B. Import-Datum
		
		return Movie
	}
	
	def insert_vhs = { field ->
		def Movie = new Movie()

		//AUTOID;TITLE;NR;SPRACHE;TYP;OFFSET;PLAY
		// auto: id
		Movie.mediaId = (field[2] == '0' ? null : field[2]) // 'NR'  ('0' = Kaufkassette, Offset ist dann = '00:00:00', obwohl das nicht richtig ist (es gibt oft einen Vorspann) $HR improve)
		Movie.mediaFormat = 'VHS'
		Movie.title   = field[1] // 'TITLE'
		Movie.fsk     = '' // = 'unknown'
		// TYP: 'K'=Kaufkassette, 'E'=Eigenaufnahme (von VHS oder DVD oder TV)
		if(field[4] == '1') { Movie.source  = 'K'	}
		if(field[4] == '2') { Movie.source  = 'E'	}
		Movie.remark  = null
		Movie.copyOnHD= false
		Movie.infoLink= null
		if(field[2]=='0') // '0' = Kaufkassette => Lagerort undefiniert
			Movie.physStorage = null
		else
			Movie.physStorage = 'VHS Bibliothek'
		// ignore: "Fam. Frank"
		// added for VHS:
		Movie.languages = null
		// 'SPRACHE': ['E', 'D', 'M', 'F', 'O']
		if(field[3] == '1') Movie.languages = 'E' //$HR mapping eleganter lösen
		if(field[3] == '2') Movie.languages = 'D'
		if(field[3] == '3') Movie.languages = 'M'
		if(field[3] == '4') Movie.languages = 'F'
		if(field[3] == '5') Movie.languages = 'O'
		Movie.offset  = field[5] // 'OFFSET' (hh:mm:ss)
		Movie.quality = field[6] // 'PLAY'
		// auto: dateCreated()  => $HR besser wäre das gleiche Dummy-Datum für alle importierten Records, z.B. Import-Datum
		
		return Movie
	}
	
	def importdb = {
		def resultList = new ArrayList() // list of import results
		int totalErrors = 0

		// $HR filenames hardcoded:
		resultList.add(CSVWithNestedQuotesParser.parseCSVFile(/..\..\..\..\_Programming\_Groovy\parseCSV\videodb\dvd-schatzkiste.csv/, 8, 1, insert))

		//$HR todo: diese Records dürfen nicht jedem angezeigt werden => evtl. auch für Records aus anderen "Storages" => z.B. Tags einführen
		resultList.add(CSVWithNestedQuotesParser.parseCSVFile(/..\..\..\..\_Programming\_Groovy\parseCSV\videodb\dvd-adult.csv/, 6, 3, insert_adult))

		resultList.add(CSVWithNestedQuotesParser.parseCSVFile(/..\..\..\..\_Programming\_Groovy\parseCSV\videodb\vhs.csv/, 7, 1, insert_vhs))

		// imported last => gives "high" Ids so that records appear to be created last (which is roughly correct)  $HR besser mit Sort-Funktion für DVD E-records
		// eigentlich nur 8 Felder, aber einige Records haben mehr:
		resultList << CSVWithNestedQuotesParser.parseCSVFile(/..\..\..\..\_Programming\_Groovy\parseCSV\videodb\dvd-main.csv/, 9, 1, insert)

		resultList.each { totalErrors += it.nerrors  }
		println("Total errors: $totalErrors")

		render (view: '/importdb', model: [results: resultList, errors: totalErrors]) //$HR todo: total records read
	}
	
//	def create = {
///*		params.isDVD = 'on'
//		params
//*/
//	}
	
    def create() { // static scaffolding: see GiA p.102
		println("movie.create: ${params}")//$HR debug
        def movieInstance = new Movie()
        //movieInstance.properties = params  // $HR wozu ?
		
		// set some defaults for user convenience:
		movieInstance.mediaFormat = 'DVD'
		movieInstance.physStorage = 'DVD E'
		movieInstance.source      = 'E'
/*QBE:		
		def m = Movie.findAll(movieInstance)
*/
		// the current rule how to determine the next mediaId:
		def c = Movie.createCriteria() // we use 'GORM Criteria DSL' to do a dynamic search
		def m = c.list() {
			ilike("mediaId", "E___-B") // '%' = do full text search with wildcards
			order("mediaId", "asc")
		}
		m.each { println("DVD E: ${it.mediaId}") } //$HR debug
		def nextId = 'Ennn-B'
		if(m.size() > 0) {
			def lastId = m[m.size()-1].mediaId
			println("last mediaId = ${lastId}")
			nextId = "E${(lastId[1..3].toInteger()+1).toString()}-B" // $HR besser mit pattern ?!
			println("next mediaId = ${nextId}")
			movieInstance.mediaId = nextId
		}
		
		
		println("movie:create: properties = ${movieInstance.properties}")//$HR debug
        return [movieInstance: movieInstance]
    }
	
    def edit(Long id) {
        def movieInstance = Movie.get(id)
        if (!movieInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'movie.label', default: 'Movie'), id])
            redirect(action: "list")
            return
        }

        [movieInstance: movieInstance]
    }

    def update(Long id, Long version) {
        def movieInstance = Movie.get(id)
        if (!movieInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'movie.label', default: 'Movie'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (movieInstance.version > version) {
                movieInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'movie.label', default: 'Movie')] as Object[],
                          "Another user has updated this Movie while you were editing")
                render(view: "edit", model: [movieInstance: movieInstance])
                return
            }
        }

        movieInstance.properties = params

        if (!movieInstance.save(flush: true)) {
            render(view: "edit", model: [movieInstance: movieInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'movie.label', default: 'Movie'), movieInstance.id])
        redirect(action: "show", id: movieInstance.id)
    }

    def show(Long id) {
		println("movie.show: ${params}")//$HR debug
        def movieInstance = Movie.get(id)
        if (!movieInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'movie.label', default: 'Movie'), id])
            redirect(action: "list")
            return
        }

        [movieInstance: movieInstance]
    }
	
    def backup() {
		def conn = new groovy.sql.Sql( // HRc initialise with existing connection from 'Spring context'
//				(java.sql.Connection) AH.application.mainContext.sessionFactory.currentSession.connection())
//				(java.sql.Connection) grailsApplication.mainContext.sessionFactory.currentSession.connection())
			(java.sql.Connection) grailsApplication.getMainContext().sessionFactory.currentSession.connection())
		
		try
		{
		  def date = new Date()
		  def formattedDate = date.format('yyyy-MM-dd-HHmmss')
		  println(formattedDate)
//			  conn.execute("BACKUP TO 'D:/_work_hr/experiments/_WebProgramming/_Grails/grails211/${formattedDate}-helibase-data.zip'")
		  String sqlcmd = "BACKUP TO '${formattedDate}-helibase-data.zip'"
		  println(sqlcmd)
		  conn.execute(sqlcmd)
		}
		catch (Exception e)
		{
		  System.out.println "Error " + e.toString()
//			  System.out.println proc
		}
		conn.close()
    }
	
}



// use logging:
//import java.util.logging.*
//Logger.getLogger('groovy.sql').level = Level.FINE

//$HR extract to service 
class CSVWithNestedQuotesParser {
	// TODO: parse fields with CR/LF correctly
	
	// P: absoluter Pfad
//	static final String basedir = "D:\\_work_hr\\experiments\\_WebProgramming\\_Grails\\grails11"
	static final String SEPARATOR = ';'
	static final String QUOTE = '"'
	static int NFields  // the number of required fields
	static int NSkip // skip NSkip header records at file start
	static def result
	static String line
	static String field
	static int size
	static i
	
	static void parseToSeparator() { // must be called on the first character after a SEPARATOR, recurses after that
		char c = line[i]
		if(c == SEPARATOR) { // separator found => flush field to list
			result << field
			field = ""
		} else {
			field += c
		}

		if(i < size-1) { // check if there is a next character
			i++
			if((c == SEPARATOR) && (line[i] == QUOTE)) { // quote after separator detected
				i++
				parseQuoted()
			} else
				parseToSeparator()
		} else {
			result << field  // flush last field
		}
	}
	
	static void parseQuoted() { // always to be called AFTER the starting quote
		if(i < size) { // position still valid
			char c = line[i]
			boolean isNestedQuote = false
			if((c == QUOTE) && (i+1 < size) ) { // quote found: peek ahead (if possible)  and check if it is a nested quote
				if(line[i+1] == QUOTE) {
					isNestedQuote = true
					i++ // skip first nested  quote
				}
			}
			if((c == QUOTE) && !isNestedQuote) { // quote found => flush field to list
				result << field
				field = ""
				i++
				if(i < size) {
					c = line[i]
					if(c == SEPARATOR) {
						i++
						if(i < size) {
							c = line[i]
							if(c == QUOTE) {
								i++
								parseQuoted()
							} else
								parseToSeparator()
						} else {
							result << field // flush last empty field after separator
						}
					} else
						println "Error: separator expected at pos $i, found '$c'"
				} // else: finished
			} else {
				field += c  // normal character or nested quote
				i++
				parseQuoted()
			}
		} else {
			println "Error at pos $i"
		}
	}
	
	static def parse(ln) {
		line = ln
		size = line.size()
		result = []
		if(size > 0) {
			i = 0
			field = ""
			if(line[i] == QUOTE) {
				i++
				parseQuoted()
			} else {
				parseToSeparator()
			}
		}
		if(result.size() < NFields)
			(1..(NFields - result.size()) ).each() { result << '' } // add empty fields until we have all
		return result
	}
	
 
    static def parseCSVFile(filename, nfields, nskip, insert_function) {
        println "Open file \"${filename}\""
		String text = new File(filename).getText('ISO-8859-1') // P: with this method, probably the whole file must fit into memory...
		
        int lineCount = 0  
        int errorCount = 0
        NFields = nfields // all records should have <nfields> fields
        NSkip = nskip // skip <nskip> header lines
        text.eachLine() { line ->    // strips CR/LF
            if(NSkip == 0) {
                java.util.ArrayList field = parse(line) // $HR P: using "def" results in a conversion to String: !!?
                int sz = field.size()
                print "$sz: |$line| => |"
                field.each() {
                    print "$it|"
                }
                
                lineCount++
                if(sz != NFields) {
                    errorCount++
                    println "ERROR: sz=$sz    $line  => $result"
                    assert false
                }
				def rec = insert_function(field)  // the insert function (depends on imported table structure)
		
				if(rec == null) {
					println(" SKIPPED") // skipped
				} else if (rec.validate()) { //$HR sollte nicht im Parser passieren (Closure verwenden)
					rec.save()
					println()  //("OK")
				} else {
					println ("Failed: Create Movie")
					rec.errors.allErrors.each { // $HR Spring Errors interface containing data binding and/or validation errors
						println it
					}
					errorCount++
				}
            } else
                NSkip-- // skip one line
        }
        println("$lineCount lines with data, $errorCount errors")

		return [filename: filename, nlines: lineCount, nerrors: errorCount]
    } 
}
