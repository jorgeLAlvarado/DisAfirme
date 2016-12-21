<%@ include file="/WEB-INF/views/base/include/include.jsp"%>
<%@ include file="/WEB-INF/views/base/include/headerSession.jsp"%>

<link href="<c:url value="/resources/css/transaccion.css"/>" rel="stylesheet" type="text/css" />
<script src='<c:url value="/resources/js/cambio-correo-1.0.0.js"/>' type="text/javascript"></script>

<script type="text/javascript">

	$(document).ready(function(){
		$('a.atras').click(function() {
			submitFormAnimate($('#correoCuentaAfirme').attr('id'), 'configuraciones/Configuraciones.htm');
		});
		
		$('a.continuar').click(function() {
			if( FSubmitValidation() === true ){
				submitFormAnimate($('#correoCuentaAfirme').attr('id'), 'configuraciones/seguridad/correo/Seguridad_Cambio_Correo_Confirmar.htm');
			}
		});
	});

	validacionVista.ID_TBX_CORREO_ACTUAL                                          = 'tbxCorreoActual';
	validacionVista.ID_TBX_NUEVO_CORREO                                           = 'tbxCorreoNuevo';
	validacionVista.ID_TBX_NUEVO_CORREO_CONFIRMA                                  = 'tbxCorreoNuevoConfirmar';
	validacionVista.TITULO_ADVERTENCIA                                            = '<spring:message text="Code 809" code="afirmenet.etiqueta.general.notificacion.advertencia"/>';
	validacionVista.MSJ_VALIDACION_VISTA_INGRESE_SU_NUEVO_CORREO                  = '<spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.vista.valid.ingrese.su.nuevo.correo"/>';
	validacionVista.MSJ_VALIDACION_VISTA_CORREO_NUEVO_NO_DEBE_SER_IGUAL_ACTUAL    = '<spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.vista.valid.correo.nuevo.no.debe.ser.igual.actual"/>';
	validacionVista.MSJ_VALIDACION_VISTA_CORREO_ES_INVALIDO                       = '<spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.vista.valid.correo.es.invalido"/>';
	validacionVista.MSJ_VALIDACION_VISTA_CORREO_CONFIRMACION_DEBE_SER_IGUAL_NUEVO = '<spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.vista.valid.confirmacion.debe.ser.igual.a.nuevo"/>';
	validacionVista.MSJ_VALIDACION_VISTA_NO_PERMITIDO_PEGAR_TEXTO                 = '<spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.vista.valid.no.permitido.pegar.texto"/>';
	validacionVista.MSJ_VALIDACION_VISTA_CARACTER_NO_PERMITIDO_SERA_IGNORADO      = '<spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.vista.valid.caracter.no.permitido.sera.ignorado"/>';
	
</script>

<section>
	<div class="container">
		<article>
			<div class="col-izq" id="menu-wrap">
				<%@ include file="/WEB-INF/views/site/comun/menu.jsp"%> 
			</div>
				
			<div class="col-der relative">
				<div class="contenido">
				<form:form commandName="correoCuentaAfirme" method="POST" action="">
					<div class="p20">
					
						<c:if test="${not empty errMsg}">
							<script type="text/javascript">
								mostrarNotificacionError('${errMsg}');
							</script>
						</c:if>	
						<c:if test="${not empty mensajeValidacion}">
							<script type="text/javascript">
								mostrarAdvertenciaMensajeValidacion('${mensajeValidacion}');
							</script>
						</c:if>
						
						<div class="mb20"><h3 class="titulo"><spring:message code="afirmenet.etiqueta.conf.correo.titulo"/></h3></div>

			            <a href="#" class="cerrar"></a>
			            <div class="mb30"><div class="pasos">
			              <span class="paso col4 activo"><span> <spring:message text="Code 809" code="afirmenet.etiqueta.general.wizard.numero.paso1"/> <small class="hide-xs"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.paso1.cabecera"/></small></span></span>
			              <span class="paso col4"><span><spring:message text="Code 809" code="afirmenet.etiqueta.general.wizard.numero.paso2"/> <small class="hide-xs"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.paso2.cabecera"/></small></span></span>
			              <span class="paso col4"><span><spring:message text="Code 809" code="afirmenet.etiqueta.general.wizard.numero.paso3"/> <small class="hide-xs"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.paso3.cabecera"/></small></span></span>
			            </div></div>

						<div class="row mb20">
			              <div class="col9 xs-mb20">
			                <h3 class="f-verdeO m0 fL"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.subtitulo"/></h3>
			                <div class="ml20 fL xs-col12 xs-m0"><h6 class="m0 mt7"><span class="op50"><spring:message text="Code 809" code="afirmenet.obligatorio"/></span><span class="f-verdeO"><spring:message code="afirmenet.obligatorio.icon"/></span></h6></div>
			              </div>
			              <div class="col3">
			                <a href="#" class="icon icon-info fR tooltip"><span><span class="icon icon-close2"></span><span class="h6"><strong> <spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.tooltip.titulo"/> </strong></span><span><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.tooltip.mensaje"/></span></span></a>
			              </div>
			            </div>
					
					
						<div class="mb30">
			              <fieldset>
			                <div class="form-label"><label for="importe"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.actual"/></label></div>
			                <div class="form-input">
			                  <input name="tbxCorreoActual" id="tbxCorreoActual" disabled value="${correoCuentaAfirme.correoActual}" class="strong" type="text">
			                </div>
			              </fieldset>
			
			              <fieldset>
			                <div class="form-label"><label for="tbxCorreoNuevo"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.nuevo"/><span class="f-verdeO"><spring:message text="Code 809" code="afirmenet.obligatorio.icon"/></span></label></div>
			                <div class="form-input">
			                	<spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.tu.email.placeholder" var="varEtiquetaCorreoPlaceholder"/>
			                	<form:input maxlength="60" path="correoNuevo" name="tbxCorreoNuevo" id="tbxCorreoNuevo" placeholder="${varEtiquetaCorreoPlaceholder}" type="email" class="req" 
											onkeypress="event.returnValue = codigoPressPW();"
											onkeydown="event.returnValue = codigoDownPW();"
											onpaste="event.returnValue = validaPaste();"/>
			                </div>
			              </fieldset>
			
			              <fieldset>
			                <div class="form-label"><label for="tbxCorreoNuevoConfirmar"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.confirmar.nuevo"/><span class="f-verdeO"><spring:message text="Code 809" code="afirmenet.obligatorio.icon"/></span></label></div>
			                <div class="form-input">
			                  <form:input maxlength="60" path="correoNuevoConfirmacion" name="tbxCorreoNuevoConfirmar" id="tbxCorreoNuevoConfirmar" placeholder="${varEtiquetaCorreoPlaceholder}" type="email" class="req"
											onkeypress="event.returnValue = codigoPressPW();"
											onkeydown="event.returnValue = codigoDownPW();"
											onpaste="event.returnValue = validaPaste();"/>
			                </div>
			              </fieldset>
			            </div>
            
						<!-- <div class="acciones" align="right"> -->
						<div class="btop2 pt20 acciones" align="right">
							<a href="#" class="btn atras mr10"><spring:message text="Code 809" code="afirmenet.botones.atras" /></a>
							<a href="#" class="btn verde continuar"><spring:message text="Code 809" code="afirmenet.botones.continuar" /></a>
			            </div>

					</div> <!-- div p20 -->
					</form:form>
				</div>
			</div>
		</article>
	</div>
</section>


<%@ include file="/WEB-INF/views/base/include/footerSession.jsp"%>