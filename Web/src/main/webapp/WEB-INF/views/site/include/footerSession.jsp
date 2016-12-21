
<footer>
	<div class="container hide-print">
		<img src='<c:url value="/resources/img/photos/globo.png"/>' id="globo">
		<div class="row mb40 flex">
			<div class="col5">
				<img src='<c:url value="/resources/img/icons/logotipo_setrata.png"/>'  alt="20 años">
			</div>
			<div class="col3 pt15" align="right">
				<div class="dib">	
					<h6>
						<spring:message code="afirmenet.asistenciaTelefonica" /><br><a href="tel:018002234763" style="cursor: pointer;" ><spring:message code="afirmenet.01800" /></a><br>								
					</h6>
				</div>						
			</div>
			<div class="col3 pt15" align="right">
				<div class="dib">
				   <h6>								
						<spring:message code="afirmenet.asistenciaTelefonica.mty" /><br><a href="tel:83183990" style="cursor: pointer;" ><spring:message code="afirmenet.83183990" /></a>
				   </h6>
				</div>
				<%-- <a href="#" class="btn btn-lg lima"><spring:message code="afirmenet.AYUDA" /></a> --%>
				<span href="#" style="opacity: 0.0; filter: alpha(opacity=0); cursor: default;" class="btn btn-lg lima"><spring:message code="afirmenet.AYUDA" /></span>
			</div>
		</div>
		<div class="row">
			<div class="col6">
				<strong><spring:message code="afirmenet.copyright"/></strong><br>
				<a href="#"><spring:message code="afirmenet.avisoPrivacidad"/></a> | <a href="#"><spring:message code="afirmenet.terminosLegales"/></a>
			</div>
			<div class="col5" align="right">
				<p class="h6"><spring:message code="afirmenet.sellosDeSeguridad"/></p>
				
				<img class="ml20" src= '<c:url value="/resources/img/icons/verisign.jpg"/>'alt="Verisign">
				<%--  Alfonso Arizpe dice que no somos miembros de AMIPCI así que retiramos este sello de seguridad  
 				<img class="ml20" src= '<c:url value="/resources/img/icons/amipci.jpg"/>' alt="AMIPCI"> 
				--%>
				<%-- Clientless Easy Solutions se pospone la instalación
				<a class="modal ml20" href="${context}/modalEasySolutions.htm" data-fancybox-type="iframe" > 
		 			<img src="${context}/resources/img/afirme/easysolutions.png" height="40"/>
				</a>
				--%>
			</div>
		</div>
	</div>
	<%--   Clientless Easy Solutions se pospone la instalación
	<script type="text/javascript" id="64bb2703-e0c3-49e7-a41d-d4115ea3a62f">
	(function(s,h,ci,si){s=s+Math.random().toString(36).substring(7)+"/login.js?clientId="+ci;window._dmo={src:s,host:h,sessionId:si,clientId:ci};var a=document.createElement("script");a.type="text/javascript";a.src=s;a.async=!0;var b=document.getElementsByTagName("script")[0];b.parentNode.insertBefore(a,b);})( 'https://clientless.easysol.net/requestserver/script/v1/', 'https://clientless.easysol.net', '99fb97bd-e90b-43c4-98cd-9f90483d547c');
	</script>
	--%>
</footer>
<%--Esta sección va en el header, que alguien lo cambie :P --%>
<%-- JS para las funciones propias de AfirmeNet	 --%>
<script src='<c:url value="/resources/js/afirmenet/utils.js"/>' type="text/javascript"></script>	
<script type="text/javascript">
	$(document).ready(function() {
		loadAllFormElements();
	});
</script>	