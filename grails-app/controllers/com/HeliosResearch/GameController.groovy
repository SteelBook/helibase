package com.HeliosResearch

class GameController {
	def scaffold = true
	
	//static navigationScope = 'movie'
    // def index = { }  // redirected to list if undefined; else: error if index.jsp does not exist
	
	def insert_game = { field ->
		def game = new Game()

		// auto: id
		game.title        = field[0]
		game.platform     = field[1]
		game.remark       = null
		// auto: dateCreated()  => $HR besser wäre das gleiche Dummy-Datum für alle importierten Records, z.B. Import-Datum
		
		return game
	}
	
	def test_insert = { field ->
		def game = new Game()

		// auto: id
		game.title        = field[0]
		game.platform     = field[1]
		game.remark       = null
		// auto: dateCreated()  => $HR besser wäre das gleiche Dummy-Datum für alle importierten Records, z.B. Import-Datum
		
		return game  // null = do not insert
	}
	
	def importdb = {
		def resultList = new ArrayList() // list of import results
		int totalErrors = 0

		resultList << CSVWithNestedQuotesParser.parseCSVFile(
			/..\..\..\..\_Programming\_Groovy\parseCSV\videodb\games.csv/, 2, 1, insert_game) // $HR hardcoded

		resultList.each { totalErrors += it.nerrors  }
		println("Total errors: $totalErrors")

		render (view: '/importdb', model: [results: resultList, errors: totalErrors])
	}
	
	def testimport = {
		def resultList = new ArrayList() // list of import results
		int totalErrors = 0

		resultList << CSVWithNestedQuotesParser.parseCSVFile(
			/..\..\..\..\_Programming\_Groovy\parseCSV\videodb\test-games.csv/, 2, 1, test_insert) // $HR hardcoded

		resultList.each { totalErrors += it.nerrors  } //$HR nonsense  check
		println("Total errors: $totalErrors")

		[results: resultList, errors: totalErrors]
	}
	
	def abc = {
		println("game/abc")
	}
/*	
	def list = {
		println("game/list")
	}*/
}
