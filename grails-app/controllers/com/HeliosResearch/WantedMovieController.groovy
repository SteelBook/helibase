package com.HeliosResearch

class WantedMovieController {
	def scaffold = true
	
	static navigationScope = 'admin'
	
    // def index = { }
	
	def insert_wanted = { field ->
		def wantedMovie = new WantedMovie()

		// auto: id
		wantedMovie.title        = field[0]
		wantedMovie.fsk          = field[1]
		wantedMovie.available_lf = field[2]
		wantedMovie.available_vd = field[3]
		wantedMovie.status       = field[4]
		wantedMovie.remark       = field[5]
		wantedMovie.infoLink     = field[6]
		wantedMovie.orderLink    = field[7]
		wantedMovie.buyLink      = field[8]
		// auto: dateCreated()  => $HR besser wäre das gleiche Dummy-Datum für alle importierten Records, z.B. Import-Datum
		
		return wantedMovie
	}
	
	def importdb = {
		def resultList = new ArrayList() // list of import results
		int totalErrors = 0

		// $HR hardcoded:
		resultList << CSVWithNestedQuotesParser.parseCSVFile(/..\..\..\..\_Programming\_Groovy\parseCSV\videodb\dvd-get.csv/, 9, 4, insert_wanted)
		resultList << CSVWithNestedQuotesParser.parseCSVFile(/..\..\..\..\_Programming\_Groovy\parseCSV\videodb\dvd-get-kids.csv/, 9, 4, insert_wanted)
		resultList << CSVWithNestedQuotesParser.parseCSVFile(/..\..\..\..\_Programming\_Groovy\parseCSV\videodb\dvd-get-check.csv/, 9, 4, insert_wanted)

		resultList.each { totalErrors += it.nerrors  }
		println("Total errors: $totalErrors")

		render (view: '/importdb', model: [results: resultList, errors: totalErrors]) //$HR todo: total records read
	}
}
