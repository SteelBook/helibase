<html>
    <head>
        <title>HeliBase: Suchergebnis</title>
        <meta name="layout" content="main" />
    </head>
    <body>

        <h1>Gefundene Filmtitel</h1>
        <p>Es wurden ${com.HeliosResearch.Movie.count()} Datens&auml;tze durchsucht
        nach Eintr&auml;gen, die mit <em>${searchTerm}</em> &uuml;bereinstimmen.
        Es wurden <strong>${movies.size()}</strong> Treffer gefunden.
        </p>
        <ul>
            <g:each var="movie" in="${movies}">
                <li>${movie.title}</li>
            </g:each>
        </ul>

        <g:link action='search'>Neue Suche</g:link>

    </body>
</html>
