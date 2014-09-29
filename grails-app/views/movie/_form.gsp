<%@ page import="com.HeliosResearch.Movie" %>



<div class="fieldcontain ${hasErrors(bean: movieInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="movie.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField id="title1" size="80"  name="title" maxlength="100" required="" value="${movieInstance?.title}"/>
	<!-- input class="button"  type="button" value="IMDb" onclick="window.open('http://www.imdb.com/find?q=' + document.getElementById('title1').value)" -->
	<!-- a class="hr-button" onclick="window.open('http://www.imdb.com/find?q=' + document.getElementById('title1').value)">IMDb</a -->
	<tooltip:tip value="durchsucht IMDb nach dem eingegebenen Titel oder Stichwort">
		<a class="hr-button" id="b1" target="_blank" 
			onmouseover="this.href='http://www.imdb.com/find?q=' + document.getElementById('title1').value">Suchen..
		</a>
	</tooltip:tip>
</div>

<div class="fieldcontain ${hasErrors(bean: movieInstance, field: 'mediaFormat', 'error')} ">
	<label for="mediaFormat">
		<g:message code="movie.mediaFormat.label" default="Media Format" />
		
	</label>
	<g:select name="mediaFormat" from="${movieInstance.constraints.mediaFormat.inList}" value="${movieInstance?.mediaFormat}" valueMessagePrefix="movie.mediaFormat" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: movieInstance, field: 'physStorage', 'error')} ">
	<label for="physStorage">
		<g:message code="movie.physStorage.label" default="Phys Storage" />
		
	</label>
	<g:select name="physStorage" from="${movieInstance.constraints.physStorage.inList}" value="${movieInstance?.physStorage}" valueMessagePrefix="movie.physStorage" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: movieInstance, field: 'mediaId', 'error')} ">
	<label for="mediaId">
		<g:message code="movie.mediaId.label" default="Media Id" />
		
	</label>
	<g:textField name="mediaId" size="4" maxlength="7" value="${movieInstance?.mediaId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: movieInstance, field: 'fsk', 'error')} ">
	<label for="fsk">
		<g:message code="movie.fsk.label" default="Fsk" />
		
	</label>
	<g:select name="fsk" from="${movieInstance.constraints.fsk.inList}" value="${movieInstance?.fsk}" valueMessagePrefix="movie.fsk" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: movieInstance, field: 'source', 'error')} ">
	<label for="source">
		<g:message code="movie.source.label" default="Source" />
		
	</label>
	<g:select name="source" from="${movieInstance.constraints.source.inList}" value="${movieInstance?.source}" valueMessagePrefix="movie.source" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: movieInstance, field: 'remark', 'error')} ">
	<label for="remark">
		<g:message code="movie.remark.label" default="Remark" />
		
	</label>
	<g:textField name="remark" size="90" value="${movieInstance?.remark}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: movieInstance, field: 'copyOnHD', 'error')} ">
	<label for="copyOnHD">
		<g:message code="movie.copyOnHD.label" default="Copy On HD" />
		
	</label>
	<g:checkBox name="copyOnHD" value="${movieInstance?.copyOnHD}" />
</div>

<div class="fieldcontain ${hasErrors(bean: movieInstance, field: 'infoLink', 'error')} ">
	<label for="infoLink">
		<g:message code="movie.infoLink.label" default="Info Link" />
		
	</label>
	<g:field id="infoLink1" type="url" size="80" name="infoLink" value="${movieInstance?.infoLink}"/>
	<!-- input class="button"  type="button" value="&ouml;ffnen" onclick="window.open('${movieInstance?.infoLink}')" -->
	<!-- a class="hr-button" onclick="window.open('${movieInstance?.infoLink}')">&ouml;ffnen</a -->
	<a class="hr-button" id="b2" target="_blank" onmouseover="this.href=document.getElementById('infoLink1').value">&ouml;ffnen</a>
</div>

<div class="fieldcontain ${hasErrors(bean: movieInstance, field: 'languages', 'error')} ">
	<label for="languages">
		<g:message code="movie.languages.label" default="Languages" />
		
	</label>
	<g:select name="languages" from="${movieInstance.constraints.languages.inList}" value="${movieInstance?.languages}" valueMessagePrefix="movie.languages" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: movieInstance, field: 'offset', 'error')} ">
	<label for="offset">
		<g:message code="movie.offset.label" default="Offset" />
		
	</label>
	<g:textField name="offset" maxlength="8" value="${movieInstance?.offset}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: movieInstance, field: 'quality', 'error')} ">
	<label for="quality">
		<g:message code="movie.quality.label" default="Quality" />
		
	</label>
	<g:textField name="quality" maxlength="3" value="${movieInstance?.quality}"/>
</div>

