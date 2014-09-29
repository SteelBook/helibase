<html>
    <head>
        <title>Imported Data</title>
		<meta name="layout" content="main">
    </head>
    <body>
        <div class="list">
            <table>
                <thead>
                    <tr>Importierte Dateien</tr>
                    <tr>
               	        <th>Titel</th>
               	        <th># Zeilen</th>
               	        <th># Fehler</th>
                    </tr>
                </thead>
                <tbody>
	                <g:each in="${results}" status="i" var="result">
	                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                    	<td>${result.filename}</td>
	                    	<td>${result.nlines}</td>
	                    	<td>${result.nerrors}</td>
	                    </tr>
	                </g:each>
        			<tr>
        				<td colspan="2">Fehler insgesamt:</td>
        				<td>${errors}</td>
        			<tr>
                </tbody>
            </table>
        </div>
        
    </body>
</html>
