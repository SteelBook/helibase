
<%@ page import="com.HeliosResearch.Movie" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'movie.label', default: 'Movie')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-movie" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><!-- HR disable default Navigation   a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li -->
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		
		<div id="list-movie" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="title" title="${message(code: 'movie.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="mediaFormat" title="${message(code: 'movie.mediaFormat.label', default: 'Media Format')}" />
					
						<g:sortableColumn property="physStorage" title="${message(code: 'movie.physStorage.label', default: 'Phys Storage')}" />
					
						<g:sortableColumn property="mediaId" title="${message(code: 'movie.mediaId.label', default: 'Media Id')}" />
					
						<g:sortableColumn property="fsk" title="${message(code: 'movie.fsk.label', default: 'Fsk')}" />
					
						<g:sortableColumn property="source" title="${message(code: 'movie.source.label', default: 'Source')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${movieInstanceList}" status="i" var="movieInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${movieInstance.id}">${fieldValue(bean: movieInstance, field: "title")}</g:link></td>
					
						<td>${fieldValue(bean: movieInstance, field: "mediaFormat")}</td>
					
						<td>${fieldValue(bean: movieInstance, field: "physStorage")}</td>
					
						<td>${fieldValue(bean: movieInstance, field: "mediaId")}</td>
					
						<td>${fieldValue(bean: movieInstance, field: "fsk")}</td>
					
						<td>${fieldValue(bean: movieInstance, field: "source")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${movieInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
