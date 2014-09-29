<html>
    <head>
        <title>HeliBase: Filmsuche</title>
        <meta name="layout" content="main" />
    </head>
    <body>

        <formset>
            <legend>Filme suchen</legend>

            <g:form action="results">
                <label for="title">Filmtitel</label>
                <g:textField name="title" />
                
                <g:submitButton name="search" value="Suchen"/>
            </g:form>

        </formset>

    </body>
</html>
