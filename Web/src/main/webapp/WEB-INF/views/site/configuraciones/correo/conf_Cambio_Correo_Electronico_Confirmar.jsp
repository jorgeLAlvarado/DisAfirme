<%@ include file="/WEB-INF/views/base/include/include.jsp"%>
<%@ include file="/WEB-INF/views/base/include/headerSession.jsp"%>

<link href="<c:url value="/resources/css/transaccion.css"/>" rel="stylesheet" type="text/css" />

<script type="text/javascript">

	$(document).ready(function(){
		$('a.atras').click(function() {
			$('#pasoAtras').val('1');
			submitFormAnimate($('#correoCuentaAfirme').attr('id'), 'configuraciones/seguridad/correo/Seguridad_Cambio_Correo.htm');
		});
		
		$('a.btn.verde').click(function() {
			if ($('#token').val() != ""){
				submitFormAnimate($('#correoCuentaAfirme').attr('id'), 'configuraciones/seguridad/correo/Seguridad_Cambio_Correo_Comprobante.htm');
	 		}else{
	        	mostrarAdvertenciaValidacionToken();
			}
		});
	});
 
	
	
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
					<input type="hidden" name="pasoAtras" id="pasoAtras" />
					
					<div class="p20">
						<!--INCLUDE DE ERRORES DE TOKEN-->
		  				<%@ include file="/WEB-INF/views/base/error/errorToken.jsp"%>
						<c:if test="${not empty errMsg}">
							<script type="text/javascript">
								mostrarNotificacionError('${errMsg}');
							</script>
						</c:if>	
						
						<div class="mb20"><h3 class="titulo"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.titulo"/></h3></div>
						<p> <span class="f-verdeO"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.confirmar.verificar.datos"/></span></p>

			            <a href="#" class="cerrar"></a>
			            <div class="mb30"><div class="pasos">
			              <span class="paso col4 activo"><span> <spring:message text="Code 809" code="afirmenet.etiqueta.general.wizard.numero.paso1"/> <small class="hide-xs"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.paso1.cabecera"/></small></span></span>
			              <span class="paso col4 activo"><span><spring:message text="Code 809" code="afirmenet.etiqueta.general.wizard.numero.paso2"/> <small class="hide-xs"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.paso2.cabecera"/></small></span></span>
			              <span class="paso col4"><span><spring:message text="Code 809" code="afirmenet.etiqueta.general.wizard.numero.paso3"/> <small class="hide-xs"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.paso3.cabecera"/></small></span></span>
			            </div></div>
					
						<div class="row mb20">
			              <div class="col9 xs-mb20">
			                <h3 class="f-verdeO m0"><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.subtitulo"/></h3>
			              </div>
			              <div class="col3">
			                <!-- <a href="#" class="icon icon-info fR tooltip"><span><span class="icon icon-close2"></span><span class="h6"><strong>INFORMACIÓN DEL SERVICIO</strong></span><span>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea</span></span></a> -->
			              </div>
			            </div>
					
						<div class="row">
							<div class="col6 xs-break xs-mb15">
								<p><strong><spring:message text="Code 809" code="afirmenet.contrato"/></strong></p>
								<p class="f-verdeO">${correoCuentaAfirme.contrato}</p>
							</div>
							<div class="col6 xs-break">
								&nbsp;
							</div>
		                </div>
		                
		                <div class="row">
							<div class="col6 xs-break xs-mb15">
								<p><strong><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.actual"/></strong></p>
								<p class="f-verdeO">${correoCuentaAfirme.correoActual}</p>
							</div>
							<div class="col6 xs-break">
								&nbsp;
							</div>
		                </div>
		                
		                <div class="row">
							<div class="col6 xs-break xs-mb15">
								<p><strong><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.confirmar.correo.nuevo"/></strong></p>
								<p class="f-verdeO">${correoCuentaAfirme.correoNuevo}</p>
							</div>
							<div class="col6 xs-break">
								&nbsp;
							</div>
		                </div>
		                
		                <div class="row">
							<div class="col6 xs-break xs-mb15">
								<p><strong><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.confirmar.fecha.operacion"/></strong></p>
								<p class="f-verdeO">${correoCuentaAfirme.fechaOperacionddMMYY}</p>
							</div>
							<div class="col6 xs-break">
								&nbsp;
							</div>
		                </div>
						<div class="row">
							<div class="col6 xs-break xs-mb15">
								<p><strong><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.confirmar.hora.operacion"/></strong></p>
								<p class="f-verdeO">${correoCuentaAfirme.horaOperacionHHmm}</p>
							</div>
							<div class="col6 xs-break">
								&nbsp;
							</div>
		                </div>
		                
		                <div class="row">
							<div class="col6 xs-break xs-mb15">
								<p><strong><spring:message text="Code 809" code="afirmenet.etiqueta.conf.correo.confirmar.comision"/></strong></p>
								<p class="f-verdeO"><spring:message code="afirmenet.etiqueta.general.comprobante.sin.costo"/></p>
							</div>
							<div class="col6 xs-break">
								&nbsp;
							</div>
		                </div>
								            
			            <div class="bgcrema p20">
							<p class="f21 mb20"><strong class="f-gris6"><spring:message text="Code 809" code="afirmenet.transferencias.pregunta.finalizar"/></strong></p>
							<div class="row">
								<!--INCLUDE SOLICITA TOKEN-->
								<%@ include file="/WEB-INF/views/base/include/token.jsp"%>
					
								<div class="col6 xs-break">
									<p class="h6 mb5">&nbsp;</p>
									<div class="acciones" align="right">
										<a href="#" class="btn atras mr10"><spring:message text="Code 809" code="afirmenet.botones.atras"/></a>
										<a href="#" class="btn verde continuar"><spring:message text="Code 809" code="afirmenet.botones.continuar"/></a>
									</div>           
								</div>
				            </div>
						</div>

					</div> <!-- div p20 -->
					</form:form>
				</div>
			</div>
		</article>
	</div>
</section>


<%@ include file="/WEB-INF/views/base/include/footerSession.jsp"%>