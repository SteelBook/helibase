<html>
    <head>
        <title>Erweiterte Filmsuche</title>
        <meta name="layout" content="main"/>
    </head>
    <body>

        <formset>
            <table>
                <g:form action="advSearch">
                    <tr>
                        <td>Titel</td>
                        <td><g:textField name="title" value="${params?.title}" />  <label for="title">(Suchbegriffe)</label></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Format</td>
                        <td>
                        	<!-- clunky solution, improve ASAP -->
	                        <g:checkBox name="isVHS"   value="${params.isVHS}" /> <label for="isVHS">VHS&nbsp;</label>
	                        <g:checkBox name="isDVD"   value="${params.isDVD}" /> <label for="isDVD">DVD&nbsp;</label>
	                    </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>FSK</td>
                        <td>
                        	<!-- clunky solution, improve ASAP -->
	                        <g:checkBox name="fsk0"  value="${params.fsk0}" /> <label for="fsk0">0&nbsp;</label>
	                        <g:checkBox name="fsk6"  value="${params.fsk6}" /> <label for="fsk6">6&nbsp;</label>
	                        <g:checkBox name="fsk12" value="${params.fsk12}"/> <label for="fsk12">12&nbsp;</label>
	                        <g:checkBox name="fsk16" value="${params.fsk16}"/> <label for="fsk16">16&nbsp;</label>
                       		<g:checkBox name="fsk18" value="${params.fsk18}"/> <label for="fsk18">18&nbsp;</label>
	                        <g:checkBox name="fskUK" value="${params.fskUK}"/> <label for="fskUK">(unbekannt)</label>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Sprache</td>
                        <td>
                        	<!-- clunky solution, improve ASAP -->
                        	<!-- 'E', 'D', 'M', 'F', 'O' -->
	                        <g:checkBox name="langE"  value="${params.langE}" /> <label for="langE">English&nbsp;</label>
	                        <g:checkBox name="langD"  value="${params.langD}" /> <label for="langD">Deutsch&nbsp;</label>
	                        <g:checkBox name="langM"  value="${params.langM}" /> <label for="langM">Musikvideo&nbsp;</label>
	                        <g:checkBox name="langF"  value="${params.langF}" /> <label for="langF">Franz&ouml;sisch&nbsp;</label>
	                        <g:checkBox name="langO"  value="${params.langO}" /> <label for="langO">OmU&nbsp;</label>
                        </td>
                        <td></td>
                    </tr>

                    <tr>
                        <td/>
                        <td>
                        <g:submitButton name="search" value="Suche starten"/></td>
                    </tr>
                </g:form>
            </table>

        </formset>

		<g:if test="${movies != null}">
	        <h1><strong>${session.movies.size()}</strong> Treffer</h1>
	        
			<g:paginate total="${session.movies ? session.movies.size() : 0}" max="${session.maxRecords ? session.maxRecords : 0}" />
	        <div class="list">
	            <table>
	                <thead>
	                    <tr>
	               	        <g:sortableColumn property="mediaId" title="Media Id" />
	               	        <g:sortableColumn property="mediaFormat" title="Format" />
	               	        <g:sortableColumn property="physStorage" title="Storage" />
	               	        <g:sortableColumn property="fsk" title="FSK" />
	               	        <g:sortableColumn property="languages" title="Sprachen" />
	               	        <th>Link</th>
	               	        <g:sortableColumn property="title" title="Titel" />
	                    </tr>
	                </thead>
	                <tbody>
	                <g:each in="${movies}" status="i" var="movie">
	                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                    	<td><g:link action="show" id="${movie.id}">${movie.mediaId ? movie.mediaId : '...'}</g:link></td>
	                    	<td>${movie.mediaFormat}</td>
	                    	<td>${movie.physStorage}</td>
	                    	<td>${movie.fsk}</td>
	                    	<td>${movie.languages}</td>
	                    	<td>
	                    		<g:if test="${!( (movie?.infoLink == null) || (movie?.infoLink == '') )}">
	                    			<g:link url="${movie.infoLink}" target="_blank">more</g:link>
	                    		</g:if>
	                    	</td>
	                    	<td>${movie.title}</td>
	                    </tr>
	                </g:each>
	                </tbody>
	            </table>
	        </div>
			<g:paginate total="${session.movies ? session.movies.size() : 0}" max="${session.maxRecords ? session.maxRecords : 0}" />
		</g:if>

    </body>
</html>
