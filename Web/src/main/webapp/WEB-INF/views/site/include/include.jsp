
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
	
<%--     <%@page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%> --%>
	<title><spring:message code="afirmenet.titulo"/></title>
	
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<meta http-equiv="Content-Language" content="es-MX" />
	<meta name="title" content="AfirmeNet" /> 
	<meta name="author" content="Afirme" />
	<meta name="copyright" content="Afirme" />
	<meta name="Language" content="Spanish" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="robots" content="all | index | follow" />
	<meta name="description" content="AfirmeNet" />
	<meta name="keywords" content="afirme,afirmenet,banco,banca,electronica,ebank,bank" />
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

<script type="text/javascript"> 

	if (navigator.appName == "Microsoft Internet Explorer"){
	  	document.write('<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/afirmeico.ico"/>"/>');	
		document.write('<link rel="icon" type="image/x-icon" href="<c:url value="/resources/afirmeico.ico"/>"/>');
	}else{
  		document.write('<link rel="shortcut icon" type="image/png" href="<c:url value="/resources/afirmeico.png"/>"/>');
		document.write('<link rel="icon" type="image/png" href="<c:url value="/resources/afirmeico.png"/>"/>'); 
	}
</script>

<%--   	<link rel="icon" href='<c:url value="/resources/favicon.ico"/>' type="image/vnd.microsoft.icon"/>  --%>
<%--   	<link rel="shortcut icon" href='<c:url value="/resources/favicon.ico"/>' type="image/vnd.microsoft.icon"/>  --%>
<%--   	<link rel="icon" href="<c:url value="/resources/favicon.ico"/>" type="image/x-icon"/>  --%>
<%--   	<link rel="shortcut icon" href="<c:url value="/resources/favicon.ico"/>" type="image/x-icon"/> --%>
  	
  	<link rel="apple-touch-icon" href="<c:url value="/resources/afirmeico.png"/>" />
  	<link rel="apple-touch-icon-precomposed" href="<c:url value="/resources/afirmeico.png"/>" />
  	<link rel="apple-touch-icon" sizes="72x72" href="<c:url value="/resources/afirmeico72x72.png"/>" />
  	<link rel="apple-touch-icon" sizes="144x144" href="<c:url value="/resources/afirmeico144x144.png"/>" />
  	
    <%-- CSS PRINCIPAL	 --%>
    
    <%--CSS PRINCIPAL patrimonial o normal, según flag en sesión N = no, Variable vacía = no, S = si--%>
    <c:if test="${afirmeNetUser.patrimonial == 'N'}">
		<link href='<c:url value="/resources/css/main.css?frameX"/>' rel="stylesheet" type="text/css">   			
	</c:if>
	<c:if test="${empty afirmeNetUser.patrimonial}">
		<link href='<c:url value="/resources/css/main.css?frameX"/>' rel="stylesheet" type="text/css">   			
	</c:if>
	<c:if test="${afirmeNetUser.patrimonial == 'S'}">
		<link href='<c:url value="/resources/css/main_p.css?frameX"/>' rel="stylesheet" type="text/css">   			
	</c:if>	
	
	
	<%-- CSS CARGA LOS ESTILOS DEL MODAL	 --%>
	<link href='<c:url value="/resources/css/fancybox.css"/>' rel="stylesheet" type="text/css" />
	<%-- CSS Contiene los estilos requeridos del plugin Dropkick, utilizado para darle la apariencia a los selectores dropdown	 --%>
	<link href="<c:url value="/resources/css/dropkick.css"/>" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/css/afirme/afirme.css"/>" rel="stylesheet" type="text/css" />
	
	<%-- JS Librería de Javascript que controla interacciones, manipulación de elementos del DOM	 --%>
    <script>window.jQuery || document.write('<script src=\'<c:url value="/resources/js/jquery-1.11.0.min.js"/>\'><\/script>')</script>
    <%-- JS Librería de Javascript que detecta características de HTML5 y CSS3, usado generalmente para la compatibilidad en browsers	 --%>
  	<script src='<c:url value="/resources/js/vendor/modernizr-2.6.2.min.js"/>' type="text/javascript"></script>
  	<%-- JS Contiene la funcion para validar si el browser del dispositivo utilizado es movil --%>
   	<script src='<c:url value="/resources/js/afirmenet/detectmobilebrowser.js"/>' type="text/javascript"></script>
   	<%-- JS Plugin para permitir la edicion del History --%>
   	<script src='<c:url value="/resources/js/history.min.js"/>' type="text/javascript"></script>
  	<%-- JS Contiene las funciones Javascript utilizadas a través de todo el sitio	 --%>  	
  	<script src='<c:url value="/resources/js/main.js"/>' type="text/javascript"></script>
  	<%-- JS Plugin de Javascript utilizado para cajas modales.	 --%>
	<script src='<c:url value="/resources/js/vendor/jquery.fancybox.pack.js"/>' type="text/javascript"></script>
	<%-- JS Plugin de Javascript que ayuda en el despliegue de los bloques de Resumen de Cuentas	 --%> 
	<script src='<c:url value="/resources/js/vendor/packery.js"/>' type="text/javascript"></script>
	<%-- Plugin de Javascript que da funcionalidad a los selectores dropdown estilizados	 --%>
	<script src='<c:url value="/resources/js/vendor/dropkick.js"/>' type="text/javascript"></script>
	<%-- Script para la impresion	 --%>
	<script type="text/javascript" src="<c:url value="/resources/js/afirmenet/jQuery.base64.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/afirmenet/print.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/notificaciones-1.0.0.js"/>"></script>

</head>

	<c:set var="context" value="${pageContext.request.contextPath}"/>
	<fmt:setLocale value="es_MX" scope="session"></fmt:setLocale>
	<spring:eval var="urlPortalAfirme" expression="@URL_PORTAL_AFIRME"/>

	<script type="text/javascript">

		var context = "${context}";
	
		$('document').ready(function(){
	        
	        <%------------Solo Campaña Easy Solutions ----------------------------------%>		
			$("a[rel='/modalEasySolutions.htm'].ir-campania-action").addClass('modal');
			$("a[rel='/modalEasySolutions.htm'].ir-campania-action").attr('data-fancybox-type', 'iframe');
	        $("a[rel='/modalEasySolutions.htm'].ir-campania-action").attr('href','${context}/modalEasySolutions.htm');
	        $("a[rel='/modalEasySolutions.htm'].ir-campania-action").removeClass('ir-campania-action');
	        $("a[rel='/modalEasySolutions.htm'].modal.verde.btn").removeAttr("rel");
	        <%-------------------------------------------------------------%>
	        
			$('.modal').fancybox({
				maxWidth : 600,
				maxHeight : 450,
				width: '100%',
				autoHeight: true,
				fitToView : false,
				padding   : 0
			});
		});
		
		// Inicializar los mensajes de notificacion mas comunes.
		notificacion.TITULO_GENERAL_ERROR              = '<spring:message text="Code 809" code="afirmenet.etiqueta.general.notificacion.error"/>';
		notificacion.MENSAJE_GENERAL_ERROR             = '<spring:message text="Code 809" code="afirmenet.etiqueta.general.notificacion.error.mensaje"/>';
		notificacion.TITULO_GENERAL_ADVERTENCIA        = '<spring:message text="Code 809" code="afirmenet.etiqueta.general.notificacion.advertencia"/>';
		notificacion.MENSAJE_GENERAL_ADVERTENCIA       = '<spring:message text="Code 809" code="afirmenet.etiqueta.general.notificacion.advertencia.mensaje"/>';
		notificacion.TITULO_GENERAL_EXITOSA            = '<spring:message text="Code 809" code="afirmenet.etiqueta.general.notificacion.exitosa"/>';
		notificacion.MENSAJE_GENERAL_EXITOSA           = '<spring:message text="Code 809" code="afirmenet.etiqueta.general.notificacion.exitosa.mensaje"/>';
		notificacion.TITULO_GENERAL_VALIDACION_TOKEN   = '<spring:message text="Code 809" code="afirmenet.etiqueta.general.validacion.de.token"/>';
		notificacion.MENSAJE_GENERAL_VALIDACION_TOKEN  = '<spring:message text="Code 809" code="afirmenet.etiqueta.general.validacion.de.token.mensaje.incorrecto"/>';
		
		// Var generales
		vargeneral.CONTEXT = '${context}';
		vargeneral.PORTAL_AFIRME = '${urlPortalAfirme}';
	</script>
	
<script type="text/javascript"> 
	var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-27277572-1']);
  _gaq.push(['_setDomainName', 'afirmeeninternet.com']);
  _gaq.push(['_setAllowLinker', true]);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>