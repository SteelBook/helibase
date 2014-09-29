navigation = {

	// Declare the "app" scope, used by default in tags
	app {
//		movies (controller: 'movie', action: 'list', title: 'titel-1')
		// import is a keyword => use quotes
//		movie(titleText: 'Filme', controller: 'movie', action: 'advSearch') {//, uri: '?offset=0&max=20') {
		home(uri: '/', titleText: 'Start')
		
		movie(titleText: 'Filme', controller: 'movie', action: 'advSearch', 
				uri: '/movie/advSearch?listAll=') { // in the link: overrides controller/action with URI
			create (controller: 'movie') { //titleText: 'Neuer Film') { // "action: create" by default
				
			}
			importdb (titleText: "Import") {}
		}
		game (titleText: 'Spiele', action: 'list')  { // controller is selected by default (here: 'game')
			create (controller: 'game') {} //(titleText: 'Neues Spiel')
			importdb (titleText: "Import") {}
		}
//		tools(titleText: 'Werkzeuge')

		wantedMovie(titleText: 'Wunschliste', action: 'list') {
			create (controller: 'wantedMovie') {}
			importdb (titleText: "Import") {}
		}
//		login(titleText: 'Anmelden')
	}
	
/*	
	movie {
		advSearch()
	}
	
	importdb {
		
	}
*/
/*
	admin {
        importMovies (controller: 'movie', action: 'importdb', titleText: 'Import Movies')
//		wantedMovie(titleText: 'Wunschliste')
	}
*/
}
