
<%@ page import="com.HeliosResearch.Movie" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'movie.label', default: 'Movie')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-movie" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<!-- HR   li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li-->
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-movie" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list movie">
			
				<g:if test="${movieInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="movie.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${movieInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movieInstance?.mediaFormat}">
				<li class="fieldcontain">
					<span id="mediaFormat-label" class="property-label"><g:message code="movie.mediaFormat.label" default="Media Format" /></span>
					
						<span class="property-value" aria-labelledby="mediaFormat-label"><g:fieldValue bean="${movieInstance}" field="mediaFormat"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movieInstance?.physStorage}">
				<li class="fieldcontain">
					<span id="physStorage-label" class="property-label"><g:message code="movie.physStorage.label" default="Phys Storage" /></span>
					
						<span class="property-value" aria-labelledby="physStorage-label"><g:fieldValue bean="${movieInstance}" field="physStorage"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movieInstance?.mediaId}">
				<li class="fieldcontain">
					<span id="mediaId-label" class="property-label"><g:message code="movie.mediaId.label" default="Media Id" /></span>
					
						<span class="property-value" aria-labelledby="mediaId-label"><g:fieldValue bean="${movieInstance}" field="mediaId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movieInstance?.fsk}">
				<li class="fieldcontain">
					<span id="fsk-label" class="property-label"><g:message code="movie.fsk.label" default="Fsk" /></span>
					
						<span class="property-value" aria-labelledby="fsk-label"><g:fieldValue bean="${movieInstance}" field="fsk"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movieInstance?.source}">
				<li class="fieldcontain">
					<span id="source-label" class="property-label"><g:message code="movie.source.label" default="Source" /></span>
					
						<span class="property-value" aria-labelledby="source-label"><g:fieldValue bean="${movieInstance}" field="source"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movieInstance?.remark}">
				<li class="fieldcontain">
					<span id="remark-label" class="property-label"><g:message code="movie.remark.label" default="Remark" /></span>
					
						<span class="property-value" aria-labelledby="remark-label"><g:fieldValue bean="${movieInstance}" field="remark"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movieInstance?.copyOnHD}">
				<li class="fieldcontain">
					<span id="copyOnHD-label" class="property-label"><g:message code="movie.copyOnHD.label" default="Copy On HD" /></span>
					
						<span class="property-value" aria-labelledby="copyOnHD-label"><g:formatBoolean boolean="${movieInstance?.copyOnHD}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${movieInstance?.infoLink}">
				<li class="fieldcontain">
					<span id="infoLink-label" class="property-label"><g:message code="movie.infoLink.label" default="Info Link" /></span>
					
						<span class="property-value" aria-labelledby="infoLink-label">
						<a href="${movieInstance?.infoLink}" target="_blank">IMDb</a>
						</span>
				</li>
				</g:if>
			
				<g:if test="${movieInstance?.languages}">
				<li class="fieldcontain">
					<span id="languages-label" class="property-label"><g:message code="movie.languages.label" default="Languages" /></span>
					
						<span class="property-value" aria-labelledby="languages-label"><g:fieldValue bean="${movieInstance}" field="languages"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movieInstance?.offset}">
				<li class="fieldcontain">
					<span id="offset-label" class="property-label"><g:message code="movie.offset.label" default="Offset" /></span>
					
						<span class="property-value" aria-labelledby="offset-label"><g:fieldValue bean="${movieInstance}" field="offset"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movieInstance?.quality}">
				<li class="fieldcontain">
					<span id="quality-label" class="property-label"><g:message code="movie.quality.label" default="Quality" /></span>
					
						<span class="property-value" aria-labelledby="quality-label"><g:fieldValue bean="${movieInstance}" field="quality"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movieInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="movie.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${movieInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${movieInstance?.id}" />
					<g:link class="edit" action="edit" id="${movieInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
